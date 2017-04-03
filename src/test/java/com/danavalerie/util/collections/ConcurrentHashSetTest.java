package com.danavalerie.util.collections;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConcurrentHashSetTest {

    @Test
    public void test() {
        final ConcurrentHashSet<String> set = new ConcurrentHashSet<>();
        set.add("foo");
        set.add("bar");
        set.add("foo");
        set.add("baz");
        assertThat(set).containsExactlyInAnyOrder("foo", "bar", "baz");
    }

}
