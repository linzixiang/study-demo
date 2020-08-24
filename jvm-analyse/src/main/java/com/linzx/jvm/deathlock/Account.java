package com.linzx.jvm.deathlock;

import java.util.concurrent.atomic.AtomicInteger;

public class Account {

    private int balance = 100000;//这里假设每个人账户里面初始化的钱
    private final int accNo;
    private static final AtomicInteger sequence = new AtomicInteger();

    public Account() {
        accNo = sequence.incrementAndGet();
    }

    void debit(int m) throws InterruptedException {
        Thread.sleep(5);//模拟操作时间
        balance = balance + m;
    }

    void credit(int m) throws InterruptedException {
        Thread.sleep(5);//模拟操作时间
        balance = balance - m;
    }

    int getBalance() {
        return balance;
    }

    int getAccNo() {
        return accNo;
    }

    public int compareTo(int money) {
        if (balance > money) {
            return 1;
        }else if (balance < money) {
            return -1;
        }else {
            return 0;
        }
    }

}
