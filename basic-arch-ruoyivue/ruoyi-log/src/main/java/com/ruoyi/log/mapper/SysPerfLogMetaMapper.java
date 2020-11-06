package com.ruoyi.log.mapper;

import java.util.List;

import com.ruoyi.log.domain.SysPerfLogMetaPo;

public interface SysPerfLogMetaMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysPerfLogMetaPo record);

    int insertSelective(SysPerfLogMetaPo record);

    SysPerfLogMetaPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysPerfLogMetaPo record);

    int updateByPrimaryKey(SysPerfLogMetaPo record);

    List<SysPerfLogMetaPo> exist(SysPerfLogMetaPo query);
}