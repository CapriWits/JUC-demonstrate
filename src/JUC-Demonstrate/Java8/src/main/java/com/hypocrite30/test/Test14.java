package com.hypocrite30.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

import static com.hypocrite30._2.util.Sleeper.sleep;

/**
 * Thread.interrupted() 会清除打断状态，置为 false
 * LockSupport.park() 只能在标记是 false 时生效，所以用 Thread.interrupted() 可以让 park() 再次运行
 * @Description: 测试 park()
 * @Author: Hypocrite30
 * @Date: 2021/7/16 12:32
 */
@Slf4j(topic = "c.Test14")
public class Test14 {

    private static void test4() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                log.debug("park...");
                LockSupport.park(); // 阻塞，然后被打断
                // log.debug("打断状态：{}", Thread.interrupted()); // Thread.interrupted()会清除打断状态
                log.debug("打断状态：{}", Thread.currentThread().isInterrupted()); // Thread.interrupted()会清除打断状态

                LockSupport.park(); // 标记是 false 才能阻塞
                log.debug("unpark...");
            }
        });
        t1.start();

        sleep(1);
        t1.interrupt();
    }

    private static void test3() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("park...");
            LockSupport.park();
            log.debug("unpark...");
            log.debug("打断状态：{}", Thread.currentThread().isInterrupted());
        }, "t1");
        t1.start();

        sleep(1);
        t1.interrupt();
    }

    public static void main(String[] args) throws InterruptedException {
        // test3();
        test4();
    }
}
