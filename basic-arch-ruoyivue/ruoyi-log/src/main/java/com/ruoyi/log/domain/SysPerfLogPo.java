package com.ruoyi.log.domain;

import java.io.Serializable;
import java.util.Date;

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
public class SysPerfLogPo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     * 唯一ID
     */
    private String traceId;
    /**
     * 元数据id
     */
    private Long metaId;
    /**
     * 执行时间
     */
    private Integer executeTimespan;
    /**
     * 方法入参
     */
    private String paramsIn;
    /**
     * 方法出参数
     */
    private String paramsOut;
    /**
     * 调用结果0:成功,-1:系统异常,大于0业务异常
     */
    private Integer code;
    /**
     * 系统异常堆栈信息
     */
    private String errmsg;
    /**
     * 调用时间
     */
    private Date createTime;
    /**
    * 更新时间
    */
    private Date timestamp;
}
