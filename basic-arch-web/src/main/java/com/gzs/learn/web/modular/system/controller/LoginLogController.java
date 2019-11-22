package com.gzs.learn.web.modular.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gzs.learn.inf.PageResponseDto;
import com.gzs.learn.log.inf.UserLoginLogDto;
import com.gzs.learn.log.inf.search.UserLoginLogSearchDto;
import com.gzs.learn.web.common.annotion.Permission;
import com.gzs.learn.web.common.constant.Const;
import com.gzs.learn.web.common.controller.BaseController;
import com.gzs.learn.web.modular.biz.service.ISystemLogService;

/**
 * 日志管理的控制器
 */
@Controller
@RequestMapping("/loginLog")
public class LoginLogController extends BaseController {
    private static String PREFIX = "/system/log/";

    private ISystemLogService systemLogService;

    /**
     * 跳转到日志管理的首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "login_log.html";
    }

    /**
     * 查询登录日志列表
     */
    @RequestMapping("/list")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public PageResponseDto<UserLoginLogDto> list(UserLoginLogSearchDto loginLogSearchDto) {
        loginLogSearchDto.setPage(1);
        loginLogSearchDto.setPageSize(10);
        PageResponseDto<UserLoginLogDto> result = systemLogService.getLoginLogs(loginLogSearchDto);
        return result;
    }
}
