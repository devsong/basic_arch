package com.gzs.learn.serial.service.imp;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gzs.learn.serial.exception.SerialCode;
import com.gzs.learn.serial.exception.SerialException;
import com.gzs.learn.serial.inf.DataStatus;
import com.gzs.learn.serial.inf.SerialGroup;
import com.gzs.learn.serial.inf.pk.SerialGroupPK;
import com.gzs.learn.serial.po.SerialGroupPo;
import com.gzs.learn.serial.po.SerialPartitionPo;
import com.gzs.learn.serial.repository.SerialGroupMapper;
import com.gzs.learn.serial.repository.SerialPartitionMapper;
import com.gzs.learn.serial.service.SerialManagerService;
import com.gzs.learn.serial.service.ZookeeperNotifyService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

@Component
@Slf4j
public class SerialManagerServiceImp implements SerialManagerService {

    @Autowired
    private SerialGroupMapper serialGroupMapper;

    @Autowired
    private SerialPartitionMapper serialPartitionMapper;

    @Autowired
    private ZookeeperNotifyService zookeeperNotifyService;

    @Override
    public void init() {
        Example enableExample = new Example(SerialGroupPo.class);
        enableExample.createCriteria().andEqualTo("stat", DataStatus.ENABLE.calculate());
        List<SerialGroupPo> groups = serialGroupMapper.selectByExample(enableExample);
        List<SerialGroupPK> primaryKeys = groups.stream().map(e -> new SerialGroupPK(e.getName(), e.getVersion()))
                .collect(Collectors.toList());
        for (SerialGroupPK pk : primaryKeys) {
            if (this.zookeeperNotifyService.ceateNode(pk)) {
                log.info("create node [{}_{}] success!", pk.getName(), pk.getVersion());
            } else {
                log.info("create node [{}_{}] failed!", pk.getName(), pk.getVersion());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SerialGroupPK createSerialGroup(SerialGroup group) throws SerialException {
        try {
            group.check();
            int maxVersion = findMaxVersion(group);
            if (group.getVersion() == 0) {
                group.setVersion(maxVersion + 1);
            }
            SerialGroupPo serialGroupPo = new SerialGroupPo();
            BeanUtils.copyProperties(group, serialGroupPo);
            serialGroupPo.setStat(group.getStat().calculate());

            int row = serialGroupMapper.insertSelective(serialGroupPo);

            if (row != 1) {
                throw new SerialException(SerialCode.SERIAL_CREATE_ERROR);
            }
            this.createSerialPartition(group);
            return new SerialGroupPK(group.getName(), group.getVersion());
        } catch (Exception e) {
            log.error("create serial error", e);
            throw e;
        }
    }

    private void createSerialPartition(SerialGroup group) throws SerialException {
        long length = (group.getMax() - group.getMin()) / group.getPart();
        long min = group.getMin() - 1;
        long max = min + length;
        for (int index = 0; index < group.getPart(); index++) {
            SerialPartitionPo partition = new SerialPartitionPo();
            partition.setName(group.getName());
            partition.setPart(index);
            partition.setVersion(group.getVersion());
            partition.setStat(group.getStat().calculate());
            partition.setMin(min + index * length + 1);
            partition.setMax(max + index * length);
            partition.setUsed(partition.getMin());
            int row = serialPartitionMapper.insertSelective(partition);
            if (row != 1) {
                throw new SerialException(SerialCode.SERIAL_PARTITION_ERROR);
            }
        }
    }

    private int findMaxVersion(SerialGroup group) {
        Example enableExample = new Example(SerialGroupPo.class);
        enableExample.createCriteria().andEqualTo("name", group.getName());
        List<SerialGroupPo> groups = serialGroupMapper.selectByExample(enableExample);
        int maxVersion = 0;
        for (SerialGroupPo version : groups) {
            // 相同版本号
            if (version.getVersion() == group.getVersion()) {
                throw new SerialException(SerialCode.SERIAL_ALREADY_EXISTS,
                        String.format(" name=[%s],version=[%d].", version.getName(), version.getVersion()));
            }
            // 与已有版本存在区间冲突
            if (!(group.getMax() < version.getMin() || group.getMin() > version.getMax())) {
                throw new SerialException(SerialCode.SERIAL_RANGE_ERROR, String.format(" name=[%s],version=[%d],Range=[%d~%d].",
                        version.getName(), version.getVersion(), version.getMin(), version.getMax()));
            }
            maxVersion = maxVersion > version.getVersion() ? maxVersion : version.getVersion();
        }
        return maxVersion;
    }
}
