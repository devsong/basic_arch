package com.gzs.learn.log.service;

import com.gzs.learn.inf.PageResponseDto;
import com.gzs.learn.log.inf.UserLoginLogDto;
import com.gzs.learn.log.inf.search.UserLoginLogSearchDto;

public interface IUserLoginLogService {

    boolean insertUserLoginLog(UserLoginLogDto userLoginLogDto);

    PageResponseDto<UserLoginLogDto> searchUserLoginLogs(UserLoginLogSearchDto userLoginLogSearchDto);

    UserLoginLogDto getDetail(Long id);

}
