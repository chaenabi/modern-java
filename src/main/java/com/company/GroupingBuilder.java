package com.company;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collector;

import static com.company.GroupingBuilder.groupOn;
import static java.util.stream.Collectors.groupingBy;
import static org.assertj.core.api.Assertions.assertThat;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupingBuilder<T, D, K> {
    private final Collector<? super T, ?, Map<K, D>> collector;

    public Collector<? super T, ?, Map<K, D>> get() {
        return collector;
    }

    public <J> GroupingBuilder<T, Map<K, D>, J> after(Function<? super T, ? extends J> classifier) {
        return new GroupingBuilder<>(groupingBy(classifier, collector));
    }

    public static <T, K> GroupingBuilder<T, List<T>, K> groupOn(Function<? super T, ? extends K> classifier) {
        return new GroupingBuilder<>(groupingBy(classifier));
    }
}

class GroupingBuilderTest {

    @Test
    @DisplayName("")
    public void groupingAndGetCollectorTest() {
        Collector<? super Car, ?, Map<Brand, Map<Color, List<Car>>>> carGroupingCollector = groupOn(Car::getColor).after(Car::getBrand).get();
        var i = carGroupingCollector.accumulator();
        assertThat(i).isEqualTo((BiConsumer.class).getClassLoader());
    }
}