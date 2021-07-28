package com.hypocrite30._6;

import com.hypocrite30._6.util.UnsafeAccessor;
import sun.misc.Unsafe;

/**
 * @Description: 自定义原子 Integer，使用 Unsafe 实现
 * @Author: Hypocrite30
 * @Date: 2021/7/28 12:45
 */
public class DIYAtomicInteger {
    public static void main(String[] args) {
        Account.demo(new MyAtomicInteger(10000));
    }
}

class MyAtomicInteger implements Account {
    private volatile int value;
    private static final long valueOffset; // MyAtomicInteger class 中 字段 value 的偏移量
    private static final Unsafe UNSAFE;
    static {
        UNSAFE = UnsafeAccessor.getUnsafe();
        try {
            valueOffset = UNSAFE.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException(e); // static 中，try-catch负责监测，抛出运行时异常
        }
    }

    public int getValue() {
        return value;
    }

    public void decrement(int amount) {
        while(true) {
            int prev = this.value;
            int next = prev - amount;
            if (UNSAFE.compareAndSwapInt(this, valueOffset, prev, next)) {
                break;
            }
        }
    }

    public MyAtomicInteger(int value) {
        this.value = value;
    }

    @Override
    public Integer getBalance() {
        return getValue();
    }

    @Override
    public void withdraw(Integer amount) {
        decrement(amount);
    }
}
