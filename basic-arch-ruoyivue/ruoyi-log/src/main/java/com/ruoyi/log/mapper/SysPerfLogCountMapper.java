package com.ruoyi.log.mapper;

import java.util.List;

import com.ruoyi.log.domain.SysPerfLogCountPo;
import com.ruoyi.log.dto.SysPerfLogCountSearchDto;

public interface SysPerfLogCountMapper {
    List<SysPerfLogCountPo> searchPerfCountLogs(SysPerfLogCountSearchDto sysPerfLogCountSearchDto);

    boolean saveCountPo(SysPerfLogCountPo countPo);

    int deleteByPrimaryKey(Long id);

    int insert(SysPerfLogCountPo record);

    int insertSelective(SysPerfLogCountPo record);

    SysPerfLogCountPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysPerfLogCountPo record);

    int updateByPrimaryKey(SysPerfLogCountPo record);

    List<SysPerfLogCountPo> exist(SysPerfLogCountPo countPo);
}