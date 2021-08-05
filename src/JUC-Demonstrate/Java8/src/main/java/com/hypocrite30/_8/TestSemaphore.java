package com.hypocrite30._8;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

import static com.hypocrite30._2.util.Sleeper.sleep;

/**
 * 信号量类比于停车场的剩余车位指示
 * @Description: 测试信号量
 * @Author: Hypocrite30
 * @Date: 2021/8/5 11:36
 */
@Slf4j(topic = "c.TestSemaphore")
public class TestSemaphore {
    public static void main(String[] args) {
        // 1. 创建 semaphore 对象
        Semaphore semaphore = new Semaphore(3);

        // 2. 10个线程同时运行
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire(); // 获取信号量
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    log.debug("running...");
                    sleep(1);
                    log.debug("end...");
                } finally {
                    semaphore.release(); // 释放信号量
                }
            }).start();
        }
    }
}
