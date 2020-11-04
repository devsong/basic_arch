package com.gzs.learn.serial.dubbo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gzs.learn.inf.PageResponseDto;
import com.gzs.learn.serial.exception.SerialCode;
import com.gzs.learn.serial.exception.SerialException;
import com.gzs.learn.serial.inf.SerialGroup;
import com.gzs.learn.serial.inf.SerialGroupSearch;
import com.gzs.learn.serial.inf.SerialPartition;
import com.gzs.learn.serial.inf.pk.SerialGroupPK;
import com.gzs.learn.serial.service.SerialManagerService;
import com.gzs.learn.serial.service.ZookeeperNotifyService;

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
}
