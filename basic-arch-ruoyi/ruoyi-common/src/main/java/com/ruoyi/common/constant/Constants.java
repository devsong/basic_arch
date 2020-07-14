package com.ruoyi.common.constant;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ruoyi.common.config.thread.NamedThreadFactory;

/**
 * 通用常量信息
 * 
 * @author guanzhisong
 */
public interface Constants {
    /**
     * 系统名称
     */
    String SYSTEM_NAME = "若依后台管理系统";

    /**
     * 系统包名前缀
     */
    String SYSTEM_PREFIX = "com.ruoyi";

    /**
     * UTF-8 字符集
     */
    String UTF8 = "UTF-8";

    /**
     * 通用成功标识
     */
    String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    String FAIL = "1";

    /**
     * 登录成功
     */
    String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    String LOGOUT = "Logout";

    /**
     * 注册
     */
    String REGISTER = "Register";

    /**
     * 登录失败
     */
    String LOGIN_FAIL = "Error";

    /**
     * 当前记录起始索引
     */
    String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    String IS_ASC = "isAsc";

    /**
     * 参数管理 cache name
     */
    String SYS_CONFIG_CACHE = "sys-config";

    /**
     * 参数管理 cache key
     */
    String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache name
     */
    String SYS_DICT_CACHE = "sys-dict";

    /**
     * 字典管理 cache key
     */
    String SYS_DICT_KEY = "sys_dict:";

    /**
     * 资源映射路径 前缀
     */
    String RESOURCE_PREFIX = "/profile";

    /**
     * 系统公共的线程池,用于执行一些轻量级的异步任务
     */
    ExecutorService THREAD_POOL = Executors.newFixedThreadPool(50, new NamedThreadFactory("ruoyi-system"));
}
