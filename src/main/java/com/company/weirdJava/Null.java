package com.company.weirdJava;

public class Null {
    static int number = 42;

    Null getNull() {
        return null;
    }

    public static void main(String... args) {
        Null nil = new Null();
        System.out.println(nil.getNull().number);
    }
}
