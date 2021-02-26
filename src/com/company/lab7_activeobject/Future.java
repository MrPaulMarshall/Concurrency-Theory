package com.company.lab7_activeobject;

import java.util.List;

public class Future {

    // WARNING:
    //  as Future objects are designed to be changed only once, they are NOT synchronized
    //  that means, that every non-final field of this class must be volatile, to prevent bugs (cache etc.)
    private final long scheduleTimeStamp;
    private volatile boolean chosenForExecution = false;
    private volatile boolean finished = false;
    private volatile List<Integer> result = null;

    public Future(long scheduleTimeStamp) {
        this.scheduleTimeStamp = scheduleTimeStamp;
    }

    // setters

    public void markChosenForExecution() {
        this.chosenForExecution = true;
    }

    public void markFinished() {
        this.finished = true;
    }

    public void setResult(List<Integer> list) {
        this.result = list;
    }

    // getters

    public long getScheduleTimeStamp() {
        return scheduleTimeStamp;
    }

    public boolean isChosenForExecution() {
        return this.chosenForExecution;
    }

    public boolean isAvailable() {
        return this.finished;
    }

    public List<Integer> getAsync() {
        return this.result;
    }

    synchronized public List<Integer> getSync() throws InterruptedException {
        wait();
        return this.result;
    }

    synchronized public void signalFinished() {
        notify();
    }
}
