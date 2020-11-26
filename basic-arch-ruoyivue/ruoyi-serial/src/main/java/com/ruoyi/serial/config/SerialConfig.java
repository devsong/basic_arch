package com.ruoyi.serial.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "serial")
@Data
public class SerialConfig {
    /**
     * 名称
     */
    private String name;
    /**
     * 数据中心ID
     */
    private Integer dataCenterId;
    /**
     * 服务端口
     */
    private Integer serverPort;
    /**
     * zk地址
     */
    private String zk;
}
