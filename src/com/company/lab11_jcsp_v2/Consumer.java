package com.company.lab11_jcsp_v2;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelInputInt;
import org.jcsp.lang.One2OneChannelInt;

/**
 * Consumer class: reads ints from input channel, displays them,
 * then
 * terminates when a negative value is read.
 */
public class Consumer implements CSProcess {
    final private One2OneChannelInt in;

    public Consumer(final One2OneChannelInt in) {
        this.in = in;
    } // constructor

    public void run() {
        int item;
        for (int k = 0; k < 100; k++) {
            ChannelInputInt channelInputInt = in.in();
            item = channelInputInt.read();
            if (item < 0)
                break;
            System.out.println("[CONSUMER] " + item);
        } // for
        System.out.println("[CONSUMER] ended.");
    } // run
} // class Consumer
