package com.hypocrite30.pattern.SequentialControl;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 同步控制 固定运行顺序 wait-notify 版
 * @Author: Hypocrite30
 * @Date: 2021/7/25 22:43
 */
@Slf4j(topic = "c.v1_1")
public class v1_1 {
    static final Object lock = new Object();
    // 表示 t2 是否运行过
    static boolean t2runned = false;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                while (!t2runned) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("1");
            }
        }, "t1");


        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                log.debug("2");
                t2runned = true;
                lock.notify();
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}

