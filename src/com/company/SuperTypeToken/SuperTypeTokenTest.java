package com.company.SuperTypeToken;

import lombok.Getter;
import lombok.ToString;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.core.ResolvableType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class SuperTypeTokenTest {
    static class TypeSafeMap {
        Map<Type, Object> map = new HashMap<>();

        <T> void put(TypeReference<T> tr, T value) {
            map.put(tr.type, value);
        }

        <T> T get(TypeReference<T> tr) {
            if (tr.type instanceof Class<?>)
                return ((Class<T>)tr.type).cast(map.get(tr.type));
            else
                return ((Class<T>)((ParameterizedType)tr.type).getRawType()).cast(map.get(tr.type));
        }
    }

    static class TypeSafeList {
        private static class Types<T> {
            private Type type;
            private T data;

            @Override
            public String toString() {
                return "Types{" +
                        "type=" + type +
                        ", data=" + data +
                        '}';
            }

            public Type getType() { return type; }
            public T getData() { return data; }
        }

        final List<Types<?>> list = new ArrayList<>();

        <T> void add(TypeReference<T> tr, T data) {
            Types<T> types = new Types<>();
            types.type = tr.type;
            types.data = data;
            list.add(types);
        }

        /**
         * @param <T> tr.type
         * @param tr 타입안정성을 위해 명시된 슈퍼타입토큰
         * @return  tr.type 에 해당하는 모든 값을 list 에서 찾아내어 리스트로 모아서 반환
         */
        <T> List<T> get(TypeReference<T> tr) {
            if (tr.type instanceof Class<?>)
                return (List<T>) list.stream().filter(e -> e.type.equals(tr.type)).map(e -> e.data).collect(toList());
            else
                return (List<T>) ((Class<T>)((ParameterizedType)tr.type).getRawType()).cast(list.stream().filter(e -> e.type.equals(tr.type)).map(e -> e.data).collect(toList()));
        }
    }

    abstract static class TypeReference<T> {
        final Type type;

        public TypeReference() {
            Type sType = this.getClass().getGenericSuperclass();
            if (sType instanceof ParameterizedType) {
                this.type = ((ParameterizedType) sType).getActualTypeArguments()[0];
            } else {
                throw new RuntimeException();
            }
        }
    }

    @Test
    @DisplayName("타입 안정성을 지닌 다수의 데이터타입을 가지는 해시맵이 구현되었는지 검증 테스트")
    public void typeSafeMapTest() {

        // given
        TypeSafeMap m = new TypeSafeMap();

        // when
        m.put(new TypeReference<String>(){}, "String");
        m.put(new TypeReference<Integer>(){}, 1);
        m.put(new TypeReference<List<Integer>>(){}, List.of(1,2,3));
        m.put(new TypeReference<List<String>>(){}, List.of("a", "b", "c"));

        // then
        assertThat(m.get(new TypeReference<String>(){})).isEqualTo("String");
        assertThat(m.get(new TypeReference<Integer>(){})).isEqualTo(1);
        assertThat(m.get(new TypeReference<List<Integer>>(){})).isEqualTo(List.of(1,2,3));
        assertThat(m.get(new TypeReference<List<String>>(){})).isEqualTo(List.of("a","b","c"));
    }

    @Test
    @DisplayName("스프링이 지원하는 ResolvableType 을 이용한 다양한 타입에 대한 접근이 가능한지에 대하여 검증")
    public void testResolvableType() {
        ResolvableType rt = ResolvableType.forInstance(new TypeReference<List<Map<Set<Integer>, List<String>>>>() {});

        assertThat(rt.getSuperType().getGenerics().length).isEqualTo(1);
        assertThat(rt.getSuperType().getNested(1).toString()).isEqualTo("com.company.SuperTypeToken.SuperTypeTokenTest$TypeReference<java.util.List<java.util.Map<java.util.Set<java.lang.Integer>, " +
                                                                            "java.util.List<java.lang.String>>>>");
        assertThat(rt.getSuperType().getNested(2).toString()).isEqualTo("java.util.List<java.util.Map<java.util.Set<java.lang.Integer>, java.util.List<java.lang.String>>>");
        assertThat(rt.getSuperType().getNested(3).toString()).isEqualTo("java.util.Map<java.util.Set<java.lang.Integer>, java.util.List<java.lang.String>>");
    }

    @Test
    public void typeSafeMapTest2() {
        // given
        TypeSafeMap m = new TypeSafeMap() {};

        // when
        m.put(new TypeReference<>(){},List.of("One"));
        m.put(new TypeReference<>(){},12.0);
        m.put(new TypeReference<>(){},List.of(Set.of(1, 2, 3)));

        // then
        assertThat(m.get(new TypeReference<List<String>>() {})).isEqualTo(List.of("One"));
        assertThat(m.get(new TypeReference<Double>() {})).isEqualTo(12.0);
        assertThat(m.get(new TypeReference<List<Set<Integer>>>() {})).isEqualTo(List.of(Set.of(1, 2, 3)));

        // after check
        // m.map.forEach((k, v) -> System.out.println(k + " : " + v));
    }
    enum Grade { VIP, VVIP }
    @Test
    @DisplayName("TypeSafeList 에 들어가는 타입 및 값 검증")
    public void typeSafeListTest() {
        // given
        @ToString @Getter
        class User {
            private final String name;
            private final Integer age;
            private Grade grade;

            public User(String name, Integer age) {
                this.name = name;
                this.age = age;
                this.grade = Grade.VIP;
            }

            public User setGrade(Grade grade) { this.grade = grade; return this; }
        }
        TypeSafeList l = new TypeSafeList();


        // when
        l.add(new TypeReference<Integer>() {}, 4934);
        l.add(new TypeReference<List<Double>>() {}, List.of(13.0, 50.2));
        l.add(new TypeReference<List<Map<Integer, String>>>() {}, List.of(Map.of(42, "the answer to the 'ultimate question of life, the universe, and everything.'")));
        l.add(new TypeReference<User>(){}, new User("charlie", 17));
        l.add(new TypeReference<User>(){}, new User("charlie", 17));

        // then
        assertThat(l.get(new TypeReference<Integer>(){})).isEqualTo(List.of(4934));
        l.add(new TypeReference<Integer>() {}, 2391);
        assertThat(l.get(new TypeReference<Integer>(){})).isEqualTo(List.of(4934, 2391));
        assertThat(l.get(new TypeReference<String>(){})).isEqualTo(Collections.emptyList());
        assertThat(l.get(new TypeReference<List<Double>>(){})).isEqualTo(List.of(List.of(13.0, 50.2)));
        l.add(new TypeReference<List<Double>>(){}, List.of(543.23, 705.06));
        assertThat(l.get(new TypeReference<List<Double>>(){})).isEqualTo(List.of(List.of(13.0, 50.2), List.of(543.23, 705.06)));
        assertThat(l.get(new TypeReference<User>(){}).get(0).getName()).isEqualTo("charlie");
        assertThat(l.get(new TypeReference<List<Map<Integer, String>>>(){}).get(0).get(0).get(42)).isEqualTo("the answer to the 'ultimate question of life, the universe, and everything.'");
        assertThat(l.get(new TypeReference<User>(){}).get(0).setGrade(Grade.VVIP).getGrade()).isEqualTo(Grade.VVIP);
        /**
         *  after check
         *  print all list elements in l
         *  l.list.stream().forEach((v) -> System.out.println(v + " "));
         */
    }
}
