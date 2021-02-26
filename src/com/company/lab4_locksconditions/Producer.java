package com.company.lab4_locksconditions;

import java.util.concurrent.ThreadLocalRandom;

public class Producer extends Thread {

    private final int id;
    private final Buffer buffer;
    private final int maxCount;
    private final boolean randomize;

    public Producer(int id, Buffer buffer, int maxCount, boolean randomize) {
        this.id = id;
        this.buffer = buffer;
        this.maxCount = maxCount;
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
                buffer.produce(id, countItems);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
