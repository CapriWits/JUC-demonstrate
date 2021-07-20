package com.hypocrite30._4;

import java.util.ArrayList;

/**
 * @Description: 全局变量与局部变量共享安全分析
 * @Author: Hypocrite30
 * @Date: 2021/7/17 15:53
 */
public class TestThreadSafe {
    static final int THREAD_NUMBER = 2;
    static final int LOOP_NUMBER = 200;

    public static void main(String[] args) {
        // ThreadUnsafe test = new ThreadUnsafe();
        // ThreadSafe test = new ThreadSafe();
        ThreadSafeSubClass test = new ThreadSafeSubClass();
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(() -> {
                test.method1(LOOP_NUMBER);
            }, "Thread" + (i + 1)).start();
        }
    }
}

/**
 * 成员变量，共享 list，不安全
 */
class ThreadUnsafe {
    ArrayList<String> list = new ArrayList<>();

    public void method1(int loopNumber) {
        for (int i = 0; i < loopNumber; i++) {
            method2();
            method3();
        }
    }

    private void method2() {
        list.add("1");
    }

    private void method3() {
        list.remove(0);
    }
}

/**
 * 线程安全，list 是局部变量，通过传参调用，只存在各自线程的局部变量表里
 */
class ThreadSafe {
    public final void method1(int loopNumber) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < loopNumber; i++) {
            method2(list);
            method3(list);
        }
    }

    private void method2(ArrayList<String> list) {
        list.add("1");
    }

    private void method3(ArrayList<String> list) {
        list.remove(0);
    }
}

/**
 * 又创建一个线程同时共享 list ，线程不安全
 */
class ThreadSafeSubClass extends ThreadSafe {
    // @Override
    public void method3(ArrayList<String> list) {
        System.out.println(2);
        new Thread(() -> {
            list.remove(0);
        }).start();
    }
}