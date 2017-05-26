package com.danavalerie.util.collections;

import java.io.Serializable;
import java.util.Objects;

public final class Pair<F, S> implements Serializable {

    private static final long serialVersionUID = -6984815809160635589L;

    private final F _first;
    private final S _second;

    public Pair(final F first, final S second) {
        _first = first;
        _second = second;
    }

    public static <F, S> Pair<F, S> of(final F first, final S second) {
        return new Pair<>(first, second);
    }

    public F getFirst() {
        return _first;
    }

    public S getSecond() {
        return _second;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Pair<?, ?> other = (Pair<?, ?>) o;
        return Objects.equals(_first, other._first) && Objects.equals(_second, other._second);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(_first) + 31 * Objects.hashCode(_second);
    }

    @Override
    public String toString()
    {
        return "[" + _first + ", " + _second + "]";
    }

}
