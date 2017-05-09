package com.danavalerie.util.stream;

public interface XBiFunction<T, U, R> {

    R apply(T t, U u) throws Throwable;

}
