package com.gzs.learn.log;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.gzs.learn.common.factory.NamedThreadFactory;

public interface ILogConstant {
    /**
     * 默认数据查询范围
     */
    int DEFAULT_DATA_DURATION = 3;
    
    String MODULE_NAME = "basic-arch-log-server";

    Executor EXECUTOR = Executors.newFixedThreadPool(20, new NamedThreadFactory(MODULE_NAME));
}
