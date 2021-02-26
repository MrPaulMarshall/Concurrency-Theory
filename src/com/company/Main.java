package com.company;

import com.company.lab11_jcsp_homework.Main11Homework;
import com.company.lab11_jcsp_v2.Main11V2;

public class Main {

    public static void main(String[] args) {
        // lab 1 -- semaphores
//        Main1.runCounter(1000);

        // lab 2 -- 5 philosophers
//        Main2.runFivePhilosophers(5, true);

        // lab 3 -- monitors
//        Main3.runProducerConsumer(1, 2, 1);

        // lab 4 -- locks & conditions
//        Main4.runProducerConsumer(80, 2, 4);

        // lab 5 -- locks & conditions - more
//        Main5.runProducerConsumer(1000, 10, 10);

        // lab 6 -- many processes in buffer
//        Main6.runProducerConsumer(30, 10, 10);

        // lab 7 -- producer-consumer using Active Object design pattern
//        Main7.runProducerConsumer(100, 20, 20);

        // lab 8 -- execution time tests
//        Main8.runTests();

        // lab 11 -- producer-consumer in JCSP
        Main11Homework.runJCSP(10, 10, 12, 10000);
//        Main11Homework.runTests(10, 10, 12, 10000, 10);
    }
}
