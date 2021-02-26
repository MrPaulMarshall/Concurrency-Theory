package com.company.lab8_timeexecutiontests.lab7_activeobject;

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
        List<Integer> result = buffer.consume(howMany);
        future.setResult(result);
        future.markFinished();
    }

    @Override
    public boolean guard() {
        return buffer.enoughItems(howMany);
    }
}
