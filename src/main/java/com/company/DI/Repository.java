package com.company.DI;

import org.junit.jupiter.api.Test;

public interface Repository extends Jpa, CustomRepository {
    void doSome();
}

interface Jpa {
    void findAll();
}

interface CustomRepository {
    void doCustom();
}

abstract class RepositoryImpl implements Repository {
    @Override
    public void findAll() {
        System.out.println("RepositoryImpl.findAll");
    }

    @Override
    public void doCustom() {
        System.out.println("RepositoryImpl.doCustom");
    }
}

class Impl extends RepositoryImpl {
    @Override
    public void doSome() {

    }
}


class CustomImpl implements CustomRepository {

    @Override
    public void doCustom() {
        System.out.println("CustomImpl.doCustom");
    }
}

class TestClass {
    @Test
    public void diTest() {

    }
}