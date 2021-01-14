package com.ruoyi.log.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.log.enums.SysPerfLogDurationEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author guanzhisong
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysPerfLogDto implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * id,js浮点数限制,long型强制转换为字符串值在前端展示
     */
    private String id;
    /**
     * 产品线
     */
    private String product;
    /**
     * 组名
     */
    private String groupName;
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
     * 操作IP
     */
    private String operatorIp;
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
    private String errmsg;
    /**
     * 统计日志记录维度
     */
    private SysPerfLogDurationEnum durationEnum;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
