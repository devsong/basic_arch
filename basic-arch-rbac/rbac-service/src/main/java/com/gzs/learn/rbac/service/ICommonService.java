package com.gzs.learn.rbac.service;

import java.util.List;

import com.gzs.learn.rbac.inf.DictDto;
import com.gzs.learn.rbac.inf.NoticeDto;

public interface ICommonService {

    DictDto getDict(Integer dictId);

    List<DictDto> getDicts(List<Integer> dictIds);

    DictDto getDictByName(String name);

    List<DictDto> getDictByPid(Long pid);

    NoticeDto getNotice(Integer notictId);

    List<DictDto> searchDict(String condition);
}
