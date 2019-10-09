package com.gzs.learn.rbac.po;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 字典表
 * </p>
 */
@Table(name = "sys_dict")
@Data
@EqualsAndHashCode(callSuper = false)
public class DictPo extends Base {
    private static final long serialVersionUID = 1L;
    /**
     * 排序
     */
    private Integer num;
    /**
     * 父级字典
     */
    private Long pid;
    /**
     * 名称
     */
    private String name;
    /**
     * 提示
     */
    private String tips;
}
