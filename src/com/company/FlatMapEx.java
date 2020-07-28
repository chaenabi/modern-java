package com.company;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static java.lang.Math.sqrt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FlatMapEx {
    public static void main(String[] args) {

       // flatMap1();
        flatMap2();
    }


    private static void flatMap1() {
        List<Integer> number1 = List.of(1, 2, 3);
        List<Integer> number2 = List.of(3, 4);

        List<int[]> pairs = number1.stream()
                .flatMap(i -> number2.stream()
                        .map(j -> new int[]{i, j}))
                .collect(Collectors.toList());

        String result = pairs.stream()
                .flatMapToInt(Arrays::stream)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" "));

        Collections.singletonList(result).forEach(System.out::print);
    }

    private static void flatMap2() {
        Stream<int[]> pythagoreanTriples =
            IntStream.rangeClosed(1, 100).boxed()
                     .flatMap(a ->
                            IntStream.rangeClosed(a, 100)
                                     .filter(b -> sqrt(a*a + b*b) % 1 == 0)
                                     .mapToObj(b -> new int[]{a, b, (int) sqrt(a*a + b*b)})
                    );

        pythagoreanTriples.limit(5)
                          .forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));

    }
}
