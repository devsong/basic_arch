package com.gzs.learn.log.po;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginLogPo implements Serializable {
    /**
    * 
    */
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 日志名
     */
    private String logname;

    /**
     * 用户id
     */
    private Long userid;

    private String succeed;

    private String message;

    private String ip;

    private Date createTime;

    private Date timestamp;
}
