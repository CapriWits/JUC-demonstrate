package com.hypocrite30._5;

import static com.hypocrite30._2.util.Sleeper.sleep;

/**
 * @Description: volatile 关键字使得成员变量或静态成员变量可见
 * @Author: Hypocrite30
 * @Date: 2021/7/26 18:32
 */
public class TestVolatile {
    /* volatile */ static boolean run = true;

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            while (run) {
                // ....
            }
        });
        t.start();
        sleep(1);
        run = false; // 线程t不会如预想的停下来，需要在 run 变量加上 volatile
    }

}
