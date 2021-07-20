package com.hypocrite30.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 创建线程并命名
 * @Author: Hypocrite30
 * @Date: 2021/7/15 16:16
 */
@Slf4j(topic = "c.Test1")
public class Test1 {

    public static void test2() {
        Thread t = new Thread(() -> {
            log.debug("running");
        }, "t2");

        t.start();
    }

    public static void test1() {
        Thread t = new Thread() {
            @Override
            public void run() {
                log.debug("running");
            }
        };
        t.setName("t1");
        t.start();
    }
}
