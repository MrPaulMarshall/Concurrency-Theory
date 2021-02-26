package com.company.lab8_timeexecutiontests.lab5_queues;

import com.company.lab8_timeexecutiontests.Parameters;
import com.company.lab8_timeexecutiontests.TimeConverter;

import java.util.concurrent.ThreadLocalRandom;

public class Consumer extends Thread {

    private final int id;
    private final Buffer buffer;
    private final int maxCount;

    public Consumer(int id, Buffer buffer, int maxCount) {
        this.id = id;
        this.buffer = buffer;
        this.maxCount = maxCount;
    }

    @Override
    public void run() {
        long threadStart = System.nanoTime();

        while (true) {
            try {
                int countItems = ThreadLocalRandom.current().nextInt(1, maxCount + 1);
                buffer.consume(id, countItems);

                long startTime = System.nanoTime();

                while (TimeConverter.timeSince(startTime) < Parameters.additionalTime) {
                    double dummyValue = Math.tan(Math.PI / 8);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
