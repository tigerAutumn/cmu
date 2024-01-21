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

 Date: 01/07/2019 14:57:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dict_country
-- ----------------------------
DROP TABLE IF EXISTS `dict_country`;
CREATE TABLE `dict_country` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `locale` varchar(10) NOT NULL,
  `abbr` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `capital` varchar(50) NOT NULL,
  `area_code` int(10) NOT NULL,
  `country_code` int(10) NOT NULL,
  `currency_code` varchar(20) NOT NULL,
  `currency_name` varchar(20) NOT NULL,
  `domain_code` varchar(10) NOT NULL,
  `letter_code2` varchar(10) NOT NULL,
  `letter_code3` varchar(10) NOT NULL,
  `status` int(10) NOT NULL DEFAULT '1',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_locale_name` (`locale`,`name`),
  UNIQUE KEY `uk_locale_country_code` (`locale`,`country_code`),
  KEY `ix_locale` (`locale`) USING BTREE,
  KEY `ix_name` (`name`) USING BTREE,
  KEY `ix_area_code` (`area_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=614 DEFAULT CHARSET=utf8 COMMENT='全球国家(地区)表';

-- ----------------------------
-- Records of dict_country
-- ----------------------------
BEGIN;
INSERT INTO `dict_country` VALUES (205, 'en-us', 'Afghanistan', 'Islamic State of Afghanistan', 'Kabul', 93, 4, 'AFN', 'Afghani', '.af', 'AF', 'AFG', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (206, 'en-us', 'Albania', 'Republic of Albania', 'Tirana', 355, 8, 'ALL', 'Lek', '.al', 'AL', 'ALB', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (207, 'en-us', 'Algeria', 'People\'s Democratic Republic of Algeria', 'Algiers', 213, 12, 'DZD', 'Dinar', '.dz', 'DZ', 'DZA', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (208, 'en-us', 'Andorra', 'Principality of Andorra', 'Andorra la Vella', 376, 20, 'EUR', 'Euro', '.ad', 'AD', 'AND', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (209, 'en-us', 'Angola', 'Republic of Angola', 'Luanda', 244, 24, 'AOA', 'Kwanza', '.ao', 'AO', 'AGO', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (210, 'en-us', 'Anguilla', 'Anguilla', 'The Valley', 1264, 660, 'XCD', 'Dollar', '.ai', 'AI', 'AIA', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (211, 'en-us', 'Antigua and Barbuda', 'Antigua and Barbuda', 'Saint John\'s', 1268, 28, 'XCD', 'Dollar', '.ag', 'AG', 'ATG', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (212, 'en-us', 'Argentina', 'Argentine Republic', 'Buenos Aires', 54, 32, 'ARS', 'Peso', '.ar', 'AR', 'ARG', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (213, 'en-us', 'Armenia', 'Republic of Armenia', 'Yerevan', 374, 51, 'AMD', 'Dram', '.am', 'AM', 'ARM', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (214, 'en-us', 'Australia', 'Commonwealth of Australia', 'Canberra', 61, 36, 'AUD', 'Dollar', '.au', 'AU', 'AUS', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (215, 'en-us', 'Austria', 'Republic of Austria', 'Vienna', 43, 40, 'EUR', 'Euro', '.at', 'AT', 'AUT', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (216, 'en-us', 'Azerbaijan', 'Republic of Azerbaijan', 'Baku', 994, 31, 'AZN', 'Manat', '.az', 'AZ', 'AZE', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (217, 'en-us', 'Bahamas', 'Commonwealth of The Bahamas', 'Nassau', 1242, 44, 'BSD', 'Dollar', '.bs', 'BS', 'BHS', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (218, 'en-us', 'Bahrain', 'Kingdom of Bahrain', 'Manama', 973, 48, 'BHD', 'Dinar', '.bh', 'BH', 'BHR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (219, 'en-us', 'Bangladesh', 'People&acute;s Republic of Bangladesh', 'Dhaka', 880, 50, 'BDT', 'Taka', '.bd', 'BD', 'BGD', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (220, 'en-us', 'Barbados', 'Barbados', 'Bridgetown', 1246, 52, 'BBD', 'Dollar', '.bb', 'BB', 'BRB', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (221, 'en-us', 'Belarus', 'Republic of Belarus', 'Minsk', 375, 112, 'BYR', 'Ruble', '.by', 'BY', 'BLR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (222, 'en-us', 'Belgium', 'Kingdom of Belgium', 'Brussels', 32, 56, 'EUR', 'Euro', '.be', 'BE', 'BEL', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (223, 'en-us', 'Belize', 'Belize', 'Belmopan', 501, 84, 'BZD', 'Dollar', '.bz', 'BZ', 'BLZ', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (224, 'en-us', 'Benin', 'Republic of Benin', 'Porto-Novo', 229, 204, 'XOF', 'Franc', '.bj', 'BJ', 'BEN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (225, 'en-us', 'Bermuda', 'Bermuda', 'Hamilton', 1441, 60, 'BMD', 'Dollar', '.bm', 'BM', 'BMU', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (226, 'en-us', 'Bhutan', 'Kingdom of Bhutan', 'Thimphu', 975, 64, 'BTN', 'Ngultrum', '.bt', 'BT', 'BTN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (227, 'en-us', 'Bolivia', 'Republic of Bolivia', 'La Paz (administrative/legislative) and Sucre (jud', 591, 68, 'BOB', 'Boliviano', '.bo', 'BO', 'BOL', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (228, 'en-us', 'Bosnia and Herzegovina', 'Bosnia and Herzegovina', 'Sarajevo', 387, 70, 'BAM', 'Marka', '.ba', 'BA', 'BIH', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (229, 'en-us', 'Botswana', 'Republic of Botswana', 'Gaborone', 267, 72, 'BWP', 'Pula', '.bw', 'BW', 'BWA', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (230, 'en-us', 'Brazil', 'Federative Republic of Brazil', 'Brasilia', 55, 76, 'BRL', 'Real', '.br', 'BR', 'BRA', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (231, 'en-us', 'British Virgin Islands', 'British Virgin Islands', 'Road Town', 1284, 92, 'USD', 'Dollar', '.vg', 'VG', 'VGB', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (232, 'en-us', 'Brunei', 'Negara Brunei Darussalam', 'Bandar Seri Begawan', 673, 96, 'BND', 'Dollar', '.bn', 'BN', 'BRN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (233, 'en-us', 'Bulgaria', 'Republic of Bulgaria', 'Sofia', 359, 100, 'BGN', 'Lev', '.bg', 'BG', 'BGR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (234, 'en-us', 'Burkina Faso', 'Burkina Faso', 'Ouagadougou', 226, 854, 'XOF', 'Franc', '.bf', 'BF', 'BFA', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (235, 'en-us', 'Burundi', 'Republic of Burundi', 'Bujumbura', 257, 108, 'BIF', 'Franc', '.bi', 'BI', 'BDI', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (236, 'en-us', 'Cambodia', 'Kingdom of Cambodia', 'Phnom Penh', 855, 116, 'KHR', 'Riels', '.kh', 'KH', 'KHM', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (237, 'en-us', 'Cameroon', 'Republic of Cameroon', 'Yaounde', 237, 120, 'XAF', 'Franc', '.cm', 'CM', 'CMR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (238, 'en-us', 'Canada', 'Canada', 'Ottawa', 1, 124, 'CAD', 'Dollar', '.ca', 'CA', 'CAN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (239, 'en-us', 'Cape Verde', 'Republic of Cape Verde', 'Praia', 238, 132, 'CVE', 'Escudo', '.cv', 'CV', 'CPV', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (240, 'en-us', 'Cayman Islands', 'Cayman Islands', 'George Town', 1345, 136, 'KYD', 'Dollar', '.ky', 'KY', 'CYM', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (241, 'en-us', 'Central African Republic', 'null', 'Bangui', 236, 140, 'XAF', 'Franc', '.cf', 'CF', 'CAF', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (242, 'en-us', 'Chad', 'Republic of Chad', 'N\'Djamena', 235, 148, 'XAF', 'Franc', '.td', 'TD', 'TCD', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (243, 'en-us', 'Chile', 'Republic of Chile', 'Santiago (administrative/judical) and Valparaiso (', 56, 152, 'CLP', 'Peso', '.cl', 'CL', 'CHL', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (244, 'en-us', 'Colombia', 'Republic of Colombia', 'Bogota', 57, 170, 'COP', 'Peso', '.co', 'CO', 'COL', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (245, 'en-us', 'Comoros', 'Union of Comoros', 'Moroni', 269, 174, 'KMF', 'Franc', '.km', 'KM', 'COM', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (246, 'en-us', 'Congo, Republic', 'Republic of the Congo', 'Brazzaville', 242, 178, 'XAF', 'Franc', '.cg', 'CG', 'COG', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (247, 'en-us', 'Congo, Democratic Republic', 'Democratic Republic of the Congo', 'Kinshasa', 243, 180, 'CDF', 'Franc', '.cd', 'CD', 'COD', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (248, 'en-us', 'Costa Rica', 'Republic of Costa Rica', 'San Jose', 506, 188, 'CRC', 'Colon', '.cr', 'CR', 'CRI', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (249, 'en-us', 'Cote d&acute;Ivoire &#40;Ivory Coast&#41;', 'Republic of Cote d&acute;Ivoire', 'Yamoussoukro', 225, 384, 'XOF', 'Franc', '.ci', 'CI', 'CIV', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (250, 'en-us', 'Croatia', 'Republic of Croatia', 'Zagreb', 385, 191, 'HRK', 'Kuna', '.hr', 'HR', 'HRV', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (251, 'en-us', 'Cuba', 'Republic of Cuba', 'Havana', 53, 192, 'CUP', 'Peso', '.cu', 'CU', 'CUB', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (252, 'en-us', 'Cyprus', 'Republic of Cyprus', 'Nicosia', 357, 196, 'CYP', 'Pound', '.cy', 'CY', 'CYP', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (253, 'en-us', 'Czech Republic', 'Czech Republic', 'Prague', 420, 203, 'CZK', 'Koruna', '.cz', 'CZ', 'CZE', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (254, 'en-us', 'Denmark', 'Kingdom of Denmark', 'Copenhagen', 45, 208, 'DKK', 'Krone', '.dk', 'DK', 'DNK', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (255, 'en-us', 'Djibouti', 'Republic of Djibouti', 'Djibouti', 253, 262, 'DJF', 'Franc', '.dj', 'DJ', 'DJI', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (256, 'en-us', 'Dominica', 'Commonwealth of Dominica', 'Roseau', 1767, 212, 'XCD', 'Dollar', '.dm', 'DM', 'DMA', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (257, 'en-us', 'Dominican Republic', 'Dominican Republic', 'Santo Domingo', 1849, 214, 'DOP', 'Peso', '.do', 'DO', 'DOM', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (258, 'en-us', 'Ecuador', 'Republic of Ecuador', 'Quito', 593, 218, 'USD', 'Dollar', '.ec', 'EC', 'ECU', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (259, 'en-us', 'Egypt', 'Arab Republic of Egypt', 'Cairo', 20, 818, 'EGP', 'Pound', '.eg', 'EG', 'EGY', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (260, 'en-us', 'El Salvador', 'Republic of El Salvador', 'San Salvador', 503, 222, 'USD', 'Dollar', '.sv', 'SV', 'SLV', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (261, 'en-us', 'Equatorial Guinea', 'Republic of Equatorial Guinea', 'Malabo', 240, 226, 'XAF', 'Franc', '.gq', 'GQ', 'GNQ', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (262, 'en-us', 'Eritrea', 'State of Eritrea', 'Asmara', 291, 232, 'ERN', 'Nakfa', '.er', 'ER', 'ERI', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (263, 'en-us', 'Estonia', 'Republic of Estonia', 'Tallinn', 372, 233, 'EEK', 'Kroon', '.ee', 'EE', 'EST', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (264, 'en-us', 'Ethiopia', 'Federal Democratic Republic of Ethiopia', 'Addis Ababa', 251, 231, 'ETB', 'Birr', '.et', 'ET', 'ETH', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (265, 'en-us', 'Fiji', 'Republic of the Fiji Islands', 'Suva', 679, 242, 'FJD', 'Dollar', '.fj', 'FJ', 'FJI', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (266, 'en-us', 'Finland', 'Republic of Finland', 'Helsinki', 358, 246, 'EUR', 'Euro', '.fi', 'FI', 'FIN', 1, '2018-04-27 09:36:32', '2018-07-27 12:12:19');
INSERT INTO `dict_country` VALUES (267, 'en-us', 'France', 'French Republic', 'Paris', 33, 250, 'EUR', 'Euro', '.fr', 'FR', 'FRA', 1, '2018-04-27 09:36:32', '2018-07-27 12:08:25');
INSERT INTO `dict_country` VALUES (268, 'en-us', 'Gabon', 'Gabonese Republic', 'Libreville', 241, 266, 'XAF', 'Franc', '.ga', 'GA', 'GAB', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (269, 'en-us', 'Gambia, The', 'Republic of The Gambia', 'Banjul', 220, 270, 'GMD', 'Dalasi', '.gm', 'GM', 'GMB', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (270, 'en-us', 'Georgia', 'Republic of Georgia', 'Tbilisi', 995, 268, 'GEL', 'Lari', '.ge', 'GE', 'GEO', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (271, 'en-us', 'Germany', 'Federal Republic of Germany', 'Berlin', 49, 276, 'EUR', 'Euro', '.de', 'DE', 'DEU', 1, '2018-04-27 09:36:32', '2018-07-27 12:09:10');
INSERT INTO `dict_country` VALUES (272, 'en-us', 'Ghana', 'Republic of Ghana', 'Accra', 233, 288, 'GHC', 'Cedi', '.gh', 'GH', 'GHA', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (273, 'en-us', 'Greece', 'Hellenic Republic', 'Athens', 30, 300, 'EUR', 'Euro', '.gr', 'GR', 'GRC', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (274, 'en-us', 'Grenada', 'Grenada', 'Saint George\'s', 1473, 308, 'XCD', 'Dollar', '.gd', 'GD', 'GRD', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (275, 'en-us', 'Guatemala', 'Republic of Guatemala', 'Guatemala', 502, 320, 'GTQ', 'Quetzal', '.gt', 'GT', 'GTM', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (276, 'en-us', 'Guinea', 'Republic of Guinea', 'Conakry', 224, 324, 'GNF', 'Franc', '.gn', 'GN', 'GIN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (277, 'en-us', 'Guinea-Bissau', 'Republic of Guinea-Bissau', 'Bissau', 245, 624, 'XOF', 'Franc', '.gw', 'GW', 'GNB', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (278, 'en-us', 'Guyana', 'Co-operative Republic of Guyana', 'Georgetown', 592, 328, 'GYD', 'Dollar', '.gy', 'GY', 'GUY', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (279, 'en-us', 'Haiti', 'Republic of Haiti', 'Port-au-Prince', 509, 332, 'HTG', 'Gourde', '.ht', 'HT', 'HTI', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (280, 'en-us', 'Honduras', 'Republic of Honduras', 'Tegucigalpa', 504, 340, 'HNL', 'Lempira', '.hn', 'HN', 'HND', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (281, 'en-us', 'Hong Kong', 'Hong Kong Special Administrative Region', 'null', 852, 344, 'HKD', 'Dollar', '.hk', 'HK', 'HKG', 1, '2018-04-27 09:36:32', '2018-07-12 13:16:58');
INSERT INTO `dict_country` VALUES (282, 'en-us', 'Hungary', 'Republic of Hungary', 'Budapest', 36, 348, 'HUF', 'Forint', '.hu', 'HU', 'HUN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (283, 'en-us', 'Iceland', 'Republic of Iceland', 'Reykjavik', 354, 352, 'ISK', 'Krona', '.is', 'IS', 'ISL', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (284, 'en-us', 'India', 'Republic of India', 'New Delhi', 91, 356, 'INR', 'Rupee', '.in', 'IN', 'IND', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (285, 'en-us', 'Indonesia', 'Republic of Indonesia', 'Jakarta', 62, 360, 'IDR', 'Rupiah', '.id', 'ID', 'IDN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (286, 'en-us', 'Iran', 'Islamic Republic of Iran', 'Tehran', 98, 364, 'IRR', 'Rial', '.ir', 'IR', 'IRN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (287, 'en-us', 'Iraq', 'Republic of Iraq', 'Baghdad', 964, 368, 'IQD', 'Dinar', '.iq', 'IQ', 'IRQ', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (288, 'en-us', 'Ireland', 'Ireland', 'Dublin', 353, 372, 'EUR', 'Euro', '.ie', 'IE', 'IRL', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (289, 'en-us', 'Israel', 'State of Israel', 'Jerusalem', 972, 376, 'ILS', 'Shekel', '.il', 'IL', 'ISR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (290, 'en-us', 'Italy', 'Italian Republic', 'Rome', 39, 380, 'EUR', 'Euro', '.it', 'IT', 'ITA', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (291, 'en-us', 'Jamaica', 'Jamaica', 'Kingston', 1876, 388, 'JMD', 'Dollar', '.jm', 'JM', 'JAM', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (292, 'en-us', 'Japan', 'Japan', 'Tokyo', 81, 392, 'JPY', 'Yen', '.jp', 'JP', 'JPN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (293, 'en-us', 'Jordan', 'Hashemite Kingdom of Jordan', 'Amman', 962, 400, 'JOD', 'Dinar', '.jo', 'JO', 'JOR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (294, 'en-us', 'Kazakhstan', 'Republic of Kazakhstan', 'Astana', 7, 398, 'KZT', 'Tenge', '.kz', 'KZ', 'KAZ', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (295, 'en-us', 'Kenya', 'Republic of Kenya', 'Nairobi', 254, 404, 'KES', 'Shilling', '.ke', 'KE', 'KEN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (296, 'en-us', 'Kiribati', 'Republic of Kiribati', 'Tarawa', 686, 296, 'AUD', 'Dollar', '.ki', 'KI', 'KIR', 1, '2018-04-27 09:36:32', '2018-08-06 11:27:02');
INSERT INTO `dict_country` VALUES (297, 'en-us', 'Korea, North', 'Democratic People\'s Republic of Korea', 'Pyongyang', 850, 408, 'KPW', 'Won', '.kp', 'KP', 'PRK', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (298, 'en-us', 'South Korea', 'Republic of Korea', 'Seoul', 82, 410, 'KRW', 'Won', '.kr', 'KR', 'KOR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (299, 'en-us', 'Kuwait', 'State of Kuwait', 'Kuwait', 965, 414, 'KWD', 'Dinar', '.kw', 'KW', 'KWT', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (300, 'en-us', 'Kyrgyzstan', 'Kyrgyz Republic', 'Bishkek', 996, 417, 'KGS', 'Som', '.kg', 'KG', 'KGZ', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (301, 'en-us', 'Laos', 'Lao People&acute;s Democratic Republic', 'Vientiane', 856, 418, 'LAK', 'Kip', '.la', 'LA', 'LAO', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (302, 'en-us', 'Latvia', 'Republic of Latvia', 'Riga', 371, 428, 'LVL', 'Lat', '.lv', 'LV', 'LVA', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (303, 'en-us', 'Lebanon', 'Lebanese Republic', 'Beirut', 961, 422, 'LBP', 'Pound', '.lb', 'LB', 'LBN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (304, 'en-us', 'Lesotho', 'Kingdom of Lesotho', 'Maseru', 266, 426, 'LSL', 'Loti', '.ls', 'LS', 'LSO', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (305, 'en-us', 'Liberia', 'Republic of Liberia', 'Monrovia', 231, 430, 'LRD', 'Dollar', '.lr', 'LR', 'LBR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (306, 'en-us', 'Libya', 'Great Socialist People&acute;s Libyan Arab Jamahiriya', 'Tripoli', 218, 434, 'LYD', 'Dinar', '.ly', 'LY', 'LBY', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (307, 'en-us', 'Liechtenstein', 'Principality of Liechtenstein', 'Vaduz', 423, 438, 'CHF', 'Franc', '.li', 'LI', 'LIE', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (308, 'en-us', 'Lithuania', 'Republic of Lithuania', 'Vilnius', 370, 440, 'LTL', 'Litas', '.lt', 'LT', 'LTU', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (309, 'en-us', 'Luxembourg', 'Grand Duchy of Luxembourg', 'Luxembourg', 352, 442, 'EUR', 'Euro', '.lu', 'LU', 'LUX', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (310, 'en-us', 'Macau', 'Macau Special Administrative Region', 'Macau', 853, 446, 'MOP', 'Pataca', '.mo', 'MO', 'MAC', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (311, 'en-us', 'Macedonia', 'Republic of Macedonia', 'Skopje', 389, 807, 'MKD', 'Denar', '.mk', 'MK', 'MKD', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (312, 'en-us', 'Madagascar', 'Republic of Madagascar', 'Antananarivo', 261, 450, 'MGA', 'Ariary', '.mg', 'MG', 'MDG', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (313, 'en-us', 'Malawi', 'Republic of Malawi', 'Lilongwe', 265, 454, 'MWK', 'Kwacha', '.mw', 'MW', 'MWI', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (314, 'en-us', 'Malaysia', 'Malaysia', 'Kuala Lumpur (legislative/judical) and Putrajaya (', 60, 458, 'MYR', 'Ringgit', '.my', 'MY', 'MYS', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (315, 'en-us', 'Maldives', 'Republic of Maldives', 'Male', 960, 462, 'MVR', 'Rufiyaa', '.mv', 'MV', 'MDV', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (316, 'en-us', 'Mali', 'Republic of Mali', 'Bamako', 223, 466, 'XOF', 'Franc', '.ml', 'ML', 'MLI', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (317, 'en-us', 'Malta', 'Republic of Malta', 'Valletta', 356, 470, 'MTL', 'Lira', '.mt', 'MT', 'MLT', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (318, 'en-us', 'Marshall Islands', 'Republic of the Marshall Islands', 'Majuro', 692, 584, 'USD', 'Dollar', '.mh', 'MH', 'MHL', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (319, 'en-us', 'Mauritania', 'Islamic Republic of Mauritania', 'Nouakchott', 222, 478, 'MRO', 'Ouguiya', '.mr', 'MR', 'MRT', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (320, 'en-us', 'Mauritius', 'Republic of Mauritius', 'Port Louis', 230, 480, 'MUR', 'Rupee', '.mu', 'MU', 'MUS', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (321, 'en-us', 'Mexico', 'United Mexican States', 'Mexico', 52, 484, 'MXN', 'Peso', '.mx', 'MX', 'MEX', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (322, 'en-us', 'Micronesia', 'Federated States of Micronesia', 'Palikir', 691, 583, 'USD', 'Dollar', '.fm', 'FM', 'FSM', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (323, 'en-us', 'Moldova', 'Republic of Moldova', 'Chisinau', 373, 498, 'MDL', 'Leu', '.md', 'MD', 'MDA', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (324, 'en-us', 'Monaco', 'Principality of Monaco', 'Monaco', 377, 492, 'EUR', 'Euro', '.mc', 'MC', 'MCO', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (325, 'en-us', 'Mongolia', 'Mongolia', 'Ulaanbaatar', 976, 496, 'MNT', 'Tugrik', '.mn', 'MN', 'MNG', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (326, 'en-us', 'Montenegro', 'Republic of Montenegro', 'Podgorica', 382, 499, 'EUR', 'Euro', '.me and .y', 'ME', 'MNE', 1, '2018-04-27 09:36:32', '2018-08-06 11:27:02');
INSERT INTO `dict_country` VALUES (327, 'en-us', 'Morocco', 'Kingdom of Morocco', 'Rabat', 212, 504, 'MAD', 'Dirham', '.ma', 'MA', 'MAR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (328, 'en-us', 'Mozambique', 'Republic of Mozambique', 'Maputo', 258, 508, 'MZM', 'Meticail', '.mz', 'MZ', 'MOZ', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (329, 'en-us', 'Myanmar &#40;Burma&#41;', 'Union of Myanmar', 'Naypyidaw', 95, 104, 'MMK', 'Kyat', '.mm', 'MM', 'MMR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (330, 'en-us', 'Namibia', 'Republic of Namibia', 'Windhoek', 264, 516, 'NAD', 'Dollar', '.na', 'NA', 'NAM', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (331, 'en-us', 'Nauru', 'Republic of Nauru', 'Yaren', 674, 520, 'AUD', 'Dollar', '.nr', 'NR', 'NRU', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (332, 'en-us', 'Nepal', 'Nepal', 'Kathmandu', 977, 524, 'NPR', 'Rupee', '.np', 'NP', 'NPL', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (333, 'en-us', 'Netherlands', 'Kingdom of the Netherlands', 'Amsterdam (administrative) and The Hague (legislat', 31, 528, 'EUR', 'Euro', '.nl', 'NL', 'NLD', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (334, 'en-us', 'New Zealand', 'New Zealand', 'Wellington', 64, 554, 'NZD', 'Dollar', '.nz', 'NZ', 'NZL', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (335, 'en-us', 'Nicaragua', 'Republic of Nicaragua', 'Managua', 505, 558, 'NIO', 'Cordoba', '.ni', 'NI', 'NIC', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (336, 'en-us', 'Niger', 'Republic of Niger', 'Niamey', 227, 562, 'XOF', 'Franc', '.ne', 'NE', 'NER', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (337, 'en-us', 'Nigeria', 'Federal Republic of Nigeria', 'Abuja', 234, 566, 'NGN', 'Naira', '.ng', 'NG', 'NGA', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (338, 'en-us', 'Norway', 'Kingdom of Norway', 'Oslo', 47, 578, 'NOK', 'Krone', '.no', 'NO', 'NOR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (339, 'en-us', 'Oman', 'Sultanate of Oman', 'Muscat', 968, 512, 'OMR', 'Rial', '.om', 'OM', 'OMN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (340, 'en-us', 'Pakistan', 'Islamic Republic of Pakistan', 'Islamabad', 92, 586, 'PKR', 'Rupee', '.pk', 'PK', 'PAK', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (341, 'en-us', 'Palau', 'Republic of Palau', 'Melekeok', 680, 585, 'USD', 'Dollar', '.pw', 'PW', 'PLW', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (342, 'en-us', 'Panama', 'Republic of Panama', 'Panama', 507, 591, 'PAB', 'Balboa', '.pa', 'PA', 'PAN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (343, 'en-us', 'Papua New Guinea', 'Independent State of Papua New Guinea', 'Port Moresby', 675, 598, 'PGK', 'Kina', '.pg', 'PG', 'PNG', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (344, 'en-us', 'Paraguay', 'Republic of Paraguay', 'Asuncion', 595, 600, 'PYG', 'Guarani', '.py', 'PY', 'PRY', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (345, 'en-us', 'Peru', 'Republic of Peru', 'Lima', 51, 604, 'PEN', 'Sol', '.pe', 'PE', 'PER', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (346, 'en-us', 'Philippines', 'Republic of the Philippines', 'Manila', 63, 608, 'PHP', 'Peso', '.ph', 'PH', 'PHL', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (347, 'en-us', 'Poland', 'Republic of Poland', 'Warsaw', 48, 616, 'PLN', 'Zloty', '.pl', 'PL', 'POL', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (348, 'en-us', 'Portugal', 'Portuguese Republic', 'Lisbon', 351, 620, 'EUR', 'Euro', '.pt', 'PT', 'PRT', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (349, 'en-us', 'Qatar', 'State of Qatar', 'Doha', 974, 634, 'QAR', 'Rial', '.qa', 'QA', 'QAT', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (350, 'en-us', 'Romania', 'Romania', 'Bucharest', 40, 642, 'RON', 'Leu', '.ro', 'RO', 'ROU', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (351, 'en-us', 'Russia', 'Russian Federation', 'Moscow', 7, 643, 'RUB', 'Ruble', '.ru and .s', 'RU', 'RUS', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (352, 'en-us', 'Rwanda', 'Republic of Rwanda', 'Kigali', 250, 646, 'RWF', 'Franc', '.rw', 'RW', 'RWA', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (353, 'en-us', 'Saint Kitts and Nevis', 'Federation of Saint Kitts and Nevis', 'Basseterre', 1869, 659, 'XCD', 'Dollar', '.kn', 'KN', 'KNA', 1, '2018-04-27 09:36:32', '2018-08-06 11:27:02');
INSERT INTO `dict_country` VALUES (354, 'en-us', 'Saint Lucia', 'Saint Lucia', 'Castries', 1758, 662, 'XCD', 'Dollar', '.lc', 'LC', 'LCA', 1, '2018-04-27 09:36:32', '2018-08-06 11:27:02');
INSERT INTO `dict_country` VALUES (355, 'en-us', 'Saint Vincent and the Grenadines', 'Saint Vincent and the Grenadines', 'Kingstown', 1784, 670, 'XCD', 'Dollar', '.vc', 'VC', 'VCT', 1, '2018-04-27 09:36:32', '2018-08-06 11:27:02');
INSERT INTO `dict_country` VALUES (356, 'en-us', 'Samoa', 'Independent State of Samoa', 'Apia', 685, 882, 'WST', 'Tala', '.ws', 'WS', 'WSM', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (357, 'en-us', 'San Marino', 'Republic of San Marino', 'San Marino', 378, 674, 'EUR', 'Euro', '.sm', 'SM', 'SMR', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (358, 'en-us', 'Sao Tome and Principe', 'Democratic Republic of Sao Tome and Principe', 'Sao Tome', 239, 678, 'STD', 'Dobra', '.st', 'ST', 'STP', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (359, 'en-us', 'Saudi Arabia', 'Kingdom of Saudi Arabia', 'Riyadh', 966, 682, 'SAR', 'Rial', '.sa', 'SA', 'SAU', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (360, 'en-us', 'Senegal', 'Republic of Senegal', 'Dakar', 221, 686, 'XOF', 'Franc', '.sn', 'SN', 'SEN', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (361, 'en-us', 'Serbia', 'Republic of Serbia', 'Belgrade', 381, 688, 'RSD', 'Dinar', '.rs and .y', 'RS', 'SRB', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (362, 'en-us', 'Seychelles', 'Republic of Seychelles', 'Victoria', 248, 690, 'SCR', 'Rupee', '.sc', 'SC', 'SYC', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (363, 'en-us', 'Sierra Leone', 'Republic of Sierra Leone', 'Freetown', 232, 694, 'SLL', 'Leone', '.sl', 'SL', 'SLE', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (364, 'en-us', 'Singapore', 'Republic of Singapore', 'Singapore', 65, 702, 'SGD', 'Dollar', '.sg', 'SG', 'SGP', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (365, 'en-us', 'Slovakia', 'Slovak Republic', 'Bratislava', 421, 703, 'SKK', 'Koruna', '.sk', 'SK', 'SVK', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (366, 'en-us', 'Slovenia', 'Republic of Slovenia', 'Ljubljana', 386, 705, 'EUR', 'Euro', '.si', 'SI', 'SVN', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (367, 'en-us', 'Solomon Islands', 'Solomon Islands', 'Honiara', 677, 90, 'SBD', 'Dollar', '.sb', 'SB', 'SLB', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (368, 'en-us', 'Somalia', 'Somalia', 'Mogadishu', 252, 706, 'SOS', 'Shilling', '.so', 'SO', 'SOM', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (369, 'en-us', 'South Africa', 'Republic of South Africa', 'Pretoria (administrative), Cape Town (legislative)', 27, 710, 'ZAR', 'Rand', '.za', 'ZA', 'ZAF', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (370, 'en-us', 'Spain', 'Kingdom of Spain', 'Madrid', 34, 724, 'EUR', 'Euro', '.es', 'ES', 'ESP', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (371, 'en-us', 'Sri Lanka', 'Democratic Socialist Republic of Sri Lanka', 'Colombo (administrative/judical) and Sri Jayewarde', 94, 144, 'LKR', 'Rupee', '.lk', 'LK', 'LKA', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (372, 'en-us', 'Sudan', 'Republic of the Sudan', 'Khartoum', 249, 729, 'SDD', 'Dinar', '.sd', 'SD', 'SDN', 1, '2018-05-19 07:13:09', '2018-05-19 07:13:09');
INSERT INTO `dict_country` VALUES (373, 'en-us', 'Suriname', 'Republic of Suriname', 'Paramaribo', 597, 740, 'SRD', 'Dollar', '.sr', 'SR', 'SUR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (374, 'en-us', 'Swaziland', 'Kingdom of Swaziland', 'Mbabane (administrative) and Lobamba (legislative)', 268, 748, 'SZL', 'Lilangeni', '.sz', 'SZ', 'SWZ', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (375, 'en-us', 'Sweden', 'Kingdom of Sweden', 'Stockholm', 46, 752, 'SEK', 'Kronoa', '.se', 'SE', 'SWE', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (376, 'en-us', 'Switzerland', 'Swiss Confederation', 'Bern', 41, 756, 'CHF', 'Franc', '.ch', 'CH', 'CHE', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (377, 'en-us', 'Syria', 'Syrian Arab Republic', 'Damascus', 963, 760, 'SYP', 'Pound', '.sy', 'SY', 'SYR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (378, 'en-us', 'Taiwan', 'Taiwan, China', 'Taipei', 886, 158, 'TWD', 'Dollar', '.tw', 'TW', 'TWN', 1, '2018-05-19 07:42:28', '2018-05-27 02:39:16');
INSERT INTO `dict_country` VALUES (379, 'en-us', 'Tajikistan', 'Republic of Tajikistan', 'Dushanbe', 992, 762, 'TJS', 'Somoni', '.tj', 'TJ', 'TJK', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (380, 'en-us', 'Tanzania', 'United Republic of Tanzania', 'Dar es Salaam (administrative/judical) and Dodoma ', 255, 834, 'TZS', 'Shilling', '.tz', 'TZ', 'TZA', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (381, 'en-us', 'Thailand', 'Kingdom of Thailand', 'Bangkok', 66, 764, 'THB', 'Baht', '.th', 'TH', 'THA', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (382, 'en-us', 'Timor-Leste (East Timor)', 'Democratic Republic of Timor-Leste', 'Dili', 670, 626, 'USD', 'Dollar', '.tp and .t', 'TL', 'TLS', 1, '2018-04-27 09:36:32', '2018-08-06 11:27:02');
INSERT INTO `dict_country` VALUES (383, 'en-us', 'Togo', 'Togolese Republic', 'Lome', 228, 768, 'XOF', 'Franc', '.tg', 'TG', 'TGO', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (384, 'en-us', 'Tonga', 'Kingdom of Tonga', 'Nuku\'alofa', 676, 776, 'TOP', 'Pa\'anga', '.to', 'TO', 'TON', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (385, 'en-us', 'Trinidad and Tobago', 'Republic of Trinidad and Tobago', 'Port-of-Spain', 1868, 780, 'TTD', 'Dollar', '.tt', 'TT', 'TTO', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (386, 'en-us', 'Tunisia', 'Tunisian Republic', 'Tunis', 216, 788, 'TND', 'Dinar', '.tn', 'TN', 'TUN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (387, 'en-us', 'Turkey', 'Republic of Turkey', 'Ankara', 90, 792, 'TRY', 'Lira', '.tr', 'TR', 'TUR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (388, 'en-us', 'Turkmenistan', 'Turkmenistan', 'Ashgabat', 993, 795, 'TMM', 'Manat', '.tm', 'TM', 'TKM', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (389, 'en-us', 'Tuvalu', 'Tuvalu', 'Funafuti', 688, 798, 'AUD', 'Dollar', '.tv', 'TV', 'TUV', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (390, 'en-us', 'U.S. Virgin Islands', 'United States Virgin Islands', 'Charlotte Amalie', 1340, 850, 'USD', 'Dollar', '.vi', 'VI', 'VIR', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (391, 'en-us', 'Uganda', 'Republic of Uganda', 'Kampala', 256, 800, 'UGX', 'Shilling', '.ug', 'UG', 'UGA', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (392, 'en-us', 'Ukraine', 'Ukraine', 'Kiev', 380, 804, 'UAH', 'Hryvnia', '.ua', 'UA', 'UKR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (393, 'en-us', 'United Arab Emirates', 'United Arab Emirates', 'Abu Dhabi', 971, 784, 'AED', 'Dirham', '.ae', 'AE', 'ARE', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (394, 'en-us', 'United Kingdom', 'United Kingdom of Great Britain and Northern Ireland', 'London', 44, 826, 'GBP', 'Pound', '.uk', 'GB', 'GBR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (395, 'en-us', 'United States', 'United States of America', 'Washington', 1, 840, 'USD', 'Dollar', '.us', 'US', 'USA', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (396, 'en-us', 'Uruguay', 'Oriental Republic of Uruguay', 'Montevideo', 598, 858, 'UYU', 'Peso', '.uy', 'UY', 'URY', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (397, 'en-us', 'Uzbekistan', 'Republic of Uzbekistan', 'Tashkent', 998, 860, 'UZS', 'Som', '.uz', 'UZ', 'UZB', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (398, 'en-us', 'Vanuatu', 'Republic of Vanuatu', 'Port-Vila', 678, 548, 'VUV', 'Vatu', '.vu', 'VU', 'VUT', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (399, 'en-us', 'Vatican City', 'State of the Vatican City', 'Vatican City', 39, 336, 'EUR', 'Euro', '.va', 'VA', 'VAT', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (400, 'en-us', 'Venezuela', 'Bolivarian Republic of Venezuela', 'Caracas', 58, 862, 'VEB', 'Bolivar', '.ve', 'VE', 'VEN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (401, 'en-us', 'Vietnam', 'Socialist Republic of Vietnam', 'Hanoi', 84, 704, 'VND', 'Dong', '.vn', 'VN', 'VNM', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (402, 'en-us', 'Yemen', 'Republic of Yemen', 'Sanaa', 967, 887, 'YER', 'Rial', '.ye', 'YE', 'YEM', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (403, 'en-us', 'Zambia', 'Republic of Zambia', 'Lusaka', 260, 894, 'ZMK', 'Kwacha', '.zm', 'ZM', 'ZMB', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (404, 'en-us', 'Zimbabwe', 'Republic of Zimbabwe', 'Harare', 263, 716, 'ZWD', 'Dollar', '.zw', 'ZW', 'ZWE', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (405, 'en-us', 'Kosovo', 'The Republic of Kosovo', 'Pristinaa', 383, 2000, 'EUR', 'Euro', '.xk', 'XK', 'XKX', 1, '2018-05-19 07:55:07', '2018-05-19 07:55:07');
INSERT INTO `dict_country` VALUES (406, 'en-us', 'South Sudan', 'Republic of South Sudan', 'Juba', 211, 728, 'SSP', 'South Sudanese pound', '.ss', 'SS', 'SSD', 1, '2018-05-19 07:25:13', '2018-05-19 07:25:13');
INSERT INTO `dict_country` VALUES (407, 'en-us', 'China', 'People´s Republic of China', 'Beijing', 86, 156, 'CNY', 'RMB', '.cn', 'CN', 'CHN', 1, '2018-05-19 07:19:14', '2018-05-19 07:19:14');
INSERT INTO `dict_country` VALUES (408, 'en-us', 'Palestine', 'State of Palestine', 'East Jerusalem', 970, 275, 'ILS', 'SHEKEL', '.ps', 'PS', 'PSE', 1, '2018-05-19 07:37:32', '2018-05-19 07:37:32');
INSERT INTO `dict_country` VALUES (410, 'zh-cn', 'Afghanistan', '阿富汗', 'Kabul', 93, 4, 'AFN', 'Afghani', '.af', 'AF', 'AFG', 1, '2018-04-27 09:36:32', '2018-07-12 15:40:58');
INSERT INTO `dict_country` VALUES (411, 'zh-cn', 'Albania', '阿尔巴尼亚', 'Tirana', 355, 8, 'ALL', 'Lek', '.al', 'AL', 'ALB', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (412, 'zh-cn', 'Algeria', '阿尔及利亚', 'Algiers', 213, 12, 'DZD', 'Dinar', '.dz', 'DZ', 'DZA', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (413, 'zh-cn', 'Andorra', '安道尔', 'Andorra la Vella', 376, 20, 'EUR', 'Euro', '.ad', 'AD', 'AND', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (414, 'zh-cn', 'Angola', '安哥拉', 'Luanda', 244, 24, 'AOA', 'Kwanza', '.ao', 'AO', 'AGO', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (415, 'zh-cn', 'Anguilla', '安圭拉', 'The Valley', 1264, 660, 'XCD', 'Dollar', '.ai', 'AI', 'AIA', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (416, 'zh-cn', 'Antigua and Barbuda', '安提瓜和巴布达', 'Saint John\'s', 1268, 28, 'XCD', 'Dollar', '.ag', 'AG', 'ATG', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (417, 'zh-cn', 'Argentina', '阿根廷', 'Buenos Aires', 54, 32, 'ARS', 'Peso', '.ar', 'AR', 'ARG', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (418, 'zh-cn', 'Armenia', '亚美尼亚', 'Yerevan', 374, 51, 'AMD', 'Dram', '.am', 'AM', 'ARM', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (419, 'zh-cn', 'Australia', '澳大利亚', 'Canberra', 61, 36, 'AUD', 'Dollar', '.au', 'AU', 'AUS', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (420, 'zh-cn', 'Austria', '奥地利', 'Vienna', 43, 40, 'EUR', 'Euro', '.at', 'AT', 'AUT', 1, '2018-04-27 09:36:32', '2018-07-27 12:05:46');
INSERT INTO `dict_country` VALUES (421, 'zh-cn', 'Azerbaijan', '阿塞拜疆', 'Baku', 994, 31, 'AZN', 'Manat', '.az', 'AZ', 'AZE', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (422, 'zh-cn', 'Bahamas', '巴哈马', 'Nassau', 1242, 44, 'BSD', 'Dollar', '.bs', 'BS', 'BHS', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (423, 'zh-cn', 'Bahrain', '巴林', 'Manama', 973, 48, 'BHD', 'Dinar', '.bh', 'BH', 'BHR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (424, 'zh-cn', 'Bangladesh', '孟加拉国', 'Dhaka', 880, 50, 'BDT', 'Taka', '.bd', 'BD', 'BGD', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (425, 'zh-cn', 'Barbados', '巴巴多斯', 'Bridgetown', 1246, 52, 'BBD', 'Dollar', '.bb', 'BB', 'BRB', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (426, 'zh-cn', 'Belarus', '白俄罗斯', 'Minsk', 375, 112, 'BYR', 'Ruble', '.by', 'BY', 'BLR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (427, 'zh-cn', 'Belgium', '比利时', 'Brussels', 32, 56, 'EUR', 'Euro', '.be', 'BE', 'BEL', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (428, 'zh-cn', 'Belize', '伯利兹', 'Belmopan', 501, 84, 'BZD', 'Dollar', '.bz', 'BZ', 'BLZ', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (429, 'zh-cn', 'Benin', '贝宁', 'Porto-Novo', 229, 204, 'XOF', 'Franc', '.bj', 'BJ', 'BEN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (430, 'zh-cn', 'Bermuda', '百慕大', 'Hamilton', 1441, 60, 'BMD', 'Dollar', '.bm', 'BM', 'BMU', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (431, 'zh-cn', 'Bhutan', '不丹', 'Thimphu', 975, 64, 'BTN', 'Ngultrum', '.bt', 'BT', 'BTN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (432, 'zh-cn', 'Bolivia', '玻利维亚', 'La Paz (administrative/legislative) and Sucre (jud', 591, 68, 'BOB', 'Boliviano', '.bo', 'BO', 'BOL', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (433, 'zh-cn', 'Bosnia and Herzegovina', '波斯尼亚和黑塞哥维那', 'Sarajevo', 387, 70, 'BAM', 'Marka', '.ba', 'BA', 'BIH', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (434, 'zh-cn', 'Botswana', '博茨瓦纳', 'Gaborone', 267, 72, 'BWP', 'Pula', '.bw', 'BW', 'BWA', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (435, 'zh-cn', 'Brazil', '巴西', 'Brasilia', 55, 76, 'BRL', 'Real', '.br', 'BR', 'BRA', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (436, 'zh-cn', 'British Virgin Islands', '英属维尔京群岛', 'Road Town', 1284, 92, 'USD', 'Dollar', '.vg', 'VG', 'VGB', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (437, 'zh-cn', 'Brunei', '文莱', 'Bandar Seri Begawan', 673, 96, 'BND', 'Dollar', '.bn', 'BN', 'BRN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (438, 'zh-cn', 'Bulgaria', '保加利亚', 'Sofia', 359, 100, 'BGN', 'Lev', '.bg', 'BG', 'BGR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (439, 'zh-cn', 'Burkina Faso', '布基纳法索', 'Ouagadougou', 226, 854, 'XOF', 'Franc', '.bf', 'BF', 'BFA', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (440, 'zh-cn', 'Burundi', '布隆迪', 'Bujumbura', 257, 108, 'BIF', 'Franc', '.bi', 'BI', 'BDI', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (441, 'zh-cn', 'Cambodia', '柬埔寨', 'Phnom Penh', 855, 116, 'KHR', 'Riels', '.kh', 'KH', 'KHM', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (442, 'zh-cn', 'Cameroon', '喀麦隆', 'Yaounde', 237, 120, 'XAF', 'Franc', '.cm', 'CM', 'CMR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (443, 'zh-cn', 'Canada', '加拿大', 'Ottawa', 1, 124, 'CAD', 'Dollar', '.ca', 'CA', 'CAN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (444, 'zh-cn', 'Cape Verde', '佛得角', 'Praia', 238, 132, 'CVE', 'Escudo', '.cv', 'CV', 'CPV', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (445, 'zh-cn', 'Cayman Islands', '开曼群岛', 'George Town', 1345, 136, 'KYD', 'Dollar', '.ky', 'KY', 'CYM', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (446, 'zh-cn', 'Central African Republic', '中非共和国', 'Bangui', 236, 140, 'XAF', 'Franc', '.cf', 'CF', 'CAF', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (447, 'zh-cn', 'Chad', '乍得', 'N\'Djamena', 235, 148, 'XAF', 'Franc', '.td', 'TD', 'TCD', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (448, 'zh-cn', 'Chile', '智利', 'Santiago (administrative/judical) and Valparaiso (', 56, 152, 'CLP', 'Peso', '.cl', 'CL', 'CHL', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (449, 'zh-cn', 'Colombia', '哥伦比亚', 'Bogota', 57, 170, 'COP', 'Peso', '.co', 'CO', 'COL', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (450, 'zh-cn', 'Comoros', '科摩罗', 'Moroni', 269, 174, 'KMF', 'Franc', '.km', 'KM', 'COM', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (451, 'zh-cn', 'Congo, Republic', '刚果（布）', 'Brazzaville', 242, 178, 'XAF', 'Franc', '.cg', 'CG', 'COG', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (452, 'zh-cn', 'Congo, Democratic Republic', '刚果（金）', 'Kinshasa', 243, 180, 'CDF', 'Franc', '.cd', 'CD', 'COD', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (453, 'zh-cn', 'Costa Rica', '哥斯达黎加', 'San Jose', 506, 188, 'CRC', 'Colon', '.cr', 'CR', 'CRI', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (454, 'zh-cn', 'Cote d&acute;Ivoire &#40;Ivory Coast&#41;', '科特迪瓦', 'Yamoussoukro', 225, 384, 'XOF', 'Franc', '.ci', 'CI', 'CIV', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (455, 'zh-cn', 'Croatia', '克罗地亚', 'Zagreb', 385, 191, 'HRK', 'Kuna', '.hr', 'HR', 'HRV', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (456, 'zh-cn', 'Cuba', '古巴', 'Havana', 53, 192, 'CUP', 'Peso', '.cu', 'CU', 'CUB', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (457, 'zh-cn', 'Cyprus', '塞浦路斯', 'Nicosia', 357, 196, 'CYP', 'Pound', '.cy', 'CY', 'CYP', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (458, 'zh-cn', 'Czech Republic', '捷克共和国', 'Prague', 420, 203, 'CZK', 'Koruna', '.cz', 'CZ', 'CZE', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (459, 'zh-cn', 'Denmark', '丹麦', 'Copenhagen', 45, 208, 'DKK', 'Krone', '.dk', 'DK', 'DNK', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (460, 'zh-cn', 'Djibouti', '吉布提', 'Djibouti', 253, 262, 'DJF', 'Franc', '.dj', 'DJ', 'DJI', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (461, 'zh-cn', 'Dominica', '多米尼加', 'Roseau', 1767, 212, 'XCD', 'Dollar', '.dm', 'DM', 'DMA', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (462, 'zh-cn', 'Dominican Republic', '多明尼加共和国', 'Santo Domingo', 1849, 214, 'DOP', 'Peso', '.do', 'DO', 'DOM', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (463, 'zh-cn', 'Ecuador', '厄瓜多尔', 'Quito', 593, 218, 'USD', 'Dollar', '.ec', 'EC', 'ECU', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (464, 'zh-cn', 'Egypt', '埃及', 'Cairo', 20, 818, 'EGP', 'Pound', '.eg', 'EG', 'EGY', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (465, 'zh-cn', 'El Salvador', '萨尔瓦多', 'San Salvador', 503, 222, 'USD', 'Dollar', '.sv', 'SV', 'SLV', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (466, 'zh-cn', 'Equatorial Guinea', '赤道几内亚', 'Malabo', 240, 226, 'XAF', 'Franc', '.gq', 'GQ', 'GNQ', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (467, 'zh-cn', 'Eritrea', '厄立特里亚', 'Asmara', 291, 232, 'ERN', 'Nakfa', '.er', 'ER', 'ERI', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (468, 'zh-cn', 'Estonia', '爱沙尼亚', 'Tallinn', 372, 233, 'EEK', 'Kroon', '.ee', 'EE', 'EST', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (469, 'zh-cn', 'Ethiopia', '埃塞俄比亚', 'Addis Ababa', 251, 231, 'ETB', 'Birr', '.et', 'ET', 'ETH', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (470, 'zh-cn', 'Fiji', '斐济', 'Suva', 679, 242, 'FJD', 'Dollar', '.fj', 'FJ', 'FJI', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (471, 'zh-cn', 'Finland', '芬兰', 'Helsinki', 358, 246, 'EUR', 'Euro', '.fi', 'FI', 'FIN', 1, '2018-04-27 09:36:32', '2018-07-27 12:11:50');
INSERT INTO `dict_country` VALUES (472, 'zh-cn', 'France', '法国', 'Paris', 33, 250, 'EUR', 'Euro', '.fr', 'FR', 'FRA', 1, '2018-04-27 09:36:32', '2018-07-27 11:57:38');
INSERT INTO `dict_country` VALUES (473, 'zh-cn', 'Gabon', '加蓬', 'Libreville', 241, 266, 'XAF', 'Franc', '.ga', 'GA', 'GAB', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (474, 'zh-cn', 'Gambia, The', '冈比亚', 'Banjul', 220, 270, 'GMD', 'Dalasi', '.gm', 'GM', 'GMB', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (475, 'zh-cn', 'Georgia', '格鲁吉亚', 'Tbilisi', 995, 268, 'GEL', 'Lari', '.ge', 'GE', 'GEO', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (476, 'zh-cn', 'Germany', '德国', 'Berlin', 49, 276, 'EUR', 'Euro', '.de', 'DE', 'DEU', 1, '2018-04-27 09:36:32', '2018-07-27 11:54:06');
INSERT INTO `dict_country` VALUES (477, 'zh-cn', 'Ghana', '加纳', 'Accra', 233, 288, 'GHC', 'Cedi', '.gh', 'GH', 'GHA', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (478, 'zh-cn', 'Greece', '希腊', 'Athens', 30, 300, 'EUR', 'Euro', '.gr', 'GR', 'GRC', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (479, 'zh-cn', 'Grenada', '格林纳达', 'Saint George\'s', 1473, 308, 'XCD', 'Dollar', '.gd', 'GD', 'GRD', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (480, 'zh-cn', 'Guatemala', '危地马拉', 'Guatemala', 502, 320, 'GTQ', 'Quetzal', '.gt', 'GT', 'GTM', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (481, 'zh-cn', 'Guinea', '几内亚', 'Conakry', 224, 324, 'GNF', 'Franc', '.gn', 'GN', 'GIN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (482, 'zh-cn', 'Guinea-Bissau', '几内亚比绍', 'Bissau', 245, 624, 'XOF', 'Franc', '.gw', 'GW', 'GNB', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (483, 'zh-cn', 'Guyana', '圭亚那', 'Georgetown', 592, 328, 'GYD', 'Dollar', '.gy', 'GY', 'GUY', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (484, 'zh-cn', 'Haiti', '海地', 'Port-au-Prince', 509, 332, 'HTG', 'Gourde', '.ht', 'HT', 'HTI', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (485, 'zh-cn', 'Honduras', '洪都拉斯', 'Tegucigalpa', 504, 340, 'HNL', 'Lempira', '.hn', 'HN', 'HND', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (486, 'zh-cn', 'Hong Kong', '中国香港', 'null', 852, 344, 'HKD', 'Dollar', '.hk', 'HK', 'HKG', 1, '2018-04-27 09:36:32', '2018-07-12 13:16:47');
INSERT INTO `dict_country` VALUES (487, 'zh-cn', 'Hungary', '匈牙利', 'Budapest', 36, 348, 'HUF', 'Forint', '.hu', 'HU', 'HUN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (488, 'zh-cn', 'Iceland', '冰岛', 'Reykjavik', 354, 352, 'ISK', 'Krona', '.is', 'IS', 'ISL', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (489, 'zh-cn', 'India', '印度', 'New Delhi', 91, 356, 'INR', 'Rupee', '.in', 'IN', 'IND', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (490, 'zh-cn', 'Indonesia', '印尼', 'Jakarta', 62, 360, 'IDR', 'Rupiah', '.id', 'ID', 'IDN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (491, 'zh-cn', 'Iran', '伊朗', 'Tehran', 98, 364, 'IRR', 'Rial', '.ir', 'IR', 'IRN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (492, 'zh-cn', 'Iraq', '伊拉克', 'Baghdad', 964, 368, 'IQD', 'Dinar', '.iq', 'IQ', 'IRQ', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (493, 'zh-cn', 'Ireland', '爱尔兰', 'Dublin', 353, 372, 'EUR', 'Euro', '.ie', 'IE', 'IRL', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (494, 'zh-cn', 'Israel', '以色列', 'Jerusalem', 972, 376, 'ILS', 'Shekel', '.il', 'IL', 'ISR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (495, 'zh-cn', 'Italy', '意大利', 'Rome', 39, 380, 'EUR', 'Euro', '.it', 'IT', 'ITA', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (496, 'zh-cn', 'Jamaica', '牙买加', 'Kingston', 1876, 388, 'JMD', 'Dollar', '.jm', 'JM', 'JAM', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (497, 'zh-cn', 'Japan', '日本', 'Tokyo', 81, 392, 'JPY', 'Yen', '.jp', 'JP', 'JPN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (498, 'zh-cn', 'Jordan', '约旦', 'Amman', 962, 400, 'JOD', 'Dinar', '.jo', 'JO', 'JOR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (499, 'zh-cn', 'Kazakhstan', '哈萨克斯坦', 'Astana', 7, 398, 'KZT', 'Tenge', '.kz', 'KZ', 'KAZ', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (500, 'zh-cn', 'Kenya', '肯尼亚', 'Nairobi', 254, 404, 'KES', 'Shilling', '.ke', 'KE', 'KEN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (501, 'zh-cn', 'Kiribati', '基里巴斯', 'Tarawa', 686, 296, 'AUD', 'Dollar', '.ki', 'KI', 'KIR', 1, '2018-04-27 09:36:32', '2018-08-06 11:27:02');
INSERT INTO `dict_country` VALUES (502, 'zh-cn', 'Korea, North', '朝鲜', 'Pyongyang', 850, 408, 'KPW', 'Won', '.kp', 'KP', 'PRK', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (503, 'zh-cn', 'South Korea', '韩国', 'Seoul', 82, 410, 'KRW', 'Won', '.kr', 'KR', 'KOR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (504, 'zh-cn', 'Kuwait', '科威特', 'Kuwait', 965, 414, 'KWD', 'Dinar', '.kw', 'KW', 'KWT', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (505, 'zh-cn', 'Kyrgyzstan', '吉尔吉斯斯坦', 'Bishkek', 996, 417, 'KGS', 'Som', '.kg', 'KG', 'KGZ', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (506, 'zh-cn', 'Laos', '老挝', 'Vientiane', 856, 418, 'LAK', 'Kip', '.la', 'LA', 'LAO', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (507, 'zh-cn', 'Latvia', '拉脱维亚', 'Riga', 371, 428, 'LVL', 'Lat', '.lv', 'LV', 'LVA', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (508, 'zh-cn', 'Lebanon', '黎巴嫩', 'Beirut', 961, 422, 'LBP', 'Pound', '.lb', 'LB', 'LBN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (509, 'zh-cn', 'Lesotho', '莱索托', 'Maseru', 266, 426, 'LSL', 'Loti', '.ls', 'LS', 'LSO', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (510, 'zh-cn', 'Liberia', '利比里亚', 'Monrovia', 231, 430, 'LRD', 'Dollar', '.lr', 'LR', 'LBR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (511, 'zh-cn', 'Libya', '利比亚', 'Tripoli', 218, 434, 'LYD', 'Dinar', '.ly', 'LY', 'LBY', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (512, 'zh-cn', 'Liechtenstein', '列支敦士登', 'Vaduz', 423, 438, 'CHF', 'Franc', '.li', 'LI', 'LIE', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (513, 'zh-cn', 'Lithuania', '立陶宛', 'Vilnius', 370, 440, 'LTL', 'Litas', '.lt', 'LT', 'LTU', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (514, 'zh-cn', 'Luxembourg', '卢森堡', 'Luxembourg', 352, 442, 'EUR', 'Euro', '.lu', 'LU', 'LUX', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (515, 'zh-cn', 'Macau', '中国澳门', 'Macau', 853, 446, 'MOP', 'Pataca', '.mo', 'MO', 'MAC', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (516, 'zh-cn', 'Macedonia', '马其顿', 'Skopje', 389, 807, 'MKD', 'Denar', '.mk', 'MK', 'MKD', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (517, 'zh-cn', 'Madagascar', '马达加斯加', 'Antananarivo', 261, 450, 'MGA', 'Ariary', '.mg', 'MG', 'MDG', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (518, 'zh-cn', 'Malawi', '马拉维', 'Lilongwe', 265, 454, 'MWK', 'Kwacha', '.mw', 'MW', 'MWI', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (519, 'zh-cn', 'Malaysia', '马来西亚', 'Kuala Lumpur (legislative/judical) and Putrajaya (', 60, 458, 'MYR', 'Ringgit', '.my', 'MY', 'MYS', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (520, 'zh-cn', 'Maldives', '马尔代夫', 'Male', 960, 462, 'MVR', 'Rufiyaa', '.mv', 'MV', 'MDV', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (521, 'zh-cn', 'Mali', '马里', 'Bamako', 223, 466, 'XOF', 'Franc', '.ml', 'ML', 'MLI', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (522, 'zh-cn', 'Malta', '马耳他', 'Valletta', 356, 470, 'MTL', 'Lira', '.mt', 'MT', 'MLT', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (523, 'zh-cn', 'Marshall Islands', '马绍尔群岛', 'Majuro', 692, 584, 'USD', 'Dollar', '.mh', 'MH', 'MHL', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (524, 'zh-cn', 'Mauritania', '毛里塔尼亚', 'Nouakchott', 222, 478, 'MRO', 'Ouguiya', '.mr', 'MR', 'MRT', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (525, 'zh-cn', 'Mauritius', '毛里求斯', 'Port Louis', 230, 480, 'MUR', 'Rupee', '.mu', 'MU', 'MUS', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (526, 'zh-cn', 'Mexico', '墨西哥', 'Mexico', 52, 484, 'MXN', 'Peso', '.mx', 'MX', 'MEX', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (527, 'zh-cn', 'Micronesia', '密克罗尼西亚', 'Palikir', 691, 583, 'USD', 'Dollar', '.fm', 'FM', 'FSM', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (528, 'zh-cn', 'Moldova', '摩尔多瓦', 'Chisinau', 373, 498, 'MDL', 'Leu', '.md', 'MD', 'MDA', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (529, 'zh-cn', 'Monaco', '摩纳哥', 'Monaco', 377, 492, 'EUR', 'Euro', '.mc', 'MC', 'MCO', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (530, 'zh-cn', 'Mongolia', '蒙古', 'Ulaanbaatar', 976, 496, 'MNT', 'Tugrik', '.mn', 'MN', 'MNG', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (531, 'zh-cn', 'Montenegro', '黑山', 'Podgorica', 382, 499, 'EUR', 'Euro', '.me and .y', 'ME', 'MNE', 1, '2018-04-27 09:36:32', '2018-08-06 11:27:02');
INSERT INTO `dict_country` VALUES (532, 'zh-cn', 'Morocco', '摩洛哥', 'Rabat', 212, 504, 'MAD', 'Dirham', '.ma', 'MA', 'MAR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (533, 'zh-cn', 'Mozambique', '莫桑比克', 'Maputo', 258, 508, 'MZM', 'Meticail', '.mz', 'MZ', 'MOZ', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (534, 'zh-cn', 'Myanmar &#40;Burma&#41;', '缅甸', 'Naypyidaw', 95, 104, 'MMK', 'Kyat', '.mm', 'MM', 'MMR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (535, 'zh-cn', 'Namibia', '纳米比亚', 'Windhoek', 264, 516, 'NAD', 'Dollar', '.na', 'NA', 'NAM', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (536, 'zh-cn', 'Nauru', '瑙鲁', 'Yaren', 674, 520, 'AUD', 'Dollar', '.nr', 'NR', 'NRU', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (537, 'zh-cn', 'Nepal', '尼泊尔', 'Kathmandu', 977, 524, 'NPR', 'Rupee', '.np', 'NP', 'NPL', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (538, 'zh-cn', 'Netherlands', '荷兰', 'Amsterdam (administrative) and The Hague (legislat', 31, 528, 'EUR', 'Euro', '.nl', 'NL', 'NLD', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (539, 'zh-cn', 'New Zealand', '新西兰', 'Wellington', 64, 554, 'NZD', 'Dollar', '.nz', 'NZ', 'NZL', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (540, 'zh-cn', 'Nicaragua', '尼加拉瓜', 'Managua', 505, 558, 'NIO', 'Cordoba', '.ni', 'NI', 'NIC', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (541, 'zh-cn', 'Niger', '尼日尔', 'Niamey', 227, 562, 'XOF', 'Franc', '.ne', 'NE', 'NER', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (542, 'zh-cn', 'Nigeria', '尼日利亚', 'Abuja', 234, 566, 'NGN', 'Naira', '.ng', 'NG', 'NGA', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (543, 'zh-cn', 'Norway', '挪威', 'Oslo', 47, 578, 'NOK', 'Krone', '.no', 'NO', 'NOR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (544, 'zh-cn', 'Oman', '阿曼', 'Muscat', 968, 512, 'OMR', 'Rial', '.om', 'OM', 'OMN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (545, 'zh-cn', 'Pakistan', '巴基斯坦', 'Islamabad', 92, 586, 'PKR', 'Rupee', '.pk', 'PK', 'PAK', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (546, 'zh-cn', 'Palau', '帕劳', 'Melekeok', 680, 585, 'USD', 'Dollar', '.pw', 'PW', 'PLW', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (547, 'zh-cn', 'Panama', '巴拿马', 'Panama', 507, 591, 'PAB', 'Balboa', '.pa', 'PA', 'PAN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (548, 'zh-cn', 'Papua New Guinea', '巴布亚新几内亚', 'Port Moresby', 675, 598, 'PGK', 'Kina', '.pg', 'PG', 'PNG', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (549, 'zh-cn', 'Paraguay', '巴拉圭', 'Asuncion', 595, 600, 'PYG', 'Guarani', '.py', 'PY', 'PRY', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (550, 'zh-cn', 'Peru', '秘鲁', 'Lima', 51, 604, 'PEN', 'Sol', '.pe', 'PE', 'PER', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (551, 'zh-cn', 'Philippines', '菲律宾', 'Manila', 63, 608, 'PHP', 'Peso', '.ph', 'PH', 'PHL', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (552, 'zh-cn', 'Poland', '波兰', 'Warsaw', 48, 616, 'PLN', 'Zloty', '.pl', 'PL', 'POL', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (553, 'zh-cn', 'Portugal', '葡萄牙', 'Lisbon', 351, 620, 'EUR', 'Euro', '.pt', 'PT', 'PRT', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (554, 'zh-cn', 'Qatar', '卡塔尔', 'Doha', 974, 634, 'QAR', 'Rial', '.qa', 'QA', 'QAT', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (555, 'zh-cn', 'Romania', '罗马尼亚', 'Bucharest', 40, 642, 'RON', 'Leu', '.ro', 'RO', 'ROU', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (556, 'zh-cn', 'Russia', '俄罗斯', 'Moscow', 7, 643, 'RUB', 'Ruble', '.ru and .s', 'RU', 'RUS', 1, '2018-05-19 07:39:59', '2018-05-19 07:39:59');
INSERT INTO `dict_country` VALUES (557, 'zh-cn', 'Rwanda', '卢旺达', 'Kigali', 250, 646, 'RWF', 'Franc', '.rw', 'RW', 'RWA', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (558, 'zh-cn', 'Saint Kitts and Nevis', '圣基茨和尼维斯', 'Basseterre', 1869, 659, 'XCD', 'Dollar', '.kn', 'KN', 'KNA', 1, '2018-04-27 09:36:32', '2018-08-06 11:27:02');
INSERT INTO `dict_country` VALUES (559, 'zh-cn', 'Saint Lucia', '圣卢西亚', 'Castries', 1758, 662, 'XCD', 'Dollar', '.lc', 'LC', 'LCA', 1, '2018-04-27 09:36:32', '2018-08-06 11:27:02');
INSERT INTO `dict_country` VALUES (560, 'zh-cn', 'Saint Vincent and the Grenadines', '圣文森特和格林纳丁斯', 'Kingstown', 1784, 670, 'XCD', 'Dollar', '.vc', 'VC', 'VCT', 1, '2018-04-27 09:36:32', '2018-08-06 11:27:02');
INSERT INTO `dict_country` VALUES (561, 'zh-cn', 'Samoa', '萨摩亚', 'Apia', 685, 882, 'WST', 'Tala', '.ws', 'WS', 'WSM', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (562, 'zh-cn', 'San Marino', '圣马力诺', 'San Marino', 378, 674, 'EUR', 'Euro', '.sm', 'SM', 'SMR', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (563, 'zh-cn', 'Sao Tome and Principe', '圣多美和普林西比', 'Sao Tome', 239, 678, 'STD', 'Dobra', '.st', 'ST', 'STP', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (564, 'zh-cn', 'Saudi Arabia', '沙特阿拉伯', 'Riyadh', 966, 682, 'SAR', 'Rial', '.sa', 'SA', 'SAU', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (565, 'zh-cn', 'Senegal', '塞内加尔', 'Dakar', 221, 686, 'XOF', 'Franc', '.sn', 'SN', 'SEN', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (566, 'zh-cn', 'Serbia', '塞尔维亚', 'Belgrade', 381, 688, 'RSD', 'Dinar', '.rs and .y', 'RS', 'SRB', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (567, 'zh-cn', 'Seychelles', '塞舌尔', 'Victoria', 248, 690, 'SCR', 'Rupee', '.sc', 'SC', 'SYC', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (568, 'zh-cn', 'Sierra Leone', '塞拉利昂', 'Freetown', 232, 694, 'SLL', 'Leone', '.sl', 'SL', 'SLE', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (569, 'zh-cn', 'Singapore', '新加坡', 'Singapore', 65, 702, 'SGD', 'Dollar', '.sg', 'SG', 'SGP', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (570, 'zh-cn', 'Slovakia', '斯洛伐克', 'Bratislava', 421, 703, 'SKK', 'Koruna', '.sk', 'SK', 'SVK', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (571, 'zh-cn', 'Slovenia', '斯洛文尼亚', 'Ljubljana', 386, 705, 'EUR', 'Euro', '.si', 'SI', 'SVN', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (572, 'zh-cn', 'Solomon Islands', '所罗门群岛', 'Honiara', 677, 90, 'SBD', 'Dollar', '.sb', 'SB', 'SLB', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (573, 'zh-cn', 'Somalia', '索马里', 'Mogadishu', 252, 706, 'SOS', 'Shilling', '.so', 'SO', 'SOM', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (574, 'zh-cn', 'South Africa', '南非', 'Pretoria (administrative), Cape Town (legislative)', 27, 710, 'ZAR', 'Rand', '.za', 'ZA', 'ZAF', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (575, 'zh-cn', 'Spain', '西班牙', 'Madrid', 34, 724, 'EUR', 'Euro', '.es', 'ES', 'ESP', 1, '2018-04-27 09:36:32', '2018-08-05 16:53:05');
INSERT INTO `dict_country` VALUES (576, 'zh-cn', 'Sri Lanka', '斯里兰卡', 'Colombo (administrative/judical) and Sri Jayewarde', 94, 144, 'LKR', 'Rupee', '.lk', 'LK', 'LKA', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (577, 'zh-cn', 'Sudan', '苏丹', 'Khartoum', 249, 729, 'SDD', 'Dinar', '.sd', 'SD', 'SDN', 1, '2018-05-19 07:13:09', '2018-05-19 07:13:09');
INSERT INTO `dict_country` VALUES (578, 'zh-cn', 'Suriname', '苏里南', 'Paramaribo', 597, 740, 'SRD', 'Dollar', '.sr', 'SR', 'SUR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (579, 'zh-cn', 'Swaziland', '斯威士兰', 'Mbabane (administrative) and Lobamba (legislative)', 268, 748, 'SZL', 'Lilangeni', '.sz', 'SZ', 'SWZ', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (580, 'zh-cn', 'Sweden', '瑞典', 'Stockholm', 46, 752, 'SEK', 'Kronoa', '.se', 'SE', 'SWE', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (581, 'zh-cn', 'Switzerland', '瑞士', 'Bern', 41, 756, 'CHF', 'Franc', '.ch', 'CH', 'CHE', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (582, 'zh-cn', 'Syria', '叙利亚', 'Damascus', 963, 760, 'SYP', 'Pound', '.sy', 'SY', 'SYR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (583, 'zh-cn', 'Taiwan', '中国台湾', 'Taipei', 886, 158, 'TWD', 'Dollar', '.tw', 'TW', 'TWN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (584, 'zh-cn', 'Tajikistan', '塔吉克斯坦', 'Dushanbe', 992, 762, 'TJS', 'Somoni', '.tj', 'TJ', 'TJK', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (585, 'zh-cn', 'Tanzania', '坦桑尼亚', 'Dar es Salaam (administrative/judical) and Dodoma ', 255, 834, 'TZS', 'Shilling', '.tz', 'TZ', 'TZA', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (586, 'zh-cn', 'Thailand', '泰国', 'Bangkok', 66, 764, 'THB', 'Baht', '.th', 'TH', 'THA', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (587, 'zh-cn', 'Timor-Leste (East Timor)', '东帝汶', 'Dili', 670, 626, 'USD', 'Dollar', '.tp and .t', 'TL', 'TLS', 1, '2018-04-27 09:36:32', '2018-08-06 11:27:02');
INSERT INTO `dict_country` VALUES (588, 'zh-cn', 'Togo', '多哥', 'Lome', 228, 768, 'XOF', 'Franc', '.tg', 'TG', 'TGO', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (589, 'zh-cn', 'Tonga', '汤加', 'Nuku\'alofa', 676, 776, 'TOP', 'Pa\'anga', '.to', 'TO', 'TON', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (590, 'zh-cn', 'Trinidad and Tobago', '特里尼达和多巴哥', 'Port-of-Spain', 1868, 780, 'TTD', 'Dollar', '.tt', 'TT', 'TTO', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (591, 'zh-cn', 'Tunisia', '突尼斯', 'Tunis', 216, 788, 'TND', 'Dinar', '.tn', 'TN', 'TUN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (592, 'zh-cn', 'Turkey', '土耳其', 'Ankara', 90, 792, 'TRY', 'Lira', '.tr', 'TR', 'TUR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (593, 'zh-cn', 'Turkmenistan', '土库曼斯坦', 'Ashgabat', 993, 795, 'TMM', 'Manat', '.tm', 'TM', 'TKM', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (594, 'zh-cn', 'Tuvalu', '图瓦卢', 'Funafuti', 688, 798, 'AUD', 'Dollar', '.tv', 'TV', 'TUV', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (595, 'zh-cn', 'U.S. Virgin Islands', '美属维尔京群岛', 'Charlotte Amalie', 1340, 850, 'USD', 'Dollar', '.vi', 'VI', 'VIR', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (596, 'zh-cn', 'Uganda', '乌干达', 'Kampala', 256, 800, 'UGX', 'Shilling', '.ug', 'UG', 'UGA', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (597, 'zh-cn', 'Ukraine', '乌克兰', 'Kiev', 380, 804, 'UAH', 'Hryvnia', '.ua', 'UA', 'UKR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (598, 'zh-cn', 'United Arab Emirates', '阿联酋', 'Abu Dhabi', 971, 784, 'AED', 'Dirham', '.ae', 'AE', 'ARE', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (599, 'zh-cn', 'United Kingdom', '英国', 'London', 44, 826, 'GBP', 'Pound', '.uk', 'GB', 'GBR', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (600, 'zh-cn', 'United States', '美国', 'Washington', 1, 840, 'USD', 'Dollar', '.us', 'US', 'USA', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (601, 'zh-cn', 'Uruguay', '乌拉圭', 'Montevideo', 598, 858, 'UYU', 'Peso', '.uy', 'UY', 'URY', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (602, 'zh-cn', 'Uzbekistan', '乌兹别克斯坦', 'Tashkent', 998, 860, 'UZS', 'Som', '.uz', 'UZ', 'UZB', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (603, 'zh-cn', 'Vanuatu', '瓦努阿图', 'Port-Vila', 678, 548, 'VUV', 'Vatu', '.vu', 'VU', 'VUT', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (604, 'zh-cn', 'Vatican City', '梵蒂冈城', 'Vatican City', 39, 336, 'EUR', 'Euro', '.va', 'VA', 'VAT', 1, '2018-04-27 09:36:32', '2018-08-06 11:30:59');
INSERT INTO `dict_country` VALUES (605, 'zh-cn', 'Venezuela', '委内瑞拉', 'Caracas', 58, 862, 'VEB', 'Bolivar', '.ve', 'VE', 'VEN', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (606, 'zh-cn', 'Vietnam', '越南', 'Hanoi', 84, 704, 'VND', 'Dong', '.vn', 'VN', 'VNM', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (607, 'zh-cn', 'Yemen', '也门', 'Sanaa', 967, 887, 'YER', 'Rial', '.ye', 'YE', 'YEM', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (608, 'zh-cn', 'Zambia', '赞比亚', 'Lusaka', 260, 894, 'ZMK', 'Kwacha', '.zm', 'ZM', 'ZMB', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (609, 'zh-cn', 'Zimbabwe', '津巴布韦', 'Harare', 263, 716, 'ZWD', 'Dollar', '.zw', 'ZW', 'ZWE', 1, '2018-04-27 09:36:32', '2018-04-27 09:36:32');
INSERT INTO `dict_country` VALUES (610, 'zh-cn', 'Kosovo', '科索沃', 'Pristinaa', 383, 2000, 'EUR', 'Euro', '.xk', 'XK', 'XKS', 1, '2018-05-19 07:55:19', '2018-05-19 07:55:19');
INSERT INTO `dict_country` VALUES (611, 'zh-cn', 'South Sudan', '南苏丹', 'Juba', 211, 728, 'SSP', 'South Sudanese pound', '.ss', 'SS', 'SSD', 1, '2018-05-19 07:28:21', '2018-05-19 07:28:21');
INSERT INTO `dict_country` VALUES (612, 'zh-cn', 'China', '中国', 'Beijing', 86, 156, 'CNY', 'RMB', '.cn', 'CN', 'CHN', 1, '2018-05-19 07:38:59', '2018-05-19 07:38:59');
INSERT INTO `dict_country` VALUES (613, 'zh-cn', 'Palestine', '巴勒斯坦', 'East Jerusalem', 970, 275, 'ILS', 'SHEKEL', '.ps', 'PS', 'PSE', 1, '2018-05-19 07:38:49', '2018-05-19 07:38:49');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
