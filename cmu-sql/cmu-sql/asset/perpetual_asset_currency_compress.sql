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

 Date: 01/07/2019 14:56:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
-- Records of perpetual_asset_currency_compress
-- ----------------------------
BEGIN;
INSERT INTO `perpetual_asset_currency_compress` VALUES (1, 'BTC', '{\"blockBrowser\":\"123\",\"cnWikiUrl\":\"\",\"currencyPictureUrl\":\"\",\"fullName\":\"btc\",\"needAlert\":0,\"needTag\":0,\"sign\":\"btc\",\"tagField\":\"\",\"txExplorerUrl\":\"啊的2121\",\"usWikiUrl\":\"\",\"zone\":1}', 1, 1, 1, 1, 0, 0, 1, 0.01000000, 10, 10, 10.00000000, 0.00100000, 100.00000000, '2018-12-19 11:51:15', 1);
INSERT INTO `perpetual_asset_currency_compress` VALUES (77, 'TUSD', '{\"blockBrowser\":\"123\",\"cnWikiUrl\":\"\",\"currencyPictureUrl\":\"\",\"fullName\":\"tusd\",\"needAlert\":0,\"needTag\":0,\"sign\":\"tusd\",\"tagField\":\"\",\"txExplorerUrl\":\"啊的2121\",\"usWikiUrl\":\"\",\"zone\":1}', 1, 1, 2, 0, 0, 0, 1, 0.01000000, 3, 4, 1.00000000, 1.00000000, 1000.00000000, '2018-12-19 12:48:56', 1);
INSERT INTO `perpetual_asset_currency_compress` VALUES (65, 'ZRX', '{\"blockBrowser\":\"http://www.baidu.com\",\"cnWikiUrl\":\"\",\"currencyPictureUrl\":\"\",\"fullName\":\"ZRX\",\"needAlert\":0,\"needTag\":0,\"sign\":\"ZRX\",\"tagField\":\"\",\"txExplorerUrl\":\"http://www.baidu.com\",\"usWikiUrl\":\"\",\"zone\":1}', 1, 1, 1, 1, 0, 0, 1, 1.00000000, 1, 1, 1.00000000, 1.00000000, 10000.00000000, '2018-12-03 20:37:33', 1);
INSERT INTO `perpetual_asset_currency_compress` VALUES (4, 'ETC', '{\"blockBrowser\":\"看\",\"cnWikiUrl\":\"\",\"currencyPictureUrl\":\"\",\"currencySummary\":\"Ethereum Classic\",\"fullName\":\"Ethereum Classic\",\"needAlert\":0,\"needTag\":0,\"sign\":\"E\",\"tagField\":\"\",\"txExplorerUrl\":\"11\",\"usWikiUrl\":\"\",\"zone\":0}', 1, 1, 4, 1, 0, 0, 1, 0.01000000, 12, 120, 0.01000000, 0.02000000, 1.00000000, '2018-08-23 11:33:53', 1);
INSERT INTO `perpetual_asset_currency_compress` VALUES (6, 'USDT', '{\"blockBrowser\":\"1\",\"cnWikiUrl\":\"https://coinmex.zendesk.com/hc/zh-cn/articles/360004404972\",\"currencyPictureUrl\":\"\",\"fullName\":\"USDT\",\"needAlert\":0,\"needTag\":0,\"sign\":\"$\",\"tagField\":\"\",\"txExplorerUrl\":\"http://www.baidu.com\",\"usWikiUrl\":\"https://coinmex.zendesk.com/hc/zh-cn/articles/360004404972\",\"zone\":0}', 1, 0, 123, 1, 0, 0, 1, 10.00000000, 1, 6, 100.00000000, 100.00000000, 1.00000000, '2018-12-19 12:21:52', 1);
INSERT INTO `perpetual_asset_currency_compress` VALUES (10, 'OMG', '{\"blockBrowser\":\"1234\",\"cnWikiUrl\":\"\",\"currencyPictureUrl\":\"\",\"fullName\":\"OMG\",\"needAlert\":0,\"needTag\":0,\"sign\":\"omg\",\"tagField\":\"\",\"txExplorerUrl\":\"1234\",\"usWikiUrl\":\"\",\"zone\":0}', 1, 1, 111, 1, 0, 0, 0, 0.20000000, 12, 120, 0.20000000, 0.40000000, 1.00000000, '2018-12-19 12:18:46', 1);
INSERT INTO `perpetual_asset_currency_compress` VALUES (5, 'BCH', '{\"blockBrowser\":\"4\",\"cnWikiUrl\":\"https://coinmex.zendesk.com/hc/zh-cn/articles/360004405012\",\"currencyPictureUrl\":\"\",\"fullName\":\"Bitcoin Cash\",\"needAlert\":0,\"needTag\":0,\"sign\":\"B\",\"tagField\":\"\",\"txExplorerUrl\":\"http://bch.btc.com/\",\"usWikiUrl\":\"https://coinmex.zendesk.com/hc/zh-cn/articles/360004405012\",\"zone\":0}', 1, 1, 10, 1, 0, 0, 1, 0.00080000, 1, 6, 0.01000000, 0.00200000, 1.00000000, '2018-12-19 11:57:30', 1);
INSERT INTO `perpetual_asset_currency_compress` VALUES (2, 'ETH', '{\"blockBrowser\":\"http://www.baidu.com\",\"cnWikiUrl\":\"http://www.baidu.com\",\"currencyPictureUrl\":\"\",\"fullName\":\"ETH\",\"needAlert\":0,\"needTag\":0,\"sign\":\"ETH\",\"tagField\":\"\",\"txExplorerUrl\":\"http://www.baidu.com\",\"usWikiUrl\":\"http://www.baidu.com\",\"zone\":1}', 1, 1, 1, 1, 0, 0, 1, 0.10000000, 1, 1, 1.00000000, 0.01000000, 10000.00000000, '2018-12-04 17:42:24', 1);
INSERT INTO `perpetual_asset_currency_compress` VALUES (26, 'CMDK', '{\"blockBrowser\":\"1\",\"cnWikiUrl\":\"\",\"currencyPictureUrl\":\"\",\"fullName\":\"cmdk\",\"needAlert\":0,\"needTag\":0,\"sign\":\"\",\"tagField\":\"\",\"txExplorerUrl\":\"1111\",\"usWikiUrl\":\"\",\"zone\":0}', 0, 1, 26, 1, 0, 0, 1, 1.00000000, 1, 1, 1.00000000, 1.00000000, 1.00000000, '2018-12-05 11:05:34', 1);
INSERT INTO `perpetual_asset_currency_compress` VALUES (94, 'FBTC', '{\"blockBrowser\":\"www\",\"cnWikiUrl\":\"\",\"currencyPictureUrl\":\"\",\"fullName\":\"FBTC\",\"needAlert\":0,\"needTag\":0,\"sign\":\"FBTC\",\"tagField\":\"\",\"txExplorerUrl\":\"www\",\"usWikiUrl\":\"\",\"zone\":1}', 0, 0, 3, 1, 0, 0, 1, 0.01000000, 1, 1, 1.00000000, 1.00000000, 100000.00000000, '2019-07-01 14:29:44', 1);
INSERT INTO `perpetual_asset_currency_compress` VALUES (10001, 'USD', '{\"blockBrowser\":\"www\",\"cnWikiUrl\":\"\",\"currencyPictureUrl\":\"\",\"fullName\":\"USD\",\"needAlert\":0,\"needTag\":0,\"sign\":\"USD\",\"tagField\":\"\",\"txExplorerUrl\":\"www\",\"usWikiUrl\":\"\",\"zone\":1}', 1, 1, 7, 1, 0, 0, 1, 0.01000000, 1, 1, 1.00000000, 1.00000000, 10000.00000000, '2018-12-21 17:14:48', 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
