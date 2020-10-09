package com.gzs.learn.log.dubbo;

import static com.gzs.learn.log.util.LogSystemUtil.dealProxyMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gzs.learn.inf.PageResponseDto;
import com.gzs.learn.log.inf.UserOperationLogDto;
import com.gzs.learn.log.inf.search.UserOperationLogSearchDto;
import com.gzs.learn.log.service.IUserOperationLogService;

@Component("dubboUserOperationLogService")
public class DubboUserOperationLogServiceImpl implements DubboUserOperationLogService {

    @Autowired
    private IUserOperationLogService userOperationLogService;

    @Override
    public boolean insertOperationLog(UserOperationLogDto operationLogDto) {
        operationLogDto.setMethod(dealProxyMethod(operationLogDto.getMethod()));
        return userOperationLogService.insertOperationLog(operationLogDto);
    }

    @Override
    public PageResponseDto<UserOperationLogDto> searchOperationLogs(UserOperationLogSearchDto userOperationLogSearchDto) {
        return userOperationLogService.searchOperationLogs(userOperationLogSearchDto);
    }

    @Override
    public UserOperationLogDto getOperationLogDetail(Long id) {
        return userOperationLogService.getDetail(id);
    }

}
