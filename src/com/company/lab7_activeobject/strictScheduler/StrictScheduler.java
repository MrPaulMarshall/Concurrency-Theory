package com.company.lab7_activeobject.strictScheduler;

import com.company.lab7_activeobject.MethodRequest;
import com.company.lab7_activeobject.ConsumeMethodRequest;
import com.company.lab7_activeobject.ProduceMethodRequest;
import com.company.lab7_activeobject.Scheduler;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StrictScheduler extends Thread implements Scheduler {

    private final StrictActivateQueue queue = new StrictActivateQueue();
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
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return flag;
    }

    @Override
    public void run() {
        MethodRequest request;
        MethodRequest firstReq;
        ProduceMethodRequest firstProd;
        ConsumeMethodRequest firstCons;

        while (true) {
            request = null;

            lock.lock();
            try {
                while (queue.isEmpty()) {
                    nonEmptyQueue.await();
                }

                firstReq = queue.getFirstRequest();
                firstProd = queue.getFirstProduceRequest();
                firstCons = queue.getFirstConsumeRequest();

                if (firstProd == firstReq) {
                    if (firstProd.guard()) {
                        request = firstProd;
                    } else {
                        if (firstCons != null && firstCons.guard()) {
                            request = firstCons;
                        }
                    }
                } else if (firstCons == firstReq) {
                    if (firstCons.guard()) {
                        request = firstCons;
                    } else {
                        if (firstProd != null && firstProd.guard()) {
                            request = firstProd;
                        }
                    }
                }

                if (request != null) {
                    if (queue.remove(request)) {
                        request.call();
                    } else {
                        throw new IllegalStateException("SCHEDULER ERROR");
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
