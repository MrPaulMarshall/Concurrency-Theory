package com.company.lab11_jcsp_v2;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Channel;
import org.jcsp.lang.One2OneChannelInt;
import org.jcsp.lang.Parallel;

/**
 * Main program class for Producer/Consumer example.
 * Sets up channels, creates processes then
 * executes them in parallel, using JCSP.
 */
public final class Main11V2 {
    public static void runJCSP() {
        final One2OneChannelInt channel = Channel.one2oneInt();

        // Create parallel construct
        CSProcess[] procList = {
                new Producer(channel, 0),
                new Consumer(channel),
        }; // Processes

        // PAR construct
        Parallel par = new Parallel(procList);
        // Execute processes in parallel
        par.run();
    } // runJCSP
} // class Main
