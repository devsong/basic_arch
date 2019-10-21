package com.gzs.learn.log.dao;

import java.util.List;

import com.gzs.learn.log.inf.search.SysPerfLogSearchDto;
import com.gzs.learn.log.po.SysPerfLogPo;

import tk.mybatis.mapper.common.Mapper;

public interface SysPerfLogMapper extends Mapper<SysPerfLogPo> {

    List<SysPerfLogPo> searchPerfLogs(SysPerfLogSearchDto searchDto);

}
