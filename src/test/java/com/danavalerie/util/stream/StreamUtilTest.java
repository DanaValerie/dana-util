package com.danavalerie.util.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class StreamUtilTest {

    @Test
    public void testFromIterable() {
        final List<Integer> values = Arrays.asList(17, 41, 2);
        //noinspection RedundantCast
        final Stream<Integer> stream = StreamUtil.from((Iterable<Integer>) values);
        assertThat(stream.mapToInt(x -> x).sum()).isEqualTo(60);
    }

    @Test
    public void testFromIterator() {
        final Iterator<Integer> iterator = Arrays.asList(17, 41, 2).iterator();
        final Stream<Integer> stream = StreamUtil.from(iterator);
        assertThat(stream.mapToInt(x -> x).sum()).isEqualTo(60);
    }

}
