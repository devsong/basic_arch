package com.gzs.learn.log.dubbo;

import com.gzs.learn.inf.PageResponseDto;
import com.gzs.learn.log.inf.SysPerfLogDto;
import com.gzs.learn.log.inf.SysPerfLogMetaDto;
import com.gzs.learn.log.inf.search.SysPerfLogMetaSearchDto;
import com.gzs.learn.log.inf.search.SysPerfLogSearchDto;

/**
 * 系统日志
 * @author guanzhisong
 *
 */
public interface DubboPerfLogService {
    /**
     * 写入元数据信息
     * @param sysLogDto
     * @return
     */
    boolean insertPerfLogMeta(SysPerfLogDto sysLogDto);

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
    PageResponseDto<SysPerfLogDto> searchPerfLogs(SysPerfLogSearchDto searchDto);

    /**
     * 元数据搜索
     * @param searchDto
     * @return
     */
    PageResponseDto<SysPerfLogMetaDto> searchMetas(SysPerfLogMetaSearchDto searchDto);
}
