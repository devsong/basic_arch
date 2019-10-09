package com.gzs.learn.rbac.service;

import java.util.List;

import com.gzs.learn.rbac.inf.DeptDto;
import com.gzs.learn.rbac.inf.ZTreeNode;

public interface IDeptService {
    boolean updateDept(DeptDto dept);

    boolean insertDept(DeptDto dept);

    boolean deleteDept(Long deptId);

    List<DeptDto> searchDepts(String condition);

    DeptDto getDeptDto(Long deptId);

    List<ZTreeNode> getDeptTree();

    List<DeptDto> getSubDepts(Long deptId);
}
