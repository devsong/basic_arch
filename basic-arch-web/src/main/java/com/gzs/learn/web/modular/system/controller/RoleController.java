package com.gzs.learn.web.modular.system.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gzs.learn.rbac.dubbo.DubboRbacRoleService;
import com.gzs.learn.rbac.inf.RoleDto;
import com.gzs.learn.rbac.inf.UserDto;
import com.gzs.learn.rbac.inf.ZTreeNode;
import com.gzs.learn.web.common.annotion.Permission;
import com.gzs.learn.web.common.annotion.log.BussinessLog;
import com.gzs.learn.web.common.constant.CommonResponse;
import com.gzs.learn.web.common.constant.Const;
import com.gzs.learn.web.common.constant.Dict;
import com.gzs.learn.web.common.constant.cache.Cache;
import com.gzs.learn.web.common.constant.factory.ConstantFactory;
import com.gzs.learn.web.common.constant.tips.Tip;
import com.gzs.learn.web.common.controller.BaseController;
import com.gzs.learn.web.common.exception.BizExceptionEnum;
import com.gzs.learn.web.common.exception.BussinessException;
import com.gzs.learn.web.core.cache.CacheKit;
import com.gzs.learn.web.core.log.LogObjectHolder;
import com.gzs.learn.web.core.util.Convert;
import com.gzs.learn.web.core.util.ToolUtil;
import com.gzs.learn.web.modular.system.service.IRoleService;
import com.gzs.learn.web.modular.system.service.IUserService;
import com.gzs.learn.web.modular.system.wrapper.RoleWrapper;

/**
 * 角色控制器
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
    private static String PREFIX = "/system/role";

    @Autowired
    private DubboRbacRoleService dubboRbacRoleService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserService userService;

    /**
     * 跳转到角色列表页面
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/role.html";
    }

    /**
     * 跳转到添加角色
     */
    @RequestMapping(value = "/role_add")
    public String roleAdd() {
        return PREFIX + "/role_add.html";
    }

    /**
     * 跳转到修改角色
     */
    @Permission
    @RequestMapping(value = "/role_edit/{roleId}")
    public ModelAndView roleEdit(@PathVariable Long roleId) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        ModelAndView mav = new ModelAndView(PREFIX + "/role_edit.html");
        RoleDto role = dubboRbacRoleService.getRole(roleId);
        mav.addObject(role);
        mav.addObject("pName", ConstantFactory.me().getSingleRoleName(role.getPid()));
        mav.addObject("deptName", ConstantFactory.me().getDeptName(role.getDeptid()));
        LogObjectHolder.me().set(role);
        return mav;
    }

    /**
     * 跳转到角色分配
     */
    @Permission
    @RequestMapping(value = "/role_assign/{roleId}")
    public ModelAndView roleAssign(@PathVariable("roleId") Long roleId) {
        ModelAndView mav = new ModelAndView(PREFIX + "/role_assign.html");
        if (ToolUtil.isEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        mav.addObject("roleId", roleId);
        mav.addObject("roleName", ConstantFactory.me().getSingleRoleName(roleId));
        return mav;
    }

    /**
     * 获取角色列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String roleName) {
        List<RoleDto> roles = dubboRbacRoleService.searchRoles(roleName);
        return CommonResponse.buildSuccess((List<?>) new RoleWrapper(roles).wrap());
    }

    /**
     * 角色新增
     */
    @RequestMapping(value = "/add")
    @BussinessLog(value = "添加角色", key = "name", dict = Dict.RoleDict)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip add(@Valid RoleDto role, BindingResult result) {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        role.setId(null);
        dubboRbacRoleService.insertRole(role);
        return SUCCESS_TIP;
    }

    /**
     * 角色修改
     */
    @RequestMapping(value = "/edit")
    @BussinessLog(value = "修改角色", key = "name", dict = Dict.RoleDict)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip edit(@Valid RoleDto role, BindingResult result) {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        dubboRbacRoleService.updateRole(role);
        // 删除缓存
        CacheKit.removeAll(Cache.CONSTANT);
        return SUCCESS_TIP;
    }

    /**
     * 删除角色
     */
    @RequestMapping(value = "/remove")
    @BussinessLog(value = "删除角色", key = "roleId", dict = Dict.DeleteDict)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip remove(@RequestParam Long roleId) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }

        // 不能删除超级管理员角色
        if (roleId.equals(Const.ADMIN_ROLE_ID)) {
            throw new BussinessException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }
        LogObjectHolder.me().set(ConstantFactory.me().getSingleRoleName(roleId));
        dubboRbacRoleService.delRole(roleId);
        // 删除缓存
        CacheKit.removeAll(Cache.CONSTANT);
        return SUCCESS_TIP;
    }

    /**
     * 查看角色
     */
    @RequestMapping(value = "/view/{roleId}")
    @ResponseBody
    public Tip view(@PathVariable Long roleId) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        dubboRbacRoleService.getRole(roleId);
        return SUCCESS_TIP;
    }

    /**
     * 配置权限
     */
    @RequestMapping("/setAuthority")
    @BussinessLog(value = "配置权限", key = "roleId,ids", dict = Dict.RoleDict)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip setAuthority(@RequestParam("roleId") Long roleId, @RequestParam("ids") String ids) {
        if (ToolUtil.isOneEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        roleService.setAuthority(roleId, ids);
        return SUCCESS_TIP;
    }

    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/roleTreeList")
    @ResponseBody
    public List<ZTreeNode> roleTreeList(@PathVariable() Long roleId) {
        List<ZTreeNode> roleTreeList = dubboRbacRoleService.roleTreeList();
        roleTreeList.add(ZTreeNode.createParent());
        return roleTreeList;
    }

    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/roleTreeListByUserId/{userId}")
    @ResponseBody
    public List<ZTreeNode> roleTreeListByUserId(@PathVariable Long userId) {
        UserDto theUser = userService.selectByPrimaryKey(userId);
        String roleid = theUser.getRoleid();
        if (ToolUtil.isEmpty(roleid)) {
            List<ZTreeNode> roleTreeList = dubboRbacRoleService.roleTreeList();
            return roleTreeList;
        } else {
            String[] strArray = Convert.toStrArray(",", roleid);
            List<ZTreeNode> roleTreeListByUserId = dubboRbacRoleService.roleTreeListByRoleId(strArray);
            return roleTreeListByUserId;
        }
    }

    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/roleTreeListByRoleIds/{roleIds}")
    @ResponseBody
    public List<ZTreeNode> roleTreeList(@PathVariable("roleIds") String roleIds) {
        List<ZTreeNode> roleTreeList = dubboRbacRoleService.roleTreeListByRoleId(roleIds.split(","));
        roleTreeList.add(ZTreeNode.createParent());
        return roleTreeList;
    }
}
