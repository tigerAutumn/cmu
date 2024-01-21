/*
 Navicat Premium Data Transfer

 Source Server         : ppl-xxx1
 Source Server Type    : MySQL
 Source Server Version : 50644
 Source Host           : 192.168.102.36:3306
 Source Schema         : perpetual

 Target Server Type    : MySQL
 Target Server Version : 50644
 File Encoding         : 65001

 Date: 01/07/2019 14:45:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for latest_ticker
-- ----------------------------
DROP TABLE IF EXISTS `latest_ticker`;
CREATE TABLE `latest_ticker` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `contract_code` varchar(255) NOT NULL COMMENT '是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109',
  `high` decimal(32,16) unsigned NOT NULL DEFAULT '0.0000000000000000' COMMENT '最高成交价',
  `low` decimal(32,16) unsigned NOT NULL DEFAULT '0.0000000000000000' COMMENT '最低成交价',
  `amount24` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '24小时成交张数',
  `size24` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '24小时成交价值',
  `first` decimal(32,16) unsigned NOT NULL DEFAULT '0.0000000000000000' COMMENT '原始成交价',
  `last` decimal(32,16) unsigned NOT NULL DEFAULT '0.0000000000000000' COMMENT '最新成交价',
  `change_24` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '24小时价格涨跌幅',
  `buy` decimal(32,16) unsigned NOT NULL DEFAULT '0.0000000000000000' COMMENT '盘口最高买价',
  `sell` decimal(32,16) unsigned NOT NULL DEFAULT '0.0000000000000000' COMMENT '盘口最低卖价',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_contract_code` (`contract_code`),
  KEY `idx_lt_created_date` (`created_date`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='最新成交';

-- ----------------------------
-- Records of latest_ticker
-- ----------------------------
BEGIN;
INSERT INTO `latest_ticker` VALUES (9, 'fbtcusd', 12301.7285000000000000, 11057.0864000000000000, 219622298.0000000000000000, 19047.8460384100000000, 11910.9755000000000000, 11300.1475000000000000, -610.8280000000000000, 11298.8700000000000000, 11301.6300000000000000, '2019-07-01 14:45:11', '2019-06-26 15:31:26');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
