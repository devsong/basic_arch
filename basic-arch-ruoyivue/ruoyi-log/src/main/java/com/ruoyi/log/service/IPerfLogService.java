package com.ruoyi.log.service;

import com.gzs.learn.inf.PageResponseDto;
import com.ruoyi.log.domain.SysPerfLogMetaPo;
import com.ruoyi.log.dto.SysPerfLogDto;
import com.ruoyi.log.dto.SysPerfLogSearchDto;

public interface IPerfLogService {

    boolean insertPerfLogMeta(SysPerfLogMetaPo po);

    boolean insertPerfLog(SysPerfLogDto sysLogDto);

    PageResponseDto<SysPerfLogDto> searchPerfLogs(SysPerfLogSearchDto searchDto);

}
