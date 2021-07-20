package com.hypocrite30._2.util;

import java.util.concurrent.TimeUnit;

/**
 * @Description: 睡眠工具类
 * @Author: Hypocrite30
 * @Date: 2021/7/15 15:37
 */
public class Sleeper {
    /**
     * 控制线程睡眠时间
     * @param i 睡眠秒数
     */
    public static void sleep(int i) {
        try {
            TimeUnit.SECONDS.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 控制线程睡眠时间
     * @param i 睡眠秒数
     */
    public static void sleep(double i) {
        try {
            TimeUnit.MILLISECONDS.sleep((int) (i * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
