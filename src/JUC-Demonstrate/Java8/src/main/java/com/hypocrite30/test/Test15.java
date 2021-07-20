package com.hypocrite30.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 守护线程：其他非守护线程运行结束，守护线程会马上结束
 * @Author: Hypocrite30
 * @Date: 2021/7/16 15:25
 */
@Slf4j(topic = "c.Test15")
public class Test15 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
            log.debug("结束");
        }, "t1");
        t1.setDaemon(true);
        t1.start();

        Thread.sleep(1000);
        log.debug("结束");
    }
}
