package com.gzs.learn.web.common.constant.dictmap.factory;

import com.gzs.learn.web.common.constant.dictmap.base.AbstractDictMap;
import com.gzs.learn.web.common.constant.dictmap.base.SystemDict;
import com.gzs.learn.web.common.exception.BizExceptionEnum;
import com.gzs.learn.web.common.exception.BussinessException;

public class DictMapFactory {
    private static final String basePath = "com.gzs.learn.web.common.constant.dictmap.";

    @SuppressWarnings("unchecked")
    public static AbstractDictMap createDictMap(String className) {
        if ("SystemDict".equals(className)) {
            return new SystemDict();
        }
        try {
            Class<AbstractDictMap> clazz = (Class<AbstractDictMap>) Class.forName(basePath + className);
            return clazz.newInstance();
        } catch (Exception e) {
            throw new BussinessException(BizExceptionEnum.ERROR_CREATE_DICT);
        }
    }
}
