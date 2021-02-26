package com.company.lab8_timeexecutiontests;

import com.company.lab8_timeexecutiontests.lab5_queues.Main5;
import com.company.lab8_timeexecutiontests.lab7_activeobject.Main7;

public class Main8 {

    public static void runTests() {
        if (Parameters.toTest.equals(Parameters.Monitor)) {
            Main5.runProducerConsumer();
        }
        if (Parameters.toTest.equals(Parameters.AO)) {
            Main7.runProducerConsumer();
        }
    }
}
