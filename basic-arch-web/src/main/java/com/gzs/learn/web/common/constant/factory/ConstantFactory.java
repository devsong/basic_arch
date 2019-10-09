package com.gzs.learn.web.common.constant.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.gzs.learn.rbac.dubbo.DubboRbacCommonService;
import com.gzs.learn.rbac.dubbo.DubboRbacDeptService;
import com.gzs.learn.rbac.dubbo.DubboRbacMenuService;
import com.gzs.learn.rbac.dubbo.DubboRbacRoleService;
import com.gzs.learn.rbac.dubbo.DubboRbacUserService;
import com.gzs.learn.rbac.inf.DeptDto;
import com.gzs.learn.rbac.inf.DictDto;
import com.gzs.learn.rbac.inf.MenuDto;
import com.gzs.learn.rbac.inf.NoticeDto;
import com.gzs.learn.rbac.inf.RoleDto;
import com.gzs.learn.rbac.inf.UserDto;
import com.gzs.learn.web.common.constant.enums.ManagerStatus;
import com.gzs.learn.web.common.constant.enums.MenuStatus;
import com.gzs.learn.web.common.persistence.model.Dept;
import com.gzs.learn.web.common.persistence.model.Dict;
import com.gzs.learn.web.core.log.LogObjectHolder;
import com.gzs.learn.web.core.support.StrKit;
import com.gzs.learn.web.core.util.Convert;
import com.gzs.learn.web.core.util.SpringContextHolder;
import com.gzs.learn.web.core.util.ToolUtil;

import tk.mybatis.mapper.entity.Example;

@Component
@DependsOn("springContextHolder")
public class ConstantFactory implements IConstantFactory {
    @Autowired
    private DubboRbacUserService dubboRbacUserService;

    @Autowired
    private DubboRbacMenuService dubboRbacMenuService;

    @Autowired
    private DubboRbacDeptService dubboRbacDeptService;

    @Autowired
    private DubboRbacRoleService dubboRbacRoleService;

    @Autowired
    private DubboRbacCommonService dubboRbacCommonService;

    public static IConstantFactory me() {
        return SpringContextHolder.getBean("constantFactory");
    }

    @Override
    public String getUserNameById(Long userId) {
        UserDto userDto = dubboRbacUserService.getUserById(userId);
        if (userDto != null) {
            return userDto.getName();
        }
        return "--";
    }

    @Override
    public String getUserAccountById(Long userId) {
        UserDto userDto = dubboRbacUserService.getUserById(userId);
        if (userDto != null) {
            return userDto.getAccount();
        }
        return "--";
    }

    @Override
    public String getRoleName(String roleIds) {
        Long[] roles = Convert.toLongArray(roleIds);
        StringBuilder sb = new StringBuilder();
        for (Long role : roles) {
            RoleDto roleObj = dubboRbacRoleService.getRole(role);
            if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
                sb.append(roleObj.getName()).append(",");
            }
        }
        return StrKit.removeSuffix(sb.toString(), ",");
    }

    @Override
    public String getSingleRoleName(Long roleId) {
        if (0 == roleId) {
            return "--";
        }
        RoleDto roleObj = dubboRbacRoleService.getRole(roleId);
        if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
            return roleObj.getName();
        }
        return "";
    }

    @Override
    public String getSingleRoleTip(Long roleId) {
        if (0 == roleId) {
            return "--";
        }
        RoleDto roleObj = dubboRbacRoleService.getRole(roleId);
        if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
            return roleObj.getTips();
        }
        return "";
    }

    @Override
    public String getDeptName(Long deptId) {
        DeptDto dept = dubboRbacDeptService.getDept(deptId);
        if (ToolUtil.isNotEmpty(dept) && ToolUtil.isNotEmpty(dept.getFullname())) {
            return dept.getFullname();
        }
        return "";
    }

    @Override
    public String getMenuNames(String menuIds) {
        Long[] menus = Convert.toLongArray(menuIds);
        List<MenuDto> menuDtos = dubboRbacMenuService.getMenu(Lists.newArrayList(menus));
        String menuName = menuDtos == null ? ""
                : menuDtos.stream().filter(e -> ToolUtil.isNotEmpty(e) && ToolUtil.isNotEmpty(e.getName())).map(e -> e.getName())
                        .collect(Collectors.joining(","));
        return menuName;
    }

    @Override
    public String getMenuName(Long menuId) {
        if (ToolUtil.isEmpty(menuId)) {
            return "";
        }
        MenuDto menu = dubboRbacMenuService.getMenu(menuId);
        if (menu == null) {
            return "";
        }
        return menu.getName();
    }

    @Override
    public String getMenuNameByCode(String code) {
        if (ToolUtil.isEmpty(code)) {
            return "";
        }
        MenuDto menu = dubboRbacMenuService.getMenuByCode(code);
        if (menu == null) {
            return "";
        }
        return menu.getName();
    }

    @Override
    public String getDictName(Integer dictId) {
        if (ToolUtil.isEmpty(dictId)) {
            return "";
        }
        DictDto dict = dubboRbacCommonService.getDict(dictId);
        if (dict == null) {
            return "";
        }
        return dict.getName();
    }

    @Override
    public String getNoticeTitle(Integer notictId) {
        if (ToolUtil.isEmpty(notictId)) {
            return "";
        }
        NoticeDto notice = dubboRbacCommonService.getNotice(notictId);
        if (notice == null) {
            return "";
        }
        return notice.getTitle();
    }

    @Override
    public String getDictsByName(String name, Integer val) {
        DictDto dict = dubboRbacCommonService.getDictByName(name);
        if (dict == null) {
            return "";
        }
        Example example = new Example(Dict.class);
        example.createCriteria().andEqualTo("pid", dict.getId());
        List<DictDto> dicts = dubboRbacCommonService.getDictByPid(dict.getId());
        for (DictDto item : dicts) {
            if (item.getNum() != null && item.getNum().equals(val)) {
                return item.getName();
            }
        }
        return "";
    }

    @Override
    public String getSexName(Integer sex) {
        return getDictsByName("性别", sex);
    }

    @Override
    public String getStatusName(Integer status) {
        return ManagerStatus.valueOf(status);
    }

    @Override
    public String getMenuStatusName(Integer status) {
        return MenuStatus.valueOf(status);
    }

    @Override
    public List<DictDto> findSubDict(Integer id) {
        if (ToolUtil.isEmpty(id)) {
            return null;
        }
        Example example = new Example(Dict.class);
        example.createCriteria().andEqualTo("pid", id);
        return dubboRbacCommonService.getDictByPid(id.longValue());
    }

    @Override
    public String getCacheObject(String para) {
        return LogObjectHolder.me().get().toString();
    }

    @Override
    public List<Long> getSubDeptId(Long deptid) {
        Example example = new Example(Dept.class);
        example.createCriteria().andLike("pids", "%[" + deptid + "]%");
        List<DeptDto> depts = dubboRbacDeptService.getSubDeptDtos(deptid);
        List<Long> deptids = new ArrayList<>();
        if (depts != null && depts.size() > 0) {
            for (DeptDto dept : depts) {
                deptids.add(dept.getId());
            }
        }

        return deptids;
    }

    /**
     * 获取所有父部门id
     */
    @Override
    public List<Long> getParentDeptIds(Long deptid) {
        DeptDto dept = dubboRbacDeptService.getDept(deptid);
        String pids = dept.getPids();
        String[] split = pids.split(",");
        List<Long> parentDeptIds = new ArrayList<>();
        for (String s : split) {
            parentDeptIds.add(Long.valueOf(StrKit.removeSuffix(StrKit.removePrefix(s, "["), "]")));
        }
        return parentDeptIds;
    }
}
