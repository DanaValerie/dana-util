package com.danavalerie.util.stream;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class StreamUtil {

    private StreamUtil() {
    }

    @Nonnull
    public static <T> Stream<T> from(@Nonnull final Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    @Nonnull
    public static <T> Stream<T> from(@Nonnull final Iterator<T> iterator) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED),
                false
        );
    }

}
