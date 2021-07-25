package com.hypocrite30.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static com.hypocrite30._2.util.Sleeper.sleep;

/**
 * @Description: ReentrantLock 锁超时
 * @Author: Hypocrite30
 * @Date: 2021/7/25 21:34
 */
@Slf4j(topic = "c.Test24")
public class Test24 {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("尝试获得锁");
            try {
                if (!lock.tryLock(2, TimeUnit.SECONDS)) { // 等2s
                    log.debug("获取不到锁");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("获取不到锁");
                return;
            }
            try {
                log.debug("获得到锁");
            } finally {
                lock.unlock();
            }
        }, "t1");

        lock.lock();
        log.debug("获得到锁");
        t1.start();
        sleep(1);
        log.debug("释放了锁");
        lock.unlock();
    }

}
