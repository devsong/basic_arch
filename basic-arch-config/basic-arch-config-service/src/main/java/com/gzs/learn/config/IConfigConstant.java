package com.gzs.learn.config;

public interface IConfigConstant {
    String ZK_GROUP = "/config";
    String ZK_LOCK = "/lock";
    String CONFIG_FORMAT = "/${product}_${groupName}_${app}_${configKey}";
    int RESERVE_CONFIG_ITEMS = 10;
}
