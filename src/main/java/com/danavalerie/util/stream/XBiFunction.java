package com.danavalerie.util.stream;

@FunctionalInterface
public interface XBiFunction<T, U, R> {

    R apply(T t, U u) throws Throwable;

}
