package com.company;

import java.math.BigInteger;

public class Basic {

    public static void main(String[] args) {
        // E 69
        // X 88
        byte[] value = new byte[2];
        value[0] = 69;
        value[1] = 88;

        System.out.println(new BigInteger(1,value));
    }
}
