package com.company.lab4_locksconditions;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {

    private final int size;
    private int count;

//    private final LinkedList<Integer> items = new LinkedList<>();

    private final Lock lock = new ReentrantLock();
    private final Condition noEnoughElems = lock.newCondition();
    private final Condition noEnoughPlace = lock.newCondition();


    public Buffer(int size) {
        this.size = size;
        this.count = 0;
    }

    void produce(int thread, int countItems) throws InterruptedException {
        lock.lock();
        try {
            System.out.println("Producer " + thread + ": TRY " + countItems + " items");
            while (count + countItems > size) {
                noEnoughPlace.await();
            }
//            items.add(newValue);
            count += countItems;
            System.out.println("Producer " + thread + ": Did " + countItems + " items");
            noEnoughElems.signal();
        } finally {
            lock.unlock();
        }
    }

    void consume(int thread, int countItems) throws InterruptedException {
        lock.lock();
        try {
            System.out.println("Consumer " + thread + ": TRY " + countItems + " items");
            while (count < countItems) {
                noEnoughElems.await();
            }
//            newValue = items.removeFirst();
            count -= countItems;
            System.out.println("Consumer " + thread + ": Did " + countItems + " items");
            noEnoughPlace.signal();
        } finally {
            lock.unlock();
        }
    }

}
