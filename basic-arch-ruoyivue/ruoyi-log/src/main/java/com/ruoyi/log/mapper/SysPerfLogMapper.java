package com.ruoyi.log.mapper;

import java.util.List;

import com.ruoyi.log.domain.SysPerfLogPo;
import com.ruoyi.log.dto.SysPerfLogSearchDto;

public interface SysPerfLogMapper {

    List<SysPerfLogPo> searchPerfLogs(SysPerfLogSearchDto searchDto);

    int deleteByPrimaryKey(Long id);

    int insert(SysPerfLogPo record);

    int insertSelective(SysPerfLogPo record);

    SysPerfLogPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysPerfLogPo record);

    int updateByPrimaryKeyWithBLOBs(SysPerfLogPo record);

    int updateByPrimaryKey(SysPerfLogPo record);
}
