package com.danavalerie.util.stream;

import com.danavalerie.util.exceptions.ExceptionUtil;
import com.danavalerie.util.reflect.ClassUtil;
import com.danavalerie.util.reflect.ClasspathUtil;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class FunctionalInterfaceTest {

    @Test
    public void testAllXInterfacesAreAnnotatedAsFunctionalInterface() throws Exception {
        ClasspathUtil.findAllClasses(
                XFunction.class.getProtectionDomain().getCodeSource().getLocation().toURI()
        )
                .stream()
                .map(name -> ExceptionUtil.<Class>uncheck(() -> Class.forName(name)))
                .filter(clazz -> clazz.getPackage().equals(XFunction.class.getPackage()))
                .filter(Class::isInterface)
                .filter(clazz -> clazz.getSimpleName().startsWith("X"))
                .forEach(
                        clazz -> assertThat(
                                clazz.getAnnotation(FunctionalInterface.class)
                        ).as(clazz.getName()).isNotNull()
                );
    }

}
