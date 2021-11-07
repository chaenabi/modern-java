package com.company;

public class GarbageMain {
    public static void main(String[] args) {
        int[] anInt = new int[1];
        anInt[0] = 42;
        Runnable runnable = () -> {
          anInt[0]++;
            System.out.println("Changed: "+anInt[0]);
        };
        new Thread(runnable).start();
    }
}
