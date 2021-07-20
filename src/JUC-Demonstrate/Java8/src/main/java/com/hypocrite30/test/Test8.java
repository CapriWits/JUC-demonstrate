package com.hypocrite30.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Description: TimeUnit 让时间可读性更高
 * @Author: Hypocrite30
 * @Date: 2021/7/15 18:05
 */
@Slf4j(topic = "c.Test8")
public class Test8 {

    public static void main(String[] args) throws InterruptedException {
        log.debug("enter");
        TimeUnit.SECONDS.sleep(1);
        log.debug("end");
        // Thread.sleep(1000);
    }
}

