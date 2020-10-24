package com.company;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

import static java.util.stream.Collectors.groupingBy;

@RequiredArgsConstructor
@Getter
class Car {
    private final String name;
    private final Brand brand;
    private final Color color;
}

enum Brand {
    AUDI, BMW, HYUNDAI
}

enum Color {
    Green, Red, Black, White, Blue
}

public class CollectorsPractice {
    public static void main(String[] args) {
        List<Car> cars = List.of(new Car("K5", Brand.HYUNDAI,Color.Green), new Car("A8", Brand.AUDI,Color.Black));
        Map<String, Map<Color, List<Car>>> carByBrandAndColor = cars.stream()
                                                                    .collect(groupingBy(Car::getName,
                                                                                        groupingBy(Car::getColor)));

        Collector<? super Car, ?, Map<Brand, Map<Color, List<Car>>>> carGroupingCollector = groupingBy(Car::getBrand, groupingBy(Car::getColor));
    }
}
