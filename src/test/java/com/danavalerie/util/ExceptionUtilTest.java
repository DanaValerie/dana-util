package com.danavalerie.util;

import com.danavalerie.util.exceptions.ExceptionUtil;
import com.danavalerie.util.stream.XRunnable;
import com.danavalerie.util.stream.XSupplier;
import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;

import java.io.IOException;
import java.net.ConnectException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ExceptionUtilTest {

    @Test
    public void testThrowException() throws Exception {
        assertThatThrownBy(
                new ThrowableAssert.ThrowingCallable() {
                    @Override
                    public void call() throws Throwable {
                        //noinspection ThrowableNotThrown
                        ExceptionUtil.throwException(new IOException("message"));
                    }
                }
        )
                .isOfAnyClassIn(IOException.class)
                .hasMessage("message");
    }

    @Test
    public void testUncheck_XSupplier() throws Exception {
        final XSupplier<Void> supplier = new XSupplier<Void>() {
            @Override
            public Void get() throws Throwable {
                throw new IOException("message");
            }
        };
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                ExceptionUtil.uncheck(supplier);
            }
        })
                .isOfAnyClassIn(IOException.class)
                .hasMessage("message");
    }

    @Test
    public void testUncheck_XRunnable() throws Exception {
        final XRunnable runnable = new XRunnable() {
            @Override
            public void run() throws Throwable {
                throw new IOException("message");
            }
        };
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                ExceptionUtil.uncheck(runnable);
            }
        })
                .isOfAnyClassIn(IOException.class)
                .hasMessage("message");
    }

    @Test
    public void testGetStackTraceAsString() {
        assertThat(
                ExceptionUtil.getStackTraceAsString(new ConnectException("foo"))
        ).startsWith(
                "java.net.ConnectException: foo\n\tat com.danavalerie.util.ExceptionUtilTest" +
                        ".testGetStackTraceAsString(ExceptionUtilTest.java:"
        );
    }

}
