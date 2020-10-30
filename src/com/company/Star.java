package com.company;

import java.util.Scanner;

public class Star {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int cnt = 5;
        for(int i = 0; i < cnt; i++) {
            for(int j = 0; j < i; j++) {
                System.out.print(" ");
            }

            for(int j = 0; j <= i; j++) {
                System.out.print("*");
            }
            for(int j = 0; j < cnt-i; j++) {
                System.out.print(" ");
            }
            System.out.println();
        }
        for(int i = cnt; i > 1; i--) {
            for(int j = cnt; j > cnt-i+2; j--) {
                System.out.print(" ");
            }
            for(int j = cnt; j > cnt-i+1; j--) {
                System.out.print("*");
            }
            for(int j = cnt; j > i-cnt; j--) {
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
