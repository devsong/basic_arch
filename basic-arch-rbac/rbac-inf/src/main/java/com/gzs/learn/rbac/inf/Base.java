package com.gzs.learn.rbac.inf;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class Base implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    protected Long id;

    /**
     * 创建时间
     */
    protected Date createtime;
    /**
     * 更新时间
     */
    protected Date timestamp;
}
