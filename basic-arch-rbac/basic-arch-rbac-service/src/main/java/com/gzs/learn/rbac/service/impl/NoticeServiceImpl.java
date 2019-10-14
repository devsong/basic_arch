package com.gzs.learn.rbac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gzs.learn.common.util.BeanUtil;
import com.gzs.learn.rbac.dao.NoticeMapper;
import com.gzs.learn.rbac.inf.NoticeDto;
import com.gzs.learn.rbac.po.NoticePo;
import com.gzs.learn.rbac.service.INoticeService;

@Component
@Transactional
public class NoticeServiceImpl implements INoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public List<NoticeDto> searchNotice(String condition) {
        List<NoticePo> noticePos = noticeMapper.list(condition);
        List<NoticeDto> noticeDtos = BeanUtil.copyList(noticePos, NoticeDto.class);
        return noticeDtos;
    }

    @Override
    public NoticeDto getNotice(Integer noticeId) {
        NoticePo noticePo = noticeMapper.selectByPrimaryKey(noticeId);
        NoticeDto noticeDto = new NoticeDto();
        BeanUtil.copyProperties(noticePo, noticeDto);
        return noticeDto;
    }

    @Override
    public boolean insertNotice(NoticeDto notice) {
        NoticePo po = new NoticePo();
        BeanUtil.copyProperties(notice, po);
        return noticeMapper.insertSelective(po) == 1;
    }

    @Override
    public boolean deleteNotice(Integer noticeId) {
        return noticeMapper.deleteByPrimaryKey(noticeId) == 1;
    }

    @Override
    public boolean updateNotice(NoticeDto updateDto) {
        NoticePo po = new NoticePo();
        BeanUtil.copyProperties(updateDto, po);
        return noticeMapper.updateByPrimaryKeySelective(po) == 1;
    }
}
