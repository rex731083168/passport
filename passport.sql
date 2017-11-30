CREATE DATABASE passport;
USE passport;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`username` varchar(64) CHARACTER SET utf8 DEFAULT NULL,
`password` varchar(64) CHARACTER SET utf8 DEFAULT NULL,
`nickname` varchar(64) CHARACTER SET utf8 DEFAULT NULL,
`headimg` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
`mobile` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
`email` varchar(64) CHARACTER SET utf8 DEFAULT NULL,
`create_time` datetime DEFAULT NULL,
`login_time` datetime DEFAULT NULL,
`last_login_time` datetime DEFAULT NULL,
`counts` int(11) DEFAULT NULL,
`status` tinyint(4) DEFAULT '0',
`isvalid` tinyint(4) DEFAULT '0',
`parentId` int(11) DEFAULT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `mobile` (`mobile`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
 
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
`role_id` int(10) NOT NULL AUTO_INCREMENT,
`role_name` varchar(64) CHARACTER SET utf8 NOT NULL,
`description` varchar(200) CHARACTER SET utf8 DEFAULT '' COMMENT '描述',
`create_time` datetime DEFAULT NULL COMMENT '创建日期',
`status` tinyint(4) DEFAULT NULL,
PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
 
DROP TABLE IF EXISTS `auth`;
CREATE TABLE `auth` (
`auth_id` int(10) NOT NULL AUTO_INCREMENT,
`auth_name` varchar(64) CHARACTER SET utf8 NOT NULL,
`description` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
`belongSys` int(11) DEFAULT NULL,
`url` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
`parentId` int(10) DEFAULT '0',
`status` tinyint(4) DEFAULT '0',
`isvalid` tinyint(4) DEFAULT '0',
`create_time` datetime DEFAULT NULL,
PRIMARY KEY (`auth_id`)
) ENGINE=InnoDB AUTO_INCREMENT=165 DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `r_roleauth`;
CREATE TABLE `r_roleauth` (
`ra_id` int(10) NOT NULL AUTO_INCREMENT,
`role_id` int(10) NOT NULL,
`auth_id` int(10) NOT NULL,
PRIMARY KEY (`ra_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
 
DROP TABLE IF EXISTS `r_userrole`;
CREATE TABLE `r_userrole` (
`ur_id` int(10) NOT NULL AUTO_INCREMENT,
`uid` int(10) NOT NULL,
`role_id` int(10) NOT NULL,
PRIMARY KEY (`ur_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
 
DROP TABLE IF EXISTS `sysinfo`;
CREATE TABLE `sysinfo` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`sysName` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
`description` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
`create_time` datetime DEFAULT NULL,
`status` tinyint(4) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
 
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`uid` mediumtext,
`client_ip` varchar(255) DEFAULT NULL,
`log_time` datetime DEFAULT NULL,
`log_system` tinyint(4) DEFAULT NULL,
`log_message` varchar(255) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;