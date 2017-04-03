package com.danavalerie.util.ref;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RefTest {

    @Test
    public void testRef() {
        final Ref<String> ref = new Ref<>();
        assertThat(ref.get()).isNull();
        ref.set("foo");
        assertThat(ref.get()).isEqualTo("foo");
        ref.set("bar");
        assertThat(ref.get()).isEqualTo("bar");
        ref.set(null);
        assertThat(ref.get()).isNull();
        ref.set("baz");
        assertThat(ref.get()).isEqualTo("baz");
    }

}
