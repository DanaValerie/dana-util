package com.danavalerie.util.ref;

/**
 * A simple reference to an object. Not thread safe.
 */
public final class Ref<T> {

    private T _value;

    public Ref() {
        this(null);
    }

    public Ref(final T initialValue) {
        _value = initialValue;
    }

    public T get() {
        return _value;
    }

    public void set(final T value) {
        _value = value;
    }

    public static <T> Ref<T> of(final T value) {
        return new Ref<>(value);
    }

}
