package com.gzs.learn.web.core.log.factory;

import static com.gzs.learn.web.common.constant.enums.LogSucceed.FAIL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gzs.learn.log.inf.UserLoginLogDto;
import com.gzs.learn.log.inf.UserOperationLogDto;
import com.gzs.learn.web.common.constant.enums.LogSucceed;
import com.gzs.learn.web.common.constant.enums.LogType;
import com.gzs.learn.web.core.log.LogManager;
import com.gzs.learn.web.core.util.SpringContextHolder;
import com.gzs.learn.web.core.util.ToolUtil;
import com.gzs.learn.web.modular.biz.service.ISystemLogService;

/**
 * 日志操作任务创建工厂
 */
public class LogTaskFactory {
    private static Logger logger = LoggerFactory.getLogger(LogManager.class);
    private static ISystemLogService systemLogService = SpringContextHolder.getBean(ISystemLogService.class);

    public static Runnable loginLog(final Long userId, final String ip) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    UserLoginLogDto loginLog = LogFactory.createLoginLog(LogType.LOGIN, userId, null, ip);
                    systemLogService.saveLoginLog(loginLog);
                } catch (Exception e) {
                    logger.error("创建登录日志异常!", e);
                }
            }
        };
    }

    public static Runnable loginLog(final String username, final String msg, final String ip) {
        return new Runnable() {
            @Override
            public void run() {
                UserLoginLogDto loginLog = LogFactory.createLoginLog(LogType.LOGIN_FAIL, null, "账号:" + username + "," + msg, ip);
                try {
                    systemLogService.saveLoginLog(loginLog);
                } catch (Exception e) {
                    logger.error("创建登录失败异常!", e);
                }
            }
        };
    }

    public static Runnable exitLog(final Long userId, final String ip) {
        return new Runnable() {
            @Override
            public void run() {
                UserLoginLogDto loginLog = LogFactory.createLoginLog(LogType.EXIT, userId, null, ip);
                try {
                    systemLogService.saveLoginLog(loginLog);
                } catch (Exception e) {
                    logger.error("创建退出日志异常!", e);
                }
            }
        };
    }

    public static Runnable bussinessLog(final Long userId, final String bussinessName, final String clazzName, final String methodName,
            final String msg) {
        return new Runnable() {
            @Override
            public void run() {
                UserOperationLogDto operationLog = LogFactory.createOperationLog(LogType.BUSSINESS, userId, bussinessName, clazzName,
                        methodName, msg, LogSucceed.SUCCESS);
                try {
                    systemLogService.saveOperationLog(operationLog);
                } catch (Exception e) {
                    logger.error("创建业务日志异常!", e);
                }
            }
        };
    }

    public static Runnable exceptionLog(final Long userId, final Exception exception) {
        return new Runnable() {
            @Override
            public void run() {
                String msg = ToolUtil.getExceptionMsg(exception);
                UserOperationLogDto operationLog = LogFactory.createOperationLog(LogType.EXCEPTION, userId, "", null, null, msg, FAIL);
                try {
                    systemLogService.saveOperationLog(operationLog);
                } catch (Exception e) {
                    logger.error("创建异常日志异常!", e);
                }
            }
        };
    }
}
