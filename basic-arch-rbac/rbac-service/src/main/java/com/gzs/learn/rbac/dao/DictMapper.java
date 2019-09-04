package com.gzs.learn.rbac.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gzs.learn.rbac.po.DeptPo;
import com.gzs.learn.rbac.po.DictPo;

import tk.mybatis.mapper.common.Mapper;

public interface DictMapper extends Mapper<DictPo> {
    List<DeptPo> list(@Param("condition") String conditiion);
}