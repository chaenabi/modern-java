package com.company;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class OptionalTest {

    @Test
    @DisplayName("if datalist has null, replace to optional class")
    public void when_data_has_Null() {
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
        // validate
        assertTrue(wrappedMembers.stream().anyMatch(Optional::isEmpty));
    }

    static class Member {
        public Member(String name, int age) {} /** @data just for identify */
    }
}
