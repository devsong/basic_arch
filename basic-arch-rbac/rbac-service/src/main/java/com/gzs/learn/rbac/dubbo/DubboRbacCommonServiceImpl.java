package com.gzs.learn.rbac.dubbo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gzs.learn.rbac.inf.DictDto;
import com.gzs.learn.rbac.inf.NoticeDto;
import com.gzs.learn.rbac.service.ICommonService;

@Component("dubboRbacCommonService")
public class DubboRbacCommonServiceImpl implements DubboRbacCommonService {

    @Autowired
    private ICommonService commonService;

    @Override
    public DictDto getDict(Integer dictId) {
        return commonService.getDict(dictId);
    }

    @Override
    public List<DictDto> getDicts(List<Integer> dictIds) {
        return commonService.getDicts(dictIds);
    }

    @Override
    public DictDto getDictByName(String name) {
        return commonService.getDictByName(name);
    }

    @Override
    public List<DictDto> getDictByPid(Long pid) {
        return commonService.getDictByPid(pid);
    }

    @Override
    public List<DictDto> searchDict(String condition) {
        return commonService.searchDict(condition);
    }

    @Override
    public NoticeDto getNotice(Integer notictId) {
        return commonService.getNotice(notictId);
    }
}
