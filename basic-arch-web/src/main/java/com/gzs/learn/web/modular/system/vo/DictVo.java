package com.gzs.learn.web.modular.system.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class DictVo implements Serializable {
    /**
    * 
    */
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    /*
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
    /**
     * 字典明细信息
     */
    private String detail;
}
