package com.danavalerie.util.collections;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PairTest {

    @Test
    public void testPair() {
        final Pair<String, String> pair = new Pair<>("foo", "bar");
        assertThat(pair.getFirst()).isEqualTo("foo");
        assertThat(pair.getSecond()).isEqualTo("bar");
    }

    @Test
    public void testEqualsAndHashCode() {
        final Pair<String, String> pair1 = new Pair<>("foo", "bar");
        final Pair<String, String> pair2 = new Pair<>("foo", "bar");
        final Pair<String, String> pair3 = new Pair<>("bar", "foo");
        assertThat(pair1).isEqualTo(pair2);
        assertThat(pair1).isNotEqualTo(pair3);
        assertThat(pair1.hashCode()).isEqualTo(pair2.hashCode());
    }

    @Test
    public void testNullValue() {
        final Pair<String, String> pair1 = new Pair<>("foo", null);
        final Pair<String, String> pair2 = new Pair<>("foo", null);
        final Pair<String, String> pair3 = new Pair<>(null, "foo");
        assertThat(pair1.getFirst()).isEqualTo("foo");
        assertThat(pair1.getSecond()).isNull();
        assertThat(pair1).isEqualTo(pair2);
        assertThat(pair1).isNotEqualTo(pair3);
    }

    @Test
    public void testToString() {
        assertThat(new Pair<>("foo", "bar").toString()).isEqualTo("[foo, bar]");
        assertThat(new Pair<>(null, "bar").toString()).isEqualTo("[null, bar]");
        assertThat(new Pair<>("foo", null).toString()).isEqualTo("[foo, null]");
        assertThat(new Pair<>(null, null).toString()).isEqualTo("[null, null]");
    }
}
