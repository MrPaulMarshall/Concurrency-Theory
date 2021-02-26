package com.company.lab7_activeobject.strictScheduler;

import com.company.lab7_activeobject.ActivateQueue;
import com.company.lab7_activeobject.ConsumeMethodRequest;
import com.company.lab7_activeobject.MethodRequest;
import com.company.lab7_activeobject.ProduceMethodRequest;

import java.util.LinkedList;


public class StrictActivateQueue implements ActivateQueue {

    private final LinkedList<ProduceMethodRequest> produceRequests = new LinkedList<>();
    private final LinkedList<ConsumeMethodRequest> consumeRequests = new LinkedList<>();
    private final LinkedList<MethodRequest> allRequests = new LinkedList<>();

//    public void put(ProduceMethodRequest mr) {
//        produceRequests.add(mr);
//        allRequests.add(mr);
//    }
//
//    public void put(ConsumeMethodRequest mr) {
//        consumeRequests.add(mr);
//        allRequests.add(mr);
//    }

    @Override
    public void put(MethodRequest mr) {
        if (mr instanceof ProduceMethodRequest) {
            produceRequests.add((ProduceMethodRequest) mr);
        } else {
            consumeRequests.add((ConsumeMethodRequest) mr);
        }
        allRequests.add(mr);
    }

    public ProduceMethodRequest getFirstProduceRequest() {
        if (produceRequests.isEmpty()) return null;
        return produceRequests.getFirst();
    }

    public ConsumeMethodRequest getFirstConsumeRequest() {
        if (consumeRequests.isEmpty()) return null;
        return consumeRequests.getFirst();
    }

    public MethodRequest getFirstRequest() {
        if (allRequests.isEmpty()) return null;
        return allRequests.getFirst();
    }

    // ONLY first producer/consumer request can be removed
    public boolean remove(MethodRequest mr) {
        boolean somethingRemoved = false;
        if (!produceRequests.isEmpty() && produceRequests.getFirst() == mr) {
            produceRequests.removeFirst();
            somethingRemoved = true;
        }
        if (!consumeRequests.isEmpty() && consumeRequests.getFirst() == mr) {
            consumeRequests.removeFirst();
            somethingRemoved = true;
        }
        allRequests.remove(mr);
        return somethingRemoved;
    }

    public boolean isEmpty() {
        return allRequests.isEmpty();
    }

    public String stateOfQueues() {
        return "Prod: " + produceRequests.size() + " +  Cons: " + consumeRequests.size() + " = " + allRequests.size();
    }
}
