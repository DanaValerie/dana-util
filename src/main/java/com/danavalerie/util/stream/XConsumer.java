package com.danavalerie.util.stream;

@FunctionalInterface
public interface XConsumer<T> {

    void accept(T t) throws Throwable;

}
