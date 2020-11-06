package com.ruoyi.log.domain;

import java.util.Date;

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
public class SysPerfLogCountPo implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private Long id;

    /**
     * 元数据id
     */
    private Long metaId;

    /**
     * 统计日期
     */
    private String countDate;

    /**
     * 统计区间
     */
    private String countDuration;

    /**
     * 执行次数
     */
    private Long executeTotal;

    /**
     * 执行异常次数
     */
    private Long executeException;

    /**
     * 系统异常次数
     */
    private Long executeSysException;

    /**
     * 业务异常次数
     */
    private Long executeBizException;

    /**
     * 执行总时间
     */
    private Long executeTimeTotal;

    /**
     * 平均执行时间
     */
    private Double executeTimeAvg;

    /**
     * 最长执行时间
     */
    private Integer executeTimeMax;

    /**
     * 最短执行时间
     */
    private Integer executeTimeMin;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date timestamp;
}