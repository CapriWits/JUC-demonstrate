package com.hypocrite30._4;

import lombok.extern.slf4j.Slf4j;

import static com.hypocrite30._2.util.Sleeper.sleep;

@Slf4j(topic = "c.TestWaitNotify")
/**
 * @Description: t1 t2线程先执行，然后wait，主线程选择唤醒
 * @Author: Hypocrite30
 * @Date: 2021/7/21 16:03
 */
public class TestWaitNotify {
    final static Object obj = new Object();

    public static void main(String[] args) {

        new Thread(() -> {
            synchronized (obj) {
                log.debug("执行....");
                try {
                    obj.wait(); // 让线程在obj上一直等待下去
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("其它代码....");
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (obj) {
                log.debug("执行....");
                try {
                    obj.wait(); // 让线程在obj上一直等待下去
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("其它代码....");
            }
        }, "t2").start();

        // 主线程两秒后执行
        sleep(2);
        log.debug("唤醒 obj 上其它线程");
        synchronized (obj) {
            obj.notify(); // 唤醒obj上随机一个线程
            // obj.notifyAll(); // 唤醒obj上所有等待线程
        }
    }
}
