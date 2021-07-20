package com.hypocrite30._2;

import com.hypocrite30.Constants;
import com.hypocrite30._2.util.FileReader;
import lombok.extern.slf4j.Slf4j;

/**
 * 15:46:29.079 c.Async [main] - do other things ...
 * 15:46:29.079 c.FileReader [Thread-0] - read [841486762_1_0.mp4] start ...
 * 15:46:29.412 c.FileReader [Thread-0] - read [841486762_1_0.mp4] end ... cost: 333 ms
 * @Description: 异步不等待
 * @Author: Hypocrite30
 * @Date: 2021/7/15 15:45
 */
@Slf4j(topic = "c.Async")
public class Async {
    public static void main(String[] args) {
        // 异步方式读取 mp4 文件
        new Thread(() -> FileReader.read(Constants.MP4_FULL_PATH)).start();
        log.debug("do other things ...");
    }
}
