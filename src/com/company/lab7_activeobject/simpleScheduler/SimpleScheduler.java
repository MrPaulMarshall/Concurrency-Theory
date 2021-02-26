package com.company.lab7_activeobject.simpleScheduler;

import com.company.lab7_activeobject.MethodRequest;
import com.company.lab7_activeobject.Scheduler;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleScheduler extends Thread implements Scheduler {

    private final SimpleActivateQueue queue = new SimpleActivateQueue();
    private final Lock lock = new ReentrantLock();
    private final Condition nonEmptyQueue = lock.newCondition();

    public boolean put(MethodRequest mr) {
        boolean flag = true;
        lock.lock();
        try {
            queue.put(mr);
            nonEmptyQueue.signal();
        } catch (Exception e) {
            flag = false;
            queue.remove(mr);
        } finally {
            lock.unlock();
        }
        return flag;
    }

    @Override
    public void run() {
        MethodRequest[] requests = new MethodRequest[2];

        while (true) {
            lock.lock();
            try {
                while (queue.isEmpty()) {
                    nonEmptyQueue.await();
                }

                requests[0] = queue.getFirstProduceRequest();
                requests[1] = queue.getFirstConsumeRequest();

                for (int i = 0; i < 2; i++) {
                    if (requests[i] != null && requests[i].guard()) {
                        if (queue.remove(requests[i])) {
                            requests[i].call();
                        } else {
                            throw new IllegalStateException("REQUEST WASN'T PROPERLY REMOVED");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
