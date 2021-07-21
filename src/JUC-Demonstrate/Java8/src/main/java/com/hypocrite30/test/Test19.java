package com.hypocrite30.test;

import com.hypocrite30._2.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: wait 与 sleep 区别
 * @Author: Hypocrite30
 * @Date: 2021/7/21 16:13
 */
@Slf4j(topic = "c.Test19")
public class Test19 {

    static final Object lock = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (lock) {
                log.debug("获得锁");
                try {
                    // Thread.sleep(20000); // sleep 阻塞不让锁
                    lock.wait(20000); // wait 让锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        Sleeper.sleep(1);
        synchronized (lock) {
            log.debug("获得锁");
        }
    }
}