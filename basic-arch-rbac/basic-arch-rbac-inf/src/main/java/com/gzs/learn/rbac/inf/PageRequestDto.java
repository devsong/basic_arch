package com.gzs.learn.rbac.inf;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;
    // 页号
    private Integer page;
    // 每页条目数
    private Integer limit;
}