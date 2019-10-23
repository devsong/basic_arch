package com.gzs.learn.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.gzs.learn.common.factory.NamedThreadFactory;

public interface IConfigConstant {
    String MODULE_NAME = "basic-arch-config-server";
    String ZK_GROUP = "/config";
    String ZK_LOCK = "/lock";
    String CONFIG_FORMAT = "/${product}_${groupName}_${app}_${configKey}";
    int RESERVE_CONFIG_ITEMS = 10;

    Executor EXECUTOR = Executors.newFixedThreadPool(20, new NamedThreadFactory(MODULE_NAME));
}
