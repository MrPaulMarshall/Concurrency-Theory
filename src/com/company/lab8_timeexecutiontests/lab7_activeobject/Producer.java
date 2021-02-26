package com.company.lab8_timeexecutiontests.lab7_activeobject;

import com.company.lab8_timeexecutiontests.Parameters;
import com.company.lab8_timeexecutiontests.TimeConverter;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Producer extends Thread {

    private final Proxy proxy;
    private final Buffer buffer;
    private final int maxValue;
    private static final Random random = new Random();

    public Producer(Scheduler scheduler, Buffer buffer, int maxValue) {
        this.proxy = new Proxy(scheduler);
        this.buffer = buffer;
        this.maxValue = maxValue;
    }

    @Override
    public void run() {
        long threadStart = System.nanoTime();

        while (true) {
            List<Integer> list = new LinkedList<>();
            int size = random.nextInt(maxValue) + 1;
            for (int i = 0; i < size; i++) {
                list.add(i);
            }

            long start = System.nanoTime();
            Future future = this.proxy.produce(buffer, list);

            while (TimeConverter.timeSince(start) < Parameters.additionalTime) {
                double dummyValue = Math.sin(Math.PI / 6);
            }

            while (!future.isAvailable()) {}

//            System.out.println("Time to produce: " + TimeConverter.timeSince(start) + " ms");
        }
    }
}
