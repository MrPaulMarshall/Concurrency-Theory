package com.company.lab7_activeobject;

import java.util.List;

public class Proxy {

    private final Scheduler scheduler;

    public Proxy(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public Future produce(Buffer buffer, List<Integer> list) {
        Future future = new Future(System.nanoTime());
        ProduceMethodRequest mr = new ProduceMethodRequest(future, buffer, list);
        boolean flag = this.scheduler.put(mr);
        if (flag) return future;
        return null;
    }

    public Future consume(Buffer buffer, int howMany) {
        Future future = new Future(System.nanoTime());
        ConsumeMethodRequest mr = new ConsumeMethodRequest(future, buffer, howMany);
        boolean flag = this.scheduler.put(mr);
        if (flag) return future;
        return null;
    }
}
