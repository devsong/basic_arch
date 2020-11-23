package com.gzs.learn.serial.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "serialgroup")
public class SerialGroupPo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 分组名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 分组版本
     */
    @Column(name = "ver")
    private Integer version;

    /**
     * 分组状态
     */
    @Column(name = "stat")
    private Integer stat;

    /**
     * 分组最小值
     */
    @Column(name = "min")
    private Long min;

    /**
     * 分组最大值
     */
    @Column(name = "max")
    private Long max;

    /**
     * 分区数量
     */
    @Column(name = "part")
    private Integer part;

    /**
     * 步长
     */
    @Column(name = "step")
    private Integer step;

    /**
     * 变更用户标识
     */
    @Column(name = "upid")
    private Long upid;

    /**
     * 变更时间戳
     */
    @Column(name = "uptime")
    private Long uptime;

    /**
     * 创建时间
     */
    @Column(name = "tscr")
    private Date tscr;
}
