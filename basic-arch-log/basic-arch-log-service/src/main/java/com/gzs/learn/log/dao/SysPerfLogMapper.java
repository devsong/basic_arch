package com.gzs.learn.log.dao;

import java.util.List;

import com.gzs.learn.log.inf.search.SysPerfLogSearchDto;
import com.gzs.learn.log.po.SysPerfLogPo;

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
