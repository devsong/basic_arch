package com.ruoyi.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 程序注解配置
 *
 * @author guanzhisong
 */
@Configuration
@ConfigurationProperties(prefix = "app")
@Data
public class ApplicationConfig {
    // apollo appid
    private Integer id;

    private String product;

    private String group;

    private String app;
}
