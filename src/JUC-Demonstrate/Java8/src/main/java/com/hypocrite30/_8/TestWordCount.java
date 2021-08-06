package com.hypocrite30._8;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 案例：26个英文字母，每个字母200个，分别输出到26个文件中
 * 再数其中的字母个数是否为200，证明多线程下的正确性
 * @Description: 测试 ConcurrentHashMap
 * @Author: Hypocrite30
 * @Date: 2021/8/5 20:44
 */
public class TestWordCount {
    public static void main(String[] args) {
        demo(
                // 创建 map 集合
                // 创建 ConcurrentHashMap 对不对？
                () -> new ConcurrentHashMap<String, LongAdder>(8, 0.75f, 8),

                (map, words) -> {
                    for (String word : words) {

                        // 如果缺少一个 key，则计算生成一个 value , 然后将  key value 放入 map，否则什么都不做
                        //                  a      0
                        LongAdder value = map.computeIfAbsent(word, (key) -> new LongAdder());
                        // 执行累加
                        value.increment(); // 2

                        // 以下操作虽然每一个操作都有锁保护，但是放在一起就不具有原子性
                        /*// 检查 key 有没有
                        Integer counter = map.get(word);
                        int newValue = counter == null ? 1 : counter + 1;
                        // 没有 则 put
                        map.put(word, newValue);*/
                    }
                }
        );
    }


    private static void demo2() {

        Map<String, Integer> collect = IntStream.range(1, 27).parallel()
                .mapToObj(idx -> readFromFile(idx))
                .flatMap(list -> list.stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(w -> 1)));
        System.out.println(collect);
    }

    private static <V> void demo(Supplier<Map<String, V>> supplier, BiConsumer<Map<String, V>, List<String>> consumer) {
        Map<String, V> counterMap = supplier.get();
        // key value
        // a   200
        // b   200
        List<Thread> ts = new ArrayList<>();
        for (int i = 1; i <= 26; i++) {
            int idx = i;
            Thread thread = new Thread(() -> {
                List<String> words = readFromFile(idx);
                consumer.accept(counterMap, words);
            });
            ts.add(thread);
        }

        ts.forEach(t -> t.start());
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(counterMap);
    }

    public static List<String> readFromFile(int i) {
        ArrayList<String> words = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("tmp/" + i + ".txt")))) {
            while (true) {
                String word = in.readLine();
                if (word == null) {
                    break;
                }
                words.add(word);
            }
            return words;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
