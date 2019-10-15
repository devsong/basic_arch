package com.gzs.learn.config;

import java.util.Date;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.gzs.learn.common.util.BeanUtil;
import com.gzs.learn.config.dubbo.DubboConfigService;
import com.gzs.learn.config.inf.SysConfigDto;
import com.gzs.learn.config.inf.SysConfigKeyDto;

public class DubboConfigServiceTest {
    ApplicationContext ctx;

    @Before
    public void before() {
        ctx = new ClassPathXmlApplicationContext("client-dubbo.xml");
    }

    @Test
    public void testAddConfig() {
        DubboConfigService dubboConfigService = ctx.getBean(DubboConfigService.class);
        Map<String, String> configMap = Maps.newHashMap();
        configMap.put("foo", "bartest");
        SysConfigDto sysConfigDto = SysConfigDto.builder().product("com.gzs").groupName("learn").app("config").configKey("foo")
                .configName("测试配置").configValue(JSON.toJSONString(configMap)).version(1).status(0).creator("gzs").createTime(new Date())
                .lastUpdateIp("127.0.0.1").lastUpdator("gzs").lastUpdateTime(new Date()).build();
        //dubboConfigService.addConfig(sysConfigDto);

        SysConfigKeyDto key = new SysConfigKeyDto();
        BeanUtil.copyProperties(sysConfigDto, key);
        String result = dubboConfigService.getConfig(key);
        System.out.println(result);
        Assert.assertNotNull(result);
    }
}
