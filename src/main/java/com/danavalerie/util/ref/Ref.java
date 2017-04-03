package com.danavalerie.util.ref;

/**
 * A simple reference to an object. Not thread safe.
 */
public final class Ref<T> {

    private T value;

    public Ref() {
        this(null);
    }

    public Ref(final T initialValue) {
        value = initialValue;
    }

    public T get() {
        return value;
    }

    public void set(final T value) {
        this.value = value;
    }

    public static <T> Ref<T> of(final T value) {
        return new Ref<>(value);
    }

}
