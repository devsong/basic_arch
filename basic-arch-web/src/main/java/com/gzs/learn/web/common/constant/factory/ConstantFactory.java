package com.gzs.learn.web.common.constant.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.gzs.learn.rbac.dubbo.DubboRbacMenuService;
import com.gzs.learn.rbac.dubbo.DubboRbacUserService;
import com.gzs.learn.rbac.inf.DeptDto;
import com.gzs.learn.rbac.inf.MenuDto;
import com.gzs.learn.rbac.inf.RoleDto;
import com.gzs.learn.rbac.inf.UserDto;
import com.gzs.learn.web.common.constant.enums.ManagerStatus;
import com.gzs.learn.web.common.constant.enums.MenuStatus;
import com.gzs.learn.web.common.persistence.dao.DeptMapper;
import com.gzs.learn.web.common.persistence.dao.DictMapper;
import com.gzs.learn.web.common.persistence.dao.MenuMapper;
import com.gzs.learn.web.common.persistence.dao.NoticeMapper;
import com.gzs.learn.web.common.persistence.model.Dept;
import com.gzs.learn.web.common.persistence.model.Dict;
import com.gzs.learn.web.common.persistence.model.Menu;
import com.gzs.learn.web.common.persistence.model.Notice;
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

    private DeptMapper deptMapper = SpringContextHolder.getBean(DeptMapper.class);
    private DictMapper dictMapper = SpringContextHolder.getBean(DictMapper.class);
    private MenuMapper menuMapper = SpringContextHolder.getBean(MenuMapper.class);
    private NoticeMapper noticeMapper = SpringContextHolder.getBean(NoticeMapper.class);

    public static IConstantFactory me() {
        return SpringContextHolder.getBean("constantFactory");
    }

    @Override
    public String getUserNameById(Integer userId) {
        UserDto userDto = dubboRbacUserService.getUserById(userId.longValue());
        if (userDto != null) {
            return userDto.getName();
        }
        return "--";
    }

    @Override
    public String getUserAccountById(Integer userId) {
        UserDto userDto = dubboRbacUserService.getUserById(userId.longValue());
        if (userDto != null) {
            return userDto.getAccount();
        }
        return "--";
    }

    @Override
    public String getRoleName(String roleIds) {
        Integer[] roles = Convert.toIntArray(roleIds);
        StringBuilder sb = new StringBuilder();
        for (int role : roles) {
            RoleDto roleObj = dubboRbacUserService.getRole(role);
            if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
                sb.append(roleObj.getName()).append(",");
            }
        }
        return StrKit.removeSuffix(sb.toString(), ",");
    }

    @Override
    public String getSingleRoleName(Integer roleId) {
        if (0 == roleId) {
            return "--";
        }
        RoleDto roleObj = dubboRbacUserService.getRole(roleId);
        if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
            return roleObj.getName();
        }
        return "";
    }

    @Override
    public String getSingleRoleTip(Integer roleId) {
        if (0 == roleId) {
            return "--";
        }
        RoleDto roleObj = dubboRbacUserService.getRole(roleId);
        if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
            return roleObj.getTips();
        }
        return "";
    }

    @Override
    public String getDeptName(Integer deptId) {
        DeptDto dept = dubboRbacUserService.getDept(deptId);
        if (ToolUtil.isNotEmpty(dept) && ToolUtil.isNotEmpty(dept.getFullname())) {
            return dept.getFullname();
        }
        return "";
    }

    @Override
    public String getMenuNames(String menuIds) {
        Integer[] menus = Convert.toIntArray(menuIds);
        List<MenuDto> menuDtos = dubboRbacMenuService.getMenu(Lists.newArrayList(menus));
        String menuName = menuDtos == null ? ""
                : menuDtos.stream().filter(e -> ToolUtil.isNotEmpty(e) && ToolUtil.isNotEmpty(e.getName())).map(e -> e.getName())
                        .collect(Collectors.joining(","));
        return menuName;
    }

    @Override
    public String getMenuName(Integer menuId) {
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
        Menu param = new Menu();
        param.setCode(code);
        Menu menu = menuMapper.selectOne(param);
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
        Dict dict = dictMapper.selectByPrimaryKey(dictId);
        if (dict == null) {
            return "";
        }
        return dict.getName();
    }

    @Override
    public String getNoticeTitle(Integer dictId) {
        if (ToolUtil.isEmpty(dictId)) {
            return "";
        }
        Notice notice = noticeMapper.selectByPrimaryKey(dictId);
        if (notice == null) {
            return "";
        }
        return notice.getTitle();
    }

    @Override
    public String getDictsByName(String name, Integer val) {
        Dict temp = new Dict();
        temp.setName(name);
        Dict dict = dictMapper.selectOne(temp);
        if (dict == null) {
            return "";
        }
        Example example = new Example(Dict.class);
        example.createCriteria().andEqualTo("pid", dict.getId());
        List<Dict> dicts = dictMapper.selectByExample(example);
        for (Dict item : dicts) {
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
    public List<Dict> findSubDict(Integer id) {
        if (ToolUtil.isEmpty(id)) {
            return null;
        }
        Example example = new Example(Dict.class);
        example.createCriteria().andEqualTo("pid", id);
        return dictMapper.selectByExample(example);
    }

    @Override
    public String getCacheObject(String para) {
        return LogObjectHolder.me().get().toString();
    }

    @Override
    public List<Integer> getSubDeptId(Integer deptid) {
        Example example = new Example(Dept.class);
        example.createCriteria().andLike("pids", "%[" + deptid + "]%");
        List<Dept> depts = this.deptMapper.selectByExample(example);

        ArrayList<Integer> deptids = new ArrayList<>();

        if (depts != null && depts.size() > 0) {
            for (Dept dept : depts) {
                deptids.add(dept.getId().intValue());
            }
        }

        return deptids;
    }

    /**
     * 获取所有父部门id
     */
    @Override
    public List<Integer> getParentDeptIds(Integer deptid) {
        Dept dept = deptMapper.selectByPrimaryKey(deptid);
        String pids = dept.getPids();
        String[] split = pids.split(",");
        ArrayList<Integer> parentDeptIds = new ArrayList<>();
        for (String s : split) {
            parentDeptIds.add(Integer.valueOf(StrKit.removeSuffix(StrKit.removePrefix(s, "["), "]")));
        }
        return parentDeptIds;
    }
}
