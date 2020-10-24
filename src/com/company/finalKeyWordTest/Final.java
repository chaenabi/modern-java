package com.company.finalKeyWordTest;


import lombok.Getter;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Getter
public class Final {
    private final String title;

    private Final(String title) {
        this.title = title;
    }

    public static Final setTitle(String title) {
        return new Final(title);
    }
}

class FinalTest {

    public static void main(String[] args) {
        new FinalTest().finalTest();
    }

    @Test
    public void finalTest() {
        Final fi = Final.setTitle("origin"); // redundant code
        fi = Final.setTitle("modified");
        assertThat(fi.getTitle()).isEqualTo("origin"); // failed. it must be same with "modified"
    }
}