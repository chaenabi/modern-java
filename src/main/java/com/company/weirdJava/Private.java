package com.company.weirdJava;

import java.lang.reflect.Field;

public class Private {
    public static void main(String[] args) throws Exception {

        PrivateClass pc = new PrivateClass();
        //int id = pc.id; // pc.id를 찾을 수 없으므로 당연한 에러 발생
        Field pcId = PrivateClass.class.getDeclaredField("id");
        pcId.setAccessible(true);
        pcId.setInt(pc, 99999);
        int id = (int) pcId.get(pc);
        System.out.println(id);
    }

}

class PrivateClass {
    private final int id = 27356;
}
