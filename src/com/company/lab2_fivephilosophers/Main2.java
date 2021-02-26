package com.company.lab2_fivephilosophers;

import com.company.lab1_pmsemaphore.PMSemaphore;

public class Main2 {

    public static void runFivePhilosophers(int n, boolean withLackey) {

        // create lackey semaphore if users wants to
        PMSemaphore lackey;
        if (withLackey) {
            lackey = new PMSemaphore();
        } else {
            lackey = null;
        }

        // create n forks (semaphores)
        PMSemaphore[] forks = new PMSemaphore[n];
        for (int i = 0; i < n; i++) {
            forks[i] = new PMSemaphore();
        }

        // create n philosophers - threads to be run
        Philosopher[] philosophers = new Philosopher[n];
        for (int i = 0; i < n; i++) {
            philosophers[i] = new Philosopher(i, forks[i], forks[(i + 1) % n], lackey);
        }

        // run all philosophers
        for (int i = 0; i < n; i++) {
            philosophers[i].start();
        }

        // wait indefinitely
        while (true) {
        }
    }

}
