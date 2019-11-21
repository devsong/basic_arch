package com.gzs.learn.log.service;

import com.gzs.learn.inf.PageResponseDto;
import com.gzs.learn.log.inf.UserOperationLogDto;
import com.gzs.learn.log.inf.search.UserOperationLogSearchDto;

public interface IUserOperationLogService {

    boolean insertOperationLog(UserOperationLogDto operationLogDto);

    PageResponseDto<UserOperationLogDto> searchOperationLogs(UserOperationLogSearchDto userOperationLogSearchDto);

    UserOperationLogDto getDetail(Long id);

}
