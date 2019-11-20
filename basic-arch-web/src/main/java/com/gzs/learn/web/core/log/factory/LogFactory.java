package com.gzs.learn.web.core.log.factory;

import java.util.Date;

import com.gzs.learn.log.inf.UserLoginLogDto;
import com.gzs.learn.log.inf.UserOperationLogDto;
import com.gzs.learn.web.common.constant.enums.LogSucceed;
import com.gzs.learn.web.common.constant.enums.LogType;

/**
 * 日志对象创建工厂
 *
 * @author fengshuonan
 * @date 2016年12月6日 下午9:18:27
 */
public class LogFactory {

    /**
     * 创建操作日志
     *
     * @author fengshuonan
     * @Date 2017/3/30 18:45
     */
    public static UserOperationLogDto createOperationLog(LogType logType, Long userId, String bussinessName, String clazzName,
            String methodName, String msg, LogSucceed succeed) {
        UserOperationLogDto operationLog = new UserOperationLogDto();
        operationLog.setLogtype(logType.getMessage());
        operationLog.setLogname(bussinessName);
        operationLog.setUserid(userId);
        operationLog.setClassname(clazzName);
        operationLog.setMethod(methodName);
        operationLog.setCreateTime(new Date());
        operationLog.setSucceed(succeed.getMessage());
        operationLog.setMessage(msg);
        operationLog.setOperatorIp("");
        return operationLog;
    }

    /**
     * 创建登录日志
     *
     * @author fengshuonan
     * @Date 2017/3/30 18:46
     */
    public static UserLoginLogDto createLoginLog(LogType logType, Long userId, String msg, String ip) {
        UserLoginLogDto loginLog = new UserLoginLogDto();
        loginLog.setLogname(logType.getMessage());
        loginLog.setUserid(userId);
        loginLog.setCreateTime(new Date());
        loginLog.setSucceed(LogSucceed.SUCCESS.getMessage());
        loginLog.setOperatorIp(ip);
        loginLog.setMessage(msg);
        return loginLog;
    }
}
