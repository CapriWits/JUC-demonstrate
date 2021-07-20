package com.hypocrite30.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 线程休眠状态
 * @Author: Hypocrite30
 * @Date: 2021/7/15 17:55
 */
@Slf4j(topic = "c.Test6")
public class Test6 {

    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        t1.start();
        log.debug("t1 state: {}", t1.getState()); // c.Test6 [main] - t1 state: RUNNABLE

        try {
            Thread.sleep(500); // 让主线程休眠一会，让 t1 启动起来
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("t1 state: {}", t1.getState()); // c.Test6 [main] - t1 state: TIMED_WAITING 休眠状态
    }
}
