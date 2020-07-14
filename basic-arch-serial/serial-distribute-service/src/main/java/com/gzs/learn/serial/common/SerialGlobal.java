package com.gzs.learn.serial.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.gzs.learn.common.factory.NamedThreadFactory;
import com.gzs.learn.serial.ISerialConst;

/**
 * 全局的常量
 * @author guanzhisong
 *
 */
public interface SerialGlobal {
    ExecutorService POOLS = Executors.newFixedThreadPool(5, new NamedThreadFactory(ISerialConst.THREAD_POOL_PREFIX));
}
