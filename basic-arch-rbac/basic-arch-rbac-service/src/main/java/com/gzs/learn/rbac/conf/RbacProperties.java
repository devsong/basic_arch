package com.gzs.learn.rbac.conf;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "rbac")
@Data
public class RbacProperties implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String product;

    private String groupName;

    private String app;
}
