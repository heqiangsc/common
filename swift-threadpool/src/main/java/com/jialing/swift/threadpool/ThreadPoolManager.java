package com.jialing.swift.threadpool;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class ThreadPoolManager implements ThreadPool {


    private static int workerNum = 5;

    WorkThread[] workThreads;

    private static volatile int executeTaskNumber = 0;

    private BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();

    private static ThreadPoolManager threadPool;

    private AtomicLong threadNum = new AtomicLong();

    private ThreadPoolManager() {
        this(workerNum);
    }

    private ThreadPoolManager(int workerNum2) {
        if (workerNum2 > 0) {
            workerNum = workerNum2;
        }
        workThreads = new WorkThread[workerNum];
        for (int i = 0; i < workerNum; i++) {
            workThreads[i] = new WorkThread();
            workThreads[i].setName("ThreadPool-worker" + threadNum.incrementAndGet());
            System.out.println("初始化线程数:" + (i + 1) + "/" + workerNum + "----当前线程名称是：" + workThreads[i].getName());
            workThreads[i].start();
        }

    }

    @Override
    public String toString() {
        return "当前工作线程数量为:" + workerNum + "  已经完成任务数:" + executeTaskNumber + "  等待任务数:" + getWaitTaskNumber();
    }

    @Override
    public void execute(Runnable task) {
        synchronized (taskQueue) {
            try {
                taskQueue.put(task);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
            taskQueue.notifyAll();
        }
    }

    @Override
    public void execute(Runnable[] tasks) {
        synchronized (taskQueue) {
            for (Runnable task : tasks) {
                try {
                    taskQueue.put(task);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            taskQueue.notifyAll();
        }
    }

    @Override
    public void execute(List<Runnable> tasks) {
        synchronized (taskQueue) {
            for (Runnable task : tasks) {
                try {
                    taskQueue.put(task);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            taskQueue.notifyAll();

        }
    }

    @Override
    public int getExecuteTaskNumber() {
        return executeTaskNumber;
    }

    @Override
    public int getWaitTaskNumber() {
        return taskQueue.size();
    }

    @Override
    public int getWorkThreadNumber() {
        return workerNum;
    }


    public static ThreadPool getThreadPool() {
        return getThreadPool(workerNum);
    }

    public static ThreadPool getThreadPool(int workerNum) {
        if (workerNum <= 0) {
            workerNum = ThreadPoolManager.workerNum;
        }
        if (threadPool == null) {
            threadPool = new ThreadPoolManager(workerNum);
        }
        return threadPool;
    }

    @Override
    public void destory() {
        while (!taskQueue.isEmpty()) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < workerNum; i++) {
            workThreads[i].stopWorker();
            workThreads[i] = null;
        }
        threadPool = null;
        taskQueue.clear();

    }


    private class WorkThread extends Thread {

        private boolean isRunning = true;

        @Override
        public void run() {
            Runnable r = null;
            while (isRunning) {
                synchronized (taskQueue) {
                    while (isRunning && taskQueue.isEmpty()) {
                        try {
                            taskQueue.wait(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                    if (!taskQueue.isEmpty()) {
                        try {
                            r = taskQueue.take();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (null != r) {
                        r.run();
                    }
                    executeTaskNumber++;
                    r = null;
                }
            }
        }

        public void stopWorker() {
            isRunning = false;
        }
    }
}
