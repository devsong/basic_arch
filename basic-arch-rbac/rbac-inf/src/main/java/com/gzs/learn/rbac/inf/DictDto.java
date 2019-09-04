package com.gzs.learn.rbac.inf;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DictDto extends Base {
    private static final long serialVersionUID = 1L;
    /**
     * 排序
     */
    private Integer num;
    /**
     * 父级字典
     */
    private Integer pid;
    /**
     * 名称
     */
    private String name;
    /**
     * 提示
     */
    private String tips;
}
