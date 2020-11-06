package com.gzs.learn.log.dao;

import java.util.List;

import com.gzs.learn.log.po.SysPerfLogMetaPo;

public interface SysPerfLogMetaMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysPerfLogMetaPo record);

    int insertSelective(SysPerfLogMetaPo record);

    SysPerfLogMetaPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysPerfLogMetaPo record);

    int updateByPrimaryKey(SysPerfLogMetaPo record);

    List<SysPerfLogMetaPo> exist(SysPerfLogMetaPo query);
}