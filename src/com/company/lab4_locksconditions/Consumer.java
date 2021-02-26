package com.company.lab4_locksconditions;

import java.util.concurrent.ThreadLocalRandom;

public class Consumer extends Thread {

    private final int id;
    private final Buffer buffer;
    private final int maxCount;
    private final boolean randomize;

    public Consumer(int id, Buffer buffer, int maxCount, boolean randomize) {
        this.buffer = buffer;
        this.maxCount = maxCount;
        this.id = id;
        this.randomize = randomize;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int countItems = maxCount;
                if (randomize) {
                    countItems = ThreadLocalRandom.current().nextInt(1, maxCount + 1);
                }
                buffer.consume(id, countItems);
//                System.out.println(value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
