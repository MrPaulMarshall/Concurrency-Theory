package com.company.lab7_activeobject;

public interface Scheduler {
    boolean put(MethodRequest mr);
    void run();

    // to make Schedulers able to extend Thread class
    void start();
}
