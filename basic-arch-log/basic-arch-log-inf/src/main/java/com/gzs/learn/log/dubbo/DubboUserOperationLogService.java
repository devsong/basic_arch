package com.gzs.learn.log.dubbo;

import java.util.List;

import com.gzs.learn.log.inf.UserOperationLogDto;
import com.gzs.learn.log.inf.search.UserOperationLogSearchDto;

public interface DubboUserOperationLogService {
    /**
     * 保存操作日志
     * @param operationLogDto
     * @return
     */
    boolean insertOperationLog(UserOperationLogDto operationLogDto);

    /**
     * 搜索操作日志
     * @param userOperationLogSearchDto
     * @return
     */
    List<UserOperationLogDto> searchOperationLogs(UserOperationLogSearchDto userOperationLogSearchDto);

    /**
     * 取日志详情
     * @param id
     * @return
     */
    UserOperationLogDto getOperationLogDetail(Long id);
}
