package com.danavalerie.util.ref;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Random;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class LazyVarTest {

    @Test
    public void testLazyVar() {
        final LazyVar<String> lazyVar = LazyVar.of(() -> "foo" + "bar");
        assertThat(lazyVar.get()).isEqualTo("foobar");
    }

    @Test
    public void testLazyVarIsOnlyInitializedOnce() {
        final Random random = new Random();
        final int[] initializationCount = new int[]{0};
        final LazyVar<Integer> lazyVar = LazyVar.of(() -> {
            Thread.sleep(random.nextInt(10));
            return initializationCount[0]++;
        });
        IntStream.range(0, 100)
                .parallel()
                .mapToObj(x -> lazyVar.get())
                .forEach(x -> Assertions.assertThat(x).isEqualTo(0));
        assertThat(initializationCount[0]).isEqualTo(1);
    }

}
