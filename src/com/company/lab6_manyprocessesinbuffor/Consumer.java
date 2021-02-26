package com.company.lab6_manyprocessesinbuffor;

public class Consumer extends Thread {

    private final Monitor monitor;
    private final Buffer buffer;
    private int spot;

    public Consumer(Monitor monitor, Buffer buffer) {
        this.monitor = monitor;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // take ticket
                this.spot = monitor.startConsuming();


                // do action
                buffer.consume(this.spot);

                // diagnose situation
                System.out.println("CONSUMER: (Active threads, Errors): (" +
                        monitor.getActiveThreads() + ", " + buffer.getCountErrors() + ")");

                // simulate long execution time
                Thread.sleep(400);

                // give ticket back
                monitor.endConsuming(this.spot);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
