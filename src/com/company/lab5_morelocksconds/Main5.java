package com.company.lab5_morelocksconds;

import java.util.ArrayList;

public class Main5 {

   // assumption: bufferSize == 2 * M
    static public void runProducerConsumer(int bufferSize, int countProducers, int countConsumers) {
        Buffer buffer = new Buffer(bufferSize);

        ArrayList<Thread> producers = new ArrayList<>();
        ArrayList<Thread> consumers = new ArrayList<>();

        for (int i = 0; i < countProducers; i++) {
//            Producer producer = new Producer(i, buffer, 1, bufferSize / 2, true);
            Producer producer = new Producer(i, buffer, 1, 10, true);
            producers.add(producer);
        }

        for (int i = 0; i < countConsumers; i++) {
//            Consumer consumer = new Consumer(i, buffer, 1, bufferSize / 2, true);
            Consumer consumer = new Consumer(i, buffer, 490, 500, true);
            consumers.add(consumer);
        }

        consumers.forEach(Thread::start);
        producers.forEach(Thread::start);

        while (true) {
        }
    }

}
