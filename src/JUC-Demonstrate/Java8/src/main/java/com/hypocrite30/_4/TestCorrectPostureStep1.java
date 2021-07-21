package com.hypocrite30._4;

import lombok.extern.slf4j.Slf4j;

import static com.hypocrite30._2.util.Sleeper.sleep;

@Slf4j(topic = "c.TestCorrectPosture")
/**
 * 其它干活的线程，都要一直阻塞，效率太低
 * 小南线程必须睡足 2s 后才能醒来，就算烟提前送到，也无法立刻醒来
 * 加了 synchronized (room) 后，就好比小南在里面反锁了门睡觉，烟根本没法送进门，main 没加
 * synchronized 就好像 main 线程是翻窗户进来的
 * 解决方法，使用 wait - notify 机制
 * @Description: 用sleep来模拟送烟问题
 * @Author: Hypocrite30
 * @Date: 2021/7/21 16:26
 */
public class TestCorrectPostureStep1 {
    static final Object room = new Object();
    static boolean hasCigarette = false; // 有没有烟
    static boolean hasTakeout = false;

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (room) {
                log.debug("有烟没？[{}]", hasCigarette);
                if (!hasCigarette) {
                    log.debug("没烟，先歇会！");
                    sleep(2); // 先睡2s，等烟
                }
                log.debug("有烟没？[{}]", hasCigarette);
                if (hasCigarette) {
                    log.debug("可以开始干活了");
                }
            }
        }, "小南").start();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                synchronized (room) {
                    log.debug("可以开始干活了");
                }
            }, "其它人").start();
        }

        sleep(1);
        new Thread(() -> {
            // 这里能不能加 synchronized (room)？ 不能锁住，相当于锁门进入休息室，送烟人进不来
            // synchronized (room) {
                hasCigarette = true;
                log.debug("烟到了噢！");
            // }
        }, "送烟的").start();
    }

}
