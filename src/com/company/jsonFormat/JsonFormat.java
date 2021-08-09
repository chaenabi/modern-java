package com.company.jsonFormat;

import com.google.gson.Gson;

public class JsonFormat<T> {

    T data;

    public JsonFormat(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new Gson().toJson(data);
    }
}

class Response<T> {
    T dtoData;

    public Response(T dtoData) {
        this.dtoData = dtoData;
    }

    @Override
    public String toString() {
        return new Gson().toJson(dtoData);
    }
}

class Request<T> {
    T dtoData;

    public Request(T dtoData) {
        this.dtoData = dtoData;
    }

    @Override
    public String toString() {
        return new Gson().toJson(dtoData);
    }
}


class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

class MainJsonFormat {
    public static void main(String[] args) {
        JsonFormat<Person> memberTest = new JsonFormat<>(new Person("korean", 10));
        System.out.println(memberTest);

        JsonFormat<Request<Person>> request = new JsonFormat<>(new Request<>(new Person("english", 20)));
        System.out.println(request.data);

        JsonFormat<Response<Person>> response = new JsonFormat<>(new Response<>(new Person("mathematics", 40)));
        System.out.println(response.data);
    }
}