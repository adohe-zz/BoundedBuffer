package com.xqbase.buffer;

import net.jcip.annotations.ThreadSafe;

/**
 * Bounded buffer using crude blocking.
 *
 * @author Tony He
 */
@ThreadSafe
public class SleepyBoundedBuffer<V> extends BaseBoundedBuffer<V> {

    private static final int SLEEP_GRANULARITY = 60;

    public SleepyBoundedBuffer(int capacity) {
        super(capacity);
    }

    public void put(V v) throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isFull()) {
                    doPut(v);
                    return;
                }
            }
            Thread.sleep(SLEEP_GRANULARITY);
        }
    }

    public V get() throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isEmpty()) {
                    return doGet();
                }
            }
            Thread.sleep(SLEEP_GRANULARITY);
        }
    }
}
