package com.gzs.learn.web.modular.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gzs.learn.rbac.dubbo.DubboRbacDeptService;
import com.gzs.learn.rbac.inf.DeptDto;
import com.gzs.learn.rbac.inf.ZTreeNode;
import com.gzs.learn.web.common.annotion.Permission;
import com.gzs.learn.web.common.annotion.log.BussinessLog;
import com.gzs.learn.web.common.constant.CommonResponse;
import com.gzs.learn.web.common.constant.Dict;
import com.gzs.learn.web.common.constant.factory.ConstantFactory;
import com.gzs.learn.web.common.constant.tips.Tip;
import com.gzs.learn.web.common.controller.BaseController;
import com.gzs.learn.web.common.exception.BizExceptionEnum;
import com.gzs.learn.web.common.exception.BussinessException;
import com.gzs.learn.web.core.log.LogObjectHolder;
import com.gzs.learn.web.core.util.ToolUtil;
import com.gzs.learn.web.modular.system.wrapper.DeptWrapper;

/**
 * 部门控制器
 */
@Controller
@RequestMapping("/dept")
public class DeptController extends BaseController {
    private String PREFIX = "/system/dept/";
    @Autowired
    private DubboRbacDeptService dubboRbacDeptService;

    /**
     * 跳转到部门管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "dept.html";
    }

    /**
     * 跳转到添加部门
     */
    @RequestMapping("/dept_add")
    public String deptAdd() {
        return PREFIX + "dept_add.html";
    }

    /**
     * 跳转到修改部门
     */
    @Permission
    @RequestMapping("/dept_update/{deptId}")
    public ModelAndView deptUpdate(@PathVariable Long deptId) {
        ModelAndView mav = new ModelAndView(PREFIX + "dept_edit.html"); 
        DeptDto dept = dubboRbacDeptService.getDept(deptId);
        mav.addObject(dept);
        mav.addObject("pName", ConstantFactory.me().getDeptName(dept.getPid()));
        LogObjectHolder.me().set(dept);
        return mav;
    }

    /**
     * 获取部门的tree列表
     */
    @RequestMapping(value = "/tree")
    @ResponseBody
    public List<ZTreeNode> tree() {
        List<ZTreeNode> tree = dubboRbacDeptService.getDeptTree();
        tree.add(ZTreeNode.createParent());
        return tree;
    }

    /**
     * 新增部门
     */
    @BussinessLog(value = "添加部门", key = "simplename", dict = Dict.DeptDict)
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(DeptDto dept) {
        if (ToolUtil.isOneEmpty(dept, dept.getSimplename())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        // 完善pids,根据pid拿到pid的pids
        deptSetPids(dept);
        return dubboRbacDeptService.insertDept(dept);
    }

    /**
     * 获取所有部门列表
     */
    @RequestMapping(value = "/list")
    @Permission
    @ResponseBody
    public CommonResponse<Object> list(String condition) {
        List<DeptDto> list = dubboRbacDeptService.searchDepts(condition);
        return CommonResponse.buildSuccess(warpObject(new DeptWrapper(list)));
    }

    /**
     * 部门详情
     */
    @RequestMapping(value = "/detail/{deptId}")
    @Permission
    @ResponseBody
    public CommonResponse<DeptDto> detail(@PathVariable("deptId") Long deptId) {
        DeptDto deptDto = dubboRbacDeptService.getDept(deptId);
        return CommonResponse.buildSuccess(deptDto);
    }

    /**
     * 修改部门
     */
    @BussinessLog(value = "修改部门", key = "simplename", dict = Dict.DeptDict)
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(DeptDto dept) {
        if (ToolUtil.isEmpty(dept) || dept.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        deptSetPids(dept);
        dubboRbacDeptService.updateDept(dept);
        return SUCCESS_TIP;
    }

    /**
     * 删除部门
     */
    @BussinessLog(value = "删除部门", key = "deptId", dict = Dict.DeleteDict)
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Tip delete(@RequestParam Long deptId) {
        LogObjectHolder.me().set(ConstantFactory.me().getDeptName(deptId));
        dubboRbacDeptService.deleteDept(deptId);
        return SUCCESS_TIP;
    }

    private void deptSetPids(DeptDto dept) {
        if (ToolUtil.isEmpty(dept.getPid()) || dept.getPid().equals(0L)) {
            dept.setPid(0L);
            dept.setPids("[0],");
        } else {
            Long pid = dept.getPid();
            DeptDto temp = dubboRbacDeptService.getDept(dept.getId());
            String pids = temp.getPids();
            dept.setPid(pid);
            dept.setPids(pids + "[" + pid + "],");
        }
    }
}
