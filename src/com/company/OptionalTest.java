package com.company;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class OptionalTest {

    @Test
    public void dataIncludesNull() {
        // provided resource
        List<Member> members = Arrays.asList(new Member("홍길동", 20),
                                            new Member("장길산", 30),
                                            null,
                                            new Member("이순신", 40),
                                            null
                                            );

        // when
        List<Optional<Member>> wrappedMembers = members.stream()
                                                       .map(Optional::ofNullable)
                                                       .collect(Collectors.toList());

        wrappedMembers.forEach(System.out::println); // 내용 출력

        // validate
        assertTrue(wrappedMembers.stream().anyMatch(Optional::isEmpty));
    }

    static class Member {
        private final String name;
        private final int age;

        public Member(String name, int age) {
            this.name = name;
            this.age = age;
        }
        @Override
        public String toString() {
            return "Member{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
