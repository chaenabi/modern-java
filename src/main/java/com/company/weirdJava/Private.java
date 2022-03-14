package com.company.weirdJava;

import java.lang.reflect.Field;

public class Private {
    public static void main(String[] args) throws Exception {

        PrivateClass pc = new PrivateClass();
        //int id = pc.id; // pc.id를 찾을 수 없으므로 당연한 에러 발생
        Field pcId = PrivateClass.class.getDeclaredField("id"); // 리플렉션
        pcId.setAccessible(true);
        int id = (int) pcId.get(pc); // 에러 안남
        System.out.println(id);
        Field pcName = String.class.getDeclaredField("name");
        pcName.setAccessible(true);
        pcName.set("Charlie", pcName.get("Informix"));
        System.out.println(pcName);
    }

}

class PrivateClass {
    private final int id = 27356;
    private String name = "Charlie";
}
