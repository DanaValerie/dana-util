package com.danavalerie.util.stream;

public interface XBiConsumer<T, U> {

    void accept(T t, U u) throws Throwable;

}
