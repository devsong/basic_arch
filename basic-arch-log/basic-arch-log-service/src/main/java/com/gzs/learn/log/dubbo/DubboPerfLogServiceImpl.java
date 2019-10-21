package com.gzs.learn.log.dubbo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gzs.learn.log.LogPageResponseDto;
import com.gzs.learn.log.inf.SysPerfLogDto;
import com.gzs.learn.log.inf.search.SysPerfLogSearchDto;
import com.gzs.learn.log.service.IPerfLogService;

@Component("dubboPerflogService")
public class DubboPerfLogServiceImpl implements DubboPerfLogService {

    @Autowired
    private IPerfLogService perfLogService;

    @Override
    public boolean insertPerflog(SysPerfLogDto sysLogDto) {
        return perfLogService.insertPerfLog(sysLogDto);
    }

    @Override
    public LogPageResponseDto<SysPerfLogDto> searchPerfLogs(SysPerfLogSearchDto searchDto) {
        return perfLogService.searchPerfLogs(searchDto);
    }

}
