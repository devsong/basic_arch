package com.gzs.learn.log.dubbo;

import static com.gzs.learn.common.util.ClassUtil.dealProxyMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gzs.learn.common.util.BeanUtil;
import com.gzs.learn.inf.PageResponseDto;
import com.gzs.learn.log.inf.SysPerfLogDto;
import com.gzs.learn.log.inf.SysPerfLogMetaDto;
import com.gzs.learn.log.inf.search.SysPerfLogMetaSearchDto;
import com.gzs.learn.log.inf.search.SysPerfLogSearchDto;
import com.gzs.learn.log.po.SysPerfLogMetaPo;
import com.gzs.learn.log.service.IPerfLogService;

@Component("dubboPerflogService")
public class DubboPerfLogServiceImpl implements DubboPerfLogService {

    @Autowired
    private IPerfLogService perfLogService;

    @Override
    public boolean insertPerfLogMeta(SysPerfLogDto sysLogDto) {
        SysPerfLogMetaPo metaPo = new SysPerfLogMetaPo();
        sysLogDto.setMethod(dealProxyMethod(sysLogDto.getMethod()));
        BeanUtil.copyProperties(sysLogDto, metaPo);
        return perfLogService.insertPerfLogMeta(metaPo);
    }

    @Override
    public boolean insertPerflog(SysPerfLogDto sysLogDto) {
        sysLogDto.setMethod(dealProxyMethod(sysLogDto.getMethod()));
        return perfLogService.insertPerfLog(sysLogDto);
    }

    @Override
    public PageResponseDto<SysPerfLogDto> searchPerfLogs(SysPerfLogSearchDto searchDto) {
        return perfLogService.searchPerfLogs(searchDto);
    }

    @Override
    public PageResponseDto<SysPerfLogMetaDto> searchMetas(SysPerfLogMetaSearchDto searchDto) {
        return null;
    }


}
