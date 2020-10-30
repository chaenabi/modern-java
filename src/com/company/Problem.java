package com.company;

import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class Problem {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");
        List<Transaction> transactions = List.of(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        // 1. 2011년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정리하시오.
        transactions.stream()
                    .filter(year -> year.getYear() == 2011)
                    .sorted(comparing(Transaction::getValue))
                    .collect(toList())
                    .forEach(i -> System.out.println(i.getTrader()));

        System.out.println();

        // 2. 거래자가 근무하는 모든 도시를 중복 없이 나열하시오.
        transactions.stream()
                    .map(Transaction::getTrader)
                    .map(Trader::getCity)
                    .distinct()
                    .forEach(System.out::println);

        System.out.println();

        // 3. 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오.
        transactions.stream()
                    .map(Transaction::getTrader)
                    .filter(city -> city.getCity().equals("Cambridge"))
                    .sorted(comparing(Trader::getName))
                    .collect(toList())
                    .forEach(System.out::println);

        System.out.println();

        // 4. 모든 거래자의 이름을 알파벳순으로 정렬해서 반환하시오.
        transactions.stream()
                    .map(Transaction::getTrader)
                    .sorted(comparing(Trader::getName))
                    .collect(toList())
                    .forEach(System.out::println);

        // 5. 밀라노에 거래자가 있는가?
        boolean isExistDealer = transactions.stream()
                                            .map(Transaction::getTrader)
                                            .anyMatch(i -> i.getCity()
                                                            .equals("Milan"));
        System.out.println();
        System.out.println(isExistDealer);

        // 6. 케임브리지에 거주하는 거래자의 모든 트랜잭션값을 출력하시오.
        transactions.stream()
                    .filter(i -> i.getTrader().getCity().equals("Cambridge"))
                    .map(Transaction::getValue)
                    .forEach(System.out::println);


        // 7. 전체 트랜잭션 중 최댓값은 얼마인가?
        int maxValue = transactions.stream()
                                   .map(Transaction::getValue)
                                   .reduce(Integer.MIN_VALUE, Integer::max);
        System.out.println();
        System.out.println(maxValue);


        // 8. 전체 트랜잭션 중 최솟값은 얼마인가?
        int minValue = transactions.stream()
                                   .map(Transaction::getValue)
                                   .reduce(Integer.MAX_VALUE, Integer::min);
        System.out.println();
        System.out.println(minValue);
        System.out.println();
    }
}

class Trader {
    private final String name;
    private final String city;
    public Trader(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "Trader{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}

class Transaction {
    private final Trader trader;
    private final int year;
    private final int value;
    public Transaction(Trader trader, int year, int value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    public Trader getTrader() {
        return trader;
    }

    public int getYear() {
        return year;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "trader=" + trader +
                ", year=" + year +
                ", value=" + value +
                '}';
    }
}