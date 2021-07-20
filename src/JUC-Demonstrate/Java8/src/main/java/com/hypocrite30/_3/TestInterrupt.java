package com.hypocrite30._3;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

import static com.hypocrite30._2.util.Sleeper.sleep;

/**
 * @Description: Interrupt 用法
 * @Author: Hypocrite30
 * @Date: 2021/7/17 12:56
 */
@Slf4j(topic = "c.TestInterrupt")
public class TestInterrupt {
    public static void main(String[] args) throws InterruptedException {
        test4();
    }

    private static void test4() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                log.debug("park...");
                LockSupport.park(); // 标记为 false 才会阻塞，详细见 Test14
                log.debug("打断状态：{}", Thread.interrupted()); // 会清空标记，下一次循环 park 能继续阻塞
            }
        });
        t1.start();

        sleep(1);
        t1.interrupt();
    }

    private static void test3() {
        Thread t1 = new Thread(() -> {
            log.debug("park...");
            LockSupport.park();
            log.debug("unpark...");
            log.debug("打断状态：{}", Thread.currentThread().isInterrupted()); // true
        }, "t1");
        t1.start();


        sleep(0.5);
        t1.interrupt(); // 打断 park
    }

    private static void test2() throws InterruptedException {
        Thread t2 = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                boolean interrupted = current.isInterrupted();
                if (interrupted) {
                    log.debug(" 打断状态: {}", interrupted);
                    break;
                }
            }
        }, "t2");
        t2.start();

        sleep(0.5);
        t2.interrupt(); // 正常运行被打断，标记不会情况，为 true
    }

    private static void test1() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            sleep(1);
        }, "t1");
        t1.start();

        sleep(0.5); // sleep wait join被interrupt打断会重置标记，为 false
        t1.interrupt();
        log.debug(" 打断状态: {}", t1.isInterrupted()); // false
    }
}
