package com.ruoyi.serial.segment.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.gzs.learn.inf.PageResponseDto;
import com.gzs.learn.inf.PageResponseDto.PageResponse;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.serial.domain.SerialAlloc;
import com.ruoyi.serial.dto.SegmentSearchDto;
import com.ruoyi.serial.dto.SerialAllocDto;
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
    @DataSource(DataSourceType.SLAVE)
    public PageResponseDto<SerialAllocDto> searchBizKeys(SegmentSearchDto segmentSearchDto) {
        PageInfo<SerialAlloc> pageInfo = PageHelper.startPage(segmentSearchDto.getPageNum(), segmentSearchDto.getPageSize(), true)
                .doSelectPageInfo(() -> {
                    idAllocMapper.search(segmentSearchDto);
                });
        List<SerialAlloc> list = pageInfo.getList();
        List<SerialAllocDto> result = Lists.newArrayList();
        for (SerialAlloc serialAlloc : list) {
            SerialAllocDto dto = new SerialAllocDto();
            BeanUtils.copyProperties(serialAlloc, dto);

            dto.setMaxId(serialAlloc.getMaxId() + "");

            result.add(dto);
        }
        PageResponse pageResponse = PageResponse.builder().page(segmentSearchDto.getPageNum()).pageSize(segmentSearchDto.getPageSize())
                .total((int) pageInfo.getTotal()).build();
        PageResponseDto<SerialAllocDto> pageResponseDto = new PageResponseDto<>();
        pageResponseDto.setCode(0);
        pageResponseDto.setPage(pageResponse);
        pageResponseDto.setData(result);
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
