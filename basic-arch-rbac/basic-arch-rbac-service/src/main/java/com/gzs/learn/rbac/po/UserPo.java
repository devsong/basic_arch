package com.gzs.learn.rbac.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 管理员表
 * </p>
 */
@Table(name = "sys_user")
@Data
@EqualsAndHashCode(callSuper = false)
public class UserPo extends Base {
    private static final long serialVersionUID = 1L;

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
     * md5密码盐
     */
    private String salt;
    /**
     * 名字
     */
    private String name;
    /**
     * 生日
     */
    private Date birthday;
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
    @Column(name = "role_id")
    private String roleid;
    /**
     * 部门id
     */
    @Column(name = "dept_id")
    private Long deptid;
    /**
     * 状态(1：启用  2：冻结  3：删除）
     */
    private Integer status;
    /**
     * 保留字段
     */
    private Integer version;
}
