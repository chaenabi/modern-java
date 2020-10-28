package com.company;

class Box<T> {
    private T t;
    public void set(T t) {
        this.t = t;
    }
    public T get() {
        return t;
    }
}

class Toy {
    @Override
    public String toString() {
        return "I am a Toy";
    }
}

class CarToy extends Toy {
    @Override
    public String toString() {
        return "I am a Car Toy";
    }
}

class BoxHandler {

    public static void outBox(Box<? extends Toy> box) { // must get only.
        Toy toy = box.get(); // get.
        CarToy carToy = (CarToy) box.get(); // get.
        System.out.println(toy);
        // cannot use box.set() by WildCard: <? extends Toy>


    }

    public static void inBox(Box<? super Toy> box, Toy n) { // must set only.
        box.set(n); // set.
        // cannot use box.get() by WildCard: <? super Toy>
    }
}

public class BoundedWildcardBase {

    public static void main(String[] args) {
        Box<Toy> box = new Box<>();
        BoxHandler.inBox(box, new Toy());
        BoxHandler.outBox(box);
    }

}

