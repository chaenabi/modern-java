package com.company.SuperTypeToken;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

import static java.util.stream.Collectors.toList;

class TypeSafeMap {
    Map<Type, Object> map = new HashMap<>();

    <T> void put(TypeReference<T> tr, T value) {
        map.put(tr.getType(), value);
    }

    // explicit type casting is perfectly safe.
    @SuppressWarnings("unchecked")
    <T> T get(TypeReference<T> tr) {
        if (tr.getType() instanceof Class<?>) return ((Class<T>) tr.getType()).cast(map.get(tr.getType()));
        else return ((Class<T>) ((ParameterizedType) tr.getType()).getRawType()).cast(map.get(tr.getType()));
    }
}

class TypeSafeList implements Iterable<TypeSafeList.Types<?>> {

    @Override
    public Iterator<Types<?>> iterator() {
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

    private final List<Types<?>> list = new ArrayList<>();

    <T> void add(TypeReference<T> tr, T data) {
        Types<T> types = new Types<>();
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
            return (List<T>) list.stream().filter(e -> e.getType().equals(tr.getType())).map(e -> e.data).collect(toList());
        else
            return (List<T>) ((Class<T>) ((ParameterizedType) tr.getType()).getRawType()).cast(list.stream().filter(e -> e.type.equals(tr.getType())).map(e -> e.data).collect(toList()));
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
abstract class TypeReference<T> {
    private final Type type;

    public TypeReference() {
        Type sType = this.getClass().getGenericSuperclass();
        if (sType instanceof ParameterizedType) {
            this.type = ((ParameterizedType) sType).getActualTypeArguments()[0];
        } else {
            throw new RuntimeException();
        }
    }
    public Type getType() { return type; }
}

@ToString @Getter
class Person { private final String name = "철수"; }

public class TypeSafeCollection {
    public static void main(String[] args) {
        TypeSafeList tslist = new TypeSafeList();
        TypeSafeMap tsMap = new TypeSafeMap();

        tslist.add(new TypeReference<>() {}, 1236);
        tslist.add(new TypeReference<>() {}, "text");
        tslist.add(new TypeReference<>() {}, true);
        tslist.add(new TypeReference<>() {}, false);
        tslist.add(new TypeReference<>() {}, List.of(1, 2, 3, 4, 5));
        tslist.add(new TypeReference<>() {}, new Person());

        var element = tslist.getValues(new TypeReference<String>() {});
        var element2 = tslist.getValues(new TypeReference<Boolean>() {});
        var element3 = tslist.getValues(new TypeReference<Integer>() {});
        var element4 = tslist.getValues(new TypeReference<List<Integer>>() {});
        var element5 = tslist.getValues(new TypeReference<Person>() {});

        for (var i : tslist) System.out.println(i.getData());
    }
}