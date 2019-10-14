package com.gzs.learn.rbac.dubbo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gzs.learn.rbac.inf.DictDto;
import com.gzs.learn.rbac.inf.NoticeDto;
import com.gzs.learn.rbac.service.ICommonService;
import com.gzs.learn.rbac.service.INoticeService;

@Component("dubboRbacCommonService")
public class DubboRbacCommonServiceImpl implements DubboRbacCommonService {

    @Autowired
    private ICommonService commonService;

    @Autowired
    private INoticeService noticeService;

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
    public NoticeDto getNotice(Integer noticeId) {
        return noticeService.getNotice(noticeId);
    }

    @Override
    public List<NoticeDto> searchNotice(String condition) {
        return noticeService.searchNotice(condition);
    }

    @Override
    public boolean insertNotice(NoticeDto notice) {
        return noticeService.insertNotice(notice);
    }

    @Override
    public boolean deleteNotice(Integer noticeId) {
        return noticeService.deleteNotice(noticeId);
    }

    @Override
    public boolean updateNotice(NoticeDto updateDto) {
        return noticeService.updateNotice(updateDto);
    }

    @Override
    public DictDto insertDict(DictDto itemDict) {
        return null;
    }

    @Override
    public boolean deleteDict(Integer dictId) {
        return false;
    }
}
