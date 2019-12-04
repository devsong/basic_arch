package com.gzs.learn.web.modular.system.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gzs.learn.inf.PageResponseDto;
import com.gzs.learn.log.inf.UserOperationLogDto;
import com.gzs.learn.log.inf.search.UserOperationLogSearchDto;
import com.gzs.learn.web.common.annotion.Permission;
import com.gzs.learn.web.common.annotion.log.BussinessLog;
import com.gzs.learn.web.common.constant.Const;
import com.gzs.learn.web.common.controller.BaseController;
import com.gzs.learn.web.core.support.BeanKit;
import com.gzs.learn.web.modular.biz.service.ISystemLogService;
import com.gzs.learn.web.modular.system.wrapper.LogWrapper;

/**
 * 日志管理的控制器
 */
@Controller
@RequestMapping("/log")
public class LogController extends BaseController {
    private static String PREFIX = "/system/log/";

    @Autowired
    private ISystemLogService systemLogService;

    /**
     * 跳转到日志管理的首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "log.html";
    }

    /**
     * 查询操作日志列表
     */
    @RequestMapping("/list")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public PageResponseDto<UserOperationLogDto> list(UserOperationLogSearchDto operationLogSearchDto) {
        PageResponseDto<UserOperationLogDto> result = systemLogService.getOperationLogs(operationLogSearchDto);
        return result;
    }

    /**
     * 查询操作日志详情
     */
    @RequestMapping("/detail/{id}")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object detail(@PathVariable Long id) {
        UserOperationLogDto operationLog = systemLogService.getOperationLogDetail(id);
        Map<String, Object> stringObjectMap = BeanKit.beanToMap(operationLog);
        return super.warpObject(new LogWrapper(stringObjectMap));
    }

    /**
     * 清空日志
     */
    @BussinessLog(value = "清空业务日志")
    @RequestMapping("/delLog")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object delLog() {
        // systemLogService.truncateBizLog();
        return SUCCESS_TIP;
    }
}
