/*
 Navicat Premium Data Transfer

 Source Server         : ppl-xxx1
 Source Server Type    : MySQL
 Source Server Version : 50644
 Source Host           : 192.168.102.36:3306
 Source Schema         : integration

 Target Server Type    : MySQL
 Target Server Version : 50644
 File Encoding         : 65001

 Date: 01/07/2019 14:52:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for broker_sign_config
-- ----------------------------
DROP TABLE IF EXISTS `broker_sign_config`;
CREATE TABLE `broker_sign_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `broker_id` int(11) NOT NULL COMMENT '商家 broker id',
  `sign` varchar(64) NOT NULL COMMENT '签名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_broker_id` (`broker_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='borker 与 sign 配置表';

-- ----------------------------
-- Table structure for calc_daily_result
-- ----------------------------
DROP TABLE IF EXISTS `calc_daily_result`;
CREATE TABLE `calc_daily_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date` varchar(45) NOT NULL,
  `mail_success_count_zh` int(11) DEFAULT NULL,
  `mail_failed_count_zh` int(11) DEFAULT NULL,
  `sms_success_count_zh` int(11) DEFAULT NULL,
  `sms_failed_count_zh` int(11) DEFAULT NULL,
  `mail_people_count_zh` int(11) DEFAULT NULL,
  `sms_people_count_zh` int(11) DEFAULT NULL,
  `mail_success_count_en` int(11) DEFAULT NULL,
  `mail_failed_count_en` int(11) DEFAULT NULL,
  `sms_success_count_en` int(11) DEFAULT NULL,
  `sms_failed_count_en` int(11) DEFAULT NULL,
  `mail_people_count_en` int(11) DEFAULT NULL,
  `sms_people_count_en` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for flyway_schema_history
-- ----------------------------
DROP TABLE IF EXISTS `flyway_schema_history`;
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for int_message
-- ----------------------------
DROP TABLE IF EXISTS `int_message`;
CREATE TABLE `int_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_date` timestamp NULL DEFAULT NULL,
  `modify_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `country_code` varchar(50) DEFAULT NULL,
  `phone_number` varchar(100) DEFAULT '',
  `email_address` varchar(200) DEFAULT '',
  `from_alias` varchar(100) DEFAULT NULL,
  `template_code` varchar(100) DEFAULT NULL,
  `template_params` varchar(5000) DEFAULT NULL,
  `subject` varchar(1000) DEFAULT NULL,
  `content` varchar(5000) DEFAULT NULL,
  `is_sent` tinyint(1) NOT NULL DEFAULT '0',
  `locale` varchar(10) DEFAULT 'en_us' COMMENT '消息语言(zh_cn;en_us等)',
  `retry_times` int(20) NOT NULL DEFAULT '0',
  `next_retry_time` timestamp NULL DEFAULT NULL,
  `broker_id` int(11) DEFAULT '1' COMMENT 'broker id',
  PRIMARY KEY (`id`),
  KEY `ix_template_code` (`template_code`) USING BTREE,
  KEY `ix_next_retry_time` (`next_retry_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COMMENT='发送的消息(邮件/短信等)表';

-- ----------------------------
-- Table structure for int_message_blacklist
-- ----------------------------
DROP TABLE IF EXISTS `int_message_blacklist`;
CREATE TABLE `int_message_blacklist` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `type` varchar(4) NOT NULL DEFAULT '' COMMENT '类型sms|mail',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '手机或邮箱',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `broker_id` int(11) DEFAULT '1' COMMENT 'broker id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_type_name` (`type`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信与邮件黑名单表';

-- ----------------------------
-- Table structure for int_message_send_status_detail
-- ----------------------------
DROP TABLE IF EXISTS `int_message_send_status_detail`;
CREATE TABLE `int_message_send_status_detail` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '标识自增',
  `msg_id` bigint(20) unsigned NOT NULL COMMENT '消息ID',
  `channel` varchar(50) NOT NULL COMMENT '发送渠道代号',
  `type` varchar(10) NOT NULL COMMENT '信息类型(mail|sms)',
  `region` varchar(20) NOT NULL COMMENT '服务地区(china|international)',
  `status` int(10) unsigned NOT NULL COMMENT '是否成功(1表示成功，0表示失败,其他保留)',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `broker_id` int(11) DEFAULT '1' COMMENT 'broker id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='信息发送状态明细表';

-- ----------------------------
-- Table structure for int_message_success_ratio
-- ----------------------------
DROP TABLE IF EXISTS `int_message_success_ratio`;
CREATE TABLE `int_message_success_ratio` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增标识',
  `channel` varchar(50) NOT NULL COMMENT '发送渠道代号',
  `type` varchar(10) NOT NULL COMMENT '信息类型(MAIL|SMS)',
  `region` varchar(20) NOT NULL COMMENT '服务地区(china|international)\n',
  `ratio` double NOT NULL COMMENT '成功率',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='信息息发送成功率统计表';

-- ----------------------------
-- Table structure for int_message_template
-- ----------------------------
DROP TABLE IF EXISTS `int_message_template`;
CREATE TABLE `int_message_template` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '标识',
  `code` varchar(50) NOT NULL COMMENT '模板唯一编号',
  `sign` varchar(30) NOT NULL DEFAULT '' COMMENT '模板签名',
  `subject` varchar(100) NOT NULL COMMENT '邮件主题',
  `locale` varchar(10) NOT NULL DEFAULT 'zh_cn' COMMENT '模板语言(zh_cn|en_us|ko_kr等）',
  `type` varchar(10) NOT NULL COMMENT '模板类型(email|sms等)',
  `content` varchar(1000) NOT NULL COMMENT '模板内容',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code_locale_type` (`code`,`locale`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=349 DEFAULT CHARSET=utf8 COMMENT='信息模板表';

-- ----------------------------
-- Table structure for int_message_whitelist
-- ----------------------------
DROP TABLE IF EXISTS `int_message_whitelist`;
CREATE TABLE `int_message_whitelist` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增标识',
  `type` varchar(10) NOT NULL DEFAULT '' COMMENT 'mail|sms等',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT 'mobile或email地址',
  `max_request_times` int(11) NOT NULL COMMENT '发送次数上限',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `broker_id` int(11) DEFAULT '1' COMMENT 'broker id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_type_name` (`type`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信与邮件发送白名单表';

-- ----------------------------
-- Table structure for int_provider_conf
-- ----------------------------
DROP TABLE IF EXISTS `int_provider_conf`;
CREATE TABLE `int_provider_conf` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '信息提供者唯一名称',
  `weight` double NOT NULL DEFAULT '5' COMMENT '在同类所有提供者的权重',
  `type` varchar(20) NOT NULL DEFAULT 'sms' COMMENT '类型(sms|mail)',
  `region` varchar(20) NOT NULL DEFAULT 'china' COMMENT '服务地区(china|international)',
  `options` varchar(1000) NOT NULL DEFAULT '' COMMENT '信息提供者配置选项(JSON格式）',
  `memo` varchar(50) NOT NULL DEFAULT '' COMMENT '备注',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态0禁用,1启用其他保留',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `broker_id` int(11) DEFAULT '1' COMMENT 'broker id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name_region_broker` (`name`,`region`,`broker_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `result` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for warning_config
-- ----------------------------
DROP TABLE IF EXISTS `warning_config`;
CREATE TABLE `warning_config` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '配置Id',
  `code` varchar(50) NOT NULL COMMENT '配置code',
  `biz_type` varchar(20) NOT NULL COMMENT '业务类型，spot,c2c',
  `content` varchar(1000) NOT NULL COMMENT '报警详情',
  `memo` varchar(100) NOT NULL COMMENT '配置备注',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报警配置表';

SET FOREIGN_KEY_CHECKS = 1;
