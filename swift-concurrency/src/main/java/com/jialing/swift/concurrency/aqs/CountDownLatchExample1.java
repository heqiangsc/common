package com.jialing.swift.concurrency.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CountDownLatchExample1 {


    private static final int request = 5000;


    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(request);
        for (int i = 0; i < request; i++) {
            service.execute(() -> {
                try {
                    add();
                } catch (Exception e) {
                    System.out.println("excepiton: " + e.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        //countDownLatch.await(10, TimeUnit.MILLISECONDS);
        countDownLatch.await();
        service.shutdown();
        System.out.println("count:" + count.get());
    }

    public static void add() {
        count.incrementAndGet();
    }
}
