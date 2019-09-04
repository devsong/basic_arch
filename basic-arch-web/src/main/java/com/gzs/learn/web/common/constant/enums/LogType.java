package com.gzs.learn.web.common.constant.enums;

import lombok.Getter;

@Getter
public enum LogType {

    LOGIN("登录日志"),

    LOGIN_FAIL("登录失败日志"),

    EXIT("退出日志"),

    EXCEPTION("异常日志"),

    BUSSINESS("业务日志");

    String message;

    LogType(String message) {
        this.message = message;
    }
}
