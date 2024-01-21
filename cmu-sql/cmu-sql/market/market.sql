/*
 Navicat Premium Data Transfer

 Source Server         : ppl-xxx1
 Source Server Type    : MySQL
 Source Server Version : 50644
 Source Host           : 192.168.102.36:3306
 Source Schema         : market

 Target Server Type    : MySQL
 Target Server Version : 50644
 File Encoding         : 65001

 Date: 01/07/2019 14:49:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for coin_config
-- ----------------------------
DROP TABLE IF EXISTS `coin_config`;
CREATE TABLE `coin_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `symbol` int(11) NOT NULL DEFAULT '0' COMMENT '币种数字值',
  `symbol_name` varchar(255) NOT NULL COMMENT '币种英文缩写',
  `index_market_from` int(11) NOT NULL DEFAULT '0' COMMENT '币指数的marketFrom',
  `symbol_mark` varchar(255) DEFAULT NULL COMMENT '币种符号',
  `market_from` varchar(512) DEFAULT NULL COMMENT '币种对应的marketFrom列表',
  `pricePoint` int(11) NOT NULL DEFAULT '2' COMMENT '交易小数位',
  `invalid_switch` tinyint(2) NOT NULL DEFAULT '0' COMMENT '无效运算开关 0默认关、1开',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '类型 0指数、1组合指数',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态 0正常、1预发、2下线',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_cc_symbol` (`symbol`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for fetching_data_path
-- ----------------------------
DROP TABLE IF EXISTS `fetching_data_path`;
CREATE TABLE `fetching_data_path` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '抓取数据URL',
  `market_from` int(11) NOT NULL COMMENT '唯一标识',
  `create_time` timestamp NULL DEFAULT '2014-01-01 09:01:01' COMMENT '添加时间',
  `path` varchar(1024) DEFAULT NULL COMMENT '路径',
  `symbol` int(11) DEFAULT NULL,
  `web_name` varchar(100) NOT NULL COMMENT '网站名称',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `url_key` varchar(255) DEFAULT NULL,
  `ovm` varchar(255) DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1启用0禁用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8;

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
-- Table structure for latest_ticker
-- ----------------------------
DROP TABLE IF EXISTS `latest_ticker`;
CREATE TABLE `latest_ticker` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `high` double(32,8) DEFAULT NULL,
  `low` double(32,8) DEFAULT NULL,
  `volume` double(32,8) DEFAULT NULL,
  `volume_22` double(32,8) DEFAULT NULL,
  `change_24` double(32,8) DEFAULT NULL,
  `last` double(32,8) DEFAULT NULL,
  `buy` double(32,8) DEFAULT NULL,
  `sell` double(32,8) DEFAULT NULL,
  `open` double(32,8) DEFAULT NULL,
  `market_from` int(11) NOT NULL,
  `offset_buy` double(32,8) DEFAULT NULL,
  `offset_sell` double(32,8) DEFAULT NULL,
  `auto` tinyint(2) NOT NULL DEFAULT '0' COMMENT '模式0:自动1:手动',
  `delay` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否立即执行0:否1:是',
  `order_index` tinyint(2) NOT NULL DEFAULT '0' COMMENT '排序',
  `name` varchar(50) NOT NULL COMMENT '网站名称',
  `base_currency` int(11) NOT NULL COMMENT '交易币',
  `quote_currency` int(11) NOT NULL COMMENT '计价币 3 非币对币模式',
  `moneytype` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0:cny 1:usd',
  `symbol` varchar(255) NOT NULL COMMENT 'BTC/USD',
  `valid` tinyint(2) NOT NULL DEFAULT '1' COMMENT '无效计算开关  默认1开、0关',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `market_from_UNIQUE` (`market_from`) USING BTREE,
  KEY `order_index` (`order_index`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for market_all_rate
-- ----------------------------
DROP TABLE IF EXISTS `market_all_rate`;
CREATE TABLE `market_all_rate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rate_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '汇率名',
  `modify_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1可用0不可用',
  `create_time` timestamp NULL DEFAULT '2014-01-01 09:01:01',
  `rate_parities` double NOT NULL COMMENT '汇率',
  `pair_avg` double(10,3) NOT NULL DEFAULT '0.000',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for market_data_1
-- ----------------------------
DROP TABLE IF EXISTS `market_data_1`;
CREATE TABLE `market_data_1` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '????',
  `market_from` int(4) NOT NULL DEFAULT '0',
  `open` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '???',
  `high` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '???',
  `low` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '???',
  `close` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '???',
  `volume` double(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '???',
  `coin_volume` double(16,8) DEFAULT '0.00000000' COMMENT '???',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0:1?? 1:5?? 2:15?? 3:? 4:? 5:?',
  `start_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '??id',
  `end_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '??id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '????',
  PRIMARY KEY (`id`),
  KEY `idx_md1_type_create_date` (`type`,`created_date`) USING BTREE,
  KEY `idx_md1_type` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for market_data_100001
-- ----------------------------
DROP TABLE IF EXISTS `market_data_100001`;
CREATE TABLE `market_data_100001` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '交易数据',
  `market_from` int(4) NOT NULL DEFAULT '0',
  `open` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '开盘价',
  `high` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最高价',
  `low` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最低价',
  `close` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '收盘价',
  `volume` double(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交量',
  `coin_volume` double(16,8) DEFAULT '0.00000000' COMMENT '币总数',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0:1分钟 1:5分钟 2:15分钟 3:日 4:周 5:月',
  `start_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '起始id',
  `end_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_md100001_type_create_date` (`type`,`created_date`) USING BTREE,
  KEY `idx_md100001_type` (`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33955807 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for market_data_100002
-- ----------------------------
DROP TABLE IF EXISTS `market_data_100002`;
CREATE TABLE `market_data_100002` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '交易数据',
  `market_from` int(4) NOT NULL DEFAULT '0',
  `open` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '开盘价',
  `high` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最高价',
  `low` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最低价',
  `close` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '收盘价',
  `volume` double(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交量',
  `coin_volume` double(16,8) DEFAULT '0.00000000' COMMENT '币总数',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0:1分钟 1:5分钟 2:15分钟 3:日 4:周 5:月',
  `start_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '起始id',
  `end_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_md100002_type_create_date` (`type`,`created_date`) USING BTREE,
  KEY `idx_md100002_type` (`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33955807 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for market_data_100003
-- ----------------------------
DROP TABLE IF EXISTS `market_data_100003`;
CREATE TABLE `market_data_100003` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '交易数据',
  `market_from` int(4) NOT NULL DEFAULT '0',
  `open` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '开盘价',
  `high` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最高价',
  `low` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最低价',
  `close` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '收盘价',
  `volume` double(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交量',
  `coin_volume` double(16,8) DEFAULT '0.00000000' COMMENT '币总数',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0:1分钟 1:5分钟 2:15分钟 3:日 4:周 5:月',
  `start_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '起始id',
  `end_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_md100003_type_create_date` (`type`,`created_date`) USING BTREE,
  KEY `idx_md100003_type` (`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33955807 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for market_data_100004
-- ----------------------------
DROP TABLE IF EXISTS `market_data_100004`;
CREATE TABLE `market_data_100004` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '交易数据',
  `market_from` int(4) NOT NULL DEFAULT '0',
  `open` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '开盘价',
  `high` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最高价',
  `low` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最低价',
  `close` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '收盘价',
  `volume` double(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交量',
  `coin_volume` double(16,8) DEFAULT '0.00000000' COMMENT '币总数',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0:1分钟 1:5分钟 2:15分钟 3:日 4:周 5:月',
  `start_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '起始id',
  `end_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_md100004_type_create_date` (`type`,`created_date`) USING BTREE,
  KEY `idx_md100004_type` (`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33955807 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for market_data_100005
-- ----------------------------
DROP TABLE IF EXISTS `market_data_100005`;
CREATE TABLE `market_data_100005` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '交易数据',
  `market_from` int(4) NOT NULL DEFAULT '0',
  `open` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '开盘价',
  `high` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最高价',
  `low` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最低价',
  `close` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '收盘价',
  `volume` double(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交量',
  `coin_volume` double(16,8) DEFAULT '0.00000000' COMMENT '币总数',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0:1分钟 1:5分钟 2:15分钟 3:日 4:周 5:月',
  `start_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '起始id',
  `end_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_md100005_type_create_date` (`type`,`created_date`) USING BTREE,
  KEY `idx_md100005_type` (`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33955807 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for market_data_10001
-- ----------------------------
DROP TABLE IF EXISTS `market_data_10001`;
CREATE TABLE `market_data_10001` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '交易数据',
  `market_from` int(4) NOT NULL DEFAULT '0',
  `open` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '开盘价',
  `high` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最高价',
  `low` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最低价',
  `close` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '收盘价',
  `volume` double(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交量',
  `coin_volume` double(16,8) DEFAULT '0.00000000' COMMENT '币总数',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0:1分钟 1:5分钟 2:15分钟 3:日 4:周 5:月',
  `start_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '起始id',
  `end_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_md10001_type_create_date` (`type`,`created_date`) USING BTREE,
  KEY `idx_md10001_type` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for market_data_10002
-- ----------------------------
DROP TABLE IF EXISTS `market_data_10002`;
CREATE TABLE `market_data_10002` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '交易数据',
  `market_from` int(4) NOT NULL DEFAULT '0',
  `open` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '开盘价',
  `high` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最高价',
  `low` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最低价',
  `close` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '收盘价',
  `volume` double(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交量',
  `coin_volume` double(16,8) DEFAULT '0.00000000' COMMENT '币总数',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0:1分钟 1:5分钟 2:15分钟 3:日 4:周 5:月',
  `start_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '起始id',
  `end_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_md10002_type_create_date` (`type`,`created_date`) USING BTREE,
  KEY `idx_md10002_type` (`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33955807 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for market_data_10003
-- ----------------------------
DROP TABLE IF EXISTS `market_data_10003`;
CREATE TABLE `market_data_10003` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '交易数据',
  `market_from` int(4) NOT NULL DEFAULT '0',
  `open` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '开盘价',
  `high` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最高价',
  `low` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最低价',
  `close` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '收盘价',
  `volume` double(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交量',
  `coin_volume` double(16,8) DEFAULT '0.00000000' COMMENT '币总数',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0:1分钟 1:5分钟 2:15分钟 3:日 4:周 5:月',
  `start_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '起始id',
  `end_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_md10003_type_create_date` (`type`,`created_date`) USING BTREE,
  KEY `idx_md10003_type` (`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33955807 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for market_data_10004
-- ----------------------------
DROP TABLE IF EXISTS `market_data_10004`;
CREATE TABLE `market_data_10004` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '交易数据',
  `market_from` int(4) NOT NULL DEFAULT '0',
  `open` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '开盘价',
  `high` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最高价',
  `low` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最低价',
  `close` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '收盘价',
  `volume` double(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交量',
  `coin_volume` double(16,8) DEFAULT '0.00000000' COMMENT '币总数',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0:1分钟 1:5分钟 2:15分钟 3:日 4:周 5:月',
  `start_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '起始id',
  `end_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_md10004_type_create_date` (`type`,`created_date`) USING BTREE,
  KEY `idx_md10004_type` (`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33955807 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for market_data_10005
-- ----------------------------
DROP TABLE IF EXISTS `market_data_10005`;
CREATE TABLE `market_data_10005` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '交易数据',
  `market_from` int(4) NOT NULL DEFAULT '0',
  `open` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '开盘价',
  `high` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最高价',
  `low` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最低价',
  `close` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '收盘价',
  `volume` double(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交量',
  `coin_volume` double(16,8) DEFAULT '0.00000000' COMMENT '币总数',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0:1分钟 1:5分钟 2:15分钟 3:日 4:周 5:月',
  `start_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '起始id',
  `end_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_md10005_type_create_date` (`type`,`created_date`) USING BTREE,
  KEY `idx_md10005_type` (`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33955807 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for market_data_11
-- ----------------------------
DROP TABLE IF EXISTS `market_data_11`;
CREATE TABLE `market_data_11` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '交易数据',
  `market_from` int(4) NOT NULL DEFAULT '0',
  `open` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '开盘价',
  `high` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最高价',
  `low` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最低价',
  `close` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '收盘价',
  `volume` double(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交量',
  `coin_volume` double(16,8) DEFAULT '0.00000000' COMMENT '币总数',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0:1分钟 1:5分钟 2:15分钟 3:日 4:周 5:月',
  `start_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '起始id',
  `end_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_md11_type_create_date` (`type`,`created_date`) USING BTREE,
  KEY `idx_md11_type` (`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33955807 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for market_data_13
-- ----------------------------
DROP TABLE IF EXISTS `market_data_13`;
CREATE TABLE `market_data_13` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '交易数据',
  `market_from` int(4) NOT NULL DEFAULT '0',
  `open` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '开盘价',
  `high` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最高价',
  `low` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最低价',
  `close` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '收盘价',
  `volume` double(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交量',
  `coin_volume` double(16,8) DEFAULT '0.00000000' COMMENT '币总数',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0:1分钟 1:5分钟 2:15分钟 3:日 4:周 5:月',
  `start_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '起始id',
  `end_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_md13_type_create_date` (`type`,`created_date`) USING BTREE,
  KEY `idx_md13_type` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for market_data_2
-- ----------------------------
DROP TABLE IF EXISTS `market_data_2`;
CREATE TABLE `market_data_2` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '????',
  `market_from` int(4) NOT NULL DEFAULT '0',
  `open` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '???',
  `high` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '???',
  `low` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '???',
  `close` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '???',
  `volume` double(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '???',
  `coin_volume` double(16,8) DEFAULT '0.00000000' COMMENT '???',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0:1?? 1:5?? 2:15?? 3:? 4:? 5:?',
  `start_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '??id',
  `end_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '??id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '????',
  PRIMARY KEY (`id`),
  KEY `idx_md2_type_create_date` (`type`,`created_date`) USING BTREE,
  KEY `idx_md2_type` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for market_data_3213
-- ----------------------------
DROP TABLE IF EXISTS `market_data_3213`;
CREATE TABLE `market_data_3213` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '交易数据',
  `market_from` int(4) NOT NULL DEFAULT '0',
  `open` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '开盘价',
  `high` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最高价',
  `low` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最低价',
  `close` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '收盘价',
  `volume` double(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交量',
  `coin_volume` double(16,8) DEFAULT '0.00000000' COMMENT '币总数',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0:1分钟 1:5分钟 2:15分钟 3:日 4:周 5:月',
  `start_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '起始id',
  `end_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_md3213_type_create_date` (`type`,`created_date`) USING BTREE,
  KEY `idx_md3213_type` (`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33955807 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for market_data_4
-- ----------------------------
DROP TABLE IF EXISTS `market_data_4`;
CREATE TABLE `market_data_4` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '????',
  `market_from` int(4) NOT NULL DEFAULT '0',
  `open` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '???',
  `high` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '???',
  `low` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '???',
  `close` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '???',
  `volume` double(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '???',
  `coin_volume` double(16,8) DEFAULT '0.00000000' COMMENT '???',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0:1?? 1:5?? 2:15?? 3:? 4:? 5:?',
  `start_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '??id',
  `end_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '??id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '????',
  PRIMARY KEY (`id`),
  KEY `idx_md4_type_create_date` (`type`,`created_date`) USING BTREE,
  KEY `idx_md4_type` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for market_data_5
-- ----------------------------
DROP TABLE IF EXISTS `market_data_5`;
CREATE TABLE `market_data_5` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '????',
  `market_from` int(4) NOT NULL DEFAULT '0',
  `open` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '???',
  `high` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '???',
  `low` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '???',
  `close` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '???',
  `volume` double(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '???',
  `coin_volume` double(16,8) DEFAULT '0.00000000' COMMENT '???',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0:1?? 1:5?? 2:15?? 3:? 4:? 5:?',
  `start_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '??id',
  `end_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '??id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '????',
  PRIMARY KEY (`id`),
  KEY `idx_md5_type_create_date` (`type`,`created_date`) USING BTREE,
  KEY `idx_md5_type` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for market_data_6
-- ----------------------------
DROP TABLE IF EXISTS `market_data_6`;
CREATE TABLE `market_data_6` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '????',
  `market_from` int(4) NOT NULL DEFAULT '0',
  `open` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '???',
  `high` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '???',
  `low` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '???',
  `close` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '???',
  `volume` double(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '???',
  `coin_volume` double(16,8) DEFAULT '0.00000000' COMMENT '???',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0:1?? 1:5?? 2:15?? 3:? 4:? 5:?',
  `start_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '??id',
  `end_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '??id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '????',
  PRIMARY KEY (`id`),
  KEY `idx_md6_type_create_date` (`type`,`created_date`) USING BTREE,
  KEY `idx_md6_type` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for market_data_7
-- ----------------------------
DROP TABLE IF EXISTS `market_data_7`;
CREATE TABLE `market_data_7` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '交易数据',
  `market_from` int(4) NOT NULL DEFAULT '0',
  `open` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '开盘价',
  `high` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最高价',
  `low` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最低价',
  `close` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '收盘价',
  `volume` double(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交量',
  `coin_volume` double(16,8) DEFAULT '0.00000000' COMMENT '币总数',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0:1分钟 1:5分钟 2:15分钟 3:日 4:周 5:月',
  `start_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '起始id',
  `end_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_md7_type_create_date` (`type`,`created_date`) USING BTREE,
  KEY `idx_md7_type` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for market_data_8
-- ----------------------------
DROP TABLE IF EXISTS `market_data_8`;
CREATE TABLE `market_data_8` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '交易数据',
  `market_from` int(4) NOT NULL DEFAULT '0',
  `open` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '开盘价',
  `high` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最高价',
  `low` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最低价',
  `close` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '收盘价',
  `volume` double(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交量',
  `coin_volume` double(16,8) DEFAULT '0.00000000' COMMENT '币总数',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0:1分钟 1:5分钟 2:15分钟 3:日 4:周 5:月',
  `start_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '起始id',
  `end_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_md8_type_create_date` (`type`,`created_date`) USING BTREE,
  KEY `idx_md8_type` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for market_data_9
-- ----------------------------
DROP TABLE IF EXISTS `market_data_9`;
CREATE TABLE `market_data_9` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '交易数据',
  `market_from` int(4) NOT NULL DEFAULT '0',
  `open` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '开盘价',
  `high` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最高价',
  `low` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '最低价',
  `close` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '收盘价',
  `volume` double(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交量',
  `coin_volume` double(16,8) DEFAULT '0.00000000' COMMENT '币总数',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0:1分钟 1:5分钟 2:15分钟 3:日 4:周 5:月',
  `start_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '起始id',
  `end_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_md9_type_create_date` (`type`,`created_date`) USING BTREE,
  KEY `idx_md9_type` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for market_index
-- ----------------------------
DROP TABLE IF EXISTS `market_index`;
CREATE TABLE `market_index` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `symbol` int(11) NOT NULL DEFAULT '0' COMMENT '0:btc 1:ltc',
  `price` double(16,4) NOT NULL,
  `detail` text COMMENT '生成指数来源',
  `invalid` varchar(40) DEFAULT NULL COMMENT '标识平台价格是否有效 “,”号隔开',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_fi_created_date` (`created_date`),
  KEY `idx_fi_sysmbol_createdDate` (`symbol`,`created_date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=338699 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for market_index_record_1
-- ----------------------------
DROP TABLE IF EXISTS `market_index_record_1`;
CREATE TABLE `market_index_record_1` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '??',
  `market_from` int(11) NOT NULL COMMENT '??',
  `price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '??',
  `middle_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '????',
  `invalid` tinyint(2) NOT NULL DEFAULT '0' COMMENT '??0???1??',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '????',
  PRIMARY KEY (`id`),
  KEY `idx_mir1_market_from` (`market_from`) USING BTREE,
  KEY `idx_mir1_created_date` (`created_date`)
) ENGINE=InnoDB AUTO_INCREMENT=426051 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for market_index_record_13
-- ----------------------------
DROP TABLE IF EXISTS `market_index_record_13`;
CREATE TABLE `market_index_record_13` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `market_from` int(11) NOT NULL COMMENT '站点',
  `price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '价格',
  `middle_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '中间价格',
  `invalid` tinyint(2) NOT NULL DEFAULT '0' COMMENT '默认0有效、1无效',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录时间',
  PRIMARY KEY (`id`),
  KEY `idx_mir13_market_from` (`market_from`) USING BTREE,
  KEY `idx_mir13_created_date` (`created_date`)
) ENGINE=InnoDB AUTO_INCREMENT=85211 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for market_index_record_2
-- ----------------------------
DROP TABLE IF EXISTS `market_index_record_2`;
CREATE TABLE `market_index_record_2` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '??',
  `market_from` int(11) NOT NULL COMMENT '??',
  `price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '??',
  `middle_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '????',
  `invalid` tinyint(2) NOT NULL DEFAULT '0' COMMENT '??0???1??',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '????',
  PRIMARY KEY (`id`),
  KEY `idx_mir2_market_from` (`market_from`) USING BTREE,
  KEY `idx_mir2_created_date` (`created_date`)
) ENGINE=InnoDB AUTO_INCREMENT=383446 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for market_index_record_4
-- ----------------------------
DROP TABLE IF EXISTS `market_index_record_4`;
CREATE TABLE `market_index_record_4` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '??',
  `market_from` int(11) NOT NULL COMMENT '??',
  `price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '??',
  `middle_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '????',
  `invalid` tinyint(2) NOT NULL DEFAULT '0' COMMENT '??0???1??',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '????',
  PRIMARY KEY (`id`),
  KEY `idx_mir4_market_from` (`market_from`) USING BTREE,
  KEY `idx_mir4_created_date` (`created_date`)
) ENGINE=InnoDB AUTO_INCREMENT=426051 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for market_index_record_5
-- ----------------------------
DROP TABLE IF EXISTS `market_index_record_5`;
CREATE TABLE `market_index_record_5` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '??',
  `market_from` int(11) NOT NULL COMMENT '??',
  `price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '??',
  `middle_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '????',
  `invalid` tinyint(2) NOT NULL DEFAULT '0' COMMENT '??0???1??',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '????',
  PRIMARY KEY (`id`),
  KEY `idx_mir5_market_from` (`market_from`) USING BTREE,
  KEY `idx_mir5_created_date` (`created_date`)
) ENGINE=InnoDB AUTO_INCREMENT=170421 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for market_index_record_6
-- ----------------------------
DROP TABLE IF EXISTS `market_index_record_6`;
CREATE TABLE `market_index_record_6` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '??',
  `market_from` int(11) NOT NULL COMMENT '??',
  `price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '??',
  `middle_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '????',
  `invalid` tinyint(2) NOT NULL DEFAULT '0' COMMENT '??0???1??',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '????',
  PRIMARY KEY (`id`),
  KEY `idx_mir6_market_from` (`market_from`) USING BTREE,
  KEY `idx_mir6_created_date` (`created_date`)
) ENGINE=InnoDB AUTO_INCREMENT=127816 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for market_index_record_7
-- ----------------------------
DROP TABLE IF EXISTS `market_index_record_7`;
CREATE TABLE `market_index_record_7` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `market_from` int(11) NOT NULL COMMENT '站点',
  `price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '价格',
  `middle_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '中间价格',
  `invalid` tinyint(2) NOT NULL DEFAULT '0' COMMENT '默认0有效、1无效',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录时间',
  PRIMARY KEY (`id`),
  KEY `idx_mir7_market_from` (`market_from`) USING BTREE,
  KEY `idx_mir7_created_date` (`created_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for market_index_record_8
-- ----------------------------
DROP TABLE IF EXISTS `market_index_record_8`;
CREATE TABLE `market_index_record_8` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `market_from` int(11) NOT NULL COMMENT '站点',
  `price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '价格',
  `middle_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '中间价格',
  `invalid` tinyint(2) NOT NULL DEFAULT '0' COMMENT '默认0有效、1无效',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录时间',
  PRIMARY KEY (`id`),
  KEY `idx_mir8_market_from` (`market_from`) USING BTREE,
  KEY `idx_mir8_created_date` (`created_date`)
) ENGINE=InnoDB AUTO_INCREMENT=85211 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for market_index_record_9
-- ----------------------------
DROP TABLE IF EXISTS `market_index_record_9`;
CREATE TABLE `market_index_record_9` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `market_from` int(11) NOT NULL COMMENT '站点',
  `price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '价格',
  `middle_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '中间价格',
  `invalid` tinyint(2) NOT NULL DEFAULT '0' COMMENT '默认0有效、1无效',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录时间',
  PRIMARY KEY (`id`),
  KEY `idx_mir9_market_from` (`market_from`) USING BTREE,
  KEY `idx_mir9_created_date` (`created_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for market_properties
-- ----------------------------
DROP TABLE IF EXISTS `market_properties`;
CREATE TABLE `market_properties` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `prop_key` varchar(255) NOT NULL DEFAULT '' COMMENT '查询KEY',
  `prop_value` text NOT NULL COMMENT '配置信息为JSON数据',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_futures_properties_prop_key` (`prop_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for market_rate
-- ----------------------------
DROP TABLE IF EXISTS `market_rate`;
CREATE TABLE `market_rate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rate_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '汇率名',
  `modify_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1可用0不可用',
  `create_time` timestamp NULL DEFAULT '2014-01-01 09:01:01',
  `rate_parities` double NOT NULL COMMENT '汇率',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for mine_pool_share_data
-- ----------------------------
DROP TABLE IF EXISTS `mine_pool_share_data`;
CREATE TABLE `mine_pool_share_data` (
  `name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '矿池',
  `pool_mode` varchar(20) COLLATE utf8_bin NOT NULL COMMENT 'year:年, month: 月, month3: 3个月, day: 天, day3: 3天, all: 所有',
  `market_share_of_pool` double(5,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '算力占比',
  `block_amount` int(11) NOT NULL DEFAULT '0' COMMENT '块数量',
  `empty_block_proportion` double(16,4) NOT NULL DEFAULT '0.0000' COMMENT '空块占比',
  `avg_block_size` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '平均块大小(Bytes)',
  `avg_block_miner_fee` double(20,4) unsigned NOT NULL DEFAULT '0.0000' COMMENT '平均块矿工费(BTC)',
  `miner_fee_and_block_bonus_proportion` double(16,4) unsigned NOT NULL DEFAULT '0.0000' COMMENT '矿工费与块奖励占比',
  `rank` tinyint(2) NOT NULL COMMENT '排名',
  `computing_power` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '算力',
  `created_date` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`name`,`pool_mode`),
  KEY `pool_mode` (`pool_mode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

SET FOREIGN_KEY_CHECKS = 1;
