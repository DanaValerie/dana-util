package com.danavalerie.util.io;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class IoStreamUtilTest {

    @Test
    public void testGetContent() {
        final Random random = new Random();
        final byte[] expected = new byte[1024 * 1024];
        random.nextBytes(expected);
        final ByteArrayInputStream bais = new ByteArrayInputStream(expected);
        final byte[] actual = IoStreamUtil.getContent(bais);
        assertThat(actual).containsExactly(expected);
    }

    @Test
    public void testGetStringContent() {
        final ByteArrayInputStream bais = new ByteArrayInputStream("foobar".getBytes(StandardCharsets.UTF_8));
        assertThat(IoStreamUtil.getStringContent(bais, StandardCharsets.UTF_8)).isEqualTo("foobar");
    }

    @Test
    public void testCopy() throws IOException {
        final Random random = new Random();
        final byte[] expected = new byte[1024 * 1024];
        random.nextBytes(expected);
        final ByteArrayInputStream bais = new ByteArrayInputStream(expected);
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        IoStreamUtil.copy(bais, baos);
        final byte[] actual = baos.toByteArray();
        assertThat(actual).containsExactly(expected);
    }

}
