package com.danavalerie.util.stream;

public interface XConsumer<T> {

    void accept(T t) throws Throwable;

}
