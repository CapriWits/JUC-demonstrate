package com.hypocrite30.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 线程交替执行
 * @Author: Hypocrite30
 * @Date: 2021/7/15 17:51
 */
@Slf4j(topic = "c.Test3")
public class Test3 {
    public static void main(String[] args) {
        new Thread(() -> {
            while(true) {
                log.debug("running...");
            }
        }, "t1").start();

        new Thread(() -> {
            while(true) {
                log.debug("running...");
            }
        }, "t2").start();
    }
}
