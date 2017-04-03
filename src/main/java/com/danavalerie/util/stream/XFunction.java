package com.danavalerie.util.stream;

@FunctionalInterface
public interface XFunction<T, R> {

    R apply(T t) throws Throwable;

}
