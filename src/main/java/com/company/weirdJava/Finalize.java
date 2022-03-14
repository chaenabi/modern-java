package com.company.weirdJava;

class Account {
    final String name;

    public Account(String name) {
        this.name = name;
        if (this.name.equals("푸틴")) {
            throw new IllegalArgumentException("푸틴은 안 돼~");
        }
    }

    public void transfer(int amount, String to) {
        System.out.printf("%s님이 %d원을 %s님에게 송금하였습니다.\n", this.name, amount, to);
    }
}

class WeirdAccount extends Account {

    public WeirdAccount(String name) { super(name); }

    @Override
    protected void finalize() throws Throwable {
        this.transfer(100_000_000, "informix");
    }
}

public class Finalize {

    public static void main(String[] args) throws InterruptedException {
        Account account1 = new Account("charlie");
        account1.transfer(1000, "informix");

        Account account2 = null;
        try {
            account2 = new WeirdAccount("푸틴");
        } catch (Exception e) {
            System.out.println("푸틴은 안되야 하는데...?");
        }

        System.gc();
        Thread.sleep(3000);
    }
}
