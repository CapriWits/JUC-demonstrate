package com.hypocrite30._8;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 测试 ThreadPoolExecutor 的两种工厂方法创建线程池
 * @Author: Hypocrite30
 * @Date: 2021/7/31 12:18
 */
@Slf4j(topic = "c.TestExecutors")
public class TestExecutors {
    public static void main(String[] args) throws InterruptedException {
        test2();
    }

    /**
     * newSingleThreadExecutor
     */
    public static void test2() {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        pool.execute(() -> {
            log.debug("1");
            int i = 1 / 0; // 出现异常了，后面的任务也有线程来执行
        });

        pool.execute(() -> {
            log.debug("2");
        });

        pool.execute(() -> {
            log.debug("3");
        });
    }

    /**
     * newFixedThreadPool
     */
    private static void test1() {
        // ThreadFactory 方便取名
        ExecutorService pool = Executors.newFixedThreadPool(2, new ThreadFactory() {
            private AtomicInteger t = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "mypool_t" + t.getAndIncrement());
            }
        });

        pool.execute(() -> {
            log.debug("1");
        });

        pool.execute(() -> {
            log.debug("2");
        });

        pool.execute(() -> {
            log.debug("3");
        });
    }
}
