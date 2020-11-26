package com.ruoyi.serial.segment.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.serial.domain.SerialAlloc;
import com.ruoyi.serial.dto.SegmentSearchDto;
import com.ruoyi.serial.mapper.SerialAllocMapper;
import com.ruoyi.serial.segment.SerialAllocService;

@Service
@DataSource(DataSourceType.MASTER)
public class SerialAllocServiceImpl implements SerialAllocService {
    @Autowired
    private SerialAllocMapper idAllocMapper;

    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<SerialAlloc> getAllSerialAllocs() {
        return idAllocMapper.getAllSerialAllocs();
    }

    @Override
    @Transactional
    public SerialAlloc updateMaxIdAndGetSerialAlloc(String tag) {
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

    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<SerialAlloc> search(SegmentSearchDto segmentSearchDto) {
        List<SerialAlloc> list = idAllocMapper.search(segmentSearchDto);
        return list;
    }

    @Override
    public int saveSerialAlloc(SerialAlloc serialAlloc) {
        int row = idAllocMapper.saveSerial(serialAlloc);
        return row;
    }
}
