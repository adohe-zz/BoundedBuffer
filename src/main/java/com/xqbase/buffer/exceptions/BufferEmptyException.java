package com.xqbase.buffer.exceptions;

/**
 * Throw such exception when buffer is empty.
 *
 * @author Tony He
 */
public class BufferEmptyException extends RuntimeException {

    public BufferEmptyException() {
    }

    public BufferEmptyException(String message) {
        super(message);
    }
}
