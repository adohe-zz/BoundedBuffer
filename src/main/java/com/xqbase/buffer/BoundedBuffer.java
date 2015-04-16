package com.xqbase.buffer;

/**
 * Bounded buffer using condition queues.
 *
 * @author Tony He
 */
public class BoundedBuffer<V> extends BaseBoundedBuffer<V> {

    protected BoundedBuffer(int capacity) {
        super(capacity);
    }

    public synchronized void put(V v) throws InterruptedException {
        while (isFull())
            wait();
        doPut(v);
        notifyAll();
    }

    public synchronized V get() throws InterruptedException {
        while (isEmpty())
            wait();
        V v = doGet();
        notifyAll();
        return v;
    }
}
