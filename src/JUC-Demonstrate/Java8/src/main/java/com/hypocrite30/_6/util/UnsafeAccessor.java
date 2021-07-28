package com.hypocrite30._6.util;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Description: 用反射构造 Unsafe 对象「单例」
 * @Author: Hypocrite30
 * @Date: 2021/7/28 12:42
 */
public class UnsafeAccessor {
    private static final Unsafe unsafe;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Error(e);
        }
    }

    public static Unsafe getUnsafe() {
        return unsafe;
    }
}
