package com.hypocrite30.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 其他线程用 interrupt 打断正在睡眠的线程
 * @Author: Hypocrite30
 * @Date: 2021/7/15 17:58
 */
@Slf4j(topic = "c.Test7")
public class Test7 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.debug("enter sleep...");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    log.debug("wake up...");
                    e.printStackTrace();
                }
            }
        };
        t1.start();

        Thread.sleep(1000);
        log.debug("interrupt...");
        t1.interrupt(); // 主线程把 t1 睡眠打断
    }
}
