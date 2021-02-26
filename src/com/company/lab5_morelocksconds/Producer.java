package com.company.lab5_morelocksconds;

import java.util.concurrent.ThreadLocalRandom;

public class Producer extends Thread {

    private final int id;
    private final Buffer buffer;
    private final int minCount;
    private final int maxCount;
    private final boolean randomize;

    public Producer(int id, Buffer buffer, int minCount, int maxCount, boolean randomize) {
        this.id = id;
        this.buffer = buffer;
        this.minCount = minCount;
        this.maxCount = maxCount;
        this.randomize = randomize;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int countItems = maxCount;
                if (randomize) {
                    countItems = ThreadLocalRandom.current().nextInt(minCount, maxCount + 1);
                }
                buffer.produce(id, countItems);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
