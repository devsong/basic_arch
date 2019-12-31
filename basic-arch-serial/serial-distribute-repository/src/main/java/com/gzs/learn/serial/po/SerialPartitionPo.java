package com.gzs.learn.serial.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "serialpart")
public class SerialPartitionPo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "name")
    private String name;

    /**
     * 分组版本
     */
    @Column(name = "ver")
    private Integer version;

    /**
     * 分区编号
     */
    @Column(name = "part")
    private Integer part;

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
     * 分组使用位置
     */
    @Column(name = "used")
    private Long used;
    /**
     * 创建时间
     */
    @Column(name = "tscr")
    private Date tscr;
}
