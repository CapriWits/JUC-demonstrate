package com.hypocrite30.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 获得线程状态
 * @Author: Hypocrite30
 * @Date: 2021/7/15 17:53
 */
@Slf4j(topic = "c.Test5")
public class Test5 {

    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.debug("running...");
            }
        };

        System.out.println(t1.getState()); // NEW
        t1.start();
        System.out.println(t1.getState()); // RUNNABLE
    }
}
