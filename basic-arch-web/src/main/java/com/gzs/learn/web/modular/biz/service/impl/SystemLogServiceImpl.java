package com.gzs.learn.web.modular.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gzs.learn.log.dubbo.DubboPerfLogService;
import com.gzs.learn.log.dubbo.DubboUserLoginLogService;
import com.gzs.learn.log.dubbo.DubboUserOperationLogService;
import com.gzs.learn.log.inf.SysPerfLogDto;
import com.gzs.learn.log.inf.UserLoginLogDto;
import com.gzs.learn.log.inf.UserOperationLogDto;
import com.gzs.learn.log.inf.search.UserLoginLogSearchDto;
import com.gzs.learn.log.inf.search.UserOperationLogSearchDto;
import com.gzs.learn.web.common.constant.Const;
import com.gzs.learn.web.modular.biz.service.ISystemLogService;

@Component
@Transactional
public class SystemLogServiceImpl implements ISystemLogService {
    @Autowired
    private DubboPerfLogService dubboPerflogService;

    @Autowired
    private DubboUserLoginLogService dubboUserLoginLogService;

    @Autowired
    private DubboUserOperationLogService dubboUserOperationLogService;

    @Override
    public List<UserLoginLogDto> getLoginLogs(UserLoginLogSearchDto loginLogSearchDto) {
        List<UserLoginLogDto> searchUserLoginLogs = dubboUserLoginLogService.searchUserLoginLogs(loginLogSearchDto);
        return searchUserLoginLogs;
    }

    @Override
    public UserLoginLogDto getLoginLogDetail(Long id) {
        return dubboUserLoginLogService.getLoginDetail(id);
    }

    @Override
    public boolean saveLoginLog(UserLoginLogDto loginLog) {
        return dubboUserLoginLogService.insertUserLoginLog(loginLog);
    }

    @Override
    public List<UserOperationLogDto> getOperationLogs(UserOperationLogSearchDto operationLogSearchDto) {
        List<UserOperationLogDto> operationLogs = dubboUserOperationLogService.searchOperationLogs(operationLogSearchDto);
        return operationLogs;
    }

    @Override
    public UserOperationLogDto getOperationLogDetail(Long id) {
        return dubboUserOperationLogService.getOperationLogDetail(id);
    }

    @Override
    public boolean saveOperationLog(UserOperationLogDto operationLog) {
        return dubboUserOperationLogService.insertOperationLog(operationLog);
    }

    @Override
    public void savePerfLog(SysPerfLogDto sysPerfLogDto) {
        Const.SYSTEM_POOL.execute(() -> {
            dubboPerflogService.insertPerflog(sysPerfLogDto);
        });
    }
}
