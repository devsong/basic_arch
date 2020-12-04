package com.ruoyi.log.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysPerfLogMetaRequestDto implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 层级
     */
    private String level;

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
}