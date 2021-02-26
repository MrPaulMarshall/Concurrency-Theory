package com.company.lab1_pmsemaphore;

public class PMSemaphore {
    private boolean free;

    public PMSemaphore() {
        this.free = true;
    }

    public synchronized void myFree() {
        free = true;
        notify();
    }

    public synchronized void myWait() throws InterruptedException {
        while (!free) {
            wait();
        }
        free = false;
        notify();
    }
}
