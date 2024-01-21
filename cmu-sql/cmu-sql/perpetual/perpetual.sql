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

 Date: 01/07/2019 14:44:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for assets_fee_rate
-- ----------------------------
DROP TABLE IF EXISTS `assets_fee_rate`;
CREATE TABLE `assets_fee_rate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `contract_code` varchar(255) NOT NULL COMMENT '是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109',
  `time_index` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '生成当前数据的时间，如201809040916',
  `fee_rate` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '资金费率',
  `user_position_amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '用户持仓总数',
  `insurance_size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '风险准备金',
  `created_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_hpi_modify_date` (`contract_code`,`time_index`)
) ENGINE=InnoDB AUTO_INCREMENT=681 DEFAULT CHARSET=utf8 COMMENT='资金费率流水表';

-- ----------------------------
-- Table structure for assets_transfer
-- ----------------------------
DROP TABLE IF EXISTS `assets_transfer`;
CREATE TABLE `assets_transfer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT 'id',
  `target` int(11) NOT NULL,
  `type` int(11) NOT NULL COMMENT ' 1: 2',
  `status` int(11) NOT NULL,
  `amount` decimal(32,16) NOT NULL,
  `trade_no` varchar(128) NOT NULL,
  `broker_id` int(11) NOT NULL DEFAULT '1' COMMENT ' id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `currency_code` varchar(50) NOT NULL,
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_af_tn` (`trade_no`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for common_prop
-- ----------------------------
DROP TABLE IF EXISTS `common_prop`;
CREATE TABLE `common_prop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `prop_key` varchar(255) NOT NULL DEFAULT '' COMMENT '查询KEY',
  `prop_value` text NOT NULL COMMENT '配置信息为JSON数据',
  `broker_id` int(11) NOT NULL DEFAULT '1' COMMENT '业务方ID',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_cp_prop_key` (`prop_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公共配置表';

-- ----------------------------
-- Table structure for contract
-- ----------------------------
DROP TABLE IF EXISTS `contract`;
CREATE TABLE `contract` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `biz` varchar(24) NOT NULL DEFAULT 'perpetual' COMMENT '业务分类:perpetual,future',
  `index_base` varchar(24) NOT NULL COMMENT '指数基础货币,如BTC、ETH',
  `base` varchar(24) NOT NULL COMMENT '基础货币名,如BTC、FBTC',
  `quote` varchar(255) NOT NULL COMMENT '计价货币名，USD,CNY,USDT',
  `direction` int(11) NOT NULL DEFAULT '1' COMMENT '方向 0:正向,1:反向',
  `pair_code` varchar(255) NOT NULL COMMENT '是Base和Quote之间的组合 PP_BTC_USD，FR_BTC_USD',
  `unit_amount` decimal(32,16) NOT NULL DEFAULT '1.0000000000000000' COMMENT '一张合约对应的面值,默认1',
  `insurance_account` bigint(20) NOT NULL COMMENT '保险金账号',
  `min_trade_digit` int(11) NOT NULL DEFAULT '8' COMMENT '基础货币最小交易小数位',
  `min_quote_digit` int(11) NOT NULL DEFAULT '8' COMMENT '计价货币最小交易小数位',
  `contract_code` varchar(255) NOT NULL COMMENT '是Base和Quote之间的组合 pp_btc_usdt，fr_btc_usdt1109',
  `type` varchar(64) NOT NULL DEFAULT 'perpetual' COMMENT 'perpetual;week,nextweek,month,quarter 合约类型列表, perpetual为永续,周,次周,月,季度',
  `pre_liqudate_price_threshold` decimal(32,16) NOT NULL DEFAULT '0.5000000000000000' COMMENT '预强平价格阈值:强平价格+-(开仓价格-强平价格)*阈值',
  `liquidation_date` datetime DEFAULT NULL COMMENT '清算时间',
  `settlement_date` datetime DEFAULT NULL COMMENT '结算日期',
  `expired` int(2) NOT NULL DEFAULT '0' COMMENT '状态 0:可用 1:过期',
  `env` int(11) NOT NULL DEFAULT '0' COMMENT '是否测试盘 0:线上盘,1:测试盘',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '合约状态 0 正常 1 清算中 2 清算结束',
  `broker_id` int(11) NOT NULL DEFAULT '1' COMMENT '业务方ID',
  `interest_rate` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '利率，用于计算资金费率',
  `market_price` varchar(2048) DEFAULT NULL COMMENT '清算时刻的标记价格',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `market_price_digit` int(11) NOT NULL DEFAULT '2',
  PRIMARY KEY (`id`),
  KEY `idx_contract_code` (`contract_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1370 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='合约表';

-- ----------------------------
-- Table structure for currency_pair
-- ----------------------------
DROP TABLE IF EXISTS `currency_pair`;
CREATE TABLE `currency_pair` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `biz` varchar(24) NOT NULL DEFAULT 'perpetual' COMMENT '业务分类:perpetual,future',
  `index_base` varchar(24) NOT NULL COMMENT '指数基础货币,如BTC、ETH',
  `base` varchar(24) NOT NULL COMMENT '基础货币名,如btc、fbtc',
  `quote` varchar(255) NOT NULL COMMENT '计价货币名，usd,cny,usdt',
  `direction` int(11) NOT NULL DEFAULT '1' COMMENT '方向 0:正向,1:反向',
  `pair_code` varchar(255) NOT NULL COMMENT '是base和quote之间的组合 pp_btc_usd，fr_btc_usd',
  `insurance_account` bigint(20) NOT NULL COMMENT '保险金账号',
  `unit_amount` decimal(32,16) NOT NULL DEFAULT '1.0000000000000000' COMMENT '一张合约对应的面值,默认1',
  `min_order_amount` decimal(32,16) NOT NULL DEFAULT '1.0000000000000000' COMMENT '每笔最小挂单张数',
  `max_order_amount` decimal(32,16) NOT NULL DEFAULT '10000000.0000000000000000' COMMENT '每笔最大挂单张数',
  `max_orders` int(11) NOT NULL DEFAULT '100' COMMENT '单人最大挂单订单数',
  `min_trade_digit` int(11) NOT NULL DEFAULT '8' COMMENT '基础货币最小交易小数位',
  `min_quote_digit` int(11) NOT NULL DEFAULT '8' COMMENT '计价货币最小交易小数位',
  `type` varchar(64) NOT NULL DEFAULT 'perpetual' COMMENT 'perpetual;week,nextweek,month,quarter 合约类型列表, perpetual为永续,周,次周,月,季度',
  `pre_liqudate_price_threshold` decimal(32,16) NOT NULL DEFAULT '0.5000000000000000' COMMENT '预强平价格阈值:强平价格+(开仓价格-强平价格)*阈值',
  `premium_min_range` decimal(32,16) NOT NULL DEFAULT '-0.0005000000000000' COMMENT '溢价指数阈值下限',
  `premium_max_range` decimal(32,16) NOT NULL DEFAULT '0.0005000000000000' COMMENT '溢价指数阈值上限',
  `premium_depth` decimal(32,16) NOT NULL COMMENT '溢价指数加权买卖价的取值深度,单位为基础货币',
  `funding_ceiling` decimal(32,16) NOT NULL DEFAULT '0.7500000000000000' COMMENT '绝对的资金费率上限为 起始保证金 - 维持保证金 的 百分比',
  `min_gear` decimal(32,16) NOT NULL DEFAULT '100.0000000000000000' COMMENT '单位是base,最低档位',
  `diff_gear` decimal(32,16) NOT NULL DEFAULT '100.0000000000000000' COMMENT '单位是base,每档的差值',
  `max_gear` decimal(32,16) NOT NULL DEFAULT '1000.0000000000000000' COMMENT '单位是base,最高档位',
  `maintain_rate` decimal(32,16) NOT NULL COMMENT '维持保证金费率,每升一档都加一个维持保证金费率',
  `liquidation_hour` int(11) NOT NULL DEFAULT '8' COMMENT '清算周期，以小时: 从北京中午12:00点开始',
  `dk_fee` int(11) NOT NULL DEFAULT '0' COMMENT '点卡抵扣手续费 0:不使用,1:使用',
  `env` int(11) NOT NULL DEFAULT '0' COMMENT '是否测试盘 0:线上盘,1:测试盘',
  `online` int(11) NOT NULL DEFAULT '0' COMMENT '状态:0下线;1预发;2线上可用',
  `interest_rate` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '利率，用于计算资金费率',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `market_price_digit` int(11) NOT NULL DEFAULT '2',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_ccp_pair_code` (`biz`,`pair_code`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='合约币对';

-- ----------------------------
-- Table structure for currency_pair_broker_relation
-- ----------------------------
DROP TABLE IF EXISTS `currency_pair_broker_relation`;
CREATE TABLE `currency_pair_broker_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pair_code` varchar(255) NOT NULL COMMENT '币对',
  `broker_id` int(1) NOT NULL DEFAULT '1' COMMENT '券商 id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_bid_pcd` (`broker_id`,`pair_code`),
  KEY `idx_pcd` (`pair_code`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='币对 券商 对应关系表';

-- ----------------------------
-- Table structure for explosion
-- ----------------------------
DROP TABLE IF EXISTS `explosion`;
CREATE TABLE `explosion` (
  `id` bigint(20) NOT NULL COMMENT '破产表主键，与用户持仓表主键相同',
  `user_id` bigint(20) NOT NULL COMMENT '用户 id',
  `broker_id` int(11) NOT NULL DEFAULT '1' COMMENT '业务方ID',
  `contract_code` varchar(255) NOT NULL COMMENT '是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109',
  `cancel_order_id` varchar(1024) DEFAULT NULL COMMENT '取消订单id',
  `close_order_id` bigint(20) DEFAULT NULL COMMENT '平仓订单id',
  `history_explosion_id` bigint(20) DEFAULT NULL COMMENT '当前流水 id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间戳',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间戳',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_broker_id_user_id` (`broker_id`,`user_id`,`contract_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='爆仓';

-- ----------------------------
-- Table structure for fee
-- ----------------------------
DROP TABLE IF EXISTS `fee`;
CREATE TABLE `fee` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pair_code` varchar(255) NOT NULL COMMENT 'pair_code的base走手续费',
  `side` int(11) NOT NULL DEFAULT '0' COMMENT '0:both 1:maker 2:taker',
  `rate` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '手续费',
  `broker_id` int(11) NOT NULL DEFAULT '1' COMMENT '业务方ID',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_pair_code_side_broker_id` (`pair_code`,`side`,`broker_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='平台手续费配置表';

-- ----------------------------
-- Table structure for history_explosion
-- ----------------------------
DROP TABLE IF EXISTS `history_explosion`;
CREATE TABLE `history_explosion` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `broker_id` int(11) NOT NULL DEFAULT '1' COMMENT '业务方ID',
  `contract_code` varchar(255) NOT NULL COMMENT '是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109',
  `side` varchar(50) NOT NULL DEFAULT 'long' COMMENT '仓位类型，long多，short空',
  `before_position_quantity` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '爆仓前持仓数量',
  `after_position_quantity` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '爆仓后持仓数量',
  `close_position_quantity` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '爆仓张数',
  `market_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '标记价格',
  `pre_liqudate_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '预强平价',
  `liqudate_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '强平价',
  `broker_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '破产价',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_lq_uId_brokerId_status` (`broker_id`,`user_id`,`contract_code`)
) ENGINE=InnoDB AUTO_INCREMENT=66420 DEFAULT CHARSET=utf8 COMMENT='爆仓流水表';

-- ----------------------------
-- Table structure for history_explosion_expand
-- ----------------------------
DROP TABLE IF EXISTS `history_explosion_expand`;
CREATE TABLE `history_explosion_expand` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `history_explosion_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '爆仓流水 id',
  `refer_id` bigint(20) NOT NULL DEFAULT '1' COMMENT '关联业务 id，如 爆仓单 id，对敲用户id',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '1 order, 2 user',
  `before_position_quantity` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '爆仓前持仓数量',
  `after_position_quantity` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '爆仓后持仓数量',
  `close_position_quantity` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '爆仓张数',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_his_exp_id` (`history_explosion_id`)
) ENGINE=InnoDB AUTO_INCREMENT=127846 DEFAULT CHARSET=utf8 COMMENT='爆仓流水扩展表';

-- ----------------------------
-- Table structure for history_liquidate
-- ----------------------------
DROP TABLE IF EXISTS `history_liquidate`;
CREATE TABLE `history_liquidate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '平仓表主键',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `broker_id` int(11) NOT NULL DEFAULT '1' COMMENT '业务方ID',
  `contract_code` varchar(255) NOT NULL COMMENT '是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109',
  `side` varchar(50) NOT NULL DEFAULT 'long' COMMENT '仓位类型，long多，short空',
  `before_position_quantity` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '平仓前持仓数量',
  `after_position_quantity` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '平仓后持仓数量',
  `close_position_quantity` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '平仓张数',
  `market_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '标记价格',
  `pre_liqudate_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '预强平价',
  `liqudate_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '强平价',
  `broker_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '破产价',
  `order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '强平单 id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_hl_uId` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=802283 DEFAULT CHARSET=utf8 COMMENT='预爆仓备份表(不会被任务删除)';

-- ----------------------------
-- Table structure for history_mark_price
-- ----------------------------
DROP TABLE IF EXISTS `history_mark_price`;
CREATE TABLE `history_mark_price` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `contract_code` varchar(255) NOT NULL COMMENT '是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109',
  `time_index` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '生成当前数据的时间，如201809040916',
  `index_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '指数价格',
  `mark_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '标记价格',
  `premium_index` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '溢价指数',
  `ask_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '深度加权买价',
  `bid_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '深度加权卖价',
  `created_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_hpi_modify_date` (`contract_code`,`time_index`)
) ENGINE=InnoDB AUTO_INCREMENT=386148 DEFAULT CHARSET=utf8 COMMENT='溢价指数流水表';

-- ----------------------------
-- Table structure for history_settlement
-- ----------------------------
DROP TABLE IF EXISTS `history_settlement`;
CREATE TABLE `history_settlement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `contract_code` varchar(255) NOT NULL COMMENT '是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109',
  `type` int(11) NOT NULL DEFAULT '1' COMMENT '类型 1.交割 2.清算',
  `settlement_date` timestamp NULL DEFAULT NULL COMMENT '交割日期',
  `settlement_amount` decimal(32,16) DEFAULT '0.0000000000000000' COMMENT '交割合约张数',
  `settlement_price` decimal(32,16) DEFAULT '0.0000000000000000' COMMENT '交割平均价',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='清算历史记录表';

-- ----------------------------
-- Table structure for history_settlement_user
-- ----------------------------
DROP TABLE IF EXISTS `history_settlement_user`;
CREATE TABLE `history_settlement_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `contract_code` varchar(255) NOT NULL COMMENT '是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109',
  `type` int(11) NOT NULL DEFAULT '1' COMMENT '类型 1.交割 2.清算',
  `settlement_date` timestamp NULL DEFAULT NULL COMMENT '交割日期',
  `time_index` bigint(20) NOT NULL DEFAULT '0' COMMENT '生成当前数据的时间，如201809040916',
  `user_id` bigint(20) NOT NULL COMMENT '用户 id',
  `before_balance` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT 'before余额',
  `after_balance` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT 'after余额',
  `diff_balance` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT 'after-before余额',
  `broker_id` int(11) NOT NULL DEFAULT '1' COMMENT '业务方ID',
  `settlement_amount` decimal(32,16) DEFAULT '0.0000000000000000' COMMENT '交割合约张数',
  `settlement_price` decimal(32,16) DEFAULT '0.0000000000000000' COMMENT '交割平均价',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='清算历史记录表';

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
-- Table structure for liquidate
-- ----------------------------
DROP TABLE IF EXISTS `liquidate`;
CREATE TABLE `liquidate` (
  `id` bigint(20) NOT NULL COMMENT '强平表主键，与用户持仓表主键相同',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `broker_id` int(11) NOT NULL DEFAULT '1' COMMENT '业务方ID',
  `contract_code` varchar(255) NOT NULL COMMENT '是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109',
  `cancel_order_id` varchar(1024) DEFAULT NULL COMMENT '取消订单id',
  `close_order_id` bigint(20) DEFAULT NULL COMMENT '平仓订单id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间戳',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间戳',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_broker_id_user_id` (`broker_id`,`user_id`,`contract_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='强平表';

-- ----------------------------
-- Table structure for mark_price
-- ----------------------------
DROP TABLE IF EXISTS `mark_price`;
CREATE TABLE `mark_price` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contract_code` varchar(255) NOT NULL,
  `quote_currency` varchar(128) NOT NULL,
  `base_currency` varchar(128) NOT NULL,
  `mark_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000',
  `index_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000',
  `fee_rate` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000',
  `estimate_fee_rate` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000',
  `last_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=13099586 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for market_data
-- ----------------------------
DROP TABLE IF EXISTS `market_data`;
CREATE TABLE `market_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `contract_code` varchar(255) NOT NULL COMMENT '是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109',
  `open` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '开盘价',
  `high` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '最高价',
  `low` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '最低价',
  `close` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '收盘价',
  `amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '成交张数',
  `size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '成交价值',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '0:1分钟 1:5分钟 2:15分钟 3:日 4:周 5:月',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_md` (`created_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='K线';

-- ----------------------------
-- Table structure for market_data_fbtc_usd
-- ----------------------------
DROP TABLE IF EXISTS `market_data_fbtc_usd`;
CREATE TABLE `market_data_fbtc_usd` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `contract_code` varchar(255) NOT NULL COMMENT '是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109',
  `open` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '开盘价',
  `high` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '最高价',
  `low` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '最低价',
  `close` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '收盘价',
  `amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '成交张数',
  `size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '成交价值',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '0:1分钟 1:5分钟 2:15分钟 3:日 4:周 5:月',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_md` (`created_date`)
) ENGINE=InnoDB AUTO_INCREMENT=356044 DEFAULT CHARSET=utf8 COMMENT='K线';

-- ----------------------------
-- Table structure for matching_cache
-- ----------------------------
DROP TABLE IF EXISTS `matching_cache`;
CREATE TABLE `matching_cache` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contract_code` varchar(255) NOT NULL COMMENT '是Base和Quote之间的组合 FBTCUSD，RBTCUSD1109',
  `contract_info` longtext NOT NULL COMMENT 'contract的json',
  `pending_info` longtext NOT NULL COMMENT 'pending的json',
  `order_all_update_info` longtext NOT NULL COMMENT 'order_all修改的json',
  `order_all_delete_info` longtext NOT NULL COMMENT 'order_all删除的json',
  `order_finish_info` longtext NOT NULL COMMENT 'order_finish的json',
  `user_bill_info` longtext NOT NULL COMMENT 'user_bill的json',
  `system_bill_info` longtext NOT NULL COMMENT 'system_bill的json',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=176150 DEFAULT CHARSET=utf8 COMMENT='撮合缓存表';

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `contract_code` varchar(255) NOT NULL COMMENT '是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109',
  `clazz` int(11) NOT NULL DEFAULT '0' COMMENT '0:下单 1:撤单',
  `must_maker` int(11) NOT NULL DEFAULT '0' COMMENT '被动委托：0:不care 1:只做maker，如何是taker就撤单',
  `side` varchar(64) NOT NULL DEFAULT 'long' COMMENT '仓位方向，long多，short空',
  `detail_side` varchar(64) NOT NULL COMMENT '1.开多open_long 2.开空open_short 3.平多close_long 4.平空close_short',
  `amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '委托数量',
  `show_amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '冰山数量',
  `price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '用户订单委托或者破产价格',
  `avg_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '平均成交价格',
  `deal_amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '已成交数量',
  `size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '委托价值',
  `broker_size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '穿仓价值',
  `deal_size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '已经成交价值',
  `open_margin` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '开仓保险金',
  `extra_margin` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '额外价格偏移保证金',
  `avg_margin` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '每张合约摊到的保证金',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0 等待成交 1 部分成交 2 已经成交 -1 已经撤销',
  `order_from` int(11) NOT NULL DEFAULT '0' COMMENT '下单来源 0网页下单,2openApi,3IOS,4android',
  `system_type` int(11) NOT NULL DEFAULT '0' COMMENT '10:限价 11:市价 13:强平单 14:爆仓单',
  `match_status` int(11) NOT NULL DEFAULT '0' COMMENT '0:撮合已经扫描过，1:撮合未扫描过',
  `relation_order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '订单关联id',
  `profit` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '该笔订单成交后对应的盈亏: 正表示盈利,负表示亏损',
  `fee` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '该笔订单成交后交的手续费: 正表示得手续费,负表示付手续费',
  `reason` int(11) NOT NULL DEFAULT '0' COMMENT '该笔订单取消的理由，0是默认值，没有理由',
  `broker_id` int(11) NOT NULL DEFAULT '1' COMMENT '业务方ID',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_id` (`order_id`),
  KEY `idx_orders_contract_code` (`contract_code`),
  KEY `idx_orders_uId_brokerId` (`user_id`,`broker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='订单表';

-- ----------------------------
-- Table structure for order_all
-- ----------------------------
DROP TABLE IF EXISTS `order_all`;
CREATE TABLE `order_all` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `contract_code` varchar(255) NOT NULL COMMENT '是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109',
  `clazz` int(11) NOT NULL DEFAULT '0' COMMENT '0:下单 1:撤单',
  `must_maker` int(11) NOT NULL DEFAULT '0' COMMENT '被动委托：0:不care 1:只做maker，如何是taker就撤单',
  `side` varchar(64) NOT NULL DEFAULT 'long' COMMENT '仓位方向，long多，short空',
  `detail_side` varchar(64) NOT NULL COMMENT '1.开多open_long 2.开空open_short 3.平多close_long 4.平空close_short',
  `amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '委托数量',
  `price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '用户订单委托价格',
  `avg_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '平均成交价格',
  `deal_amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '已成交数量',
  `size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '委托价值',
  `deal_size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '已经成交价值',
  `open_margin` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '开仓保险金',
  `extra_margin` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '额外价格偏移保证金',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0 等待成交 1 部分成交 2 已经成交 -1 已经撤销',
  `order_from` int(11) NOT NULL DEFAULT '0' COMMENT '下单来源 0网页下单,2openApi,3IOS,4android',
  `system_type` int(11) NOT NULL DEFAULT '0' COMMENT '10:限价 11:市价 13:强平单 14:爆仓单',
  `relation_order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '订单关联id',
  `profit` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '该笔订单成交后对应的盈亏: 正表示盈利,负表示亏损',
  `fee` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '该笔订单成交后交的手续费: 正表示得手续费,负表示付手续费',
  `reason` int(11) NOT NULL DEFAULT '0' COMMENT '该笔订单取消的理由，0是默认值，没有理由',
  `broker_id` int(11) NOT NULL DEFAULT '1' COMMENT '业务方ID',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_id` (`order_id`),
  KEY `idx_orders_uId_brokerId` (`user_id`,`broker_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20681243 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='全部订单表';

-- ----------------------------
-- Table structure for order_condition
-- ----------------------------
DROP TABLE IF EXISTS `order_condition`;
CREATE TABLE `order_condition` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) NOT NULL DEFAULT '' COMMENT '触发类型：指数价格，标记价格，最新价格',
  `direction` varchar(64) NOT NULL DEFAULT 'greater' COMMENT '触发方向，greater大于，less小于',
  `condition_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '触发价格',
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `contract_code` varchar(255) NOT NULL COMMENT '是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109',
  `clazz` int(11) NOT NULL DEFAULT '0' COMMENT '0:下单 1:撤单',
  `must_maker` int(11) NOT NULL DEFAULT '0' COMMENT '被动委托：0:不care 1:只做maker，如何是taker就撤单',
  `side` varchar(64) NOT NULL DEFAULT 'long' COMMENT '仓位方向，long多，short空',
  `detail_side` varchar(64) NOT NULL COMMENT '1.开多open_long 2.开空open_short 3.平多close_long 4.平空close_short',
  `amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '委托数量',
  `price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '用户订单委托或者破产价格',
  `avg_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '平均成交价格',
  `deal_amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '已成交数量',
  `size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '委托价值',
  `deal_size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '已经成交价值',
  `open_margin` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '开仓保险金',
  `extra_margin` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '额外价格偏移保证金',
  `avg_margin` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '每张合约摊到的保证金',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0 未触发 1 已经触发 -1 已经撤销',
  `order_from` int(11) NOT NULL DEFAULT '0' COMMENT '下单来源 0网页下单,2openApi,3IOS,4android',
  `system_type` int(11) NOT NULL DEFAULT '0' COMMENT '10:限价 11:市价 13:强平单 14:爆仓单',
  `relation_order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '订单关联id',
  `broker_id` int(11) NOT NULL DEFAULT '1' COMMENT '业务方ID',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_id` (`order_id`),
  KEY `idx_orders_uId_brokerId` (`user_id`,`broker_id`)
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='计划委托条件表';

-- ----------------------------
-- Table structure for order_fbtc_usd
-- ----------------------------
DROP TABLE IF EXISTS `order_fbtc_usd`;
CREATE TABLE `order_fbtc_usd` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `contract_code` varchar(255) NOT NULL COMMENT '是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109',
  `clazz` int(11) NOT NULL DEFAULT '0' COMMENT '0:下单 1:撤单',
  `must_maker` int(11) NOT NULL DEFAULT '0' COMMENT '被动委托：0:不care 1:只做maker，如何是taker就撤单',
  `side` varchar(64) NOT NULL DEFAULT 'long' COMMENT '仓位方向，long多，short空',
  `detail_side` varchar(64) NOT NULL COMMENT '1.开多open_long 2.开空open_short 3.平多close_long 4.平空close_short',
  `amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '委托数量',
  `show_amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '冰山数量',
  `price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '用户订单委托或者破产价格',
  `avg_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '平均成交价格',
  `deal_amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '已成交数量',
  `size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '委托价值',
  `broker_size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '穿仓价值',
  `deal_size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '已经成交价值',
  `open_margin` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '开仓保险金',
  `extra_margin` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '额外价格偏移保证金',
  `avg_margin` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '每张合约摊到的保证金',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0 等待成交 1 部分成交 2 已经成交 -1 已经撤销',
  `order_from` int(11) NOT NULL DEFAULT '0' COMMENT '下单来源 0网页下单,2openApi,3IOS,4android',
  `system_type` int(11) NOT NULL DEFAULT '0' COMMENT '10:限价 11:市价 13:强平单 14:爆仓单',
  `match_status` int(11) NOT NULL DEFAULT '0' COMMENT '0:撮合已经扫描过，1:撮合未扫描过',
  `relation_order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '订单关联id',
  `profit` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '该笔订单成交后对应的盈亏: 正表示盈利,负表示亏损',
  `fee` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '该笔订单成交后交的手续费: 正表示得手续费,负表示付手续费',
  `reason` int(11) NOT NULL DEFAULT '0' COMMENT '该笔订单取消的理由，0是默认值，没有理由',
  `broker_id` int(11) NOT NULL DEFAULT '1' COMMENT '业务方ID',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_id` (`order_id`),
  KEY `idx_orders_contract_code` (`contract_code`),
  KEY `idx_orders_uId_brokerId` (`user_id`,`broker_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23930900 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='订单表';

-- ----------------------------
-- Table structure for order_finish
-- ----------------------------
DROP TABLE IF EXISTS `order_finish`;
CREATE TABLE `order_finish` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `contract_code` varchar(255) NOT NULL COMMENT '是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109',
  `clazz` int(11) NOT NULL DEFAULT '0' COMMENT '0:下单 1:撤单',
  `must_maker` int(11) NOT NULL DEFAULT '0' COMMENT '被动委托：0:不care 1:只做maker，如何是taker就撤单',
  `side` varchar(64) NOT NULL DEFAULT 'long' COMMENT '仓位方向，long多，short空',
  `detail_side` varchar(64) NOT NULL COMMENT '1.开多open_long 2.开空open_short 3.平多close_long 4.平空close_short',
  `amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '委托数量',
  `price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '用户订单委托价格',
  `avg_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '平均成交价格',
  `deal_amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '已成交数量',
  `size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '委托价值',
  `deal_size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '已经成交价值',
  `open_margin` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '开仓保险金',
  `extra_margin` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '额外价格偏移保证金',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0 等待成交 1 部分成交 2 已经成交 -1 已经撤销',
  `order_from` int(11) NOT NULL DEFAULT '0' COMMENT '下单来源 0网页下单,2openApi,3IOS,4android',
  `system_type` int(11) NOT NULL DEFAULT '0' COMMENT '10:限价 11:市价 13:强平单 14:爆仓单',
  `relation_order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '订单关联id',
  `profit` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '该笔订单成交后对应的盈亏: 正表示盈利,负表示亏损',
  `fee` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '该笔订单成交后交的手续费: 正表示得手续费,负表示付手续费',
  `reason` int(11) NOT NULL DEFAULT '0' COMMENT '该笔订单取消的理由，0是默认值，没有理由',
  `broker_id` int(11) NOT NULL DEFAULT '1' COMMENT '业务方ID',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_id` (`order_id`),
  KEY `idx_orders_uId_brokerId` (`user_id`,`broker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单完成总表';

-- ----------------------------
-- Table structure for order_finish_fbtc_usd
-- ----------------------------
DROP TABLE IF EXISTS `order_finish_fbtc_usd`;
CREATE TABLE `order_finish_fbtc_usd` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `contract_code` varchar(255) NOT NULL COMMENT '是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109',
  `clazz` int(11) NOT NULL DEFAULT '0' COMMENT '0:下单 1:撤单',
  `must_maker` int(11) NOT NULL DEFAULT '0' COMMENT '被动委托：0:不care 1:只做maker，如何是taker就撤单',
  `side` varchar(64) NOT NULL DEFAULT 'long' COMMENT '仓位方向，long多，short空',
  `detail_side` varchar(64) NOT NULL COMMENT '1.开多open_long 2.开空open_short 3.平多close_long 4.平空close_short',
  `amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '委托数量',
  `price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '用户订单委托价格',
  `avg_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '平均成交价格',
  `deal_amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '已成交数量',
  `size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '委托价值',
  `deal_size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '已经成交价值',
  `open_margin` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '开仓保险金',
  `extra_margin` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '额外价格偏移保证金',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0 等待成交 1 部分成交 2 已经成交 -1 已经撤销',
  `order_from` int(11) NOT NULL DEFAULT '0' COMMENT '下单来源 0网页下单,2openApi,3IOS,4android',
  `system_type` int(11) NOT NULL DEFAULT '0' COMMENT '10:限价 11:市价 13:强平单 14:爆仓单',
  `relation_order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '订单关联id',
  `profit` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '该笔订单成交后对应的盈亏: 正表示盈利,负表示亏损',
  `fee` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '该笔订单成交后交的手续费: 正表示得手续费,负表示付手续费',
  `reason` int(11) NOT NULL DEFAULT '0' COMMENT '该笔订单取消的理由，0是默认值，没有理由',
  `broker_id` int(11) NOT NULL DEFAULT '1' COMMENT '业务方ID',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_id` (`order_id`),
  KEY `idx_orders_uId_brokerId` (`user_id`,`broker_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32362834 DEFAULT CHARSET=utf8 COMMENT='订单完成总表';

-- ----------------------------
-- Table structure for order_history
-- ----------------------------
DROP TABLE IF EXISTS `order_history`;
CREATE TABLE `order_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `contract_code` varchar(255) NOT NULL COMMENT '是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109',
  `clazz` int(11) NOT NULL DEFAULT '0' COMMENT '0:下单 1:撤单',
  `must_maker` int(11) NOT NULL DEFAULT '0' COMMENT '被动委托：0:不care 1:只做maker，如何是taker就撤单',
  `side` varchar(64) NOT NULL DEFAULT 'long' COMMENT '仓位方向，long多，short空',
  `detail_side` varchar(64) NOT NULL COMMENT '1.开多open_long 2.开空open_short 3.平多close_long 4.平空close_short',
  `amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '委托数量',
  `price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '用户订单委托价格',
  `avg_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '平均成交价格',
  `deal_amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '已成交数量',
  `size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '委托价值',
  `deal_size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '已经成交价值',
  `open_margin` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '开仓保险金',
  `extra_margin` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '额外价格偏移保证金',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0 等待成交 1 部分成交 2 已经成交 -1 已经撤销',
  `order_from` int(11) NOT NULL DEFAULT '0' COMMENT '下单来源 0网页下单,2openApi,3IOS,4android',
  `system_type` int(11) NOT NULL DEFAULT '0' COMMENT '10:限价 11:市价 13:强平单 14:爆仓单',
  `relation_order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '订单关联id',
  `profit` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '该笔订单成交后对应的盈亏: 正表示盈利,负表示亏损',
  `fee` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '该笔订单成交后交的手续费: 正表示得手续费,负表示付手续费',
  `reason` int(11) NOT NULL DEFAULT '0' COMMENT '该笔订单取消的理由，0是默认值，没有理由',
  `broker_id` int(11) NOT NULL DEFAULT '1' COMMENT '业务方ID',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_id` (`order_id`),
  KEY `idx_orders_uId_brokerId` (`user_id`,`broker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='订单归档表';

-- ----------------------------
-- Table structure for order_history_fbtc_usd
-- ----------------------------
DROP TABLE IF EXISTS `order_history_fbtc_usd`;
CREATE TABLE `order_history_fbtc_usd` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `contract_code` varchar(255) NOT NULL COMMENT '是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109',
  `clazz` int(11) NOT NULL DEFAULT '0' COMMENT '0:下单 1:撤单',
  `must_maker` int(11) NOT NULL DEFAULT '0' COMMENT '被动委托：0:不care 1:只做maker，如何是taker就撤单',
  `side` varchar(64) NOT NULL DEFAULT 'long' COMMENT '仓位方向，long多，short空',
  `detail_side` varchar(64) NOT NULL COMMENT '1.开多open_long 2.开空open_short 3.平多close_long 4.平空close_short',
  `amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '委托数量',
  `price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '用户订单委托价格',
  `avg_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '平均成交价格',
  `deal_amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '已成交数量',
  `size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '委托价值',
  `deal_size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '已经成交价值',
  `open_margin` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '开仓保险金',
  `extra_margin` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '额外价格偏移保证金',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0 等待成交 1 部分成交 2 已经成交 -1 已经撤销',
  `order_from` int(11) NOT NULL DEFAULT '0' COMMENT '下单来源 0网页下单,2openApi,3IOS,4android',
  `system_type` int(11) NOT NULL DEFAULT '0' COMMENT '10:限价 11:市价 13:强平单 14:爆仓单',
  `relation_order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '订单关联id',
  `profit` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '该笔订单成交后对应的盈亏: 正表示盈利,负表示亏损',
  `fee` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '该笔订单成交后交的手续费: 正表示得手续费,负表示付手续费',
  `reason` int(11) NOT NULL DEFAULT '0' COMMENT '该笔订单取消的理由，0是默认值，没有理由',
  `broker_id` int(11) NOT NULL DEFAULT '1' COMMENT '业务方ID',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_id` (`order_id`),
  KEY `idx_orders_uId_brokerId` (`user_id`,`broker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='订单归档表';

-- ----------------------------
-- Table structure for pending
-- ----------------------------
DROP TABLE IF EXISTS `pending`;
CREATE TABLE `pending` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `contract_code` varchar(255) NOT NULL COMMENT '是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109',
  `order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '订单id',
  `side` varchar(64) NOT NULL COMMENT '方向：1开多,2开空,3平多,4平空',
  `other_side` varchar(64) NOT NULL COMMENT '对手方向：1开多,2开空,3平多,4平空',
  `amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '成交数量',
  `price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '成交价：注意不是委托价',
  `size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '成交额',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '1部分成交 2完全成交',
  `broker_id` int(11) NOT NULL DEFAULT '1' COMMENT '业务方ID',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_pending_uId` (`user_id`),
  KEY `idx_pending_uId_brokerId` (`user_id`,`broker_id`),
  KEY `idx_pending_created_date` (`created_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易流水表';

-- ----------------------------
-- Table structure for pending_fbtc_usd
-- ----------------------------
DROP TABLE IF EXISTS `pending_fbtc_usd`;
CREATE TABLE `pending_fbtc_usd` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `contract_code` varchar(255) NOT NULL COMMENT '是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109',
  `order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '订单id',
  `side` varchar(64) NOT NULL COMMENT '方向：1开多,2开空,3平多,4平空',
  `other_side` varchar(64) NOT NULL COMMENT '对手方向：1开多,2开空,3平多,4平空',
  `amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '成交数量',
  `price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '成交价：注意不是委托价',
  `size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '成交额',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '1部分成交 2完全成交',
  `broker_id` int(11) NOT NULL DEFAULT '1' COMMENT '业务方ID',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_pending_uId` (`user_id`),
  KEY `idx_pending_uId_brokerId` (`user_id`,`broker_id`),
  KEY `idx_pending_created_date` (`created_date`)
) ENGINE=InnoDB AUTO_INCREMENT=5603271 DEFAULT CHARSET=utf8 COMMENT='交易流水表';

-- ----------------------------
-- Table structure for position_clear
-- ----------------------------
DROP TABLE IF EXISTS `position_clear`;
CREATE TABLE `position_clear` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `broker_id` int(11) NOT NULL DEFAULT '1' COMMENT '业务方ID',
  `fee_rate` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '资金费率',
  `type` int(2) NOT NULL DEFAULT '0' COMMENT '0全仓，1逐仓',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0未处理，1已处理',
  `side` varchar(64) NOT NULL DEFAULT 'long' COMMENT '仓位方向，long多，short空',
  `base` varchar(24) NOT NULL COMMENT '基础货币名,如BTC、FBTC',
  `contract_code` varchar(255) NOT NULL COMMENT '是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109',
  `contract_id` bigint(20) NOT NULL COMMENT '合约 id',
  `available_balance` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '扣除或增加可用余额',
  `open_margin` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '扣除或增加开仓总保证金',
  `order_margin` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '扣除或增加挂单总保证金',
  `order_fee` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '扣除或增加挂单冻结总手续费',
  `before_balance` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '前余额',
  `after_balance` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '后余额',
  `last_price` decimal(32,16) NOT NULL COMMENT '最新成交价',
  `realized_surplus` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '已实现盈余',
  `amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '持仓张数',
  `sum_size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '挂单冻结总手续费',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `mark_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code_uid` (`contract_id`,`broker_id`,`user_id`),
  KEY `idx_cid_uid` (`contract_code`,`broker_id`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=47062 DEFAULT CHARSET=utf8 COMMENT='用户持仓清算流水表';

-- ----------------------------
-- Table structure for pre_liquidate_alert
-- ----------------------------
DROP TABLE IF EXISTS `pre_liquidate_alert`;
CREATE TABLE `pre_liquidate_alert` (
  `id` bigint(20) NOT NULL COMMENT '风险告警表主键，与用户持仓表主键相同',
  `user_id` bigint(20) NOT NULL COMMENT '用户 id',
  `broker_id` int(11) NOT NULL DEFAULT '1' COMMENT '业务方ID',
  `contract_code` varchar(255) NOT NULL COMMENT '是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109',
  `times` int(11) DEFAULT NULL COMMENT '告警次数',
  `expire_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '失效时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间戳',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间戳',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_broker_id_user_id` (`contract_code`,`broker_id`,`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='风险告警表';

-- ----------------------------
-- Table structure for system_bill
-- ----------------------------
DROP TABLE IF EXISTS `system_bill`;
CREATE TABLE `system_bill` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '系统手续费',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `currency_code` varchar(255) NOT NULL COMMENT '币种',
  `fee_currency_code` varchar(255) NOT NULL COMMENT '手续费对应的币种，可能是币种，可能是点卡',
  `fee` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '手续费: 正表示付手续费,负表示得手续费',
  `profit` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '该笔交易对应的盈亏: 正表示盈利,负表示亏损',
  `u_id` varchar(255) NOT NULL COMMENT 'user_bill的uid',
  `broker_id` int(11) NOT NULL DEFAULT '1' COMMENT '业务方ID',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3034123 DEFAULT CHARSET=utf8 COMMENT='对账流水表';

-- ----------------------------
-- Table structure for system_bill_total
-- ----------------------------
DROP TABLE IF EXISTS `system_bill_total`;
CREATE TABLE `system_bill_total` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `system_bill_id` bigint(20) NOT NULL COMMENT '上次一最大的系统手续费id',
  `currency_code` varchar(255) NOT NULL COMMENT '币种',
  `fee` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '变动手续费总和',
  `profit` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '变动收益总和',
  `user_balance` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '用户资产余额总和',
  `position_size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '用户持仓价值总和',
  `total_fee` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '用户手续费总和',
  `total_profit` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '用户账单收益总和',
  `base_adjust` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '人工调整',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_system_bill_id` (`system_bill_id`),
  KEY `idx_created_date` (`created_date`)
) ENGINE=InnoDB AUTO_INCREMENT=354339 DEFAULT CHARSET=utf8 COMMENT='对账汇总表';

-- ----------------------------
-- Table structure for user_activity_reward
-- ----------------------------
DROP TABLE IF EXISTS `user_activity_reward`;
CREATE TABLE `user_activity_reward` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `currency_code` varchar(255) NOT NULL COMMENT '币种',
  `type` int(11) NOT NULL COMMENT '转账类型 1:转入 2转出',
  `amount` decimal(32,16) NOT NULL COMMENT '数量',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `broker_id` int(11) NOT NULL DEFAULT '1' COMMENT '券商 id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8 COMMENT='活动币领取表';

-- ----------------------------
-- Table structure for user_balance
-- ----------------------------
DROP TABLE IF EXISTS `user_balance`;
CREATE TABLE `user_balance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `currency_code` varchar(255) NOT NULL COMMENT '币种',
  `env` int(11) NOT NULL DEFAULT '0' COMMENT '是否测试币 0:线上币,1:测试币',
  `reward_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '测试币领取时间',
  `frozen_status` int(2) NOT NULL DEFAULT '0' COMMENT '冻结状态：0未冻结，1冻结',
  `available_balance` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '可用余额',
  `frozen_balance` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '冻结余额',
  `position_size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '仓位资产：正数为多仓，负数为空仓',
  `position_margin` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '开仓总保证金',
  `position_fee` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '仓位总冻结手续费',
  `order_margin` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '挂单总保证金',
  `order_fee` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '挂单冻结总手续费',
  `realized_surplus` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '总已实现盈余',
  `broker_id` int(11) NOT NULL DEFAULT '1' COMMENT '业务方ID',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '用户状态，0 正常，1 强平中，2 爆仓中',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_c_balance_user_currency_code` (`broker_id`,`currency_code`,`user_id`) USING BTREE,
  KEY `uk_c_balance_user_broker_id` (`user_id`,`broker_id`)
) ENGINE=InnoDB AUTO_INCREMENT=128667 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='账户资产';

-- ----------------------------
-- Table structure for user_bill
-- ----------------------------
DROP TABLE IF EXISTS `user_bill`;
CREATE TABLE `user_bill` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '账单表',
  `u_id` varchar(255) NOT NULL COMMENT '唯一id，system_bill使用uid关联user_bill',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `contract_code` varchar(255) NOT NULL COMMENT '是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109',
  `currency_code` varchar(255) NOT NULL COMMENT '币种',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '类型（11.充值 12.提现 13.转入 14.转出 15.多/买 16.空/卖 17.系统收取手续费 18.保险金 19.结算 20.穿仓对敲）',
  `detail_side` varchar(64) NOT NULL DEFAULT '' COMMENT '交易类型 1.开多open_long 2.开空open_short 3.平多close_long 4.平空close_short',
  `amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '成交张数',
  `price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '成交价',
  `size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '成交金额',
  `fee_currency_code` varchar(255) NOT NULL COMMENT '手续费对应的币种，可能是币种，可能是点卡',
  `fee` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '手续费: 正表示付手续费,负表示得手续费',
  `profit` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '该笔交易对应的盈亏: 正表示盈利,负表示亏损',
  `before_position` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '前余额',
  `after_position` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '后余额',
  `before_balance` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '前余额',
  `after_balance` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '后余额',
  `maker_taker` int(11) DEFAULT '0' COMMENT '1:maker 2:taker',
  `refer_id` bigint(20) DEFAULT '0' COMMENT '账单关联记录 id',
  `trade_no` varchar(128) DEFAULT NULL COMMENT '钱包的交易编码',
  `broker_id` int(11) NOT NULL DEFAULT '1' COMMENT '业务方ID',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_ub_uId` (`user_id`),
  KEY `idx_ub_uId_brokerId` (`user_id`,`broker_id`),
  KEY `idx_ub_created_date` (`created_date`)
) ENGINE=InnoDB AUTO_INCREMENT=3034123 DEFAULT CHARSET=utf8 COMMENT='账单';

-- ----------------------------
-- Table structure for user_fee
-- ----------------------------
DROP TABLE IF EXISTS `user_fee`;
CREATE TABLE `user_fee` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pair_code` varchar(255) NOT NULL COMMENT 'pair_code的base走手续费',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `side` int(4) NOT NULL COMMENT '0:both 1:maker 2:taker',
  `rate` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '手续费',
  `broker_id` int(11) NOT NULL DEFAULT '1' COMMENT '业务方ID',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_pair_code_user_id_side_broker_id` (`pair_code`,`user_id`,`side`,`broker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户手续费配置表';

-- ----------------------------
-- Table structure for user_position
-- ----------------------------
DROP TABLE IF EXISTS `user_position`;
CREATE TABLE `user_position` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `base` varchar(24) NOT NULL COMMENT '基础货币名,如BTC、ETH',
  `quote` varchar(255) NOT NULL COMMENT '计价货币名，USD,CNY,USDT',
  `contract_code` varchar(255) NOT NULL COMMENT '是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109',
  `type` int(2) NOT NULL DEFAULT '0' COMMENT '0全仓，1逐仓',
  `lever` decimal(32,16) NOT NULL DEFAULT '100.0000000000000000' COMMENT '杠杆',
  `gear` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '档位',
  `side` varchar(50) NOT NULL DEFAULT 'long' COMMENT '仓位类型，long多，short空',
  `amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '持仓数量',
  `closing_amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '正在平仓的张数',
  `open_margin` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '开仓保证金',
  `maintenance_margin` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '维持保证金',
  `fee` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '维持仓位冻结手续费',
  `price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '开仓价格: 仓位平均价',
  `size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '仓位价值',
  `pre_liqudate_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '预强平价',
  `liqudate_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '强平价',
  `broker_price` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '破产价',
  `realized_surplus` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '已实现盈余',
  `order_margin` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '挂单保证金',
  `order_fee` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '挂单冻结手续费',
  `order_long_amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '多单的张数',
  `order_short_amount` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '空单的张数',
  `order_long_size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '多单的价值',
  `order_short_size` decimal(32,16) NOT NULL DEFAULT '0.0000000000000000' COMMENT '空单的价值',
  `broker_id` int(11) NOT NULL DEFAULT '1' COMMENT '业务方ID',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_uid_cde_bid` (`user_id`,`contract_code`,`broker_id`) USING BTREE,
  KEY `idx_up_preLiqudatePrice` (`pre_liqudate_price`),
  KEY `idx_up_liqudatePrice` (`liqudate_price`),
  KEY `idx_up_brokerPrice` (`broker_price`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='用户合约持仓数据表';

SET FOREIGN_KEY_CHECKS = 1;
