package com.gzs.learn.config;

import java.util.Date;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.gzs.learn.common.util.BeanUtil;
import com.gzs.learn.config.dao.SysConfigMapper;
import com.gzs.learn.config.inf.SysConfigDto;
import com.gzs.learn.config.po.SysConfigPo;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SysConfigServiceTest {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Test
    public void testAddSysConfig() {
        Map<String, String> configMap = Maps.newHashMap();
        configMap.put("test", "test");
        SysConfigDto sysConfigDto = SysConfigDto.builder().product("com.gzs").groupName("learn").app("config").configKey("foo")
                .configName("foo test").configValue(JSON.toJSONString(configMap)).version(1).status(0).creator("gzs").createTime(new Date())
                .lastUpdateIp("127.0.0.1").lastUpdator("gzs").lastUpdateTime(new Date()).build();
        SysConfigPo sysConfigPo = new SysConfigPo();
        BeanUtil.copyProperties(sysConfigDto, sysConfigPo);
        int row = sysConfigMapper.insertSelective(sysConfigPo);
        Assert.assertTrue(row == 1);
    }
}
