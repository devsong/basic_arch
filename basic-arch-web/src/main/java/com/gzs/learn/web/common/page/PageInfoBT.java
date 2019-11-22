package com.gzs.learn.web.common.page;

import java.util.List;

import com.gzs.learn.inf.PageResponseDto;
import com.gzs.learn.web.common.constant.tips.Tip;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页结果的封装(for Bootstrap Table)
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PageInfoBT<T> extends Tip {
    // 结果集
    private List<T> rows;

    // 总数
    private long total;

    public PageInfoBT(PageResponseDto<T> pageResponseDto) {
        this.rows = pageResponseDto.getData();
        this.total = pageResponseDto.getPage().getTotal();
    }

    public PageInfoBT(List<T> page) {
        this.rows = page;
        this.total = page.size();
    }

    public PageInfoBT(List<T> rows, long total) {
        this.rows = rows;
        this.total = total;
    }
}
