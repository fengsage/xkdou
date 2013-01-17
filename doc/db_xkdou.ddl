drop database db_xkdou;

create database db_xkdou;

use db_xkdou;

CREATE TABLE IF NOT EXISTS `child_info` (
  `id` INT unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `realname` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '真实姓名',
  `pic` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '真实照片',
  `info_from` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '数据来源',
  `info_from_no` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '数据来源处编号',
  `info_from_url` VARCHAR(500) NOT NULL DEFAULT '' COMMENT '数据来源URL地址',
  `sex` tinyint NOT NULL DEFAULT '0' COMMENT '性别(0:男,1:女)',
  `birthday` DATE DEFAULT '0000-00-00' COMMENT '出生年月',
  `height` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '身高',
  `weight` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '体重',
  `lost_time` DATETIME  DEFAULT '0000-00-00 00:00:00' COMMENT '走失时间',
  `lost_addr` VARCHAR(500) NOT NULL DEFAULT '' COMMENT '走势地点',
  `tezheng` VARCHAR(2048) NOT NULL DEFAULT '' COMMENT '特征',
  `remark` VARCHAR(2048) NOT NULL DEFAULT '' COMMENT '描述',
  `is_show` tinyint(3) NOT NULL DEFAULT '0' COMMENT '是否前台显示',
  `is_del` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除,1:删除)',
  `is_finded` tinyint(3) NOT NULL DEFAULT '0' COMMENT '已经找到(0:未找到,1:已经找到)',
  `type` tinyint(3) NOT NULL DEFAULT '0' COMMENT '儿童类型(0:默认不区分,1:家寻找宝贝,2:寻家宝贝,3:流浪乞讨,4:其他寻人,5:海外寻人)',
  `create_time` DATETIME  DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_child_info_info_from` (`info_from`),
  KEY `idx_child_info_info_from_no` (`info_from_no`),
  KEY `idx_child_info_info_from_url` (`info_from_url`),
  KEY `idx_child_info_create_time` (`create_time`),
  UNIQUE KEY `idx_child_info_info_from_from_no` (`info_from`,`info_from_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='儿童信息';


DROP TABLE IF EXISTS `xkd_auth_user`;
CREATE TABLE IF NOT EXISTS `xkd_auth_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `role_ids` varchar(10240) NOT NULL COMMENT '',
  `last_login_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最后登录时间',
  `update_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `add_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '添加的时间',
  PRIMARY KEY (`id`),
  KEY `idx_xkd_auth_user_username` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=0;

DROP TABLE IF EXISTS `xkd_auth_role`;
CREATE TABLE IF NOT EXISTS `xkd_auth_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(20) NOT NULL COMMENT '角色名字',
  `permissions_ids` varchar(10240) NOT NULL COMMENT '',
  `update_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `add_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '添加的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=0;


DROP TABLE IF EXISTS `xkd_auth_permission`;
CREATE TABLE IF NOT EXISTS `xkd_auth_permission` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) NOT NULL COMMENT '权限名字',
  `alias` varchar(50) NOT NULL COMMENT '权限别名',
  `parent_id` int(10) unsigned NOT NULL COMMENT '父级权限',
  `is_menu` tinyint(3) unsigned NOT NULL default '0' COMMENT '是否是菜单',
  `permission` varchar(30) NOT NULL COMMENT '权限值',
  `add_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '添加的时间',
  PRIMARY KEY (`id`),
  KEY `idx_xkd_auth_permission_alias` (`alias`),
  KEY `idx_xkd_auth_permission_parent_id` (`parent_id`),
  UNIQUE `udx_xkd_auth_permission_alias_parent_id` (`alias`,`parent_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=0;






