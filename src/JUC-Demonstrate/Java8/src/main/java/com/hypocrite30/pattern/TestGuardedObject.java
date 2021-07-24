package com.hypocrite30.pattern;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

import static com.hypocrite30.pattern.Downloader.download;

/**
 * @Description: 同步模式之保护性暂停
 * @Author: Hypocrite30
 * @Date: 2021/7/24 12:03
 */
@Slf4j(topic = "c.TestGuardedObject")
public class TestGuardedObject {
    public static void main(String[] args) {
        GuardedObject guardedObject = new GuardedObject();
        new Thread(() -> {
            try {
                List<String> response = download();
                log.debug("download complete...");
                guardedObject.complete(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        log.debug("waiting...");
        Object response = guardedObject.get();
        log.debug("get response: [{}] lines", ((List<String>) response).size());
    }
}

class GuardedObject {

    private Object response;
    private final Object lock = new Object();

    /**
     * 获得response
     */
    public Object get() {
        synchronized (lock) {
            // 条件不满足则等待
            while (response == null) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return response;
        }
    }

    /**
     * 下载
     */
    public void complete(Object response) {
        synchronized (lock) {
            // 条件满足，通知等待线程
            this.response = response;
            lock.notifyAll();
        }
    }
}