package com.company;

import java.util.concurrent.TimeUnit;

public final class ConcurrentProblem {
    private int i = 0;

    public void inc() {
        System.out.println((i = i + 1));
    }

}

final class CounterExample implements Runnable {
    private final ConcurrentProblem cp;

    public CounterExample(ConcurrentProblem cp) {
        this.cp = cp;
    }

    @Override
    public void run() {
       // System.out.println(Runtime.getRuntime().availableProcessors());
        for(int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName());
            cp.inc();
        }
    }
}

final class Counter {
    public static void main(String[] args) {
        multiThreadTest();
    }

    private static void multiThreadTest() {
        Runnable r1 = new CounterExample(new ConcurrentProblem());
        int threadCount = 4;
        Thread[] thread = new Thread[4];
        for(int i = 0; i < 4; i++) {
            thread[i] = new Thread(r1);
            //slowDown();
            thread[i].start();
        }
    }

    private static void slowDown() {
        try {
            TimeUnit.SECONDS.sleep(3L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}