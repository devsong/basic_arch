package com.gzs.learn.serial.dubbo;

import static com.gzs.learn.serial.service.snowflake.SnowflakeConst.DATA_CENTER_ID_BITS;
import static com.gzs.learn.serial.service.snowflake.SnowflakeConst.DATA_CENTER_ID_SHIFT;
import static com.gzs.learn.serial.service.snowflake.SnowflakeConst.SEQUENCE_BITS;
import static com.gzs.learn.serial.service.snowflake.SnowflakeConst.TIMESTAMP_SHIFT;
import static com.gzs.learn.serial.service.snowflake.SnowflakeConst.WORKER_ID_BITS;
import static com.gzs.learn.serial.service.snowflake.SnowflakeConst.WORKER_ID_SHIFT;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gzs.learn.inf.PageResponseDto;
import com.gzs.learn.serial.exception.SerialCode;
import com.gzs.learn.serial.exception.SerialException;
import com.gzs.learn.serial.inf.SerialGroup;
import com.gzs.learn.serial.inf.SerialGroupSearch;
import com.gzs.learn.serial.inf.SerialPartition;
import com.gzs.learn.serial.inf.SerialSnowflakeInfo;
import com.gzs.learn.serial.inf.pk.SerialGroupPK;
import com.gzs.learn.serial.service.SerialManagerService;
import com.gzs.learn.serial.service.ZookeeperNotifyService;
import com.gzs.learn.serial.service.snowflake.SnowflakeConst;

import lombok.extern.slf4j.Slf4j;

@Component("dubboSerialManagerService")
@Slf4j
public class DubboSerialManagerServiceImpl implements DubboSerialManagerService {
    @Autowired
    private SerialManagerService serialManagerService;
    @Autowired
    private ZookeeperNotifyService zookeeperNotifyService;

    @Override
    public void createSerialGroup(SerialGroup group) throws SerialException {
        SerialGroupPK pk = this.serialManagerService.createSerialGroup(group);
        if (!this.zookeeperNotifyService.ceateNode(pk)) {
            log.error("send primary key to zk error {}", pk);
            throw new SerialException(SerialCode.SERIAL_CREATE_ERROR);
        }
    }

    @Override
    public PageResponseDto<SerialGroup> getSerialGroup(SerialGroupSearch search) {
        return null;
    }

    @Override
    public PageResponseDto<SerialPartition> getSerialPartition(long groupId) {
        return null;
    }

    @Override
    public SerialSnowflakeInfo decodeSnowflake(long id) {
        long seqId = id ^ (id >> SEQUENCE_BITS << SEQUENCE_BITS);
        long workerId = (id >> WORKER_ID_SHIFT) ^ (id >> (WORKER_ID_SHIFT + WORKER_ID_BITS) << WORKER_ID_BITS);
        long dataCenterId = (id >> DATA_CENTER_ID_SHIFT) ^ (id >> (DATA_CENTER_ID_SHIFT + DATA_CENTER_ID_BITS) << DATA_CENTER_ID_BITS);
        long timestamp = (id >> TIMESTAMP_SHIFT) + SnowflakeConst.TWEPOCH;
        Date time = new Date(timestamp);
        String timeStr = DateFormatUtils.format(time, "yyyy-MM-dd HH:mm:ss.SSS");
        SerialSnowflakeInfo info = new SerialSnowflakeInfo();
        info.setSeq(seqId);
        info.setWorkerId(workerId);
        info.setDataCenterId(dataCenterId);
        info.setTimestamp(timestamp);
        info.setTime(timeStr);
        return info;
    }

}
