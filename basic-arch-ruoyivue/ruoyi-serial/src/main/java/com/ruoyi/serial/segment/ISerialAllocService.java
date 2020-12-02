package com.ruoyi.serial.segment;

import java.util.List;

import com.gzs.learn.inf.PageResponseDto;
import com.ruoyi.serial.domain.SerialAlloc;
import com.ruoyi.serial.dto.SegmentSearchDto;

public interface ISerialAllocService {

    SerialAlloc updateMaxIdAndGetSerialAlloc(String tag);

    SerialAlloc updateMaxIdByCustomStepAndGetLeafAlloc(SerialAlloc leafAlloc);

    List<String> getAllTags();

    List<SerialAlloc> search(SegmentSearchDto segmentSearchDto);

    int saveSerialAlloc(SerialAlloc serialAlloc);

    boolean updateStatus(String key, Integer status);

    PageResponseDto<SerialAlloc> searchBizKeys(SegmentSearchDto segmentSearchDto);

    boolean add(SerialAlloc serialAlloc);

    SerialAlloc getBizKey(String bizKey);

    boolean update(SerialAlloc serialAlloc);

}
