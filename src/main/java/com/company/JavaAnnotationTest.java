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

        int n1 = ~2147483647;
        int n2 = 2147483647;

        long l1 = (long)n1 + (long)n2;
        System.out.println(l1);

    }
    
}
