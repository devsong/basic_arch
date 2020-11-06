package com.gzs.learn.log.dao;

import java.util.List;

import com.gzs.learn.log.inf.search.SysPerfLogCountSearchDto;
import com.gzs.learn.log.po.SysPerfLogCountPo;

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