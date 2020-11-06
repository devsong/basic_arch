package com.ruoyi.log.enums;

import lombok.Getter;

/**
 * 日志统计间隔枚举
 * @author guanzhisong
 *
 */
@Getter
public enum SysPerfLogDurationEnum {
    BY_DATE(1, "yyyy-MM-dd", "按天"),

    BY_HOUR(2, "yyyy-MM-dd HH", "按小时"),

    BY_MINUTE(3, "yyyy-MM-dd HH:mm", "按分钟");

    private int code;
    private String pattern;
    private String desc;

    SysPerfLogDurationEnum(int code, String pattern, String desc) {
        this.code = code;
        this.pattern = pattern;
        this.desc = desc;
    }
}
