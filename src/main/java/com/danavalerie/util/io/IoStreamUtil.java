package com.danavalerie.util.io;

import com.danavalerie.util.exceptions.ExceptionUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

public final class IoStreamUtil {

    private IoStreamUtil() {
    }

    public static String getStringContent(final InputStream is, final Charset charset) {
        return new String(getContent(is), charset);
    }

    public static byte[] getContent(final InputStream is) {
        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            copy(is, baos);
            return baos.toByteArray();
        } catch (final IOException ex) {
            throw ExceptionUtil.throwException(ex);
        }
    }

    public static void copy(final InputStream is, final OutputStream baos) throws IOException {
        final byte[] buf = new byte[16384];
        while (true) {
            final int count = is.read(buf);
            if (count < 0) {
                break; // EOF
            }
            baos.write(buf, 0, count);
        }
    }

}
