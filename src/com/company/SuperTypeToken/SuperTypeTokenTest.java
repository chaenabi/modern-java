package com.company.SuperTypeToken;

import lombok.EqualsAndHashCode;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SuperTypeTokenTest {

    @EqualsAndHashCode
    static class TypeSafeMap {
        private final Map<Type, Object> map = new HashMap<>();

        <T> void put(TypeReference<T> tr, T value) {
            map.put(tr.type, value);
        }

        // explicit type casting is perfectly safe.
        @SuppressWarnings("unchecked")
        <T> T get(TypeReference<T> tr) {
            if (tr.type instanceof Class<?>) return ((Class<T>) tr.type).cast(map.get(tr.type));
            else return ((Class<T>)((ParameterizedType)tr.type).getRawType()).cast(map.get(tr.type));
        }

        public int size() { return map.size(); }
        public void clear() { map.clear(); }
        public boolean isEmpty() { return map.isEmpty(); }
        public boolean containsKey(Object key) { return map.containsKey(key); }
        public <T> T remove(T key) { return (T) map.remove(key); }
        public boolean containsValue(Object value) { return map.containsValue(value); }
        public Set<Type> keySet() { return map.keySet(); }

    }

    static class TypeSafeList implements Iterable<TypeSafeList.Types<?>> {

        @Override
        public Iterator<Types<?>> iterator() {
            return list.iterator();
        }

        @ToString @EqualsAndHashCode
        static class Types<T> {
            private Type type;
            private T data;

            public Type getType() { return type; }
            public T getData() { return data; }
            boolean isSameTypeWith(Type rtype) { return rtype.equals(getType()); }
        }

        private final List<Types<?>> list = new ArrayList<>();

        <T> void add(TypeReference<T> tr, T data) {
            Types<T> types = new Types<>();
            types.type = tr.type;
            types.data = data;
            list.add(types);
        }

        /**
         * @param <T> tr.type
         * @param tr 타입안정성을 위해 사용된 슈퍼 타입토큰
         * @return  tr.type 에 해당하는 모든 값을 list 에서 찾아내어 리스트로 모아서 반환
         */
        // explicit type casting is perfectly safe.
        @SuppressWarnings("unchecked")
        <T> List<T> getValues(TypeReference<T> tr) {
            if (tr.type instanceof Class<?>)
                return (List<T>) list.stream().filter(e -> e.type.equals(tr.type)).map(e -> e.data).collect(toList());
            else
                return (List<T>) ((Class<T>)((ParameterizedType)tr.type).getRawType()).cast(list.stream().filter(e -> e.type.equals(tr.type)).map(e -> e.data).collect(toList()));
        }

        Type getType(int index) {
            return list.get(index).getType();
        }

        // only one value contains in list. list is immutable.
        List<?> getValue(int index) {
            return List.of(list.get(index).getData());
        }

        public int size() {
            return list.size();
        }

        public List<Types<?>> getList() {
            return list;
        }
    }

    // T does not used in this class directly, but indirectly (getGenericSuperclass()) uses.
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

    enum Grade { VIP, VVIP }
    @ToString @Getter @EqualsAndHashCode
    static class User {
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
        assertThat(rt.getSuperType().getNested(4).toString()).isEqualTo("java.util.List<java.lang.String>");
        assertThat(rt.getSuperType().getNested(5).toString()).isEqualTo("java.lang.String");
        assertThat(rt.getSuperType().getNested(6).toString()).isEqualTo("?");
        assertThat(rt.getSuperType().getNested(7).toString()).isEqualTo("?");
        assertThat(rt.getSuperType().getNested(8).toString()).isEqualTo("?");
        assertThat(rt.getSuperType().getNested(9).toString()).isEqualTo("?");
    }

    @Test
    @DisplayName("typeSafeMap validate value")
    public void typeSafeMapTest2() {
        // given
        TypeSafeMap typeSafeMap = new TypeSafeMap() {};

        // when
        typeSafeMap.put(new TypeReference<>(){},List.of("One"));
        typeSafeMap.put(new TypeReference<>(){},12.0);
        typeSafeMap.put(new TypeReference<>(){},List.of(Set.of(1, 2, 3)));

        // then
        assertThat(typeSafeMap.get(new TypeReference<List<String>>() {})).isEqualTo(List.of("One"));
        assertThat(typeSafeMap.get(new TypeReference<Double>() {})).isEqualTo(12.0);
        assertThat(typeSafeMap.get(new TypeReference<List<Set<Integer>>>() {})).isEqualTo(List.of(Set.of(1, 2, 3)));

        // after check
        // typeSafeMap.map.forEach((k, v) -> System.out.println(k + " : " + v));
    }


    @Test
    @DisplayName("TypeSafeList 에 들어간 타입 중 특정 타입 값들을 모두 리스트로 꺼내어 타입과 값을 검증함, getValues(TypeReference tr) 사용")
    public void typeSafeListTest() {

        // given
        TypeSafeList typeSafeList = new TypeSafeList();

        // when
        typeSafeList.add(new TypeReference<Integer>() {}, 4934);
        typeSafeList.add(new TypeReference<List<Double>>() {}, List.of(13.0, 50.2));
        typeSafeList.add(new TypeReference<List<Map<Integer, String>>>() {}, List.of(Map.of(42, "the answer to the 'ultimate question of life, the universe, and everything.'")));
        typeSafeList.add(new TypeReference<User>(){}, new User("charlie", 17));
        typeSafeList.add(new TypeReference<User>(){}, new User("mark", 31));

        // then
        assertThat(typeSafeList.getValues(new TypeReference<Integer>(){})).isEqualTo(List.of(4934));
        typeSafeList.add(new TypeReference<Integer>() {}, 2391);
        assertThat(typeSafeList.getValues(new TypeReference<Integer>(){})).isEqualTo(List.of(4934, 2391));
        assertThat(typeSafeList.getValues(new TypeReference<String>(){})).isEqualTo(Collections.emptyList());
        assertThat(typeSafeList.getValues(new TypeReference<List<Double>>(){})).isEqualTo(List.of(List.of(13.0, 50.2)));
        typeSafeList.add(new TypeReference<List<Double>>(){}, List.of(543.23, 705.06));
        assertThat(typeSafeList.getValues(new TypeReference<List<Double>>(){})).isEqualTo(List.of(List.of(13.0, 50.2), List.of(543.23, 705.06)));
        assertThat(typeSafeList.getValues(new TypeReference<User>(){}).get(0).getName()).isEqualTo("charlie");
        assertThat(typeSafeList.getValues(new TypeReference<List<Map<Integer, String>>>(){}).get(0).get(0).get(42)).isEqualTo("the answer to the 'ultimate question of life, the universe, and everything.'");
        assertThat(typeSafeList.getValues(new TypeReference<User>(){}).get(0).setGrade(Grade.VVIP).getGrade()).isEqualTo(Grade.VVIP);
        /**
         *  after check
         *  print all list elements in typeSafeList
         *  typeSafeList.list.stream().forEach((v) -> System.out.println(v + " "));
         */
    }

    @Test
    @DisplayName("TypeSafeList에 담긴 타입들을 검증함, getType(int index) 사용")
    public void typeSafeListTest2() {
        // given
        TypeSafeList typeSafeList = new TypeSafeList();
        // given for compare using typeReference
        ResolvableType listDoubleRT = ResolvableType.forInstance(new TypeReference<List<Double>>() {});
        ResolvableType listMapRT = ResolvableType.forInstance(new TypeReference<List<Map<Integer, String>>>() {});

        // when
        typeSafeList.add(new TypeReference<Integer>() {}, 4934);
        typeSafeList.add(new TypeReference<List<Double>>() {}, List.of(13.0, 50.2));
        typeSafeList.add(new TypeReference<List<Map<Integer, String>>>() {}, List.of(Map.of(42, "the answer to the 'ultimate question of life, the universe, and everything.'")));
        typeSafeList.add(new TypeReference<User>(){}, new User("charlie", 17));
        typeSafeList.add(new TypeReference<User>(){}, new User("mark", 34));

        assertThat(typeSafeList.getType(0)).isEqualTo(Integer.class);
        assertThat(typeSafeList.getType(1)).isEqualTo(listDoubleRT.getSuperType().getNested(2).getType());
        assertThat(typeSafeList.getType(2)).isEqualTo(listMapRT.getSuperType().getNested(2).getType());
        assertThat(typeSafeList.getType(3)).isEqualTo(User.class);
        assertThat(typeSafeList.getType(4)).isEqualTo(User.class);
    }

    @Test
    @DisplayName("TypeSafeList에 담긴 값 하나를 꺼내어 검증함, getValue(int value) 사용")
    public void typeSafeListTest3() {
        // given
        TypeSafeList tslist = new TypeSafeList();
        // given for compare using typeReference
        ResolvableType listDoubleRT = ResolvableType.forInstance(new TypeReference<List<Double>>() {});
        ResolvableType listMapRT = ResolvableType.forInstance(new TypeReference<List<Map<Integer, String>>>() {});

        // when
        tslist.add(new TypeReference<Integer>() {}, 4934);
        tslist.add(new TypeReference<List<Double>>() {}, List.of(13.0, 50.2));
        tslist.add(new TypeReference<List<Map<Integer, String>>>() {}, List.of(Map.of(42, "the answer to the 'ultimate question of life, the universe, and everything.'")));
        tslist.add(new TypeReference<User>(){}, new User("charlie", 17));
        tslist.add(new TypeReference<User>(){}, new User("mark", 34));

        assertThat(tslist.getValue(0).get(0)).isEqualTo(4934);
        assertThat(tslist.getValue(1).get(0)).isEqualTo(List.of(13.0, 50.2));
        assertThat(tslist.getValue(2).get(0)).isEqualTo(List.of(Map.of(42, "the answer to the 'ultimate question of life, the universe, and everything.'")));
        assertThat(tslist.getValue(3).get(0)).isEqualTo(new User("charlie", 17));
        assertThat(tslist.getValue(4).get(0)).isNotEqualTo(new User("charlie", 17));
    }

    @Test
    @DisplayName("TypeSafeList foreach type validation")
    public void typeSafeListTest4() {
        // given
        TypeSafeList typeSafeList = new TypeSafeList();

        // when
        typeSafeList.add(new TypeReference<Integer>() {}, 4934);
        typeSafeList.add(new TypeReference<List<Double>>() {}, List.of(13.0, 50.2));
        typeSafeList.add(new TypeReference<List<Map<Integer, String>>>() {}, List.of(Map.of(42, "the answer to the 'ultimate question of life, the universe, and everything.'")));
        typeSafeList.add(new TypeReference<User>(){}, new User("charlie", 17));
        typeSafeList.add(new TypeReference<User>(){}, new User("mark", 34));

        for(int i = 0; i < typeSafeList.size(); i++) {
            assertTrue(typeSafeList.getList().get(i).isSameTypeWith(typeSafeList.getType(i)));
        }

        for(var i : typeSafeList) {
            System.out.println(i.getData());
        }
    }
}