package com.jialing.swift.concurrency.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;

public class LockExample2 {

    private static final int threadPool = 20;
    private static final int request = 5000;


    private static Integer count = 0;

    private final static StampedLock lock = new StampedLock();

    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(request);
        final Semaphore semaphore = new Semaphore(threadPool);
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
        System.out.println("count:" + count);
    }

    public static void add() {
        long stamped = lock.writeLock();
        count++;
        lock.unlock(stamped);
    }
}
