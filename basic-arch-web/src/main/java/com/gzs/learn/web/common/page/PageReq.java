package com.gzs.learn.web.common.page;

import java.io.Serializable;

import lombok.Data;

@Data
public class PageReq implements Serializable {
    private static final long serialVersionUID = -1524970970794892969L;

    private Integer limit;
    private Integer offset;
    private String sort;
    private String order;
    private boolean openSort;
    private boolean asc;

    public PageReq() {
    }

    public PageReq(Integer limit, Integer offset, String sort, String order) {
        this.limit = limit;
        this.offset = offset;
        this.sort = sort;
        this.order = order;
    }
}
