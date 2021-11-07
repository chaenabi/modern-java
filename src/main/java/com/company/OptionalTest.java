package com.company;



import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class OptionalTest {

    public void when_data_has_Null() {
        // provided resource
        List<Member> members;
        members = Arrays.asList(new Member("홍길동", 20),
                                new Member("장길산", 30),
                                null,
                                new Member("이순신", 40),
                                null
                                );

        // when
        List<Optional<Member>> wrappedMembers;
        wrappedMembers = members.stream()
                                .map(Optional::ofNullable)
                                .collect(Collectors.toList());
        // validate
       // assertTrue(wrappedMembers.stream().anyMatch(Optional::isEmpty));
    }

    static class Member {
        public Member(String name, int age) {} /** @data just for identify */
    }
}
