package com.hypocrite30._8;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.SynchronousQueue;

import static com.hypocrite30._2.util.Sleeper.sleep;

/**
 * SynchronousQueue 配合 newCachedThreadPool 使用，适用于「任务数比较密集，但每个任务执行时间较短的情况」
 * @Description: SynchronousQueue 没有容量，只有线程取走任务才能放进去，否则一直阻塞住
 * @Author: Hypocrite30
 * @Date: 2021/7/31 12:24
 */
@Slf4j(topic = "c.TestSynchronousQueue")
public class TestSynchronousQueue {
    public static void main(String[] args) {
        SynchronousQueue<Integer> integers = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                log.debug("putting {} ", 1);
                integers.put(1);
                log.debug("{} putted...", 1);

                log.debug("putting...{} ", 2);
                integers.put(2);
                log.debug("{} putted...", 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1").start();

        sleep(1);

        new Thread(() -> {
            try {
                log.debug("taking {}", 1);
                integers.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t2").start();

        sleep(1);

        new Thread(() -> {
            try {
                log.debug("taking {}", 2);
                integers.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t3").start();
    }
}
