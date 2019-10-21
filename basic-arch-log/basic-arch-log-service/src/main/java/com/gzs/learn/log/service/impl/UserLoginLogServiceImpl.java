package com.gzs.learn.log.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gzs.learn.log.inf.UserLoginLogDto;
import com.gzs.learn.log.inf.search.UserLoginLogSearchDto;
import com.gzs.learn.log.service.IUserLoginLogService;

@Component
public class UserLoginLogServiceImpl implements IUserLoginLogService {

    @Override
    public boolean insertUserLoginLog(UserLoginLogDto userLoginLogDto) {
        return false;
    }

    @Override
    public List<UserLoginLogDto> searchUserLoginLogs(UserLoginLogSearchDto userLoginLogSearchDto) {
        return null;
    }

}
