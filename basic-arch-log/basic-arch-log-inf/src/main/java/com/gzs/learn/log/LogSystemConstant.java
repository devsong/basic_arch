package com.gzs.learn.log;

import com.gzs.learn.inf.GlobalConstant;

public interface LogSystemConstant {
    int CODE_SUCCESS = 0;
    int CODE_SYS_EXCEPTION = -1;
    int CODE_BIZ_EXCEPTION = 1;

    String LOG_SYSTEM_PREFIX = GlobalConstant.SYSTEM_PACKAGE_PREFIX + ".log";
}
