package com.jialing.swift.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadExample1 {

    private static final int threadPool = 20;
    private static final int request = 5000;


    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newCachedThreadPool();
        CountDownLatch countDownLatch = new CountDownLatch(request);
        Semaphore semaphore = new Semaphore(threadPool);
        for (int i = 0; i < request; i++) {
            service.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                    countDownLatch.countDown();
                } catch (Exception e) {
                    System.out.println("excepiton: " + e.getMessage());
                }
            });
        }
        countDownLatch.await();
        service.shutdown();
        System.out.println("count:" + count.get());
    }

    public static void add() {
        count.incrementAndGet();
    }
}
