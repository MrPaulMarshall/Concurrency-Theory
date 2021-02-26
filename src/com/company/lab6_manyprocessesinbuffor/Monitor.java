package com.company.lab6_manyprocessesinbuffor;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {

    private final LinkedList<Integer> freeSpots = new LinkedList<>();
    private final LinkedList<Integer> fullSpots = new LinkedList<>();

    private final int bufferSize;

    private final ReentrantLock lock = new ReentrantLock();

    private final Condition waitingConsumers = lock.newCondition();
    private final Condition waitingProducers = lock.newCondition();

    public Monitor(int bufferSize) {
        this.bufferSize = bufferSize;
        for (int i = 0; i < bufferSize; i++) {
            freeSpots.add(i);
        }
    }

    public int startProducing() throws InterruptedException {
        int freeSpot;

        lock.lock();
        try {
            while (freeSpots.isEmpty()) {
                waitingProducers.await();
            }

            freeSpot = freeSpots.removeFirst();
        } finally {
            lock.unlock();
        }

        return freeSpot;
    }

    public void endProducing(int index) {
        lock.lock();
        try {
            fullSpots.add(index);
            waitingConsumers.signal();
        } finally {
            lock.unlock();
        }
    }

    public int startConsuming() throws InterruptedException {
        int fullSpot;

        lock.lock();
        try {
            while (fullSpots.isEmpty()) {
                waitingConsumers.await();
            }

            fullSpot = fullSpots.removeFirst();
        } finally {
            lock.unlock();
        }

        return fullSpot;
    }

    public void endConsuming(int index) {
        lock.lock();
        try {
            freeSpots.add(index);
            waitingProducers.signal();
        } finally {
            lock.unlock();
        }
    }

    public int getActiveThreads() {
        return (bufferSize - freeSpots.size() - fullSpots.size());
    }

    public void printActiveThreads() {
        System.out.println("Threads that are doing something: " + this.getActiveThreads());
    }
}
