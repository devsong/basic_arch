package com.gzs.learn.inf;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageRequestDto implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    protected Integer page;
    protected Integer pageSize;
    // 兼容已有分页逻辑
    protected Integer limit;

    public Integer getPageSize() {
        return pageSize == null ? limit : pageSize;
    }
}