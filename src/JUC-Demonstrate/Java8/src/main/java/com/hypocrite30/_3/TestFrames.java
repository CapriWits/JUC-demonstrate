package com.hypocrite30._3;

/**
 * @Description: JVM为方法分配一块栈内存
 * @Author: Hypocrite30
 * @Date: 2021/7/15 17:20
 */
public class TestFrames {
    public static void main(String[] args) {
        Thread t1 = new Thread(){
            @Override
            public void run() {
                method1(20);
            }
        };
        t1.setName("t1");
        t1.start();
        method1(10);
    }

    private static void method1(int x) {
        int y = x + 1;
        Object m = method2();
        System.out.println(m);
    }

    private static Object method2() {
        Object n = new Object();
        return n;
    }
}
