package com.gzs.learn.rbac;

import java.util.concurrent.CountDownLatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.gzs.learn")
@ImportResource("classpath:/META-INF/applicationContext.xml")
@SpringBootApplication
@Slf4j
public class RbacServer {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext ctx = SpringApplication.run(RbacServer.class, args);
        ctx.registerShutdownHook();
        log.info("start rbac server success");
        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }
}
