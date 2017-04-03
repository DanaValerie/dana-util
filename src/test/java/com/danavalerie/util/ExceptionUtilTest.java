package com.danavalerie.util;

import com.danavalerie.util.exceptions.ExceptionUtil;
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
    public void testUncheck() throws Exception {
        assertThatThrownBy(
                () -> ExceptionUtil.uncheck(() -> {
                    throw new IOException("message");
                })
        )
                .isOfAnyClassIn(IOException.class)
                .hasMessage("message");
    }

}
