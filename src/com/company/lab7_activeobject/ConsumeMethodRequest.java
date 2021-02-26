package com.company.lab7_activeobject;

import java.util.ArrayList;
import java.util.List;

public class ConsumeMethodRequest implements MethodRequest {

    private final Future future;
    private final Buffer buffer;
    private final int howMany;

    public ConsumeMethodRequest(Future future, Buffer buffer, int howMany) {
        this.future = future;
        this.buffer = buffer;
        this.howMany = howMany;
    }

    @Override
    public void call() {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < howMany; i++) {
            result.add(buffer.consume());
        }
        future.setResult(result);
        future.markFinished();
        future.signalFinished();
    }

    @Override
    public boolean guard() {
        return buffer.enoughItems(howMany);
    }
}
