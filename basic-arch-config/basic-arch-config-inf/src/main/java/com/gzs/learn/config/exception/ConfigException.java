package com.gzs.learn.config.exception;

/**
 * 系统异常
 * @author guanzhisong
 *
 */
public class ConfigException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ConfigException() {
        super();
    }

    public ConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigException(String message) {
        super(message);
    }

    public ConfigException(Throwable cause) {
        super(cause);
    }

}
