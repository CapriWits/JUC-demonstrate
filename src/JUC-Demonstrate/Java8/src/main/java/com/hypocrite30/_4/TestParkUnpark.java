package com.hypocrite30._4;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

import static com.hypocrite30._2.util.Sleeper.sleep;

/**
 * @Description: 测试 park & umpark
 * @Author: Hypocrite30
 * @Date: 2021/7/25 11:23
 */
@Slf4j(topic = "c.TestParkUnpark")
public class TestParkUnpark {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("start...");
            sleep(2);
            log.debug("park...");
            LockSupport.park();
            log.debug("resume...");
        }, "t1");
        t1.start();

        sleep(1);
        log.debug("unpark...");
        LockSupport.unpark(t1);
    }
}
