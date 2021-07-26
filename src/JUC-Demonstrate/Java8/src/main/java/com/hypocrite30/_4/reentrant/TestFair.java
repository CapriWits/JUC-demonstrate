package com.hypocrite30._4.reentrant;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: ReentranLock 默认不公平
 * @Author: Hypocrite30
 * @Date: 2021/7/26 17:01
 */
public class TestFair {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock(false);

        lock.lock();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + " running...");
                } finally {
                    lock.unlock();
                }
            }, "t" + i).start();
        }

        // 1s 之后去争抢锁
        Thread.sleep(1000);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + " running...");
                } finally {
                    lock.unlock();
                }
            }, "强行插入").start();
        }
        lock.unlock();
    }
}
