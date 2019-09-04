package com.gzs.learn.serial.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public interface SerialConst {
    String THREAD_POOL_PREFIX = "serial";

    ExecutorService POOLS = Executors.newFixedThreadPool(5, new NamedThreadFactory());
}
