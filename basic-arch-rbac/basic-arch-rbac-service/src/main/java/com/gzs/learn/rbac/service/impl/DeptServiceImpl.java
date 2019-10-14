package com.gzs.learn.rbac.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gzs.learn.common.util.BeanUtil;
import com.gzs.learn.rbac.dao.DeptMapper;
import com.gzs.learn.rbac.inf.DeptDto;
import com.gzs.learn.rbac.inf.ZTreeNode;
import com.gzs.learn.rbac.po.DeptPo;
import com.gzs.learn.rbac.service.IDeptService;

import tk.mybatis.mapper.entity.Example;

@Component
@Transactional
public class DeptServiceImpl implements IDeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public boolean updateDept(DeptDto dept) {
        if (dept == null) {
            return false;
        }
        DeptPo deptPo = new DeptPo();
        BeanUtil.copyProperties(dept, deptPo);
        return deptMapper.updateByPrimaryKeySelective(deptPo) == 1;
    }

    @Override
    public boolean insertDept(DeptDto dept) {
        DeptPo deptPo = new DeptPo();
        BeanUtil.copyProperties(dept, deptPo);
        return deptMapper.insertSelective(deptPo) == 1;
    }

    @Override
    public boolean deleteDept(Long deptId) {
        deptMapper.deleteByPrimaryKey(deptId);
        Example example = new Example(DeptPo.class);
        example.createCriteria().andLike("pid", "%[" + deptId + "]%");
        List<DeptPo> subDeptPos = deptMapper.selectByExample(example);
        List<Long> deptIds = subDeptPos.stream().map(s -> s.getId()).collect(Collectors.toList());
        Example deleteExample = new Example(DeptPo.class);
        deleteExample.createCriteria().andIn("id", deptIds);
        deptMapper.deleteByExample(deleteExample);
        return true;
    }

    @Override
    public List<DeptDto> searchDepts(String condition) {
        List<DeptPo> list = deptMapper.search(condition);
        List<DeptDto> deptDtos = BeanUtil.copyList(list, DeptDto.class);
        return deptDtos;
    }

    @Override
    public DeptDto getDeptDto(Long deptId) {
        DeptPo deptPo = deptMapper.selectByPrimaryKey(deptId);
        if (deptPo == null) {
            return null;
        }
        DeptDto deptDto = new DeptDto();
        BeanUtil.copyProperties(deptPo, deptDto);
        return deptDto;
    }

    @Override
    public List<ZTreeNode> getDeptTree() {
        return deptMapper.tree();
    }

    @Override
    public List<DeptDto> getSubDepts(Long deptId) {
        Example example = new Example(DeptPo.class);
        example.createCriteria().andLike("pids", "%[" + deptId + "]%");
        List<DeptPo> deptPos = deptMapper.selectByExample(example);
        List<DeptDto> deptDtos = BeanUtil.copyList(deptPos, DeptDto.class);
        return deptDtos;
    }
}
