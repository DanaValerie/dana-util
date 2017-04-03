package com.danavalerie.util.ref;

import com.danavalerie.util.exceptions.ExceptionUtil;
import com.danavalerie.util.stream.XSupplier;

import javax.annotation.Nonnull;

/**
 * A thread-safe, lazily initialized value.
 */
public final class LazyVar<T> {

    private final Object lock = new Object();
    private XSupplier<T> init;
    private volatile Ref<T> value;

    private LazyVar(@Nonnull final XSupplier<T> init) {
        this.init = init;
    }

    public T get() {
        // See https://en.wikipedia.org/wiki/Double-checked_locking#Usage_in_Java
        Ref<T> result = value;
        if (result == null) {
            synchronized (lock) {
                result = value;
                if (result == null) {
                    try {
                        value = result = new Ref<>(init.get());
                        init = null; // gc
                    } catch (final Throwable t) {
                        throw ExceptionUtil.throwException(t);
                    }
                }
            }
        }
        return result.get();
    }

    public static <T> LazyVar<T> of(final XSupplier<T> init) {
        return new LazyVar<>(init);
    }

}
