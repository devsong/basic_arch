package com.gzs.learn.serial.common;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {
    private static final AtomicInteger counter = new AtomicInteger(1);

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(SerialConst.THREAD_POOL_PREFIX + "-" + counter.getAndIncrement());
        return thread;
    }

}
