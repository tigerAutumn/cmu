/*
 Navicat Premium Data Transfer

 Source Server         : ppl-xxx1
 Source Server Type    : MySQL
 Source Server Version : 50644
 Source Host           : 192.168.102.36:3306
 Source Schema         : wallet

 Target Server Type    : MySQL
 Target Server Version : 50644
 File Encoding         : 65001

 Date: 01/07/2019 14:46:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `account_transaction`;
CREATE TABLE `account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for act_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `act_account_transaction`;
CREATE TABLE `act_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for act_address
-- ----------------------------
DROP TABLE IF EXISTS `act_address`;
CREATE TABLE `act_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for act_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `act_withdraw_record`;
CREATE TABLE `act_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for act_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `act_withdraw_transaction`;
CREATE TABLE `act_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for add_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `add_account_transaction`;
CREATE TABLE `add_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,4) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for add_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `add_withdraw_record`;
CREATE TABLE `add_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,4) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for add_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `add_withdraw_transaction`;
CREATE TABLE `add_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,4) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ae_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `ae_account_transaction`;
CREATE TABLE `ae_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ae_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `ae_withdraw_record`;
CREATE TABLE `ae_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ae_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `ae_withdraw_transaction`;
CREATE TABLE `ae_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for arn_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `arn_account_transaction`;
CREATE TABLE `arn_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for arn_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `arn_withdraw_record`;
CREATE TABLE `arn_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for arn_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `arn_withdraw_transaction`;
CREATE TABLE `arn_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for aua_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `aua_account_transaction`;
CREATE TABLE `aua_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,4) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for aua_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `aua_withdraw_record`;
CREATE TABLE `aua_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,4) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for aua_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `aua_withdraw_transaction`;
CREATE TABLE `aua_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,4) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for baic_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `baic_account_transaction`;
CREATE TABLE `baic_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for baic_address
-- ----------------------------
DROP TABLE IF EXISTS `baic_address`;
CREATE TABLE `baic_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for baic_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `baic_withdraw_record`;
CREATE TABLE `baic_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for baic_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `baic_withdraw_transaction`;
CREATE TABLE `baic_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bat_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `bat_account_transaction`;
CREATE TABLE `bat_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bat_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `bat_withdraw_record`;
CREATE TABLE `bat_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bat_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `bat_withdraw_transaction`;
CREATE TABLE `bat_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bch_address
-- ----------------------------
DROP TABLE IF EXISTS `bch_address`;
CREATE TABLE `bch_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bch_utxo_transaction
-- ----------------------------
DROP TABLE IF EXISTS `bch_utxo_transaction`;
CREATE TABLE `bch_utxo_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `seq` smallint(8) unsigned NOT NULL COMMENT 'output序号',
  `spent` tinyint(2) unsigned NOT NULL COMMENT '是否被花费',
  `status` tinyint(2) DEFAULT '0' COMMENT '-1: 删除；0:提现中;1:签名中;2:已发送; 3:已确认',
  `spent_tx_id` varchar(128) NOT NULL DEFAULT '' COMMENT '花费此输出的txid',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`,`seq`),
  KEY `spent_tx_id` (`spent_tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bch_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `bch_withdraw_record`;
CREATE TABLE `bch_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bch_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `bch_withdraw_transaction`;
CREATE TABLE `bch_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for best_block_height
-- ----------------------------
DROP TABLE IF EXISTS `best_block_height`;
CREATE TABLE `best_block_height` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `currency` int(11) unsigned NOT NULL,
  `height` bigint(20) unsigned NOT NULL,
  `interval_time` bigint(20) NOT NULL DEFAULT '300000' COMMENT '区块更新间隔时间，默认5分钟',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `currency` (`currency`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bkbt_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `bkbt_account_transaction`;
CREATE TABLE `bkbt_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bkbt_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `bkbt_withdraw_record`;
CREATE TABLE `bkbt_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bkbt_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `bkbt_withdraw_transaction`;
CREATE TABLE `bkbt_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bmb_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `bmb_account_transaction`;
CREATE TABLE `bmb_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bmb_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `bmb_withdraw_record`;
CREATE TABLE `bmb_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bmb_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `bmb_withdraw_transaction`;
CREATE TABLE `bmb_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bosc_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `bosc_account_transaction`;
CREATE TABLE `bosc_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bosc_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `bosc_withdraw_record`;
CREATE TABLE `bosc_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bosc_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `bosc_withdraw_transaction`;
CREATE TABLE `bosc_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bsv_address
-- ----------------------------
DROP TABLE IF EXISTS `bsv_address`;
CREATE TABLE `bsv_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bsv_utxo_transaction
-- ----------------------------
DROP TABLE IF EXISTS `bsv_utxo_transaction`;
CREATE TABLE `bsv_utxo_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `seq` smallint(8) unsigned NOT NULL COMMENT 'output序号',
  `spent` tinyint(2) unsigned NOT NULL COMMENT '是否被花费',
  `status` tinyint(2) DEFAULT '0' COMMENT '-1: 删除；0:提现中;1:签名中;2:已发送; 3:已确认',
  `spent_tx_id` varchar(128) NOT NULL DEFAULT '' COMMENT '花费此输出的txid',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`,`seq`),
  KEY `spent_tx_id` (`spent_tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bsv_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `bsv_withdraw_record`;
CREATE TABLE `bsv_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bsv_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `bsv_withdraw_transaction`;
CREATE TABLE `bsv_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for btc_address
-- ----------------------------
DROP TABLE IF EXISTS `btc_address`;
CREATE TABLE `btc_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for btc_utxo_transaction
-- ----------------------------
DROP TABLE IF EXISTS `btc_utxo_transaction`;
CREATE TABLE `btc_utxo_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `seq` smallint(8) unsigned NOT NULL COMMENT 'output序号',
  `spent` tinyint(2) unsigned NOT NULL COMMENT '是否被花费',
  `status` tinyint(2) DEFAULT '0' COMMENT '-1: 删除；0:提现中;1:签名中;2:已发送; 3:已确认',
  `spent_tx_id` varchar(128) NOT NULL DEFAULT '' COMMENT '花费此输出的txid',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`,`seq`),
  KEY `spent_tx_id` (`spent_tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for btc_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `btc_withdraw_record`;
CREATE TABLE `btc_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for btc_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `btc_withdraw_transaction`;
CREATE TABLE `btc_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for btl_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `btl_account_transaction`;
CREATE TABLE `btl_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for btl_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `btl_withdraw_record`;
CREATE TABLE `btl_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for btl_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `btl_withdraw_transaction`;
CREATE TABLE `btl_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for btm_address
-- ----------------------------
DROP TABLE IF EXISTS `btm_address`;
CREATE TABLE `btm_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for btm_utxo_transaction
-- ----------------------------
DROP TABLE IF EXISTS `btm_utxo_transaction`;
CREATE TABLE `btm_utxo_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(192) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `seq` smallint(8) unsigned NOT NULL COMMENT 'output序号',
  `spent` tinyint(2) unsigned NOT NULL COMMENT '是否被花费',
  `status` tinyint(2) DEFAULT '0' COMMENT '-1: 删除；0:提现中;1:签名中;2:已发送; 3:已确认',
  `spent_tx_id` varchar(128) NOT NULL DEFAULT '' COMMENT '花费此输出的txid',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`,`seq`),
  KEY `spent_tx_id` (`spent_tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for btm_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `btm_withdraw_record`;
CREATE TABLE `btm_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for btm_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `btm_withdraw_transaction`;
CREATE TABLE `btm_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cel_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `cel_account_transaction`;
CREATE TABLE `cel_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cel_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `cel_withdraw_record`;
CREATE TABLE `cel_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cel_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `cel_withdraw_transaction`;
CREATE TABLE `cel_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for chp_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `chp_account_transaction`;
CREATE TABLE `chp_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for chp_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `chp_withdraw_record`;
CREATE TABLE `chp_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for chp_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `chp_withdraw_transaction`;
CREATE TABLE `chp_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cpg_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `cpg_account_transaction`;
CREATE TABLE `cpg_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cpg_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `cpg_withdraw_record`;
CREATE TABLE `cpg_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cpg_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `cpg_withdraw_transaction`;
CREATE TABLE `cpg_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ct_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `ct_account_transaction`;
CREATE TABLE `ct_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ct_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `ct_withdraw_record`;
CREATE TABLE `ct_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ct_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `ct_withdraw_transaction`;
CREATE TABLE `ct_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ctb_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `ctb_account_transaction`;
CREATE TABLE `ctb_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ctb_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `ctb_withdraw_record`;
CREATE TABLE `ctb_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ctb_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `ctb_withdraw_transaction`;
CREATE TABLE `ctb_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for currency_balance
-- ----------------------------
DROP TABLE IF EXISTS `currency_balance`;
CREATE TABLE `currency_balance` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `currency_index` int(11) NOT NULL,
  `balance` decimal(32,9) NOT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `currency_index` (`currency_index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dash_address
-- ----------------------------
DROP TABLE IF EXISTS `dash_address`;
CREATE TABLE `dash_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dash_utxo_transaction
-- ----------------------------
DROP TABLE IF EXISTS `dash_utxo_transaction`;
CREATE TABLE `dash_utxo_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `seq` smallint(8) unsigned NOT NULL COMMENT 'output序号',
  `spent` tinyint(2) unsigned NOT NULL COMMENT '是否被花费',
  `status` tinyint(2) DEFAULT '0' COMMENT '-1: 删除；0:提现中;1:签名中;2:已发送; 3:已确认',
  `spent_tx_id` varchar(128) NOT NULL DEFAULT '' COMMENT '花费此输出的txid',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`,`seq`),
  KEY `spent_tx_id` (`spent_tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dash_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `dash_withdraw_record`;
CREATE TABLE `dash_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dash_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `dash_withdraw_transaction`;
CREATE TABLE `dash_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ddd_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `ddd_account_transaction`;
CREATE TABLE `ddd_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ddd_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `ddd_withdraw_record`;
CREATE TABLE `ddd_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ddd_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `ddd_withdraw_transaction`;
CREATE TABLE `ddd_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for doge_address
-- ----------------------------
DROP TABLE IF EXISTS `doge_address`;
CREATE TABLE `doge_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for doge_utxo_transaction
-- ----------------------------
DROP TABLE IF EXISTS `doge_utxo_transaction`;
CREATE TABLE `doge_utxo_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(192) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `seq` smallint(8) unsigned NOT NULL COMMENT 'output序号',
  `spent` tinyint(2) unsigned NOT NULL COMMENT '是否被花费',
  `status` tinyint(2) DEFAULT '0' COMMENT '-1: 删除；0:提现中;1:签名中;2:已发送; 3:已确认',
  `spent_tx_id` varchar(128) NOT NULL DEFAULT '' COMMENT '花费此输出的txid',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`,`seq`),
  KEY `spent_tx_id` (`spent_tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for doge_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `doge_withdraw_record`;
CREATE TABLE `doge_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(32,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for doge_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `doge_withdraw_transaction`;
CREATE TABLE `doge_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL,
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dpy_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `dpy_account_transaction`;
CREATE TABLE `dpy_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dpy_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `dpy_withdraw_record`;
CREATE TABLE `dpy_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dpy_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `dpy_withdraw_transaction`;
CREATE TABLE `dpy_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dusd_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `dusd_account_transaction`;
CREATE TABLE `dusd_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dusd_address
-- ----------------------------
DROP TABLE IF EXISTS `dusd_address`;
CREATE TABLE `dusd_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dusd_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `dusd_withdraw_record`;
CREATE TABLE `dusd_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dusd_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `dusd_withdraw_transaction`;
CREATE TABLE `dusd_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for eeth_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `eeth_account_transaction`;
CREATE TABLE `eeth_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,4) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for eeth_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `eeth_withdraw_record`;
CREATE TABLE `eeth_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,4) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for eeth_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `eeth_withdraw_transaction`;
CREATE TABLE `eeth_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,4) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for elet_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `elet_account_transaction`;
CREATE TABLE `elet_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for elet_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `elet_withdraw_record`;
CREATE TABLE `elet_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for elet_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `elet_withdraw_transaction`;
CREATE TABLE `elet_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for eos_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `eos_account_transaction`;
CREATE TABLE `eos_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,4) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for eos_address
-- ----------------------------
DROP TABLE IF EXISTS `eos_address`;
CREATE TABLE `eos_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,4) DEFAULT '0.0000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for eos_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `eos_withdraw_record`;
CREATE TABLE `eos_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,4) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for eos_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `eos_withdraw_transaction`;
CREATE TABLE `eos_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,4) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for eosc_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `eosc_account_transaction`;
CREATE TABLE `eosc_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,4) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for eosc_address
-- ----------------------------
DROP TABLE IF EXISTS `eosc_address`;
CREATE TABLE `eosc_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,4) DEFAULT '0.0000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for eosc_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `eosc_withdraw_record`;
CREATE TABLE `eosc_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,4) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for eosc_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `eosc_withdraw_transaction`;
CREATE TABLE `eosc_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,4) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for eosdac_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `eosdac_account_transaction`;
CREATE TABLE `eosdac_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,4) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for eosdac_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `eosdac_withdraw_record`;
CREATE TABLE `eosdac_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,4) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for eosdac_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `eosdac_withdraw_transaction`;
CREATE TABLE `eosdac_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,4) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for etc_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `etc_account_transaction`;
CREATE TABLE `etc_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for etc_address
-- ----------------------------
DROP TABLE IF EXISTS `etc_address`;
CREATE TABLE `etc_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for etc_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `etc_withdraw_record`;
CREATE TABLE `etc_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for etc_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `etc_withdraw_transaction`;
CREATE TABLE `etc_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for eth_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `eth_account_transaction`;
CREATE TABLE `eth_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for eth_address
-- ----------------------------
DROP TABLE IF EXISTS `eth_address`;
CREATE TABLE `eth_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for eth_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `eth_withdraw_record`;
CREATE TABLE `eth_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for eth_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `eth_withdraw_transaction`;
CREATE TABLE `eth_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for fc_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `fc_account_transaction`;
CREATE TABLE `fc_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for fc_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `fc_withdraw_record`;
CREATE TABLE `fc_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for fc_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `fc_withdraw_transaction`;
CREATE TABLE `fc_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
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
-- Table structure for fti_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `fti_account_transaction`;
CREATE TABLE `fti_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for fti_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `fti_withdraw_record`;
CREATE TABLE `fti_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for fti_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `fti_withdraw_transaction`;
CREATE TABLE `fti_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gard_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `gard_account_transaction`;
CREATE TABLE `gard_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gard_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `gard_withdraw_record`;
CREATE TABLE `gard_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gard_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `gard_withdraw_transaction`;
CREATE TABLE `gard_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gas_address
-- ----------------------------
DROP TABLE IF EXISTS `gas_address`;
CREATE TABLE `gas_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gas_utxo_transaction
-- ----------------------------
DROP TABLE IF EXISTS `gas_utxo_transaction`;
CREATE TABLE `gas_utxo_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `seq` smallint(8) unsigned NOT NULL COMMENT 'output序号',
  `spent` tinyint(2) unsigned NOT NULL COMMENT '是否被花费',
  `status` tinyint(2) DEFAULT '0' COMMENT '-1: 删除；0:提现中;1:签名中;2:已发送; 3:已确认',
  `spent_tx_id` varchar(128) NOT NULL DEFAULT '' COMMENT '花费此输出的txid',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`,`seq`),
  KEY `spent_tx_id` (`spent_tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gas_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `gas_withdraw_record`;
CREATE TABLE `gas_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gas_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `gas_withdraw_transaction`;
CREATE TABLE `gas_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for get_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `get_account_transaction`;
CREATE TABLE `get_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for get_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `get_withdraw_record`;
CREATE TABLE `get_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for get_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `get_withdraw_transaction`;
CREATE TABLE `get_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gnt_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `gnt_account_transaction`;
CREATE TABLE `gnt_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gnt_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `gnt_withdraw_record`;
CREATE TABLE `gnt_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gnt_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `gnt_withdraw_transaction`;
CREATE TABLE `gnt_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gnx_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `gnx_account_transaction`;
CREATE TABLE `gnx_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gnx_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `gnx_withdraw_record`;
CREATE TABLE `gnx_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gnx_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `gnx_withdraw_transaction`;
CREATE TABLE `gnx_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for god_address
-- ----------------------------
DROP TABLE IF EXISTS `god_address`;
CREATE TABLE `god_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for god_utxo_transaction
-- ----------------------------
DROP TABLE IF EXISTS `god_utxo_transaction`;
CREATE TABLE `god_utxo_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `seq` smallint(8) unsigned NOT NULL COMMENT 'output序号',
  `spent` tinyint(2) unsigned NOT NULL COMMENT '是否被花费',
  `status` tinyint(2) DEFAULT '0' COMMENT '-1: 删除；0:提现中;1:签名中;2:已发送; 3:已确认',
  `spent_tx_id` varchar(128) NOT NULL DEFAULT '' COMMENT '花费此输出的txid',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`,`seq`),
  KEY `spent_tx_id` (`spent_tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for god_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `god_withdraw_record`;
CREATE TABLE `god_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for god_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `god_withdraw_transaction`;
CREATE TABLE `god_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gusd_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `gusd_account_transaction`;
CREATE TABLE `gusd_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gusd_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `gusd_withdraw_record`;
CREATE TABLE `gusd_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gusd_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `gusd_withdraw_transaction`;
CREATE TABLE `gusd_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for hur_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `hur_account_transaction`;
CREATE TABLE `hur_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for hur_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `hur_withdraw_record`;
CREATE TABLE `hur_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for hur_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `hur_withdraw_transaction`;
CREATE TABLE `hur_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for iota_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `iota_account_transaction`;
CREATE TABLE `iota_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for iota_address
-- ----------------------------
DROP TABLE IF EXISTS `iota_address`;
CREATE TABLE `iota_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for iota_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `iota_withdraw_record`;
CREATE TABLE `iota_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for iota_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `iota_withdraw_transaction`;
CREATE TABLE `iota_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` longtext,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for kbcc_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `kbcc_account_transaction`;
CREATE TABLE `kbcc_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for kbcc_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `kbcc_withdraw_record`;
CREATE TABLE `kbcc_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for kbcc_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `kbcc_withdraw_transaction`;
CREATE TABLE `kbcc_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for kcash_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `kcash_account_transaction`;
CREATE TABLE `kcash_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for kcash_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `kcash_withdraw_record`;
CREATE TABLE `kcash_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for kcash_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `kcash_withdraw_transaction`;
CREATE TABLE `kcash_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lad_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `lad_account_transaction`;
CREATE TABLE `lad_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lad_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `lad_withdraw_record`;
CREATE TABLE `lad_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lad_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `lad_withdraw_transaction`;
CREATE TABLE `lad_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for let_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `let_account_transaction`;
CREATE TABLE `let_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for let_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `let_withdraw_record`;
CREATE TABLE `let_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for let_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `let_withdraw_transaction`;
CREATE TABLE `let_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lga_address
-- ----------------------------
DROP TABLE IF EXISTS `lga_address`;
CREATE TABLE `lga_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lga_utxo_transaction
-- ----------------------------
DROP TABLE IF EXISTS `lga_utxo_transaction`;
CREATE TABLE `lga_utxo_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `seq` smallint(8) unsigned NOT NULL COMMENT 'output序号',
  `spent` tinyint(2) unsigned NOT NULL COMMENT '是否被花费',
  `status` tinyint(2) DEFAULT '0' COMMENT '-1: 删除；0:提现中;1:签名中;2:已发送; 3:已确认',
  `spent_tx_id` varchar(128) NOT NULL DEFAULT '' COMMENT '花费此输出的txid',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`,`seq`),
  KEY `spent_tx_id` (`spent_tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lga_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `lga_withdraw_record`;
CREATE TABLE `lga_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lga_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `lga_withdraw_transaction`;
CREATE TABLE `lga_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lrc_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `lrc_account_transaction`;
CREATE TABLE `lrc_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lrc_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `lrc_withdraw_record`;
CREATE TABLE `lrc_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lrc_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `lrc_withdraw_transaction`;
CREATE TABLE `lrc_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lrn_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `lrn_account_transaction`;
CREATE TABLE `lrn_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lrn_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `lrn_withdraw_record`;
CREATE TABLE `lrn_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lrn_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `lrn_withdraw_transaction`;
CREATE TABLE `lrn_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lrt_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `lrt_account_transaction`;
CREATE TABLE `lrt_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lrt_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `lrt_withdraw_record`;
CREATE TABLE `lrt_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lrt_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `lrt_withdraw_transaction`;
CREATE TABLE `lrt_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ltc_address
-- ----------------------------
DROP TABLE IF EXISTS `ltc_address`;
CREATE TABLE `ltc_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ltc_utxo_transaction
-- ----------------------------
DROP TABLE IF EXISTS `ltc_utxo_transaction`;
CREATE TABLE `ltc_utxo_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `seq` smallint(8) unsigned NOT NULL COMMENT 'output序号',
  `spent` tinyint(2) unsigned NOT NULL COMMENT '是否被花费',
  `status` tinyint(2) DEFAULT '0' COMMENT '-1: 删除；0:提现中;1:签名中;2:已发送; 3:已确认',
  `spent_tx_id` varchar(128) NOT NULL DEFAULT '' COMMENT '花费此输出的txid',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`,`seq`),
  KEY `spent_tx_id` (`spent_tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ltc_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `ltc_withdraw_record`;
CREATE TABLE `ltc_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ltc_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `ltc_withdraw_transaction`;
CREATE TABLE `ltc_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for luckywin_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `luckywin_account_transaction`;
CREATE TABLE `luckywin_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for luckywin_address
-- ----------------------------
DROP TABLE IF EXISTS `luckywin_address`;
CREATE TABLE `luckywin_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for luckywin_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `luckywin_withdraw_record`;
CREATE TABLE `luckywin_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(32,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for luckywin_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `luckywin_withdraw_transaction`;
CREATE TABLE `luckywin_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL,
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lym_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `lym_account_transaction`;
CREATE TABLE `lym_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lym_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `lym_withdraw_record`;
CREATE TABLE `lym_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lym_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `lym_withdraw_transaction`;
CREATE TABLE `lym_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mds_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `mds_account_transaction`;
CREATE TABLE `mds_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mds_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `mds_withdraw_record`;
CREATE TABLE `mds_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mds_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `mds_withdraw_transaction`;
CREATE TABLE `mds_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for meetone_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `meetone_account_transaction`;
CREATE TABLE `meetone_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,4) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for meetone_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `meetone_withdraw_record`;
CREATE TABLE `meetone_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,4) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for meetone_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `meetone_withdraw_transaction`;
CREATE TABLE `meetone_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,4) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mkr_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `mkr_account_transaction`;
CREATE TABLE `mkr_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mkr_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `mkr_withdraw_record`;
CREATE TABLE `mkr_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mkr_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `mkr_withdraw_transaction`;
CREATE TABLE `mkr_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mrs_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `mrs_account_transaction`;
CREATE TABLE `mrs_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mrs_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `mrs_withdraw_record`;
CREATE TABLE `mrs_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mrs_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `mrs_withdraw_transaction`;
CREATE TABLE `mrs_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mtx_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `mtx_account_transaction`;
CREATE TABLE `mtx_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mtx_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `mtx_withdraw_record`;
CREATE TABLE `mtx_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mtx_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `mtx_withdraw_transaction`;
CREATE TABLE `mtx_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mvp_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `mvp_account_transaction`;
CREATE TABLE `mvp_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mvp_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `mvp_withdraw_record`;
CREATE TABLE `mvp_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mvp_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `mvp_withdraw_transaction`;
CREATE TABLE `mvp_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for neo_address
-- ----------------------------
DROP TABLE IF EXISTS `neo_address`;
CREATE TABLE `neo_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for neo_utxo_transaction
-- ----------------------------
DROP TABLE IF EXISTS `neo_utxo_transaction`;
CREATE TABLE `neo_utxo_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `seq` smallint(8) unsigned NOT NULL COMMENT 'output序号',
  `spent` tinyint(2) unsigned NOT NULL COMMENT '是否被花费',
  `status` tinyint(2) DEFAULT '0' COMMENT '-1: 删除；0:提现中;1:签名中;2:已发送; 3:已确认',
  `spent_tx_id` varchar(128) NOT NULL DEFAULT '' COMMENT '花费此输出的txid',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`,`seq`),
  KEY `spent_tx_id` (`spent_tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for neo_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `neo_withdraw_record`;
CREATE TABLE `neo_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for neo_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `neo_withdraw_transaction`;
CREATE TABLE `neo_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for of_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `of_account_transaction`;
CREATE TABLE `of_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for of_address
-- ----------------------------
DROP TABLE IF EXISTS `of_address`;
CREATE TABLE `of_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for of_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `of_withdraw_record`;
CREATE TABLE `of_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for of_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `of_withdraw_transaction`;
CREATE TABLE `of_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for olt_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `olt_account_transaction`;
CREATE TABLE `olt_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for olt_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `olt_withdraw_record`;
CREATE TABLE `olt_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for olt_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `olt_withdraw_transaction`;
CREATE TABLE `olt_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for omg_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `omg_account_transaction`;
CREATE TABLE `omg_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for omg_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `omg_withdraw_record`;
CREATE TABLE `omg_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for omg_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `omg_withdraw_transaction`;
CREATE TABLE `omg_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ong_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `ong_account_transaction`;
CREATE TABLE `ong_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,9) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ong_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `ong_withdraw_record`;
CREATE TABLE `ong_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,9) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ong_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `ong_withdraw_transaction`;
CREATE TABLE `ong_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,9) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ont_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `ont_account_transaction`;
CREATE TABLE `ont_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ont_address
-- ----------------------------
DROP TABLE IF EXISTS `ont_address`;
CREATE TABLE `ont_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ont_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `ont_withdraw_record`;
CREATE TABLE `ont_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ont_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `ont_withdraw_transaction`;
CREATE TABLE `ont_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pag_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `pag_account_transaction`;
CREATE TABLE `pag_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pag_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `pag_withdraw_record`;
CREATE TABLE `pag_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pag_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `pag_withdraw_transaction`;
CREATE TABLE `pag_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for panda_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `panda_account_transaction`;
CREATE TABLE `panda_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for panda_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `panda_withdraw_record`;
CREATE TABLE `panda_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for panda_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `panda_withdraw_transaction`;
CREATE TABLE `panda_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pax_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `pax_account_transaction`;
CREATE TABLE `pax_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pax_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `pax_withdraw_record`;
CREATE TABLE `pax_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pax_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `pax_withdraw_transaction`;
CREATE TABLE `pax_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ppt_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `ppt_account_transaction`;
CREATE TABLE `ppt_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ppt_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `ppt_withdraw_record`;
CREATE TABLE `ppt_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ppt_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `ppt_withdraw_transaction`;
CREATE TABLE `ppt_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pst_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `pst_account_transaction`;
CREATE TABLE `pst_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pst_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `pst_withdraw_record`;
CREATE TABLE `pst_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pst_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `pst_withdraw_transaction`;
CREATE TABLE `pst_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pvb_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `pvb_account_transaction`;
CREATE TABLE `pvb_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pvb_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `pvb_withdraw_record`;
CREATE TABLE `pvb_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pvb_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `pvb_withdraw_transaction`;
CREATE TABLE `pvb_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qbt_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `qbt_account_transaction`;
CREATE TABLE `qbt_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qbt_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `qbt_withdraw_record`;
CREATE TABLE `qbt_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qbt_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `qbt_withdraw_transaction`;
CREATE TABLE `qbt_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qtb_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `qtb_account_transaction`;
CREATE TABLE `qtb_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qtb_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `qtb_withdraw_record`;
CREATE TABLE `qtb_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qtb_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `qtb_withdraw_transaction`;
CREATE TABLE `qtb_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rbtc_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `rbtc_account_transaction`;
CREATE TABLE `rbtc_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rbtc_address
-- ----------------------------
DROP TABLE IF EXISTS `rbtc_address`;
CREATE TABLE `rbtc_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rbtc_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `rbtc_withdraw_record`;
CREATE TABLE `rbtc_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rbtc_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `rbtc_withdraw_transaction`;
CREATE TABLE `rbtc_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rc_address
-- ----------------------------
DROP TABLE IF EXISTS `rc_address`;
CREATE TABLE `rc_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rc_utxo_transaction
-- ----------------------------
DROP TABLE IF EXISTS `rc_utxo_transaction`;
CREATE TABLE `rc_utxo_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `seq` smallint(8) unsigned NOT NULL COMMENT 'output序号',
  `spent` tinyint(2) unsigned NOT NULL COMMENT '是否被花费',
  `status` tinyint(2) DEFAULT '0' COMMENT '-1: 删除；0:提现中;1:签名中;2:已发送; 3:已确认',
  `spent_tx_id` varchar(128) NOT NULL DEFAULT '' COMMENT '花费此输出的txid',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`,`seq`),
  KEY `spent_tx_id` (`spent_tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rc_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `rc_withdraw_record`;
CREATE TABLE `rc_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rc_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `rc_withdraw_transaction`;
CREATE TABLE `rc_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` mediumtext COMMENT 'signature',
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rep_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `rep_account_transaction`;
CREATE TABLE `rep_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rep_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `rep_withdraw_record`;
CREATE TABLE `rep_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rep_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `rep_withdraw_transaction`;
CREATE TABLE `rep_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rif_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `rif_account_transaction`;
CREATE TABLE `rif_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rif_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `rif_withdraw_record`;
CREATE TABLE `rif_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rif_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `rif_withdraw_transaction`;
CREATE TABLE `rif_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rrc_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `rrc_account_transaction`;
CREATE TABLE `rrc_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rrc_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `rrc_withdraw_record`;
CREATE TABLE `rrc_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rrc_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `rrc_withdraw_transaction`;
CREATE TABLE `rrc_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sds_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `sds_account_transaction`;
CREATE TABLE `sds_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sds_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `sds_withdraw_record`;
CREATE TABLE `sds_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sds_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `sds_withdraw_transaction`;
CREATE TABLE `sds_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sdusd_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `sdusd_account_transaction`;
CREATE TABLE `sdusd_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sdusd_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `sdusd_withdraw_record`;
CREATE TABLE `sdusd_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sdusd_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `sdusd_withdraw_transaction`;
CREATE TABLE `sdusd_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for snt_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `snt_account_transaction`;
CREATE TABLE `snt_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for snt_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `snt_withdraw_record`;
CREATE TABLE `snt_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for snt_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `snt_withdraw_transaction`;
CREATE TABLE `snt_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ssc_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `ssc_account_transaction`;
CREATE TABLE `ssc_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ssc_address
-- ----------------------------
DROP TABLE IF EXISTS `ssc_address`;
CREATE TABLE `ssc_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ssc_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `ssc_withdraw_record`;
CREATE TABLE `ssc_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ssc_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `ssc_withdraw_transaction`;
CREATE TABLE `ssc_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for swag_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `swag_account_transaction`;
CREATE TABLE `swag_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for swag_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `swag_withdraw_record`;
CREATE TABLE `swag_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for swag_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `swag_withdraw_transaction`;
CREATE TABLE `swag_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for trx_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `trx_account_transaction`;
CREATE TABLE `trx_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,6) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for trx_address
-- ----------------------------
DROP TABLE IF EXISTS `trx_address`;
CREATE TABLE `trx_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,6) DEFAULT '0.000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for trx_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `trx_withdraw_record`;
CREATE TABLE `trx_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,6) NOT NULL,
  `fee` decimal(16,6) DEFAULT '0.000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for trx_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `trx_withdraw_transaction`;
CREATE TABLE `trx_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,6) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tusd_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `tusd_account_transaction`;
CREATE TABLE `tusd_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tusd_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `tusd_withdraw_record`;
CREATE TABLE `tusd_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tusd_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `tusd_withdraw_transaction`;
CREATE TABLE `tusd_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for usdc_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `usdc_account_transaction`;
CREATE TABLE `usdc_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for usdc_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `usdc_withdraw_record`;
CREATE TABLE `usdc_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for usdc_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `usdc_withdraw_transaction`;
CREATE TABLE `usdc_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for usdt_address
-- ----------------------------
DROP TABLE IF EXISTS `usdt_address`;
CREATE TABLE `usdt_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for usdt_omni_transaction
-- ----------------------------
DROP TABLE IF EXISTS `usdt_omni_transaction`;
CREATE TABLE `usdt_omni_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `omni_balance` decimal(32,8) unsigned DEFAULT '0.00000000' COMMENT 'omni数值',
  `balance` decimal(32,8) unsigned DEFAULT '0.00000000' COMMENT 'btc数值',
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `seq` smallint(8) unsigned NOT NULL COMMENT 'output序号',
  `spent` tinyint(2) unsigned NOT NULL COMMENT '是否被花费',
  `status` tinyint(2) DEFAULT '0' COMMENT '-1: 删除；0:提现中;1:签名中;2:已发送; 3:已确认',
  `spent_tx_id` varchar(128) NOT NULL DEFAULT '' COMMENT '花费此输出的txid',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`,`seq`),
  KEY `spent_tx_id` (`spent_tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for usdt_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `usdt_withdraw_record`;
CREATE TABLE `usdt_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for usdt_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `usdt_withdraw_transaction`;
CREATE TABLE `usdt_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for vollar_address
-- ----------------------------
DROP TABLE IF EXISTS `vollar_address`;
CREATE TABLE `vollar_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for vollar_utxo_transaction
-- ----------------------------
DROP TABLE IF EXISTS `vollar_utxo_transaction`;
CREATE TABLE `vollar_utxo_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `seq` smallint(8) unsigned NOT NULL COMMENT 'output序号',
  `spent` tinyint(2) unsigned NOT NULL COMMENT '是否被花费',
  `status` tinyint(2) DEFAULT '0' COMMENT '-1: 删除；0:提现中;1:签名中;2:已发送; 3:已确认',
  `spent_tx_id` varchar(128) NOT NULL DEFAULT '' COMMENT '花费此输出的txid',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`,`seq`),
  KEY `spent_tx_id` (`spent_tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for vollar_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `vollar_withdraw_record`;
CREATE TABLE `vollar_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for vollar_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `vollar_withdraw_transaction`;
CREATE TABLE `vollar_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wbbt_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `wbbt_account_transaction`;
CREATE TABLE `wbbt_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wbbt_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `wbbt_withdraw_record`;
CREATE TABLE `wbbt_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wbbt_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `wbbt_withdraw_transaction`;
CREATE TABLE `wbbt_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for win_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `win_account_transaction`;
CREATE TABLE `win_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for win_address
-- ----------------------------
DROP TABLE IF EXISTS `win_address`;
CREATE TABLE `win_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for win_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `win_withdraw_record`;
CREATE TABLE `win_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for win_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `win_withdraw_transaction`;
CREATE TABLE `win_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wtc_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `wtc_account_transaction`;
CREATE TABLE `wtc_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wtc_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `wtc_withdraw_record`;
CREATE TABLE `wtc_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wtc_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `wtc_withdraw_transaction`;
CREATE TABLE `wtc_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for xem_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `xem_account_transaction`;
CREATE TABLE `xem_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for xem_address
-- ----------------------------
DROP TABLE IF EXISTS `xem_address`;
CREATE TABLE `xem_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for xem_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `xem_withdraw_record`;
CREATE TABLE `xem_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for xem_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `xem_withdraw_transaction`;
CREATE TABLE `xem_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for xlm_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `xlm_account_transaction`;
CREATE TABLE `xlm_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for xlm_address
-- ----------------------------
DROP TABLE IF EXISTS `xlm_address`;
CREATE TABLE `xlm_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for xlm_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `xlm_withdraw_record`;
CREATE TABLE `xlm_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for xlm_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `xlm_withdraw_transaction`;
CREATE TABLE `xlm_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for xmr_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `xmr_account_transaction`;
CREATE TABLE `xmr_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(251) NOT NULL,
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for xmr_address
-- ----------------------------
DROP TABLE IF EXISTS `xmr_address`;
CREATE TABLE `xmr_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(251) NOT NULL,
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for xmr_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `xmr_withdraw_record`;
CREATE TABLE `xmr_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(251) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for xmr_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `xmr_withdraw_transaction`;
CREATE TABLE `xmr_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for xrp_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `xrp_account_transaction`;
CREATE TABLE `xrp_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for xrp_address
-- ----------------------------
DROP TABLE IF EXISTS `xrp_address`;
CREATE TABLE `xrp_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for xrp_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `xrp_withdraw_record`;
CREATE TABLE `xrp_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for xrp_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `xrp_withdraw_transaction`;
CREATE TABLE `xrp_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for you_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `you_account_transaction`;
CREATE TABLE `you_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for you_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `you_withdraw_record`;
CREATE TABLE `you_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for you_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `you_withdraw_transaction`;
CREATE TABLE `you_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zec_address
-- ----------------------------
DROP TABLE IF EXISTS `zec_address`;
CREATE TABLE `zec_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) DEFAULT '0.00000000',
  `biz` int(6) NOT NULL COMMENT '业务类型',
  `nonce` int(11) DEFAULT '0' COMMENT '账户类型的币发送交易时需要nounce',
  `index` int(11) NOT NULL COMMENT 'userId生成的第几个地址',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zec_utxo_transaction
-- ----------------------------
DROP TABLE IF EXISTS `zec_utxo_transaction`;
CREATE TABLE `zec_utxo_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `seq` smallint(8) unsigned NOT NULL COMMENT 'output序号',
  `spent` tinyint(2) unsigned NOT NULL COMMENT '是否被花费',
  `status` tinyint(2) DEFAULT '0' COMMENT '-1: 删除；0:提现中;1:签名中;2:已发送; 3:已确认',
  `spent_tx_id` varchar(128) NOT NULL DEFAULT '' COMMENT '花费此输出的txid',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`,`seq`),
  KEY `spent_tx_id` (`spent_tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zec_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `zec_withdraw_record`;
CREATE TABLE `zec_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zec_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `zec_withdraw_transaction`;
CREATE TABLE `zec_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 08:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zrx_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `zrx_account_transaction`;
CREATE TABLE `zrx_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zrx_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `zrx_withdraw_record`;
CREATE TABLE `zrx_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zrx_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `zrx_withdraw_transaction`;
CREATE TABLE `zrx_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zxt_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `zxt_account_transaction`;
CREATE TABLE `zxt_account_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `block_height` bigint(20) unsigned NOT NULL,
  `address` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) unsigned NOT NULL,
  `confirm_num` bigint(11) unsigned NOT NULL COMMENT '确认数',
  `biz` int(11) NOT NULL COMMENT '业务类型',
  `status` tinyint(2) DEFAULT '0',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`tx_id`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zxt_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `zxt_withdraw_record`;
CREATE TABLE `zxt_withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT 'waiting',
  `address` varchar(128) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(32,8) NOT NULL,
  `fee` decimal(16,8) DEFAULT '0.00000000',
  `currency` int(11) NOT NULL,
  `biz` int(11) NOT NULL,
  `withdraw_id` varchar(128) NOT NULL DEFAULT '' COMMENT '系统间交互的唯一标识，防止发送重复交易',
  `status` tinyint(4) DEFAULT '0' COMMENT '0:提现中;1:签名中;2:已发送; 3:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `withdraw_id` (`withdraw_id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zxt_withdraw_transaction
-- ----------------------------
DROP TABLE IF EXISTS `zxt_withdraw_transaction`;
CREATE TABLE `zxt_withdraw_transaction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tx_id` varchar(128) NOT NULL DEFAULT '',
  `balance` decimal(32,8) NOT NULL COMMENT '此笔交易的金额',
  `signature` text,
  `currency` int(11) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '0:正在签名;1:已发送;2:已确认',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '1970-08-18 16:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `tx_id` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
