package com.hypocrite30._8;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

import static com.hypocrite30._2.util.Sleeper.sleep;

/**
 * @Description: 任务调度线程池 ScheduledExecutorService
 * @Author: Hypocrite30
 * @Date: 2021/8/1 12:06
 */
@Slf4j(topic = "c.TestTimer")
public class TestTimer {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        pool.schedule(() -> {
            try { // 手动 catch 异常
                log.debug("task1");
                int i = 1 / 0;
            } catch (Exception e) {
                log.error("error:", e); // 异常加入到日志中
            }
        }, 1, TimeUnit.SECONDS);*/

        ExecutorService pool = Executors.newFixedThreadPool(1);
        pool.submit(() -> {
            try {
                log.debug("task1");
                int i = 1 / 0; // 手动 catch 异常
            } catch (Exception e) {
                log.error("error:", e); // 异常加入到日志中
            }
        });
    }

    /**
     * scheduleWithFixedDelay 是真正的间隔时间，线程执行完，再过间隔时间才会运行下一个任务
     */
    private static void method4() {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        log.debug("start...");
        pool.scheduleWithFixedDelay(()-> {
            log.debug("running...");
            sleep(2);
        }, 1, 1, TimeUnit.SECONDS);
    }

    /**
     * scheduleAtFixedRate 以固定的间隔时间执行任务
     * 如果运行时间 > 间隔时间，则线程间的间隔时间就会被「撑」大
     */
    private static void method3() {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        log.debug("start...");
        pool.scheduleAtFixedRate(() -> {
            log.debug("running...");
            sleep(2); // 运行 2s > 1s
        }, 1, 1, TimeUnit.SECONDS);
    }

    /**
     * ScheduledExecutorService::schedule 改写
     */
    private static void method2(ScheduledExecutorService pool) {
        pool.schedule(() -> {
            log.debug("task1");
            int i = 1 / 0; // 有异常不会报出来
        }, 1, TimeUnit.SECONDS);

        pool.schedule(() -> {
            log.debug("task2");
        }, 1, TimeUnit.SECONDS);
    }

    /**
     * Timer 串行执行，同一时间只能有一个任务在执行，前一个
     * 任务的延迟或异常都将会影响到之后的任务
     */
    private static void method1() {
        Timer timer = new Timer();
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task 1");
                sleep(2);
            }
        };
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task 2");
            }
        };

        log.debug("start...");
        timer.schedule(task1, 1000);
        timer.schedule(task2, 1000);
    }
}
