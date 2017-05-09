package com.danavalerie.util.stream;

public interface XFunction<T, R> {

    R apply(T t) throws Throwable;

}
