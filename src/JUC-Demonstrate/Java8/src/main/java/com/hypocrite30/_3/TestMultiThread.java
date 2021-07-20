package com.hypocrite30._3;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 线程交替执行
 * @Author: Hypocrite30
 * @Date: 2021/7/15 17:06
 */
@Slf4j(topic = "c.TestMultiThread")
public class TestMultiThread {

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                log.debug("running");
            }
        }, "t1").start();
        new Thread(() -> {
            while (true) {
                log.debug("running");
            }
        }, "t2").start();
    }
}
