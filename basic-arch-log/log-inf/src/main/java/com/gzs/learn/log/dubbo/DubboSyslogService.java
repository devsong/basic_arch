package com.gzs.learn.log.dubbo;

import com.gzs.learn.log.inf.SysLogDto;

/**
 * 系统日志
 * @author guanzhisong
 *
 */
public interface DubboSyslogService {
    boolean inserSyslog(SysLogDto sysLogDto);
}
