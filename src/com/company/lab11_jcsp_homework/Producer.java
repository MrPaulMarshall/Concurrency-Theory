package com.company.lab11_jcsp_homework;

import org.jcsp.lang.*;

/**
 * Producer class:  100 times produces random integer, asks controller for free buffer
 * and sends that integer to buffer
 * The random integers are in a given range [start...start+100)
 */
public class Producer implements CSProcess {

    private final ChannelOutputInt contOut;

    private final AltingChannelInputInt[] buffIns;
    private final ChannelOutputInt[] buffOuts;

    private final int startValue;
    private final int numberOfProductions;

    /**
     * Creates producer-object
     * @param contOut
     * @param buffIns
     * @param buffOuts
     * @param startValue
     * @param numberOfProductions
     */
    public Producer(ChannelOutputInt contOut, AltingChannelInputInt[] buffIns, ChannelOutputInt[] buffOuts, int startValue, int numberOfProductions) {
        this.contOut = contOut;
        this.buffIns = buffIns;
        this.buffOuts = buffOuts;
        this.startValue = startValue;
        this.numberOfProductions = numberOfProductions;
    }

    @Override
    public void run() {
        final Alternative alt = new Alternative(buffIns);

        int index;
        int item;
        for (int k = 0; k < numberOfProductions; k++) {
            item = (int) (Math.random() * numberOfProductions) + 1 + startValue;
//            item = k;
            contOut.write(0);

            index = alt.select();
            buffIns[index].read();
            buffOuts[index].write(item);
        }
        contOut.write(-1);
        System.out.println("Producer ended.");
    }
}
