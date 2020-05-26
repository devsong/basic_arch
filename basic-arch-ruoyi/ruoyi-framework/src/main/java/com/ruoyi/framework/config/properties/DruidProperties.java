package com.ruoyi.framework.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * druid 配置属性
 * 
 * @author guanzhisong
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.druid")
public class DruidProperties extends BaseDataSourceProperties {

    public DruidDataSource dataSource(DruidDataSource datasource) {
        datasource = (DruidDataSource) super.config(datasource);
        return datasource;
    }
}
