package com.company.lab8_timeexecutiontests.lab5_queues;

import com.company.lab8_timeexecutiontests.Parameters;
import com.company.lab8_timeexecutiontests.TimeConverter;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {

    // buffer, its size and count of the stored items
    private final LinkedList<Integer> items = new LinkedList<>();
    private final int size;
    private int count;

    // lock and conditions
    private final ReentrantLock lock = new ReentrantLock();

    private final Condition firstProdCond = lock.newCondition();
    private final Condition firstConsCond = lock.newCondition();
    private final Condition restProdCond = lock.newCondition();
    private final Condition restConsCond = lock.newCondition();

    // variables to determine proper order of execution
    private boolean firstProdExists = false;
    private boolean firstConsExists = false;

    // variables used to check execution of the program
    private int firstProdCount = 0;
    private int firstConsCount = 0;
    private int restProdCount = 0;
    private int restConsCount = 0;

    public int jobsDone = 0;

    // ---

    public Buffer(int size) {
        this.size = size;
        this.count = 0;
    }

    void produce(int producerId, int countItems) throws InterruptedException {
        lock.lock();
        try {
//            System.out.println("Producer " + producerId + " enters: (" + firstProdCount + ", " + restProdCount + ")\n");

            while (firstProdExists) {
                restProdCount++;
                restProdCond.await();
                restProdCount--;
            }

            while (count + countItems > size) {
                firstProdExists = true;
                firstProdCount++;

                firstProdCond.await();

                firstProdExists = false;
                firstProdCount--;
            }

            long startTime = System.nanoTime();

            count += countItems;
            for (int i = 0; i < countItems; i++) {
                items.add(i);
            }

            while (TimeConverter.timeSince(startTime) < Parameters.bufferTime) {
                double dummyValue = Math.tan(Math.PI / 5);
            }

            jobDone();

//            System.out.println("Producer " + producerId + " ends: (" + firstProdCount + ", " + restProdCount + ")");
//            System.out.println("PRODUCED " + countItems + " items\n");

            restProdCond.signal();
            firstConsCond.signal();
        } finally {
            lock.unlock();
        }
    }

    void consume(int consumerId, int countItems) throws InterruptedException {
        lock.lock();
        try {
//            System.out.println("Consumer " + consumerId + " enters: (" + firstConsCount + ", " + restConsCount + ")\n");

            while (firstConsExists) {
                restConsCount++;
                restConsCond.await();
                restConsCount--;
            }

            while (count < countItems) {
                firstConsExists = true;
                firstConsCount++;

                firstConsCond.await();

                firstConsExists = false;
                firstConsCount--;
            }

            long startTime = System.nanoTime();

            List<Integer> consumedItems = new LinkedList<>();
            count -= countItems;
            for (int i = 0; i < countItems; i++) {
                consumedItems.add(items.removeFirst());
            }

            while (TimeConverter.timeSince(startTime) < Parameters.bufferTime) {
                double dummyValue = Math.tan(Math.PI / 5);
            }

            jobDone();

//            System.out.println("Consumer " + consumerId + " ends: (" + firstConsCount + ", " + restConsCount + ")");
//            System.out.println("CONSUMED " + countItems + " items: ");
//            System.out.println(consumedItems);
//            System.out.println("\n");

            restConsCond.signal();
            firstProdCond.signal();
        } finally {
            lock.unlock();
        }
    }

    private void jobDone() {
        jobsDone++;
        if (jobsDone >= Parameters.jobsToDo) {
            jobsDone = 0;
            Main5.testFinished();
        }
    }
}
