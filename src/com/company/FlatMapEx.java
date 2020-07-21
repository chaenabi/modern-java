package com.company;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;

public class FlatMapEx {
    public static void main(String[] args) {

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

}
