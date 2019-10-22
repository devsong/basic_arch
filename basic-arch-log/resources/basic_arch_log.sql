# ************************************************************
# Sequel Pro SQL dump
# Version 5425
#
# https://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 8.0.15)
# Database: abel_guns_log
# Generation Time: 2019-10-22 08:13:23 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table sys_perf_log
# ------------------------------------------------------------

CREATE TABLE `sys_perf_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `meta_id` bigint(20) NOT NULL COMMENT '元数据id',
  `execute_timespan` int(11) NOT NULL DEFAULT '0' COMMENT '执行时间',
  `params_in` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '入参',
  `params_out` text NOT NULL COMMENT '出参',
  `code` int(11) NOT NULL DEFAULT '0' COMMENT '状态码',
  `errmsg` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '异常信息',
  `create_time` datetime NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '创建时间',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_createtime` (`create_time`),
  KEY `idx_pga` (`meta_id`),
  KEY `idx_clazz_method` (`meta_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统接口日志';



# Dump of table sys_perf_log_count
# ------------------------------------------------------------

CREATE TABLE `sys_perf_log_count` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `meta_id` bigint(20) NOT NULL COMMENT '元数据id',
  `count_date` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '统计日期',
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
  UNIQUE KEY `uniq_metaid_date` (`meta_id`,`count_date`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统日志统计信息';



# Dump of table sys_perf_log_meta
# ------------------------------------------------------------

CREATE TABLE `sys_perf_log_meta` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `product` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '产品线',
  `group_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '组名',
  `app` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '应用名',
  `clazz` varchar(256) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '类名',
  `method` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '方法名',
  `operator_ip` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '客户端机器ip',
  `create_time` datetime NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '创建时间',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_pgacmo` (`product`,`group_name`,`app`,`clazz`,`method`,`operator_ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统日志元数据信息';



# Dump of table user_login_log
# ------------------------------------------------------------

CREATE TABLE `user_login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `logname` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '日志名称',
  `userid` bigint(20) NOT NULL DEFAULT '0' COMMENT '管理员id',
  `succeed` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '是否执行成功',
  `message` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '具体消息',
  `operator_ip` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '登录ip',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间戳',
  PRIMARY KEY (`id`),
  KEY `idx_create_time_ip` (`create_time`,`operator_ip`),
  KEY `idx_userid` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录记录';



# Dump of table user_operation_log
# ------------------------------------------------------------

CREATE TABLE `user_operation_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `logtype` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '日志类型',
  `logname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '日志名称',
  `operator_ip` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '操作ip',
  `userid` bigint(20) NOT NULL COMMENT '用户id',
  `classname` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '类名称',
  `method` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '方法名称',
  `succeed` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '是否成功',
  `message` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '创建时间',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间戳',
  PRIMARY KEY (`id`),
  KEY `idx_create_time_ip` (`create_time`,`operator_ip`),
  KEY `idx_userid_classname_method` (`userid`,`classname`,`method`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志';




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
