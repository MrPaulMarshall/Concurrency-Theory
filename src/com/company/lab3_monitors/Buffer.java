package com.company.lab3_monitors;

import java.util.LinkedList;

public class Buffer {

    private int size;
    private int count;

    private LinkedList<Integer> items = new LinkedList<>();

    public Buffer(int size) {
        this.size = size;
        this.count = 0;
    }

    synchronized void produce(int newValue) throws InterruptedException {
        while (count == size) {
            wait();
        }
        items.add(newValue);
        count++;
        notifyAll();
    }

    synchronized int consume() throws InterruptedException {
        while (count == 0) {
            wait();
        }
        int newValue = items.removeFirst();
        count--;
        notify();
        return newValue;
    }

}
