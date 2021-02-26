package com.company.lab8_timeexecutiontests.lab7_activeobject;

import com.company.lab8_timeexecutiontests.Parameters;
import com.company.lab8_timeexecutiontests.lab7_activeobject.strictScheduler.StrictScheduler;

import java.util.ArrayList;
import java.util.Arrays;

import static com.company.lab8_timeexecutiontests.Parameters.bufferSize;

public class Main7 {

    static public final int STARVATION_THRESHOLD = Integer.MAX_VALUE;

    static private int testsCounter = 0;

    static private long start;
    static private final long[] elapsedTimes = new long[Parameters.testsToAverage];

    static public void runProducerConsumer() {

        Buffer buffer = new Buffer(bufferSize);
        Scheduler scheduler = new StrictScheduler();

        ArrayList<Thread> producers = new ArrayList<>();
        ArrayList<Thread> consumers = new ArrayList<>();

        for (int i = 0; i < Parameters.producers; i++) {
            Producer producer = new Producer(scheduler, buffer, bufferSize / 2);
            producers.add(producer);
        }

        for (int i = 0; i < Parameters.consumers; i++) {
            Consumer consumer = new Consumer(scheduler, buffer, bufferSize / 2);
            consumers.add(consumer);
        }

        start = System.currentTimeMillis();

        scheduler.start();
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
            System.out.println("ActiveObject: " + avgTime + " ms");
            System.exit(0);
        }
    }
}
