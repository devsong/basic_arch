package com.gzs.learn.rbac.service;

import java.util.List;

import com.gzs.learn.rbac.inf.NoticeDto;

public interface INoticeService {
    List<NoticeDto> searchNotice(String condition);

    NoticeDto getNotice(Integer notictId);

    boolean insertNotice(NoticeDto notice);

    boolean deleteNotice(Integer noticeId);

    boolean updateNotice(NoticeDto updateDto);
}
