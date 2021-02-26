package com.company.lab11_jcsp_homework;

import org.jcsp.lang.*;

/**
 * Class of little buffer, which contains 1 spot for data
 */
public class Buffer implements CSProcess {

    private final AltingChannelInputInt contIn;
    private final ChannelOutputInt contOut;

    private final AltingChannelInputInt[] prodIns;
    private final ChannelOutputInt[] prodOuts;

    private final ChannelOutputInt[] consOuts;

    /**
     *Creates buffer-object
     * @param contIn
     * @param contOut
     * @param prodIns
     * @param prodOuts
     * @param consOuts
     */
    public Buffer(AltingChannelInputInt contIn, ChannelOutputInt contOut, AltingChannelInputInt[] prodIns, ChannelOutputInt[] prodOuts, ChannelOutputInt[] consOuts) {
        this.contIn = contIn;
        this.contOut = contOut;
        this.prodIns = prodIns;
        this.prodOuts = prodOuts;
        this.consOuts = consOuts;
    }

    @Override
    public void run() {
        int index;
        int data;

        while (true) {
            // wait for producer's index
            index = contIn.read();
            if (index < 0) {
                break;
            }
            prodOuts[index].write(0);
            data = prodIns[index].read();
            contOut.write(1);

            // wait for consumer's index
            index = contIn.read();
            if (index < 0) {
                break;
            }
            consOuts[index].write(data);
            contOut.write(2);
        }
        System.out.println("Buffer ended.");
    }
}
