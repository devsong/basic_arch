package com.gzs.learn.web.modular.system.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserEditVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户的ID
     */
    private Long id;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 名字
     */
    private String name;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 性别（1：男 2：女）
     */
    private Integer sex;
    /**
     * 电子邮件
     */
    private String email;
    /**
     * 电话
     */
    private String phone;
    /**
     * 角色id
     */
    private String roleid;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 部门id
     */
    private Long deptid;
    /**
     * 部门名
     */
    private String deptName;
    /**
     * 状态(1：启用  2：冻结  3：删除）
     */
    private Integer status;
}
