package com.gzs.learn.rbac.enums;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

/**
 * 是否是菜单的枚举
 */
@Getter
public enum IsMenu {

    YES(1, "是"),

    NO(0, "不是");// 不是菜单的是按钮

    int code;
    String message;

    private static Map<Integer, IsMenu> holder = new HashMap<Integer, IsMenu>();

    static {
        for (IsMenu v : values()) {
            holder.put(v.getCode(), v);
        }
    }

    IsMenu(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String valueOf(Integer status) {
        if (status == null) {
            return "";
        }
        IsMenu v = holder.get(status);
        if (v == null) {
            return "";
        }
        return v.getMessage();
    }
}
