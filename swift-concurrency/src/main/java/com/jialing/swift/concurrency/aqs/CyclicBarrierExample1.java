package com.jialing.swift.concurrency.aqs;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class CyclicBarrierExample1 {

    private static final int request = 10;


    static final CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < request; i++) {
            //Thread.sleep(1000);
            final  int threadNum = i;
            service.execute(() -> {
                try {
                    race(threadNum);
                } catch (Exception e) {
                    System.out.println("excepiton: " + e.getMessage());
                }
            });
        }
        service.shutdown();
    }

    public static void race(int threadNum) throws Exception{
        Thread.sleep(1000);
        System.out.println(threadNum + " is ready!");
        cyclicBarrier.await();
        System.out.println(threadNum + " continue");
    }
}
