package com.company.lab11_jcsp_homework;

import org.jcsp.lang.*;

/**
 * Consumer class: asks controller for full buffer,
 * reads data from buffer that calls to it, displays it
 * terminates when a direct msg from controller is read.
 */
public class Consumer implements CSProcess {

    private final AltingChannelInputInt contIn;
    private final ChannelOutputInt contOut;

    private final AltingChannelInputInt[] buffIns;

    /**
     * Creates consumer-object
     * @param contIn
     * @param contOut
     * @param buffIns
     */
    public Consumer(AltingChannelInputInt contIn, ChannelOutputInt contOut, AltingChannelInputInt[] buffIns) {
        this.contIn = contIn;
        this.contOut = contOut;
        this.buffIns = buffIns;
    }

    @Override
    public void run() {
        final Guard[] guards = new Guard[buffIns.length + 1];
        System.arraycopy(buffIns, 0, guards, 0, buffIns.length);
        guards[buffIns.length] = contIn;

        final Alternative alt = new Alternative(guards);

        int index;
        int item;
        while (true) {
            contOut.write(0);

            index = alt.select();
            if (index < buffIns.length) {
                item = buffIns[index].read();
//                System.out.println(item);
            } else {
                contIn.read();
                break;
            }
        }
        System.out.println("Consumer ended.");
    }
}
