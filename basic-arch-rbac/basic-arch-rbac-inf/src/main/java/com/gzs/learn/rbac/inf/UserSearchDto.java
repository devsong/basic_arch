package com.gzs.learn.rbac.inf;

import java.io.Serializable;

import com.gzs.learn.inf.PageRequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserSearchDto extends PageRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String deptId;
    private String timeLimit;
    private String beginTime;
    private String endTime;
}
