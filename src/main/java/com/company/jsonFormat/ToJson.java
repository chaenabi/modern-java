package com.company.jsonFormat;


import com.google.gson.Gson;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;

class ToJsonExtension {
    public static <T> String toJson(T source) {
        return new Gson().toJson(source);
    }
}

@Builder
@RequiredArgsConstructor
public class ToJson {
    private final String id;
    private final Integer no;
    private final String name;
}

@ExtensionMethod(ToJsonExtension.class)
class ToStringJsonWithExtensionMethodTest {
    public static void main(String[] args) {
        ToJson method = new ToJson("id", 1, "name");
        System.out.println(method.toJson());
        System.out.println("ㅎㅇ".toJson());
    }
}
