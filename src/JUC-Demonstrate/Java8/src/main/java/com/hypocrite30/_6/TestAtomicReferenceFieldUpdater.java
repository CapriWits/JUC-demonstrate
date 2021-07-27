package com.hypocrite30._6;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @Description: 测试 AtomicReferenceFieldUpdater，对字段原子性修改
 * @Author: Hypocrite30
 * @Date: 2021/7/27 21:24
 */
@Slf4j(topic = "c.Test40")
public class TestAtomicReferenceFieldUpdater {
    public static void main(String[] args) {
        Student stu = new Student();

        AtomicReferenceFieldUpdater updater =
                AtomicReferenceFieldUpdater.newUpdater(Student.class, String.class, "name");

        System.out.println(updater.compareAndSet(stu, null, "张三"));
        System.out.println(stu);
    }
}

class Student {
    volatile String name;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}