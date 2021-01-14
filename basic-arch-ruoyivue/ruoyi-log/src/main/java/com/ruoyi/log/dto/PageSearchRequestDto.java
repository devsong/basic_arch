package com.ruoyi.log.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    protected Date beginTime;
    /**
     * 日志结束查询时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    protected Date endTime;
    /**
     * 操作ip集合
     */
    protected List<String> operatorIps;
    /**
     * 查询起始页数
     */
    protected Integer pageNum;
    /**
     * 页大小
     */
    protected Integer pageSize;
}
