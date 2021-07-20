package com.hypocrite30._4.exercise;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * @Description: 售票问题，线程不安全
 * @Author: Hypocrite30
 * @Date: 2021/7/17 18:27
 */
@Slf4j(topic = "c.ExerciseSell")
public class ExerciseSell {
    public static void main(String[] args) throws InterruptedException {
        // 模拟多人买票
        TicketWindow window = new TicketWindow(1000);
        // 所有线程的集合
        List<Thread> threadList = new ArrayList<>(); // 把每个线程加到集合里，为了等所有线程任务结束再统计结果
        // 卖出的票数统计
        List<Integer> amountList = new Vector<>();
        for (int i = 0; i < 2000; i++) {  // 2000个人
            Thread thread = new Thread(() -> {
                // 买票
                int amount = window.sell(random(5)); // 每个人买 1-5 张
                try {
                    Thread.sleep(random(5)); // 每个线程随机睡一段时间，交错运行方便显示出不安全结果
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 统计买票数
                amountList.add(amount);
            });
            threadList.add(thread);
            thread.start();
        }

        for (Thread thread : threadList) {
            thread.join(); // 让所有线程任务结束再统计结果
        }

        // 统计卖出的票数和剩余票数
        log.debug("余票：{}", window.getCount());
        log.debug("卖出的票数：{}", amountList.stream().mapToInt(i -> i).sum()); // 观察余票+卖出去的票 == 总票 ?
    }

    // Random 为线程安全
    static Random random = new Random();

    // 随机 1~5
    public static int random(int amount) {
        return random.nextInt(amount) + 1;
    }
}

// 售票窗口
class TicketWindow {
    private int count; // 票数

    public TicketWindow(int count) {
        this.count = count;
    }

    // 获取余票数量
    public int getCount() {
        return count;
    }

    // 售票
    public /* synchronized */ int sell(int amount) { // 同步之后，就不会出现线程安全问题
        if (this.count >= amount) {
            this.count -= amount;
            return amount;
        } else {
            return 0;
        }
    }
}