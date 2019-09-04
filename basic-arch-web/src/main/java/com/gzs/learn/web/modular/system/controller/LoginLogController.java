package com.gzs.learn.web.modular.system.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gzs.learn.web.common.annotion.Permission;
import com.gzs.learn.web.common.annotion.log.BussinessLog;
import com.gzs.learn.web.common.constant.Const;
import com.gzs.learn.web.common.controller.BaseController;
import com.gzs.learn.web.common.page.PageReq;
import com.gzs.learn.web.common.persistence.model.logs.LoginLog;
import com.gzs.learn.web.modular.biz.bo.QueryLogBo;
import com.gzs.learn.web.modular.biz.service.ISystemLogService;

/**
 * 日志管理的控制器
 *
 * @author fengshuonan
 * @Date 2017年4月5日 19:45:36
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
    public Object list(QueryLogBo queryLogBo) {
        PageReq params = defaultPage();
        queryLogBo.setPageReq(params);
        List<LoginLog> result = systemLogService.getLoginLogs(queryLogBo);
        return packForBT(result);
    }

    /**
     * 清空日志
     */
    @BussinessLog("清空登录日志")
    @RequestMapping("/delLoginLog")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object delLog() {
        systemLogService.truncateLoginLog();
        return SUCCESS_TIP;
    }
}
