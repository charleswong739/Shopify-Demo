package com.chwonghm.exception;

/**
 * An exception thrown when attempting to perform some operation on an entity that
 * does not exist.
 *
 * @author Charles Wong
 */
public class ResourceNotFoundException extends Exception {

    /**
     * Constructs a exception with some custom message.
     *
     * @param msg the String message to initialize this exception with
     */
    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
