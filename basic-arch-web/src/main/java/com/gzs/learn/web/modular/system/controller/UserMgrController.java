package com.gzs.learn.web.modular.system.controller;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.naming.NoPermissionException;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Maps;
import com.gzs.learn.inf.PageResponseDto;
import com.gzs.learn.rbac.inf.DataScope;
import com.gzs.learn.rbac.inf.UserDto;
import com.gzs.learn.rbac.inf.UserSearchDto;
import com.gzs.learn.web.common.annotion.Permission;
import com.gzs.learn.web.common.annotion.log.BussinessLog;
import com.gzs.learn.web.common.constant.CommonResponse;
import com.gzs.learn.web.common.constant.Const;
import com.gzs.learn.web.common.constant.Dict;
import com.gzs.learn.web.common.constant.enums.ManagerStatus;
import com.gzs.learn.web.common.constant.factory.ConstantFactory;
import com.gzs.learn.web.common.constant.tips.Tip;
import com.gzs.learn.web.common.controller.BaseController;
import com.gzs.learn.web.common.exception.BizExceptionEnum;
import com.gzs.learn.web.common.exception.BussinessException;
import com.gzs.learn.web.common.page.PageInfoBT;
import com.gzs.learn.web.config.properties.GunsProperties;
import com.gzs.learn.web.core.log.LogObjectHolder;
import com.gzs.learn.web.core.shiro.ShiroKit;
import com.gzs.learn.web.core.shiro.ShiroUser;
import com.gzs.learn.web.core.util.ToolUtil;
import com.gzs.learn.web.modular.system.service.IUserService;
import com.gzs.learn.web.modular.system.wrapper.UserWarpper;

import lombok.extern.slf4j.Slf4j;

/**
 * 系统管理员控制器
 */
@Controller
@RequestMapping("/mgr")
@Slf4j
public class UserMgrController extends BaseController {
    private static String PREFIX = "/system/user/";
    @Autowired
    private GunsProperties gunsProperties;
    @Autowired
    private IUserService userService;

    /**
     * 跳转到查看管理员列表的页面
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "user.html";
    }

    /**
     * 跳转到查看管理员列表的页面
     */
    @RequestMapping("/user_add")
    public String addView() {
        return PREFIX + "user_add.html";
    }

    /**
     * 跳转到角色分配页面
     */
    @Permission
    @RequestMapping("/role_assign")
    public String roleAssign(Long userId, Model model) {
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        UserDto user = userService.selectByPrimaryKey(userId);
        model.addAttribute("userId", userId);
        model.addAttribute("userAccount", user.getAccount());
        return PREFIX + "user_roleassign.html";
    }

    /**
     * 跳转到编辑管理员页面
     */
    @Permission
    @RequestMapping("/user_edit")
    public String userEdit(Long id, Model model) {
        if (ToolUtil.isEmpty(id)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(id);
        UserDto user = userService.selectByPrimaryKey(id);
        model.addAttribute(user);
        model.addAttribute("roleName", ConstantFactory.me().getRoleName(user.getRoleid()));
        model.addAttribute("deptName", ConstantFactory.me().getDeptName(user.getDeptid()));
        LogObjectHolder.me().set(user);
        return PREFIX + "user_edit.html";
    }

    /**
     * 跳转到查看用户详情页面
     */
    @RequestMapping("/user_info")
    public String userInfo(Model model) {
        Long userId = ShiroKit.getUser().getId();
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        UserDto user = userService.selectByPrimaryKey(userId);
        user.setAvatar(gunsProperties.getFilePrefix() + user.getAvatar());
        model.addAttribute(user);
        model.addAttribute("roleName", ConstantFactory.me().getRoleName(user.getRoleid()));
        model.addAttribute("deptName", ConstantFactory.me().getDeptName(user.getDeptid()));
        LogObjectHolder.me().set(user);
        return "/frame/user_info.html";
    }

    /**
     * 跳转到修改密码界面
     */
    @RequestMapping("/user_chpwd")
    public String chPwd() {
        return PREFIX + "user_chpwd.html";
    }

    /**
     * 修改当前用户的密码
     */
    @RequestMapping("/changePwd")
    @ResponseBody
    public Object changePwd(@RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String repeatPassword) {
        if (!newPassword.equals(repeatPassword)) {
            throw new BussinessException(BizExceptionEnum.TWO_PWD_NOT_MATCH);
        }
        Long userId = ShiroKit.getUser().getId();
        UserDto user = userService.selectByPrimaryKey(userId);
        String oldMd5 = ShiroKit.md5(oldPassword, user.getSalt());
        if (user.getPassword().equals(oldMd5)) {
            String newMd5 = ShiroKit.md5(newPassword, user.getSalt());
            user.setPassword(newMd5);
            userService.updateByPrimaryKey(user);
            return SUCCESS_TIP;
        }
        throw new BussinessException(BizExceptionEnum.OLD_PWD_NOT_RIGHT);
    }

    /**
     * 查询管理员列表
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/list")
    @Permission
    @ResponseBody
    public PageInfoBT<Object> list(UserSearchDto userSearchDto) {
        if (StringUtils.isNotEmpty(userSearchDto.getTimeLimit())) {
            String[] split = userSearchDto.getTimeLimit().split(" - ");
            userSearchDto.setBeginTime(split[0]);
            userSearchDto.setEndTime(split[1]);
        }
        PageResponseDto<UserDto> users = null;
        if (ShiroKit.isAdmin()) {
            users = userService.selectUsers(null, userSearchDto);
        } else {
            Long userId = ShiroKit.getUser().getId();
            UserDto user = userService.selectByPrimaryKey(userId);
            DataScope dataScope = new DataScope(ShiroKit.getDeptDataScope(user));
            users = userService.selectUsers(dataScope, userSearchDto);
        }

        List<Object> obj = (List<Object>) new UserWarpper(users.getData()).warp();
        return new PageInfoBT<Object>(obj, users.getPage().getTotal());
    }

    /**
     * 添加管理员
     */
    @RequestMapping("/add")
    @BussinessLog(value = "添加管理员", key = "account", dict = Dict.UserDict)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip add(@Valid UserDto user, BindingResult result) {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }

        // 判断账号是否重复
        UserDto theUser = userService.getByAccount(user.getAccount());
        if (theUser != null) {
            throw new BussinessException(BizExceptionEnum.USER_ALREADY_REG);
        }
        theUser = new UserDto();
        try {
            BeanUtils.copyProperties(theUser, user);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("copy property error", e);
            throw new BussinessException(BizExceptionEnum.SERVER_ERROR);
        }
        // 完善账号信息
        String salt = ShiroKit.getRandomSalt(5);
        theUser.setSalt(salt);
        theUser.setPassword(ShiroKit.md5(user.getPassword(), salt));
        theUser.setStatus(ManagerStatus.OK.getCode());
        theUser.setCreatetime(new Date());

        userService.insert(theUser);
        return SUCCESS_TIP;
    }

    /**
     * 修改管理员
     *
     * @throws NoPermissionException
     */
    @RequestMapping("/edit")
    @BussinessLog(value = "修改管理员", key = "account", dict = Dict.UserDict)
    @ResponseBody
    public Tip edit(@Valid UserDto user, BindingResult result) throws NoPermissionException {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        if (ShiroKit.hasRole(Const.ADMIN_NAME)) {
            userService.updateByPrimaryKeySelective(user);
            return SUCCESS_TIP;
        }
        assertAuth(user.getId());
        ShiroUser shiroUser = ShiroKit.getUser();
        if (shiroUser.getId().equals(user.getId())) {
            userService.updateByPrimaryKeySelective(user);
            return SUCCESS_TIP;
        }
        throw new BussinessException(BizExceptionEnum.NO_PERMITION);
    }

    /**
     * 删除管理员（逻辑删除）
     */
    @RequestMapping("/delete")
    @BussinessLog(value = "删除管理员", key = "userId", dict = Dict.UserDict)
    @Permission
    @ResponseBody
    public Tip delete(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        // 不能删除超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new BussinessException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }
        assertAuth(userId);
        userService.setStatus(userId, ManagerStatus.DELETED.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 查看管理员详情
     */
    @RequestMapping("/view/{userId}")
    @ResponseBody
    public UserDto view(@PathVariable Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(userId);
        return userService.selectByPrimaryKey(userId);
    }

    /**
     * 重置管理员的密码
     */
    @RequestMapping("/reset")
    @BussinessLog(value = "重置管理员密码", key = "userId", dict = Dict.UserDict)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip reset(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(userId);
        UserDto user = userService.selectByPrimaryKey(userId);
        user.setSalt(ShiroKit.getRandomSalt(5));
        user.setPassword(ShiroKit.md5(Const.DEFAULT_PWD, user.getSalt()));
        userService.updateByPrimaryKey(user);
        return SUCCESS_TIP;
    }

    /**
     * 冻结用户
     */
    @RequestMapping("/freeze")
    @BussinessLog(value = "冻结用户", key = "userId", dict = Dict.UserDict)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip freeze(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        // 不能冻结超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new BussinessException(BizExceptionEnum.CANT_FREEZE_ADMIN);
        }
        assertAuth(userId);
        userService.setStatus(userId, ManagerStatus.FREEZED.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 解除冻结用户
     */
    @RequestMapping("/unfreeze")
    @BussinessLog(value = "解除冻结用户", key = "userId", dict = Dict.UserDict)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip unfreeze(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(userId);
        userService.setStatus(userId, ManagerStatus.OK.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 分配角色
     */
    @RequestMapping("/setRole")
    @BussinessLog(value = "分配角色", key = "userId,roleIds", dict = Dict.UserDict)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip setRole(@RequestParam("userId") Long userId, @RequestParam("roleIds") String roleIds) {
        if (ToolUtil.isOneEmpty(userId, roleIds)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        // 不能修改超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new BussinessException(BizExceptionEnum.CANT_CHANGE_ADMIN);
        }
        assertAuth(userId);
        userService.setRoles(userId, roleIds);
        return SUCCESS_TIP;
    }

    /**
     * 上传图片(上传到项目的webapp/static/img)
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public CommonResponse<Map<String, String>> upload(@RequestPart("file") MultipartFile picture) {
        String pictureName = UUID.randomUUID().toString() + ".jpg";
        try {
            String fileSavePath = gunsProperties.getFileUploadPath();
            picture.transferTo(new File(fileSavePath + pictureName));
            UserDto user = new UserDto();
            user.setAvatar(pictureName);
            user.setId(ShiroKit.getUser().getId());
            userService.updateByPrimaryKeySelective(user);
        } catch (Exception e) {
            throw new BussinessException(BizExceptionEnum.UPLOAD_ERROR);
        }
        Map<String, String> result = Maps.newHashMap();
        result.put("url", gunsProperties.getFilePrefix() + pictureName);
        return CommonResponse.buildSuccess(result);
    }

    /**
     * 判断当前登录的用户是否有操作这个用户的权限
     */
    private void assertAuth(Long userId) {
        UserDto user = userService.selectByPrimaryKey(userId);
        List<Long> deptDataScope = ShiroKit.getDeptDataScope(user);
        Long deptid = user.getDeptid();
        if (deptDataScope.contains(deptid)) {
            return;
        }
        throw new BussinessException(BizExceptionEnum.NO_PERMITION);
    }

    /**
     * 获取当前用户详情
     */
    @RequestMapping("/currentUserInfo")
    @ResponseBody
    public CommonResponse<UserDto> currentUserInfo() {
        ShiroUser currentUser = ShiroKit.getUser();
        UserDto user = userService.selectByPrimaryKey(currentUser.getId());
        UserDto userDto = new UserDto();
        try {
            BeanUtils.copyProperties(userDto, user);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        userDto.setAvatar(gunsProperties.getFilePrefix() + userDto.getAvatar());
        userDto.setRoleName(ConstantFactory.me().getRoleName(user.getRoleid()));
        userDto.setDeptName(ConstantFactory.me().getDeptName(user.getDeptid()));
        return CommonResponse.buildSuccess(userDto);
    }

    /**
     * 获取当前用户详情
     */
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public CommonResponse<UserDto> getUserInfo(Long id) {
        UserDto user = userService.selectByPrimaryKey(id);
        UserDto userDto = new UserDto();
        try {
            BeanUtils.copyProperties(userDto, user);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        userDto.setAvatar(gunsProperties.getFilePrefix() + userDto.getAvatar());
        userDto.setRoleName(ConstantFactory.me().getRoleName(user.getRoleid()));
        userDto.setDeptName(ConstantFactory.me().getDeptName(user.getDeptid()));
        return CommonResponse.buildSuccess(userDto);
    }
}
