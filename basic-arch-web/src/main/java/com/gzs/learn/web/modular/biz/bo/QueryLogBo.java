package com.gzs.learn.web.modular.biz.bo;

import java.io.Serializable;

import com.gzs.learn.web.common.page.PageReq;

import lombok.Data;

@Data
public class QueryLogBo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String beginTime;
    private String endTime;
    private String logName;
    private String logType;
    private String orderBy;
    private PageReq pageReq;
}
