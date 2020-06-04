package com.gzs.learn.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.alibaba.druid.pool.DruidDataSource;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 数据库数据源配置
 * </p>
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource")
@Data
@EqualsAndHashCode(callSuper = false)
public class DataSourceProperties extends BaseDataSourceProperties {
    protected String name;

    protected String url;

    protected String username;

    protected String password;

    @Override
    public DruidDataSource config(DruidDataSource dataSource) {
        dataSource.setName(getName());
        if (getUrl().indexOf("?") == -1) {
            dataSource.setUrl(getUrl() + "?" + DEFAULT_MYSQL_CONNECT_PARAMS);
        } else {
            dataSource.setUrl(getUrl());
        }
        dataSource.setUsername(getUsername());
        dataSource.setPassword(getPassword());
        super.config(dataSource);
        return dataSource;
    }
}
