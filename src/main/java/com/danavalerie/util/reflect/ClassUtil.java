package com.danavalerie.util.reflect;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public final class ClassUtil {

    private static final Map<Class<?>, Class<?>> primitiveToBox;
    private static final Map<Class<?>, Class<?>> boxToPrimitive;

    static {
        final Map<Class<?>, Class<?>> p2b = new LinkedHashMap<>();
        final Map<Class<?>, Class<?>> b2p = new LinkedHashMap<>();
        addPrimitiveType(p2b, b2p, boolean.class, Boolean.class);
        addPrimitiveType(p2b, b2p, byte.class, Byte.class);
        addPrimitiveType(p2b, b2p, char.class, Character.class);
        addPrimitiveType(p2b, b2p, short.class, Short.class);
        addPrimitiveType(p2b, b2p, int.class, Integer.class);
        addPrimitiveType(p2b, b2p, long.class, Long.class);
        addPrimitiveType(p2b, b2p, float.class, Float.class);
        addPrimitiveType(p2b, b2p, double.class, Double.class);
        addPrimitiveType(p2b, b2p, void.class, Void.class);
        primitiveToBox = Collections.unmodifiableMap(p2b);
        boxToPrimitive = Collections.unmodifiableMap(b2p);
    }

    private ClassUtil() {
    }

    private static void addPrimitiveType(
            final Map<Class<?>, Class<?>> p2b,
            final Map<Class<?>, Class<?>> b2p,
            final Class<?> primitiveClass,
            final Class<?> boxClass
    ) {
        p2b.put(primitiveClass, boxClass);
        b2p.put(boxClass, primitiveClass);
    }

    @Nonnull
    public static Class<?> box(@Nonnull final Class<?> javaClass) {
        return primitiveToBox.getOrDefault(javaClass, javaClass);
    }

    @Nonnull
    public static Class<?> unbox(@Nonnull final Class<?> javaClass) {
        return boxToPrimitive.getOrDefault(javaClass, javaClass);
    }

    @Nonnull
    public static Set<Class<?>> getPrimitiveTypes() {
        return primitiveToBox.keySet();
    }

    @Nonnull
    public static Set<Class<?>> getBoxTypes() {
        return boxToPrimitive.keySet();
    }

    @Nonnull
    public static Set<Class<?>> getAllClassesInHierarchy(@Nonnull Class<?> clazz) {
        final Set<Class<?>> allClassesInHierarchy = new HashSet<>();
        while (clazz != null) {
            allClassesInHierarchy.add(clazz);
            allClassesInHierarchy.addAll(Arrays.asList(clazz.getInterfaces()));
            clazz = clazz.getSuperclass();
        }
        return Collections.unmodifiableSet(allClassesInHierarchy);
    }

    @Nonnull
    public static String getPackage(@Nonnull final String name) {
        final int idx = name.lastIndexOf('.');
        return idx >= 0 ? name.substring(0, idx) : "";
    }

    @Nonnull
    public static String getNameNoPackage(@Nonnull final String name) {
        final int idx = name.lastIndexOf('.');
        return idx >= 0 ? name.substring(idx + 1) : "";
    }

}
