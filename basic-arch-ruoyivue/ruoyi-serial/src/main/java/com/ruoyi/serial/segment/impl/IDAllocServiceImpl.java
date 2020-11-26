package com.ruoyi.serial.segment.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.serial.domain.SerialAlloc;
import com.ruoyi.serial.mapper.IDAllocMapper;
import com.ruoyi.serial.segment.IDAllocService;

@Service
@DataSource(DataSourceType.MASTER)
public class IDAllocServiceImpl implements IDAllocService {
    @Autowired
    private IDAllocMapper idAllocMapper;

    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<SerialAlloc> getAllLeafAllocs() {
        return idAllocMapper.getAllSerialAllocs();
    }

    @Override
    @Transactional
    public SerialAlloc updateMaxIdAndGetLeafAlloc(String tag) {
        idAllocMapper.updateMaxId(tag);
        SerialAlloc result = idAllocMapper.getSerialAlloc(tag);
        return result;
    }

    @Override
    @Transactional
    public SerialAlloc updateMaxIdByCustomStepAndGetLeafAlloc(SerialAlloc leafAlloc) {
        idAllocMapper.updateMaxIdByCustomStep(leafAlloc);
        SerialAlloc result = idAllocMapper.getSerialAlloc(leafAlloc.getKey());
        return result;
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<String> getAllTags() {
        return idAllocMapper.getAllTags();
    }
}
