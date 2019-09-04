package com.gzs.learn.web.common.constant.enums;

import lombok.Getter;

@Getter
public enum LogSucceed {
    SUCCESS("成功"),

    FAIL("失败");

    String message;

    LogSucceed(String message) {
        this.message = message;
    }
}
