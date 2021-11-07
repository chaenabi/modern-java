/*
package com.company.jsonFormat;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

enum EnumType {
    HI, GOODBYE;
}
@ToString
@ToStringJson
@RequiredArgsConstructor
public class ToStringJsonTest {
    private final int seq;
    private final String title;
    private final Node node;
    private final EnumType type;

    public static class Node {
        int val;
        Node left;
        Node right;

        public Node(int val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }


    }
}

class ToStringJsonMain {
    public static void main(String[] args) {
        ToStringJsonTest toStringJson = new ToStringJsonTest(1, "첫번째", new ToStringJsonTest.Node(1, null, null), EnumType.HI);
        System.out.println(toStringJson.toString());
    }
}*/
