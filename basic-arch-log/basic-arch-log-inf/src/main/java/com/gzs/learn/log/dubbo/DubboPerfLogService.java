package com.gzs.learn.log.dubbo;

import com.gzs.learn.log.LogPageResponseDto;
import com.gzs.learn.log.inf.SysPerfLogDto;
import com.gzs.learn.log.inf.search.SysPerfLogSearchDto;

/**
 * 系统日志
 * @author guanzhisong
 *
 */
public interface DubboPerfLogService {
    /**
     * 写入性能日志数据
     * @param sysLogDto
     * @return
     */
    boolean insertPerflog(SysPerfLogDto sysLogDto);

    /**
     * 搜索日志数据
     * @param searchDto
     * @return
     */
    LogPageResponseDto<SysPerfLogDto> searchPerfLogs(SysPerfLogSearchDto searchDto);
}
