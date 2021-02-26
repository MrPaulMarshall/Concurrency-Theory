package com.company.lab3_monitors;

import java.util.ArrayList;

public class Main3 {

    static public void runProducerConsumer(int bufferSize, int countProducers, int countConsumers) {
        Buffer buffer = new Buffer(bufferSize);

        ArrayList<Thread> producers = new ArrayList<>();
        ArrayList<Thread> consumers = new ArrayList<>();

        for (int i = 0; i < countProducers; i++) {
            Producer producer = new Producer(buffer, i);
            producers.add(producer);
        }

        for (int i = 0; i < countConsumers; i++) {
            Consumer consumer = new Consumer(buffer);
            consumers.add(consumer);
        }

        consumers.forEach(Thread::start);
        producers.forEach(Thread::start);

        while (true) {
        }
    }

}
