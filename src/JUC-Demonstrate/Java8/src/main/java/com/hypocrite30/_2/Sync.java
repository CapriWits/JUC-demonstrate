package com.hypocrite30._2;

import com.hypocrite30.Constants;
import com.hypocrite30._2.util.FileReader;
import lombok.extern.slf4j.Slf4j;

/**
 * 15:47:07.770 c.FileReader [main] - read [841486762_1_0.mp4] start ...
 * 15:47:08.122 c.FileReader [main] - read [841486762_1_0.mp4] end ... cost: 354 ms
 * 15:47:08.123 c.Sync [main] - do other things ...
 * @Description: 同步等待
 * @Author: Hypocrite30
 * @Date: 2021/7/15 15:34
 */
@Slf4j(topic = "c.Sync")
public class Sync {
    public static void main(String[] args) {
        // 同步方式读取 mp4 文件
        FileReader.read(Constants.MP4_FULL_PATH);
        log.debug("do other things ...");
    }
}

