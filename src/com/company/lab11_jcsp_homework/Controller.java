package com.company.lab11_jcsp_homework;

import org.jcsp.lang.*;

import java.util.LinkedList;

/**
 * Class of main Controller, which connects ready producers/consumers to ready buffers
 * terminates, when all other processes already terminated
 */
public class Controller implements CSProcess {

    // information about buffers and processes

    private final LinkedList<Integer> freeBuffers = new LinkedList<>();
    private final LinkedList<Integer> fullBuffers = new LinkedList<>();

    private final LinkedList<Integer> waitingProducers = new LinkedList<>();
    private final LinkedList<Integer> waitingConsumers = new LinkedList<>();

    // short-named variables for numbers of processes

    private final int p;
    private final int c;
    private final int b;

    // numbers of still working sub-processes -> if all are 0, end the main process

    private int workingBuffers;
    private int workingProducers;
    private int workingConsumers;

    // ends of specific channels

    private final AltingChannelInputInt[] prodIns;

    private final AltingChannelInputInt[] consIns;
    private final ChannelOutputInt[] consOuts;

    private final AltingChannelInputInt[] buffIns;
    private final ChannelOutputInt[] buffOuts;

    /**
     * Creates controller-object
     * @param prodIns
     * @param consIns
     * @param consOuts
     * @param buffIns
     * @param buffOuts
     */
    public Controller(AltingChannelInputInt[] prodIns, AltingChannelInputInt[] consIns, ChannelOutputInt[] consOuts, AltingChannelInputInt[] buffIns, ChannelOutputInt[] buffOuts) {
        this.prodIns = prodIns;
        this.consIns = consIns;
        this.consOuts = consOuts;
        this.buffIns = buffIns;
        this.buffOuts = buffOuts;

        this.workingProducers = this.p = prodIns.length;
        this.workingConsumers = this.c = consIns.length;
        this.workingBuffers = this.b = buffIns.length;

        for (int i = 0; i < this.b; i++) {
            this.freeBuffers.addLast(i);
        }
    }

    /**
     * Gets index of triggered guard
     * Return type of process who wrote to controller
     *  1 - producer
     *  2 - consumer
     *  3 - buffer
     * @param index
     * @return
     */
    private int determineChannelCategory(int index) {
        if (index >= 0 && index < p) return 1;
        if (index >= p && index < p + c) return 2;
        if (index >= p + c && index < p + c + b) return 3;
        return -1;
    }

    /**
     * Transforms index of triggered guard to index of channel-end in specific array
     * @param index
     * @param category
     * @return
     */
    private int getShiftedIndex(int index, int category) {
        switch (category) {
            case 1:
                return index;
            case 2:
                return index - p;
            case 3:
                return index - p - c;
        }
        return -1;
    }

    @Override
    public void run() {

        final Guard[] guards = new Guard[p + c + b];
        if (p >= 0) System.arraycopy(prodIns, 0, guards, 0, p);
        if (c >= 0) System.arraycopy(consIns, 0, guards, p, c);
        if (b >= 0) System.arraycopy(buffIns, 0, guards, p + c, b);

        final Alternative alt = new Alternative(guards);

        int index;
        int category;
        int readValue;

        while (workingBuffers > 0 || workingProducers > 0 || workingConsumers > 0) {

            // wait for some msg and do something about it
            index = alt.select();

            category = determineChannelCategory(index);
            index = getShiftedIndex(index, category);

            switch (category) {
                case 1: // msg from Producer
                    readValue = prodIns[index].read();
                    if (readValue < 0) { // producer ends
                        workingProducers--;
                    } else { // producer asks for buffer
                        waitingProducers.addLast(index);
                    }
                    break;
                case 2: // msg from Consumer
                    consIns[index].read();
                    waitingConsumers.addLast(index);
                    break;
                case 3: // msg from Buffer
                    readValue = buffIns[index].read();
                    if (readValue == 2) { // buffer is empty
                        freeBuffers.add(index);
                    } else if (readValue == 1) { // buffer is full
                        fullBuffers.add(index);
                    }
                    break;
                default:
                    throw new RuntimeException("Controller: undefined guard's index");
            }

            if (workingProducers <= 0) {
                while (!freeBuffers.isEmpty()) {
                    buffOuts[ freeBuffers.removeFirst() ].write(-1);
                    workingBuffers--;
                }

                if (workingBuffers <= 0) {
                    while (!waitingConsumers.isEmpty()) {
                        consOuts[ waitingConsumers.removeFirst() ].write(-1);
                        workingConsumers--;
                    }
                }
            }

            // assign free buffers to waiting producers
            while (!freeBuffers.isEmpty() && !waitingProducers.isEmpty()) {
                buffOuts[ freeBuffers.removeFirst() ].write( waitingProducers.removeFirst() );
            }
            // assign full buffers to waiting consumers
            while (!fullBuffers.isEmpty() && !waitingConsumers.isEmpty()) {
                buffOuts[ fullBuffers.removeFirst() ].write( waitingConsumers.removeFirst() );
            }

        }

        System.out.println("Controller ended.");

    }
}
