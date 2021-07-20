package com.hypocrite30.test;

import lombok.extern.slf4j.Slf4j;

import static com.hypocrite30._2.util.Sleeper.sleep;

/**
 * @Description: 测试 Join
 * @Author: Hypocrite30
 * @Date: 2021/7/15 18:18
 */
@Slf4j(topic = "c.Test10")
public class Test10 {
    static int r = 0;
    public static void main(String[] args) throws InterruptedException {
        test1();
    }
    private static void test1() throws InterruptedException {
        log.debug("开始");
        Thread t1 = new Thread(() -> {
            log.debug("开始");
            sleep(1);
            log.debug("结束");
            r = 10;
        },"t1");
        t1.start();
        t1.join();
        log.debug("结果为:{}", r);
        log.debug("结束");
    }
}
