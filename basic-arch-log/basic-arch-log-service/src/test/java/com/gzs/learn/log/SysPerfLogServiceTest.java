package com.gzs.learn.log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gzs.learn.common.util.IpUtil;
import com.gzs.learn.log.dao.SysPerfLogMapper;
import com.gzs.learn.log.inf.SysPerfLogDto;
import com.gzs.learn.log.service.IPerfLogService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SysPerfLogServiceTest {

    @Autowired
    private SysPerfLogMapper sysPerfLogMapper;

    @Autowired
    private IPerfLogService perfLogService;

    @Test
    public void testSysPerfLog() {
        sysPerfLogMapper.selectByPrimaryKey(1);
    }

    @Test
    public void testPerfLogServiceInsert() {
        SysPerfLogDto sysLogDto = SysPerfLogDto.builder().product("perf").groupName("perf").app("dubbo-app").clazz("SysPerfLogServiceTest")
                .method("testPerfLogServiceInsert").operatorIp(IpUtil.getLocalIp()).executeTimespan(20).paramsIn("test").paramsOut("test")
                .build();
        perfLogService.insertPerfLog(sysLogDto);
    }

}
