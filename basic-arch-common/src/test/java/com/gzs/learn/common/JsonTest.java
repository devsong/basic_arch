package com.gzs.learn.common;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.gzs.learn.common.util.JsonUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class JsonTest {

    @Test
    public void testToJson() {
        FooJson foo = new FooJson(1, "test");
        System.out.println(JsonUtil.toJSONString(foo));
    }

    @Test
    public void testFromJson() {
        String json = "{\"id\":1,\"name\":\"test\"}";
        FooJson obj = JsonUtil.parseObject(json, FooJson.class);
        Assert.assertTrue(obj.getId() == 1);
    }

    @Test
    public void testParseList() {
        List<FooJson> list = Lists.newArrayList(new FooJson(1, "test"), new FooJson(2, "test2"));
        list = JsonUtil.parseList(JsonUtil.toJSONString(list), FooJson.class);
        Assert.assertTrue(list.size() == 2);
    }

    @Test
    public void testParseList2() {
        List<FooJson> list = Lists.newArrayList(new FooJson(1, "test"), new FooJson(2, "test2"));
        list = JsonUtil.parseObject(JsonUtil.toJSONString(list), new TypeReference<List<FooJson>>() {
        });
        Assert.assertTrue(list.size() == 2);
    }

}

@Data
@NoArgsConstructor
@AllArgsConstructor
class FooJson {
    private int id;
    private String name;

    public String toString() {
        return JsonUtil.toJSONString(this);
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class BarJson {
    private int id;
    private String name;
}