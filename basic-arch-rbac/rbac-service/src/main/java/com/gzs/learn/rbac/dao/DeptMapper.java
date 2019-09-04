package com.gzs.learn.rbac.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gzs.learn.rbac.inf.ZTreeNode;
import com.gzs.learn.rbac.po.DeptPo;

import tk.mybatis.mapper.common.Mapper;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 */
public interface DeptMapper extends Mapper<DeptPo> {
    List<ZTreeNode> tree();

    List<DeptPo> list(@Param("condition") String condition);
}