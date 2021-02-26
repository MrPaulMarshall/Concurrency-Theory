package com.company.lab8_timeexecutiontests.lab7_activeobject;

import com.company.lab8_timeexecutiontests.Parameters;
import com.company.lab8_timeexecutiontests.TimeConverter;

import java.util.ArrayList;
import java.util.List;

public class Buffer {

    private final int[] array;
    private int consIndex;
    private int prodIndex;
    private int count;

    private int jobsDone = 0;

    public Buffer(int size) {
        array = new int[size];
        consIndex = 0;
        prodIndex = 0;
        count = 0;
    }

    public void produce(List<Integer> items) {
        long start = System.nanoTime();
        for (int item : items) {
            produceOne(item);
        }
        while (TimeConverter.timeSince(start) < Parameters.bufferTime) {
            double dummyValue = Math.sqrt(System.nanoTime());
        }

        jobDone();
    }

    private void produceOne(int item) {
        array[prodIndex] = item;
        prodIndex = (prodIndex + 1) % array.length;
        count++;
    }

    public List<Integer> consume(int howMany) {
        long start = System.nanoTime();
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < howMany; i++) {
            result.add(consumeOne());
        }
        while (TimeConverter.timeSince(start) < Parameters.bufferTime) {
            double dummyValue = Math.pow(System.nanoTime(), (double)1/3);
        }

        jobDone();

        return result;
    }

    private int consumeOne() {
        int item = array[consIndex];
        consIndex = (consIndex + 1) % array.length;
        count--;
        return item;
    }

    public boolean enoughFreeSpace(int toProduce) {
        return array.length - count >= toProduce;
    }

    public boolean enoughItems(int toConsume) {
        return count >= toConsume;
    }

    public int getCount() {
        return count;
    }

    private void jobDone() {
        jobsDone++;
        if (jobsDone >= Parameters.jobsToDo) {
            jobsDone = 0;
            Main7.testFinished();
        }
    }
}
