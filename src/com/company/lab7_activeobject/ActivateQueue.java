package com.company.lab7_activeobject;

public interface ActivateQueue {
    void put(MethodRequest mr);
    ProduceMethodRequest getFirstProduceRequest();
    ConsumeMethodRequest getFirstConsumeRequest();
    boolean remove(MethodRequest mr);
}
