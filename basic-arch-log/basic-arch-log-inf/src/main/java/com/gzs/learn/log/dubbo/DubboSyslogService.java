package com.gzs.learn.log.dubbo;

import com.gzs.learn.log.inf.SysPerfLogDto;

/**
 * 系统日志
 * @author guanzhisong
 *
 */
public interface DubboSyslogService {
    boolean inserSyslog(SysPerfLogDto sysLogDto);
}
