package com.gzs.learn.log.dubbo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gzs.learn.common.util.BeanUtil;
import com.gzs.learn.log.LogPageResponseDto;
import com.gzs.learn.log.inf.SysPerfLogDto;
import com.gzs.learn.log.inf.search.SysPerfLogSearchDto;
import com.gzs.learn.log.po.SysPerfLogMetaPo;
import com.gzs.learn.log.service.IPerfLogService;
import com.gzs.learn.log.util.LogSystemUtil;

@Component("dubboPerflogService")
public class DubboPerfLogServiceImpl implements DubboPerfLogService {

    @Autowired
    private IPerfLogService perfLogService;

    @Override
    public boolean insertPerfLogMeta(SysPerfLogDto sysLogDto) {
        SysPerfLogMetaPo metaPo = new SysPerfLogMetaPo();
        BeanUtil.copyProperties(sysLogDto, metaPo);
        return perfLogService.insertPerLogMeta(metaPo);
    }

    @Override
    public boolean insertPerflog(SysPerfLogDto sysLogDto) {
        return perfLogService.insertPerfLog(sysLogDto);
    }

    @Override
    public LogPageResponseDto<SysPerfLogDto> searchPerfLogs(SysPerfLogSearchDto searchDto) {
        LogSystemUtil.setDefaultSearchRange(searchDto);
        return perfLogService.searchPerfLogs(searchDto);
    }
}
