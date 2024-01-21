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

 Date: 01/07/2019 14:50:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
-- Records of fetching_data_path
-- ----------------------------
BEGIN;
INSERT INTO `fetching_data_path` VALUES (8, 9, '2018-12-12 13:02:04', 'https://api.huobi.pro/market/detail/merged?symbol=ltcusdt', 2, 'Huobi', '2018-12-12 13:02:04', NULL, '', 1);
INSERT INTO `fetching_data_path` VALUES (9, 8, '2018-12-12 13:02:04', 'https://api.huobi.pro/market/detail/merged?symbol=btcusdt', 1, 'Huobi', '2018-12-12 13:02:04', NULL, '', 1);
INSERT INTO `fetching_data_path` VALUES (10, 38, '2018-12-12 13:02:04', 'https://api.huobi.pro/market/detail/merged?symbol=ethusdt', 4, 'Huobi', '2018-12-12 13:02:04', NULL, '', 1);
INSERT INTO `fetching_data_path` VALUES (11, 49, '2018-12-12 13:02:04', 'https://api.huobi.pro/market/detail/merged?symbol=etcusdt', 5, 'Huobi', '2018-12-12 13:02:04', NULL, '', 1);
INSERT INTO `fetching_data_path` VALUES (12, 110, '2018-12-12 13:02:04', 'https://www.bitstamp.net/api/v2/ticker/ltcusd/', 2, 'Bitstamp', '2018-12-12 13:02:04', NULL, '', 1);
INSERT INTO `fetching_data_path` VALUES (13, 116, '2018-12-12 13:02:04', 'https://www.bitstamp.net/api/v2/ticker/ethusd/', 4, 'Bitstamp', '2018-12-12 13:02:04', NULL, '', 1);
INSERT INTO `fetching_data_path` VALUES (14, 19, '2018-12-12 13:02:04', 'https://api.bitfinex.com/v1/pubticker/btcusd', 1, 'Bitfinex', '2018-12-12 13:02:04', NULL, '', 1);
INSERT INTO `fetching_data_path` VALUES (15, 20, '2018-12-12 13:02:04', 'https://api.bitfinex.com/v1/pubticker/ltcusd', 2, 'Bitfinex', '2018-12-12 13:02:04', NULL, '', 1);
INSERT INTO `fetching_data_path` VALUES (16, 43, '2018-12-12 13:02:04', 'https://api.bitfinex.com/v1/pubticker/etcusd', 5, 'Bitfinex', '2018-12-12 13:02:04', NULL, '', 1);
INSERT INTO `fetching_data_path` VALUES (17, 44, '2018-12-12 13:02:04', 'https://api.bitfinex.com/v1/pubticker/ethusd', 4, 'Bitfinex', '2018-12-12 13:02:04', NULL, '', 1);
INSERT INTO `fetching_data_path` VALUES (22, 106, '2018-12-12 13:02:04', 'https://www.okcoin.com/api/v1/ticker.do?symbol=bch_usd', 6, 'OKCoin', '2018-12-12 13:02:04', NULL, '', 1);
INSERT INTO `fetching_data_path` VALUES (23, 0, '2018-12-12 13:02:04', 'https://www.okcoin.com/api/v1/ticker.do?symbol=btc_usd', 1, 'OKCoin', '2018-12-12 13:02:04', NULL, '', 1);
INSERT INTO `fetching_data_path` VALUES (24, 3, '2018-12-12 13:02:04', 'https://www.okcoin.com/api/v1/ticker.do?symbol=ltc_usd', 2, 'OKCoin', '2018-12-12 13:02:04', NULL, '', 1);
INSERT INTO `fetching_data_path` VALUES (25, 36, '2018-12-12 13:02:04', 'https://www.okcoin.com/api/v1/ticker.do?symbol=eth_usd', 4, 'OKCoin', '2018-12-12 13:02:04', NULL, '', 1);
INSERT INTO `fetching_data_path` VALUES (26, 25, '2018-12-12 13:02:04', 'https://api.itbit.com/v1/markets/XBTUSD/ticker', 1, 'ItBit', '2018-12-12 13:02:04', NULL, '', 1);
INSERT INTO `fetching_data_path` VALUES (27, 51, '2018-12-12 13:02:04', 'https://api.kraken.com/0/public/Ticker?pair=LTCUSD', 2, 'Kraken', '2018-12-12 13:02:04', 'XLTCZUSD', '', 1);
INSERT INTO `fetching_data_path` VALUES (28, 24, '2018-12-12 13:02:04', 'https://api.kraken.com/0/public/Ticker?pair=XBTUSD', 1, 'Kraken', '2018-12-12 13:02:04', 'XXBTZUSD', '', 1);
INSERT INTO `fetching_data_path` VALUES (29, 45, '2018-12-12 13:02:04', 'https://api.kraken.com/0/public/Ticker?pair=ETCUSD', 5, 'Kraken', '2018-12-12 13:02:04', 'XETCZUSD', '', 1);
INSERT INTO `fetching_data_path` VALUES (30, 46, '2018-12-12 13:02:04', 'https://api.kraken.com/0/public/Ticker?pair=ETHUSD', 4, 'Kraken', '2018-12-12 13:02:04', 'XETHZUSD', '', 1);
INSERT INTO `fetching_data_path` VALUES (35, 4, '2018-12-12 13:02:04', 'https://www.bitstamp.net/api/ticker/', 1, 'Bitstamp', '2018-12-12 13:02:04', NULL, '', 1);
INSERT INTO `fetching_data_path` VALUES (36, 27, '2018-12-12 13:02:04', 'https://api.gdax.com/products/BTC-USD/ticker', 1, 'GDAX', '2018-12-12 13:02:04', 'https://api.gdax.com/products/BTC-USD/book', 'https://api.gdax.com/products/BTC-USD/stats', 1);
INSERT INTO `fetching_data_path` VALUES (37, 50, '2018-12-12 13:02:04', 'https://api.gdax.com/products/LTC-USD/ticker', 2, 'GDAX', '2018-12-12 13:02:04', 'https://api.gdax.com/products/LTC-USD/book', 'https://api.gdax.com/products/LTC-USD/stats', 1);
INSERT INTO `fetching_data_path` VALUES (38, 42, '2018-12-12 13:02:04', 'https://api.gdax.com/products/ETH-USD/ticker', 4, 'GDAX', '2018-12-12 13:02:04', 'https://api.gdax.com/products/ETH-USD/book', 'https://api.gdax.com/products/ETH-USD/stats', 1);
INSERT INTO `fetching_data_path` VALUES (39, 23, '2018-12-12 13:02:04', 'https://anxpro.com/api/2/BTCUSD/money/ticker', 1, 'Anxbtc', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (42, 39, '2018-12-12 13:02:04', 'https://www.okcoin.com/api/v1/ticker.do?symbol=etc_usd', 5, 'OKCoin', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (43, 21, '2018-12-12 13:02:04', 'http://api.btctrade.com/api/ticker/', 1, 'BtcTrade', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (46, 118, '2018-12-12 13:02:04', 'https://spotusd-data.btcc.com/data/pro/ticker?symbol=BTCUSD', 1, 'BTCC-USD', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (47, 22, '2018-12-12 13:02:04', 'https://www.bitcoin.de/en', 1, 'Bitcoin', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (48, -1, '2018-12-12 13:02:04', 'https://btc.com/stats/pool?pool_mode=all', -1, 'orePool', '2018-12-12 13:02:04', 'all', NULL, 1);
INSERT INTO `fetching_data_path` VALUES (49, -1, '2018-12-12 13:02:04', 'https://btc.com/stats/pool?pool_mode=year', -1, 'orePool', '2018-12-12 13:02:04', 'year', NULL, 1);
INSERT INTO `fetching_data_path` VALUES (50, -1, '2018-12-12 13:02:04', 'https://btc.com/stats/pool?pool_mode=month3', -1, 'orePool', '2018-12-12 13:02:04', 'month3', NULL, 1);
INSERT INTO `fetching_data_path` VALUES (51, -1, '2018-12-12 13:02:04', 'https://btc.com/stats/pool?pool_mode=month', -1, 'orePool', '2018-12-12 13:02:04', 'month', NULL, 1);
INSERT INTO `fetching_data_path` VALUES (52, -1, '2018-12-12 13:02:04', 'https://btc.com/stats/pool?pool_mode=week', -1, 'orePool', '2018-12-12 13:02:04', 'week', NULL, 1);
INSERT INTO `fetching_data_path` VALUES (53, -1, '2018-12-12 13:02:04', 'https://btc.com/stats/pool?pool_mode=day', -1, 'orePool', '2018-12-12 13:02:04', 'day', NULL, 1);
INSERT INTO `fetching_data_path` VALUES (54, -1, '2018-12-12 13:02:04', 'https://btc.com/stats/pool?pool_mode=day3', -1, 'orePool', '2018-12-12 13:02:04', 'day3', NULL, 1);
INSERT INTO `fetching_data_path` VALUES (55, -1, '2018-12-12 13:02:04', 'http://opendata.baidu.com/api.php?query=1%E6%AC%A7%E5%85%83%E7%AD%89%E4%BA%8E%E5%A4%9A%E5%B0%91%E4%BA%BA%E6%B0%91%E5%B8%81%E5%85%83&co=&resource_id=6017&t=1398159587150&ie=utf8&oe=gbk&cb=op_aladdin_callback&format=json&tn=baidu&cb=jQuery110205116758023097', -1, 'rate', '2018-12-12 13:02:04', 'eur_cny', NULL, 1);
INSERT INTO `fetching_data_path` VALUES (56, -1, '2018-12-12 13:02:04', 'http://opendata.baidu.com/api.php?query=1%E7%BE%8E%E5%85%83%E7%AD%89%E4%BA%8E%E5%A4%9A%E5%B0%91%E4%BA%BA%E6%B0%91%E5%B8%81%E5%85%83&co=&resource_id=6017&t=1398159587150&ie=utf8&oe=gbk&cb=op_aladdin_callback&format=json&tn=baidu&cb=jQuery110205116758023097', -1, 'rate', '2018-12-12 13:02:04', 'usd_cny', NULL, 1);
INSERT INTO `fetching_data_path` VALUES (57, 117, '2018-12-12 13:02:04', 'https://api.huobi.pro/market/detail/merged?symbol=bchusdt', 6, 'Huobi', '2018-12-12 13:02:04', '', '', 1);
INSERT INTO `fetching_data_path` VALUES (58, 119, '2018-12-12 13:02:04', 'https://api.gemini.com/v1/pubticker/btcusd', 1, 'GEMINI', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (59, 120, '2018-12-12 13:02:04', 'https://api.gemini.com/v1/pubticker/ethusd', 4, 'GEMINI', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (60, 121, '2018-12-12 13:02:04', ' https://bittrex.com/api/v1.1/public/getmarketsummary?market=btc-ltc', 1, 'Bittrex', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (61, 122, '2018-12-12 13:02:04', 'https://bittrex.com/api/v1.1/public/getmarketsummary?market=BTC-BCC', 6, 'Bittrex', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (62, 123, '2018-12-12 13:02:04', 'https://bittrex.com/api/v1.1/public/getmarketsummary?market=BTC-ETC', 5, 'Bittrex', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (63, 124, '2018-12-12 13:02:04', 'https://bittrex.com/api/v1.1/public/getmarketsummary?market=btc-dash', 17, 'Bittrex', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (64, 125, '2018-12-12 13:02:04', 'https://bittrex.com/api/v1.1/public/getmarketsummary?market=btc-xrp', 15, 'Bittrex', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (65, 126, '2018-12-12 13:02:04', 'https://poloniex.com/public?command=returnTicker', -1, 'Poloniex', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (66, 127, '2018-12-12 13:02:04', 'https://api.bitfinex.com/v1/pubticker/EOSBTC', 20, 'Bitfinex', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (67, 128, '2018-12-12 13:02:04', 'https://api.bitfinex.com/v1/pubticker/XRPBTC', 15, 'Bitfinex', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (68, 129, '2018-12-12 13:02:04', 'https://www.okex.com/v2/markets/dash_btc/ticker', 17, 'OKEX', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (69, 130, '2018-12-12 13:02:04', 'https://www.okex.com/v2/markets/etc_btc/ticker', 5, 'OKEX', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (70, 131, '2018-12-12 13:02:04', 'https://www.okex.com/v2/markets/bch_btc/ticker', 6, 'OKEX', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (71, 132, '2018-12-12 13:02:04', 'https://www.okex.com/v2/markets/xrp_btc/ticker', 15, 'OKEX', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (72, 133, '2018-12-12 13:02:04', 'https://www.okex.com/v2/markets/eos_btc/ticker', 20, 'OKEX', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (73, 144, '2018-12-12 13:02:04', 'https://api.bitfinex.com/v1/pubticker/btgbtc', 10, 'Bitfinex', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (74, 145, '2018-12-12 13:02:04', 'https://www.okex.com/v2/markets/btg_btc/ticker', 10, 'OKEX', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (75, 148, '2018-12-12 13:02:04', 'https://api.bitfinex.com/v1/pubticker/bchusd', 6, 'Bitfinex', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (77, 149, '2018-12-12 13:02:04', 'https://api.bitfinex.com/v1/pubticker/dshbtc', 17, 'BItfinex', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (78, 173, '2018-12-12 13:02:04', 'https://www.okex.com/v2/markets/eos_btc/ticker', 20, 'OKEX', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (79, 174, '2018-12-12 13:02:04', 'https://www.okex.com/v2/markets/qtum_btc/ticker', 12, 'OKEX', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (80, 175, '2018-12-12 13:02:04', 'https://www.okex.com/v2/markets/neo_btc/ticker', 13, 'OKEX', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (81, 176, '2018-12-12 13:02:04', 'https://www.okex.com/v2/markets/xuc_btc/ticker', 19, 'OKEX', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (82, 177, '2018-12-12 13:02:04', 'https://api.bitfinex.com/v1/pubticker/qtmusd', 12, 'Bitfinex', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (83, 178, '2018-12-12 13:02:04', 'https://api.bitfinex.com/v1/pubticker/NEOUSD', 13, 'Bitfinex', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (84, 179, '2018-12-12 13:02:04', 'https://api.bitfinex.com/v1/pubticker/EOSUSD', 20, 'Bitfinex', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (85, 180, '2018-12-12 13:02:04', 'https://api.bithumb.com/public/ticker/eos', 20, 'Bithumb', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (86, 181, '2018-12-12 13:02:04', 'https://api.bithumb.com/public/ticker/qtum', 12, 'Bithumb', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (87, 182, '2018-12-12 13:02:04', 'https://api.hitbtc.com/api/2/public/ticker/XUCETH', 19, 'Hitbtc', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (88, -1, '2018-12-12 13:02:04', 'http://opendata.baidu.com/api.php?query=1%E9%9F%A9%E5%85%83%E7%AD%89%E4%BA%8E%E5%A4%9A%E5%B0%91%E7%BE%8E%E5%85%83&co=&resource_id=6017&t=1398159587150&ie=utf8&oe=gbk&cb=op_aladdin_callback&format=json&tn=baidu&cb=jQuery110205116758023097', -1, 'rate', '2018-12-12 13:02:04', 'krw_usd', NULL, 1);
INSERT INTO `fetching_data_path` VALUES (89, 197, '2018-12-12 13:02:04', 'https://api.binance.com/api/v1/ticker/24hr?symbol=BTCUSDT', 1, 'binance', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (90, 183, '2018-12-12 13:02:04', 'https://api.binance.com/api/v1/ticker/24hr?symbol=LTCUSDT', 2, 'binance', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (91, 184, '2018-12-12 13:02:04', 'https://api.binance.com/api/v1/ticker/24hr?symbol=ETHUSDT', 4, 'binance', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (92, 185, '2018-12-12 13:02:04', 'http://api.zb.cn/data/v1/ticker?market=btc_usdt', 1, 'zb', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (93, 186, '2018-12-12 13:02:04', 'http://api.zb.cn/data/v1/ticker?market=ltc_usdt', 2, 'zb', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (94, 187, '2018-12-12 13:02:04', 'http://api.zb.cn/data/v1/ticker?market=eth_usdt', 4, 'zb', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (95, 188, '2018-12-12 13:02:04', 'https://api.bibox.com/v1/mdata?cmd=ticker&pair=BTC_USDT', 1, 'bibox', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (96, 189, '2018-12-12 13:02:04', 'https://api.bibox.com/v1/mdata?cmd=ticker&pair=LTC_USDT', 2, 'bibox', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (97, 190, '2018-12-12 13:02:04', 'https://api.bibox.com/v1/mdata?cmd=ticker&pair=ETH_USDT', 4, 'bibox', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (98, 191, '2018-12-12 13:02:04', 'https://data.gateio.io/api2/1/ticker/btc_usdt', 1, 'gateio', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (99, 192, '2018-12-12 13:02:04', 'https://data.gateio.io/api2/1/ticker/ltc_usdt', 2, 'gateio', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (100, 193, '2018-12-12 13:02:04', 'https://data.gateio.io/api2/1/ticker/eth_usdt', 4, 'gateio', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (101, 194, '2018-12-12 13:02:04', 'https://www.okex.com/api/v1/ticker.do?symbol=btc_usdt', 1, 'okex', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (102, 195, '2018-12-12 13:02:04', 'https://www.okex.com/api/v1/ticker.do?symbol=ltc_usdt', 2, 'okex', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (103, 196, '2018-12-12 13:02:04', 'https://www.okex.com/api/v1/ticker.do?symbol=eth_usdt', 4, 'okex', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (104, 199, '2018-12-12 13:02:04', 'https://www.coinmex.com/api/v1/spot/public/products/hur_eth/ticker', 8, 'coinmex', '2018-12-12 13:02:04', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (108, -1, '2018-12-12 13:02:04', 'http://opendata.baidu.com/api.php?query=1%E7%BE%8E%E5%85%83%E7%AD%89%E4%BA%8E%E5%A4%9A%E5%B0%91%E6%97%A5%E5%85%83&co=&resource_id=6017&t=1398159587150&ie=utf8&oe=gbk&cb=op_aladdin_callback&format=json&tn=baidu&cb=jQuery110205116758023097', -1, 'rate', '2018-12-12 13:02:04', 'usd_jpy', NULL, 1);
INSERT INTO `fetching_data_path` VALUES (109, -1, '2018-12-12 13:02:04', 'http://opendata.baidu.com/api.php?query=1%E7%BE%8E%E5%85%83%E7%AD%89%E4%BA%8E%E5%A4%9A%E5%B0%91%E6%AC%A7%E5%85%83&co=&resource_id=6017&t=1398159587150&ie=utf8&oe=gbk&cb=op_aladdin_callback&format=json&tn=baidu&cb=jQuery110205116758023097', -1, 'rate', '2018-12-12 13:02:04', 'usd_eur', NULL, 1);
INSERT INTO `fetching_data_path` VALUES (110, -1, '2018-12-12 13:02:04', 'http://opendata.baidu.com/api.php?query=1%E7%BE%8E%E5%85%83%E7%AD%89%E4%BA%8E%E5%A4%9A%E5%B0%91%E4%BF%84%E7%BD%97%E6%96%AF%E5%8D%A2%E5%B8%83&co=&resource_id=6017&t=1398159587150&ie=utf8&oe=gbk&cb=op_aladdin_callback&format=json&tn=baidu&cb=jQuery110205116758023097', -1, 'rate', '2018-12-12 13:02:04', 'usd_rub', NULL, 1);
INSERT INTO `fetching_data_path` VALUES (111, 300, '2018-12-12 13:02:04', 'https://api.huobi.pro/market/detail/merged?symbol=neousdt', 13, 'Huobi', '2019-01-25 12:25:22', NULL, NULL, 1);
INSERT INTO `fetching_data_path` VALUES (112, 301, '2018-12-12 13:02:04', 'https://api.binance.com/api/v1/ticker/24hr?symbol=NEOUSDT', 13, 'binance', '2019-01-25 12:25:22', NULL, NULL, 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
