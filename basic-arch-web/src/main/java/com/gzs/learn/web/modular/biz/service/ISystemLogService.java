package com.gzs.learn.web.modular.biz.service;

import com.gzs.learn.inf.PageResponseDto;
import com.gzs.learn.log.inf.SysPerfLogDto;
import com.gzs.learn.log.inf.UserLoginLogDto;
import com.gzs.learn.log.inf.UserOperationLogDto;
import com.gzs.learn.log.inf.search.UserLoginLogSearchDto;
import com.gzs.learn.log.inf.search.UserOperationLogSearchDto;

public interface ISystemLogService {
    /**
     * 获取登录日志
     * @param queryLogBo
     * @return
     */
    PageResponseDto<UserLoginLogDto> getLoginLogs(UserLoginLogSearchDto loginLogSearchDto);

    /**
     * 获取登陆日志详情
     * @param id
     * @return
     */
    UserLoginLogDto getLoginLogDetail(Long id);

    /**
     * 保存登陆日志
     * @param loginLog
     * @return
     */
    boolean saveLoginLog(UserLoginLogDto loginLog);

    /**
     * 获取操作日志
     * @param queryLogBo
     * @return
     */
    PageResponseDto<UserOperationLogDto> getOperationLogs(UserOperationLogSearchDto operationLogSearchDto);

    /**
     * 查询详情
     * @param id
     * @return
     */
    UserOperationLogDto getOperationLogDetail(Long id);

    /**
     * 保存操作日志
     * @param operationLog
     * @return
     */
    boolean saveOperationLog(UserOperationLogDto operationLog);

    void savePerfLog(SysPerfLogDto sysPerfLogDto);
}
