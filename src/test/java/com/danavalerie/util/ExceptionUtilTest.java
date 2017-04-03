package com.danavalerie.util;

import com.danavalerie.util.exceptions.ExceptionUtil;
import com.danavalerie.util.stream.XRunnable;
import com.danavalerie.util.stream.XSupplier;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ExceptionUtilTest {

    @Test
    public void testThrowException() throws Exception {
        assertThatThrownBy(
                () -> ExceptionUtil.throwException(new IOException("message"))
        )
                .isOfAnyClassIn(IOException.class)
                .hasMessage("message");
    }

    @Test
    public void testUncheck_XSupplier() throws Exception {
        final XSupplier<Void> supplier = () -> {
            throw new IOException("message");
        };
        assertThatThrownBy(() -> ExceptionUtil.uncheck(supplier))
                .isOfAnyClassIn(IOException.class)
                .hasMessage("message");
    }

    @Test
    public void testUncheck_XRunnable() throws Exception {
        final XRunnable runnable = () -> {
            throw new IOException("message");
        };
        assertThatThrownBy(() -> ExceptionUtil.uncheck(runnable))
                .isOfAnyClassIn(IOException.class)
                .hasMessage("message");
    }

}
