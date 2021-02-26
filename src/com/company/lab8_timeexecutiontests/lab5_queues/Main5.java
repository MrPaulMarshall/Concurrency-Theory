package com.company.lab8_timeexecutiontests.lab5_queues;

import com.company.lab8_timeexecutiontests.Parameters;
import com.company.lab8_timeexecutiontests.TimeConverter;

import java.util.ArrayList;
import java.util.Arrays;

import static com.company.lab8_timeexecutiontests.Parameters.bufferSize;

public class Main5 {

    static private int testsCounter = 0;

    static private long start;
    static private final long[] elapsedTimes = new long[Parameters.testsToAverage];

    // assumption: bufferSize == 2 * M
    static public void runProducerConsumer() {

        Buffer buffer = new Buffer(bufferSize);

        ArrayList<Thread> producers = new ArrayList<>();
        ArrayList<Thread> consumers = new ArrayList<>();

        for (int i = 0; i < Parameters.producers; i++) {
            Producer producer = new Producer(i, buffer, bufferSize / 2);
            producers.add(producer);
        }

        for (int i = 0; i < Parameters.consumers; i++) {
            Consumer consumer = new Consumer(i, buffer, bufferSize / 2);
            consumers.add(consumer);
        }

        start = System.currentTimeMillis();

        consumers.forEach(Thread::start);
        producers.forEach(Thread::start);
    }

    static public void testFinished() {
        long finish = System.currentTimeMillis();
        elapsedTimes[testsCounter] = finish - start;
        start = finish;

        testsCounter++;
        if (testsCounter >= Parameters.testsToAverage) {
            double avgTime = Arrays.stream(elapsedTimes).average().orElse(0);

            // TODO: print it to file or something
            System.out.println("Monitor: " + avgTime + " ms");
            System.exit(0);
        }
    }
}
