/*
 Navicat Premium Data Transfer

 Source Server         : ppl-xxx1
 Source Server Type    : MySQL
 Source Server Version : 50644
 Source Host           : 192.168.102.36:3306
 Source Schema         : extra

 Target Server Type    : MySQL
 Target Server Version : 50644
 File Encoding         : 65001

 Date: 01/07/2019 14:53:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ai_group_rooms
-- ----------------------------
DROP TABLE IF EXISTS `ai_group_rooms`;
CREATE TABLE `ai_group_rooms` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `project_id` bigint(20) DEFAULT '0' COMMENT '项目id',
  `classify` varchar(64) NOT NULL COMMENT '分类：微信，QQ和telegram',
  `group_user_name` varchar(128) NOT NULL DEFAULT '' COMMENT '群组name',
  `group_nick_name` varchar(128) NOT NULL DEFAULT '' COMMENT '群组名称',
  `group_member_count` int(11) NOT NULL DEFAULT '0' COMMENT '群组人数',
  `self_display_name` varchar(128) NOT NULL DEFAULT '' COMMENT '自己的别名',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `classify_group_user_name` (`classify`,`group_user_name`) USING BTREE COMMENT '分类,群组name唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='群组表';

-- ----------------------------
-- Table structure for ai_group_statistics
-- ----------------------------
DROP TABLE IF EXISTS `ai_group_statistics`;
CREATE TABLE `ai_group_statistics` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `project_id` bigint(20) DEFAULT '0' COMMENT '项目id',
  `classify` varchar(64) NOT NULL COMMENT '分类：微信，QQ和telegram',
  `group` varchar(128) NOT NULL COMMENT '群组',
  `msg_count` int(11) NOT NULL DEFAULT '0' COMMENT '消息条数',
  `msg_char_count` bigint(20) NOT NULL DEFAULT '0' COMMENT '消息总字符数',
  `user_count` int(11) NOT NULL DEFAULT '0' COMMENT '活跃用户数',
  `positive` int(11) NOT NULL DEFAULT '0' COMMENT '正面消息',
  `normal` int(11) NOT NULL DEFAULT '0' COMMENT '普通消息',
  `negative` int(11) NOT NULL DEFAULT '0' COMMENT '负面消息',
  `minute` int(11) NOT NULL DEFAULT '0' COMMENT '统计分钟间隔',
  `date` varchar(64) NOT NULL COMMENT '统计时间',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='群组信息统计表';

-- ----------------------------
-- Table structure for ai_project
-- ----------------------------
DROP TABLE IF EXISTS `ai_project`;
CREATE TABLE `ai_project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(64) NOT NULL COMMENT '币种编号',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `weibo` varchar(500) DEFAULT NULL COMMENT '微博地址',
  `wechat_mp` varchar(500) DEFAULT NULL COMMENT '微信公众号',
  `facebook` varchar(500) DEFAULT NULL COMMENT 'facebook地址',
  `twitter` varchar(500) DEFAULT NULL COMMENT 'twitter地址',
  `github` varchar(500) DEFAULT NULL COMMENT '项目地址',
  `wechat` varchar(500) DEFAULT NULL COMMENT '微信群',
  `qq` varchar(500) DEFAULT NULL COMMENT 'qq群',
  `telegram` varchar(500) DEFAULT NULL COMMENT 'telegram群',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目信息基础配置表';

-- ----------------------------
-- Table structure for ai_project_info
-- ----------------------------
DROP TABLE IF EXISTS `ai_project_info`;
CREATE TABLE `ai_project_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `project_id` bigint(20) NOT NULL COMMENT '项目id',
  `type` varchar(20) NOT NULL COMMENT '类型,wechatmp,weibo,facebook,twitter,github',
  `title` varchar(128) DEFAULT NULL COMMENT '标题',
  `url` varchar(128) DEFAULT NULL COMMENT 'url地址',
  `content` varchar(2000) DEFAULT NULL COMMENT '内容',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_type_title_url` (`type`,`title`,`url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目信息采集信息表';

-- ----------------------------
-- Table structure for cms_app_download_page
-- ----------------------------
DROP TABLE IF EXISTS `cms_app_download_page`;
CREATE TABLE `cms_app_download_page` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` varchar(500) NOT NULL DEFAULT '' COMMENT '名称',
  `logo_img` varchar(1000) NOT NULL DEFAULT '' COMMENT 'logo图片',
  `locale` varchar(20) NOT NULL DEFAULT '' COMMENT '语言',
  `uid` varchar(100) NOT NULL DEFAULT '' COMMENT '标识码',
  `link` varchar(1000) NOT NULL DEFAULT '' COMMENT '下载页链接',
  `android_url` varchar(1000) NOT NULL DEFAULT '' COMMENT 'Android下载链接',
  `ios_url` varchar(1000) NOT NULL DEFAULT '' COMMENT 'iOS下载链接',
  `status` int(5) NOT NULL DEFAULT '0' COMMENT '状态：0已保存 1已发布 2已下架 -1已删除',
  `template_id` int(11) NOT NULL DEFAULT '0' COMMENT '文章模板',
  `first_title` varchar(500) NOT NULL DEFAULT '' COMMENT '第一部分：标题',
  `first_intro` varchar(1000) NOT NULL DEFAULT '' COMMENT '第一部分：简介',
  `first_img` varchar(1000) NOT NULL DEFAULT '' COMMENT '第一部分：图片',
  `second_title` varchar(500) NOT NULL DEFAULT '' COMMENT '第二部分：标题',
  `second_intro` varchar(1000) NOT NULL DEFAULT '' COMMENT '第二部分：简介',
  `second_img` varchar(1000) NOT NULL DEFAULT '' COMMENT '第二部分：图片',
  `third_feature_1` varchar(500) NOT NULL DEFAULT '' COMMENT '第三部分：特点',
  `third_feature_2` varchar(500) NOT NULL DEFAULT '' COMMENT '第三部分：特点',
  `third_feature_3` varchar(500) NOT NULL DEFAULT '' COMMENT '第三部分：特点',
  `third_feature_4` varchar(500) NOT NULL DEFAULT '' COMMENT '第三部分：特点',
  `third_intro_1` varchar(1000) NOT NULL DEFAULT '' COMMENT '第三部分：简介',
  `third_intro_2` varchar(1000) NOT NULL DEFAULT '' COMMENT '第三部分：简介',
  `third_intro_3` varchar(1000) NOT NULL DEFAULT '' COMMENT '第三部分：简介',
  `third_intro_4` varchar(1000) NOT NULL DEFAULT '' COMMENT '第三部分：简介',
  `third_img_1` varchar(1000) NOT NULL DEFAULT '' COMMENT '第三部分：图片',
  `third_img_2` varchar(1000) NOT NULL DEFAULT '' COMMENT '第三部分：图片',
  `third_img_3` varchar(1000) NOT NULL DEFAULT '' COMMENT '第三部分：图片',
  `third_img_4` varchar(1000) NOT NULL DEFAULT '' COMMENT '第三部分：图片',
  `publisher_id` int(11) NOT NULL COMMENT '发布用户id对应boss系统的管理员id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `broker_id` int(11) DEFAULT '1' COMMENT '券商ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='App下载页表';

-- ----------------------------
-- Table structure for cms_banner_notice
-- ----------------------------
DROP TABLE IF EXISTS `cms_banner_notice`;
CREATE TABLE `cms_banner_notice` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` varchar(20) DEFAULT '' COMMENT 'banner在当前locale语言下唯一编号',
  `type` int(5) NOT NULL DEFAULT '0' COMMENT '类型：1banner 2公告 3轮播图',
  `locale` varchar(50) DEFAULT NULL,
  `title` varchar(100) NOT NULL DEFAULT '' COMMENT '标题',
  `text` varchar(200) DEFAULT NULL COMMENT '文本',
  `image_url` varchar(2000) NOT NULL DEFAULT '' COMMENT '图片地址',
  `original_image_url` varchar(2000) DEFAULT NULL COMMENT '原始图片',
  `link` varchar(2000) NOT NULL DEFAULT '' COMMENT '链接地址',
  `platform` tinyint(4) NOT NULL DEFAULT '0' COMMENT '展示平台 0默认 1PC 2IOS 3android',
  `start_time` timestamp NULL DEFAULT NULL COMMENT '发布时间',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '下架时间',
  `status` int(5) NOT NULL DEFAULT '0' COMMENT '状态：0已保存 1已发布 2已下架 -1已删除',
  `sort` int(5) NOT NULL DEFAULT '0' COMMENT '排序',
  `publish_user` varchar(100) DEFAULT NULL COMMENT '发布人',
  `rnd_num` varchar(20) NOT NULL DEFAULT '' COMMENT '随机数：时间戳+随机数(4位数字)',
  `share_title` varchar(50) NOT NULL DEFAULT '' COMMENT '分享标题',
  `share_text` varchar(50) NOT NULL DEFAULT '',
  `share_image_url` varchar(2000) DEFAULT NULL,
  `share_link` varchar(500) NOT NULL DEFAULT '',
  `created_date` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `broker_id` int(11) DEFAULT '1' COMMENT '券商ID',
  PRIMARY KEY (`id`,`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='首页banner广告表';

-- ----------------------------
-- Table structure for cms_dapps_info
-- ----------------------------
DROP TABLE IF EXISTS `cms_dapps_info`;
CREATE TABLE `cms_dapps_info` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `locale` varchar(50) NOT NULL DEFAULT '' COMMENT '语言',
  `title` varchar(100) NOT NULL DEFAULT '' COMMENT 'dapps名称',
  `image_url` varchar(200) NOT NULL DEFAULT '' COMMENT '封面地址',
  `app_link` varchar(200) NOT NULL DEFAULT '' COMMENT 'app链接',
  `pc_link` varchar(200) NOT NULL DEFAULT '' COMMENT 'pc链接',
  `product` varchar(50) NOT NULL DEFAULT '' COMMENT '交易币对',
  `currency` varchar(50) NOT NULL DEFAULT '' COMMENT '法币币种',
  `status` int(5) NOT NULL DEFAULT '0' COMMENT '状态：0未上线 1已上线 2预发',
  `sort` int(5) NOT NULL DEFAULT '10' COMMENT '展示顺序',
  `text` varchar(1000) NOT NULL DEFAULT '' COMMENT '解释文案',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `broker_id` int(11) DEFAULT '1' COMMENT '券商ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_locale_title` (`locale`,`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='dapps应用程序表';

-- ----------------------------
-- Table structure for cms_i18n_language
-- ----------------------------
DROP TABLE IF EXISTS `cms_i18n_language`;
CREATE TABLE `cms_i18n_language` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `code` varchar(10) NOT NULL DEFAULT '' COMMENT '语言代号参考BCP47标准',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '语言名称',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='本地化语言表';

-- ----------------------------
-- Table structure for cms_i18n_message
-- ----------------------------
DROP TABLE IF EXISTS `cms_i18n_message`;
CREATE TABLE `cms_i18n_message` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `msg_code_id` int(11) NOT NULL COMMENT '本地化文本Code ID',
  `locale` varchar(10) NOT NULL DEFAULT '' COMMENT '本地化语言代号(zh-cn/en-us等)',
  `msg_code` varchar(100) NOT NULL DEFAULT '' COMMENT '本地化文本Code',
  `msg_text` varchar(1000) NOT NULL DEFAULT '' COMMENT '本地化文本内容',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_locale_code_id` (`locale`,`msg_code_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='本地化文本表';

-- ----------------------------
-- Table structure for cms_i18n_message_code
-- ----------------------------
DROP TABLE IF EXISTS `cms_i18n_message_code`;
CREATE TABLE `cms_i18n_message_code` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `parent_id` int(11) NOT NULL COMMENT '父节点id',
  `code` varchar(100) NOT NULL DEFAULT '' COMMENT 'Message唯一编号',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `type` varchar(10) NOT NULL DEFAULT '' COMMENT '节点类型(cate类别节点|leaf叶子节点)',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_pid_code` (`parent_id`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='本地化文本编码表';

-- ----------------------------
-- Table structure for cms_img_info
-- ----------------------------
DROP TABLE IF EXISTS `cms_img_info`;
CREATE TABLE `cms_img_info` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` varchar(500) NOT NULL DEFAULT '' COMMENT '名称',
  `img_url` varchar(1000) NOT NULL DEFAULT '' COMMENT '图片',
  `publisher_id` int(11) NOT NULL COMMENT '发布用户id对应boss系统的管理员id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='cms图片表';

-- ----------------------------
-- Table structure for cms_news
-- ----------------------------
DROP TABLE IF EXISTS `cms_news`;
CREATE TABLE `cms_news` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `template_id` int(11) NOT NULL COMMENT '文章模版id',
  `publisher_id` int(11) NOT NULL COMMENT '发布用户id对应boss系统的管理员id',
  `uid` varchar(64) NOT NULL DEFAULT '' COMMENT '文章唯一编号',
  `locale` varchar(10) NOT NULL DEFAULT '' COMMENT '本地化语言代号(zh-cn/en-us等）',
  `title` varchar(100) NOT NULL DEFAULT '' COMMENT '文章标题',
  `content` text NOT NULL COMMENT '文章内容',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `category_id` int(11) NOT NULL COMMENT '文章类别id',
  `link` varchar(1000) NOT NULL DEFAULT '' COMMENT '访问链接',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_uid_locale` (`uid`,`locale`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='新闻文章表';

-- ----------------------------
-- Table structure for cms_news_category
-- ----------------------------
DROP TABLE IF EXISTS `cms_news_category`;
CREATE TABLE `cms_news_category` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `parent_id` int(11) NOT NULL COMMENT '父节点id',
  `locale` varchar(10) NOT NULL DEFAULT '' COMMENT '语言(zh-cn\\en-us等)',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_parent_locale_name` (`parent_id`,`locale`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='新闻或文章类别表';

-- ----------------------------
-- Table structure for cms_news_template
-- ----------------------------
DROP TABLE IF EXISTS `cms_news_template`;
CREATE TABLE `cms_news_template` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '模板名称',
  `content` text NOT NULL COMMENT '模板内容',
  `memo` varchar(50) NOT NULL COMMENT '模板备注',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='新闻模板表';

-- ----------------------------
-- Table structure for cms_push
-- ----------------------------
DROP TABLE IF EXISTS `cms_push`;
CREATE TABLE `cms_push` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `locale` varchar(16) NOT NULL DEFAULT '' COMMENT '本地化语言代号(zh-cn/en-us等)',
  `type` varchar(16) NOT NULL DEFAULT '' COMMENT '类型',
  `title` varchar(512) NOT NULL DEFAULT '' COMMENT '标题',
  `link` varchar(128) NOT NULL DEFAULT '' COMMENT '链接',
  `platform` varchar(16) NOT NULL DEFAULT '' COMMENT '平台',
  `push_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '推送时间',
  `status` varchar(16) NOT NULL DEFAULT '' COMMENT '状态(等待推送/已推送)',
  `operator` varchar(16) NOT NULL DEFAULT '' COMMENT '发布人',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息推送表';

-- ----------------------------
-- Table structure for cms_version_info
-- ----------------------------
DROP TABLE IF EXISTS `cms_version_info`;
CREATE TABLE `cms_version_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `platform` int(5) NOT NULL DEFAULT '0' COMMENT '客户端对应的平台：1-iOS，2-Android',
  `new_version` varchar(20) NOT NULL DEFAULT '' COMMENT '当前最新版本号',
  `force_update` int(5) NOT NULL DEFAULT '0' COMMENT '是否强制升级：0不提醒 1仅提醒升级 2 强制升级',
  `content` text NOT NULL COMMENT '更新内容',
  `download_url` varchar(500) NOT NULL DEFAULT '' COMMENT '下载链接',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `broker_id` int(11) DEFAULT '1' COMMENT '券商ID',
  `locale` varchar(10) NOT NULL DEFAULT '' COMMENT '本地化语言代号(zh-cn/en-us等）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='版本信息表';

-- ----------------------------
-- Table structure for currency_base_info
-- ----------------------------
DROP TABLE IF EXISTS `currency_base_info`;
CREATE TABLE `currency_base_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增id',
  `code` varchar(20) NOT NULL DEFAULT '' COMMENT '币种唯一代码（币种简称）',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '币种名称（币种全称）',
  `symbol` varchar(20) NOT NULL DEFAULT '' COMMENT '币种符号',
  `img_url` varchar(500) NOT NULL DEFAULT '' COMMENT '币种图标',
  `issue_date` varchar(20) NOT NULL DEFAULT '' COMMENT '币种发行日期',
  `issue_price` varchar(128) NOT NULL DEFAULT '' COMMENT '币种发行价格',
  `max_supply` bigint(20) NOT NULL DEFAULT '0' COMMENT '币种发行总量',
  `circulating_supply` bigint(20) NOT NULL DEFAULT '0' COMMENT '币种当前流通数量',
  `offical_website` varchar(500) NOT NULL DEFAULT '' COMMENT '币种官网地址',
  `explorer` varchar(1000) NOT NULL DEFAULT '' COMMENT '币种区块浏览器地址，多个之间用英文逗号隔开',
  `source_code_url` varchar(500) NOT NULL DEFAULT '' COMMENT '源代码',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，1-待审核，2-已发布，3-已下架',
  `publisher_id` int(11) NOT NULL DEFAULT '0' COMMENT '发布用户id',
  `sort` int(11) NOT NULL DEFAULT '10' COMMENT '排序，可用于置顶，越小排序越靠前',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='全球数字货币基本信息表';

-- ----------------------------
-- Table structure for currency_detail_info
-- ----------------------------
DROP TABLE IF EXISTS `currency_detail_info`;
CREATE TABLE `currency_detail_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增id',
  `code` varchar(20) NOT NULL DEFAULT '' COMMENT '币种唯一代码（币种简称）',
  `locale` varchar(10) NOT NULL DEFAULT '' COMMENT '语言(zh-cn/en-us等)',
  `concept` varchar(500) NOT NULL DEFAULT '' COMMENT '币种概念',
  `white_paper` varchar(500) NOT NULL DEFAULT '' COMMENT '币种白皮书地址',
  `introduction` text NOT NULL COMMENT '币种详细介绍',
  `telegram` varchar(500) NOT NULL DEFAULT '' COMMENT 'Telegram',
  `facebook` varchar(500) NOT NULL DEFAULT '' COMMENT 'Facebook',
  `twitter` varchar(500) NOT NULL DEFAULT '' COMMENT 'Twitter',
  `weibo` varchar(500) NOT NULL DEFAULT '' COMMENT 'weibo',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，1-待审核，2-已发布，3-已下架',
  `publisher_id` int(11) NOT NULL DEFAULT '0' COMMENT '发布用户id',
  `sort` int(11) NOT NULL DEFAULT '10' COMMENT '排序，可用于置顶，越小排序越靠前',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `coin_market_cap_url` varchar(500) DEFAULT '' COMMENT '数字货币行情链接',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code_locale` (`code`,`locale`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='全球数字货币详细信息表';

-- ----------------------------
-- Table structure for currency_permission
-- ----------------------------
DROP TABLE IF EXISTS `currency_permission`;
CREATE TABLE `currency_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增id',
  `code` varchar(20) NOT NULL DEFAULT '' COMMENT '币种唯一代码（币种简称）',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `name` varchar(200) NOT NULL DEFAULT '' COMMENT '姓名',
  `organization` varchar(200) NOT NULL DEFAULT '' COMMENT '组织机构',
  `mobile` varchar(20) NOT NULL DEFAULT '' COMMENT '手机',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态，0-禁用，1-启用',
  `publisher_id` int(11) NOT NULL DEFAULT '0' COMMENT '发布用户id对应boss系统的管理员id',
  `sort` int(11) NOT NULL DEFAULT '10' COMMENT '排序，可用于置顶，越小排序越靠前',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='币种wiki用户权限管理表';

-- ----------------------------
-- Table structure for currency_progress_info
-- ----------------------------
DROP TABLE IF EXISTS `currency_progress_info`;
CREATE TABLE `currency_progress_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增id',
  `code` varchar(20) NOT NULL DEFAULT '' COMMENT '币种唯一代码（币种简称）',
  `locale` varchar(10) NOT NULL DEFAULT '' COMMENT '语言(zh-cn/en-us等)',
  `publish_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '日期',
  `content` text NOT NULL COMMENT '更新内容',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，1-待审核，2-已发布，3-已下架',
  `publisher_id` int(11) NOT NULL DEFAULT '0' COMMENT '发布用户id',
  `sort` int(11) NOT NULL DEFAULT '10' COMMENT '排序，可用于置顶，越小排序越靠前',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目进展信息表';

-- ----------------------------
-- Table structure for currency_tag
-- ----------------------------
DROP TABLE IF EXISTS `currency_tag`;
CREATE TABLE `currency_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增id',
  `code` varchar(100) NOT NULL DEFAULT '' COMMENT '币种唯一代码（币种简称）',
  `tag_category_code` varchar(100) NOT NULL DEFAULT '' COMMENT '标签分类编码',
  `tag_info_code` varchar(100) NOT NULL DEFAULT '' COMMENT '标签编码',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='币种标签表';

-- ----------------------------
-- Table structure for currency_trend_info
-- ----------------------------
DROP TABLE IF EXISTS `currency_trend_info`;
CREATE TABLE `currency_trend_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增id',
  `code` varchar(20) NOT NULL DEFAULT '' COMMENT '币种唯一代码（币种简称）',
  `locale` varchar(10) NOT NULL DEFAULT '' COMMENT '语言(zh-cn/en-us等)',
  `title` varchar(500) NOT NULL DEFAULT '' COMMENT '标题',
  `content` text NOT NULL COMMENT '内容',
  `link` varchar(500) NOT NULL DEFAULT '' COMMENT '链接',
  `publish_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '日期',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，1-待审核，2-已发布，3-已下架',
  `publisher_id` int(11) NOT NULL DEFAULT '0' COMMENT '发布用户id',
  `sort` int(11) NOT NULL DEFAULT '10' COMMENT '排序，可用于置顶，越小排序越靠前',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目动态信息表';

-- ----------------------------
-- Table structure for customer_work_order
-- ----------------------------
DROP TABLE IF EXISTS `customer_work_order`;
CREATE TABLE `customer_work_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '工单表',
  `menu_id` int(20) NOT NULL DEFAULT '0' COMMENT '菜单id',
  `group_id` int(20) NOT NULL DEFAULT '0' COMMENT '组别id',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '状态 0:未分配 1:已受理 2:处理中 3:待确认 4评价完成',
  `unfold_count` int(4) NOT NULL DEFAULT '0' COMMENT '打开次数 0',
  `urgent` int(2) NOT NULL DEFAULT '0' COMMENT '0：默认 1：紧急',
  `site_type` int(2) NOT NULL DEFAULT '0' COMMENT '0:Default',
  `locale` varchar(10) NOT NULL DEFAULT '0' COMMENT 'zh-cn/en-us',
  `from_type` int(2) NOT NULL DEFAULT '0' COMMENT '0: 客户 1: 运营',
  `is_show` int(2) NOT NULL DEFAULT '1' COMMENT '0: 不对前端显示 1: 显示',
  `source` int(4) NOT NULL DEFAULT '0' COMMENT '工单来源 0：系统派发 1：转发',
  `content` varchar(2000) NOT NULL DEFAULT '' COMMENT '问题描述',
  `remit_name` varchar(50) NOT NULL DEFAULT '' COMMENT '汇款人姓名',
  `remit_card_number` varchar(50) NOT NULL DEFAULT '' COMMENT '汇款人卡号',
  `remit_alipay` varchar(50) NOT NULL DEFAULT '' COMMENT '支付宝账号',
  `remit_amount` varchar(50) NOT NULL DEFAULT '' COMMENT '汇款人金额',
  `coin_type` varchar(50) NOT NULL DEFAULT '' COMMENT '币种',
  `withdraw_address` varchar(255) NOT NULL DEFAULT '' COMMENT '提币地址',
  `withdraw_number` decimal(32,2) NOT NULL DEFAULT '0.00' COMMENT '提币数量',
  `answer_status` int(2) NOT NULL DEFAULT '0' COMMENT '绑定手机可否接听电话状态 0:不可接听 1:可接听',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户姓名',
  `user_email` varchar(50) NOT NULL DEFAULT '' COMMENT '用户邮箱',
  `user_phone` varchar(50) NOT NULL DEFAULT '' COMMENT '手机号',
  `satisfaction` int(2) NOT NULL DEFAULT '0' COMMENT '满意度 -1:不满意  1:满意',
  `response_time` timestamp NULL DEFAULT NULL COMMENT '首次回复时间',
  `solve_time` timestamp NULL DEFAULT NULL COMMENT '解决时间',
  `handle_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '首次回复减去受理时间',
  `dispose_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '解决时间减去受理时间',
  `comment` varchar(200) NOT NULL DEFAULT '' COMMENT '评价内容',
  `accept_time` timestamp NULL DEFAULT NULL COMMENT '受理时间',
  `last_reply_time` timestamp NULL DEFAULT NULL COMMENT '最后一次回复时间',
  `fresh` int(4) NOT NULL DEFAULT '0' COMMENT '是否为新版本数据 1:是；0:否',
  `admin_user_id` int(20) NOT NULL DEFAULT '0' COMMENT '处理人',
  `admin_account` varchar(64) NOT NULL COMMENT '处理人账号',
  `admin_name` varchar(50) NOT NULL COMMENT '处理人姓名',
  `create_admin_user_id` int(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_work_order_group_id_status` (`group_id`,`status`),
  KEY `idx_work_order_admin_user_id_status` (`admin_user_id`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工单表';

-- ----------------------------
-- Table structure for customer_work_order_attachment
-- ----------------------------
DROP TABLE IF EXISTS `customer_work_order_attachment`;
CREATE TABLE `customer_work_order_attachment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '工单附件表',
  `work_order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '工单id或回复id',
  `type` int(2) NOT NULL DEFAULT '0' COMMENT '附件来源 0:工单 1:工单回复 ',
  `original_name` varchar(50) NOT NULL DEFAULT '' COMMENT '原文件名',
  `path` varchar(200) NOT NULL DEFAULT '' COMMENT '附件路径',
  `desc` varchar(200) NOT NULL DEFAULT '' COMMENT '描叙',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_work_order_attachment_obejct_id` (`work_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工单附件表';

-- ----------------------------
-- Table structure for customer_work_order_menu
-- ----------------------------
DROP TABLE IF EXISTS `customer_work_order_menu`;
CREATE TABLE `customer_work_order_menu` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '菜单表',
  `parent_id` int(20) NOT NULL DEFAULT '0' COMMENT '菜单父级id',
  `locale` varchar(10) NOT NULL DEFAULT '' COMMENT '本地化语言(zh-cn/en-us等)',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '菜单名称',
  `status` int(2) NOT NULL COMMENT '状态 0 启用 1 停用',
  `group_id` varchar(200) NOT NULL COMMENT '处理问题负责问题的组id',
  `admin_user_id` int(20) NOT NULL COMMENT '创建人id对应后台管理员id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工单菜单表';

-- ----------------------------
-- Table structure for customer_work_order_oplog
-- ----------------------------
DROP TABLE IF EXISTS `customer_work_order_oplog`;
CREATE TABLE `customer_work_order_oplog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '工单操作记录表',
  `work_order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '工单id',
  `group_id` int(20) NOT NULL,
  `current_admin_user_id` int(20) NOT NULL DEFAULT '0' COMMENT '处理人',
  `current_admin_account` varchar(50) NOT NULL DEFAULT '' COMMENT '处理人名称',
  `content` varchar(500) NOT NULL DEFAULT '' COMMENT '操作记录',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_work_order_operator_work_id` (`work_order_id`),
  KEY `idx_work_order_operator_admin_user_id` (`current_admin_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工单操作记录表';

-- ----------------------------
-- Table structure for customer_work_order_reply
-- ----------------------------
DROP TABLE IF EXISTS `customer_work_order_reply`;
CREATE TABLE `customer_work_order_reply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '工单回复表',
  `work_order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '工单问题表id',
  `admin_user_id` int(20) NOT NULL DEFAULT '0' COMMENT '处理人',
  `reply` varchar(1024) NOT NULL DEFAULT '' COMMENT '回复内容',
  `type` int(2) NOT NULL DEFAULT '0' COMMENT '类型 0:用户回复 1:客服回复',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_work_order_reply_work_id` (`work_order_id`),
  KEY `idx_admin_userid` (`admin_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工单回复表';

-- ----------------------------
-- Table structure for customer_work_order_template
-- ----------------------------
DROP TABLE IF EXISTS `customer_work_order_template`;
CREATE TABLE `customer_work_order_template` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `menu_id` int(20) NOT NULL DEFAULT '0' COMMENT '菜单id',
  `template` varchar(2000) NOT NULL DEFAULT '' COMMENT '问题模版',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='工单问题模版表';

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
-- Table structure for product_tag
-- ----------------------------
DROP TABLE IF EXISTS `product_tag`;
CREATE TABLE `product_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增id',
  `product_id` bigint(20) NOT NULL COMMENT '币对id',
  `product_code` varchar(100) NOT NULL DEFAULT '' COMMENT '币对唯一代码',
  `currency_code` varchar(100) NOT NULL DEFAULT '' COMMENT '币种唯一代码（币种简称）',
  `tag_category_code` varchar(100) NOT NULL DEFAULT '' COMMENT '标签分类编码',
  `tag_info_code` varchar(100) NOT NULL DEFAULT '' COMMENT '标签编码',
  `type` int(11) NOT NULL DEFAULT '2' COMMENT '类别，1-币种标签（继承过来的），2-币对标签',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='币对标签表';

-- ----------------------------
-- Table structure for project_apply_info
-- ----------------------------
DROP TABLE IF EXISTS `project_apply_info`;
CREATE TABLE `project_apply_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `token_info_id` bigint(20) NOT NULL COMMENT '项目id',
  `locale` varchar(10) NOT NULL DEFAULT '' COMMENT '语言(zh-cn/en-us等)',
  `company` varchar(512) NOT NULL DEFAULT '' COMMENT '公司名称',
  `website` varchar(128) NOT NULL DEFAULT '' COMMENT '官方网站',
  `white_paper` varchar(128) NOT NULL DEFAULT '' COMMENT '白皮书',
  `project_info` text NOT NULL COMMENT '项目简介',
  `project_stage` text NOT NULL COMMENT '项目阶段',
  `project_objective` text NOT NULL COMMENT '项目目标',
  `project_progress` text NOT NULL COMMENT '项目进展',
  `see` tinyint(4) NOT NULL DEFAULT '1' COMMENT '项目是否可以看到产品 0 未看到、1 看到',
  `github_url` varchar(128) NOT NULL DEFAULT '' COMMENT 'git hub url',
  `team` text NOT NULL COMMENT '团队成员',
  `team_counselor` text NOT NULL COMMENT '团队顾问',
  `fulltime_number` int(11) NOT NULL DEFAULT '0' COMMENT '全职人员',
  `no_fulltime_number` int(11) NOT NULL DEFAULT '0' COMMENT '非全职人员',
  `raise` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否举行过私募 0 否、1 是',
  `raise_total` varchar(128) NOT NULL DEFAULT '' COMMENT '私募总量',
  `raise_price` varchar(128) NOT NULL DEFAULT '' COMMENT '私募价格',
  `raise_invest` int(11) NOT NULL DEFAULT '0' COMMENT '私募投资参与者',
  `raise_ratio` varchar(128) NOT NULL DEFAULT '' COMMENT '私募比例',
  `raise_date` varchar(128) NOT NULL DEFAULT '' COMMENT '私募时间',
  `raise_rule` text NOT NULL COMMENT '私募规则',
  `ico` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否ico 0 否、1 是',
  `ico_total` varchar(128) NOT NULL DEFAULT '' COMMENT 'ico总量',
  `ico_price` varchar(128) NOT NULL DEFAULT '' COMMENT 'ico价格',
  `ico_invest` int(11) NOT NULL DEFAULT '0' COMMENT 'ico投资者',
  `ico_ratio` varchar(128) NOT NULL DEFAULT '' COMMENT 'ico比例',
  `ico_date` varchar(128) NOT NULL DEFAULT '' COMMENT 'ico时间',
  `ico_rule` text NOT NULL COMMENT 'ico规则',
  `telegram_link` varchar(128) NOT NULL DEFAULT '' COMMENT 'telegram link ',
  `telegram_link_members` varchar(128) NOT NULL DEFAULT '' COMMENT 'telegram link  members',
  `wechat_link` varchar(128) NOT NULL DEFAULT '' COMMENT 'wechat_link',
  `wechat_link_members` varchar(128) NOT NULL DEFAULT '' COMMENT 'wechat_link_members',
  `qq` varchar(64) NOT NULL DEFAULT '' COMMENT 'qq',
  `kakao_talk` varchar(64) NOT NULL DEFAULT '' COMMENT 'kakao talk',
  `twitter` varchar(128) NOT NULL DEFAULT '' COMMENT 'twitter',
  `facebook` varchar(128) NOT NULL DEFAULT '' COMMENT 'facebook',
  `weibo` varchar(128) NOT NULL DEFAULT '' COMMENT 'weibo',
  `reddit` varchar(128) NOT NULL DEFAULT '' COMMENT 'reddit',
  `others` varchar(128) NOT NULL DEFAULT '' COMMENT 'reddit',
  `wallet_type` int(4) NOT NULL DEFAULT '1' COMMENT '1:erc20,0:其他',
  `contract` varchar(128) NOT NULL DEFAULT '' COMMENT '合约地址',
  `wallet` text NOT NULL COMMENT '项目钱包的说明',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `company_position` varchar(200) NOT NULL DEFAULT '' COMMENT '公司职务',
  `product_address` varchar(1024) NOT NULL DEFAULT '' COMMENT '项目产品地址',
  `broker_id` int(11) DEFAULT '1' COMMENT '券商ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目信息表';

-- ----------------------------
-- Table structure for project_payment_record
-- ----------------------------
DROP TABLE IF EXISTS `project_payment_record`;
CREATE TABLE `project_payment_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `token_info_id` bigint(20) NOT NULL COMMENT '项目id',
  `currency_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '币种类型：1-CT保证金，2-糖果活动币',
  `amount` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '支付数量',
  `trade_no` varchar(100) NOT NULL DEFAULT '' COMMENT 'tradeNo',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `broker_id` int(11) DEFAULT '1' COMMENT '券商ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目支付记录，记录支付CT、糖果活动币的记录';

-- ----------------------------
-- Table structure for project_reject_reason
-- ----------------------------
DROP TABLE IF EXISTS `project_reject_reason`;
CREATE TABLE `project_reject_reason` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `token_info_id` bigint(20) NOT NULL COMMENT '项目id',
  `reason` varchar(500) NOT NULL DEFAULT '' COMMENT '拒绝原因',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目拒绝原因表';

-- ----------------------------
-- Table structure for project_token_info
-- ----------------------------
DROP TABLE IF EXISTS `project_token_info`;
CREATE TABLE `project_token_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '用户名称',
  `email` varchar(128) NOT NULL DEFAULT '' COMMENT '用户邮箱',
  `token` varchar(128) NOT NULL DEFAULT '' COMMENT '币种名称（币种全称）',
  `token_symbol` varchar(128) NOT NULL DEFAULT '' COMMENT '币种代码（币种简称）',
  `img_name` varchar(256) NOT NULL DEFAULT '' COMMENT '币种logo',
  `type` varchar(128) NOT NULL DEFAULT '' COMMENT '币种类型，zhuwangbi-主网币，daibi-代币',
  `issue` varchar(128) NOT NULL DEFAULT '' COMMENT '发行总量',
  `circulating` varchar(128) NOT NULL DEFAULT '' COMMENT '流通总量',
  `coinmarketcap` varchar(512) NOT NULL DEFAULT '' COMMENT 'coinmarketcap url',
  `online` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否上线交易所 0未上线、1上线',
  `exchange_name` varchar(512) NOT NULL DEFAULT '' COMMENT '交易所名称',
  `trade_volume` varchar(1024) NOT NULL DEFAULT '' COMMENT '24小时成交量',
  `price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '价格',
  `possess_users` int(11) NOT NULL DEFAULT '0' COMMENT '拥有用户数',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '项目审核状态 0:待审核 1:初始审核 2:待上线 3:已上线  -1:拒绝',
  `deposit` decimal(16,4) NOT NULL DEFAULT '0.0000' COMMENT '保证金数量',
  `token_number` decimal(16,4) NOT NULL DEFAULT '0.0000' COMMENT '代币糖果数量',
  `token_url` varchar(64) NOT NULL DEFAULT '' COMMENT '代币糖果地址',
  `contacts` varchar(64) NOT NULL DEFAULT '' COMMENT '商务对接人',
  `wechat` varchar(64) NOT NULL DEFAULT '' COMMENT '对接微信',
  `online_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '上线时间',
  `start_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '发币时间',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `mobile` varchar(100) NOT NULL DEFAULT '' COMMENT '联系电话',
  `deposit_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '保证金支付状态:0:未支付，1：支付',
  `sweets_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '糖果支付状态:0:未支付，1：支付',
  `broker_id` int(11) DEFAULT '1' COMMENT '券商ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目基础信息表';

-- ----------------------------
-- Table structure for rt_currency_detail
-- ----------------------------
DROP TABLE IF EXISTS `rt_currency_detail`;
CREATE TABLE `rt_currency_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `cid` varchar(50) DEFAULT NULL COMMENT '代币标识',
  `circulating_supply` bigint(20) DEFAULT NULL COMMENT '流通数量',
  `issue_time` varchar(100) DEFAULT NULL COMMENT '发行时间',
  `exchange_num` smallint(20) DEFAULT NULL COMMENT '上交易所数量',
  `total_circulation` varchar(50) DEFAULT NULL COMMENT '总发行量',
  `symbol` varchar(20) DEFAULT NULL COMMENT '币种简称',
  `issue_price` varchar(50) DEFAULT NULL COMMENT '发行价格',
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='rt代币详情信息';

-- ----------------------------
-- Table structure for rt_currency_rank
-- ----------------------------
DROP TABLE IF EXISTS `rt_currency_rank`;
CREATE TABLE `rt_currency_rank` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `cid` varchar(50) DEFAULT NULL COMMENT '代币标识',
  `symbol` varchar(20) DEFAULT NULL COMMENT '币种简称',
  `currency` varchar(100) DEFAULT NULL COMMENT '币种全称',
  `industry` varchar(100) DEFAULT NULL COMMENT '所属行业',
  `logo_url` varchar(100) DEFAULT NULL COMMENT 'logo图标',
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='rt代币排名信息';

-- ----------------------------
-- Table structure for rt_currency_score
-- ----------------------------
DROP TABLE IF EXISTS `rt_currency_score`;
CREATE TABLE `rt_currency_score` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `cid` varchar(50) DEFAULT NULL COMMENT '代币标识',
  `symbol` varchar(20) DEFAULT NULL COMMENT '币种简称',
  `tech_score` float DEFAULT NULL COMMENT '技术评分',
  `team_score` float DEFAULT NULL COMMENT '团队评分',
  `hype_score` float DEFAULT NULL COMMENT '社交得分',
  `rating` float DEFAULT NULL COMMENT '综合得分',
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='rt代币评分信息';

-- ----------------------------
-- Table structure for rt_currency_team
-- ----------------------------
DROP TABLE IF EXISTS `rt_currency_team`;
CREATE TABLE `rt_currency_team` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `cid` varchar(50) DEFAULT NULL COMMENT '代币标识',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `photo_url` varchar(255) DEFAULT NULL COMMENT '个人图片',
  `bio` varchar(100) DEFAULT NULL COMMENT '职位',
  `bio_score` int(10) DEFAULT NULL COMMENT '职位得分',
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='rt代币团队信息';

-- ----------------------------
-- Table structure for tag_category
-- ----------------------------
DROP TABLE IF EXISTS `tag_category`;
CREATE TABLE `tag_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增id',
  `locale` varchar(10) NOT NULL DEFAULT '' COMMENT '本地化语言代号(zh-cn/en-us等)',
  `code` varchar(100) NOT NULL DEFAULT '' COMMENT '分类编码',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '分类名称',
  `type` int(11) NOT NULL DEFAULT '1' COMMENT '类别，1-币种标签，2-币对标签',
  `choose` int(11) NOT NULL DEFAULT '1' COMMENT '选择类型，该分类下面的标签是否可以多选，1-多选，2-单选',
  `sort` int(11) NOT NULL DEFAULT '10' COMMENT '排序',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签分类表';

-- ----------------------------
-- Table structure for tag_info
-- ----------------------------
DROP TABLE IF EXISTS `tag_info`;
CREATE TABLE `tag_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增id',
  `locale` varchar(10) NOT NULL DEFAULT '' COMMENT '本地化语言代号(zh-cn/en-us等)',
  `code` varchar(100) NOT NULL DEFAULT '' COMMENT '标签编码',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '标签名称',
  `tag_category_code` varchar(100) NOT NULL DEFAULT '' COMMENT '所属标签分类编码',
  `sort` int(11) NOT NULL DEFAULT '10' COMMENT '排序',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签信息表';

-- ----------------------------
-- Table structure for vlink_contract_info
-- ----------------------------
DROP TABLE IF EXISTS `vlink_contract_info`;
CREATE TABLE `vlink_contract_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `transaction_id` varchar(128) NOT NULL COMMENT '转账记录',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `email` varchar(50) NOT NULL COMMENT 'vlink账号email',
  `contract_id` bigint(20) NOT NULL COMMENT '合约Id',
  `currency_id` int(11) NOT NULL COMMENT '币种id',
  `contract_type` varchar(150) NOT NULL DEFAULT '' COMMENT '合约型号',
  `quantity` bigint(20) NOT NULL COMMENT '购买数量',
  `total` bigint(20) NOT NULL COMMENT '总价',
  `serial_id` varchar(128) NOT NULL DEFAULT '' COMMENT 'vlink系统订单号',
  `activate_date` timestamp NULL DEFAULT NULL COMMENT '合约计算收益时间',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_transaction` (`transaction_id`),
  KEY `source_user_id_index` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='vlink合约购买记录';

SET FOREIGN_KEY_CHECKS = 1;
