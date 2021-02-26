package com.company.lab7_activeobject;

public class Buffer {

    private final int[] array;
    private int consIndex;
    private int prodIndex;
    private int count;

    public Buffer(int size) {
        array = new int[size];
        consIndex = 0;
        prodIndex = 0;
        count = 0;
    }

    public void produce(int item) {
        array[prodIndex] = item;
        prodIndex = (prodIndex + 1) % array.length;
        count++;
    }

    public int consume() {
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
}
