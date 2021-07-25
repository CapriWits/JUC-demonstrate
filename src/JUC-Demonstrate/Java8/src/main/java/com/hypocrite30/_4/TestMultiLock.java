package com.hypocrite30._4;

import com.hypocrite30._2.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 用多把锁将不相关的事关联起来，增强并发度
 * @Author: Hypocrite30
 * @Date: 2021/7/25 12:15
 */
public class TestMultiLock {
    public static void main(String[] args) {
        BigRoom bigRoom = new BigRoom();
        new Thread(() -> {
            bigRoom.study();
        }, "小南").start();
        new Thread(() -> {
            bigRoom.sleep();
        }, "小女").start();
    }
}

@Slf4j(topic = "c.BigRoom")
class BigRoom {

    private final Object studyRoom = new Object();

    private final Object bedRoom = new Object();

    public void sleep() {
        synchronized (bedRoom) {
            log.debug("sleeping 2 小时");
            Sleeper.sleep(2);
        }
    }

    public void study() {
        synchronized (studyRoom) {
            log.debug("study 1 小时");
            Sleeper.sleep(1);
        }
    }

}
