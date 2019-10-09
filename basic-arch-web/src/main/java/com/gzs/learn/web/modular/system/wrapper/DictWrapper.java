package com.gzs.learn.web.modular.system.wrapper;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;

import com.google.common.collect.Lists;
import com.gzs.learn.rbac.inf.DictDto;
import com.gzs.learn.web.common.constant.factory.ConstantFactory;
import com.gzs.learn.web.modular.system.vo.DictVo;

import lombok.extern.slf4j.Slf4j;

/**
 * 字典列表的包装
 *
 * @author fengshuonan
 * @date 2017年4月25日 18:10:31
 */
@Slf4j
public class DictWrapper {

    public static DictVo convertDictDto(DictDto dict) {
        DictVo dictVo = new DictVo();
        try {
            BeanUtils.copyProperties(dictVo, dict);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("convert dict error", e);
        }
        List<DictDto> subDicts = ConstantFactory.me().findSubDict(dict.getId().intValue());
        String detail = subDicts.stream().map(s -> s.getNum() + ":" + s.getName()).collect(Collectors.joining(","));
        dictVo.setDetail(detail);
        return dictVo;
    }

    public static List<DictVo> convertDictDto(List<DictDto> dicts) {
        if (CollectionUtils.isEmpty(dicts)) {
            return Lists.newArrayList();
        }
        List<DictVo> results = Lists.newArrayListWithCapacity(dicts.size());
        for (DictDto dict : dicts) {
            results.add(convertDictDto(dict));
        }
        return results;
    }
}
