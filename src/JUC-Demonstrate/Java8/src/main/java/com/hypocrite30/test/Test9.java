package com.hypocrite30.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 测试 yield 和 优先级
 * @Author: Hypocrite30
 * @Date: 2021/7/15 18:11
 */
@Slf4j(topic = "c.Test9")
public class Test9 {

    public static void main(String[] args) {
        Runnable task1 = () -> {
            int count = 0;
            for (; ; ) {
                System.out.println("---->1 " + count++);
            }
        };
        Runnable task2 = () -> {
            int count = 0;
            for (; ; ) {
                // Thread.yield(); // 2 主动让，最终运行会比 1 少
                System.out.println("              ---->2 " + count++);
            }
        };
        Thread t1 = new Thread(task1, "t1");
        Thread t2 = new Thread(task2, "t2");
        // t1.setPriority(Thread.MIN_PRIORITY); // 测试线程优先级，
        // t2.setPriority(Thread.MAX_PRIORITY);
        t1.start();
        t2.start();
    }
}
