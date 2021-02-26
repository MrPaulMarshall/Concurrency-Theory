package com.company.lab6_manyprocessesinbuffor;

import java.util.HashMap;

public class Buffer {

    private final HashMap<Integer, Boolean> buffer;
    private int countErrors = 0;

    public Buffer(int size) {
        this.buffer = new HashMap<>(size);
    }

    void produce(int id) {
        if (buffer.containsKey(id)) {
            this.countErrors++;
            System.out.println("Box " + id + " is not empty");
        }

        buffer.put(id, true);
    }

    void consume(int id) {
        if (!buffer.containsKey(id)) {
            this.countErrors++;
            System.out.println("Box " + id + " isn't filled");
        }

        buffer.remove(id);
    }

    public int getCountErrors() {
        return this.countErrors;
    }

    public void printCountErrors() {
        System.out.println("Errors counted: " + this.getCountErrors());
    }

}
