package com.gzs.learn.log.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "logapp")
@Data
public class LogProperties {
    private String product;
    private String groupName;
    private String app;
}
