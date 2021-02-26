package com.company.lab8_timeexecutiontests.lab7_activeobject;

import com.company.lab8_timeexecutiontests.Parameters;
import com.company.lab8_timeexecutiontests.TimeConverter;

import java.util.List;
import java.util.Random;

public class Consumer extends Thread {

    private final Proxy proxy;
    private final Buffer buffer;
    private final int maxValue;
    private static final Random random = new Random();

    public Consumer(Scheduler scheduler, Buffer buffer, int maxValue) {
        this.proxy = new Proxy(scheduler);
        this.buffer = buffer;
        this.maxValue = maxValue;
    }

    @Override
    public void run() {
        long threadStart = System.nanoTime();

        while (true) {
            int howMany = random.nextInt(maxValue) + 1;

            long start = System.nanoTime();
            Future future = this.proxy.consume(buffer, howMany);

            while (TimeConverter.timeSince(start) < Parameters.additionalTime) {
                double dummyValue = Math.cos(Math.PI / 7);
            }

            while (!future.isAvailable()) { }

//            System.out.println("Time to consume: " + TimeConverter.timeSince(start) + " ms");

            List<Integer> list = future.getAsync();
//            System.out.println(list);

        }
    }
}
