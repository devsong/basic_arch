package com.gzs.learn.log.dao;

import java.util.List;

import com.gzs.learn.log.inf.search.SysPerfLogCountSearchDto;
import com.gzs.learn.log.po.SysPerfLogCountPo;

import tk.mybatis.mapper.common.Mapper;

public interface SysPerfLogCountMapper extends Mapper<SysPerfLogCountPo> {
    List<SysPerfLogCountPo> searchPerfCountLogs(SysPerfLogCountSearchDto sysPerfLogCountSearchDto);

    boolean saveCountPo(SysPerfLogCountPo countPo);
}