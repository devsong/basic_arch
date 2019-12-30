package com.gzs.learn.serial.dubbo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gzs.learn.serial.common.Result;
import com.gzs.learn.serial.common.Status;
import com.gzs.learn.serial.conf.SerialProperties;
import com.gzs.learn.serial.exception.SerialException;
import com.gzs.learn.serial.service.SerialDistributeService;
import com.gzs.learn.serial.service.snowflake.SnowflakeIDGenImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("dubboSerialDistributeService")
public class DubboSerialDistributeServiceImp implements DubboSerialDistributeService {
    @Autowired
    private SerialProperties serialProperties;
    
    @Autowired
    private SerialDistributeService serialDistributeService;

    @Autowired
    private SnowflakeIDGenImpl snowflakeIDGenImpl;

    /**
     * 获取序列号
     *
     * @param key
     * @param length
     * @return
     */
    @Override
    public long getSerial(String key, int length) {
        try {
            return this.serialDistributeService.getSerial(key, length);
        } catch (Exception e) {
            log.error("get serial error,key:[{}]", key, e);
            throw e;
        }
    }

    @Override
    public long getSnowflake() {
        Result result = snowflakeIDGenImpl.get();
        if (result.getStatus() == Status.SUCCESS) {
            return result.getId();
        }
        throw new SerialException(result.getStatus().name());
    }
}
