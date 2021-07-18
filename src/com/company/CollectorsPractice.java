package com.company;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

import static java.util.stream.Collectors.groupingBy;

@Getter
class Car {
    private String name;
    private Brand brand;
    private Color color;

    public Car(String name, Brand brand, Color color) {
        this.name = name;
        this.brand = brand;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Brand getBrand() {
        return brand;
    }

    public Color getColor() {
        return color;
    }
}

enum Brand {
    AUDI, BMW, HYUNDAI
}

enum Color {
    Green, Red, Black, White, Blue
}

public class CollectorsPractice {
    public static void main(String[] args) {
        List<Car> cars = Arrays.asList(new Car("K5", Brand.HYUNDAI, Color.Green), new Car("A8", Brand.AUDI, Color.Black));
        Map<String, Map<Color, List<Car>>> carByBrandAndColor = cars.stream()
                                                                    .collect(groupingBy(Car::getName,
                                                                                        groupingBy(Car::getColor)));

        Collector<? super Car, ?, Map<Brand, Map<Color, List<Car>>>> carGroupingCollector = groupingBy(Car::getBrand, groupingBy(Car::getColor));
    }
}
