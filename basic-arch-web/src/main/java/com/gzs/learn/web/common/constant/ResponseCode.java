package com.gzs.learn.web.common.constant;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS(0, "请求成功");

    private int code;
    private String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
