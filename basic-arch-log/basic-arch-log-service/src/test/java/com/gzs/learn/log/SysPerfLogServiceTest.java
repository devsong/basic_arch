package com.gzs.learn.log;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gzs.learn.common.util.IpUtil;
import com.gzs.learn.log.dao.SysPerfLogMapper;
import com.gzs.learn.log.enums.SysPerfLogDurationEnum;
import com.gzs.learn.log.inf.SysPerfLogDto;
import com.gzs.learn.log.inf.search.SysPerfLogSearchDto;
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
    public void testPerfLogServiceInsert() throws Exception {
        SysPerfLogDto sysLogDto = SysPerfLogDto.builder().product("perf").groupName("perf").app("dubbo-app").clazz("SysPerfLogServiceTest")
                .method("testPerfLogServiceInsert").code(-1).errMsg("success").durationEnum(SysPerfLogDurationEnum.BY_MINUTE)
                .operatorIp(IpUtil.getLocalIp()).executeTimespan(3).paramsIn("test").paramsOut("test")
                .createTime(DateUtils.parseDate("2019-10-23 09:49:02", "yyyy-MM-dd HH:mm:ss")).build();
        perfLogService.insertPerfLog(sysLogDto);
    }

    @Test
    public void testPerfLogSearch() {
        SysPerfLogSearchDto searchDto = SysPerfLogSearchDto.builder().product("perf").groupName("perf").app("dubbo-app").build();
        searchDto.setPage(1);
        searchDto.setPageSize(10);
        LogPageResponseDto<SysPerfLogDto> searchPerfLogs = perfLogService.searchPerfLogs(searchDto);
        Assert.assertNotNull(searchPerfLogs);
    }

}
