package com.hypocrite30._8;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.StampedLock;

import static com.hypocrite30._2.util.Sleeper.sleep;

/**
 * 读读可并发，读写不行，但会在读的时候尝试进行乐观读
 * @Description: 测试 StampedLock，乐观读 可以提高读的性能
 * @Author: Hypocrite30
 * @Date: 2021/8/4 17:19
 */
@Slf4j(topic = "c.TestStampedLock")
public class TestStampedLock {
    public static void main(String[] args) {
        DataContainerStamped dataContainer = new DataContainerStamped(1);
        new Thread(() -> {
            dataContainer.read(1);
        }, "t1").start();
        sleep(0.5);
        // 测试 读读
        /*new Thread(() -> {
            dataContainer.read(0);
        }, "t2").start();*/
        // 测试读写
        new Thread(() -> {
            dataContainer.write(100);
        }, "t2").start();
    }
}

@Slf4j(topic = "c.DataContainerStamped")
class DataContainerStamped {
    private int data;
    private final StampedLock lock = new StampedLock();

    public DataContainerStamped(int data) {
        this.data = data;
    }

    public int read(int readTime) {
        // 这里先进行乐观读
        long stamp = lock.tryOptimisticRead();
        log.debug("optimistic read locking...{}", stamp);
        sleep(readTime);
        // 验戳，读过程中，如果有线程获取写锁，会改变戳，则要将乐观读升级为读锁
        if (lock.validate(stamp)) {
            log.debug("read finish...{}, data:{}", stamp, data);
            return data;
        }
        // 锁升级 - 读锁
        log.debug("updating to read lock... {}", stamp);
        try {
            // 此时 写锁 还没释放，写过程还在进行，所以会在这里阻塞住，直到写过程完成
            stamp = lock.readLock();
            log.debug("read lock {}", stamp);
            sleep(readTime);
            log.debug("read finish...{}, data:{}", stamp, data);
            return data;
        } finally {
            log.debug("read unlock {}", stamp);
            lock.unlockRead(stamp);
        }
    }

    public void write(int newData) {
        long stamp = lock.writeLock();
        log.debug("write lock {}", stamp);
        try {
            sleep(2);
            this.data = newData;
        } finally {
            log.debug("write unlock {}", stamp);
            lock.unlockWrite(stamp);
        }
    }
}
