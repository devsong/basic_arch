package com.gzs.learn.web.modular.system.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gzs.learn.rbac.dubbo.DubboRbacMenuService;
import com.gzs.learn.rbac.dubbo.DubboRbacRoleService;
import com.gzs.learn.rbac.inf.MenuDto;
import com.gzs.learn.rbac.inf.ZTreeNode;
import com.gzs.learn.web.common.annotion.Permission;
import com.gzs.learn.web.common.annotion.log.BussinessLog;
import com.gzs.learn.web.common.constant.CommonResponse;
import com.gzs.learn.web.common.constant.Const;
import com.gzs.learn.web.common.constant.Dict;
import com.gzs.learn.web.common.constant.enums.MenuStatus;
import com.gzs.learn.web.common.constant.factory.ConstantFactory;
import com.gzs.learn.web.common.constant.tips.Tip;
import com.gzs.learn.web.common.controller.BaseController;
import com.gzs.learn.web.common.exception.BizExceptionEnum;
import com.gzs.learn.web.common.exception.BussinessException;
import com.gzs.learn.web.core.log.LogObjectHolder;
import com.gzs.learn.web.core.support.BeanKit;
import com.gzs.learn.web.core.util.Convert;
import com.gzs.learn.web.core.util.ToolUtil;
import com.gzs.learn.web.modular.system.wrapper.MenuWrapper;

/**
 * 菜单控制器
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {

    private static String PREFIX = "/system/menu/";

    @Autowired
    private DubboRbacMenuService dubboRbacMenuService;

    @Autowired
    private DubboRbacRoleService dubboRbacRoleService;

    /**
     * 跳转到菜单列表列表页面
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "menu.html";
    }

    /**
     * 跳转到菜单列表列表页面
     */
    @RequestMapping(value = "/menu_add")
    public String menuAdd() {
        return PREFIX + "menu_add.html";
    }

    /**
     * 跳转到菜单详情列表页面
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/menu_edit/{menuId}")
    public ModelAndView menuEdit(@PathVariable Long menuId, Model model) {
        if (ToolUtil.isEmpty(menuId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        MenuDto menu = dubboRbacMenuService.getMenu(menuId);
        // 获取父级菜单的id
        MenuDto pMenu = dubboRbacMenuService.getMenuByCode(menu.getPcode());
        ModelAndView mav = new ModelAndView(PREFIX + "menu_edit.html");
        // 如果父级是顶级菜单
        if (pMenu == null) {
            menu.setPcode("0");
        } else {
            // 设置父级菜单的code为父级菜单的id
            menu.setPcode(String.valueOf(pMenu.getId()));
        }

        Map<String, Object> menuMap = BeanKit.beanToMap(menu);
        menuMap.put("pcodeName", ConstantFactory.me().getMenuNameByCode(menu.getCode()));
        mav.addObject("menu", menuMap);
        LogObjectHolder.me().set(menu);
        return mav;
    }

    /**
     * 修该菜单
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/edit")
    @BussinessLog(value = "修改菜单", key = "name", dict = Dict.MenuDict)
    @ResponseBody
    public Tip edit(@Valid MenuDto menu, BindingResult result) {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        // 设置父级菜单编号
        menuSetPcode(menu);
        dubboRbacMenuService.updateMenu(menu);
        return SUCCESS_TIP;
    }

    /**
     * 获取菜单列表
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String menuName, @RequestParam(required = false) String level) {
        List<MenuDto> menus = dubboRbacMenuService.searchMenus(menuName, level);
        return CommonResponse.buildSuccess(super.warpObject(new MenuWrapper(menus)));
    }

    /**
     * 新增菜单
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/add")
    @BussinessLog(value = "菜单新增", key = "name", dict = Dict.MenuDict)
    @ResponseBody
    public Tip add(@Valid MenuDto menu, BindingResult result) {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }

        // 判断是否存在该编号
        String existedMenuName = ConstantFactory.me().getMenuNameByCode(menu.getCode());
        if (ToolUtil.isNotEmpty(existedMenuName)) {
            throw new BussinessException(BizExceptionEnum.EXISTED_THE_MENU);
        }

        // 设置父级菜单编号
        menuSetPcode(menu);
        menu.setStatus(MenuStatus.ENABLE.getCode());
        dubboRbacMenuService.insertMenu(menu);
        return SUCCESS_TIP;
    }

    /**
     * 删除菜单
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/remove")
    @BussinessLog(value = "删除菜单", key = "menuId", dict = Dict.DeleteDict)
    @ResponseBody
    public Tip remove(@RequestParam Long menuId) {
        if (ToolUtil.isEmpty(menuId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }

        // 缓存菜单的名称
        LogObjectHolder.me().set(ConstantFactory.me().getMenuName(menuId));
        dubboRbacMenuService.delMenuContainSubMenus(menuId);
        return SUCCESS_TIP;
    }

    /**
     * 查看菜单
     */
    @RequestMapping(value = "/view/{menuId}")
    @ResponseBody
    public Tip view(@PathVariable Long menuId) {
        if (ToolUtil.isEmpty(menuId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        dubboRbacMenuService.getMenu(menuId);
        return SUCCESS_TIP;
    }

    /**
     * 获取菜单列表(首页用)
     */
    @RequestMapping(value = "/menuTreeList")
    @ResponseBody
    public List<ZTreeNode> menuTreeList() {
        List<ZTreeNode> roleTreeList = dubboRbacMenuService.menuTreeList();
        return roleTreeList;
    }

    /**
     * 获取菜单列表(选择父级菜单用)
     */
    @RequestMapping(value = "/selectMenuTreeList")
    @ResponseBody
    public List<ZTreeNode> selectMenuTreeList() {
        List<ZTreeNode> roleTreeList = dubboRbacRoleService.roleTreeListByRoleIds(null);
        roleTreeList.add(ZTreeNode.createParent());
        return roleTreeList;
    }

    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/menuTreeListByRoleId/{roleId}")
    @ResponseBody
    public List<ZTreeNode> menuTreeListByRoleId(@PathVariable Long roleId) {
        List<Long> menuIds = dubboRbacMenuService.getMenuIdsByRoleId(roleId);
        if (ToolUtil.isEmpty(menuIds)) {
            List<ZTreeNode> roleTreeList = dubboRbacMenuService.menuTreeList();
            return roleTreeList;
        } else {
            List<ZTreeNode> roleTreeListByUserId = dubboRbacMenuService.menuTreeListByMenuIds(menuIds);
            return roleTreeListByUserId;
        }
    }

    /**
     * 根据请求的父级菜单编号设置pcode和层级
     */
    private void menuSetPcode(@Valid MenuDto menu) {
        if (ToolUtil.isEmpty(menu.getPcode()) || menu.getPcode().equals("0")) {
            menu.setPcode("0");
            menu.setPcodes("[0],");
            menu.setLevels(1);
        } else {
            Long code = Convert.toLong(menu.getPcode());
            MenuDto menuDto = dubboRbacMenuService.getMenu(code);
            Integer pLevels = menuDto.getLevels();
            menu.setPcode(menuDto.getCode());

            // 如果编号和父编号一致会导致无限递归
            if (menu.getCode().equals(menu.getPcode())) {
                throw new BussinessException(BizExceptionEnum.MENU_PCODE_COINCIDENCE);
            }

            menu.setLevels(pLevels + 1);
            menu.setPcodes(menuDto.getPcodes() + "[" + menuDto.getCode() + "],");
        }
    }

}
