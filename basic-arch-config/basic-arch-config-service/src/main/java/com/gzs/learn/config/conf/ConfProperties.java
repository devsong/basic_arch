package com.gzs.learn.config.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "conf")
@Data
public class ConfProperties {
    // 应用pga标识
    private String product;
    private String groupName;
    private String app;
}
