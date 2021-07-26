package com.hypocrite30.pattern.SequentialControl;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @Description: 同步控制 固定运行顺序 park-unpark 版
 * @Author: Hypocrite30
 * @Date: 2021/7/26 16:09
 */
@Slf4j(topic = "c.v1_2")
public class v1_2 {
    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            LockSupport.park();
            log.debug("1");
        }, "t1");
        t1.start();

        new Thread(() -> {
            log.debug("2");
            LockSupport.unpark(t1);
        },"t2").start();
    }
}
