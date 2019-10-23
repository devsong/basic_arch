package com.gzs.learn.log.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author guanzhisong
 * @date 2019-10-22 16:37:05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sys_perf_log_count")
public class SysPerfLogCountPo implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 元数据id
     */
    @Column(name = "meta_id")
    private Long metaId;

    /**
     * 统计日期
     */
    @Column(name = "count_date")
    private String countDate;

    /**
     * 统计区间
     */
    @Column(name = "count_duration")
    private String countDuration;

    /**
     * 执行次数
     */
    @Column(name = "execute_total")
    private Long executeTotal;

    /**
     * 执行异常次数
     */
    @Column(name = "execute_exception")
    private Long executeException;

    /**
     * 系统异常次数
     */
    @Column(name = "execute_sys_exception")
    private Long executeSysException;

    /**
     * 业务异常次数
     */
    @Column(name = "execute_biz_exception")
    private Long executeBizException;

    /**
     * 执行总时间
     */
    @Column(name = "execute_time_total")
    private Long executeTimeTotal;

    /**
     * 平均执行时间
     */
    @Column(name = "execute_time_avg")
    private Double executeTimeAvg;

    /**
     * 最长执行时间
     */
    @Column(name = "execute_time_max")
    private Integer executeTimeMax;

    /**
     * 最短执行时间
     */
    @Column(name = "execute_time_min")
    private Integer executeTimeMin;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    private Date timestamp;
}