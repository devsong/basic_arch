package com.gzs.learn.serial.conf;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "serial")
@Data
public class SerialProperties implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String appName;
}
