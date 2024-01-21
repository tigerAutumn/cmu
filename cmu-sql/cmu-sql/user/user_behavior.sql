/*
 Navicat Premium Data Transfer

 Source Server         : ppl-xxx1
 Source Server Type    : MySQL
 Source Server Version : 50644
 Source Host           : 192.168.102.36:3306
 Source Schema         : user

 Target Server Type    : MySQL
 Target Server Version : 50644
 File Encoding         : 65001

 Date: 01/07/2019 14:57:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_behavior
-- ----------------------------
DROP TABLE IF EXISTS `user_behavior`;
CREATE TABLE `user_behavior` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '标识',
  `category` varchar(20) NOT NULL COMMENT '行为类别',
  `name` varchar(50) NOT NULL COMMENT '行为名称',
  `description` varchar(50) NOT NULL COMMENT '行为说明',
  `has_success_monitor` tinyint(1) NOT NULL DEFAULT '1' COMMENT '操作成功是否监控',
  `has_failure_monitor` tinyint(1) NOT NULL DEFAULT '1' COMMENT '操作错误是否监控',
  `max_retry_limit` int(10) NOT NULL DEFAULT '5' COMMENT '操作最大重试次数',
  `duration_of_freezing` int(10) NOT NULL DEFAULT '120' COMMENT '冻结持续时间(单位:秒)表示当操作达到最大重试次数时,冻结多长时间后可以继续操作\n',
  `notice_template_code` varchar(100) NOT NULL COMMENT '当操作需要发送通知信息(如短信，邮件)时该操作对应的通知模板代号\n',
  `need_login` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否需要登录态(0不需要，1需要)',
  `check_rule` varchar(50) NOT NULL DEFAULT 'single' COMMENT '是否同时校验谷歌，手机和邮箱all:全部，single:单独验证，priority:按照谷歌，邮箱和手机的优先级选择其一',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3093 DEFAULT CHARSET=utf8 COMMENT='用户行为表';

-- ----------------------------
-- Records of user_behavior
-- ----------------------------
BEGIN;
INSERT INTO `user_behavior` VALUES (2, 'users', 'register_email', '邮箱注册', 1, 1, 5, 120, 'MAIL_USERS_REGISTER', 1, 'single', '2018-04-14 08:09:59', '2018-04-18 05:37:11');
INSERT INTO `user_behavior` VALUES (3, 'users', 'register_mobile', '手机注册', 1, 1, 5, 120, 'SMS_USERS_REGISTER_PHONE', 1, 'single', '2018-04-14 08:49:13', '2018-04-18 05:45:39');
INSERT INTO `user_behavior` VALUES (5, 'users', 'register_check_username', '用户名检查', 1, 1, 5, 120, '', 1, 'single', '2018-04-14 08:49:22', '2018-04-14 08:49:22');
INSERT INTO `user_behavior` VALUES (6, 'users', 'login', '用户登陆', 1, 1, 5, 120, '', 1, 'single', '2018-04-14 08:09:59', '2018-04-14 08:09:59');
INSERT INTO `user_behavior` VALUES (7, 'users', 'login_step2_auth', '登陆二次验证', 1, 1, 5, 120, '', 1, 'single', '2018-04-14 08:49:28', '2018-04-14 08:49:28');
INSERT INTO `user_behavior` VALUES (8, 'users', 'login_pwd_modify', '登陆密码修改', 1, 1, 5, 120, '', 1, 'single', '2018-04-14 08:49:38', '2018-04-14 08:49:38');
INSERT INTO `user_behavior` VALUES (9, 'users', 'login_pwd_reset', '登陆密码找回(重置)', 1, 1, 5, 120, 'MAIL_USERS_LOGIN_RESETPWD,SMS_USERS_LOGIN_RESETPWD', 1, 'all', '2018-04-14 08:49:40', '2018-04-17 07:51:31');
INSERT INTO `user_behavior` VALUES (10, 'users', 'mobile_bind', '手机绑定', 1, 1, 5, 120, 'SMS_USERS_PHONE_BIND', 1, 'single', '2018-04-14 08:11:56', '2018-04-17 07:44:05');
INSERT INTO `user_behavior` VALUES (11, 'users', 'mobile_modify', '手机修改', 1, 1, 5, 120, 'SMS_USERS_PHONE_MODIFY', 1, 'single', '2018-04-14 08:50:08', '2018-04-17 07:43:56');
INSERT INTO `user_behavior` VALUES (12, 'users', 'mobile_verification_close', '关闭手机验证', 1, 1, 5, 120, 'SMS_USERS_PHONE_MODIFY', 1, 'single', '2018-04-14 00:50:08', '2018-04-16 23:43:56');
INSERT INTO `user_behavior` VALUES (13, 'users', 'email_bind', '邮箱绑定', 1, 1, 5, 120, 'MAIL_USERS_BIND', 1, 'single', '2018-04-14 08:09:59', '2018-04-17 07:44:23');
INSERT INTO `user_behavior` VALUES (14, 'users', 'email_active', '邮箱激活', 1, 1, 5, 120, 'MAIL_USERS_ACTIVE', 1, 'single', '2018-04-14 08:09:59', '2018-04-17 07:44:38');
INSERT INTO `user_behavior` VALUES (15, 'users', 'email_update', '邮箱修改', 1, 1, 5, 120, 'MAIL_USERS_UPDATE', 1, 'single', '2018-04-14 08:09:59', '2018-04-17 07:45:32');
INSERT INTO `user_behavior` VALUES (16, 'users', 'email_anti_code', '邮箱防钓鱼码', 1, 1, 5, 120, '', 1, 'single', '2018-04-14 08:50:28', '2018-04-14 08:50:28');
INSERT INTO `user_behavior` VALUES (17, 'users', 'google_code_bind', '谷歌绑定', 1, 1, 5, 120, 'MAIL_USERS_GOOGLE_BIND,SMS_USERS_GOOGLE_BIND', 1, 'all', '2018-04-14 08:50:36', '2018-04-17 07:58:11');
INSERT INTO `user_behavior` VALUES (18, 'users', 'google_code_reset', '谷歌重置', 1, 1, 5, 120, 'MAIL_USERS_GOOGLE_RESET,SMS_USERS_GOOGLE_RESET', 1, 'all', '2018-04-14 08:50:44', '2018-04-17 07:58:17');
INSERT INTO `user_behavior` VALUES (19, 'users', 'google_verification_close', '关闭谷歌验证', 1, 1, 5, 120, 'MAIL_USERS_GOOGLE_RESET,SMS_USERS_GOOGLE_RESET', 1, 'all', '2018-04-14 00:50:44', '2018-04-16 23:58:17');
INSERT INTO `user_behavior` VALUES (21, 'users', 'trade_pwd_set', '资金密码设置', 1, 1, 5, 120, '', 1, 'single', '2018-04-14 08:51:20', '2018-04-14 08:51:20');
INSERT INTO `user_behavior` VALUES (22, 'users', 'trade_reset_pwd', '资金密码找回（重置）', 1, 1, 5, 120, '', 1, 'single', '2018-04-14 08:51:26', '2018-04-14 08:51:26');
INSERT INTO `user_behavior` VALUES (23, 'users', 'trade_pwd_modify', '资金密码修改', 1, 1, 5, 120, '', 1, 'single', '2018-04-14 08:51:32', '2018-04-14 08:51:32');
INSERT INTO `user_behavior` VALUES (24, 'users', 'trade_pwd_frequency', '资金密码输入频次', 1, 1, 5, 120, '', 1, 'single', '2018-04-14 08:51:39', '2018-04-14 08:51:39');
INSERT INTO `user_behavior` VALUES (25, 'users', 'api_key_apply', 'api创建', 1, 1, 5, 120, '', 1, 'single', '2018-04-14 08:51:48', '2018-04-14 08:51:48');
INSERT INTO `user_behavior` VALUES (26, 'users', 'api_key_detail', 'api查看详情', 1, 1, 5, 120, '', 1, 'single', '2018-04-14 08:51:53', '2018-04-14 08:51:53');
INSERT INTO `user_behavior` VALUES (27, 'users', 'api_key_modify', 'api修改', 1, 1, 5, 120, '', 1, 'single', '2018-04-14 08:51:59', '2018-04-14 08:51:59');
INSERT INTO `user_behavior` VALUES (28, 'users', 'api_key_delete', 'api删除', 1, 1, 5, 120, '', 1, 'single', '2018-04-14 08:52:05', '2018-04-14 08:52:05');
INSERT INTO `user_behavior` VALUES (30, 'users', 'sub_account_add', '子账户添加', 1, 1, 5, 120, '', 1, 'single', '2018-04-14 08:52:20', '2018-04-14 08:52:20');
INSERT INTO `user_behavior` VALUES (31, 'users', 'sub_account_delete', '子账户删除', 1, 1, 5, 120, '', 1, 'single', '2018-04-14 08:52:27', '2018-04-14 08:52:27');
INSERT INTO `user_behavior` VALUES (32, 'users', 'sub_account_pwd_modify', '子账户重置密码', 1, 1, 5, 120, '', 1, 'single', '2018-04-14 08:52:36', '2018-04-14 08:52:36');
INSERT INTO `user_behavior` VALUES (33, 'users', 'sub_account_freeze', '子账户冻结', 1, 1, 5, 120, '', 1, 'single', '2018-04-14 08:52:40', '2018-04-14 08:52:40');
INSERT INTO `user_behavior` VALUES (34, 'users', 'sub_account_remark', '子账修改备注', 1, 1, 5, 120, '', 1, 'single', '2018-04-14 08:52:44', '2018-04-14 08:52:44');
INSERT INTO `user_behavior` VALUES (35, 'users', 'sub_account_transfer', '子账户资金划转', 1, 1, 5, 120, '', 1, 'single', '2018-04-14 08:52:47', '2018-04-14 08:52:47');
INSERT INTO `user_behavior` VALUES (36, 'users', 'withdraw_asset', '提币', 1, 1, 5, 120, '', 1, 'all', '2018-04-14 08:52:47', '2018-07-12 11:25:03');
INSERT INTO `user_behavior` VALUES (3092, 'users', 'pointcard_transfer', '点卡转让', 1, 1, 5, 120, '', 1, 'single', '2018-07-06 16:52:47', '2018-07-06 16:24:38');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
