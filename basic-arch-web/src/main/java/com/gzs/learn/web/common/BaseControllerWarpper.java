package com.gzs.learn.web.common;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.sf.cglib.beans.BeanMap;

/**
 * 控制器查询结果的包装类基类
 */
public abstract class BaseControllerWarpper {
    public Object obj = null;

    public BaseControllerWarpper(Object obj) {
        this.obj = obj;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Object warp() {
        if (this.obj instanceof List) {
            List list = (List) this.obj;
            List result = Lists.newArrayListWithExpectedSize(list.size());
            for (Object obj : list) {
                if (obj instanceof Map) {
                    warpTheMap((Map) obj);
                    result.add(obj);
                } else {
                    Map objMap = Maps.newHashMap(BeanMap.create(obj));
                    warpTheMap(objMap);
                    result.add(objMap);
                }
            }
            return result;
        } else if (this.obj instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) this.obj;
            warpTheMap(map);
            return map;
        } else {
            Map m = Maps.newHashMap(BeanMap.create(this.obj));
            warpTheMap(m);
            return m;
        }
    }

    protected abstract void warpTheMap(Map<String, Object> map);
}
