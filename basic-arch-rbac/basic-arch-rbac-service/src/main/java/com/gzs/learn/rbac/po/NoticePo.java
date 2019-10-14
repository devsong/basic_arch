package com.gzs.learn.rbac.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class NoticePo extends Base {
    /**
    * 
    */
    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    private String title;
    /**
     * 通知类型
     */
    private Integer type;
    /**
     * 通知内容
     */
    private String content;
    /**
     * 创建人
     */
    private String creater;
}
