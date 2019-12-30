package com.gzs.learn.serial.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.gzs.learn.common.config.DataSourceProperties;

import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@EnableTransactionManagement(order = 2, proxyTargetClass = true)
@MapperScan(basePackages = { "com.gzs.learn.serial.repository" })
public class MybatisConfig {
    @Autowired
    DataSourceProperties dataSourceProperties;
    
    @Autowired
    private SerialProperties serialProperties;

    /**
     * 单数据源连接池配置
     */
    @Bean
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSourceProperties.config(dataSource);
        System.out.println(serialProperties.getAppName());
        return dataSource;
    }
}
