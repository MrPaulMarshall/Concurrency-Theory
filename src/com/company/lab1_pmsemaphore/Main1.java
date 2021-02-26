package com.company.lab1_pmsemaphore;

import java.util.ArrayList;

public class Main1 {

    public static void runCounter(int n) {
        Counter obj = new Counter(0);
        PMSemaphore semaphore = new PMSemaphore();

        ArrayList<Thread> threads = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            threads.add(new Thread(() -> {
                try {
                    semaphore.myWait();
                    obj.increment();
                    semaphore.myFree();
                } catch (InterruptedException exp) {
                    exp.printStackTrace();
                }
            }));
            threads.add(new Thread(() -> {
                try {
                    semaphore.myWait();
                    obj.decrement();
                    semaphore.myFree();
                } catch (InterruptedException exp) {
                    exp.printStackTrace();
                }
            }));
        }

        threads.forEach(Thread::start);

        threads.forEach(t -> {
            try {
                t.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        obj.print();
    }

}
