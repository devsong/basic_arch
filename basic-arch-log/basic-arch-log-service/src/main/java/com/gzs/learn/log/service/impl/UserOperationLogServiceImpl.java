package com.gzs.learn.log.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gzs.learn.log.inf.UserOperationLogDto;
import com.gzs.learn.log.inf.search.UserOperationLogSearchDto;
import com.gzs.learn.log.service.IUserOperationLogService;

@Component
public class UserOperationLogServiceImpl implements IUserOperationLogService {

    @Override
    public boolean insertOperationLog(UserOperationLogDto operationLogDto) {
        return false;
    }

    @Override
    public List<UserOperationLogDto> searchOperationLogs(UserOperationLogSearchDto userOperationLogSearchDto) {
        return null;
    }

}
