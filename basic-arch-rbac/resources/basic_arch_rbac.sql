# ************************************************************
# Sequel Pro SQL dump
# Version 5425
#
# https://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.26)
# Database: abel_guns
# Generation Time: 2019-11-08 10:53:02 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table sys_dept
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_dept`;

CREATE TABLE `sys_dept` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `pid` bigint(11) NOT NULL DEFAULT '0' COMMENT '父部门id',
  `pids` varchar(255) NOT NULL DEFAULT '' COMMENT '父级ids',
  `simplename` varchar(45) NOT NULL DEFAULT '' COMMENT '简称',
  `fullname` varchar(255) NOT NULL DEFAULT '' COMMENT '全称',
  `tips` varchar(255) NOT NULL DEFAULT '' COMMENT '提示',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本（乐观锁保留字段）',
  `create_time` datetime NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '创建时间',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;

INSERT INTO `sys_dept` (`id`, `num`, `pid`, `pids`, `simplename`, `fullname`, `tips`, `version`, `create_time`, `timestamp`)
VALUES
	(24,1,0,'[0],','总公司','总公司','',0,'2000-01-01 00:00:00','2019-11-08 14:50:08'),
	(25,2,24,'[0],[24],','开发部','开发部','',0,'2000-01-01 00:00:00','2019-11-08 14:50:08'),
	(26,3,24,'[0],[24],','运营部','运营部','',0,'2000-01-01 00:00:00','2019-11-08 14:50:08'),
	(27,4,24,'[0],[24],','战略部','战略部','',0,'2000-01-01 00:00:00','2019-11-08 14:50:08');

/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_dict
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_dict`;

CREATE TABLE `sys_dict` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `pid` bigint(11) NOT NULL DEFAULT '0' COMMENT '父级字典',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '名称',
  `tips` varchar(255) NOT NULL DEFAULT '' COMMENT '提示',
  `create_time` datetime NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '创建时间',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';

LOCK TABLES `sys_dict` WRITE;
/*!40000 ALTER TABLE `sys_dict` DISABLE KEYS */;

INSERT INTO `sys_dict` (`id`, `num`, `pid`, `name`, `tips`, `create_time`, `timestamp`)
VALUES
	(16,0,0,'状态','','2000-01-01 00:00:00','2019-11-08 14:50:28'),
	(17,1,16,'启用','','2000-01-01 00:00:00','2019-11-08 14:50:28'),
	(18,2,16,'禁用','','2000-01-01 00:00:00','2019-11-08 14:50:28'),
	(29,0,0,'性别','','2000-01-01 00:00:00','2019-11-08 14:50:28'),
	(30,1,29,'男','','2000-01-01 00:00:00','2019-11-08 14:50:28'),
	(31,2,29,'女','','2000-01-01 00:00:00','2019-11-08 14:50:28'),
	(35,0,0,'账号状态','','2000-01-01 00:00:00','2019-11-08 14:50:28'),
	(36,1,35,'启用','','2000-01-01 00:00:00','2019-11-08 14:50:28'),
	(37,2,35,'冻结','','2000-01-01 00:00:00','2019-11-08 14:50:28'),
	(38,3,35,'已删除','','2000-01-01 00:00:00','2019-11-08 14:50:28');

/*!40000 ALTER TABLE `sys_dict` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_menu
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(64) NOT NULL DEFAULT '' COMMENT '菜单编号',
  `pcode` varchar(64) NOT NULL DEFAULT '' COMMENT '菜单父编号',
  `pcodes` varchar(512) NOT NULL DEFAULT '' COMMENT '当前菜单的所有父菜单编号',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '菜单名称',
  `icon` varchar(128) NOT NULL DEFAULT '' COMMENT '菜单图标',
  `url` varchar(128) NOT NULL DEFAULT '' COMMENT 'url地址',
  `num` int(65) NOT NULL DEFAULT '0' COMMENT '菜单排序号',
  `levels` int(65) NOT NULL DEFAULT '0' COMMENT '菜单层级',
  `ismenu` int(11) NOT NULL DEFAULT '0' COMMENT '是否是菜单（1：是  0：不是）',
  `tips` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `status` int(65) NOT NULL DEFAULT '0' COMMENT '菜单状态 :  1:启用   0:不启用',
  `isopen` int(11) NOT NULL DEFAULT '0' COMMENT '是否打开:    1:打开   0:不打开',
  `create_time` datetime NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '创建时间',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;

INSERT INTO `sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`, `create_time`, `timestamp`)
VALUES
	(105,'system','0','[0],','系统管理','fa-user','#',4,1,1,'',1,1,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(106,'mgr','system','[0],[system],','用户管理','','/mgr',1,2,1,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(107,'mgr_add','mgr','[0],[system],[mgr],','添加用户','','/mgr/add',1,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(108,'mgr_edit','mgr','[0],[system],[mgr],','修改用户','','/mgr/edit',2,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(109,'mgr_delete','mgr','[0],[system],[mgr],','删除用户','','/mgr/delete',3,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(110,'mgr_reset','mgr','[0],[system],[mgr],','重置密码','','/mgr/reset',4,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(111,'mgr_freeze','mgr','[0],[system],[mgr],','冻结用户','','/mgr/freeze',5,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(112,'mgr_unfreeze','mgr','[0],[system],[mgr],','解除冻结用户','','/mgr/unfreeze',6,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(113,'mgr_setRole','mgr','[0],[system],[mgr],','分配角色','','/mgr/setRole',7,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(114,'role','system','[0],[system],','角色管理','','/role',2,2,1,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(115,'role_add','role','[0],[system],[role],','添加角色','','/role/add',1,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(116,'role_edit','role','[0],[system],[role],','修改角色','','/role/edit',2,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(117,'role_remove','role','[0],[system],[role],','删除角色','','/role/remove',3,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(118,'role_setAuthority','role','[0],[system],[role],','配置权限','','/role/setAuthority',4,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(119,'menu','system','[0],[system],','菜单管理','','/menu',4,2,1,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(120,'menu_add','menu','[0],[system],[menu],','添加菜单','','/menu/add',1,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(121,'menu_edit','menu','[0],[system],[menu],','修改菜单','','/menu/edit',2,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(122,'menu_remove','menu','[0],[system],[menu],','删除菜单','','/menu/remove',3,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(128,'log','system','[0],[system],','业务日志','','/log',6,2,1,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(130,'druid','system','[0],[system],','监控管理','','/druid',7,2,1,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(131,'dept','system','[0],[system],','部门管理','','/dept',3,2,1,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(132,'dict','system','[0],[system],','字典管理','','/dict',4,2,1,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(133,'loginLog','system','[0],[system],','登录日志','','/loginLog',6,2,1,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(134,'log_clean','log','[0],[system],[log],','清空日志','','/log/delLog',3,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(135,'dept_add','dept','[0],[system],[dept],','添加部门','','/dept/add',1,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(136,'dept_update','dept','[0],[system],[dept],','修改部门','','/dept/update',1,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(137,'dept_delete','dept','[0],[system],[dept],','删除部门','','/dept/delete',1,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(138,'dict_add','dict','[0],[system],[dict],','添加字典','','/dict/add',1,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(139,'dict_update','dict','[0],[system],[dict],','修改字典','','/dict/update',1,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(140,'dict_delete','dict','[0],[system],[dict],','删除字典','','/dict/delete',1,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(141,'notice','system','[0],[system],','通知管理','','/notice',9,2,1,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(142,'notice_add','notice','[0],[system],[notice],','添加通知','','/notice/add',1,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(143,'notice_update','notice','[0],[system],[notice],','修改通知','','/notice/update',2,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(144,'notice_delete','notice','[0],[system],[notice],','删除通知','','/notice/delete',3,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(145,'hello','0','[0],','通知','fa-rocket','/notice/hello',1,1,1,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(148,'code','0','[0],','代码生成','fa-code','/code',3,1,1,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(149,'api_mgr','0','[0],','接口文档','fa-leaf','/swagger-ui.html',2,1,1,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(150,'to_menu_edit','menu','[0],[system],[menu],','菜单编辑跳转','','/menu/menu_edit',4,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(151,'menu_list','menu','[0],[system],[menu],','菜单列表','','/menu/list',5,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(152,'to_dept_update','dept','[0],[system],[dept],','修改部门跳转','','/dept/dept_update',4,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(153,'dept_list','dept','[0],[system],[dept],','部门列表','','/dept/list',5,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(154,'dept_detail','dept','[0],[system],[dept],','部门详情','','/dept/detail',6,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(155,'to_dict_edit','dict','[0],[system],[dict],','修改菜单跳转','','/dict/dict_edit',4,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(156,'dict_list','dict','[0],[system],[dict],','字典列表','','/dict/list',5,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(157,'dict_detail','dict','[0],[system],[dict],','字典详情','','/dict/detail',6,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(158,'log_list','log','[0],[system],[log],','日志列表','','/log/list',2,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(159,'log_detail','log','[0],[system],[log],','日志详情','','/log/detail',3,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(160,'del_login_log','loginLog','[0],[system],[loginLog],','清空登录日志','','/loginLog/delLoginLog',1,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(161,'login_log_list','loginLog','[0],[system],[loginLog],','登录日志列表','','/loginLog/list',2,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(162,'to_role_edit','role','[0],[system],[role],','修改角色跳转','','/role/role_edit',5,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(163,'to_role_assign','role','[0],[system],[role],','角色分配跳转','','/role/role_assign',6,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(164,'role_list','role','[0],[system],[role],','角色列表','','/role/list',7,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(165,'to_assign_role','mgr','[0],[system],[mgr],','分配角色跳转','','/mgr/role_assign',8,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(166,'to_user_edit','mgr','[0],[system],[mgr],','编辑用户跳转','','/mgr/user_edit',9,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33'),
	(167,'mgr_list','mgr','[0],[system],[mgr],','用户列表','','/mgr/list',10,3,0,'',1,0,'2000-01-01 00:00:00','2019-11-08 14:50:33');

/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_notice
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_notice`;

CREATE TABLE `sys_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(255) NOT NULL DEFAULT '' COMMENT '标题',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '类型',
  `content` text NOT NULL COMMENT '内容',
  `creater` int(11) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '创建时间',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通知表';

LOCK TABLES `sys_notice` WRITE;
/*!40000 ALTER TABLE `sys_notice` DISABLE KEYS */;

INSERT INTO `sys_notice` (`id`, `title`, `type`, `content`, `creater`, `create_time`, `timestamp`)
VALUES
	(6,'世界',10,'欢迎使用Guns管理系统',1,'2000-01-01 00:00:00','2019-11-08 14:50:37'),
	(8,'你好',0,'你好',1,'2000-01-01 00:00:00','2019-11-08 14:50:37');

/*!40000 ALTER TABLE `sys_notice` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_relation
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_relation`;

CREATE TABLE `sys_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menu_id` bigint(11) NOT NULL COMMENT '菜单id',
  `role_id` bigint(11) NOT NULL COMMENT '角色id',
  `create_time` datetime NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '创建时间',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_menu_id` (`menu_id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(11) NOT NULL COMMENT '用户id',
  `role_id` bigint(11) NOT NULL COMMENT '角色id',
  `create_time` datetime NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '创建时间',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户和角色关联表';

LOCK TABLES `sys_relation` WRITE;
/*!40000 ALTER TABLE `sys_relation` DISABLE KEYS */;

INSERT INTO `sys_relation` (`id`, `menu_id`, `role_id`, `create_time`, `timestamp`)
VALUES
	(3377,105,5,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3378,106,5,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3379,107,5,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3380,108,5,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3381,109,5,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3382,110,5,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3383,111,5,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3384,112,5,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3385,113,5,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3386,114,5,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3387,115,5,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3388,116,5,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3389,117,5,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3390,118,5,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3391,119,5,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3392,120,5,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3393,121,5,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3394,122,5,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3395,150,5,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3396,151,5,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3679,105,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3680,106,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3681,107,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3682,108,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3683,109,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3684,110,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3685,111,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3686,112,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3687,113,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3688,165,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3689,166,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3690,167,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3691,114,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3692,115,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3693,116,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3694,117,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3695,118,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3696,162,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3697,163,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3698,164,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3699,119,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3700,120,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3701,121,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3702,122,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3703,150,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3704,151,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3705,128,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3706,134,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3707,158,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3708,159,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3709,130,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3710,131,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3711,135,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3712,136,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3713,137,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3714,152,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3715,153,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3716,154,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3717,132,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3718,138,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3719,139,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3720,140,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3721,155,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3722,156,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3723,157,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3724,133,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3725,160,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3726,161,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3727,141,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3728,142,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3729,143,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3730,144,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3731,148,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3732,145,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3733,149,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3734,168,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3735,169,1,'2000-01-01 00:00:00','2019-11-08 14:50:45'),
	(3736,170,1,'2000-01-01 00:00:00','2019-11-08 14:50:45');

/*!40000 ALTER TABLE `sys_relation` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) NOT NULL DEFAULT '0' COMMENT '序号',
  `pid` bigint(11) NOT NULL DEFAULT '0' COMMENT '父角色id',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '角色名称',
  `dept_id` bigint(11) NOT NULL DEFAULT '0' COMMENT '部门名称',
  `tips` varchar(128) NOT NULL DEFAULT '' COMMENT '提示',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '保留字段(暂时没用）',
  `create_time` datetime NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '创建时间',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;

INSERT INTO `sys_role` (`id`, `num`, `pid`, `name`, `dept_id`, `tips`, `version`, `create_time`, `timestamp`)
VALUES
	(1,1,0,'超级管理员',24,'administrator',1,'2000-01-01 00:00:00','2019-11-08 14:50:51'),
	(5,2,1,'临时',26,'temp',0,'2000-01-01 00:00:00','2019-11-08 14:50:51');

/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `avatar` varchar(64) NOT NULL DEFAULT '' COMMENT '头像',
  `account` varchar(64) NOT NULL DEFAULT '' COMMENT '账号',
  `password` varchar(64) NOT NULL DEFAULT '' COMMENT '密码',
  `salt` varchar(32) NOT NULL DEFAULT '' COMMENT 'md5密码盐',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '名字',
  `birthday` datetime NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '生日',
  `sex` int(11) NOT NULL DEFAULT '0' COMMENT '性别（1：男 2：女）',
  `email` varchar(64) NOT NULL DEFAULT '' COMMENT '电子邮件',
  `phone` varchar(32) NOT NULL DEFAULT '' COMMENT '电话',
  `role_id` varchar(512) NOT NULL DEFAULT '' COMMENT '角色id',
  `dept_id` bigint(11) NOT NULL DEFAULT '0' COMMENT '部门id',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态(1：启用  2：冻结  3：删除）',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '保留字段',
  `create_time` datetime NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '创建时间',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员表';

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;

INSERT INTO `sys_user` (`id`, `avatar`, `account`, `password`, `salt`, `name`, `birthday`, `sex`, `email`, `phone`, `role_id`, `dept_id`, `status`, `version`, `create_time`, `timestamp`)
VALUES
	(1,'girl.gif','admin','ecfadcde9305f8891bcfe5a1e28c253e','8pgby','张三','2017-05-05 00:00:00',2,'sn93@qq.com','18200000000','1',27,1,25,'2000-01-01 00:00:00','2019-11-08 14:50:57'),
	(44,'','test','45abb7879f6a8268f1ef600e6038ac73','ssts3','test','2017-05-01 00:00:00',1,'abc@123.com','','5',26,3,0,'2000-01-01 00:00:00','2019-11-08 14:50:57'),
	(45,'','boss','71887a5ad666a18f709e1d4e693d5a35','1f7bf','老板','2017-12-04 00:00:00',1,'','','1',24,1,0,'2000-01-01 00:00:00','2019-11-08 14:50:57'),
	(46,'','manager','b53cac62e7175637d4beb3b16b2f7915','j3cs9','经理','2017-12-04 00:00:00',1,'','','1',24,1,0,'2000-01-01 00:00:00','2019-11-08 14:50:57');

/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
