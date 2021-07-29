package com.company.SuperTypeToken;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.core.ResolvableType;

import java.util.List;
import java.util.Map;

public class SuperTypeTokenTest2 {

    @Test
    @DisplayName("TypeSafeList foreach print")
    public void typeSafeListTest4() {
        // given
        SuperTypeTokenTest.TypeSafeList l = new SuperTypeTokenTest.TypeSafeList();
        // given for compare using typeReference
        ResolvableType listDoubleRT = ResolvableType.forInstance(new SuperTypeTokenTest.TypeReference<List<Double>>() {});
        ResolvableType listMapRT = ResolvableType.forInstance(new SuperTypeTokenTest.TypeReference<List<Map<Integer, String>>>() {});

        // when
        l.add(new SuperTypeTokenTest.TypeReference<Integer>() {}, 4934);
        l.add(new SuperTypeTokenTest.TypeReference<List<Double>>() {}, List.of(13.0, 50.2));
        l.add(new SuperTypeTokenTest.TypeReference<List<Map<Integer, String>>>() {}, List.of(Map.of(42, "the answer to the 'ultimate question of life, the universe, and everything.'")));
        l.add(new SuperTypeTokenTest.TypeReference<SuperTypeTokenTest.User>(){}, new SuperTypeTokenTest.User("charlie", 17));
        l.add(new SuperTypeTokenTest.TypeReference<SuperTypeTokenTest.User>(){}, new SuperTypeTokenTest.User("mark", 34));

        for(SuperTypeTokenTest.TypeSafeList.Types<?> i : l) {
            System.out.println(i.getData());
        }
    }
}
