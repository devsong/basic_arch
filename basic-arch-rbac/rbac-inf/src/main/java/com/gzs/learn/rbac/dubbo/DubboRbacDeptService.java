package com.gzs.learn.rbac.dubbo;

import java.util.List;

import com.gzs.learn.rbac.inf.DeptDto;
import com.gzs.learn.rbac.inf.ZTreeNode;

public interface DubboRbacDeptService {
    /*
     * 
     * /** 获取部门信息
     * 
     * @param deptId
     * 
     * @return
     */
    DeptDto getDept(Long deptId);

    /**
     * 部门树状结构
     * @return
     */
    List<ZTreeNode> getDeptTree();

    /**
     * 
     * @param 更新部门信息
     * @return
     */
    boolean updateDept(DeptDto dept);

    /**
     * 保存部门信息
     * @param dept
     * @return
     */
    boolean insertDept(DeptDto dept);

    /**
     * 搜索部门信息
     * @param condition
     * @return
     */
    List<DeptDto> searchDepts(String condition);

    /**
     * 删除部门信息
     * @param deptId
     * @return
     */
    boolean deleteDept(Long deptId);

    /**
     * 获取子部门
     * @param deptId
     * @return
     */
    List<DeptDto> getSubDeptDtos(Long deptId);
}
