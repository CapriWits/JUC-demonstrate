package com.hypocrite30._3;

import com.hypocrite30.Constants;
import com.hypocrite30._2.util.FileReader;

/**
 * @Description: RUNNABLE状态包括：可执行状态，执行状态，阻塞状态
 * @Author: Hypocrite30
 * @Date: 2021/7/16 16:10
 */
public class TestState2 {
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            FileReader.read(Constants.MP4_FULL_PATH);
            FileReader.read(Constants.MP4_FULL_PATH);
            FileReader.read(Constants.MP4_FULL_PATH);
        }, "t1").start();

        Thread.sleep(1000);
        System.out.println("ok");
    }
}
