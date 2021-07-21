package com.hypocrite30.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 线程必须获得锁，才能调用 wait()
 * @Author: Hypocrite30
 * @Date: 2021/7/21 16:02
 */
@Slf4j(topic = "c.Test18")
public class Test18 {
    static final Object lock = new Object();
    public static void main(String[] args) {

        synchronized (lock) { // 这里主线程先获得锁
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
