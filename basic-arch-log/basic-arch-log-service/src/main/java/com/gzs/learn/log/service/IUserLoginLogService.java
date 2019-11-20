package com.gzs.learn.log.service;

import java.util.List;

import com.gzs.learn.log.inf.UserLoginLogDto;
import com.gzs.learn.log.inf.search.UserLoginLogSearchDto;

public interface IUserLoginLogService {

    boolean insertUserLoginLog(UserLoginLogDto userLoginLogDto);

    List<UserLoginLogDto> searchUserLoginLogs(UserLoginLogSearchDto userLoginLogSearchDto);

    UserLoginLogDto getDetail(Long id);

}
