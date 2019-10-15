package com.gzs.learn.config.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.gzs.learn.config.inf.SysConfigDto;
import com.gzs.learn.config.po.SysConfigPo;

import tk.mybatis.mapper.common.Mapper;

public interface SysConfigMapper extends Mapper<SysConfigPo> {

    @Select("select id from sys_config where product=#{config.product} and group=#{config.group} and app=#{config.app} and config_key=#{config.configKey} and status=0 order by id desc limit #{limit}")
    List<Long> getLatestConfig(@Param("config") SysConfigDto configDto, @Param("limit") int limit);

    @Delete("delete from sys_config where product=#{config.product} and group=#{config.group} and app=#{config.app} and config_key=#{config.configKey} and id<#{id}")
    boolean deleteUnusedConfig(@Param("config") SysConfigDto configDto, @Param("id") Long id);

}
