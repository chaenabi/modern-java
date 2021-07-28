package com.company.SuperTypeToken;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.lang.reflect.ParameterizedType;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class TypeParameterTest {

    @Test
    @DisplayName("리스트의 타입을 알아낼 수 있는지 검증")
    public void getCollectionType() {
        List<String> list = new ArrayList<>();
        assertThat(list.getClass()).isSameAs(ArrayList.class);
    }

    @Test
    @DisplayName("매개변수화되지 않은 제네릭 타입을 알아낼 수 있는지 검증")
    public void getParameterizedDataType() {
        List<String> list = new ArrayList<>();
        assertThat(list.getClass().getTypeName().getClass()).isSameAs(String.class);
    }

    @Test
    @DisplayName("매개변수화된 제네릭 타입을 알아낼 수 있는지 검증, 알아낼 수 없어야 통과")
    public void cannotGetParameterizedGenericType() {
        List<Map<Integer, Double>> list = new LinkedList<>();
        assertThat(list.getClass().getTypeParameters()[0].getTypeName()).isNotSameAs(HashMap.class);
    }

    @Test
    @DisplayName("TypeSafe 가 상속하고 있는 상위 인스턴스에서 매개변수화된 제네릭 타입을 알아낼 수 있는지 검증, 알아 낼 수 있어야 통과")
    public void canGetParameterizedGenericType() {
        class Super<T> {}
        class TypeSafe extends Super<List<Map<Integer, Double>>> {}

        TypeSafe ts = new TypeSafe();
        ParameterizedType pt = (ParameterizedType) ts.getClass().getGenericSuperclass();

        assertThat(pt.getActualTypeArguments()[0].getClass()).isNotSameAs(List.class);
        assertThat(pt.getActualTypeArguments()[0].getTypeName()).isEqualTo("java.util.List<java.util.Map<java.lang.Integer, java.lang.Double>>");
    }

    @Test
    @DisplayName("익명클래스가 상속하고 있는 상위 인스턴스에서 매개변수화된 제네릭 타입을 알아낼 수 있는지 검증, 알아 낼 수 있어야 통과")
    public void canGetParameterizedGenericTypeUsingAnonymousClass() {
        class Super<T> {}

        Super ts = new Super<List<Map<Integer, Double>>>() {};
        ParameterizedType pt = (ParameterizedType) ts.getClass().getGenericSuperclass();

        assertThat(pt.getActualTypeArguments()[0].getClass()).isNotSameAs(List.class);
        assertThat(pt.getActualTypeArguments()[0].getTypeName()).isEqualTo("java.util.List<java.util.Map<java.lang.Integer, java.lang.Double>>");
    }

}