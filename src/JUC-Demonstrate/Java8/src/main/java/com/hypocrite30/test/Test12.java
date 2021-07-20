package com.hypocrite30.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: interrupt打断（通知），该线程要自己决定是否自己停下来
 * @Author: Hypocrite30
 * @Date: 2021/7/16 11:36
 */
@Slf4j(topic = "c.Test12")
public class Test12 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while(true) {
                boolean interrupted = Thread.currentThread().isInterrupted();
                if(interrupted) {
                    log.debug("被打断了, 退出循环");
                    break;
                }
            }
        }, "t1");
        t1.start();

        Thread.sleep(1000);
        log.debug("interrupt");
        t1.interrupt();
    }
}
