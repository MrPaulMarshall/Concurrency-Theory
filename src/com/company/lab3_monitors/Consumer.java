package com.company.lab3_monitors;

public class Consumer extends Thread {

    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int value = buffer.consume();
                System.out.println(value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
