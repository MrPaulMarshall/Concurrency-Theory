package com.company.lab6_manyprocessesinbuffor;

public class Producer extends Thread {

    private final Monitor monitor;
    private final Buffer buffer;
    private int spot;

    public Producer(Monitor monitor, Buffer buffer) {
        this.monitor = monitor;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // take ticket
                this.spot = monitor.startProducing();

                // do action
                buffer.produce(this.spot);

                // diagnose situation
                System.out.println("PRODUCER: (Active threads, Errors): (" + monitor.getActiveThreads() + ", "
                        + buffer.getCountErrors() + ")");

                // simulate long execution time
                Thread.sleep(400);

                // give ticket back
                monitor.endProducing(this.spot);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
