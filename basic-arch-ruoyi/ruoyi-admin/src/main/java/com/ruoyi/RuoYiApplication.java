package com.ruoyi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.ruoyi.common.constant.Constants;

/**
 * 启动程序
 * 
 * @author guanzhisong
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@ImportResource("classpath*:applicationContext.xml")
@EnableApolloConfig
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan(Constants.SYSTEM_PREFIX + ".**.mapper")
public class RuoYiApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(RuoYiApplication.class, args);
        ctx.registerShutdownHook();
    }
}