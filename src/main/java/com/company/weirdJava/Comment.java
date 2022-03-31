package com.company.weirdJava;

public class Comment {
    public static void main(String[] args) {
        int number = 1;
        System.out.println(number);
        // number = 2;
        System.out.println(number);
        // \u000a number = 3;
        System.out.println(number);
        /*
           \u002A\u002F number = 4;
           System.out.println("범위형 주석 무효화 확인");
        //*/
        System.out.println(number);
    }
}
