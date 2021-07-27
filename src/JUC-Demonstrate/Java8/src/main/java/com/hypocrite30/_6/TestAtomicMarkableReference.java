package com.hypocrite30._6;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicMarkableReference;

import static com.hypocrite30._2.util.Sleeper.sleep;

/**
 * @Description: 测试 AtomicMarkableReference，相当于简化版的 AtomicStampedReference，用布尔变量代替版本号
 * @Author: Hypocrite30
 * @Date: 2021/7/27 20:43
 */
@Slf4j(topic = "c.TestAtomicMarkableReference")
public class TestAtomicMarkableReference {
    public static void main(String[] args) throws InterruptedException {
        GarbageBag bag = new GarbageBag("装满了垃圾");
        // 参数2 mark 可以看作一个标记，表示垃圾袋满了
        AtomicMarkableReference<GarbageBag> ref = new AtomicMarkableReference<>(bag, true);

        log.debug("start...");
        GarbageBag prev = ref.getReference();
        log.debug(prev.toString());

        // 用来模拟保洁阿姨已经替换掉垃圾袋
        /*new Thread(() -> {
            log.debug("start...");
            bag.setDesc("空垃圾袋");
            ref.compareAndSet(bag, bag, true, false);
            log.debug(bag.toString());
        }, "保洁阿姨").start();*/

        sleep(1);
        log.debug("想换一只新垃圾袋？");
        boolean success = ref.compareAndSet(prev, new GarbageBag("空垃圾袋"), true, false);
        log.debug("换了么？" + success);
        log.debug(ref.getReference().toString());
    }
}

class GarbageBag {
    String desc;

    public GarbageBag(String desc) {
        this.desc = desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return super.toString() + " " + desc;
    }
}