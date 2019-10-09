package com.gzs.learn.web.common.persistence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gzs.learn.rbac.inf.ZTreeNode;
import com.gzs.learn.web.common.persistence.model.Dept;

import tk.mybatis.mapper.common.Mapper;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 */
public interface DeptMapper extends Mapper<Dept> {
    /**
     * 获取ztree的节点列表
     *
     * @return
     * @date 2017年2月17日 下午8:28:43
     */
    List<ZTreeNode> tree();

    List<Map<String, Object>> list(@Param("condition") String condition);
}