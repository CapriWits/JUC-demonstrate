package com.hypocrite30._3;

import lombok.extern.slf4j.Slf4j;

import static com.hypocrite30._2.util.Sleeper.sleep;

/**
 * @Description:
 * @Author: Hypocrite30
 * @Date: 2021/7/15 18:26
 */
@Slf4j(topic = "c.TestJoin")
public class TestJoin {
    static int r = 0;
    static int r1 = 0;
    static int r2 = 0;

    public static void main(String[] args) throws InterruptedException {
        test3();
    }

    /**
     * 等 1.5 秒，就不等了
     */
    public static void test3() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            sleep(2);
            r1 = 10;
        });

        long start = System.currentTimeMillis();
        t1.start();

        // 线程执行结束会导致 join 结束
        log.debug("join begin");
        t1.join(1500);
        long end = System.currentTimeMillis();
        log.debug("r1: {} r2: {} cost: {}", r1, r2, end - start);
    }

    private static void test2() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            sleep(1);
            r1 = 10;
        });
        Thread t2 = new Thread(() -> {
            sleep(2);
            r2 = 20;
        });
        t1.start();
        t2.start();
        long start = System.currentTimeMillis();
        log.debug("join begin");
        t1.join(); // 在 t1 阻塞时，t2 已经启动了
        log.debug("t1 join end");
        t2.join();
        log.debug("t2 join end");
        long end = System.currentTimeMillis(); // 耗时 2s
        log.debug("r1: {} r2: {} cost: {}", r1, r2, end - start);
    }

    /**
     * t1.join(); 会让 t1 运行完后再继续，r = 10
     */
    private static void test1() throws InterruptedException {
        log.debug("开始");
        Thread t1 = new Thread(() -> {
            log.debug("开始");
            sleep(1);
            log.debug("结束");
            r = 10;
        });
        t1.start();
        t1.join();
        log.debug("结果为:{}", r);
        log.debug("结束");
    }
}
