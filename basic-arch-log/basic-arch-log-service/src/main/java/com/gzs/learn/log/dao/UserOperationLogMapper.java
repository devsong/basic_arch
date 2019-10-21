package com.gzs.learn.log.dao;

import java.util.List;

import com.gzs.learn.log.inf.search.UserOperationLogSearchDto;
import com.gzs.learn.log.po.UserOperationLogPo;

import tk.mybatis.mapper.common.Mapper;

public interface UserOperationLogMapper extends Mapper<UserOperationLogPo> {
    List<UserOperationLogPo> getOperationLogs(UserOperationLogSearchDto userOperationLogSearchDto);
}
