package com.ruoyi.log.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 系统接口日志对象 sys_perf_log
 * 
 * @author guanzhisong
 * @date 2020-12-04
 */
public class SysPerfLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 日志ID */
    @Excel(name = "日志ID")
    private Long id;

    /** 元数据id */
    @Excel(name = "元数据id")
    private Long metaId;

    /** 执行时间 */
    @Excel(name = "执行时间")
    private Long executeTimespan;

    /** 入参 */
    @Excel(name = "入参")
    private String paramsIn;

    /** 出参 */
    @Excel(name = "出参")
    private String paramsOut;

    /** 状态码 */
    @Excel(name = "状态码")
    private Long code;

    /** 异常信息 */
    @Excel(name = "异常信息")
    private String errmsg;
    /*
     * 创建时间
     */
    private Date createTime;

    /** 更新时间 */
    private Date timestamp;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setMetaId(Long metaId) {
        this.metaId = metaId;
    }

    public Long getMetaId() {
        return metaId;
    }

    public void setExecuteTimespan(Long executeTimespan) {
        this.executeTimespan = executeTimespan;
    }

    public Long getExecuteTimespan() {
        return executeTimespan;
    }

    public void setParamsIn(String paramsIn) {
        this.paramsIn = paramsIn;
    }

    public String getParamsIn() {
        return paramsIn;
    }

    public void setParamsOut(String paramsOut) {
        this.paramsOut = paramsOut;
    }

    public String getParamsOut() {
        return paramsOut;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Long getCode() {
        return code;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getErrmsg() {
        return errmsg;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId()).append("metaId", getMetaId())
                .append("executeTimespan", getExecuteTimespan()).append("paramsIn", getParamsIn()).append("paramsOut", getParamsOut())
                .append("code", getCode()).append("errmsg", getErrmsg()).append("createTime", getCreateTime())
                .append("timestamp", getTimestamp()).toString();
    }

}
