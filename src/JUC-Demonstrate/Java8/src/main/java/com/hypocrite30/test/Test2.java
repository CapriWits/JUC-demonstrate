package com.hypocrite30.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: Runnable 创建线程，命名
 * @Author: Hypocrite30
 * @Date: 2021/7/15 16:22
 */
@Slf4j(topic = "c.Test2")
public class Test2 {
    public static void main(String[] args) {
        Runnable r = () -> {log.debug("running");};

        Thread t = new Thread(r, "t2");

        t.start();
    }
}
