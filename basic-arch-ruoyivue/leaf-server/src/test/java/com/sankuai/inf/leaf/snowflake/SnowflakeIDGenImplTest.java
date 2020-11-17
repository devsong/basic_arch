package com.sankuai.inf.leaf.snowflake;

import org.junit.Test;

import com.ruoyi.leaf.IDGen;
import com.ruoyi.leaf.common.PropertyFactory;
import com.ruoyi.leaf.common.Result;
import com.ruoyi.leaf.snowflake.SnowflakeIDGenImpl;

import java.util.Properties;

public class SnowflakeIDGenImplTest {
    @Test
    public void testGetId() {
        Properties properties = PropertyFactory.getProperties();

        IDGen idGen = new SnowflakeIDGenImpl(properties.getProperty("leaf.zk.list"), 8080);
        for (int i = 1; i < 1000; ++i) {
            Result r = idGen.get("a");
            System.out.println(r);
        }
    }
}
