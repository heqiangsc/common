package com.jialing.swift.concurrency.aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class SemaphoreExample3 {

    private static final int threadPool = 20;
    private static final int request = 5000;


    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadPool);
        for (int i = 0; i < request; i++) {
            service.execute(() -> {
                try {
                    if (semaphore.tryAcquire()) {
                        add();
                        semaphore.release();
                    }

                } catch (Exception e) {
                    System.out.println("excepiton: " + e.getMessage());
                }
            });
        }
        service.shutdown();
    }

    public static void add() {
        count.incrementAndGet();
    }
}
