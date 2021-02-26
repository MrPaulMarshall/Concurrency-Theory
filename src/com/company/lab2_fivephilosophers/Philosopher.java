package com.company.lab2_fivephilosophers;

import com.company.lab1_pmsemaphore.PMSemaphore;

public class Philosopher extends Thread {

    private int i;
    private PMSemaphore leftFork;
    private PMSemaphore rightFork;
    private PMSemaphore lackey;

    public Philosopher(int i, PMSemaphore leftFork, PMSemaphore rightFork, PMSemaphore lackey) {
        this.i = i;
        if (leftFork == null || rightFork == null) throw new IllegalArgumentException("Forks must exist");
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.lackey = lackey;
    }

    public void eat() {
        System.out.println("Philosopher " + i + " eats");
    }

    public void think() {
        System.out.println("Philosopher " + i + " thinks");
    }

    public void run() {
        try {
            while (true) {
                think();
                if (lackey != null) lackey.myWait();
                leftFork.myWait();
                rightFork.myWait();
                eat();
                leftFork.myFree();
                rightFork.myFree();
                if (lackey != null) lackey.myFree();
            }
        } catch (InterruptedException exp) {
            System.out.println("Philosopher " + i + " exits the room");
        }
    }

}
