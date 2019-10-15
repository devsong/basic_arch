package com.gzs.learn.config.inf;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysConfigKeyDto implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /*
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
    * 配置键值
    */
    private String configKey;
}
