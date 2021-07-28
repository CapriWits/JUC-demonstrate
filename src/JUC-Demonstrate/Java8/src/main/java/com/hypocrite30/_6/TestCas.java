package com.hypocrite30._6;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

import static com.hypocrite30._2.util.Sleeper.sleep;

/**
 * @Description: 模拟实现 CAS
 * @Author: Hypocrite30
 * @Date: 2021/7/28 11:24
 */
@Slf4j(topic = "c.Test42")
public class TestCas {
    // 0 没加锁
    // 1 加锁
    private AtomicInteger state = new AtomicInteger(0);

    public void lock() {
        while (true) {
            if (state.compareAndSet(0, 1)) {
                break;
            }
        }
    }

    public void unlock() {
        log.debug("unlock...");
        state.set(0);
    }

    public static void main(String[] args) {
        TestCas lock = new TestCas();
        new Thread(() -> {
            log.debug("begin...");
            lock.lock();
            try {
                log.debug("lock...");
                sleep(1);
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            log.debug("begin...");
            lock.lock();
            try {
                log.debug("lock...");
            } finally {
                lock.unlock();
            }
        }).start();
    }
}
