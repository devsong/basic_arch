package com.gzs.learn.rbac.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gzs.learn.rbac.po.NoticePo;

import tk.mybatis.mapper.common.Mapper;

/**
 * <p>
  * 通知表 Mapper 接口
 * </p>
 */
public interface NoticeMapper extends Mapper<NoticePo> {
    List<NoticePo> list(@Param("condition") String condition);
}