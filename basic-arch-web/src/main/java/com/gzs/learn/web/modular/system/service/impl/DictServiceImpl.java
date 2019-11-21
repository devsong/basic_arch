package com.gzs.learn.web.modular.system.service.impl;

import static com.gzs.learn.web.common.constant.factory.MutiStrFactory.MUTI_STR_KEY;
import static com.gzs.learn.web.common.constant.factory.MutiStrFactory.MUTI_STR_VALUE;
import static com.gzs.learn.web.common.constant.factory.MutiStrFactory.parseKeyValue;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gzs.learn.rbac.dubbo.DubboRbacCommonService;
import com.gzs.learn.rbac.inf.DictDto;
import com.gzs.learn.web.common.exception.BizExceptionEnum;
import com.gzs.learn.web.common.exception.BussinessException;
import com.gzs.learn.web.modular.system.service.IDictService;

@Service
public class DictServiceImpl implements IDictService {
    @Autowired
    private DubboRbacCommonService dubboRbacCommonService;

    @Override
    public void addDict(String dictName, String dictValues) {
        DictDto dictDto = dubboRbacCommonService.getDictByName(dictName);
        if (dictDto != null) {
            throw new BussinessException(BizExceptionEnum.DICT_EXISTED);
        }
        // 解析dictValues
        List<Map<String, String>> items = parseKeyValue(dictValues);

        // 添加字典
        DictDto dict = new DictDto();
        dict.setName(dictName);
        dict.setNum(0);
        dict.setPid(0L);
        dict = this.dubboRbacCommonService.insertDict(dict);

        // 添加字典条目
        for (Map<String, String> item : items) {
            String num = item.get(MUTI_STR_KEY);
            String name = item.get(MUTI_STR_VALUE);
            DictDto itemDict = new DictDto();
            itemDict.setPid(dict.getId());
            itemDict.setName(name);
            try {
                itemDict.setNum(Integer.valueOf(num));
            } catch (NumberFormatException e) {
                throw new BussinessException(BizExceptionEnum.DICT_MUST_BE_NUMBER);
            }
            this.dubboRbacCommonService.insertDict(itemDict);
        }
    }

    @Override
    public void editDict(Integer dictId, String dictName, String dicts) {
        // 删除之前的字典
        this.delteDict(dictId);
        // 重新添加新的字典
        this.addDict(dictName, dicts);
    }

    @Override
    public void delteDict(Integer dictId) {
        dubboRbacCommonService.deleteDict(dictId);
    }
}
