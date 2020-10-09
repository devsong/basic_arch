package com.gzs.learn.log.dubbo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gzs.learn.inf.PageResponseDto;
import com.gzs.learn.log.inf.UserLoginLogDto;
import com.gzs.learn.log.inf.search.UserLoginLogSearchDto;
import com.gzs.learn.log.service.IUserLoginLogService;

@Component("dubboUserLoginLogService")
public class DubboUserLoginLogServiceImpl implements DubboUserLoginLogService {
    @Autowired
    private IUserLoginLogService userLoginLogService;

    @Override
    public boolean insertUserLoginLog(UserLoginLogDto userLoginLogDto) {
        return userLoginLogService.insertUserLoginLog(userLoginLogDto);
    }

    @Override
    public PageResponseDto<UserLoginLogDto> searchUserLoginLogs(UserLoginLogSearchDto userLoginLogSearchDto) {
        return userLoginLogService.searchUserLoginLogs(userLoginLogSearchDto);
    }

    @Override
    public UserLoginLogDto getLoginDetail(Long id) {
        return userLoginLogService.getDetail(id);
    }
}
