package com.gzs.learn.serial.exception;

public class ClockGoBackException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ClockGoBackException(String message) {
        super(message);
    }
}
