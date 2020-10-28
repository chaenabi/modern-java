/*
package com.company;

import java.util.function.BiFunction;
import java.util.function.Function;

//JAVA 8
public enum Calculator {

    POWER(n -> n * n), // n제곱
    GAUSE(n -> (1 + n) * (n / 2)), // 1부터 n까지 합산한 결과
    PYTHAGORAS((n, m) -> Math.sqrt(POWER.calculate(n) + POWER.calculate(m)));

    private Function<Long, Long> expression;
    private BiFunction<Long, Long, Double> pyhagoras;
    Calculator(Function<Long, Long> expression) {
        this.expression = expression;
    }
    Calculator(BiFunction<Long, Long, Double> pyhagoras) {
        this.pyhagoras = pyhagoras;
    }
    public long calculate(long value) {
        return expression.apply(value);
    }
    public double calculate(long a, long b) {
        return pyhagoras.apply(a, b);
    }
}

//JAVA 7
enum Calculator7 {
    POWER7 {
        long calculate(long n) { return n * n; }
        double calculate(long value, long value2) { return 0; } // 불필요한 코드 구현을 강제당함
    },
    GAUSE7 {
        long calculate(long n) { return (1 + n) * (n / 2); }
        double calculate(long value, long value2) { return 0; } // // 불필요한 코드 구현을 강제당함
    },
    PYTHAGORAS7 {
        long calculate(long value) { return 0; } // 불필요한 코드 구현을 강제당함
        double calculate(long n, long m) {
            return Math.sqrt(POWER7.calculate(n) + POWER7.calculate(m));
        }
    };

    abstract long calculate(long value);
    abstract double calculate(long n, long m);
}

class RunCalculator {
    public static void main(String[] args) {
        System.out.println("------------JAVA 8-------------");
        Calculator gause = Calculator.GAUSE;
        System.out.println(gause.calculate(100));

        Calculator pyhagoras = Calculator.PYTHAGORAS;
        System.out.println(pyhagoras.calculate(3, 4));

        System.out.println("------------JAVA 7-------------");

        Calculator7 gause7 = Calculator7.GAUSE7;
        System.out.println(gause7.calculate(100));
        Calculator7 pytagoras7 = Calculator7.PYTHAGORAS7;
        System.out.println(pytagoras7.calculate(3, 4));

    }
}

*/
