package com.company.lab8_timeexecutiontests;

public class Parameters {
    public static final int bufferSize = 80;
    public static final int producers = 8;
    public static final int consumers = 8;

    public static final int bufferTime = 210;
    public static final int additionalTime = 210;

    public static final int jobsToDo = 50;
    public static final int testsToAverage = 5;

    // choose one from set {"AO", "Monitor"}
    public static final String AO = "AO";
    public static final String Monitor = "Monitor";
    public static final String toTest = AO;
}
