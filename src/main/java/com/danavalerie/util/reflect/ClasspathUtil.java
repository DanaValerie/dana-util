package com.danavalerie.util.reflect;

import com.danavalerie.util.ref.LazyVar;
import com.danavalerie.util.stream.StreamUtil;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ClasspathUtil {

    private static final LazyVar<List<URI>> _systemClassPathEntries = LazyVar.of(
            () -> Arrays
                    .stream(System.getProperty("java.class.path").split(Pattern.quote(File.pathSeparator)))
                    .map(str -> Paths.get(str).toUri())
                    .collect(Collectors.toList()));

    private ClasspathUtil() {
    }

    /**
     * Finds all classes at specified classpath entry location.
     */
    @Nonnull
    public static List<String> findAllClasses(@Nonnull final URI classpathEntry) throws IOException {
        if (!classpathEntry.getScheme().equals("file")) {
            throw new IllegalArgumentException("Only 'file' URLs are supported");
        }
        final Path dirOrJarFile = new File(classpathEntry).toPath();
        if (!Files.exists(dirOrJarFile)) {
            throw new FileNotFoundException(dirOrJarFile.toString());
        }
        return Files.isDirectory(dirOrJarFile)
                ? findAllClassesInDirectory(dirOrJarFile)
                : findAllClassesInJarFile(dirOrJarFile);
    }

    /**
     * Finds all classes in the specified directory (should be a valid classpath root).
     */
    @Nonnull
    private static List<String> findAllClassesInDirectory(
            final Path codebase
    ) throws IOException {
        final List<String> allClasses = new ArrayList<>();
        try (Stream<Path> allFiles = Files.walk(codebase)) {
            allFiles
                    .filter(path -> path.getFileName().toString().endsWith(".class"))
                    .map(codebase::relativize)
                    .map(relativePath ->
                            StreamUtil.from(relativePath.iterator())
                                    .map(Path::toString)
                                    .collect(Collectors.joining("."))
                    )
                    .map(ClasspathUtil::removeDotClass)
                    .forEach(allClasses::add);
        }
        return allClasses;
    }

    /**
     * Finds all classes in the specified Jar file.
     */
    @Nonnull
    private static List<String> findAllClassesInJarFile(
            final Path jarFile
    ) throws IOException {
        final List<String> allClasses = new ArrayList<>();
        try (JarInputStream in = new JarInputStream(Files.newInputStream(jarFile))) {
            JarEntry jarEntry;
            while ((jarEntry = (JarEntry) in.getNextEntry()) != null) {
                if (jarEntry.getName().endsWith(".class")) {
                    final String className = removeDotClass(
                            jarEntry.getName().replace('/', '.')
                    );
                    allClasses.add(className);
                }
            }
        }
        return allClasses;
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
