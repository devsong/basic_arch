package com.gzs.learn.rbac.dubbo;

import java.util.List;

import com.gzs.learn.rbac.inf.DictDto;
import com.gzs.learn.rbac.inf.NoticeDto;

/**
 * 一些公用的方法
 * @author guanzhisong
 *
 */
public interface DubboRbacCommonService {
    DictDto getDict(Integer dictId);

    List<DictDto> getDicts(List<Integer> dictIds);

    DictDto getDictByName(String name);

    List<DictDto> getDictByPid(Long id);

    List<DictDto> searchDict(String condition);

    NoticeDto getNotice(Integer notictId);
}
