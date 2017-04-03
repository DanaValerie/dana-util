package com.danavalerie.util.collections;

import javax.annotation.Nonnull;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ConcurrentHashSet<E> extends AbstractSet<E> {

    private final Map<E, Boolean> _map = new ConcurrentHashMap<>();

    @Override
    public boolean add(final E e) {
        return _map.put(e, Boolean.TRUE) == null;
    }

    @Override
    public void clear() {
        _map.clear();
    }

    @Override
    public boolean contains(final Object o) {
        //noinspection SuspiciousMethodCalls
        return _map.containsKey(o);
    }

    @Override
    public boolean containsAll(@Nonnull final Collection<?> c) {
        return _map.keySet().containsAll(c);
    }

    @Override
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean equals(final Object o) {
        return o == this || _map.keySet().equals(o);
    }

    @Override
    public int hashCode() {
        return _map.keySet().hashCode();
    }

    @Override
    public Iterator<E> iterator() {
        return _map.keySet().iterator();
    }

    @Override
    public boolean remove(final Object o) {
        return _map.remove(o) != null;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        return _map.keySet().removeAll(c);
    }

    @Override
    public boolean retainAll(final @Nonnull Collection<?> c) {
        return _map.keySet().retainAll(c);
    }

    @Override
    public int size() {
        return _map.size();
    }

}

