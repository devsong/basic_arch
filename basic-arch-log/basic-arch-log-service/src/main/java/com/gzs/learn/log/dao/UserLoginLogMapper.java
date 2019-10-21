package com.gzs.learn.log.dao;

import java.util.List;

import com.gzs.learn.log.inf.search.UserLoginLogSearchDto;
import com.gzs.learn.log.po.UserLoginLogPo;

import tk.mybatis.mapper.common.Mapper;

public interface UserLoginLogMapper extends Mapper<UserLoginLogPo> {

    List<UserLoginLogPo> getLoginLogs(UserLoginLogSearchDto userLoginLogSearchDto);
}
