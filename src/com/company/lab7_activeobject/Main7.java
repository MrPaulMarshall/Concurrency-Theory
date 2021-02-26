package com.company.lab7_activeobject;

import com.company.lab7_activeobject.strictScheduler.StrictScheduler;

import java.util.ArrayList;

public class Main7 {

    public static final int STARVATION_THRESHOLD = Integer.MAX_VALUE;

    static public void runProducerConsumer(int bufferSize, int countProducers, int countConsumers) {
        Buffer buffer = new Buffer(bufferSize);
        Scheduler scheduler = new StrictScheduler();

        ArrayList<Thread> producers = new ArrayList<>();
        ArrayList<Thread> consumers = new ArrayList<>();

        for (int i = 0; i < countProducers; i++) {
            Producer producer = new Producer(scheduler, buffer, bufferSize / 2);
            producers.add(producer);
        }

        for (int i = 0; i < countConsumers; i++) {
            Consumer consumer = new Consumer(scheduler, buffer, bufferSize / 2);
            consumers.add(consumer);
        }

        scheduler.start();
        consumers.forEach(Thread::start);
        producers.forEach(Thread::start);

        while (true) {}
    }
}
