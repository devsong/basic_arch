package com.gzs.learn.log.inf;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysPerfLogMetaDto implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private String level;

    /**
     * 产品线
     */
    private List<String> product;

    /**
     * 组名
     */
    private List<String> groupName;

    /**
     * 应用名
     */
    private List<String> app;

    /**
     * 类名
     */
    private List<String> clazz;

    /**
     * 方法名
     */
    private List<String> method;

    /**
     * 客户端机器ip
     */
    private List<String> operatorIp;
}