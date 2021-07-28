package com.company.GenericWildCard;

import lombok.ToString;

public class Box<T> {
    T t;

    public void setBox(T t) {
        this.t = t;
    }

    public T getBox() {
        return t;
    }
}

@ToString
class Toy {
    int n;
    public Toy(int n) {this.n = n;}
}
@ToString
class Car extends Toy {
    public Car(int n) {
        super(n);
    }
}
@ToString
class Robot extends Toy {
    public Robot(int n) {
        super(n);
    }
}

class Generic {
    public static void main(String[] args) {
        Box<Toy> box = new Box<>();
        inBox(box, new Car(1));
        outBox(box);
        inBox(box, new Robot(2));
    }

    public static void outBox(Box<Toy> box) {
        Toy t = box.getBox();
        System.out.println(t);
        box.setBox(new Robot(3));
    }

    public static void inBox(Box<Toy> box, Toy n) {
        box.setBox(n);
        Toy t = box.getBox(); // 논리적인 오류가 있는
        System.out.println(t);
    }
}
