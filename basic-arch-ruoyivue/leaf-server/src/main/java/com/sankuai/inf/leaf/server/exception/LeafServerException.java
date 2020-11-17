package com.sankuai.inf.leaf.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.INTERNAL_SERVER_ERROR)
public class LeafServerException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public LeafServerException(String msg) {
        super(msg);
    }
}
