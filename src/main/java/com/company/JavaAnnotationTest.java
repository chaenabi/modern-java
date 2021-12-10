package com.company;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaAnnotationTest {

    public void annotationIgnoreTest() throws Exception {
        // setup resource
        int n = 99;

        // when resource works below.
        // \u000a ++n;

        // verify.
        assertThat(n).isEqualTo(100);
    }

    public void test() {
        char c1 = 'a';
        char c2 = 'a';

        int n = c1 + c2;
        System.out.println(n);

        long n1 = ~0;
        long n2 = 2147483647;

        // 0000 0000 0000 0000
        // 1111 1111 1111 1111
        System.out.println(Long.toBinaryString(~n1));
        System.out.println(n1);
        System.out.println(Long.toBinaryString(n1));

        long l1 = n1 + n2;
        System.out.println(l1);

    }

    public static void main(String[] args) {
        new JavaAnnotationTest().test();
    }
}
