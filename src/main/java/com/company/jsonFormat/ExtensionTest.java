package com.company.jsonFormat;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;

class 널검사기 {
    public static <T> String 널이냐(T source) {
        return source != null ? new Gson().toJson(source) : "널인데요??";
    }
}

@RequiredArgsConstructor
class 어떤클래스 {
    private final String id;
    private final Integer no;
    private final String name;
}

@ExtensionMethod(널검사기.class)
class ExtensionTest {
    public static void main(String[] args) {
        어떤클래스 클래스 = new 어떤클래스("id", 1, "name");
        어떤클래스 널 = null;

        System.out.println(클래스.널이냐());
        System.out.println("ㅎㅇ".널이냐());
        System.out.println(널.널이냐());
    }
}