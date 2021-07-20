package com.hypocrite30._3;

import lombok.extern.slf4j.Slf4j;

import static com.hypocrite30._2.util.Sleeper.sleep;

/**
 * @Description: 守护线程：所有非守护线程结束时 结束
 * @Author: Hypocrite30
 * @Date: 2021/7/17 12:52
 */
@Slf4j(topic = "c.TestDaemon")
public class TestDaemon {
    public static void main(String[] args) {
        log.debug("开始运行...");
        Thread t1 = new Thread(() -> {
            log.debug("开始运行...");
            sleep(2);
            log.debug("运行结束...");
        }, "daemon");
        // 设置该线程为守护线程
        t1.setDaemon(true);
        t1.start();

        sleep(1);
        log.debug("运行结束...");
    }
}
