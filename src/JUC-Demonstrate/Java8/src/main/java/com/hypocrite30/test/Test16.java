package com.hypocrite30.test;

import lombok.extern.slf4j.Slf4j;

import static com.hypocrite30._2.util.Sleeper.sleep;

/**
 * @Description: 烧水泡茶样例 1
 * @Author: Hypocrite30
 * @Date: 2021/7/16 16:26
 */
@Slf4j(topic = "c.Test16")
public class Test16 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("洗水壶");
            sleep(1);
            log.debug("烧开水");
            sleep(5);
        },"老王");

        Thread t2 = new Thread(() -> {
            log.debug("洗茶壶");
            sleep(1);
            log.debug("洗茶杯");
            sleep(2);
            log.debug("拿茶叶");
            sleep(1);
            try {
                t1.join(); // 等老王把水烧开
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("泡茶");
        },"小王");

        t1.start();
        t2.start();
    }
}
