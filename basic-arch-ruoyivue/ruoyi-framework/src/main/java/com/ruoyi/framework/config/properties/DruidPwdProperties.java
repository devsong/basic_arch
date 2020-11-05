package com.ruoyi.framework.config.properties;

import com.alibaba.druid.pool.DruidDataSource;

import lombok.Getter;
import lombok.Setter;

/**
 * druid 配置属性
 * 
 * @author guanzhisong
 */
@Getter
@Setter
public class DruidPwdProperties {
    protected String url;
    protected String username;
    protected String password;

    public void dataSource(DruidDataSource datasource) {
        datasource.setUrl(url);
        datasource.setUsername(username);
        datasource.setPassword(password);
    }
}
