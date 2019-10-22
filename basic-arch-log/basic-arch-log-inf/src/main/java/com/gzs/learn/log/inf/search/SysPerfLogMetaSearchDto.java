package com.gzs.learn.log.inf.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysPerfLogMetaSearchDto implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
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
     * 客户端机器ip
     */
    private String operatorIp;
}