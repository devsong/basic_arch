package com.gzs.learn.web.modular.system.controller;

import static com.gzs.learn.web.core.support.HttpKit.getIp;

import java.util.List;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.gzs.learn.common.util.IpUtil;
import com.gzs.learn.rbac.inf.MenuNodeDto;
import com.gzs.learn.rbac.inf.UserDto;
import com.gzs.learn.web.common.controller.BaseController;
import com.gzs.learn.web.common.exception.InvalidKaptchaException;
import com.gzs.learn.web.config.properties.GunsProperties;
import com.gzs.learn.web.core.log.LogManager;
import com.gzs.learn.web.core.log.factory.LogTaskFactory;
import com.gzs.learn.web.core.shiro.ShiroKit;
import com.gzs.learn.web.core.shiro.ShiroUser;
import com.gzs.learn.web.core.util.ToolUtil;
import com.gzs.learn.web.modular.system.service.IUserService;

/**
 * 登录控制器
 */
@Controller
public class LoginController extends BaseController {
    @Autowired
    private IUserService userService;

    @Autowired
    private GunsProperties gunsProperties;

    /**
     * 跳转到主页
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("/index.html");
        // 获取菜单列表
        List<Long> roleList = ShiroKit.getUser().getRoleList();
        if (roleList == null || roleList.size() == 0) {
            ShiroKit.getSubject().logout();
            mav = new ModelAndView("/login.html");
            mav.addObject("tips", "该用户没有角色，无法登陆");
            return mav;
        }
        List<MenuNodeDto> menus = userService.getMenusByRoleIds(roleList);
        List<MenuNodeDto> titles = MenuNodeDto.buildTitle(menus);
        mav.addObject("menus", titles);
        // 获取用户头像
        Long id = ShiroKit.getUser().getId();
        UserDto user = userService.selectByPrimaryKey(id);
        String avatar = user.getAvatar();
        mav.addObject("name", user.getName());
        mav.addObject("avatar", gunsProperties.getFilePrefix() + avatar);
        return mav;
    }

    /**
     * 跳转到登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        if (ShiroKit.isAuthenticated() || ShiroKit.getUser() != null) {
            return REDIRECT + "/";
        } else {
            return "/login.html";
        }
    }

    /**
     * 点击登录执行的动作
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginVali() {
        String username = super.getPara("username").trim();
        String password = super.getPara("password").trim();

        // 验证验证码是否正确
        if (ToolUtil.getKaptchaOnOff()) {
            String kaptcha = super.getPara("kaptcha").trim();
            String code = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (ToolUtil.isEmpty(kaptcha) || !kaptcha.equals(code)) {
                throw new InvalidKaptchaException();
            }
        }

        Subject currentUser = ShiroKit.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());
        token.setRememberMe(true);

        currentUser.login(token);

        ShiroUser shiroUser = ShiroKit.getUser();
        super.getSession().setAttribute("shiroUser", shiroUser);
        super.getSession().setAttribute("username", shiroUser.getAccount());

        LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), IpUtil.getIpAdrress(getHttpServletRequest())));
        ShiroKit.setSessionAttr("sessionFlag", true);
        return REDIRECT + "/";
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logOut() {
        LogManager.me().executeLog(LogTaskFactory.exitLog(ShiroKit.getUser().getId(), getIp()));
        ShiroKit.getSubject().logout();
        return REDIRECT + "/login";
    }
}
