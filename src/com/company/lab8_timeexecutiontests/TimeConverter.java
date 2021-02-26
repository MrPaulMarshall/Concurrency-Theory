package com.company.lab8_timeexecutiontests;

public class TimeConverter {

    static public double nanoToMilliSeconds(long nanoSeconds) {
        return (double)nanoSeconds / 1000000;
    }

    public static double timePassed(long start, long end) {
        return nanoToMilliSeconds(end - start);
    }

    public static double timeSince(long start) {
        return timePassed(start, System.nanoTime());
    }
}
