package com.gzs.learn.serial.service.snowflake;

public interface SnowflakeConst {
    // 时间点纪元2010-01-01 00:00:00
    final long TWEPOCH = 1262275200000L;
    // 数据中心bit位数,最大8个数据中心
    final long DATA_CENTER_ID_BITS = 3;
    // 最大的数据中心ID数
    final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);
    // 机器bit位数,一个数据中心最多32台机器
    final long WORKER_ID_BITS = 5L;
    // 最大机器数目
    final long MAX_WORKER_ID = ~(-1L << (DATA_CENTER_ID_BITS + WORKER_ID_BITS));
    // 自增序列号位数
    final long SEQUENCE_BITS = 12L;
    // 序列号掩码
    final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);
    // 机器
    final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    // 数据中心
    final long DATA_CENTER_ID_SHIFT = WORKER_ID_SHIFT + WORKER_ID_BITS;
    // 时间戳
    final long TIMESTAMP_SHIFT = DATA_CENTER_ID_SHIFT + DATA_CENTER_ID_BITS;
}
