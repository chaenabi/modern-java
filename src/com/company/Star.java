package com.company;

import java.util.function.Supplier;
enum Print {
    STAR(() -> "*"), VOID(() -> " ");
    public final Supplier<String> supplier;
    Print(Supplier<String> supplier) {
        this.supplier = supplier;
    }

    public void get() {
        System.out.print(supplier.get());
    }
}

public class Star {
    public static void main(String[] args) {
        int cnt = 5;
        for(int i = 0; i < cnt; i++) {
            for(int j = 0; j < i; j++) {
                Print.VOID.get();
            }

            for(int j = 0; j <= i; j++) {
                Print.STAR.get();
            }
            for(int j = 0; j < cnt-i; j++) {
                Print.VOID.get();
            }
            System.out.println();
        }
        for(int i = cnt; i > 1; i--) {
            for(int j = cnt; j > cnt-i+2; j--) {
                Print.VOID.get();
            }
            for(int j = cnt; j > cnt-i+1; j--) {
                Print.STAR.get();
            }
            for(int j = cnt; j > i-cnt; j--) {
                Print.VOID.get();
            }
            System.out.println();
        }
    }
}

class StarBox {
    public static void main(String[] args) {
        int cnt = 5;
        for(int i = 1; i <= cnt + 4; i++) {
            if(i >= 5) Print.STAR.get();
            else Print.VOID.get();
        }
        System.out.println();
        for(int i = 1; i < cnt + 2; i++) {
            if(i>=4) Print.STAR.get();
            else Print.VOID.get();

            for(int j = 1; j <= cnt + 2; j++) {
                if((i == 4) && (j < 4) || (j == 4) && (i > 3) || (j == 4-i) || (j > 4) && (j == 8 - i) || (8 - i < 5) && (j == (cnt + 7) - i)) {
                    Print.STAR.get();
                } else Print.VOID.get();
            }
            if(i < cnt) System.out.println("*");
            else System.out.println();
        }
        for(int i = 1; i <= cnt+4;i++) {
            if(i <= 5) Print.STAR.get();
            else Print.VOID.get();
        }

    }

}