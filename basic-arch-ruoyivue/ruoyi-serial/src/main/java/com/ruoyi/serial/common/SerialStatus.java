package com.ruoyi.serial.common;

public enum SerialStatus {
    // 正常
    NORMAL(0),

    // 禁用
    FORBIDDEN(1),

    // 删除
    DELETE(2);

    int status;

    SerialStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
