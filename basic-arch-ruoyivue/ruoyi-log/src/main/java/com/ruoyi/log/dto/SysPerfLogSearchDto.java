package com.ruoyi.log.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class SysPerfLogSearchDto extends PageSearchRequestDto {
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
     * 操作IP
     */
    private String operateIp;
    /**
     * 日志ID
     */
    private String id;
}
