package com.company.lab3_monitors;

public class Producer extends Thread {

    private Buffer buffer;
    private int value;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
        this.value = 0;
    }

    public Producer(Buffer buffer, int value) {
        this.buffer = buffer;
        this.value = value;
    }

    @Override
    public void run() {
        while (true) {
            try {
                buffer.produce(value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
