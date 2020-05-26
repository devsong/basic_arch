package com.ruoyi.common.config.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author guanzhisong
 *
 */
public class NamedThreadFactory implements ThreadFactory {
    private final AtomicInteger threadNumber = new AtomicInteger(1);

    private String threadPoolName;

    public NamedThreadFactory() {
        this.threadPoolName = "ruoyi-pool";
    }

    public NamedThreadFactory(String name) {
        this.threadPoolName = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(threadPoolName + '-' + threadNumber.getAndIncrement());
        return thread;
    }
}
