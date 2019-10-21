package com.gzs.learn.log.service;

import java.util.List;

import com.gzs.learn.log.inf.UserOperationLogDto;
import com.gzs.learn.log.inf.search.UserOperationLogSearchDto;

public interface IUserOperationLogService {

    boolean insertOperationLog(UserOperationLogDto operationLogDto);

    List<UserOperationLogDto> searchOperationLogs(UserOperationLogSearchDto userOperationLogSearchDto);

}
