package com.ruoyi.serial.snowflake;

import static com.ruoyi.serial.snowflake.SnowflakeConst.DATA_CENTER_ID_BITS;
import static com.ruoyi.serial.snowflake.SnowflakeConst.DATA_CENTER_ID_SHIFT;
import static com.ruoyi.serial.snowflake.SnowflakeConst.SEQUENCE_BITS;
import static com.ruoyi.serial.snowflake.SnowflakeConst.TIMESTAMP_SHIFT;
import static com.ruoyi.serial.snowflake.SnowflakeConst.WORKER_ID_BITS;
import static com.ruoyi.serial.snowflake.SnowflakeConst.WORKER_ID_SHIFT;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.ruoyi.serial.IDGen;
import com.ruoyi.serial.common.Result;
import com.ruoyi.serial.common.Status;
import com.ruoyi.serial.config.SerialConfig;
import com.ruoyi.serial.domain.SerialSnowflakeInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SnowflakeIDGenImpl implements IDGen {
    private final long twepoch = SnowflakeConst.TWEPOCH;
    private final long workerIdBits = SnowflakeConst.WORKER_ID_BITS;
    private final long maxWorkerId = SnowflakeConst.MAX_WORKER_ID;
    private final long workerIdShift = SnowflakeConst.WORKER_ID_SHIFT;
    private final long timestampShift = SnowflakeConst.TIMESTAMP_SHIFT;
    private final long sequenceMask = SnowflakeConst.SEQUENCE_MASK;

    private long dataCenterId;
    private long workerId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    private SnowflakeZookeeperHolder snowflakeZookeeperHolder;

    @Autowired
    public SnowflakeIDGenImpl(SerialConfig serialConfig, SnowflakeZookeeperHolder snowflakeZookeeperHolder) {
        this.dataCenterId = serialConfig.getDataCenterId();
        this.snowflakeZookeeperHolder = snowflakeZookeeperHolder;
    }

    /**
     * @param zkAddress zk地址
     * @param port      snowflake监听端口
     * @param twepoch   起始的时间戳
     */
    @Override
    @PostConstruct
    public boolean init() {
        Preconditions.checkArgument(timeGen() > twepoch, "Snowflake not support twepoch gt currentTime");
        boolean initFlag = snowflakeZookeeperHolder.init();
        if (initFlag) {
            // 添加数据中心的workerId
            workerId = (dataCenterId << workerIdBits) | snowflakeZookeeperHolder.getWorkerID();
            log.info("start success for workerId-{}", workerId);
        } else {
            Preconditions.checkArgument(initFlag, "Snowflake Id Gen is not init ok");
        }
        Preconditions.checkArgument(workerId >= 0 && workerId <= maxWorkerId, "workerID must gte 0 and lte " + maxWorkerId);
        return true;
    }

    @Override
    public synchronized Result get(String key) {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            // 当前时间小于上次生成时间，说明时钟产生回拨,等待一个阈值,暂定5ms
            long offset = lastTimestamp - timestamp;
            if (offset <= 5) {
                try {
                    // wait两倍的阈值时间
                    wait(offset << 1);
                    timestamp = timeGen();
                    if (timestamp < lastTimestamp) {
                        // 当前时间仍然小于上次生成时间,说明时钟产生大步长的回拨,抛出异常
                        return new Result(-1, Status.EXCEPTION);
                    }
                } catch (InterruptedException e) {
                    log.error("wait interrupted");
                    return new Result(-2, Status.EXCEPTION);
                }
            } else {
                // 时钟产生大步长的回拨,抛出异常
                return new Result(-3, Status.EXCEPTION);
            }
        }
        if (lastTimestamp == timestamp) {
            // 同一毫秒内,sequence做递增操作
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // seq 为0的时候表示是下一毫秒时间开始对seq做随机
                sequence = ThreadLocalRandom.current().nextInt(100);
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 新的ms开始,此处用Random与ThreadLocalRandom并无差别,方法本身是同步的方法
            sequence = ThreadLocalRandom.current().nextInt(100);
        }
        lastTimestamp = timestamp;
        // 生成snowflake ID
        long id = ((timestamp - twepoch) << timestampShift) | (workerId << workerIdShift) | sequence;
        return new Result(id, Status.SUCCESS);
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            // 类自旋操作
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    public long getWorkerId() {
        return workerId;
    }

    public SerialSnowflakeInfo decodeSnowflake(long id) {
        long seqId = id ^ (id >> SEQUENCE_BITS << SEQUENCE_BITS);
        long workerId = (id >> WORKER_ID_SHIFT) ^ (id >> (WORKER_ID_SHIFT + WORKER_ID_BITS) << WORKER_ID_BITS);
        long dataCenterId = (id >> DATA_CENTER_ID_SHIFT) ^ (id >> (DATA_CENTER_ID_SHIFT + DATA_CENTER_ID_BITS) << DATA_CENTER_ID_BITS);
        long timestamp = (id >> TIMESTAMP_SHIFT) + SnowflakeConst.TWEPOCH;
        Date time = new Date(timestamp);
        String timeStr = DateFormatUtils.format(time, "yyyy-MM-dd HH:mm:ss.SSS");
        SerialSnowflakeInfo info = new SerialSnowflakeInfo();
        info.setSeq(seqId);
        info.setWorkerId(workerId);
        info.setDataCenterId(dataCenterId);
        info.setTimestamp(timestamp);
        info.setTime(timeStr);
        return info;
    }
}
