package com.github.eostermueller.tjp.launcher;

import java.util.LinkedList;

/*
 * This class exists because memory leaks suck.
 * A FIFO queue that discards the oldest objects entered, once a limit (set in the ctor) is reached.
 * 
 * @stolenFrom: http://stackoverflow.com/questions/5498865/size-limited-queue-that-holds-last-n-elements-in-java
 * @author erikostermueller
 *
 * @param <K>
 */

public class FixedLengthQueue<E> extends LinkedList<E> {
    private int limit;

    public FixedLengthQueue(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean add(E o) {
        super.add(o);
        while (size() > limit) { super.remove(); }
        return true;
    }
}