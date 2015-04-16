package com.xqbase.buffer;

import net.jcip.annotations.GuardedBy;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Bounded buffer using explicit condition variables
 *
 * @author Tony He
 */
public class ConditionBoundedBuffer<V> {

    protected final Lock lock = new ReentrantLock();
    // CONDITION PREDICATE: notFull (count < items.length)
    private final Condition notFull = lock.newCondition();
    // CONDITION PREDICATE: notEmpty (count > 0)
    private final Condition notEmpty = lock.newCondition();

    private static final int BUFFER_SIZE = 1000;
    @GuardedBy("lock") private final V[] items = (V[]) new Object[BUFFER_SIZE];
    @GuardedBy("lock") private int tail, head, count;

    public void put(V v) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length)
                notFull.await();
            items[tail] = v;
            if (++tail == items.length)
                tail = 0;
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public V get() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0)
                notEmpty.await();
            V v = items[head];
            items[head] = null;
            if (++head == items.length)
                head = 0;
            --count;
            notFull.signal();
            return v;
        } finally {
            lock.unlock();
        }
    }
}
