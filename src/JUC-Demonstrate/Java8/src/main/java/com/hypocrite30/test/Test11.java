package com.hypocrite30.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: sleep,wait,join被interrupt打断，抛出异常
 * @Author: Hypocrite30
 * @Date: 2021/7/15 21:21
 */
@Slf4j(topic = "c.Test11")
public class Test11 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("sleep...");
            try {
                Thread.sleep(5000); // wait, join被打断后会把标记清空，重置为false
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");

        t1.start();
        Thread.sleep(1000); // 主线程等一会
        log.debug("interrupt");
        t1.interrupt();
        log.debug("打断标记:{}", t1.isInterrupted());
    }
}
