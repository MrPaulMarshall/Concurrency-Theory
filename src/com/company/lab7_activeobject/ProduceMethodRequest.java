package com.company.lab7_activeobject;

import java.util.List;

public class ProduceMethodRequest implements MethodRequest {

    private final Future future;
    private final List<Integer> itemsToProduce;
    private final Buffer buffer;

    public ProduceMethodRequest(Future future, Buffer buffer, List<Integer> items) {
        this.future = future;
        this.buffer = buffer;
        itemsToProduce = items;
    }

    @Override
    public void call() {
        for (int item : itemsToProduce) {
            buffer.produce(item);
        }
        future.markFinished();
        future.signalFinished();
    }

    @Override
    public boolean guard() {
        return buffer.enoughFreeSpace(itemsToProduce.size());
    }
}
