package com.hypocrite30.pattern;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 两阶段终止模式
 * @Author: Hypocrite30
 * @Date: 2021/7/16 12:13
 */
@Slf4j(topic = "c.TestTwoPhaseTermination")
public class TestTwoPhaseTermination {
    public static void main(String[] args) throws InterruptedException {
        TPTInterrupt t = new TPTInterrupt();
        // TPTVolatile t = new TPTVolatile();
        t.start();

        Thread.sleep(3500);
        log.debug("stop");
        t.stop();
    }
}

@Slf4j(topic = "c.TPTInterrupt")
class TPTInterrupt {
    private Thread thread;

    public void start() {
        thread = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                if (current.isInterrupted()) {
                    log.debug("料理后事");
                    break;
                }
                try {
                    Thread.sleep(1000);
                    log.debug("将结果保存");
                } catch (InterruptedException e) {
                    // 因为上一次的sleep被打断是会重新清除标记的，置为false，所以要再打断一次，下次循环即可“料理后事”
                    current.interrupt();
                }

            }
        }, "监控线程");
        thread.start();
    }

    public void stop() {
        thread.interrupt();
    }
}

@Slf4j(topic = "c.TPTVolatile")
class TPTVolatile {
    private Thread thread;
    private volatile boolean stop = false;

    public void start() {
        thread = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                if (stop) {
                    log.debug("料理后事");
                    break;
                }
                try {
                    Thread.sleep(1000);
                    log.debug("将结果保存");
                } catch (InterruptedException e) {
                }
            }
        }, "监控线程");
        thread.start();
    }

    public void stop() {
        stop = true;
        thread.interrupt();
    }
}


