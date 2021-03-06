package com.company.lab11_jcsp_v3;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelOutputInt;
import org.jcsp.lang.One2OneChannelInt;

/**
 * Producer class: produces 100 random integers and sends them on
 * output channel, then sends -1 and terminates.
 * The random integers are in a given range [start...start+100)
 */
public class Producer implements CSProcess {
    final private One2OneChannelInt channel;
    private int start;

    // constructor
    public Producer(final One2OneChannelInt out, int start) {
        channel = out;
        this.start = start;
    }
    // end constructor

    public void run() {
        int item;
        ChannelOutputInt channelOutput = channel.out();
        for (int k = 0; k < 100; k++) {
            item = (int) (Math.random() * 100) + 1 + start;
            channelOutput.write(item);
            System.out.println("[PRODUCER] " + k);
        } // for
        System.out.println("[PRODUCER] ended.");
    } // run
} // class Producer
