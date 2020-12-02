package com.ruoyi.serial.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ruoyi.serial.domain.SerialAlloc;
import com.ruoyi.serial.dto.SegmentSearchDto;

public interface SerialAllocMapper {

    List<SerialAlloc> getAllSerialAllocs();

    SerialAlloc getSerialAlloc(@Param("tag") String tag);

    void updateMaxId(@Param("tag") String tag);

    void updateMaxIdByCustomStep(@Param("serialAlloc") SerialAlloc serialAlloc);

    List<String> getAllTags();

    List<SerialAlloc> search(SegmentSearchDto segmentSearchDto);

    int saveSerial(SerialAlloc serialAlloc);

    int updateSerialAllocStatus(@Param("tag") String key, @Param("status") Integer status);

    int updateSerialAlloc(SerialAlloc serialAlloc);
}
