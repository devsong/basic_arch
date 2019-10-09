package com.gzs.learn.rbac.dubbo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gzs.learn.rbac.inf.DeptDto;
import com.gzs.learn.rbac.inf.ZTreeNode;
import com.gzs.learn.rbac.service.IDeptService;

@Component("dubboRbacDeptService")
public class DubboRbacDeptServiceImpl implements DubboRbacDeptService {
    @Autowired
    private IDeptService deptService;

    @Override
    public DeptDto getDept(Long deptId) {
        return deptService.getDeptDto(deptId);
    }

    @Override
    public List<ZTreeNode> getDeptTree() {
        return deptService.getDeptTree();
    }

    @Override
    public boolean updateDept(DeptDto dept) {
        return deptService.updateDept(dept);
    }

    @Override
    public boolean insertDept(DeptDto dept) {
        return deptService.insertDept(dept);
    }

    @Override
    public List<DeptDto> searchDepts(String condition) {
        return deptService.searchDepts(condition);
    }

    @Override
    public boolean deleteDept(Long deptId) {
        return deptService.deleteDept(deptId);
    }

    @Override
    public List<DeptDto> getSubDeptDtos(Long deptId) {
        return deptService.getSubDepts(deptId);
    }
}
