package com.company.lab7_activeobject;

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
        while (true) {
            int howMany = random.nextInt(maxValue) + 1;

            Future future = this.proxy.consume(buffer, howMany);
            while (!future.isAvailable()) {
                double c = Math.cos(Math.PI / 7);

                if (TimeConverter.timeSince(future.getScheduleTimeStamp()) > Main7.STARVATION_THRESHOLD) {
                    System.out.println("CONSUMER WAS STARVED");
                    System.exit(1);
                }
            }

            List<Integer> list = future.getAsync();

            System.out.println("Time to consume: " + TimeConverter.timeSince(future.getScheduleTimeStamp()) + " ms");
            System.out.println(list);

        }
    }
}
