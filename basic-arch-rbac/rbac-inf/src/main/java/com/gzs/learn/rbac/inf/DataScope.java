package com.gzs.learn.rbac.inf;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 数据范围
 */
@Data
public class DataScope implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 限制范围的字段名称
     */
    private String scopeName = "deptid";

    /**
     * 限制范围的
     */
    private List<Long> deptIds;

    public DataScope() {
    }

    public DataScope(List<Long> deptIds) {
        this.deptIds = deptIds;
    }

    public DataScope(String scopeName, List<Long> deptIds) {
        this.scopeName = scopeName;
        this.deptIds = deptIds;
    }
}
