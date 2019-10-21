package com.gzs.learn.log.inf.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class UserLoginLogSearchDto extends PageSearchRequestDto {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String logtype;
    private String logname;
    private Long userid;
}
