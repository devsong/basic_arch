package com.gzs.learn.rbac;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.gzs.learn.rbac.dao.TestMapper;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TxTest {
    @Autowired
    private TestMapper testMapper;

    @Test
    @Transactional
    public void testInsert() {
        com.gzs.learn.rbac.po.Test t = new com.gzs.learn.rbac.po.Test();
        t.setId(3L);
        t.setName("test");
        testMapper.insertSelective(t);
        throw new RuntimeException("111");
    }
}
