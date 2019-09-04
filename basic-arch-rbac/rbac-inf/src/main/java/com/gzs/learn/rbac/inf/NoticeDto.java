package com.gzs.learn.rbac.inf;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class NoticeDto extends Base {

    private static final long serialVersionUID = 1L;
    /**
     * 标题
     */
    private String title;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 内容
     */
    private String content;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 创建人
     */
    private Integer creater;
}
