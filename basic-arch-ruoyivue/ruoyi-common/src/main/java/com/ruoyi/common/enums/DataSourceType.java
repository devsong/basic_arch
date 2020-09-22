package com.ruoyi.common.enums;

/**
 * 数据源
 * 
 * @author guanzhisong
 */
public enum DataSourceType {
    /**
     * 主库
     */
    MASTER,

    /**
     * 从库
     */
    SLAVE,

    /**
     * 定时任务库
     */
    QRTZ
}
