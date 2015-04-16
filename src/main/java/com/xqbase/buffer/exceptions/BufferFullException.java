package com.xqbase.buffer.exceptions;

/**
 * Throw such exception when buffer is full.
 *
 * @author Tony He
 */
public class BufferFullException extends RuntimeException {

    public BufferFullException() {
    }

    public BufferFullException(String message) {
        super(message);
    }
}
