package com.gzs.learn.log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gzs.learn.log.dao.SysPerfLogMapper;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SysPerfLogMapperTest {
    @Autowired
    private SysPerfLogMapper sysPerfLogMapper;

    @Test
    public void testSysPerfLog() {
        sysPerfLogMapper.selectByPrimaryKey(1);
    }
}
