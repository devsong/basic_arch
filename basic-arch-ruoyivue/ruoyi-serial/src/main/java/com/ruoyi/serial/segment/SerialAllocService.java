package com.ruoyi.serial.segment;

import java.util.List;

import com.ruoyi.serial.domain.SerialAlloc;
import com.ruoyi.serial.dto.SegmentSearchDto;

public interface SerialAllocService {
    List<SerialAlloc> getAllSerialAllocs();

    SerialAlloc updateMaxIdAndGetSerialAlloc(String tag);

    SerialAlloc updateMaxIdByCustomStepAndGetLeafAlloc(SerialAlloc leafAlloc);

    List<String> getAllTags();

    List<SerialAlloc> search(SegmentSearchDto segmentSearchDto);

    int saveSerialAlloc(SerialAlloc serialAlloc);
}
