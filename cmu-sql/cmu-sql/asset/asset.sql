/*
 Navicat Premium Data Transfer

 Source Server         : ppl-xxx1
 Source Server Type    : MySQL
 Source Server Version : 50644
 Source Host           : 192.168.102.36:3306
 Source Schema         : asset

 Target Server Type    : MySQL
 Target Server Version : 50644
 File Encoding         : 65001

 Date: 01/07/2019 14:55:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `currency` int(11) NOT NULL COMMENT '币种',
  `acc_lock_position` decimal(32,8) NOT NULL COMMENT '锁仓账户',
  `status` int(2) NOT NULL COMMENT '状态 0正常 1已锁定',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `broker_id` int(10) DEFAULT '1' COMMENT '券商id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `broker` (`user_id`,`currency`,`broker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户账户表';

-- ----------------------------
-- Table structure for asset_currency_compress
-- ----------------------------
DROP TABLE IF EXISTS `asset_currency_compress`;
CREATE TABLE `asset_currency_compress` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '币种id',
  `symbol` varchar(128) NOT NULL DEFAULT '' COMMENT '币种代号 例:CNY BTC LTC',
  `extend_attrs` text COMMENT '拓展配置',
  `withdrawable` int(8) NOT NULL DEFAULT '1' COMMENT '是否可提现 1:可提现，0:不可提现',
  `rechargeable` int(8) NOT NULL DEFAULT '1' COMMENT '是否可充值 1:可充值，0:不可充值',
  `sort` int(8) NOT NULL DEFAULT '0' COMMENT '排序系数',
  `online` int(8) NOT NULL DEFAULT '1' COMMENT '上线',
  `exchange` int(8) NOT NULL DEFAULT '0' COMMENT '兑换 0:不能兑换 1:可兑换',
  `receive` int(8) NOT NULL DEFAULT '0' COMMENT '领取 0:不能领取 1:可领取',
  `transfer` int(8) NOT NULL DEFAULT '0' COMMENT '划转 0:不能划转 1:可划转',
  `withdraw_fee` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '提现手续费',
  `deposit_confirm` int(11) NOT NULL DEFAULT '0' COMMENT '充值确认数',
  `withdraw_confirm` int(11) NOT NULL DEFAULT '0' COMMENT '提现确认数',
  `min_deposit_amount` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最小充值数目',
  `min_withdraw_amount` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最小提现数目',
  `max_withdraw_amount` decimal(16,8) DEFAULT NULL COMMENT '最大提现数目',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `broker_id` int(10) DEFAULT '1' COMMENT '券商id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `symbol` (`symbol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='币种表';

-- ----------------------------
-- Table structure for balance_summary
-- ----------------------------
DROP TABLE IF EXISTS `balance_summary`;
CREATE TABLE `balance_summary` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `currency` int(11) NOT NULL COMMENT '币种',
  `wallet_balance` decimal(32,8) NOT NULL COMMENT '钱包余额',
  `biz_balance` text,
  `deposit_unconfirmed` decimal(32,8) NOT NULL COMMENT '充值未确认',
  `withdraw_unconfirmed` decimal(32,8) NOT NULL COMMENT '提现未确认',
  `difference` decimal(32,8) NOT NULL COMMENT '差额',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `currency` (`currency`),
  KEY `create_date` (`create_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for c2c_asset_currency
-- ----------------------------
DROP TABLE IF EXISTS `c2c_asset_currency`;
CREATE TABLE `c2c_asset_currency` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '币种id',
  `symbol` varchar(128) NOT NULL DEFAULT '' COMMENT '币种代号 例:CNY BTC LTC',
  `sign` varchar(40) NOT NULL DEFAULT '' COMMENT '币种符号',
  `full_name` varchar(128) DEFAULT NULL COMMENT '全称',
  `withdrawable` int(8) NOT NULL DEFAULT '1' COMMENT '是否可提现',
  `rechargeable` int(8) NOT NULL DEFAULT '1' COMMENT '是否可充值 1:可提现，0:不可提现',
  `sort` int(8) NOT NULL DEFAULT '0' COMMENT '排序系数',
  `online` int(8) NOT NULL DEFAULT '1' COMMENT '上线',
  `currency_picture_url` text,
  `exchange` int(8) NOT NULL DEFAULT '0' COMMENT '0:不能兑换 1:可兑换',
  `receive` int(8) NOT NULL DEFAULT '0' COMMENT '0:不能领取 1:可领取',
  `transfer` int(8) NOT NULL DEFAULT '0' COMMENT '0:不能划转 1:可划转',
  `margin_interest_rate` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '杠杆利息配置',
  `withdraw_fee` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '提现手续费',
  `deposit_confirm` int(11) NOT NULL DEFAULT '0' COMMENT '充值确认数',
  `withdraw_confirm` int(11) NOT NULL DEFAULT '0' COMMENT '提现确认数',
  `min_deposit_amount` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最小充值数目',
  `min_withdraw_amount` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最小提现数目',
  `max_withdraw_amount` decimal(16,8) DEFAULT NULL,
  `tx_explorer_url` text NOT NULL,
  `need_tag` int(8) DEFAULT NULL,
  `tag_field` varchar(128) DEFAULT '',
  `currency_summary` text,
  `currency_url` text,
  `threshold` decimal(16,8) DEFAULT '0.00000000' COMMENT '对账报警阈值',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `symbol` (`symbol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='币种表';

-- ----------------------------
-- Table structure for c2c_asset_currency_compress
-- ----------------------------
DROP TABLE IF EXISTS `c2c_asset_currency_compress`;
CREATE TABLE `c2c_asset_currency_compress` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '币种id',
  `symbol` varchar(128) NOT NULL DEFAULT '' COMMENT '币种代号 例:CNY BTC LTC',
  `extend_attrs` text COMMENT '拓展配置',
  `withdrawable` int(8) NOT NULL DEFAULT '1' COMMENT '是否可提现 1:可提现，0:不可提现',
  `rechargeable` int(8) NOT NULL DEFAULT '1' COMMENT '是否可充值 1:可充值，0:不可充值',
  `sort` int(8) NOT NULL DEFAULT '0' COMMENT '排序系数',
  `online` int(8) NOT NULL DEFAULT '1' COMMENT '上线',
  `exchange` int(8) NOT NULL DEFAULT '0' COMMENT '兑换 0:不能兑换 1:可兑换',
  `receive` int(8) NOT NULL DEFAULT '0' COMMENT '领取 0:不能领取 1:可领取',
  `transfer` int(8) NOT NULL DEFAULT '0' COMMENT '划转 0:不能划转 1:可划转',
  `withdraw_fee` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '提现手续费',
  `deposit_confirm` int(11) NOT NULL DEFAULT '0' COMMENT '充值确认数',
  `withdraw_confirm` int(11) NOT NULL DEFAULT '0' COMMENT '提现确认数',
  `min_deposit_amount` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最小充值数目',
  `min_withdraw_amount` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最小提现数目',
  `max_withdraw_amount` decimal(16,8) DEFAULT NULL COMMENT '最大提现数目',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `broker_id` int(10) DEFAULT '1' COMMENT '券商id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sb` (`symbol`,`broker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='币种表';

-- ----------------------------
-- Table structure for contract_asset_currency
-- ----------------------------
DROP TABLE IF EXISTS `contract_asset_currency`;
CREATE TABLE `contract_asset_currency` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '币种id',
  `symbol` varchar(128) NOT NULL DEFAULT '' COMMENT '币种代号 例:CNY BTC LTC',
  `sign` varchar(40) NOT NULL DEFAULT '' COMMENT '币种符号',
  `full_name` varchar(128) DEFAULT NULL COMMENT '全称',
  `withdrawable` int(8) NOT NULL DEFAULT '1' COMMENT '是否可提现',
  `rechargeable` int(8) NOT NULL DEFAULT '1' COMMENT '是否可充值 1:可提现，0:不可提现',
  `sort` int(8) NOT NULL DEFAULT '0' COMMENT '排序系数',
  `online` int(8) NOT NULL DEFAULT '1' COMMENT '上线',
  `currency_picture_url` text,
  `exchange` int(8) NOT NULL DEFAULT '0' COMMENT '0:不能兑换 1:可兑换',
  `receive` int(8) NOT NULL DEFAULT '0' COMMENT '0:不能领取 1:可领取',
  `transfer` int(8) NOT NULL DEFAULT '0' COMMENT '0:不能划转 1:可划转',
  `margin_interest_rate` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '杠杆利息配置',
  `withdraw_fee` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '提现手续费',
  `deposit_confirm` int(11) NOT NULL DEFAULT '0' COMMENT '充值确认数',
  `withdraw_confirm` int(11) NOT NULL DEFAULT '0' COMMENT '提现确认数',
  `min_deposit_amount` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最小充值数目',
  `min_withdraw_amount` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最小提现数目',
  `max_withdraw_amount` decimal(16,8) DEFAULT NULL,
  `tx_explorer_url` text NOT NULL,
  `need_tag` int(8) DEFAULT NULL,
  `tag_field` varchar(128) DEFAULT '',
  `currency_summary` text,
  `currency_url` text,
  `threshold` decimal(16,8) DEFAULT '0.00000000' COMMENT '对账报警阈值',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `symbol` (`symbol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='币种表';

-- ----------------------------
-- Table structure for contract_asset_currency_compress
-- ----------------------------
DROP TABLE IF EXISTS `contract_asset_currency_compress`;
CREATE TABLE `contract_asset_currency_compress` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '币种id',
  `symbol` varchar(128) NOT NULL DEFAULT '' COMMENT '币种代号 例:CNY BTC LTC',
  `extend_attrs` text COMMENT '拓展配置',
  `withdrawable` int(8) NOT NULL DEFAULT '1' COMMENT '是否可提现 1:可提现，0:不可提现',
  `rechargeable` int(8) NOT NULL DEFAULT '1' COMMENT '是否可充值 1:可充值，0:不可充值',
  `sort` int(8) NOT NULL DEFAULT '0' COMMENT '排序系数',
  `online` int(8) NOT NULL DEFAULT '1' COMMENT '上线',
  `exchange` int(8) NOT NULL DEFAULT '0' COMMENT '兑换 0:不能兑换 1:可兑换',
  `receive` int(8) NOT NULL DEFAULT '0' COMMENT '领取 0:不能领取 1:可领取',
  `transfer` int(8) NOT NULL DEFAULT '0' COMMENT '划转 0:不能划转 1:可划转',
  `withdraw_fee` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '提现手续费',
  `deposit_confirm` int(11) NOT NULL DEFAULT '0' COMMENT '充值确认数',
  `withdraw_confirm` int(11) NOT NULL DEFAULT '0' COMMENT '提现确认数',
  `min_deposit_amount` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最小充值数目',
  `min_withdraw_amount` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最小提现数目',
  `max_withdraw_amount` decimal(16,8) DEFAULT NULL COMMENT '最大提现数目',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `broker_id` int(10) DEFAULT '1' COMMENT '券商id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sb` (`symbol`,`broker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='币种表';

-- ----------------------------
-- Table structure for currency
-- ----------------------------
DROP TABLE IF EXISTS `currency`;
CREATE TABLE `currency` (
  `id` int(4) NOT NULL COMMENT '币种id',
  `symbol` varchar(10) NOT NULL COMMENT '币种代号 例:CNY BTC LTC',
  `sign` varchar(40) NOT NULL DEFAULT '',
  `withdrawable` int(1) NOT NULL DEFAULT '1' COMMENT '是否可提现',
  `rechargeable` int(1) NOT NULL DEFAULT '1' COMMENT '是否可充值 1:可提现，0:不可提现',
  `sort` int(4) NOT NULL DEFAULT '0' COMMENT '排序系数',
  `online` int(4) NOT NULL DEFAULT '1' COMMENT '上线',
  `can_exchange` int(1) NOT NULL DEFAULT '0' COMMENT '0:不能兑换 1:可兑换',
  `can_receive` int(1) NOT NULL DEFAULT '0' COMMENT '0:不能领取 1:可领取',
  `can_transfer` int(1) NOT NULL DEFAULT '0' COMMENT '0:不能划转 1:可划转',
  `margin_interest_rate` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '杠杆利息配置',
  `withdraw_fee` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '提现手续费',
  `deposit_confirm` int(11) NOT NULL DEFAULT '0' COMMENT '充值确认数',
  `withdraw_confirm` int(11) NOT NULL DEFAULT '0' COMMENT '提现确认数',
  `min_deposit_amount` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最小充值数目',
  `min_withdraw_amount` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最小提现数目',
  `full_name` varchar(50) DEFAULT NULL COMMENT '全称',
  `modify_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='币种表';

-- ----------------------------
-- Table structure for deposit_address
-- ----------------------------
DROP TABLE IF EXISTS `deposit_address`;
CREATE TABLE `deposit_address` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(200) NOT NULL DEFAULT '',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `status` tinyint(4) NOT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `broker_id` int(10) DEFAULT '1' COMMENT '券商id',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `address` (`address`)
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
-- Table structure for locked_position
-- ----------------------------
DROP TABLE IF EXISTS `locked_position`;
CREATE TABLE `locked_position` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `currency` int(11) NOT NULL COMMENT '币种',
  `amount` decimal(32,8) NOT NULL COMMENT '原锁仓数量',
  `lock_amount` decimal(32,8) NOT NULL COMMENT '当前锁仓数量',
  `lock_position_name` varchar(32) NOT NULL COMMENT '锁仓类型描述',
  `dividend` int(2) NOT NULL COMMENT '是否参与分红',
  `release_date` datetime NOT NULL,
  `next_release_date` datetime NOT NULL,
  `release_content` varchar(64) DEFAULT NULL COMMENT '解锁计划',
  `status` int(2) NOT NULL COMMENT '状态 0锁定失败 1已锁定',
  `remark` varchar(255) NOT NULL COMMENT '描述',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `broker_id` int(10) DEFAULT '1' COMMENT '券商id',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `currency` (`currency`),
  KEY `release_date` (`release_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='锁仓记录表';

-- ----------------------------
-- Table structure for locked_position_conf
-- ----------------------------
DROP TABLE IF EXISTS `locked_position_conf`;
CREATE TABLE `locked_position_conf` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `lock_position_name` varchar(32) NOT NULL COMMENT '锁仓类型',
  `currency` int(11) NOT NULL COMMENT '币种',
  `amount` decimal(32,8) NOT NULL COMMENT '锁仓数量',
  `dividend` int(2) NOT NULL COMMENT '是否参与分红',
  `remark` varchar(255) NOT NULL COMMENT '描述',
  `release_date` varchar(255) DEFAULT NULL COMMENT '解锁时间',
  `release_type` int(2) NOT NULL COMMENT '解锁类型',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='锁仓配置表';

-- ----------------------------
-- Table structure for notify_user
-- ----------------------------
DROP TABLE IF EXISTS `notify_user`;
CREATE TABLE `notify_user` (
  `id` int(10) unsigned NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  `auth` text NOT NULL,
  `url_path` text NOT NULL,
  `remark` varchar(128) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='充值、提现通知';

-- ----------------------------
-- Table structure for perpetual_asset_currency_compress
-- ----------------------------
DROP TABLE IF EXISTS `perpetual_asset_currency_compress`;
CREATE TABLE `perpetual_asset_currency_compress` (
  `id` int(10) unsigned NOT NULL,
  `symbol` varchar(128) NOT NULL DEFAULT '' COMMENT '币种代号 例:CNY BTC LTC',
  `extend_attrs` text COMMENT '拓展配置',
  `withdrawable` int(8) NOT NULL DEFAULT '1' COMMENT '是否可提现 1:可提现，0:不可提现',
  `rechargeable` int(8) NOT NULL DEFAULT '1' COMMENT '是否可充值 1:可充值，0:不可充值',
  `sort` int(8) NOT NULL DEFAULT '0' COMMENT '排序系数',
  `online` int(8) NOT NULL DEFAULT '1' COMMENT '上线',
  `exchange` int(8) NOT NULL DEFAULT '0' COMMENT '兑换 0:不能兑换 1:可兑换',
  `receive` int(8) NOT NULL DEFAULT '0' COMMENT '领取 0:不能领取 1:可领取',
  `transfer` int(8) NOT NULL DEFAULT '0' COMMENT '划转 0:不能划转 1:可划转',
  `withdraw_fee` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '提现手续费',
  `deposit_confirm` int(11) NOT NULL DEFAULT '0' COMMENT '充值确认数',
  `withdraw_confirm` int(11) NOT NULL DEFAULT '0' COMMENT '提现确认数',
  `min_deposit_amount` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最小充值数目',
  `min_withdraw_amount` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最小提现数目',
  `max_withdraw_amount` decimal(16,8) DEFAULT NULL COMMENT '最大提现数目',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `broker_id` int(10) DEFAULT '1' COMMENT '券商id',
  UNIQUE KEY `sb` (`symbol`,`broker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='币种表';

-- ----------------------------
-- Table structure for portfolio_asset_currency_compress
-- ----------------------------
DROP TABLE IF EXISTS `portfolio_asset_currency_compress`;
CREATE TABLE `portfolio_asset_currency_compress` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '币种id',
  `symbol` varchar(128) NOT NULL DEFAULT '' COMMENT '币种代号 例:CNY BTC LTC',
  `extend_attrs` text COMMENT '拓展配置',
  `withdrawable` int(8) NOT NULL DEFAULT '1' COMMENT '是否可提现 1:可提现，0:不可提现',
  `rechargeable` int(8) NOT NULL DEFAULT '1' COMMENT '是否可充值 1:可充值，0:不可充值',
  `sort` int(8) NOT NULL DEFAULT '0' COMMENT '排序系数',
  `online` int(8) NOT NULL DEFAULT '1' COMMENT '上线',
  `exchange` int(8) NOT NULL DEFAULT '0' COMMENT '兑换 0:不能兑换 1:可兑换',
  `receive` int(8) NOT NULL DEFAULT '0' COMMENT '领取 0:不能领取 1:可领取',
  `transfer` int(8) NOT NULL DEFAULT '0' COMMENT '划转 0:不能划转 1:可划转',
  `withdraw_fee` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '提现手续费',
  `deposit_confirm` int(11) NOT NULL DEFAULT '0' COMMENT '充值确认数',
  `withdraw_confirm` int(11) NOT NULL DEFAULT '0' COMMENT '提现确认数',
  `min_deposit_amount` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最小充值数目',
  `min_withdraw_amount` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最小提现数目',
  `max_withdraw_amount` decimal(16,8) DEFAULT NULL COMMENT '最大提现数目',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `broker_id` int(10) DEFAULT '1' COMMENT '券商id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sb` (`symbol`,`broker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='币种表';

-- ----------------------------
-- Table structure for reconciliation_conf
-- ----------------------------
DROP TABLE IF EXISTS `reconciliation_conf`;
CREATE TABLE `reconciliation_conf` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `currency` int(11) NOT NULL COMMENT '币种',
  `biz_balance` text,
  `total_threshold` decimal(32,8) NOT NULL COMMENT '总余额阈值',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for spot_asset_currency
-- ----------------------------
DROP TABLE IF EXISTS `spot_asset_currency`;
CREATE TABLE `spot_asset_currency` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '币种id',
  `symbol` varchar(128) NOT NULL DEFAULT '' COMMENT '币种代号 例:CNY BTC LTC',
  `sign` varchar(40) NOT NULL DEFAULT '' COMMENT '币种符号',
  `full_name` varchar(128) DEFAULT NULL COMMENT '全称',
  `withdrawable` int(8) NOT NULL DEFAULT '1' COMMENT '是否可提现',
  `rechargeable` int(8) NOT NULL DEFAULT '1' COMMENT '是否可充值 1:可提现，0:不可提现',
  `sort` int(8) NOT NULL DEFAULT '0' COMMENT '排序系数',
  `online` int(8) NOT NULL DEFAULT '1' COMMENT '上线',
  `currency_picture_url` text,
  `exchange` int(8) NOT NULL DEFAULT '0' COMMENT '0:不能兑换 1:可兑换',
  `receive` int(8) NOT NULL DEFAULT '0' COMMENT '0:不能领取 1:可领取',
  `transfer` int(8) NOT NULL DEFAULT '0' COMMENT '0:不能划转 1:可划转',
  `margin_interest_rate` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '杠杆利息配置',
  `withdraw_fee` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '提现手续费',
  `deposit_confirm` int(11) NOT NULL DEFAULT '0' COMMENT '充值确认数',
  `withdraw_confirm` int(11) NOT NULL DEFAULT '0' COMMENT '提现确认数',
  `min_deposit_amount` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最小充值数目',
  `min_withdraw_amount` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最小提现数目',
  `max_withdraw_amount` decimal(16,8) DEFAULT NULL,
  `tx_explorer_url` text NOT NULL,
  `need_tag` int(8) DEFAULT NULL,
  `tag_field` varchar(128) DEFAULT '',
  `currency_summary` text,
  `currency_url` text,
  `threshold` decimal(16,8) DEFAULT '0.00000000' COMMENT '对账报警阈值',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `symbol` (`symbol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='币种表';

-- ----------------------------
-- Table structure for spot_asset_currency_compress
-- ----------------------------
DROP TABLE IF EXISTS `spot_asset_currency_compress`;
CREATE TABLE `spot_asset_currency_compress` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '币种id',
  `symbol` varchar(128) NOT NULL DEFAULT '' COMMENT '币种代号 例:CNY BTC LTC',
  `extend_attrs` text COMMENT '拓展配置',
  `withdrawable` int(8) NOT NULL DEFAULT '1' COMMENT '是否可提现 1:可提现，0:不可提现',
  `rechargeable` int(8) NOT NULL DEFAULT '1' COMMENT '是否可充值 1:可充值，0:不可充值',
  `sort` int(8) NOT NULL DEFAULT '0' COMMENT '排序系数',
  `online` int(8) NOT NULL DEFAULT '1' COMMENT '上线',
  `exchange` int(8) NOT NULL DEFAULT '0' COMMENT '兑换 0:不能兑换 1:可兑换',
  `receive` int(8) NOT NULL DEFAULT '0' COMMENT '领取 0:不能领取 1:可领取',
  `transfer` int(8) NOT NULL DEFAULT '0' COMMENT '划转 0:不能划转 1:可划转',
  `withdraw_fee` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '提现手续费',
  `deposit_confirm` int(11) NOT NULL DEFAULT '0' COMMENT '充值确认数',
  `withdraw_confirm` int(11) NOT NULL DEFAULT '0' COMMENT '提现确认数',
  `min_deposit_amount` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最小充值数目',
  `min_withdraw_amount` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最小提现数目',
  `max_withdraw_amount` decimal(16,8) DEFAULT NULL COMMENT '最大提现数目',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `broker_id` int(10) DEFAULT '1' COMMENT '券商id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sb` (`symbol`,`broker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='币种表';

-- ----------------------------
-- Table structure for transfer_record
-- ----------------------------
DROP TABLE IF EXISTS `transfer_record`;
CREATE TABLE `transfer_record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `from` varchar(128) NOT NULL DEFAULT '',
  `to` varchar(200) NOT NULL DEFAULT '',
  `amount` decimal(32,8) NOT NULL,
  `fee` decimal(32,8) NOT NULL DEFAULT '0.00000000',
  `confirmation` int(11) NOT NULL DEFAULT '0',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `trader_no` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `transfer_type` int(11) NOT NULL,
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:已发送;',
  `remark` varchar(64) DEFAULT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `broker_id` int(10) DEFAULT '1' COMMENT '券商id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `trader_no` (`trader_no`),
  KEY `user_id` (`user_id`),
  KEY `transfer_type` (`transfer_type`),
  KEY `from` (`from`),
  KEY `to` (`to`),
  KEY `status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for transfer_record_audit
-- ----------------------------
DROP TABLE IF EXISTS `transfer_record_audit`;
CREATE TABLE `transfer_record_audit` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `trader_no` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `msg` varchar(128) DEFAULT '' COMMENT '审核消息',
  `status` tinyint(4) DEFAULT '0' COMMENT '0 待审核\n1 审核通过\n2 审核失败',
  `audit_user_id` bigint(20) DEFAULT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `broker_id` int(10) DEFAULT '1' COMMENT '券商id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for unlocked_position
-- ----------------------------
DROP TABLE IF EXISTS `unlocked_position`;
CREATE TABLE `unlocked_position` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `lock_position_id` bigint(20) NOT NULL COMMENT '锁仓记录id',
  `unlock_amount` decimal(32,8) NOT NULL COMMENT '解锁数量',
  `release_date` datetime NOT NULL COMMENT '释放时间',
  `status` int(2) NOT NULL COMMENT '状态 0未执行 1已执行',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `broker_id` int(10) DEFAULT '1' COMMENT '券商id',
  PRIMARY KEY (`id`),
  KEY `lock_position_conf` (`lock_position_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='解锁计划表';

-- ----------------------------
-- Table structure for user_asset_conf
-- ----------------------------
DROP TABLE IF EXISTS `user_asset_conf`;
CREATE TABLE `user_asset_conf` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `withdraw_limit` decimal(32,8) NOT NULL COMMENT '提现限额（等价BTC）',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for withdraw_address
-- ----------------------------
DROP TABLE IF EXISTS `withdraw_address`;
CREATE TABLE `withdraw_address` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `currency` int(11) NOT NULL,
  `biz` int(11) DEFAULT NULL,
  `status` tinyint(4) NOT NULL,
  `remark` varchar(50) DEFAULT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `broker_id` int(10) DEFAULT '1' COMMENT '券商id',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
