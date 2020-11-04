package com.gzs.learn.serial.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.gzs.learn.common.factory.NamedThreadFactory;
import com.gzs.learn.serial.ISerialConst;

/**
 * 全局的常量
 * @author guanzhisong
 *
 */
public interface SerialGlobal {
    int DEFAULT_THREAD_COUNT = 5;

    // 简单的全局线程池实现
    ExecutorService POOLS = new ThreadPoolExecutor(DEFAULT_THREAD_COUNT, DEFAULT_THREAD_COUNT, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(DEFAULT_THREAD_COUNT * 100), new NamedThreadFactory(ISerialConst.THREAD_POOL_PREFIX),
            new ThreadPoolExecutor.CallerRunsPolicy());
}
