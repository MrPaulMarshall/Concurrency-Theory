package com.company.lab6_manyprocessesinbuffor;

import java.util.ArrayList;

public class Main6 {

    static public void runProducerConsumer(int bufferSize, int countProducers, int countConsumers) {
        Buffer buffer = new Buffer(bufferSize);
        Monitor monitor = new Monitor(bufferSize);

        ArrayList<Thread> producers = new ArrayList<>();
        ArrayList<Thread> consumers = new ArrayList<>();

        for (int i = 0; i < countProducers; i++) {
            Producer producer = new Producer(monitor, buffer);
            producers.add(producer);
        }

        for (int i = 0; i < countConsumers; i++) {
            Consumer consumer = new Consumer(monitor, buffer);
            consumers.add(consumer);
        }

        consumers.forEach(Thread::start);
        producers.forEach(Thread::start);

        while (true) {
        }
    }

}
