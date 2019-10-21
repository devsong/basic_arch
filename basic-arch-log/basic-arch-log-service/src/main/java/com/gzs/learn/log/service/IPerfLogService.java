package com.gzs.learn.log.service;

import com.gzs.learn.log.LogPageResponseDto;
import com.gzs.learn.log.inf.SysPerfLogDto;
import com.gzs.learn.log.inf.search.SysPerfLogSearchDto;

public interface IPerfLogService {

    boolean insertPerfLog(SysPerfLogDto sysLogDto);

    LogPageResponseDto<SysPerfLogDto> searchPerfLogs(SysPerfLogSearchDto searchDto);

}
