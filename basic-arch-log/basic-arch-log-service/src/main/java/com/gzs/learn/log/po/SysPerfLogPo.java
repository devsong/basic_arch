package com.gzs.learn.log.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author guanzhisong
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sys_perf_log")
public class SysPerfLogPo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    /**
     * 元数据id
     */
    @Column(name = "meta_id")
    private Long metaId;
    /**
     * 执行时间
     */
    @Column(name = "execute_timespan")
    private Integer executeTimespan;
    /**
     * 方法入参
     */
    @Column(name = "params_in")
    private String paramsIn;
    /**
     * 方法出参数
     */
    @Column(name = "params_out")
    private String paramsOut;
    /**
     * 调用结果0:成功,-1:系统异常,大于0业务异常
     */
    private Integer code;
    /**
     * 系统异常堆栈信息
     */
    @Column(name = "errmsg")
    private String errMsg;
    /**
     * 调用时间
     */
    private Date createtime;
    /**
    * 更新时间
    */
    private Date timestamp;
}
