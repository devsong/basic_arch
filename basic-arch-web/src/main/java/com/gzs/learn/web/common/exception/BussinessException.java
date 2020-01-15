package com.gzs.learn.web.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BussinessException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // 友好提示的code码
    private int code;

    // 友好提示
    private String msg;

    // 业务异常跳转的页面
    private String urlPath;

    public BussinessException() {

    }

    public BussinessException(BizExceptionEnum bizExceptionEnum) {
        this.code = bizExceptionEnum.getCode();
        this.msg = bizExceptionEnum.getMsg();
        this.urlPath = bizExceptionEnum.getUrlPath();
    }
}
