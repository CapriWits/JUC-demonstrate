package com.hypocrite30._7;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * @Description: 可变类 在没有线程保护下，多线程操作引发的并发问题，需要加锁保护
 * @Author: Hypocrite30
 * @Date: 2021/7/28 16:34
 */
@Slf4j(topic = "c.Test1")
public class Test1 {
    public static void main(String[] args) {
        // DateTimeFormatter 是不可变对象
        DateTimeFormatter stf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                TemporalAccessor parse = stf.parse("1951-04-21");
                log.debug("{}", parse);
            }).start();
        }
    }

    private static void test() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                synchronized (sdf) {
                    try {
                        log.debug("{}", sdf.parse("1951-04-21"));
                    } catch (Exception e) {
                        log.error("{}", e);
                    }
                }
            }).start();
        }
    }
}

