package com.gzs.learn.serial.repository;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.gzs.learn.serial.po.SerialPartitionPo;

import tk.mybatis.mapper.common.Mapper;

public interface SerialPartitionMapper extends Mapper<SerialPartitionPo> {
    String GET_SERIAL_PARTITION_LOCK = "SELECT `name`, ver, part, stat, min, max,used,tscr FROM serialpart AS P WHERE P.`name` = #{name} AND P.ver = #{ver} AND P.part = #{part} FOR UPDATE;";

    @Select(GET_SERIAL_PARTITION_LOCK)
    SerialPartitionPo getSerialPartition(@Param("name") String name, @Param("ver") int ver, @Param("part") int partition);
}
