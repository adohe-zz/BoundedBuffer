package com.xqbase.buffer;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * The bounded buffer implemented in Java Concurrency Practice book.
 *
 * @author Tony He
 */
@ThreadSafe
public abstract class BaseBoundedBuffer<V> {

    @GuardedBy("this") private final V[] buf;
    @GuardedBy("this") private int tail;
    @GuardedBy("this") private int head;
    @GuardedBy("this") private int count;

    protected BaseBoundedBuffer(int capacity) {
        this.buf = (V[]) new Object[capacity];
    }

    protected synchronized final void doPut(V v) {
        buf[tail] = v;
        if (++tail == buf.length)
            tail = 0;
        ++count;
    }

    protected synchronized final V doGet() {
        V v = buf[head];
        buf[head] = null;
        if (++head == buf.length)
            head = 0;
        --count;
        return v;
    }

    protected synchronized final boolean isFull() {
        return count == buf.length;
    }

    protected synchronized final boolean isEmpty() {
        return count == 0;
    }
}