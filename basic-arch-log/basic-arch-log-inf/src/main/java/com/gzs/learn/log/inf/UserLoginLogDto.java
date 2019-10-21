package com.gzs.learn.log.inf;

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
public class UserLoginLogDto implements Serializable {
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

    /**
     * 成功标识
     */
    private String succeed;

    /**
     * 消息
     */
    private String message;

    /**
     * 操作ip
     */
    private String operatorIp;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date timestamp;
}
