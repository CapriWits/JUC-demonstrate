package com.hypocrite30.test;

import com.hypocrite30.Constants;
import com.hypocrite30._2.util.FileReader;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: Thread.run()只能在当前线程执行，需要用start()来执行
 * @Author: Hypocrite30
 * @Date: 2021/7/15 17:52
 */
@Slf4j(topic = "c.Test4")
public class Test4 {

    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.debug("running...");
                FileReader.read(Constants.MP4_FULL_PATH);
            }
        };

        t1.start();
        log.debug("do other things...");
    }
}
