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
public class UserOperationLogDto implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String logtype;
    private String logname;
    private String operatorIp;
    private Long userid;
    private String classname;
    private String method;
    private String succeed;
    private String message;
    private Date createTime;
    private Date timestamp;
}
