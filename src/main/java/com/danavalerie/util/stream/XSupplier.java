package com.danavalerie.util.stream;

@FunctionalInterface
public interface XSupplier<T> {

    T get() throws Throwable;

}
