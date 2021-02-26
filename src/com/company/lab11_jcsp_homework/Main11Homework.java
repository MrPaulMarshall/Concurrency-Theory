package com.company.lab11_jcsp_homework;

import org.jcsp.lang.*;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Main11Homework {

    /**
     * Simulate producer-consumer scenario using JCSP and diffuse buffer
     * @param producers
     * @param consumers
     * @param buffers
     */
    public static void runJCSP(int producers, int consumers, int buffers, int numberOfProductions) {

        // CHANNELS

        // Channels: producer - controller
        final One2OneChannelInt[] prodToContChannels = new One2OneChannelInt[producers];
        for (int i = 0; i < producers; i++) {
            prodToContChannels[i] = Channel.one2oneInt();
        }

        // Channels: consumer - controller
        final One2OneChannelInt[] consToContChannels = new One2OneChannelInt[consumers];
        final One2OneChannelInt[] contToConsChannels = new One2OneChannelInt[consumers];
        for (int i = 0; i < consumers; i++) {
            consToContChannels[i] = Channel.one2oneInt();
            contToConsChannels[i] = Channel.one2oneInt();
        }

        // Channels: buffer - controller
        final One2OneChannelInt[] buffToContChannels = new One2OneChannelInt[buffers];
        final One2OneChannelInt[] contToBuffChannels = new One2OneChannelInt[buffers];
        for (int i = 0; i < buffers; i++) {
            buffToContChannels[i] = Channel.one2oneInt();
            contToBuffChannels[i] = Channel.one2oneInt();
        }

        // Channels: producer - buffer
        final One2OneChannelInt[][] prodToBuffChannels = new One2OneChannelInt[producers][buffers];
        final One2OneChannelInt[][] buffToProdChannels = new One2OneChannelInt[producers][buffers];
        for (int i = 0; i < producers; i++) {
            for (int j = 0; j < buffers; j++) {
                prodToBuffChannels[i][j] = Channel.one2oneInt();
                buffToProdChannels[i][j] = Channel.one2oneInt();
            }
        }

        // Channels: consumer - buffer
        final One2OneChannelInt[][] buffToConsChannels = new One2OneChannelInt[consumers][buffers];
        for (int i = 0; i < consumers; i++) {
            for (int j = 0; j < buffers; j++) {
                buffToConsChannels[i][j] = Channel.one2oneInt();
            }
        }

        // PROCESSES

        CSProcess[] processes = new CSProcess[1 + buffers + producers + consumers];

        // Controller
        {
            AltingChannelInputInt[] prodIns = new AltingChannelInputInt[producers];
            for (int i = 0; i < producers; i++) {
                prodIns[i] = prodToContChannels[i].in();
            }

            AltingChannelInputInt[] consIns = new AltingChannelInputInt[consumers];
            ChannelOutputInt[] consOuts = new ChannelOutputInt[consumers];
            for (int i = 0; i < consumers; i++) {
                consIns[i] = consToContChannels[i].in();
                consOuts[i] = contToConsChannels[i].out();
            }

            AltingChannelInputInt[] buffIns = new AltingChannelInputInt[buffers];
            ChannelOutputInt[] buffOuts = new ChannelOutputInt[buffers];
            for (int i = 0; i < buffers; i++) {
                buffIns[i] = buffToContChannels[i].in();
                buffOuts[i] = contToBuffChannels[i].out();
            }

            processes[0] = new Controller(
                    prodIns,
                    consIns,
                    consOuts,
                    buffIns,
                    buffOuts
            );
        }

        // Buffers
        for (int i = 0; i < buffers; i++) {
            AltingChannelInputInt[] prodIns = new AltingChannelInputInt[producers];
            ChannelOutputInt[] prodOuts = new ChannelOutputInt[producers];
            for (int j = 0; j < producers; j++) {
                prodIns[j] = prodToBuffChannels[j][i].in();
                prodOuts[j] = buffToProdChannels[j][i].out();
            }

            ChannelOutputInt[] consOuts = new ChannelOutputInt[consumers];
            for (int j = 0; j < consumers; j++) {
                consOuts[j] = buffToConsChannels[j][i].out();
            }

            processes[1 + i] = new Buffer(
                    contToBuffChannels[i].in(),
                    buffToContChannels[i].out(),
                    prodIns,
                    prodOuts,
                    consOuts
            );
        }

        // Producers
        for (int i = 0; i < producers; i++) {
            AltingChannelInputInt[] buffIns = new AltingChannelInputInt[buffers];
            ChannelOutputInt[] buffOuts = new ChannelOutputInt[buffers];
            for (int j = 0; j < buffers; j++) {
                buffIns[j] = buffToProdChannels[i][j].in();
                buffOuts[j] = prodToBuffChannels[i][j].out();
            }

            processes[1 + buffers + i] = new Producer(
                    prodToContChannels[i].out(),
                    buffIns,
                    buffOuts,
                    i,
                    numberOfProductions
            );
        }

        // Consumers
        for (int i = 0; i < consumers; i++) {
            AltingChannelInputInt[] buffIns = new AltingChannelInputInt[buffers];
            for (int j = 0; j < buffers; j++) {
                buffIns[j] = buffToConsChannels[i][j].in();
            }

            processes[1 + buffers + producers + i] = new Consumer(
                    contToConsChannels[i].in(),
                    consToContChannels[i].out(),
                    buffIns
            );
        }

        Parallel parallel = new Parallel(processes);

        System.out.println("START");
        try {
            parallel.run();
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Main Thread Ends.");

    }

    /**
     * Executes tests - how much time is needed to perform specified numbers of actions
     * @param producers
     * @param consumers
     * @param buffers
     * @param maxNumOfProductions
     * @param dataPoints
     */
    public static void runTests(int producers, int consumers, int buffers, int maxNumOfProductions, int dataPoints) {
        int delta = maxNumOfProductions / dataPoints;
        if (delta < 1) {
            throw new RuntimeException("Too many data points / too little 'maxNumOfProductions'");
        }

        HashMap<Integer, Long> results = new LinkedHashMap<>();

        long start, end;
        for (int num = delta; num < maxNumOfProductions; num += delta) {
            start = System.currentTimeMillis();
            runJCSP(producers, consumers, buffers, num);
            end = System.currentTimeMillis();

            results.put(num, end - start);
        }

        results.forEach((num, time) -> System.out.println(num + " -> " + time + " ms"));
    }

}
