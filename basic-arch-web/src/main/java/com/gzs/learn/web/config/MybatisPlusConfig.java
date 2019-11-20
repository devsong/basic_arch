package com.gzs.learn.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.gzs.learn.web.common.aop.AopOrder;
import com.gzs.learn.web.config.properties.GunsDataSourceProperties;
import com.gzs.learn.web.config.properties.LogDataSourceProperties;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * MybatisPlus配置
 */
@Configuration
@EnableTransactionManagement(order = AopOrder.TRANSACTION_ORDER, proxyTargetClass = true)
// 由于引入多数据源，所以让spring事务的aop要在多数据源切换aop的后面
@MapperScan(basePackages = { "com.gzs.learn.web.common.persistence.dao" })
public class MybatisPlusConfig {
    @Autowired
    GunsDataSourceProperties gunsDataSourceProperties;

    @Autowired
    LogDataSourceProperties logDataSourceProperties;

    /**
     * guns的数据源
     */
    private DruidDataSource dataSourceGuns() {
        DruidDataSource dataSource = new DruidDataSource();
        gunsDataSourceProperties.config(dataSource);
        return dataSource;
    }

    /**
     * 单数据源连接池配置
     */
    @Bean
    @ConditionalOnProperty(prefix = "guns", name = "muti-datasource-open", havingValue = "false")
    public DruidDataSource singleDatasource() {
        return dataSourceGuns();
    }
}
