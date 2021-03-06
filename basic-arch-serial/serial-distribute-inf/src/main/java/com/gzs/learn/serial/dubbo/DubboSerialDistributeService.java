package com.gzs.learn.serial.dubbo;

public interface DubboSerialDistributeService {

    /**
     * 获取序列号
     */
    long getSerial(String key, int length);

    /**
     * snowflake算法
     * @return
     */
    long getSnowflake();

}
