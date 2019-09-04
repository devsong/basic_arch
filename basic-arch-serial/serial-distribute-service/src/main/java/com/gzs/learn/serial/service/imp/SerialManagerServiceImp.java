package com.gzs.learn.serial.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;
import com.gzs.learn.serial.domain.GroupListenerNode;
import com.gzs.learn.serial.domain.IndexNode;
import com.gzs.learn.serial.domain.SerialNode;
import com.gzs.learn.serial.inf.DataStatus;
import com.gzs.learn.serial.po.SerialGroupPo;
import com.gzs.learn.serial.po.SerialPartitionPo;
import com.gzs.learn.serial.repository.SerialGroupMapper;
import com.gzs.learn.serial.repository.SerialPartitionMapper;
import com.gzs.learn.serial.service.SerialDistributeService;
import com.gzs.learn.serial.service.SerialManagerService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

@Component
@Slf4j
public class SerialManagerServiceImp implements SerialManagerService {
    private static final String NODE_KEY_FORMAT = "%s,%d";

    private static final int CACHE_LENGTH = 10;

    private static final int LIMIT_LENGTH = 8;

    private static final int QUEUE_LENGTH = 10000;

    private Map<String, List<Integer>> versionMap = Maps.newConcurrentMap();

    private Map<String, SerialNode> nodeMap = Maps.newConcurrentMap();

    private Map<String, IndexNode> indexMap = Maps.newConcurrentMap();

    private Map<String, SerialGroupPo> groupMap = Maps.newConcurrentMap();

    private LinkedBlockingQueue<GroupListenerNode> queue = new LinkedBlockingQueue<>(QUEUE_LENGTH);

    @Autowired
    private SerialGroupMapper serialGroupMapper;

    @Autowired
    private SerialPartitionMapper serialPartitionMapper;

    @Autowired
    private SerialDistributeService serialDistributeService;

    @Override
    @Transactional
    public void createSerialGroup(String name, int version) {
        Example example = new Example(SerialGroupPo.class);
        example.createCriteria().andEqualTo("name", name).andEqualTo("version", version);

        List<SerialGroupPo> groups = serialGroupMapper.selectByExample(example);
        SerialGroupPo group = null;
        if (CollectionUtils.isEmpty(groups)) {
            log.error("unknow serial group name {},version {}", name, version);
            return;
        }
        group = groups.get(0);
        if (group.getStat() != DataStatus.ENABLE.calculate()) {
            log.error("serial group not enable,name=[{}], version=[{}], stat=[{}].", group.getName(), group.getVersion(), group.getStat());
            return;
        }

        IndexNode index = new IndexNode((int) (group.getPart() * Math.random()), group.getPart());
        String key = String.format(NODE_KEY_FORMAT, group.getName(), group.getVersion());
        this.indexMap.put(key, index);
        this.groupMap.put(key, group);
        List<Integer> list = this.versionMap.get(name);
        if (list == null) {
            list = new ArrayList<Integer>();
            this.versionMap.put(name, list);
        }
        list.add(version);
        this.registNode(group, index.getIndex());
    }

    @Override
    @Transactional
    public void rechargeSerialGroup(String name, int version, long tsup) {
        String key = String.format(NODE_KEY_FORMAT, name, version);
        SerialNode node = nodeMap.get(key);
        SerialGroupPo group = groupMap.get(key);
        IndexNode index = indexMap.get(key);
        if (node == null || group == null || index == null) {
            throw new IllegalArgumentException("Primary key is already exist!");
        }
        if (node.checkRemainder()) {
            for (int i = 0; i < group.getPart(); i++) {
                try {
                    this.registNode(group, index.getIndex());
                    break;
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
            node.finishRecharge();
        }
    }

    private void registNode(SerialGroupPo group, int index) {
        SerialPartitionPo partition = serialPartitionMapper.getSerialPartition(group.getName(), group.getVersion(), index);
        if (partition == null) {
            throw new IllegalArgumentException("Serial partition is null");
        }
        if (partition.getUsed() >= partition.getMax()) {
            throw new IllegalArgumentException("Serial partition is full.");
        }

        // 已使用位置小于分区位置，表示分区还未使用
        long offset = partition.getUsed() > group.getMin() ? partition.getUsed() + 1 : group.getMin();
        long max = offset + group.getStep() * CACHE_LENGTH - 1;
        long limit = offset + group.getStep() * LIMIT_LENGTH - 1;

        if (max > group.getMax()) {
            max = group.getMax();
            limit = max;
        }

        SerialNode node = new SerialNode(group.getName(), group.getVersion(), System.currentTimeMillis(), offset, max, group.getStep(),
                limit, this.queue);

        if (this.updateSerialPartition(group.getName(), group.getVersion(), index, max, partition.getUsed())) {
            this.serialDistributeService.regist(group.getName(), node);
            this.nodeMap.put(String.format(NODE_KEY_FORMAT, group.getName(), group.getVersion()), node);
        } else {
            throw new IllegalArgumentException(
                    String.format("register serial error,name=[%s], version=[%d].", group.getName(), group.getVersion()));
        }
    }

    @Override
    @Transactional
    public long directSerialGroup(String name, int length) {
        long ret = 0L;
        List<Integer> list = this.versionMap.get(name);

        for (Integer version : list) {
            String key = String.format(NODE_KEY_FORMAT, name, version);
            SerialGroupPo group = groupMap.get(key);
            IndexNode index = indexMap.get(key);
            if (group == null || index == null) {
                continue;
            }
            for (int i = 0; i < group.getPart(); i++) {
                int partIdex = index.getIndex();
                SerialPartitionPo partition = this.serialPartitionMapper.getSerialPartition(name, version, partIdex);
                if (partition.getUsed() + length > partition.getMax()) {
                    continue;
                }
                long last = partition.getUsed();
                long used = partition.getUsed() + length;
                if (updateSerialPartition(name, version, partIdex, used, last)) {
                    ret = partition.getUsed() + 1;
                    log.info("direct get serial name=[{}], version=[{}], partition=[{}].", name, version, partIdex);
                    break;
                } else {
                    continue;
                }
            }
            if (ret > 0) {
                break;
            }
        }
        return ret;
    }

    public void removeNode(String name, int version, long tsup) {
        if (tsup > 0L) {
            this.serialDistributeService.unregist(name, version, tsup);
        } else {
            List<Integer> list = this.versionMap.get(name);
            int del = -1;
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) == version) {
                        del = i;
                        break;
                    }
                }
                if (del > 0) {
                    list.remove(del);
                }
            }
            String key = String.format(NODE_KEY_FORMAT, name, version);
            this.groupMap.remove(key);
            this.nodeMap.remove(key);
        }
    }

    /**
     * @return the operatorQueue
     */
    @Override
    public LinkedBlockingQueue<GroupListenerNode> getOperatorQueue() {
        return queue;
    }

    private boolean updateSerialPartition(String name, long version, long partIndex, long used, long lastUsed) {
        Example serialpartitionExample = new Example(SerialPartitionPo.class);
        serialpartitionExample.createCriteria().andEqualTo("name", name).andEqualTo("version", version).andEqualTo("part", partIndex)
                .andEqualTo("used", lastUsed);
        SerialPartitionPo serialPartitionPo = new SerialPartitionPo();
        serialPartitionPo.setUsed(used);
        int row = serialPartitionMapper.updateByExampleSelective(serialPartitionPo, serialpartitionExample);
        if (row >= 1) {
            return true;
        }
        return false;
    }
}
