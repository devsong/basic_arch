package com.gzs.learn.rbac.inf;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DeptDto extends Base {
    private static final long serialVersionUID = 1L;
    /**
     * 排序
     */
    private Integer num;
    /**
     * 父部门id
     */
    private Integer pid;
    /**
     * 父级ids
     */
    private String pids;
    /**
     * 简称
     */
    private String simplename;
    /**
     * 全称
     */
    private String fullname;
    /**
     * 提示
     */
    private String tips;
}
