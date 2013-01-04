drop database db_childrenhome;

create database db_childrenhome;
use db_childrenhome;
-- 系统表

CREATE TABLE IF NOT EXISTS `child_info` (
  `id` INT unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `realname` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '真实姓名',
  `pic` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '真实照片',
  `info_from` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '数据来源',
  `info_from_no` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '数据来源处编号',
  `info_from_url` VARCHAR(500) NOT NULL DEFAULT '' COMMENT '数据来源URL地址',
  `sex` INT NOT NULL DEFAULT '0' COMMENT '性别(0:男,1:女)',
  `birthday` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '出生年月',
  `height` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '身高',
  `weight` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '体重',
  `loat_time` datetime  DEFAULT '0000-00-00 00:00:00' COMMENT '走失时间',
  `lost_addr` VARCHAR(500) NOT NULL DEFAULT '' COMMENT '走势地点',
  `tezheng` VARCHAR(500) NOT NULL DEFAULT '' COMMENT '特征',
  `remark` VARCHAR(500) NOT NULL DEFAULT '' COMMENT '描述',
  `create_time` datetime  DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_child_info_info_from` (`info_from`),
  KEY `idx_child_info_info_from_no` (`info_from_no`),
  KEY `idx_child_info_info_from_url` (`info_from_url`),
  KEY `idx_child_info_create_time` (`create_time`),
  UNIQUE KEY `idx_child_info_info_from_from_no` (`info_from`,`info_from_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='儿童信息';


