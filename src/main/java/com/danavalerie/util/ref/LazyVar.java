package com.danavalerie.util.ref;

import com.danavalerie.util.exceptions.ExceptionUtil;
import com.danavalerie.util.stream.XSupplier;

import javax.annotation.Nonnull;

/**
 * A thread-safe, lazily initialized _value.
 */
public final class LazyVar<T> {

    private final Object _lock = new Object();
    private XSupplier<T> _init;
    private volatile Ref<T> _value;

    private LazyVar(@Nonnull final XSupplier<T> init) {
        _init = init;
    }

    public T get() {
        // See https://en.wikipedia.org/wiki/Double-checked_locking#Usage_in_Java
        Ref<T> result = _value;
        if (result == null) {
            synchronized (_lock) {
                result = _value;
                if (result == null) {
                    try {
                        _value = result = new Ref<>(_init.get());
                        _init = null; // gc
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
