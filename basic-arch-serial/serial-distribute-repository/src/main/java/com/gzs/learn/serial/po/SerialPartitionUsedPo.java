package com.gzs.learn.serial.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "serialused")
public class SerialPartitionUsedPo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 分组名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 分组版本
     */
    @Column(name = "version")
    private Integer version;

    /**
     * 分区编号
     */
    @Column(name = "part")
    private Integer part;

    /**
     * 已使用位置
     */
    @Column(name = "upos")
    private Long upos;

    /**
     * 最后更新时间
     */
    @Column(name = "tsup")
    private Long tsup;

    @Column(name = "tscr")
    private Date tscr;
}
