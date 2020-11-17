package com.gzs.learn.serial.repository;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.gzs.learn.serial.po.SerialPartitionUsedPo;

import tk.mybatis.mapper.common.Mapper;

public interface SerialUsedMapper extends Mapper<SerialPartitionUsedPo> {
    String GET_SERIAL_USED_MAX = "SELECT MAX(upos) FROM serialused WHERE name = #{name} AND ver = #{ver} AND part = 0";

    @Select(GET_SERIAL_USED_MAX)
    Long getSerialMax(@Param("name") String name, @Param("ver") int version);
}
