package com.company.lab7_activeobject;

public class TimeConverter {

    public static double nanoToMilliseconds(long nano) {
        return (double)nano / 1000000;
    }

    public static double timePassed(long start, long end) {
        return nanoToMilliseconds(end - start);
    }

    public static double timeSince(long start) {
        return timePassed(start, System.nanoTime());
    }
}
