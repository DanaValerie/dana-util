package com.danavalerie.util.ref;

import com.danavalerie.util.stream.XSupplier;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

public class LazyVarTest {

    @Test
    public void testLazyVar() {
        final LazyVar<String> lazyVar = LazyVar.of(new XSupplier<String>() {
            @Override
            public String get() throws Throwable {
                return "foo" + "bar";
            }
        });
        assertThat(lazyVar.get()).isEqualTo("foobar");
    }

    @Test
    public void testLazyVarIsOnlyInitializedOnce() throws ExecutionException, InterruptedException {
        final Random random = new Random();
        final int[] initializationCount = new int[]{0};
        final LazyVar<Integer> lazyVar = LazyVar.of(new XSupplier<Integer>() {
            @Override
            public Integer get() throws Throwable {
                Thread.sleep(random.nextInt(10));
                return initializationCount[0]++;
            }
        });
        final ExecutorService pool = Executors.newCachedThreadPool();
        final List<Future<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            futures.add(
                    pool.submit(new Callable<Integer>() {
                        @Override
                        public Integer call() throws Exception {
                            return lazyVar.get();
                        }
                    })
            );
        }
        for (final Future<Integer> future : futures) {
            assertThat(future.get()).isEqualTo(0);
        }
        assertThat(initializationCount[0]).isEqualTo(1);
    }

}
