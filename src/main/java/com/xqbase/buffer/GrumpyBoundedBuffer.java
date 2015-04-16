package com.xqbase.buffer;

import com.xqbase.buffer.exceptions.BufferEmptyException;
import com.xqbase.buffer.exceptions.BufferFullException;
import net.jcip.annotations.ThreadSafe;

/**
 * Throw exceptions when the pre-condition does not
 * satisfy.
 *
 * @author Tony He
 */
@ThreadSafe
public class GrumpyBoundedBuffer<V> extends BaseBoundedBuffer<V> {

    public GrumpyBoundedBuffer(int capacity) {
        super(capacity);
    }

    public synchronized void put(V v) throws BufferFullException {
        if (isFull())
            throw new BufferFullException();
        doPut(v);
    }

    public synchronized V get() throws BufferEmptyException {
        if (isEmpty())
            throw new BufferEmptyException();
        return doGet();
    }
}
