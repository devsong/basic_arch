package com.gzs.learn.log.inf.search;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class PageSearchRequestDto implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 日志起始查询时间
     */
    protected Date createTimeStart;
    /**
     * 日志结束查询时间
     */
    protected Date createTimeEnd;
    /**
     * 操作ip集合
     */
    protected List<String> operatorIps;
    /**
     * 查询起始页数
     */
    protected Integer page;
    /**
     * 页大小
     */
    protected Integer pageSize;
}
