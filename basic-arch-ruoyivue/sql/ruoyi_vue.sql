# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.32-log)
# Database: ruoyi_vue
# Generation Time: 2021-01-19 02:32:44 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table gen_table
# ------------------------------------------------------------

DROP TABLE IF EXISTS `gen_table`;

CREATE TABLE `gen_table` (
  `table_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) DEFAULT '' COMMENT '表描述',
  `class_name` varchar(100) DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='代码生成业务表';



# Dump of table gen_table_column
# ------------------------------------------------------------

DROP TABLE IF EXISTS `gen_table_column`;

CREATE TABLE `gen_table_column` (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` varchar(64) DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) DEFAULT '' COMMENT '字典类型',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='代码生成业务表字段';



# Dump of table serial_alloc
# ------------------------------------------------------------

DROP TABLE IF EXISTS `serial_alloc`;

CREATE TABLE `serial_alloc` (
  `biz_tag` varchar(128) NOT NULL DEFAULT '' COMMENT '业务Key',
  `max_id` bigint(20) NOT NULL DEFAULT '1' COMMENT '最大已使用ID',
  `step` int(11) NOT NULL COMMENT '步长',
  `random_len` int(11) NOT NULL DEFAULT '0' COMMENT '尾部随机数位数',
  `description` varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态(0 正常 1 禁用 2 删除)',
  `create_time` datetime NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`biz_tag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='序列号';

LOCK TABLES `serial_alloc` WRITE;
/*!40000 ALTER TABLE `serial_alloc` DISABLE KEYS */;

INSERT INTO `serial_alloc` (`biz_tag`, `max_id`, `step`, `random_len`, `description`, `status`, `create_time`, `update_time`)
VALUES
	('order',10011000,1000,2,'订单测试',0,'2000-01-01 00:00:00','2021-01-07 11:02:01'),
	('test',10007000,1000,0,'订单测试',0,'2000-01-01 00:00:00','2021-01-07 11:02:14');

/*!40000 ALTER TABLE `serial_alloc` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_config
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_config`;

CREATE TABLE `sys_config` (
  `config_id` int(5) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='参数配置表';

LOCK TABLES `sys_config` WRITE;
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;

INSERT INTO `sys_config` (`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES
	(1,'主框架页-默认皮肤样式名称','sys.index.skinName','skin-blue','Y','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow'),
	(2,'用户管理-账号初始密码','sys.user.initPassword','123456','Y','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','初始化密码 123456'),
	(3,'主框架页-侧边栏主题','sys.index.sideTheme','theme-dark','Y','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','深色主题theme-dark，浅色主题theme-light');

/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_dept
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_dept`;

CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父部门id',
  `ancestors` varchar(50) DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) DEFAULT '' COMMENT '部门名称',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(20) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` char(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;

INSERT INTO `sys_dept` (`dept_id`, `parent_id`, `ancestors`, `dept_name`, `order_num`, `leader`, `phone`, `email`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES
	(100,0,'0','若依科技',0,'若依','15888888888','ry@qq.com','0','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00'),
	(101,100,'0,100','深圳总公司',1,'若依','15888888888','ry@qq.com','0','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00'),
	(102,100,'0,100','长沙分公司',2,'若依','15888888888','ry@qq.com','0','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00'),
	(103,101,'0,100,101','研发部门',1,'若依','15888888888','ry@qq.com','0','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00'),
	(104,101,'0,100,101','市场部门',2,'若依','15888888888','ry@qq.com','0','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00'),
	(105,101,'0,100,101','测试部门',3,'若依','15888888888','ry@qq.com','0','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00'),
	(106,101,'0,100,101','财务部门',4,'若依','15888888888','ry@qq.com','0','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00'),
	(107,101,'0,100,101','运维部门',5,'若依','15888888888','ry@qq.com','0','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00'),
	(108,102,'0,100,102','市场部门',1,'若依','15888888888','ry@qq.com','0','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00'),
	(109,102,'0,100,102','财务部门',2,'若依','15888888888','ry@qq.com','0','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00');

/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_dict_data
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_dict_data`;

CREATE TABLE `sys_dict_data` (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(4) DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典数据表';

LOCK TABLES `sys_dict_data` WRITE;
/*!40000 ALTER TABLE `sys_dict_data` DISABLE KEYS */;

INSERT INTO `sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES
	(1,1,'男','0','sys_user_sex','','','Y','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','性别男'),
	(2,2,'女','1','sys_user_sex','','','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','性别女'),
	(3,3,'未知','2','sys_user_sex','','','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','性别未知'),
	(4,1,'显示','0','sys_show_hide','','primary','Y','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','显示菜单'),
	(5,2,'隐藏','1','sys_show_hide','','danger','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','隐藏菜单'),
	(6,1,'正常','0','sys_normal_disable','','primary','Y','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','正常状态'),
	(7,2,'停用','1','sys_normal_disable','','danger','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','停用状态'),
	(8,1,'正常','0','sys_job_status','','primary','Y','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','正常状态'),
	(9,2,'暂停','1','sys_job_status','','danger','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','停用状态'),
	(10,1,'默认','DEFAULT','sys_job_group','','','Y','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','默认分组'),
	(11,2,'系统','SYSTEM','sys_job_group','','','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','系统分组'),
	(12,1,'是','Y','sys_yes_no','','primary','Y','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','系统默认是'),
	(13,2,'否','N','sys_yes_no','','danger','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','系统默认否'),
	(14,1,'通知','1','sys_notice_type','','warning','Y','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','通知'),
	(15,2,'公告','2','sys_notice_type','','success','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','公告'),
	(16,1,'正常','0','sys_notice_status','','primary','Y','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','正常状态'),
	(17,2,'关闭','1','sys_notice_status','','danger','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','关闭状态'),
	(18,1,'新增','1','sys_oper_type','','info','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','新增操作'),
	(19,2,'修改','2','sys_oper_type','','info','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','修改操作'),
	(20,3,'删除','3','sys_oper_type','','danger','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','删除操作'),
	(21,4,'授权','4','sys_oper_type','','primary','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','授权操作'),
	(22,5,'导出','5','sys_oper_type','','warning','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','导出操作'),
	(23,6,'导入','6','sys_oper_type','','warning','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','导入操作'),
	(24,7,'强退','7','sys_oper_type','','danger','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','强退操作'),
	(25,8,'生成代码','8','sys_oper_type','','warning','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','生成操作'),
	(26,9,'清空数据','9','sys_oper_type','','danger','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','清空操作'),
	(27,1,'成功','0','sys_common_status','','primary','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','正常状态'),
	(28,2,'失败','1','sys_common_status','','danger','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','停用状态');

/*!40000 ALTER TABLE `sys_dict_data` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_dict_type
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_dict_type`;

CREATE TABLE `sys_dict_type` (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`),
  UNIQUE KEY `dict_type` (`dict_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典类型表';

LOCK TABLES `sys_dict_type` WRITE;
/*!40000 ALTER TABLE `sys_dict_type` DISABLE KEYS */;

INSERT INTO `sys_dict_type` (`dict_id`, `dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES
	(1,'用户性别','sys_user_sex','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','用户性别列表'),
	(2,'菜单状态','sys_show_hide','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','菜单状态列表'),
	(3,'系统开关','sys_normal_disable','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','系统开关列表'),
	(4,'任务状态','sys_job_status','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','任务状态列表'),
	(5,'任务分组','sys_job_group','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','任务分组列表'),
	(6,'系统是否','sys_yes_no','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','系统是否列表'),
	(7,'通知类型','sys_notice_type','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','通知类型列表'),
	(8,'通知状态','sys_notice_status','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','通知状态列表'),
	(9,'操作类型','sys_oper_type','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','操作类型列表'),
	(10,'系统状态','sys_common_status','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','登录状态列表');

/*!40000 ALTER TABLE `sys_dict_type` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_job
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_job`;

CREATE TABLE `sys_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`,`job_name`,`job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时任务调度表';

LOCK TABLES `sys_job` WRITE;
/*!40000 ALTER TABLE `sys_job` DISABLE KEYS */;

INSERT INTO `sys_job` (`job_id`, `job_name`, `job_group`, `invoke_target`, `cron_expression`, `misfire_policy`, `concurrent`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES
	(1,'系统默认（无参）','DEFAULT','ryTask.ryNoParams','0/10 * * * * ?','3','1','1','admin','2018-03-16 11:33:00','ry','2020-11-11 19:07:06',''),
	(2,'系统默认（有参）','DEFAULT','ryTask.ryParams(\'ry\')','0/15 * * * * ?','3','1','1','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(3,'系统默认（多参）','DEFAULT','ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)','0/10 * * * * ?','3','1','1','admin','2018-03-16 11:33:00','admin','2020-11-11 19:05:20','');

/*!40000 ALTER TABLE `sys_job` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_menu
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `is_frame` int(1) DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单权限表';

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;

INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES
	(1,'系统管理',0,1,'system',NULL,1,'M','0','0','','system','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','系统管理目录'),
	(2,'系统监控',0,2,'monitor',NULL,1,'M','0','0','','monitor','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','系统监控目录'),
	(3,'系统工具',0,3,'tool',NULL,1,'M','0','0','','tool','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','系统工具目录'),
	(4,'若依官网',0,5,'http://ruoyi.vip',NULL,0,'M','0','0','','guide','admin','2018-03-16 11:33:00','admin','2020-11-26 16:29:37','若依官网地址'),
	(100,'用户管理',1,1,'user','system/user/index',1,'C','0','0','system:user:list','user','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','用户管理菜单'),
	(101,'角色管理',1,2,'role','system/role/index',1,'C','0','0','system:role:list','peoples','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','角色管理菜单'),
	(102,'菜单管理',1,3,'menu','system/menu/index',1,'C','0','0','system:menu:list','tree-table','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','菜单管理菜单'),
	(103,'部门管理',1,4,'dept','system/dept/index',1,'C','0','0','system:dept:list','tree','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','部门管理菜单'),
	(104,'岗位管理',1,5,'post','system/post/index',1,'C','0','0','system:post:list','post','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','岗位管理菜单'),
	(105,'字典管理',1,6,'dict','system/dict/index',1,'C','0','0','system:dict:list','dict','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','字典管理菜单'),
	(106,'参数设置',1,7,'config','system/config/index',1,'C','0','0','system:config:list','edit','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','参数设置菜单'),
	(107,'通知公告',1,8,'notice','system/notice/index',1,'C','0','0','system:notice:list','message','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','通知公告菜单'),
	(108,'日志管理',1,9,'log','system/log/index',1,'M','0','0','','log','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','日志管理菜单'),
	(109,'在线用户',2,1,'online','monitor/online/index',1,'C','0','0','monitor:online:list','online','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','在线用户菜单'),
	(110,'定时任务',2,2,'job','monitor/job/index',1,'C','0','0','monitor:job:list','job','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','定时任务菜单'),
	(111,'数据监控',2,3,'druid','monitor/druid/index',1,'C','0','0','monitor:druid:list','druid','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','数据监控菜单'),
	(112,'服务监控',2,4,'server','monitor/server/index',1,'C','0','0','monitor:server:list','server','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','服务监控菜单'),
	(113,'表单构建',3,1,'build','tool/build/index',1,'C','0','0','tool:build:list','build','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','表单构建菜单'),
	(114,'代码生成',3,2,'gen','tool/gen/index',1,'C','0','0','tool:gen:list','code','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','代码生成菜单'),
	(115,'系统接口',3,3,'swagger','tool/swagger/index',1,'C','0','0','tool:swagger:list','swagger','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','系统接口菜单'),
	(500,'操作日志',108,1,'operlog','monitor/operlog/index',1,'C','0','0','monitor:operlog:list','form','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','操作日志菜单'),
	(501,'登录日志',108,2,'logininfor','monitor/logininfor/index',1,'C','0','0','monitor:logininfor:list','logininfor','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','登录日志菜单'),
	(1001,'用户查询',100,1,'','',1,'F','0','0','system:user:query','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1002,'用户新增',100,2,'','',1,'F','0','0','system:user:add','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1003,'用户修改',100,3,'','',1,'F','0','0','system:user:edit','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1004,'用户删除',100,4,'','',1,'F','0','0','system:user:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1005,'用户导出',100,5,'','',1,'F','0','0','system:user:export','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1006,'用户导入',100,6,'','',1,'F','0','0','system:user:import','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1007,'重置密码',100,7,'','',1,'F','0','0','system:user:resetPwd','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1008,'角色查询',101,1,'','',1,'F','0','0','system:role:query','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1009,'角色新增',101,2,'','',1,'F','0','0','system:role:add','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1010,'角色修改',101,3,'','',1,'F','0','0','system:role:edit','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1011,'角色删除',101,4,'','',1,'F','0','0','system:role:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1012,'角色导出',101,5,'','',1,'F','0','0','system:role:export','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1013,'菜单查询',102,1,'','',1,'F','0','0','system:menu:query','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1014,'菜单新增',102,2,'','',1,'F','0','0','system:menu:add','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1015,'菜单修改',102,3,'','',1,'F','0','0','system:menu:edit','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1016,'菜单删除',102,4,'','',1,'F','0','0','system:menu:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1017,'部门查询',103,1,'','',1,'F','0','0','system:dept:query','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1018,'部门新增',103,2,'','',1,'F','0','0','system:dept:add','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1019,'部门修改',103,3,'','',1,'F','0','0','system:dept:edit','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1020,'部门删除',103,4,'','',1,'F','0','0','system:dept:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1021,'岗位查询',104,1,'','',1,'F','0','0','system:post:query','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1022,'岗位新增',104,2,'','',1,'F','0','0','system:post:add','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1023,'岗位修改',104,3,'','',1,'F','0','0','system:post:edit','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1024,'岗位删除',104,4,'','',1,'F','0','0','system:post:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1025,'岗位导出',104,5,'','',1,'F','0','0','system:post:export','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1026,'字典查询',105,1,'#','',1,'F','0','0','system:dict:query','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1027,'字典新增',105,2,'#','',1,'F','0','0','system:dict:add','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1028,'字典修改',105,3,'#','',1,'F','0','0','system:dict:edit','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1029,'字典删除',105,4,'#','',1,'F','0','0','system:dict:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1030,'字典导出',105,5,'#','',1,'F','0','0','system:dict:export','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1031,'参数查询',106,1,'#','',1,'F','0','0','system:config:query','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1032,'参数新增',106,2,'#','',1,'F','0','0','system:config:add','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1033,'参数修改',106,3,'#','',1,'F','0','0','system:config:edit','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1034,'参数删除',106,4,'#','',1,'F','0','0','system:config:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1035,'参数导出',106,5,'#','',1,'F','0','0','system:config:export','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1036,'公告查询',107,1,'#','',1,'F','0','0','system:notice:query','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1037,'公告新增',107,2,'#','',1,'F','0','0','system:notice:add','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1038,'公告修改',107,3,'#','',1,'F','0','0','system:notice:edit','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1039,'公告删除',107,4,'#','',1,'F','0','0','system:notice:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1040,'操作查询',500,1,'#','',1,'F','0','0','monitor:operlog:query','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1041,'操作删除',500,2,'#','',1,'F','0','0','monitor:operlog:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1042,'日志导出',500,4,'#','',1,'F','0','0','monitor:operlog:export','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1043,'登录查询',501,1,'#','',1,'F','0','0','monitor:logininfor:query','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1044,'登录删除',501,2,'#','',1,'F','0','0','monitor:logininfor:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1045,'日志导出',501,3,'#','',1,'F','0','0','monitor:logininfor:export','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1046,'在线查询',109,1,'#','',1,'F','0','0','monitor:online:query','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1047,'批量强退',109,2,'#','',1,'F','0','0','monitor:online:batchLogout','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1048,'单条强退',109,3,'#','',1,'F','0','0','monitor:online:forceLogout','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1049,'任务查询',110,1,'#','',1,'F','0','0','monitor:job:query','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1050,'任务新增',110,2,'#','',1,'F','0','0','monitor:job:add','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1051,'任务修改',110,3,'#','',1,'F','0','0','monitor:job:edit','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1052,'任务删除',110,4,'#','',1,'F','0','0','monitor:job:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1053,'状态修改',110,5,'#','',1,'F','0','0','monitor:job:changeStatus','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1054,'任务导出',110,7,'#','',1,'F','0','0','monitor:job:export','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1055,'生成查询',114,1,'#','',1,'F','0','0','tool:gen:query','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1056,'生成修改',114,2,'#','',1,'F','0','0','tool:gen:edit','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1057,'生成删除',114,3,'#','',1,'F','0','0','tool:gen:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1058,'导入代码',114,2,'#','',1,'F','0','0','tool:gen:import','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1059,'预览代码',114,4,'#','',1,'F','0','0','tool:gen:preview','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1060,'生成代码',114,5,'#','',1,'F','0','0','tool:gen:code','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(1061,'序列号',0,4,'serial',NULL,1,'M','0','0','','number','admin','2020-11-26 16:28:59','admin','2020-11-27 09:04:21',''),
	(1062,'序列号列表',1061,1,'list','serial/index',1,'C','0','0','serial:segment:list','list','admin','2020-11-26 16:36:35','admin','2020-11-27 15:56:59',''),
	(1063,'修改序列',1062,1,'',NULL,1,'F','0','0','serial:segment:update','#','admin','2020-11-27 10:22:44','admin','2020-11-27 15:56:36',''),
	(1064,'日志详情',2,1,'monitor/perflog','monitor/perflog/index',1,'C','0','0','monitor:perflog:list','druid','admin','2021-01-14 18:56:55','admin','2021-01-14 19:03:31','');

/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_notice
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_notice`;

CREATE TABLE `sys_notice` (
  `notice_id` int(4) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) NOT NULL COMMENT '公告标题',
  `notice_type` char(1) NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` varchar(2000) DEFAULT NULL COMMENT '公告内容',
  `status` char(1) DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通知公告表';

LOCK TABLES `sys_notice` WRITE;
/*!40000 ALTER TABLE `sys_notice` DISABLE KEYS */;

INSERT INTO `sys_notice` (`notice_id`, `notice_title`, `notice_type`, `notice_content`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES
	(1,'温馨提醒：2018-07-01 若依新版本发布啦','2','新版本内容','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','管理员'),
	(2,'维护通知：2018-07-01 若依系统凌晨维护','1','维护内容','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','管理员');

/*!40000 ALTER TABLE `sys_notice` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_post
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_post`;

CREATE TABLE `sys_post` (
  `post_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) NOT NULL COMMENT '岗位名称',
  `post_sort` int(4) NOT NULL COMMENT '显示顺序',
  `status` char(1) NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='岗位信息表';

LOCK TABLES `sys_post` WRITE;
/*!40000 ALTER TABLE `sys_post` DISABLE KEYS */;

INSERT INTO `sys_post` (`post_id`, `post_code`, `post_name`, `post_sort`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES
	(1,'ceo','董事长',1,'0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(2,'se','项目经理',2,'0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(3,'hr','人力资源',3,'0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),
	(4,'user','普通员工',4,'0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','');

/*!40000 ALTER TABLE `sys_post` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `status` char(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色信息表';

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;

INSERT INTO `sys_role` (`role_id`, `role_name`, `role_key`, `role_sort`, `data_scope`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES
	(1,'超级管理员','admin',1,'1','0','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','超级管理员'),
	(2,'普通角色','common',2,'2','0','0','admin','2018-03-16 11:33:00','admin','2020-11-27 09:05:49','普通角色'),
	(3,'系统管理','system_admin',1,'2','0','0','admin','2020-11-27 09:23:24','admin','2020-11-27 19:12:18','系统管理');

/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_role_dept
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_role_dept`;

CREATE TABLE `sys_role_dept` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
  `create_time` datetime NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`role_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色和部门关联表';

LOCK TABLES `sys_role_dept` WRITE;
/*!40000 ALTER TABLE `sys_role_dept` DISABLE KEYS */;

INSERT INTO `sys_role_dept` (`role_id`, `dept_id`, `create_time`, `update_time`)
VALUES
	(2,100,'2000-01-01 00:00:00','2020-11-27 14:47:48'),
	(2,101,'2000-01-01 00:00:00','2020-11-27 14:47:48'),
	(2,105,'2000-01-01 00:00:00','2020-11-27 14:47:48'),
	(3,100,'2000-01-01 00:00:00','2020-11-27 15:09:00'),
	(3,101,'2000-01-01 00:00:00','2020-11-27 15:09:00'),
	(3,102,'2000-01-01 00:00:00','2020-11-27 15:09:00'),
	(3,103,'2000-01-01 00:00:00','2020-11-27 15:09:00'),
	(3,104,'2000-01-01 00:00:00','2020-11-27 15:09:00'),
	(3,105,'2000-01-01 00:00:00','2020-11-27 15:09:00'),
	(3,106,'2000-01-01 00:00:00','2020-11-27 15:09:00'),
	(3,107,'2000-01-01 00:00:00','2020-11-27 15:09:00'),
	(3,108,'2000-01-01 00:00:00','2020-11-27 15:09:00'),
	(3,109,'2000-01-01 00:00:00','2020-11-27 15:09:00');

/*!40000 ALTER TABLE `sys_role_dept` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_role_menu
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  `create_time` datetime NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色和菜单关联表';

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;

INSERT INTO `sys_role_menu` (`role_id`, `menu_id`, `create_time`, `update_time`)
VALUES
	(2,1,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,2,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,3,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,4,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,100,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,101,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,102,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,103,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,104,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,105,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,106,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,107,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,108,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,109,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,110,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,111,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,112,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,113,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,114,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,115,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,500,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,501,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1001,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1002,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1003,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1004,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1005,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1006,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1007,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1008,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1009,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1010,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1011,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1012,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1013,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1014,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1015,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1016,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1017,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1018,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1019,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1020,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1021,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1022,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1023,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1024,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1025,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1026,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1027,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1028,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1029,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1030,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1031,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1032,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1033,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1034,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1035,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1036,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1037,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1038,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1039,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1040,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1041,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1042,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1043,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1044,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1045,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1046,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1047,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1048,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1049,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1050,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1051,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1052,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1053,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1054,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1055,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1056,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1057,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1058,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1059,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1060,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1061,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(2,1062,'2000-01-01 00:00:00','2020-11-27 14:46:45'),
	(3,1,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,2,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,3,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,100,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,101,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,102,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,103,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,104,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,105,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,106,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,107,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,108,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,109,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,110,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,111,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,112,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,113,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,114,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,115,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,500,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,501,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1001,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1002,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1003,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1004,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1005,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1006,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1007,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1008,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1009,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1010,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1011,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1012,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1013,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1014,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1015,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1016,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1017,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1018,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1019,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1020,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1021,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1022,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1023,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1024,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1025,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1026,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1027,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1028,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1029,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1030,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1031,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1032,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1033,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1034,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1035,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1036,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1037,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1038,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1039,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1040,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1041,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1042,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1043,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1044,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1045,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1046,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1047,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1048,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1049,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1050,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1051,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1052,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1053,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1054,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1055,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1056,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1057,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1058,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1059,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1060,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1061,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1062,'2000-01-01 00:00:00','2020-11-27 19:12:18'),
	(3,1063,'2000-01-01 00:00:00','2020-11-27 19:12:18');

/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) DEFAULT '' COMMENT '密码',
  `status` char(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(50) DEFAULT '' COMMENT '最后登陆IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;

INSERT INTO `sys_user` (`user_id`, `dept_id`, `user_name`, `nick_name`, `user_type`, `email`, `phonenumber`, `sex`, `avatar`, `password`, `status`, `del_flag`, `login_ip`, `login_date`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES
	(1,103,'admin','若依','00','ry@163.com','15888888888','1','','$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2','0','0','127.0.0.1','2018-03-16 11:33:00','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','管理员'),
	(2,105,'ry','若依','00','ry@qq.com','15666666666','1','','$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2','0','0','127.0.0.1','2018-03-16 11:33:00','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','测试员'),
	(3,103,'guanzhisong','关志松','00','guanzhisong@gmail.com','18202794850','0','','$2a$10$muBFqXhUb4Vxall0JZWBouqnNRuKng7.eolQ9u1MMyfvm56JdMIr.','0','0','',NULL,'admin','2020-11-27 09:22:33','admin','2020-11-27 15:09:13','测试账号');

/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_user_post
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_user_post`;

CREATE TABLE `sys_user_post` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `post_id` bigint(20) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户与岗位关联表';

LOCK TABLES `sys_user_post` WRITE;
/*!40000 ALTER TABLE `sys_user_post` DISABLE KEYS */;

INSERT INTO `sys_user_post` (`user_id`, `post_id`)
VALUES
	(1,1),
	(2,2),
	(3,4);

/*!40000 ALTER TABLE `sys_user_post` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_user_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `create_time` datetime NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户和角色关联表';

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;

INSERT INTO `sys_user_role` (`user_id`, `role_id`, `create_time`, `update_time`)
VALUES
	(1,1,'2000-01-01 00:00:00','2020-11-27 14:50:02'),
	(2,2,'2000-01-01 00:00:00','2020-11-27 14:50:02'),
	(3,3,'2000-01-01 00:00:00','2020-11-27 15:09:13');

/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
