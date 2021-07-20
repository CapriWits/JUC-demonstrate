package com.hypocrite30._4;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.locks.LockSupport;

/**
 * @Description: 偏向锁
 * @Author: Hypocrite30
 * @Date: 2021/7/19 17:47
 */
// -XX:-UseCompressedOops -XX:-UseCompressedClassPointers -XX:BiasedLockingStartupDelay=0 -XX:+PrintFlagsFinal
//-XX:-UseBiasedLocking tid=0x000000001f173000  -XX:BiasedLockingStartupDelay=0 -XX:+TraceBiasedLocking
@Slf4j(topic = "c.TestBiased")
public class TestBiased {

    /*
    [t1] - 29	00000000 00000000 00000000 00000000 00011111 01000101 01101000 00000101
    [t2] - 29	00000000 00000000 00000000 00000000 00011111 01000101 11000001 00000101
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        test3();
    }

    private static void test5() throws InterruptedException {

        log.debug("begin");
        for (int i = 0; i < 6; i++) {
            Dog d = new Dog();
            log.debug(ClassLayout.parseInstance(d).toPrintable());
            Thread.sleep(1000);
        }
    }

    static Thread t1, t2, t3;
    // 偏向撤销
    private static void test4() throws InterruptedException {
        Vector<Dog> list = new Vector<>();

        int loopNumber = 39; // 40次，偏向撤销，该类对象或新对象都不偏
        t1 = new Thread(() -> {
            for (int i = 0; i < loopNumber; i++) {
                Dog d = new Dog();
                list.add(d);
                synchronized (d) {
                    log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                }
            }
            LockSupport.unpark(t2);
        }, "t1");
        t1.start();

        t2 = new Thread(() -> {
            LockSupport.park();
            log.debug("===============> ");
            for (int i = 0; i < loopNumber; i++) {
                Dog d = list.get(i);
                log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                synchronized (d) {
                    log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                }
                log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
            }
            LockSupport.unpark(t3);
        }, "t2");
        t2.start();

        t3 = new Thread(() -> {
            LockSupport.park();
            log.debug("===============> ");
            for (int i = 0; i < loopNumber; i++) {
                Dog d = list.get(i);
                log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                synchronized (d) {
                    log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                }
                log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
            }
        }, "t3");
        t3.start();

        t3.join();
        log.debug(ClassLayout.parseInstance(new Dog()).toPrintable());
    }

    // 批量重定向，超过20次，会重新将锁偏向
    private static void test3() throws InterruptedException {
        Vector<Dog> list = new Vector<>();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                Dog d = new Dog();
                list.add(d);
                synchronized (d) {
                    log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                }
            }
            synchronized (list) {
                list.notify();
            }
        }, "t1");
        t1.start();

        Thread t2 = new Thread(() -> {
            synchronized (list) {
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("===============> ");
            for (int i = 0; i < 30; i++) {
                Dog d = list.get(i);
                log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                synchronized (d) {
                    log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                }
                log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
            }
        }, "t2");
        t2.start();
    }

    // 测试撤销偏向锁
    private static void test2() throws InterruptedException {
        Dog d = new Dog();
        Thread t1 = new Thread(() -> {
            synchronized (d) {
                log.debug(ClassLayout.parseInstance(d).toPrintable());
            }
            // 运行完，唤醒 t2
            synchronized (TestBiased.class) {
                TestBiased.class.notify();
            }
        }, "t1");
        t1.start();


        Thread t2 = new Thread(() -> {
            // 先让 t1 线程先走
            synchronized (TestBiased.class) {
                try {
                    TestBiased.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 此时偏向于t1,要偏向于t2,无法实现，所以就变成轻量锁
            log.debug(ClassLayout.parseInstance(d).toPrintable());
            synchronized (d) {
                log.debug(ClassLayout.parseInstance(d).toPrintable());
            }
            log.debug(ClassLayout.parseInstance(d).toPrintable());
        }, "t2");
        t2.start();
    }


    // 测试偏向锁
    private static void test1() {
        Dog d = new Dog();
        log.debug(ClassLayout.parseInstance(d).toPrintable());
        // 偏向锁有延迟，先睡一会
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug(ClassLayout.parseInstance(new Dog()).toPrintable());
    }
}

class Dog {

}
