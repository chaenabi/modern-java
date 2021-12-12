package com.company.jsonFormat;

import lombok.ToString;

public class JsonFormat<T> {

    T clazz;

    public JsonFormat(T clazz) {
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        return "json";
    }
}

@ToString
// @ToStringJsonFormat
class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

class MainJsonFormat {
    public static void main(String[] args) {
        JsonFormat<Person> jsonPerson = new JsonFormat<>(new Person("charles", 10));
        System.out.println(jsonPerson);

        Person person = jsonPerson.clazz;
        System.out.println(person);
    }
}