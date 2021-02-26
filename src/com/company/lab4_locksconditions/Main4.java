package com.company.lab4_locksconditions;

import java.util.ArrayList;

public class Main4 {

    // assumption: bufferSize == 2 * M
    static public void runProducerConsumer(int bufferSize, int countProducers, int countConsumers) {
        Buffer buffer = new Buffer(bufferSize);

        ArrayList<Thread> producers = new ArrayList<>();
        ArrayList<Thread> consumers = new ArrayList<>();

        for (int i = 0; i < countProducers; i++) {
            Producer producer = new Producer(i, buffer, bufferSize / 2, true);
            producers.add(producer);
        }

//        consumers.add(new Consumer(0, buffer, bufferSize / 2, false));
        for (int i = 1; i < countConsumers; i++) {
            Consumer consumer = new Consumer(i, buffer, bufferSize / 2, true);
            consumers.add(consumer);
        }

        consumers.forEach(Thread::start);
        producers.forEach(Thread::start);

        while (true) {
        }
    }

}
