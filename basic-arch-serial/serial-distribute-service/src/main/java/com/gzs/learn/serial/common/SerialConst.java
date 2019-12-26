package com.gzs.learn.serial.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.gzs.learn.common.factory.NamedThreadFactory;

public interface SerialConst {
    String THREAD_POOL_PREFIX = "basic-arch-serial";

    ExecutorService POOLS = Executors.newFixedThreadPool(5, new NamedThreadFactory(THREAD_POOL_PREFIX));
}
