package com.ruoyi.leaf.snowflake.exception;

public class CheckLastTimeException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public CheckLastTimeException(String msg){
        super(msg);
    }
}
