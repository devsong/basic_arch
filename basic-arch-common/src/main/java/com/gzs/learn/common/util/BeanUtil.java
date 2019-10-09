package com.gzs.learn.common.util;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * 对象copy
 * @author guanzhisong
 *
 */
public class BeanUtil extends BeanUtils {
    /**
     * 采用json反序列化的方式
     * @param <T>
     * @param <E>
     * @param src
     * @param desc
     */
    public static <T, E> List<E> copyList(List<T> src, Class<E> clazz) {
        String json = JSON.toJSONString(src);
        TypeReference<List<E>> ref = new TypeReference<List<E>>(clazz) {
        };
        return JSON.parseObject(json, ref.getType());
    }
}
