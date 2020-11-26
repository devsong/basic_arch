package com.ruoyi.serial.segment;

import com.gzs.learn.inf.PageResponseDto;
import com.ruoyi.serial.common.Result;
import com.ruoyi.serial.domain.SerialAlloc;
import com.ruoyi.serial.dto.SegmentSearchDto;

public interface SegmentIdGenService {

    public PageResponseDto<SerialAlloc> searchBizKeys(SegmentSearchDto segmentSearchDto);

    public boolean add(SerialAlloc serialAlloc);

    public boolean updateStatus(String key, Integer status);

    public Result get(String key);

}
