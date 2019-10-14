package com.gzs.learn.rbac.service;

import java.util.List;

import com.gzs.learn.rbac.inf.DictDto;

public interface ICommonService {

    DictDto getDict(Integer dictId);

    List<DictDto> getDicts(List<Integer> dictIds);

    DictDto getDictByName(String name);

    List<DictDto> getDictByPid(Long pid);

    List<DictDto> searchDict(String condition);

}
