package com.gzs.learn.log.dubbo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gzs.learn.log.inf.UserOperationLogDto;
import com.gzs.learn.log.inf.search.UserOperationLogSearchDto;
import com.gzs.learn.log.service.IUserOperationLogService;

@Component("dubboUserOperationLogService")
public class DubboUserOperationLogServiceImpl implements DubboUserOperationLogService {

    @Autowired
    private IUserOperationLogService userOperationLogService;

    @Override
    public boolean insertOperationLog(UserOperationLogDto operationLogDto) {
        return userOperationLogService.insertOperationLog(operationLogDto);
    }

    @Override
    public List<UserOperationLogDto> searchOperationLogs(UserOperationLogSearchDto userOperationLogSearchDto) {
        return userOperationLogService.searchOperationLogs(userOperationLogSearchDto);
    }

}
