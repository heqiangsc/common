package com.jialing.swift.threadpool;

import java.util.ArrayList;
import java.util.List;

public class ThreadPoolTest {


    public static void main(String[] args) {

        ThreadPool threadPool = ThreadPoolManager.getThreadPool(16);
        List<Runnable> taskList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            taskList.add(new Task());
        }
        threadPool.execute(taskList);
        System.out.println(threadPool);
        threadPool.destory();
        System.out.println(threadPool);

    }

    static class Task implements Runnable {

        private static volatile int i = 1;

        @Override
        public void run() {
            try {
                Thread.currentThread().sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("当前处理的线程是：" + Thread.currentThread().getName() + " 执行任务 " + (i++) + " 完成");
        }
    }
}
