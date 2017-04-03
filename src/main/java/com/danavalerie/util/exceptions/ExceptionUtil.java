package com.danavalerie.util.exceptions;

import com.danavalerie.util.stream.XRunnable;
import com.danavalerie.util.stream.XSupplier;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.security.SecureClassLoader;
import java.util.UUID;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;

public final class ExceptionUtil {

    private static final Type RETHROWER_GENERATED_ASM_TYPE = Type.getObjectType(
            "ExceptionUtil__Rethrower__" + UUID.randomUUID()
    );
    private static final ExceptionThrower RETHROWER = makeRethrower();

    private ExceptionUtil() {
    }

    private static ExceptionThrower makeRethrower() {
        try {
            return (ExceptionThrower) new SecureClassLoader() {
                public Class<?> defineRethrowerClass() {
                    final byte[] bytecode = generateRethrowerClassBytecode();
                    return defineClass(
                            RETHROWER_GENERATED_ASM_TYPE.getInternalName(),
                            bytecode,
                            0,
                            bytecode.length
                    );
                }
            }.defineRethrowerClass().newInstance();
        } catch (final Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private static byte[] generateRethrowerClassBytecode() {
        final ClassWriter cw = new ClassWriter(0);
        final String internalName = RETHROWER_GENERATED_ASM_TYPE.getInternalName();
        cw.visit(
                Opcodes.V1_8,
                ACC_PUBLIC,
                internalName,
                null,
                Type.getInternalName(Object.class),
                new String[]{Type.getType(ExceptionThrower.class).getInternalName()}
        );
        {
            // constructor
            final MethodVisitor mv = cw.visitMethod(
                    ACC_PUBLIC,
                    "<init>",
                    "()V",
                    null,
                    null
            );
            mv.visitMaxs(1, 1);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitMethodInsn(
                    Opcodes.INVOKESPECIAL,
                    Type.getInternalName(Object.class),
                    "<init>",
                    "()V",
                    true
            );
            mv.visitInsn(Opcodes.RETURN);
            mv.visitEnd();
        }
        {
            // public void throwException(Throwable t) { throw t; }
            final MethodVisitor mv = cw.visitMethod(
                    ACC_PUBLIC,
                    "throwException",
                    "(Ljava/lang/Throwable;)V",
                    null,
                    null
            );
            mv.visitMaxs(1, 2);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitInsn(Opcodes.ATHROW);
            mv.visitEnd();
        }
        cw.visitEnd();
        return cw.toByteArray();
    }

    public static RuntimeException throwException(final Throwable t) {
        RETHROWER.throwException(t);
        throw new IllegalStateException(); // should never get here
    }

    public static void uncheck(final XRunnable runnable) {
        try {
            runnable.run();
        } catch (final Throwable t) {
            throw throwException(t);
        }
    }

    public static <T> T uncheck(final XSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (final Throwable t) {
            throw throwException(t);
        }
    }

    public interface ExceptionThrower {
        void throwException(Throwable t);
    }
}
