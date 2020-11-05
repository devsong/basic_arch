package com.ruoyi.framework.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

import lombok.Getter;
import lombok.Setter;

/**
 * druid 配置属性
 * 
 * @author guanzhisong
 */
@Configuration
@ConfigurationProperties("spring.datasource.druid")
@Getter
@Setter
public class DruidPoolProperties {
    protected static final String DEFAULT_DRIVER = "com.mysql.cj.jdbc.Driver";
    // protected static final String DEFAULT_DRIVER = "com.mysql.jdbc.Driver";
    protected static final String DEFAULT_MYSQL_CONNECT_PARAMS = "serverTimezone=Asia/Shanghai&characterEncoding=utf8&useUnicode=true&useSSL=false&zeroDateTimeBehavior=convertToNull";

    protected String driverClassName = DEFAULT_DRIVER;

    protected Boolean poolPreparedStatements = true;

    protected Integer maxPoolPreparedStatementPerConnectionSize = 20;

    protected String filters = "stat";

    // @Value("${spring.datasource.druid.initialSize}")
    protected int initialSize;

    // @Value("${spring.datasource.druid.minIdle}")
    protected int minIdle;

    // @Value("${spring.datasource.druid.maxActive}")
    protected int maxActive;

    // @Value("${spring.datasource.druid.maxWait}")
    protected int maxWait;

    // @Value("${spring.datasource.druid.timeBetweenEvictionRunsMillis}")
    protected int timeBetweenEvictionRunsMillis;

    // @Value("${spring.datasource.druid.minEvictableIdleTimeMillis}")
    protected int minEvictableIdleTimeMillis;

    // @Value("${spring.datasource.druid.maxEvictableIdleTimeMillis}")
    protected int maxEvictableIdleTimeMillis;

    // @Value("${spring.datasource.druid.validationQuery}")
    protected String validationQuery;

    // @Value("${spring.datasource.druid.testWhileIdle}")
    protected boolean testWhileIdle;

    // @Value("${spring.datasource.druid.testOnBorrow}")
    protected boolean testOnBorrow;

    // @Value("${spring.datasource.druid.testOnReturn}")
    protected boolean testOnReturn;

    public void dataSource(DruidDataSource datasource) {
        String[] connectProps = DEFAULT_MYSQL_CONNECT_PARAMS.split("&");
        for (String connectProp : connectProps) {
            String[] kv = connectProp.split("=");
            String propKey = kv[0];
            String propVal = kv[1];
            datasource.addConnectionProperty(propKey, propVal);
        }

        /** 配置初始化大小、最小、最大 */
        datasource.setInitialSize(initialSize);
        datasource.setMaxActive(maxActive);
        datasource.setMinIdle(minIdle);

        /** 配置获取连接等待超时的时间 */
        datasource.setMaxWait(maxWait);

        /** 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 */
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

        /** 配置一个连接在池中最小、最大生存的时间，单位是毫秒 */
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setMaxEvictableIdleTimeMillis(maxEvictableIdleTimeMillis);

        /**
         * 用来检测连接是否有效的sql，要求是一个查询语句，常用select 'x'。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
         */
        datasource.setValidationQuery(validationQuery);
        /** 建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。 */
        datasource.setTestWhileIdle(testWhileIdle);
        /** 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。 */
        datasource.setTestOnBorrow(testOnBorrow);
        /** 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。 */
        datasource.setTestOnReturn(testOnReturn);
    }
}
