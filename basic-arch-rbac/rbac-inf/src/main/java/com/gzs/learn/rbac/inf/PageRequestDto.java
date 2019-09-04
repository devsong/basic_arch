package com.gzs.learn.rbac.inf;

import java.io.Serializable;

import lombok.Data;

@Data
public class PageRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;
    // 页号
    private Integer page;
    // 每页条目数
    private Integer limit;
}
