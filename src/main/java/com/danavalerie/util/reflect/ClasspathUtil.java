package com.danavalerie.util.reflect;

import com.danavalerie.util.ref.LazyVar;
import com.danavalerie.util.stream.XSupplier;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.regex.Pattern;

public final class ClasspathUtil {

    private static final LazyVar<List<URI>> _systemClassPathEntries = LazyVar.of(
            new XSupplier<List<URI>>() {
                @Override
                public List<URI> get() throws Throwable {
                    final String[] cpes = System.getProperty("java.class.path").split(Pattern.quote(File.pathSeparator));
                    final List<URI> ret = new ArrayList<>(cpes.length);
                    for (final String cpe : cpes) {
                        final URI uri = Paths.get(cpe).toUri();
                        ret.add(uri);
                    }
                    return ret;
                }
            });

    private ClasspathUtil() {
    }

    /**
     * Finds all classes at specified classpath entry location.
     */
    @Nonnull
    public static List<String> findAllClasses(@Nonnull final URI classpathEntry) throws IOException {
        final List<String> results = new ArrayList<>();
        for (String resourceName : findAllResources(classpathEntry)) {
            if (!resourceName.endsWith(".class")) {
                continue;
            }
            resourceName = removeDotClass(resourceName);
            resourceName = resourceName.replace('/', '.');
            results.add(resourceName);
        }
        return results;
    }

    /**
     * Finds all resources at specified classpath entry location.
     */
    @Nonnull
    public static List<String> findAllResources(@Nonnull final URI classpathEntry) throws IOException {
        if (!classpathEntry.getScheme().equals("file")) {
            throw new IllegalArgumentException("Only 'file' URLs are supported");
        }
        final Path dirOrJarFile = new File(classpathEntry).toPath();
        if (!Files.exists(dirOrJarFile)) {
            throw new FileNotFoundException(dirOrJarFile.toString());
        }
        return Collections.unmodifiableList(
                Files.isDirectory(dirOrJarFile)
                ? findAllResourcesInDirectory(dirOrJarFile)
                : findAllResourcesInJarFile(dirOrJarFile)
        );
    }

    /**
     * Finds all classes in the specified directory (should be a valid classpath root).
     */
    @Nonnull
    private static List<String> findAllResourcesInDirectory(final Path codebase) throws IOException {
        final List<String> allClasses = new ArrayList<>();
        Files.walkFileTree(codebase, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                final Path relativePath = codebase.relativize(file);
                final StringBuilder sb = new StringBuilder();
                for (final Path part : relativePath) {
                    if (sb.length() > 0) {
                        sb.append('/');
                    }
                    sb.append(part);
                }
                allClasses.add(sb.toString());
                return FileVisitResult.CONTINUE;
            }
        });
        return allClasses;
    }

    /**
     * Finds all classes in the specified Jar file.
     */
    @Nonnull
    private static List<String> findAllResourcesInJarFile(
            final Path jarFile
    ) throws IOException {
        final List<String> allResources = new ArrayList<>();
        try (JarInputStream in = new JarInputStream(Files.newInputStream(jarFile))) {
            JarEntry jarEntry;
            while ((jarEntry = (JarEntry) in.getNextEntry()) != null) {
                if (! jarEntry.isDirectory()) {
                    allResources.add(jarEntry.getName());
                }
            }
        }
        return allResources;
    }

    /**
     * Removes ".class" from the supplied string.
     */
    @Nonnull
    private static String removeDotClass(@Nonnull final String classNamePlusDotClass) {
        return classNamePlusDotClass.substring(
                0,
                classNamePlusDotClass.length() - 6
        );
    }

    public static List<URI> getSystemClassPathEntries() {
        return _systemClassPathEntries.get();
    }

}
