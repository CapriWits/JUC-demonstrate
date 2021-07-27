package com.hypocrite30._5;

/**
 * volatile 适用于：
 * 1. 一个线程写，其他线程读 的变量
 * 2. double-checked-locking 问题中，synchronized外的共享变量 读取问题，防止指令重排列
 * 下面例子：volatile 并不能保证代码原子性，t1 t2都会执行 doInit()，还是需要加锁
 * @Description: volatile 习题
 * @Author: Hypocrite30
 * @Date: 2021/7/27 11:40
 */
public class TestVolatile2 {
    volatile boolean initialized = false;

    void init() {
        if (initialized) {
            return;
        }
        doInit();
        initialized = true;
    }

    private void doInit() {

    }
}
