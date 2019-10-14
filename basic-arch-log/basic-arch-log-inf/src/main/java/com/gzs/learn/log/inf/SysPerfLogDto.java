package com.gzs.learn.log.inf;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * @author guanzhisong
 *
 */
@Data
public class SysPerfLogDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 产品线
     */
    private String product;
    /**
     * 组名
     */
    private String group;
    /**
     * 应用名
     */
    private String app;
    /**
     * 类名
     */
    private String clazz;
    /**
     * 方法名
     */
    private String method;
    /**
     * 执行时间
     */
    private Integer executeTimespan;
    /**
     * 方法入参
     */
    private String paramsIn;
    /**
     * 方法出参数
     */
    private String paramsOut;
    /**
     * 调用时间
     */
    private Long timestamp;
    /**
     * 调用结果0:成功,-1:系统异常,大于0业务异常
     */
    private Integer code;
    /**
     * 系统异常堆栈信息
     */
    private String errMsg;
}
