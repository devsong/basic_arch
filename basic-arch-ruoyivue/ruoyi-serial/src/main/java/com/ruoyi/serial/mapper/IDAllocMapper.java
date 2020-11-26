package com.ruoyi.serial.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ruoyi.serial.domain.SerialAlloc;

public interface IDAllocMapper {

    List<SerialAlloc> getAllSerialAllocs();

    SerialAlloc getSerialAlloc(@Param("tag") String tag);

    void updateMaxId(@Param("tag") String tag);

    void updateMaxIdByCustomStep(@Param("serialAlloc") SerialAlloc serialAlloc);

    List<String> getAllTags();
}
