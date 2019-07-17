package com.jialing.swift.concurrency.aqs;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierExample2 {

    private static final int request = 10;


    static final CyclicBarrier cyclicBarrier = new CyclicBarrier(5, ()->{
        System.out.println("callback is running");
    });
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
