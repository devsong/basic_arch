package com.gzs.learn.common;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    protected Integer page;
    protected Integer pageSize;
    protected Integer total;

    public Integer getTotalPage() {
        return total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
    }
}
