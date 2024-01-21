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

 Date: 01/07/2019 14:49:58
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
-- Records of coin_config
-- ----------------------------
BEGIN;
INSERT INTO `coin_config` VALUES (1, 1, 'btc', 1, 'BTC', '[4,19,24,27,119,197,185,188,191,194]', 2, 0, 0, 0, '2018-01-04 21:40:43', NULL);
INSERT INTO `coin_config` VALUES (2, 2, 'ltc', 2, 'LTC', '[20,50,51,110,183,186,189,192,195]', 3, 0, 0, 0, '2018-01-04 21:41:03', NULL);
INSERT INTO `coin_config` VALUES (3, 4, 'eth', 4, 'ETH', '[42,44,46,116,120,184,187,190,193,196]', 3, 0, 0, 0, '2018-01-04 21:41:55', NULL);
INSERT INTO `coin_config` VALUES (4, 5, 'etc', 5, 'ETC', '[43,123,130,147]', 3, 0, 0, 0, '2018-01-04 21:42:41', NULL);
INSERT INTO `coin_config` VALUES (5, 6, 'bch', 6, 'BCH', '[148,122,131]', 3, 0, 0, 0, '2018-01-04 21:44:09', NULL);
INSERT INTO `coin_config` VALUES (7, 10001, 'CMXQA001', 10001, '', '{\"initialCompleted\":true,\"initialDate\":1533189929000,\"portfolioConfigList\":[{\"price\":7649.24,\"ratio\":0.45,\"symbol\":1,\"symbolName\":\"btc\"},{\"price\":77.531,\"ratio\":0.2,\"symbol\":2,\"symbolName\":\"ltc\"},{\"price\":419.389,\"ratio\":0.35,\"symbol\":4,\"symbolName\":\"eth\"}]}', 2, 0, 1, 0, '2018-08-01 14:08:05', '2018-09-28 11:50:55');
INSERT INTO `coin_config` VALUES (8, 11, 'ZBKHHZS', 11, '', '{\"initialCompleted\":true,\"initialDate\":1533206073000,\"portfolioConfigList\":[{\"price\":7569.63,\"ratio\":0.2,\"symbol\":1,\"symbolName\":\"btc\"},{\"price\":76.836,\"ratio\":0.3,\"symbol\":2,\"symbolName\":\"ltc\"},{\"price\":414.529,\"ratio\":0.5,\"symbol\":4,\"symbolName\":\"eth\"}]}', 2, 0, 1, 2, '2018-08-02 18:36:47', '2018-09-27 14:54:14');
INSERT INTO `coin_config` VALUES (9, 10002, 'CMXQA002', 10002, '', '{\"initialCompleted\":true,\"initialDate\":1533265271000,\"portfolioConfigList\":[{\"price\":7364.01,\"ratio\":0.25,\"symbol\":1,\"symbolName\":\"btc\"},{\"price\":75.426,\"ratio\":0.25,\"symbol\":2,\"symbolName\":\"ltc\"},{\"price\":14.838,\"ratio\":0.5,\"symbol\":5,\"symbolName\":\"etc\"}]}', 2, 0, 1, 0, '2018-08-03 11:02:40', '2018-09-28 11:51:20');
INSERT INTO `coin_config` VALUES (10, 10003, 'CMXQA004', 10003, '', '{\"initialCompleted\":true,\"initialDate\":1533269003000,\"portfolioConfigList\":[{\"price\":7337.23,\"ratio\":0.2,\"symbol\":1,\"symbolName\":\"btc\"},{\"price\":75.271,\"ratio\":0.2,\"symbol\":2,\"symbolName\":\"ltc\"},{\"price\":402.161,\"ratio\":0.2,\"symbol\":4,\"symbolName\":\"eth\"},{\"price\":14.758,\"ratio\":0.2,\"symbol\":5,\"symbolName\":\"etc\"},{\"price\":1205.215,\"ratio\":0.2,\"symbol\":6,\"symbolName\":\"bch\"}]}', 2, 0, 1, 2, '2018-08-03 12:08:03', '2018-08-28 17:42:40');
INSERT INTO `coin_config` VALUES (11, 10004, 'CMT01', 10004, '', '{\"initialCompleted\":true,\"initialDate\":1533520800000,\"portfolioConfigList\":[{\"price\":7114.17,\"ratio\":0.5,\"symbol\":1,\"symbolName\":\"btc\"},{\"price\":411.351,\"ratio\":0.4,\"symbol\":4,\"symbolName\":\"eth\"},{\"price\":1168.575,\"ratio\":0.1,\"symbol\":6,\"symbolName\":\"bch\"}]}', 2, 0, 1, 2, '2018-08-04 16:58:57', '2018-09-28 11:50:50');
INSERT INTO `coin_config` VALUES (12, 3213, '平准基金', 3213, '', '{\"initialCompleted\":true,\"initialDate\":1533624704000,\"portfolioConfigList\":[{\"price\":6954.92,\"ratio\":1.0,\"symbol\":1,\"symbolName\":\"btc\"}]}', 2, 0, 1, 0, '2018-08-06 14:52:18', '2018-08-06 21:37:46');
INSERT INTO `coin_config` VALUES (13, 10005, 'test01', 10005, '', '{\"initialCompleted\":true,\"initialDate\":1533987208000,\"portfolioConfigList\":[{\"price\":6146.36,\"ratio\":0.75,\"symbol\":1,\"symbolName\":\"btc\"},{\"price\":56.519,\"ratio\":0.25,\"symbol\":2,\"symbolName\":\"ltc\"}]}', 2, 0, 1, 2, '2018-08-08 19:33:52', '2018-09-27 16:41:49');
INSERT INTO `coin_config` VALUES (14, 8, 'hur', 8, 'HUR', '[199]', 8, 0, 0, 2, '2018-01-04 21:40:43', NULL);
INSERT INTO `coin_config` VALUES (15, 100001, 'test002', 100001, '', '{\"initialCompleted\":true,\"initialDate\":1535457420000,\"portfolioConfigList\":[{\"price\":7002.51,\"ratio\":0.4,\"symbol\":1,\"symbolName\":\"btc\"},{\"price\":60.82,\"ratio\":0.25,\"symbol\":2,\"symbolName\":\"ltc\"},{\"price\":286.896,\"ratio\":0.35,\"symbol\":4,\"symbolName\":\"eth\"}]}', 2, 0, 1, 2, '2018-08-28 19:53:55', '2018-09-27 14:53:31');
INSERT INTO `coin_config` VALUES (16, 100002, 'test003', 100002, '', '{\"initialCompleted\":true,\"initialDate\":1535461800000,\"portfolioConfigList\":[{\"price\":7011.05,\"ratio\":0.5,\"symbol\":1,\"symbolName\":\"btc\"},{\"price\":61.083,\"ratio\":0.5,\"symbol\":2,\"symbolName\":\"ltc\"}]}', 2, 0, 1, 2, '2018-08-28 21:08:07', '2018-09-27 14:53:26');
INSERT INTO `coin_config` VALUES (17, 100003, 'test4', 100003, '', '{\"initialCompleted\":true,\"initialDate\":1535462565000,\"portfolioConfigList\":[{\"price\":7031.57,\"ratio\":0.5,\"symbol\":1,\"symbolName\":\"btc\"},{\"price\":61.29,\"ratio\":0.5,\"symbol\":2,\"symbolName\":\"ltc\"}]}', 2, 0, 1, 2, '2018-08-28 21:20:12', '2018-09-27 16:20:21');
INSERT INTO `coin_config` VALUES (18, 100004, 'test5', 100004, '', '{\"initialCompleted\":true,\"initialDate\":1535513234000,\"portfolioConfigList\":[{\"price\":7046.74,\"ratio\":0.5,\"symbol\":1,\"symbolName\":\"btc\"},{\"price\":61.986,\"ratio\":0.5,\"symbol\":2,\"symbolName\":\"ltc\"}]}', 2, 0, 1, 2, '2018-08-29 11:24:49', '2018-09-27 14:53:14');
INSERT INTO `coin_config` VALUES (19, 100005, 'cxytest', 100005, '', '{\"initialCompleted\":true,\"initialDate\":1537497441000,\"portfolioConfigList\":[{\"price\":1068.451,\"ratio\":0.5,\"symbol\":6,\"symbolName\":\"bch\"},{\"price\":0.0028,\"ratio\":0.5,\"symbol\":8,\"symbolName\":\"hur\"}]}', 2, 0, 1, 2, '2018-09-21 10:37:55', '2018-09-21 10:37:55');
INSERT INTO `coin_config` VALUES (21, 12, 'pvb', 8, 'PVB', '[199]', 8, 0, 0, 2, '2018-01-04 21:40:43', NULL);
INSERT INTO `coin_config` VALUES (22, 13, 'neo', 13, 'NEO', '[300,301]', 2, 0, 0, 0, '2018-01-04 21:40:43', NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
