package com.company.lab1_pmsemaphore;

public class Counter {
    private int x;

    public Counter(int x) {
        this.x = x;
    }

    public void increment() {
        x++;
    }

    public void decrement() {
        x--;
    }

    public void print() {
        System.out.println(x);
    }
}
