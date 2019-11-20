package com.gzs.learn.log.dubbo;

import java.util.List;

import com.gzs.learn.log.inf.UserLoginLogDto;
import com.gzs.learn.log.inf.search.UserLoginLogSearchDto;

public interface DubboUserLoginLogService {
    /**
     * 保存登陆日志
     * @param operationLogDto
     * @return
     */
    boolean insertUserLoginLog(UserLoginLogDto userLoginLogDto);

    /**
     * 搜索操作日志
     * @param userOperationLogSearchDto
     * @return
     */
    List<UserLoginLogDto> searchUserLoginLogs(UserLoginLogSearchDto userLoginLogSearchDto);

    /**
     * 取登录日志详情
     * @param id
     * @return
     */
    UserLoginLogDto getLoginDetail(Long id);
}
