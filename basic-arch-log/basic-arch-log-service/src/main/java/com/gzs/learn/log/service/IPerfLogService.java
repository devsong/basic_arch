package com.gzs.learn.log.service;

import com.gzs.learn.log.LogPageResponseDto;
import com.gzs.learn.log.inf.SysPerfLogDto;
import com.gzs.learn.log.inf.search.SysPerfLogSearchDto;
import com.gzs.learn.log.po.SysPerfLogMetaPo;

public interface IPerfLogService {

    boolean insertPerfLogMeta(SysPerfLogMetaPo po);

    boolean insertPerfLog(SysPerfLogDto sysLogDto);

    LogPageResponseDto<SysPerfLogDto> searchPerfLogs(SysPerfLogSearchDto searchDto);

}
