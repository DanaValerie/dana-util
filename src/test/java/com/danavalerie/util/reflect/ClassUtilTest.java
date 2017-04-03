package com.danavalerie.util.reflect;

import org.junit.Test;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ClassUtilTest {

    @Test
    public void testGetBoxTypes() {
        assertThat(ClassUtil.getBoxTypes()).containsExactlyInAnyOrder(
                Boolean.class,
                Byte.class,
                Character.class,
                Short.class,
                Integer.class,
                Long.class,
                Float.class,
                Double.class,
                Void.class
        );
    }

    @Test
    public void testGetPrimitiveTypes() {
        assertThat(ClassUtil.getPrimitiveTypes()).containsExactlyInAnyOrder(
                boolean.class,
                byte.class,
                char.class,
                short.class,
                int.class,
                long.class,
                float.class,
                double.class,
                void.class
        );
    }

    @Test
    public void testBox() {
        assertThat(ClassUtil.box(boolean.class)).isEqualTo(Boolean.class);
        assertThat(ClassUtil.box(byte.class)).isEqualTo(Byte.class);
        assertThat(ClassUtil.box(char.class)).isEqualTo(Character.class);
        assertThat(ClassUtil.box(short.class)).isEqualTo(Short.class);
        assertThat(ClassUtil.box(int.class)).isEqualTo(Integer.class);
        assertThat(ClassUtil.box(long.class)).isEqualTo(Long.class);
        assertThat(ClassUtil.box(float.class)).isEqualTo(Float.class);
        assertThat(ClassUtil.box(double.class)).isEqualTo(Double.class);
        assertThat(ClassUtil.box(void.class)).isEqualTo(Void.class);
    }

    @Test
    public void testUnbox() {
        assertThat(ClassUtil.unbox(Boolean.class)).isEqualTo(boolean.class);
        assertThat(ClassUtil.unbox(Byte.class)).isEqualTo(byte.class);
        assertThat(ClassUtil.unbox(Character.class)).isEqualTo(char.class);
        assertThat(ClassUtil.unbox(Short.class)).isEqualTo(short.class);
        assertThat(ClassUtil.unbox(Integer.class)).isEqualTo(int.class);
        assertThat(ClassUtil.unbox(Long.class)).isEqualTo(long.class);
        assertThat(ClassUtil.unbox(Float.class)).isEqualTo(float.class);
        assertThat(ClassUtil.unbox(Double.class)).isEqualTo(double.class);
        assertThat(ClassUtil.unbox(Void.class)).isEqualTo(void.class);
    }

    @Test
    public void testReciprocityOfPrimitiveTypes() {
        for (final Class<?> primitiveType : ClassUtil.getPrimitiveTypes()) {
            final Class<?> boxType = ClassUtil.box(primitiveType);
            final Class<?> actual = ClassUtil.unbox(boxType);
            assertThat(actual).isEqualTo(primitiveType);
        }
    }

    @Test
    public void testBoxOfBoxTypeIsBoxType() {
        assertThat(ClassUtil.box(Boolean.class)).isEqualTo(Boolean.class);
    }

    @Test
    public void testBoxOfMiscTypeIsSameType() {
        assertThat(ClassUtil.box(URI.class)).isEqualTo(URI.class);
    }

    @Test
    public void testUnboxOfPrimitiveTypeIsPrimitiveType() {
        assertThat(ClassUtil.unbox(boolean.class)).isEqualTo(boolean.class);
    }

    @Test
    public void testUnboxOfMiscTypeIsSameType() {
        assertThat(ClassUtil.unbox(URI.class)).isEqualTo(URI.class);
    }

    @Test
    public void testGetAllClassesInHierarchy() throws Exception {
        assertThat(ClassUtil.getAllClassesInHierarchy(MySubclass.class)).containsExactlyInAnyOrder(
                MySuperclass.class,
                MySubclass.class,
                MySuperclassInterface.class,
                MySubclassInterface.class,
                Object.class
        );
        assertThat(ClassUtil.getAllClassesInHierarchy(MySuperclass.class)).containsExactlyInAnyOrder(
                MySuperclass.class,
                MySuperclassInterface.class,
                Object.class
        );
        assertThat(ClassUtil.getAllClassesInHierarchy(Object.class)).containsExactlyInAnyOrder(
                Object.class
        );
        // hierarchy of interface does not include java.lang.Object
        assertThat(ClassUtil.getAllClassesInHierarchy(Runnable.class)).containsExactlyInAnyOrder(
                Runnable.class
        );
    }

    @Test
    public void testGetPackage() {
        assertThat(getGetPackageChain("foo.bar.Baz$Buz")).containsExactly("foo.bar", "foo", "");
        assertThat(getGetPackageChain("foo.bar")).containsExactly("foo", "");
        assertThat(getGetPackageChain(".foo")).containsExactly("");
        assertThat(getGetPackageChain(".")).containsExactly("");
        assertThat(getGetPackageChain("")).isEmpty();
    }

    @Test
    public void testGetNameNoPackage() {
        assertThat(ClassUtil.getNameNoPackage("java.lang.String")).isEqualTo("String");
        assertThat(ClassUtil.getNameNoPackage("java.lang")).isEqualTo("lang");
        assertThat(ClassUtil.getNameNoPackage(".foo")).isEqualTo("foo");
        assertThat(ClassUtil.getNameNoPackage(".")).isEqualTo("");
        assertThat(ClassUtil.getNameNoPackage("")).isEqualTo("");
    }

    private List<String> getGetPackageChain(String name) {
        final List<String> result = new ArrayList<>();
        while (!name.isEmpty()) {
            name = ClassUtil.getPackage(name);
            result.add(name);
        }
        return result;
    }

    private static class MySubclass extends MySuperclass implements MySubclassInterface {
    }

    private static class MySuperclass implements MySuperclassInterface {
    }

    private interface MySubclassInterface {
    }

    private interface MySuperclassInterface {
    }

}
