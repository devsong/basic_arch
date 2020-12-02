package com.ruoyi.serial.segment.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzs.learn.inf.PageResponseDto;
import com.gzs.learn.inf.PageResponseDto.PageResponse;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.serial.domain.SerialAlloc;
import com.ruoyi.serial.dto.SegmentSearchDto;
import com.ruoyi.serial.mapper.SerialAllocMapper;
import com.ruoyi.serial.segment.ISerialAllocService;

@Service
@DataSource(DataSourceType.MASTER)
public class SerialAllocServiceImpl implements ISerialAllocService {
    @Autowired
    private SerialAllocMapper idAllocMapper;

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

    @Override
    public PageResponseDto<SerialAlloc> searchBizKeys(SegmentSearchDto segmentSearchDto) {
        PageInfo<SerialAlloc> pageInfo = PageHelper.startPage(segmentSearchDto.getPageNum(), segmentSearchDto.getPageSize(), true)
                .doSelectPageInfo(() -> {
                    idAllocMapper.search(segmentSearchDto);
                });
        PageResponse pageResponse = PageResponse.builder().page(segmentSearchDto.getPageNum()).pageSize(segmentSearchDto.getPageSize())
                .total((int) pageInfo.getTotal()).build();
        PageResponseDto<SerialAlloc> pageResponseDto = new PageResponseDto<SerialAlloc>();
        pageResponseDto.setCode(0);
        pageResponseDto.setPage(pageResponse);
        pageResponseDto.setData(pageInfo.getList());
        return pageResponseDto;
    }

    @Override
    public boolean add(SerialAlloc serialAlloc) {
        serialAlloc.setCreateTime(new Date());
        int row = idAllocMapper.saveSerial(serialAlloc);
        return row == 1;
    }

    @Override
    public boolean updateStatus(String key, Integer status) {
        int row = idAllocMapper.updateSerialAllocStatus(key, status);
        return row >= 1;
    }

    @Override
    public SerialAlloc getBizKey(String bizKey) {
        SerialAlloc serialAlloc = idAllocMapper.getSerialAlloc(bizKey);
        return serialAlloc;
    }

    @Override
    public boolean update(SerialAlloc serialAlloc) {
        int row = idAllocMapper.updateSerialAlloc(serialAlloc);
        return row >= 1;
    }
}
