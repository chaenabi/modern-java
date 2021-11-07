package com.company;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

//JAVA 8
public enum Calculator {

    POWER(n -> n * n), // n제곱
    GAUSE(n -> (1 + n) * (n / 2)), // 1부터 n까지 합산한 결과
    PYTHAGORAS((n, m) -> Math.sqrt(POWER.calculate(n) + POWER.calculate(m)));

    private Function<Long, Long> expression;
    private BiFunction<Long, Long, Double> pythagoras;
    Calculator(Function<Long, Long> expression) {
        this.expression = expression;
    }
    Calculator(BiFunction<Long, Long, Double> pythagoras) {
        this.pythagoras = pythagoras;
    }
    public long calculate(long value) {
        return expression.apply(value);
    }
    public double calculate(long a, long b) {
        return pythagoras.apply(a, b);
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

class CalculatorTest {

    @Test
    @DisplayName("java 8 style enum with lambda -> input validation")
    public void java8() {
        assertEquals(5050, Calculator.GAUSE.calculate(100));
        assertEquals(5.0, Calculator.PYTHAGORAS.calculate(3, 4));
    }

    @Test
    @DisplayName("java 8 style enum with lambda -> input validation")
    public void java7() {
        assertEquals(5050, Calculator7.GAUSE7.calculate(100));
        assertEquals(5.0, Calculator7.PYTHAGORAS7.calculate(3, 4));
    }
}

