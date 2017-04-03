package com.danavalerie.util.reflect;

import com.danavalerie.util.io.FileUtil;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ClasspathUtilTest {

    @Test
    public void findAllClasses_Directory() throws Exception {
        final Path tempDirectory = Files.createTempDirectory("temp-");
        try {
            final Path fooDirectory = tempDirectory.resolve("foo");
            Files.createDirectory(fooDirectory);
            final Path barClass = fooDirectory.resolve("Bar.class");
            Files.write(barClass, new byte[100]);
            final Path barDirectory = fooDirectory.resolve("bar");
            Files.createDirectory(barDirectory);
            final Path bazClass = barDirectory.resolve("Baz.class");
            Files.write(bazClass, new byte[100]);
            final List<String> actual = ClasspathUtil.findAllClasses(tempDirectory.toUri());
            assertThat(actual).containsExactlyInAnyOrder("foo.Bar", "foo.bar.Baz");
        } finally {
            FileUtil.deleteRecursively(tempDirectory);
        }
    }

    @Test
    public void findAllClasses_JarFile() throws Exception {
        final Path tempDirectory = Files.createTempDirectory("temp-");
        try {
            final Path jarPath = tempDirectory.resolve("temp.jar");
            final JarOutputStream out = new JarOutputStream(Files.newOutputStream(jarPath));
            out.putNextEntry(new JarEntry("foo/Bar.class"));
            out.closeEntry();
            out.putNextEntry(new JarEntry("foo/bar/Baz.class"));
            out.closeEntry();
            out.close();
            final List<String> actual = ClasspathUtil.findAllClasses(jarPath.toUri());
            assertThat(actual).containsExactlyInAnyOrder("foo.Bar", "foo.bar.Baz");
        } finally {
            FileUtil.deleteRecursively(tempDirectory);
        }
    }

}
