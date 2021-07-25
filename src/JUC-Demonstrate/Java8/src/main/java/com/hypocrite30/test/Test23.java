package com.hypocrite30.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

import static com.hypocrite30._2.util.Sleeper.sleep;

/**
 * @Description: ReentrantLock 可打断
 * @Author: Hypocrite30
 * @Date: 2021/7/25 17:34
 */
@Slf4j(topic = "c.Test23")
public class Test23 {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            log.debug("启动...");
            try {
                /**
                 * 如果没有竞争那么此方法就会获取Lock对象锁
                 * 如果有竞争就进入阻塞队列，可以被其它线程用 interrupt 方法打断
                 */
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("等锁的过程中被打断");
                return;
            }
            try {
                log.debug("获得了锁");
            } finally {
                lock.unlock();
            }
        }, "t1");

        lock.lock();
        log.debug("获得了锁");
        t1.start();
        try {
            sleep(1);
            t1.interrupt();
            log.debug("执行打断");
        } finally {
            lock.unlock();
        }
    }

}
