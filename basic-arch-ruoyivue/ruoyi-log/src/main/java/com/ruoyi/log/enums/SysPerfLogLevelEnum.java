package com.ruoyi.log.enums;

import lombok.Getter;

@Getter
public enum SysPerfLogLevelEnum {

    PRODUCT("product"),

    GROUP("group"),

    APP("app"),

    CLAZZ("clazz"),

    METHOD("method"),

    OPERATOR_IP("operatorIp")

    ;

    String level;

    SysPerfLogLevelEnum(String level) {
        this.level = level;
    }
}
