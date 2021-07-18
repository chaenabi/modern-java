package com.company;

class Parent {
    public void show() {
        System.out.println("parent");
    }
}

class Child extends Parent {
    public void show() {
        System.out.println("child");
    }
}

public class Ideone {
    public static void main (String[] args) throws java.lang.Exception {

        Parent parent = new Child();
        Child child = (Child) parent;
        parent.show();


    }
}
