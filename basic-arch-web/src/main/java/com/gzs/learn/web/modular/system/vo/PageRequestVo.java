package com.gzs.learn.web.modular.system.vo;

import lombok.Data;

@Data
public class PageRequestVo {
    // 页号
    private Integer page;
    // 条目数
    private Integer limit;
}
