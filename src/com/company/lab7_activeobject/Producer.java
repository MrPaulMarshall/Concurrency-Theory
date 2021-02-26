package com.company.lab7_activeobject;

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
        while (true) {
            List<Integer> list = new LinkedList<>();
            int size = random.nextInt(maxValue) + 1;
            for (int i = 0; i < size; i++) {
                list.add(i);
            }

            Future future = this.proxy.produce(buffer, list);
            while (!future.isAvailable()) {
                double c = Math.sin(Math.PI / 5);

                if (TimeConverter.timeSince(future.getScheduleTimeStamp()) > Main7.STARVATION_THRESHOLD) {
                    System.out.println("PRODUCER WAS STARVED");
                    System.exit(1);
                }
            }

            System.out.println("Time to produce: " + TimeConverter.timeSince(future.getScheduleTimeStamp()) + " ms");
        }
    }
}
