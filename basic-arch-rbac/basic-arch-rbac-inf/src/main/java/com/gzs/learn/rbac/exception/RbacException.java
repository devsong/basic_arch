package com.gzs.learn.rbac.exception;

/**
 * Rbac系统异常
 * @author guanzhisong
 */
public class RbacException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public RbacException() {
        super();
    }

    public RbacException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public RbacException(String message, Throwable cause) {
        super(message, cause);
    }

    public RbacException(String message) {
        super(message);
    }

    public RbacException(Throwable cause) {
        super(cause);
    }

}
