package com.company.SuperTypeToken;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class TypeSafeCollection {
}

@EqualsAndHashCode
class TypeSafeMap {
    private final Map<Type, Object> map = new HashMap<>();

    <T> void put(TypeReference<T> tr, T value) {
        map.put(tr.getType(), value);
    }

    // explicit type casting is perfectly safe.
    @SuppressWarnings("unchecked")
    <T> T get(TypeReference<T> tr) {
        if (tr.getType() instanceof Class<?>) return ((Class<T>) tr.getType()).cast(map.get(tr.getType()));
        else return ((Class<T>) ((ParameterizedType) tr.getType()).getRawType()).cast(map.get(tr.getType()));
    }

    public int size() {
        return map.size();
    }

    public void clear() {
        map.clear();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    public <T> T remove(T key) {
        return (T) map.remove(key);
    }

    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    public Set<Type> keySet() {
        return map.keySet();
    }
}

class TypeSafeList implements Iterable<TypeSafeList.Types<?>> {

    @Override
    public Iterator<TypeSafeList.Types<?>> iterator() {
        return list.iterator();
    }

    @ToString
    @EqualsAndHashCode
    static class Types<T> {
        private Type type;
        private T data;

        public Type getType() {
            return type;
        }

        public T getData() {
            return data;
        }

        boolean isSameTypeWith(Type rtype) {
            return rtype.equals(getType());
        }
    }

    private final List<TypeSafeList.Types<?>> list = new ArrayList<>();

    <T> void add(TypeReference<T> tr, T data) {
        TypeSafeList.Types<T> types = new TypeSafeList.Types<>();
        types.type = tr.getType();
        types.data = data;
        list.add(types);
    }

    /**
     * @param <T> tr.type
     * @param tr  타입안정성을 위해 사용된 슈퍼 타입토큰
     * @return tr.type 에 해당하는 모든 값을 list 에서 찾아내어 리스트로 모아서 반환
     */
    // explicit type casting is perfectly safe.
    @SuppressWarnings("unchecked")
    <T> List<T> getValues(TypeReference<T> tr) {
        if (tr.getType() instanceof Class<?>)
            return (List<T>) list.stream()
                                 .filter(e -> e.type.equals(tr.getType()))
                                  .map(e -> e.data)
                                  .collect(toList());
        else
            return (List<T>) ((Class<T>) ((ParameterizedType) tr.getType())
                                                                .getRawType())
                                                                .cast(list.stream()
                                                                          .filter(e -> e.type.equals(tr.getType()))
                                                                          .map(e -> e.data)
                                                                          .collect(toList()));
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

abstract class TypeReference<T> {
    private final Type type;

    public TypeReference() {
        Type sType = this.getClass().getGenericSuperclass();
        if (sType instanceof ParameterizedType) type = ((ParameterizedType) sType).getActualTypeArguments()[0];
        else throw new RuntimeException();
    }

    public Type getType() {
        return type;
    }
}

class Main {
    public static void main(String[] args) {
        TypeSafeList list = new TypeSafeList();
        list.add(new TypeReference<>() {}, 1);
        list.add(new TypeReference<>() {}, 2);
        list.add(new TypeReference<>() {}, "hello world !");

        var e = list.getValues(new TypeReference<Integer>() {});
        var e2 = list.getValue(2);

        for (var i : e) System.out.println(i);
        System.out.println(e2);
    }
}
