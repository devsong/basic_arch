# ************************************************************
# Sequel Pro SQL dump
# Version 5425
#
# https://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: localdb.mysql.com (MySQL 5.7.26-log)
# Database: ruoyi_log
# Generation Time: 2020-11-26 08:15:17 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table sys_job_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_job_log`;

CREATE TABLE `sys_job_log` (
  `job_log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) DEFAULT NULL COMMENT '日志信息',
  `status` char(1) DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) DEFAULT '' COMMENT '异常信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时任务调度日志表';



# Dump of table sys_logininfor
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_logininfor`;

CREATE TABLE `sys_logininfor` (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(50) DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `status` char(1) DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`),
  KEY `idx_login_time` (`login_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统访问记录';



# Dump of table sys_oper_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_oper_log`;

CREATE TABLE `sys_oper_log` (
  `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) DEFAULT '' COMMENT '模块标题',
  `business_type` int(2) DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) DEFAULT '' COMMENT '请求方式',
  `operator_type` int(1) DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(50) DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) DEFAULT '' COMMENT '返回参数',
  `status` int(1) DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '操作时间',
  PRIMARY KEY (`oper_id`),
  KEY `idx_oper_time` (`oper_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志记录';



# Dump of table sys_perf_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_perf_log`;

CREATE TABLE `sys_perf_log` (
  `id` bigint(20) NOT NULL COMMENT '日志ID',
  `meta_id` bigint(20) NOT NULL COMMENT '元数据id',
  `execute_timespan` int(11) NOT NULL DEFAULT '0' COMMENT '执行时间',
  `params_in` text COMMENT '入参',
  `params_out` text COMMENT '出参',
  `code` int(11) NOT NULL DEFAULT '0' COMMENT '状态码',
  `errmsg` text COMMENT '异常信息',
  `create_time` datetime NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '创建时间',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_createtime` (`create_time`),
  KEY `idx_meta_id` (`meta_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统接口日志';



# Dump of table sys_perf_log_count
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_perf_log_count`;

CREATE TABLE `sys_perf_log_count` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `meta_id` bigint(20) NOT NULL COMMENT '元数据id',
  `count_date` varchar(32) NOT NULL DEFAULT '' COMMENT '统计日期',
  `count_duration` varchar(32) NOT NULL DEFAULT '' COMMENT '统计区间',
  `execute_total` bigint(11) NOT NULL DEFAULT '0' COMMENT '执行次数',
  `execute_exception` bigint(20) NOT NULL DEFAULT '0' COMMENT '执行异常次数',
  `execute_sys_exception` bigint(20) NOT NULL DEFAULT '0' COMMENT '系统异常次数',
  `execute_biz_exception` bigint(20) NOT NULL DEFAULT '0' COMMENT '业务异常次数',
  `execute_time_total` bigint(20) NOT NULL DEFAULT '0' COMMENT '执行总时间',
  `execute_time_avg` double NOT NULL DEFAULT '0' COMMENT '平均执行时间',
  `execute_time_max` int(11) NOT NULL DEFAULT '0' COMMENT '最长执行时间',
  `execute_time_min` int(11) NOT NULL COMMENT '最短执行时间',
  `create_time` datetime NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '创建时间',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_metaid_data_duration` (`meta_id`,`count_date`,`count_duration`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统日志统计信息';



# Dump of table sys_perf_log_meta
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_perf_log_meta`;

CREATE TABLE `sys_perf_log_meta` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `product` varchar(32) NOT NULL DEFAULT '' COMMENT '产品线',
  `group_name` varchar(32) NOT NULL DEFAULT '' COMMENT '组名',
  `app` varchar(32) NOT NULL DEFAULT '' COMMENT '应用名',
  `clazz` varchar(256) NOT NULL DEFAULT '' COMMENT '类名',
  `method` varchar(64) NOT NULL DEFAULT '' COMMENT '方法名',
  `operator_ip` varchar(32) NOT NULL DEFAULT '' COMMENT '客户端机器ip',
  `create_time` datetime NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '创建时间',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_pgacmo` (`product`,`group_name`,`app`,`clazz`,`method`,`operator_ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统日志元数据信息';




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
