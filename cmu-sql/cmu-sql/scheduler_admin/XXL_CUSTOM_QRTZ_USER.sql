/*
 Navicat Premium Data Transfer

 Source Server         : ppl-xxx1
 Source Server Type    : MySQL
 Source Server Version : 50644
 Source Host           : 192.168.102.36:3306
 Source Schema         : scheduler_admin

 Target Server Type    : MySQL
 Target Server Version : 50644
 File Encoding         : 65001

 Date: 01/07/2019 14:43:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for XXL_CUSTOM_QRTZ_USER
-- ----------------------------
DROP TABLE IF EXISTS `XXL_CUSTOM_QRTZ_USER`;
CREATE TABLE `XXL_CUSTOM_QRTZ_USER` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL COMMENT '类型',
  `user_name` varchar(128) NOT NULL COMMENT '用户名',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of XXL_CUSTOM_QRTZ_USER
-- ----------------------------
BEGIN;
INSERT INTO `XXL_CUSTOM_QRTZ_USER` VALUES (1, 1, 'admin', '21232f297a57a5a743894a0e4a801fc3', '2018-05-23 09:04:17');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
