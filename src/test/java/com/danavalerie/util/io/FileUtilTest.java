package com.danavalerie.util.io;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class FileUtilTest {

    @Test
    public void testDeleteRecursively_EmptyDirectory() throws IOException {
        final Path tempDirectory = Files.createTempDirectory("temp-");
        try {
            assertThat(tempDirectory).exists();
            FileUtil.deleteRecursively(tempDirectory);
            assertThat(tempDirectory).doesNotExist();
        } finally {
            FileUtil.deleteRecursively(tempDirectory);
        }
    }

    @Test
    public void testDeleteRecursively_NonEmptyDirectory() throws IOException {
        final Path tempDirectory = Files.createTempDirectory("temp-");
        try {
            assertThat(tempDirectory).exists();
            Files.write(tempDirectory.resolve("tempfile.txt"), new byte[100]);
            FileUtil.deleteRecursively(tempDirectory);
            assertThat(tempDirectory).doesNotExist();
        } finally {
            FileUtil.deleteRecursively(tempDirectory);
        }
    }

    @Test
    public void testDeleteRecursively_NonExistentDirectory() throws IOException {
        final Path tempDirectory = Files.createTempDirectory("temp-");
        try {
            assertThat(tempDirectory).exists();
            FileUtil.deleteRecursively(tempDirectory);
            assertThat(tempDirectory).doesNotExist();
            FileUtil.deleteRecursively(tempDirectory); // should not throw exception
        } finally {
            FileUtil.deleteRecursively(tempDirectory);
        }
    }

}
