package com.ruoyi.serial.segment;

import java.util.List;

import com.ruoyi.serial.domain.SerialAlloc;


public interface IDAllocService {
    List<SerialAlloc> getAllLeafAllocs();

    SerialAlloc updateMaxIdAndGetLeafAlloc(String tag);

    SerialAlloc updateMaxIdByCustomStepAndGetLeafAlloc(SerialAlloc leafAlloc);

    List<String> getAllTags();
}
