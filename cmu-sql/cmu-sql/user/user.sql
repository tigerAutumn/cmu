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

 Date: 01/07/2019 14:36:19
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
-- Table structure for global_frozen_config
-- ----------------------------
DROP TABLE IF EXISTS `global_frozen_config`;
CREATE TABLE `global_frozen_config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `code` varchar(50) NOT NULL DEFAULT '' COMMENT '全局冻结配置项唯一代号',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '冻结状态0未冻结1冻结,默认为0',
  `memo` varchar(50) NOT NULL DEFAULT '' COMMENT '说明备注',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `broker_id` int(11) DEFAULT '1' COMMENT '券商ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code_brokerId` (`code`,`broker_id`) USING BTREE COMMENT '券商全局冻结索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统业务全局冻结配置表';

-- ----------------------------
-- Table structure for organization_info
-- ----------------------------
DROP TABLE IF EXISTS `organization_info`;
CREATE TABLE `organization_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `org_name` varchar(500) NOT NULL DEFAULT '' COMMENT '组织名称',
  `org_type` int(11) NOT NULL DEFAULT '19' COMMENT '组织类型，11-团队，12-企业，19-其他组织，和UserInfo中的type字段对应',
  `contact_name` varchar(200) NOT NULL DEFAULT '' COMMENT '联系人',
  `contact_info` varchar(200) NOT NULL DEFAULT '' COMMENT '联系方式',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织详情表';

-- ----------------------------
-- Table structure for user_activity_config
-- ----------------------------
DROP TABLE IF EXISTS `user_activity_config`;
CREATE TABLE `user_activity_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `activity_code` varchar(20) NOT NULL COMMENT '活动编码',
  `currency_num` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '送币数量',
  `currency_id` int(11) NOT NULL COMMENT '活动送币',
  `currency_code` varchar(10) NOT NULL COMMENT '活动币code',
  `online` int(2) NOT NULL DEFAULT '1' COMMENT '是否上线 0:下线,1:上线',
  `invite_currency_id` int(11) NOT NULL DEFAULT '0' COMMENT '邀请人送的币id',
  `invite_currency_num` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '邀请人送的币数',
  `invite_currency_code` varchar(10) NOT NULL COMMENT '邀请人送的币名称',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `broker_id` int(11) DEFAULT '1' COMMENT '券商ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动配置表';

-- ----------------------------
-- Table structure for user_api_secret
-- ----------------------------
DROP TABLE IF EXISTS `user_api_secret`;
CREATE TABLE `user_api_secret` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `label` varchar(20) NOT NULL DEFAULT '' COMMENT '备注名称',
  `api_key` varchar(36) NOT NULL DEFAULT '' COMMENT 'api键',
  `secret` varchar(64) NOT NULL COMMENT '密钥',
  `passphrase` varchar(64) NOT NULL COMMENT 'secret口令盐',
  `authorities` varchar(50) NOT NULL DEFAULT '1' COMMENT '权限值',
  `expired_time` bigint(20) NOT NULL COMMENT '过期时间',
  `ip_address` bigint(50) NOT NULL COMMENT 'ip地址',
  `rate_limit` varchar(12) NOT NULL DEFAULT '10/1' COMMENT '请求次数限制(次数/每几秒 如:6/2表示2秒内不超过6次)默认为10/1',
  `ip_white_lists` varchar(500) NOT NULL DEFAULT '' COMMENT 'ip白名单,多个ip用英文逗号分开,ip用事整型表示',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `broker_id` int(11) DEFAULT '1' COMMENT '券商ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `api_key` (`api_key`),
  UNIQUE KEY `uk_user_id_label` (`user_id`,`label`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='apikey表';

-- ----------------------------
-- Records of user_api_secret
-- ----------------------------
BEGIN;
INSERT INTO `user_api_secret` VALUES (1, 18, 'xxx', 'cmx-fb2971adcf49f4a4465f72371571c4dc', 'e8088fc22550d4c81bfd61bedfebf7ea63e4f1b436b5390d8f44132b0b88eec3', 'bf3742dd9daed28c1f2bccdb24c57b9b4d48eea44785ad3fbe3ab9099832025f', '[\"trade\",\"readonly\"]', 1593168373503, 3232235998, '10/1', '', '2019-06-26 18:46:14', '2019-06-26 18:46:14', 1);
INSERT INTO `user_api_secret` VALUES (2, 19, 'xxx', 'cmx-02079ae8baa0c2b1df39a0eaab1fba0d', '668260454765b9fdfb63b4f4b3b5f67988d90ec8ec594e42ff56b5dd9d480b22', '379847bab1dcb22c528dc29792ce3d47a76f2ecbf3f16ef76c72ad58159e14fb', '[\"trade\",\"readonly\"]', 1593168519180, 3232235998, '10/1', '', '2019-06-26 18:48:39', '2019-06-26 18:48:39', 1);
COMMIT;

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

-- ----------------------------
-- Table structure for user_broker
-- ----------------------------
DROP TABLE IF EXISTS `user_broker`;
CREATE TABLE `user_broker` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '券商id',
  `broker_hosts` varchar(1024) NOT NULL COMMENT '券商域名',
  `broker_name` varchar(255) NOT NULL DEFAULT '' COMMENT '券商名称',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '券商状态0为开启，1为禁用，其他保留，默认为0',
  `sign` varchar(64) NOT NULL COMMENT '签名',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='券商表';

-- ----------------------------
-- Records of user_broker
-- ----------------------------
BEGIN;
INSERT INTO `user_broker` VALUES (1, 'test.ppl.com', '测试环境', 0, 'ppl', '2019-06-25 18:36:37', '2019-06-25 18:36:37');
COMMIT;

-- ----------------------------
-- Table structure for user_fiat_setting
-- ----------------------------
DROP TABLE IF EXISTS `user_fiat_setting`;
CREATE TABLE `user_fiat_setting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名称',
  `bank_card` varchar(50) NOT NULL DEFAULT '' COMMENT '银行卡号',
  `bank_address` varchar(300) NOT NULL DEFAULT '' COMMENT '开户支行',
  `bank_name` varchar(50) NOT NULL DEFAULT '' COMMENT '银行名称',
  `bank_no` varchar(50) NOT NULL DEFAULT '' COMMENT '银行编码',
  `alipay_name` varchar(50) NOT NULL DEFAULT '' COMMENT '支付宝收款人姓名',
  `alipay_card` varchar(50) NOT NULL DEFAULT '' COMMENT '支付宝账号',
  `alipay_receiving_img` varchar(100) NOT NULL DEFAULT '' COMMENT '支付宝收款码',
  `wechat_pay_name` varchar(20) NOT NULL DEFAULT '' COMMENT '微信支付收款人姓名',
  `wechat_card` varchar(50) NOT NULL DEFAULT '' COMMENT '微信支付账号',
  `wechat_receiving_img` varchar(100) NOT NULL DEFAULT '' COMMENT '微信支付收款码',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '状态 0:正常 1:冻结账户',
  `remarks` varchar(500) NOT NULL DEFAULT '' COMMENT '备注信息',
  `pay_type` int(2) NOT NULL DEFAULT '0' COMMENT '支付方式 1:银行卡,2:支付宝,3:微信支付',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`) USING BTREE COMMENT '用户等级索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户法币交易设置';

-- ----------------------------
-- Table structure for user_file_info
-- ----------------------------
DROP TABLE IF EXISTS `user_file_info`;
CREATE TABLE `user_file_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `file_type` varchar(64) NOT NULL DEFAULT '' COMMENT '文件类型：比如图片，视频等',
  `file_name` varchar(256) NOT NULL DEFAULT '' COMMENT '文件名称',
  `file_path` varchar(1024) NOT NULL DEFAULT '' COMMENT '文件相对路径',
  `note` text COMMENT '备注',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户文件表';

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id(分布式唯一id)',
  `parent_id` bigint(20) NOT NULL COMMENT '账号所属的主账号ID(如果为0表示主账号,否则为子账号)',
  `uid` varchar(64) NOT NULL COMMENT '对外提供的唯一id(rsa加密的id)',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `mobile` varchar(50) NOT NULL COMMENT '手机号',
  `password` varchar(64) NOT NULL COMMENT '登录密码',
  `trade_password` varchar(64) NOT NULL COMMENT '交易密码',
  `google_code` varchar(64) NOT NULL COMMENT 'google验证码',
  `authorities` varchar(50) NOT NULL DEFAULT 'ROLE_USER' COMMENT '用户权限角色',
  `nickname` varchar(30) NOT NULL COMMENT '用户昵称',
  `real_name` varchar(50) NOT NULL COMMENT '用户真实姓名',
  `avatar` varchar(100) NOT NULL COMMENT '用户头像url',
  `area_code` int(10) NOT NULL COMMENT '全球手机编号(如中国+86)',
  `anti_phishing_code` varchar(50) NOT NULL COMMENT '发送邮件包含的防钓鱼码',
  `channel` int(11) NOT NULL COMMENT '渠道来源(用户统计搞活动时注册的用户)',
  `frozen` int(4) NOT NULL COMMENT '用户账号冻结状态 1已冻结;0未冻结 默认为0',
  `type` int(4) NOT NULL DEFAULT '0' COMMENT '用户类型:0普通用户;1公司内部用户;默认为0;其他保留',
  `reg_from` int(2) NOT NULL COMMENT '注册位置0:网站注册1:客服注册2:微博注册3:qq注册4:微信注册',
  `status` int(4) NOT NULL DEFAULT '0' COMMENT '用户状态0为开启，1为禁用，其他保留，默认为0',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '用于乐观锁',
  `reg_ip` bigint(10) NOT NULL COMMENT '注册地IP',
  `memo` varchar(50) NOT NULL COMMENT '说明备注',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `broker_id` int(11) DEFAULT '1' COMMENT '券商ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_email` (`email`) USING BTREE,
  UNIQUE KEY `uk_phone` (`mobile`) USING BTREE,
  UNIQUE KEY `uk_uid` (`uid`) USING BTREE,
  KEY `ix_parent_id` (`parent_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of user_info
-- ----------------------------
BEGIN;
INSERT INTO `user_info` VALUES (1, 0, '05465545', 'dy.ppl.1@ppl.com', 'dy.ppl.1@ppl.com', '$2a$10$ys9fJaa3H1EHArZODvDsFOYp2sDCfFSXohPVmU..9kKeurWfbq/Ly', '', '', '', 'dy.ppl.1@ppl.com', '', '', 86, '', 999999, 0, 0, 0, 0, 0, 1667457891, '', '2019-06-26 16:57:44', '2019-06-26 16:57:43', 1);
INSERT INTO `user_info` VALUES (2, 0, '17230228', 'dy.ppl.2@ppl.com', 'dy.ppl.2@ppl.com', '$2a$10$iYTU6QYBGYTVBKCqsDtozOhREjhWFwF5.uXEuq1Q8g4ELpevMdUKm', '', '', '', 'dy.ppl.2@ppl.com', '', '', 86, '', 999999, 0, 0, 0, 0, 0, 1667457891, '', '2019-06-26 16:58:32', '2019-06-26 16:58:32', 1);
INSERT INTO `user_info` VALUES (3, 0, '68851346', 'dy.ppl.3@ppl.com', 'dy.ppl.3@ppl.com', '$2a$10$KyZEfLcjZ5dVd8Rg3hRuNeh0B.mabvIPig/TOKPK.RFN0jaCpA3X2', '', '', '', 'dy.ppl.3@ppl.com', '', '', 86, '', 999999, 0, 0, 0, 0, 0, 1667457891, '', '2019-06-26 16:58:41', '2019-06-26 16:58:41', 1);
INSERT INTO `user_info` VALUES (4, 0, '51015777', 'dy.ppl.4@ppl.com', 'dy.ppl.4@ppl.com', '$2a$10$LjMsrSXwJrSmQXRhcmh1CukXFrS4VL7uRxuJdik98k2txBG/UNe82', '', '', '', 'dy.ppl.4@ppl.com', '', '', 86, '', 999999, 0, 0, 0, 0, 0, 1667457891, '', '2019-06-26 16:58:56', '2019-06-26 16:58:56', 1);
INSERT INTO `user_info` VALUES (5, 0, '59384017', 'dy.ppl.5@ppl.com', 'dy.ppl.5@ppl.com', '$2a$10$C3Bpq0zukl0pCDpK2MEd1OHtTzgX8O2jQ0VpJC4M9GWH8DQHCyaUy', '', '', '', 'dy.ppl.5@ppl.com', '', '', 86, '', 999999, 0, 0, 0, 0, 0, 1667457891, '', '2019-06-26 16:59:03', '2019-06-26 16:59:03', 1);
INSERT INTO `user_info` VALUES (6, 0, '85871146', 'dy.ppl.6@ppl.com', 'dy.ppl.6@ppl.com', '$2a$10$7f02GIcbZzk4Y1yg838WQ.i3jZOXqTtL2AXne2bqDI02.cKCUWWgi', '', '', '', 'dy.ppl.6@ppl.com', '', '', 86, '', 999999, 0, 0, 0, 0, 0, 1667457891, '', '2019-06-26 16:59:11', '2019-06-26 16:59:10', 1);
INSERT INTO `user_info` VALUES (7, 0, '90868804', 'dy.ppl.7@ppl.com', 'dy.ppl.7@ppl.com', '$2a$10$gU7khHUbSQXPUVaRUVIJnekJse26b1cuIffMEAt2/uxk9UYLyc3ei', '', '', '', 'dy.ppl.7@ppl.com', '', '', 86, '', 999999, 0, 0, 0, 0, 0, 1667457891, '', '2019-06-26 16:59:18', '2019-06-26 16:59:18', 1);
INSERT INTO `user_info` VALUES (8, 0, '70397010', 'dy.ppl.8@ppl.com', 'dy.ppl.8@ppl.com', '$2a$10$sulNoenVdOPg3WN27VuMeuyYciDWrEVmqqzsA8w0U.kGqNNIDQsAC', '', '', '', 'dy.ppl.8@ppl.com', '', '', 86, '', 999999, 0, 0, 0, 0, 0, 1667457891, '', '2019-06-26 16:59:25', '2019-06-26 16:59:25', 1);
INSERT INTO `user_info` VALUES (9, 0, '68978123', 'dy.ppl.9@ppl.com', 'dy.ppl.9@ppl.com', '$2a$10$5X3gU3PumYlB7wKpQNqZHebX0JTM056OVsh/ata7n0nn1k82BXqDe', '', '', '', 'dy.ppl.9@ppl.com', '', '', 86, '', 999999, 0, 0, 0, 0, 0, 1667457891, '', '2019-06-26 16:59:31', '2019-06-26 16:59:31', 1);
INSERT INTO `user_info` VALUES (10, 0, '95036321', 'dy.ppl.10@ppl.com', 'dy.ppl.10@ppl.com', '$2a$10$WDYiW1FzKpCjhcvutnNxr.pN3xqFnspQvdc8tjR7w/F00HR7LG0O2', '', '', '', 'dy.ppl.10@ppl.com', '', '', 86, '', 999999, 0, 0, 0, 0, 0, 1667457891, '', '2019-06-26 16:59:38', '2019-06-26 16:59:37', 1);
INSERT INTO `user_info` VALUES (11, 0, '99155864', 'dy.ppl.11@ppl.com', 'dy.ppl.11@ppl.com', '$2a$10$icoo0/81uWZNUZHGsRNz7uUu8fgOqcGR3lbkY2KRDNvr64DnZ1EgG', '', '', '', 'dy.ppl.11@ppl.com', '', '', 86, '', 999999, 0, 0, 0, 0, 0, 1667457891, '', '2019-06-26 16:59:44', '2019-06-26 16:59:43', 1);
INSERT INTO `user_info` VALUES (12, 0, '40603156', 'dy.ppl.12@ppl.com', 'dy.ppl.12@ppl.com', '$2a$10$EafYHhcoVOjmcaRVLoXkTOYnhT9BI6JzkM9wnEqaRQmn9Pnz2y9Qq', '', '', '', 'dy.ppl.12@ppl.com', '', '', 86, '', 999999, 0, 0, 0, 0, 0, 1667457891, '', '2019-06-26 17:35:16', '2019-06-26 17:35:15', 1);
INSERT INTO `user_info` VALUES (13, 0, '36340744', 'dy.ppl.13@ppl.com', 'dy.ppl.13@ppl.com', '$2a$10$DfBtL2XA.WnVXmKIiq9z1uNSMzIbd56zxEcFlZxIZBsgr8csWoQWq', '', '', '', 'dy.ppl.13@ppl.com', '', '', 86, '', 999999, 0, 0, 0, 0, 0, 1667457891, '', '2019-06-26 17:35:24', '2019-06-26 17:35:23', 1);
INSERT INTO `user_info` VALUES (14, 0, '55982703', 'dy.ppl.14@ppl.com', 'dy.ppl.14@ppl.com', '$2a$10$m5SzM73UvVpuxl9DI1/82uuPUfHTfr/N250y7bjDJPruqGHlNRUjq', '', '', '', 'dy.ppl.14@ppl.com', '', '', 86, '', 999999, 0, 0, 0, 0, 0, 1667457891, '', '2019-06-26 17:35:32', '2019-06-26 17:35:32', 1);
INSERT INTO `user_info` VALUES (15, 0, '50144760', 'dy.ppl.15@ppl.com', 'dy.ppl.15@ppl.com', '$2a$10$UL21nNZDv3QuWvhGm9BLBuEONkAIETVjf.vGkbj5sN5IuUVRLwWdi', '', '', '', 'dy.ppl.15@ppl.com', '', '', 86, '', 999999, 0, 0, 0, 0, 0, 1667457891, '', '2019-06-26 17:35:39', '2019-06-26 17:35:38', 1);
INSERT INTO `user_info` VALUES (16, 0, '49856122', 'dy.ppl.16@ppl.com', 'dy.ppl.16@ppl.com', '$2a$10$RFpmG9n5JFGfMXaa4cEXz.VSCH9j4tKiYHRzb2mJ6hs7mccjbvpde', '', '', '', 'dy.ppl.16@ppl.com', '', '', 86, '', 999999, 0, 0, 0, 0, 0, 1667457891, '', '2019-06-26 17:35:46', '2019-06-26 17:35:46', 1);
INSERT INTO `user_info` VALUES (17, 0, '73313913', 'dy.ppl.17@ppl.com', 'dy.ppl.17@ppl.com', '$2a$10$HL2/EiG3qVTST7FUnli9kea2fGONr79HLTaFULkuFsyrAFkVV1jum', '', '', '', 'dy.ppl.17@ppl.com', '', '', 86, '', 999999, 0, 0, 0, 0, 0, 1667457891, '', '2019-06-26 17:35:53', '2019-06-26 17:35:53', 1);
INSERT INTO `user_info` VALUES (18, 0, '22345156', 'dy.ppl.18@ppl.com', 'dy.ppl.18@ppl.com', '$2a$10$yYfO8KQieKyUF5SuOhIaT.SDxF74pIk1.bjygHr3PvxJfgnbZ5BgO', '', '', '', 'dy.ppl.18@ppl.com', '', '', 86, '', 999999, 0, 0, 0, 0, 0, 1667457891, '', '2019-06-26 17:36:00', '2019-06-26 17:36:00', 1);
INSERT INTO `user_info` VALUES (19, 0, '10629145', 'dy.ppl.19@ppl.com', 'dy.ppl.19@ppl.com', '$2a$10$QP5iB3Rihgr53C5FlTw.quGW2HGhQsYAUrQc27daOTiusuOPzT1Se', '', '', '', 'dy.ppl.19@ppl.com', '', '', 86, '', 999999, 0, 0, 0, 0, 0, 1667457891, '', '2019-06-26 17:36:09', '2019-06-26 17:36:08', 1);
INSERT INTO `user_info` VALUES (20, 0, '01473473', 'and9n9c3@fxfmail.com', 'and9n9c3@fxfmail.com', '$2a$10$9WkSWOFs9Ugt.HJL47FoqeESzLjfDQO4HDJzAORkmHQNcgUZUqVkK', '', '', '', 'and9n9c3@fxfmail.com', '冯喜', '', 0, '', 100000, 0, 0, 0, 0, 0, 3232235998, '', '2019-06-28 17:36:32', '2019-06-28 17:36:32', 1);
INSERT INTO `user_info` VALUES (10000, 0, '49487368', 'sunqc5@163.com', 'sunqc5@163.com', '$2a$10$/iAh4Il5PHVMCwujTpYBz.e3rBnEgtwNYSBK6EpElG4KnnC/qYw8W', '', '', '', 'sunqc5@163.com', '', '', 0, '', 100000, 0, 0, 0, 0, 0, 3232235965, '', '2019-07-01 14:10:27', '2019-07-01 14:10:26', 1);
COMMIT;

-- ----------------------------
-- Table structure for user_invitation_channel
-- ----------------------------
DROP TABLE IF EXISTS `user_invitation_channel`;
CREATE TABLE `user_invitation_channel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `channel_name` varchar(30) NOT NULL COMMENT '渠道简称',
  `channel_full_name` varchar(100) NOT NULL,
  `channel_code` varchar(16) NOT NULL DEFAULT '',
  `channe_status` int(4) NOT NULL DEFAULT '0' COMMENT '用户状态0为开启，1为禁用，其他保留，默认为0',
  `channe_link` varchar(200) NOT NULL COMMENT '渠道链接',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `channel_code` (`channel_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='渠道推广表';

-- ----------------------------
-- Table structure for user_invite_record
-- ----------------------------
DROP TABLE IF EXISTS `user_invite_record`;
CREATE TABLE `user_invite_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) NOT NULL COMMENT '注册用户id',
  `user_name` varchar(50) NOT NULL COMMENT '注册人姓名',
  `invite_user_id` bigint(20) NOT NULL COMMENT '邀请人id',
  `invite_code` varchar(32) NOT NULL COMMENT '邀请码',
  `activity_code` varchar(32) NOT NULL COMMENT '活动编码',
  `currency_num` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '送币数量',
  `currency_id` int(11) NOT NULL COMMENT '活动送币',
  `invite_currency_num` double(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '邀请人送币数量',
  `invite_currency_id` int(11) NOT NULL COMMENT '邀请人活动送币',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `broker_id` int(11) DEFAULT '1' COMMENT '券商ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_id_activity_code` (`user_id`,`activity_code`) USING BTREE COMMENT '用户活动索引'
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='邀请好友记录表';

-- ----------------------------
-- Records of user_invite_record
-- ----------------------------
BEGIN;
INSERT INTO `user_invite_record` VALUES (1, 20, 'and9****@fxfmail.com', 7, '90868804', '', 0.00000000, 0, 0.00000000, 0, '2019-06-28 17:36:32', '2019-06-28 17:36:32', 1);
INSERT INTO `user_invite_record` VALUES (2, 10000, 'su****@163.com', 7, '90868804', '', 0.00000000, 0, 0.00000000, 0, '2019-07-01 14:10:26', '2019-07-01 14:10:26', 1);
COMMIT;

-- ----------------------------
-- Table structure for user_ip_rate_limit
-- ----------------------------
DROP TABLE IF EXISTS `user_ip_rate_limit`;
CREATE TABLE `user_ip_rate_limit` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `ip` bigint(20) NOT NULL COMMENT 'IP地址十进制表示',
  `rate_limit` varchar(12) NOT NULL DEFAULT '6/1' COMMENT '请求次数限制(次数/每几秒 如:6/2表示2秒内不超过6次)默认为6/1',
  `memo` varchar(50) NOT NULL COMMENT '备注',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `broker_id` int(11) DEFAULT '1' COMMENT '券商ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_ip` (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='API IP流量限流配置表';

-- ----------------------------
-- Table structure for user_kyc_event
-- ----------------------------
DROP TABLE IF EXISTS `user_kyc_event`;
CREATE TABLE `user_kyc_event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `level` int(2) NOT NULL DEFAULT '1' COMMENT '认证等级1:一级：2:二级',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '认证状态0：初始值，1：通过，2：驳回，3：其他异常，4：审核中',
  `remarks` varchar(5000) NOT NULL COMMENT '认证备注信息(内部查看)',
  `reject_reason` varchar(500) NOT NULL DEFAULT '' COMMENT '驳回原因',
  `deal_user_id` bigint(11) NOT NULL DEFAULT '0' COMMENT '受理客服id',
  `deal_user_name` varchar(30) NOT NULL DEFAULT '' COMMENT '受理客服姓名',
  `kyc_level_id` bigint(20) NOT NULL COMMENT 'kyc_level_id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户kyc审核操作记录';

-- ----------------------------
-- Table structure for user_kyc_img
-- ----------------------------
DROP TABLE IF EXISTS `user_kyc_img`;
CREATE TABLE `user_kyc_img` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `card_type` int(2) NOT NULL DEFAULT '1' COMMENT '证件类型1：身份证，2：护照，3：驾驶证',
  `card_img` varchar(255) NOT NULL COMMENT '证件信息照片',
  `front_img` varchar(255) NOT NULL DEFAULT '' COMMENT '身份证正面照片',
  `back_img` varchar(255) NOT NULL DEFAULT '' COMMENT '身份证背面照片',
  `hands_img` varchar(255) NOT NULL DEFAULT '' COMMENT '手持证件照片',
  `address_img` varchar(255) NOT NULL DEFAULT '' COMMENT '住址证明照片',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `broker_id` int(11) DEFAULT '1' COMMENT '券商ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_id` (`user_id`) COMMENT '用户id唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户kyc证件照片信息';

-- ----------------------------
-- Records of user_kyc_img
-- ----------------------------
BEGIN;
INSERT INTO `user_kyc_img` VALUES (1, 20, 1, '', '20190628/1561715302933.', '20190628/1561715310868.', '', '', '2019-06-28 17:48:40', '2019-06-28 17:48:40', 1);
COMMIT;

-- ----------------------------
-- Table structure for user_kyc_info
-- ----------------------------
DROP TABLE IF EXISTS `user_kyc_info`;
CREATE TABLE `user_kyc_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `first_name` varchar(50) NOT NULL COMMENT ' 姓',
  `middle_name` varchar(50) NOT NULL DEFAULT '' COMMENT '中间名称',
  `last_name` varchar(50) NOT NULL COMMENT '名',
  `gender` varchar(8) DEFAULT NULL COMMENT '性别 未知,男 ,女',
  `country` int(8) NOT NULL DEFAULT '86' COMMENT '国家编码',
  `card_type` int(2) NOT NULL DEFAULT '1' COMMENT '证件类型1：身份证，2：护照，3：驾驶证',
  `card_number` varchar(50) DEFAULT NULL COMMENT '证件号码',
  `valid_date` varchar(64) DEFAULT NULL COMMENT '证件有效期',
  `address1` varchar(300) DEFAULT NULL COMMENT '地址1',
  `address2` varchar(300) DEFAULT NULL COMMENT '地址2',
  `address3` varchar(300) DEFAULT NULL COMMENT '地址3',
  `city` varchar(300) DEFAULT NULL COMMENT '城市',
  `zip_code` varchar(20) DEFAULT NULL COMMENT '邮编',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `broker_id` int(11) DEFAULT '1' COMMENT '券商ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_id` (`user_id`),
  UNIQUE KEY `idx_idnumber` (`card_number`) USING BTREE COMMENT '用户身份证号码索引'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户kyc表';

-- ----------------------------
-- Records of user_kyc_info
-- ----------------------------
BEGIN;
INSERT INTO `user_kyc_info` VALUES (1, 20, '冯喜', '', '', '男', 156, 1, '510902198704127156', '2012.01.18-2022.01.18', '', '', '', '', '', '2019-06-28 17:48:40', '2019-06-28 17:48:40', 1);
COMMIT;

-- ----------------------------
-- Table structure for user_kyc_level
-- ----------------------------
DROP TABLE IF EXISTS `user_kyc_level`;
CREATE TABLE `user_kyc_level` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `level` int(2) NOT NULL DEFAULT '1' COMMENT '认证状态1:一级：2:二级',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '认证状态0：初始值，1：通过，2：驳回，3：其他异常，4：审核中',
  `remarks` varchar(5000) NOT NULL COMMENT '认证备注信息(内部查看)',
  `reject_reason` varchar(500) NOT NULL DEFAULT '' COMMENT '驳回原因',
  `deal_user_id` bigint(11) NOT NULL DEFAULT '0' COMMENT '受理客服id',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `broker_id` int(11) DEFAULT '1' COMMENT '券商ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_id_level` (`user_id`,`level`) COMMENT '用户等级索引'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户kyc等级';

-- ----------------------------
-- Records of user_kyc_level
-- ----------------------------
BEGIN;
INSERT INTO `user_kyc_level` VALUES (1, 20, 1, 1, '{\"birthday\":{\"month\":\"4\",\"year\":\"1987\",\"day\":\"12\"},\"side\":\"front\",\"address\":\"四川省遂宁市安居区西眉镇双河村3社7号\",\"race\":\"汉\",\"gender\":\"男\",\"legality\":{\"Temporary ID Photo\":0,\"Screen\":0,\"Edited\":0,\"Photocopy\":0,\"ID Photo\":1},\"head_rect\":{\"rb\":{\"x\":0.916,\"y\":0.6906667},\"rt\":{\"x\":0.916,\"y\":0.22666667},\"lb\":{\"x\":0.614,\"y\":0.6933333},\"lt\":{\"x\":0.614,\"y\":0.224}},\"name\":\"冯喜\",\"id_card_number\":\"510902198704127156\",\"request_id\":\"1561715303,40568981-6843-49a2-9068-c8529ec6e074\",\"time_used\":320}', '', 0, '2019-06-28 17:48:40', '2019-06-28 17:48:40', 1);
COMMIT;

-- ----------------------------
-- Table structure for user_kyc_token
-- ----------------------------
DROP TABLE IF EXISTS `user_kyc_token`;
CREATE TABLE `user_kyc_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `token` varchar(50) NOT NULL COMMENT 'face++请求token',
  `biz_id` varchar(50) NOT NULL DEFAULT '0' COMMENT 'faceId活体业务编号',
  `return_result` varchar(500) NOT NULL DEFAULT '' COMMENT 'kyc2返回结果',
  `remarks` varchar(1500) NOT NULL DEFAULT '' COMMENT '认证备注信息',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_id_token` (`user_id`,`token`) COMMENT '用户等级索引',
  UNIQUE KEY `idx_token` (`token`) COMMENT 'face++唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户kyctoken表';

-- ----------------------------
-- Records of user_kyc_token
-- ----------------------------
BEGIN;
INSERT INTO `user_kyc_token` VALUES (1, 20, 'd5b6f83be52870101bb8145c213c6b7f', '1561721517,27a1e26e-244b-44e1-8756-5905c67379f3', '{\"bizId\":\"1561721517,27a1e26e-244b-44e1-8756-5905c67379f3\",\"bizNo\":\"2FF464D8D760E2E3F8B00AEFD6F62246\",\"expiredTime\":1561725117,\"requestId\":\"1561721517,492ec517-9371-4208-a4d2-99975d71540e\",\"timeUsed\":111,\"token\":\"d5b6f83be52870101bb8145c213c6b7f\"}', '', '2019-06-28 19:31:57', '2019-06-28 19:31:57');
COMMIT;

-- ----------------------------
-- Table structure for user_level
-- ----------------------------
DROP TABLE IF EXISTS `user_level`;
CREATE TABLE `user_level` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `user_level` varchar(255) NOT NULL DEFAULT 'vip' COMMENT '用户等级(vip,angel, partner)',
  `weight` int(5) NOT NULL DEFAULT '10' COMMENT '用户等级权重 vip 10,angel 20, partner 50',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_index` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='用户等级表';

-- ----------------------------
-- Records of user_level
-- ----------------------------
BEGIN;
INSERT INTO `user_level` VALUES (1, 19, 'vip', 10, '2019-06-26 18:42:41', '2019-06-26 18:42:41');
INSERT INTO `user_level` VALUES (2, 18, 'vip', 10, '2019-06-26 18:45:11', '2019-06-26 18:45:11');
INSERT INTO `user_level` VALUES (3, 17, 'vip', 10, '2019-06-28 13:18:41', '2019-06-28 13:18:41');
INSERT INTO `user_level` VALUES (4, 20, 'vip', 10, '2019-06-28 17:36:34', '2019-06-28 17:36:34');
INSERT INTO `user_level` VALUES (5, 7, 'vip', 10, '2019-06-28 17:36:34', '2019-06-28 17:36:34');
INSERT INTO `user_level` VALUES (6, 10000, 'vip', 10, '2019-07-01 14:10:27', '2019-07-01 14:10:27');
COMMIT;

-- ----------------------------
-- Table structure for user_level_record
-- ----------------------------
DROP TABLE IF EXISTS `user_level_record`;
CREATE TABLE `user_level_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `user_level` varchar(50) NOT NULL DEFAULT '' COMMENT '用户等级(vip,angel, partner)',
  `pre_user_level` varchar(50) NOT NULL DEFAULT '' COMMENT '上次用户等级',
  `reason` varchar(500) NOT NULL DEFAULT '' COMMENT '等级变更原因',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `user_id_index` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户等级变更记录表';

-- ----------------------------
-- Table structure for user_limited_behavior
-- ----------------------------
DROP TABLE IF EXISTS `user_limited_behavior`;
CREATE TABLE `user_limited_behavior` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `type` int(11) NOT NULL DEFAULT '-1' COMMENT '行为类型',
  `login_name` varchar(100) NOT NULL DEFAULT '' COMMENT '登录名(邮箱或手机号)',
  `ip_address` bigint(20) NOT NULL DEFAULT '0' COMMENT 'ip地址',
  `ip_region` varchar(100) NOT NULL DEFAULT '0' COMMENT 'ip所属地区',
  `device_id` varchar(50) NOT NULL COMMENT '登录设备id',
  `maximum` int(4) NOT NULL DEFAULT '0' COMMENT '最大限制数',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户受限制行为表';

-- ----------------------------
-- Table structure for user_login_event
-- ----------------------------
DROP TABLE IF EXISTS `user_login_event`;
CREATE TABLE `user_login_event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `device_id` varchar(50) NOT NULL COMMENT '登录设备ID',
  `ip_address` bigint(10) NOT NULL COMMENT '当前登录IP地址',
  `last_ip_address` bigint(10) NOT NULL COMMENT '最近一次登录的ip',
  `user_agent` varchar(500) NOT NULL COMMENT 'User-Agent',
  `region` varchar(50) NOT NULL COMMENT '当前用户登录所在地区名及编号',
  `session_id` varchar(64) NOT NULL COMMENT '当前登录的session_id对应jwt中的sid字段',
  `last_login_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近一次登录时间',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
  PRIMARY KEY (`id`),
  KEY `ix_user_id` (`user_id`) USING BTREE,
  KEY `ix_created_date` (`created_date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='用户登录事件表';

-- ----------------------------
-- Records of user_login_event
-- ----------------------------
BEGIN;
INSERT INTO `user_login_event` VALUES (1, 19, '', 3232235998, 0, 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '', '10e6fafd-f927-4398-9ba9-f854cdd994861986554420', '2019-06-26 18:42:41', '2019-06-26 18:42:40', '2019-06-26 18:42:40');
INSERT INTO `user_login_event` VALUES (2, 18, '', 3232235998, 0, 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '', 'e59f9e82-3a7f-4b9b-aa7a-f525253409571252351812', '2019-06-26 18:45:11', '2019-06-26 18:45:10', '2019-06-26 18:45:10');
INSERT INTO `user_login_event` VALUES (3, 19, '', 3232235998, 0, 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '', 'b1ab1d9a-82ba-4d90-a8f3-e651b23b4858960868904', '2019-06-26 18:47:59', '2019-06-26 18:47:59', '2019-06-26 18:47:59');
INSERT INTO `user_login_event` VALUES (4, 18, '', 3232235998, 0, 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '', '7aa6c505-ba50-4d64-bb85-fe5e4f610db11657171421', '2019-06-26 18:59:27', '2019-06-26 18:59:26', '2019-06-26 18:59:26');
INSERT INTO `user_login_event` VALUES (5, 19, '', 3232235998, 0, 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '', '8f4d6b83-9a80-4922-800e-ce9f7f7a6aa571997419', '2019-06-27 12:01:18', '2019-06-27 12:01:18', '2019-06-27 12:01:18');
INSERT INTO `user_login_event` VALUES (6, 18, '', 3232235998, 0, 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:65.0) Gecko/20100101 Firefox/65.0', '', 'b48e3105-b6c9-482b-9d0e-dd242cc7f1731011634406', '2019-06-28 11:37:35', '2019-06-28 11:37:34', '2019-06-28 11:37:34');
INSERT INTO `user_login_event` VALUES (7, 17, '', 3232235998, 0, 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:65.0) Gecko/20100101 Firefox/65.0', '', '8fb900da-5d33-4550-ae03-d2b0bd7ede9b1692895650', '2019-06-28 13:18:40', '2019-06-28 13:18:40', '2019-06-28 13:18:40');
INSERT INTO `user_login_event` VALUES (8, 20, '', 3232235998, 0, 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36', '', '12f18b20-bc89-482a-8e3e-48a44ae85d13321670825', '2019-06-28 17:36:33', '2019-06-28 17:36:32', '2019-06-28 17:36:32');
INSERT INTO `user_login_event` VALUES (9, 10000, '', 3232235965, 0, 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36', '', 'b05b269e-9dcb-4b61-af76-205e94047b501387315978', '2019-07-01 14:10:27', '2019-07-01 14:10:27', '2019-07-01 14:10:27');
INSERT INTO `user_login_event` VALUES (10, 10000, '', 3232235965, 0, 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36', '', '1a4ec460-ceb7-4fc9-9710-83f943b203041489391617', '2019-07-01 14:26:51', '2019-07-01 14:26:51', '2019-07-01 14:26:51');
COMMIT;

-- ----------------------------
-- Table structure for user_notice_event
-- ----------------------------
DROP TABLE IF EXISTS `user_notice_event`;
CREATE TABLE `user_notice_event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '接收者用户',
  `behavior_id` int(11) NOT NULL COMMENT '行为编号(具体编号说明参考user_behavior表)',
  `type` int(4) NOT NULL DEFAULT '-1' COMMENT '通知类型 1验证码。2,用户通知 其他保留',
  `template_code` varchar(50) NOT NULL DEFAULT '0' COMMENT '通知模版代号',
  `target` varchar(100) NOT NULL COMMENT '目标邮件/手机号码',
  `token` varchar(255) NOT NULL COMMENT '唯一对外标志',
  `channel` int(4) NOT NULL DEFAULT '0' COMMENT '1.手机  2 邮件',
  `status` int(4) NOT NULL DEFAULT '0' COMMENT '-1 发送失败  0 新创建  1 发送成功 ',
  `params` varchar(1000) NOT NULL COMMENT '参数',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `ix_user_id` (`user_id`) USING BTREE,
  KEY `ix_created_date` (`created_date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='用户通知事件表';

-- ----------------------------
-- Records of user_notice_event
-- ----------------------------
BEGIN;
INSERT INTO `user_notice_event` VALUES (1, 1, 3011, 2, '0', 'dy.ppl.1@ppl.com', 'BWDf+YU460D7vUz4F0M0qvrUfqSAkfwcMG2ANPtmAhfgJ0w3/rzBpBke94gVvVeyBcYFXtY7Sd/faUmiDPMzeA==', 2, 0, '{\"password\":\"Test1234567890\",\"loginName\":\"dy.ppl.1@ppl.com\"}', '2019-06-26 16:57:44', '2019-06-26 16:57:44');
INSERT INTO `user_notice_event` VALUES (2, 2, 3011, 2, '0', 'dy.ppl.2@ppl.com', '3iM3h0LS34x5aPptO8Kf83q4sNFzEJV+rVxSEusyjKjFGdYTKGnPJnYt33K+puoHBcYFXtY7Sd/faUmiDPMzeA==', 2, 0, '{\"password\":\"Test1234567890\",\"loginName\":\"dy.ppl.2@ppl.com\"}', '2019-06-26 16:58:32', '2019-06-26 16:58:32');
INSERT INTO `user_notice_event` VALUES (3, 3, 3011, 2, '0', 'dy.ppl.3@ppl.com', 'IVFeT8Fo8cT4oNY4cQC/P34nFTvtTAE3jeVf6exqYyuH+uUaNCWKeedA8l9yHocvBcYFXtY7Sd/faUmiDPMzeA==', 2, 0, '{\"password\":\"Test1234567890\",\"loginName\":\"dy.ppl.3@ppl.com\"}', '2019-06-26 16:58:41', '2019-06-26 16:58:41');
INSERT INTO `user_notice_event` VALUES (4, 4, 3011, 2, '0', 'dy.ppl.4@ppl.com', '5pFeENogCDckRsmhD59Xfn9IKpCA6inmreAT4OLZVPJ3qY/1cTjjRShUET2wyhQaBcYFXtY7Sd/faUmiDPMzeA==', 2, 0, '{\"password\":\"Test1234567890\",\"loginName\":\"dy.ppl.4@ppl.com\"}', '2019-06-26 16:58:56', '2019-06-26 16:58:56');
INSERT INTO `user_notice_event` VALUES (5, 5, 3011, 2, '0', 'dy.ppl.5@ppl.com', 'fmWETALdfrdfhByv7HcCuJQh8g9LFNNl28nqUXpWJ9j49u76ZmhuoiHErkrH1ZvkBcYFXtY7Sd/faUmiDPMzeA==', 2, 0, '{\"password\":\"Test1234567890\",\"loginName\":\"dy.ppl.5@ppl.com\"}', '2019-06-26 16:59:03', '2019-06-26 16:59:03');
INSERT INTO `user_notice_event` VALUES (6, 6, 3011, 2, '0', 'dy.ppl.6@ppl.com', 'HTZeuWIA8mF7AHTXHkY68weing0d3R7rCG0ui4bHvHdFW1Bkc0URMcLYYpfK23KhBcYFXtY7Sd/faUmiDPMzeA==', 2, 0, '{\"password\":\"Test1234567890\",\"loginName\":\"dy.ppl.6@ppl.com\"}', '2019-06-26 16:59:11', '2019-06-26 16:59:11');
INSERT INTO `user_notice_event` VALUES (7, 7, 3011, 2, '0', 'dy.ppl.7@ppl.com', 'dQc38R/TrIgDodZAWANbf427myqRIKPMjcepUsY79lgJ7FsYAVTdMdPbT5f6xw8cBcYFXtY7Sd/faUmiDPMzeA==', 2, 0, '{\"password\":\"Test1234567890\",\"loginName\":\"dy.ppl.7@ppl.com\"}', '2019-06-26 16:59:18', '2019-06-26 16:59:18');
INSERT INTO `user_notice_event` VALUES (8, 8, 3011, 2, '0', 'dy.ppl.8@ppl.com', 'sOQNyDl6LiEwO8Jq9age0uFPw0yhGIWfTUepORcTaKmnP7wvz6fjqisbrSxIU+17BcYFXtY7Sd/faUmiDPMzeA==', 2, 0, '{\"password\":\"Test1234567890\",\"loginName\":\"dy.ppl.8@ppl.com\"}', '2019-06-26 16:59:25', '2019-06-26 16:59:25');
INSERT INTO `user_notice_event` VALUES (9, 9, 3011, 2, '0', 'dy.ppl.9@ppl.com', 'R+BZNGJnk3D2IbSL9lRz9UU9jHv6IHts2T/7apCbS9BQx9zGqsoXK7qKVcqlAzNSBcYFXtY7Sd/faUmiDPMzeA==', 2, 0, '{\"password\":\"Test1234567890\",\"loginName\":\"dy.ppl.9@ppl.com\"}', '2019-06-26 16:59:31', '2019-06-26 16:59:31');
INSERT INTO `user_notice_event` VALUES (10, 10, 3011, 2, '0', 'dy.ppl.10@ppl.com', 'VEwuJ/gQyYi8ScV+nO8zX18AE5uZFGufgPVPlo188CrETS9nhmYdV+UbILZt5hvf0QSUNR+F6CETGuoSLXZiwA==', 2, 0, '{\"password\":\"Test1234567890\",\"loginName\":\"dy.ppl.10@ppl.com\"}', '2019-06-26 16:59:38', '2019-06-26 16:59:38');
INSERT INTO `user_notice_event` VALUES (11, 11, 3011, 2, '0', 'dy.ppl.11@ppl.com', 'GueVFAoR6v61l6yv5fCluN+bTmsJUTV8FuA/wSX83d/vX8BqXXM90SidwH8a8VpcaNkYAUGpbPRhKfUxe2sZyQ==', 2, 0, '{\"password\":\"Test1234567890\",\"loginName\":\"dy.ppl.11@ppl.com\"}', '2019-06-26 16:59:44', '2019-06-26 16:59:44');
INSERT INTO `user_notice_event` VALUES (12, 12, 3011, 2, '0', 'dy.ppl.12@ppl.com', 'eQ9g6lFwUpkGlUf3ajlVCPrxQVzOoFu5ohrQRgk4ZeTXk/CmNGsDmMeTYOICNjzcZNjDfw7i2zP2Ar9c8vpLng==', 2, 0, '{\"password\":\"Test1234567890\",\"loginName\":\"dy.ppl.12@ppl.com\"}', '2019-06-26 17:35:16', '2019-06-26 17:35:16');
INSERT INTO `user_notice_event` VALUES (13, 13, 3011, 2, '0', 'dy.ppl.13@ppl.com', 'pLHY9ENfR958ZlnuiblejNhASNcb0QHrynJzgzhZn1tSq0rCV9rFsYwePcKo2n53bj6z9PJtRX4kVp1mczAuOg==', 2, 0, '{\"password\":\"Test1234567890\",\"loginName\":\"dy.ppl.13@ppl.com\"}', '2019-06-26 17:35:24', '2019-06-26 17:35:24');
INSERT INTO `user_notice_event` VALUES (14, 14, 3011, 2, '0', 'dy.ppl.14@ppl.com', 'A1Sg4WMbrPx/zFpjbZGJTFGrGMMV0lyRoTw0S2io6WyCzVjU7jY+1KQiwBHdRzrbB6M3yfmvAddUgMu2Q5PTzQ==', 2, 0, '{\"password\":\"Test1234567890\",\"loginName\":\"dy.ppl.14@ppl.com\"}', '2019-06-26 17:35:32', '2019-06-26 17:35:32');
INSERT INTO `user_notice_event` VALUES (15, 15, 3011, 2, '0', 'dy.ppl.15@ppl.com', 'REDOFJGOUT1lrYbYMcrrTal+BQod6zjojgdAa06gSVpq9jJk6i+xrEpQcD9oaP9wCS3XtUCxQoJDrM7bOpjPug==', 2, 0, '{\"password\":\"Test1234567890\",\"loginName\":\"dy.ppl.15@ppl.com\"}', '2019-06-26 17:35:39', '2019-06-26 17:35:39');
INSERT INTO `user_notice_event` VALUES (16, 16, 3011, 2, '0', 'dy.ppl.16@ppl.com', '0GQAfuxHRGK/397ZHtagJ41796ULrATMzybN5JSoEn/gwX08GSxkvtV+XrwLwUf1Owvz9LbDObRuX46TKSwIpg==', 2, 0, '{\"password\":\"Test1234567890\",\"loginName\":\"dy.ppl.16@ppl.com\"}', '2019-06-26 17:35:46', '2019-06-26 17:35:46');
INSERT INTO `user_notice_event` VALUES (17, 17, 3011, 2, '0', 'dy.ppl.17@ppl.com', 'r9oDur8CicmEc/kpRfgMQQdcy2boItWhRcoJH6uW0E0MXL/yQ7lw0CYkYCVFv2wTyPX7S/WpSZE5q4PGQhRG0Q==', 2, 0, '{\"password\":\"Test1234567890\",\"loginName\":\"dy.ppl.17@ppl.com\"}', '2019-06-26 17:35:53', '2019-06-26 17:35:53');
INSERT INTO `user_notice_event` VALUES (18, 18, 3011, 2, '0', 'dy.ppl.18@ppl.com', 'jIAC93XK3JHFhpZvUzlcuIdBjTjD6ko75zv36jIML8EH431ajgQBqAtWtAK1B78zL1y3u4QMk8m8jQ1SbI6j/w==', 2, 0, '{\"password\":\"Test1234567890\",\"loginName\":\"dy.ppl.18@ppl.com\"}', '2019-06-26 17:36:01', '2019-06-26 17:36:01');
INSERT INTO `user_notice_event` VALUES (19, 19, 3011, 2, '0', 'dy.ppl.19@ppl.com', '93KCsAuG8F3scNTUWzBttWOZDEQx6eRqDmGomeZ+FrTLl+Bny7NCJQQO6fwFD420bj6z9PJtRX4kVp1mczAuOg==', 2, 0, '{\"password\":\"Test1234567890\",\"loginName\":\"dy.ppl.19@ppl.com\"}', '2019-06-26 17:36:09', '2019-06-26 17:36:09');
INSERT INTO `user_notice_event` VALUES (20, 19, 3022, 1, '0', 'dy.ppl.19@ppl.com', 'fNLznDnvybTVftTDSi3Hswkb/JbSchPeaqDJkdhlovHWU65yhNenJznE100IRdOAkZ1bSHDI7KdpwCjYX9pH3A==', 2, 1, '{\"code\":\"120972\"}', '2019-06-26 18:42:02', '2019-06-26 18:42:40');
INSERT INTO `user_notice_event` VALUES (21, 19, 3022, 2, '0', 'dy.ppl.19@ppl.com', '6CC+zZb3O1vfstTKtQ8V3HyfDOrTlxEQfuCRDil8P8I+/SgRIezgMODHV9XL75C5mxQHbQFcDs50pzJ6fm78ZQ==', 2, 0, '{\"antiphishing\":\"\"}', '2019-06-26 18:42:40', '2019-06-26 18:42:40');
INSERT INTO `user_notice_event` VALUES (22, 18, 3022, 1, '0', 'dy.ppl.18@ppl.com', 'TW03xnSt/s3WmPSYL1elzHbj4TymdT+rf+Vs6ypMCQ4WriuIm/gzNzjY7O9Ns2wjD/MnF5diGgAI0SRCF+BmFA==', 2, 1, '{\"code\":\"905604\"}', '2019-06-26 18:44:55', '2019-06-26 18:45:10');
INSERT INTO `user_notice_event` VALUES (23, 18, 3022, 2, '0', 'dy.ppl.18@ppl.com', 'Gh2496utVvsCwFPJj+RtxPfcMqc9z3Ls3Zsx2hhcUk8jlPZefIa82d/UdCeFS6nyApfF47374pJflpNbHRvQWg==', 2, 0, '{\"antiphishing\":\"\"}', '2019-06-26 18:45:11', '2019-06-26 18:45:11');
INSERT INTO `user_notice_event` VALUES (24, 18, 3071, 1, '0', 'dy.ppl.18@ppl.com', 'm4u5PzciNbcpMpoRVLIP1ixqN6TAJ4gGI+lJ+1pWXaLp/ZBu2fY0dmnMP7CS0Yr7gC6V0IrQeKjFSFp2xX50xg==', 2, 1, '{\"code\":\"198882\",\"antiphishing\":\"dy.pp****@ppl.com\"}', '2019-06-26 18:45:57', '2019-06-26 18:46:13');
INSERT INTO `user_notice_event` VALUES (25, 18, 3072, 1, '0', 'dy.ppl.18@ppl.com', '9CrMaHuXKKykhitLOiV5Q1jjCkfJ8J/FV4glKzSpf7gf4ueQ+MO4gq3Bp/AGlNLpRpseInXQ4sq5tJIdffDfMQ==', 2, 1, '{\"code\":\"120800\",\"antiphishing\":\"dy.pp****@ppl.com\"}', '2019-06-26 18:46:20', '2019-06-26 18:46:31');
INSERT INTO `user_notice_event` VALUES (26, 19, 3022, 1, '0', 'dy.ppl.19@ppl.com', '2no7a8ZB/MSuIDB+jTuJXSPWaHAoJav4xPug1bQfwh3yzZDHfjcXIty5s69oAQHw/x+T/A5feSwFGAQN6LKB5Q==', 2, 1, '{\"code\":\"993691\"}', '2019-06-26 18:47:45', '2019-06-26 18:47:59');
INSERT INTO `user_notice_event` VALUES (27, 19, 3022, 2, '0', 'dy.ppl.19@ppl.com', 'gBP/ZUOiSrU3pztZY6o0Tyz7evEUovlIsxodX1PPY7W+bY5lfjTV43rykh+A9RhYyhxkdFkYv/rxy5xYdgtqHg==', 2, 0, '{\"antiphishing\":\"\"}', '2019-06-26 18:47:59', '2019-06-26 18:47:59');
INSERT INTO `user_notice_event` VALUES (28, 19, 3071, 1, '0', 'dy.ppl.19@ppl.com', 'lwEsBfJ4s4uhZn0xdHlmOrDHWyn9N+GdPPBaNpzhaXjmW1qMeYp50lKZ10xBb4mej91PDSRAI/skT1l6Tf8+5Q==', 2, 1, '{\"code\":\"697874\",\"antiphishing\":\"dy.pp****@ppl.com\"}', '2019-06-26 18:48:24', '2019-06-26 18:48:39');
INSERT INTO `user_notice_event` VALUES (29, 19, 3072, 1, '0', 'dy.ppl.19@ppl.com', '7Tf5O5xFCncVIJmanx+KaaVvpUVfykOb/xxQHIkkvhkCa8cGkwWFlV5TW0uItOa+h0LMTFW91X02gsJJ8wPxNg==', 2, 1, '{\"code\":\"508734\",\"antiphishing\":\"dy.pp****@ppl.com\"}', '2019-06-26 18:49:03', '2019-06-26 18:49:29');
INSERT INTO `user_notice_event` VALUES (30, 18, 3022, 1, '0', 'dy.ppl.18@ppl.com', '7c2sFK2c4XBm1cg7f5I36TcgqsKzgxWYi1JZwlMLJkkqHpO/V3D/7zwgRxAlWC9BkZ1bSHDI7KdpwCjYX9pH3A==', 2, 1, '{\"code\":\"454077\"}', '2019-06-26 18:59:13', '2019-06-26 18:59:26');
INSERT INTO `user_notice_event` VALUES (31, 18, 3022, 2, '0', 'dy.ppl.18@ppl.com', 'q8A9t1b0OYX0omJ86HAlpyMkBS6JrUdO4+ReXBGopOIvM35JM5Eg4bfdrytQ9hOJmxQHbQFcDs50pzJ6fm78ZQ==', 2, 0, '{\"antiphishing\":\"\"}', '2019-06-26 18:59:27', '2019-06-26 18:59:27');
INSERT INTO `user_notice_event` VALUES (32, 19, 3022, 1, '0', 'dy.ppl.19@ppl.com', 'ws4IEYzJ1GRvCNop5ZN4sPc/gOQklu5tRDW7sKoZMCPtx3conIg5qCvfEjas+lvMwd02v0aFfU1LqGz9dqEsRg==', 2, 1, '{\"code\":\"563041\"}', '2019-06-27 12:01:00', '2019-06-27 12:01:18');
INSERT INTO `user_notice_event` VALUES (33, 19, 3022, 2, '0', 'dy.ppl.19@ppl.com', '/nNo+kfOLWDoMkzZQ3RuRJZgpJF/aanSfClpVaimuN3niLsyDUW7qFHykllvqw7YkZ1bSHDI7KdpwCjYX9pH3A==', 2, 0, '{\"antiphishing\":\"\"}', '2019-06-27 12:01:18', '2019-06-27 12:01:18');
INSERT INTO `user_notice_event` VALUES (34, 19, 3022, 1, '0', 'dy.ppl.19@ppl.com', 'KlD0iPI7imW0Cc2LhhWxwlbj65KOuGF8nZZzKGyADKWQsgalmIzqVs2bLc8HS6s0yhxkdFkYv/rxy5xYdgtqHg==', 2, 0, '{\"code\":\"907138\"}', '2019-06-28 11:36:15', '2019-06-28 11:36:15');
INSERT INTO `user_notice_event` VALUES (35, 18, 3022, 1, '0', 'dy.ppl.18@ppl.com', '/7lfW+Frx4wT4qjjB11MsZLT2QlNNJ48xVihZBB0SiO/Q7u2qiVBcgTSHsZ42FJdD/MnF5diGgAI0SRCF+BmFA==', 2, 1, '{\"code\":\"140459\"}', '2019-06-28 11:37:19', '2019-06-28 11:37:34');
INSERT INTO `user_notice_event` VALUES (36, 18, 3022, 2, '0', 'dy.ppl.18@ppl.com', '2C26ntAET/clxZzDESMbFa4VAwLf+BYn4e9vG8FjAdf4Fhm37PNGQwtvaf3rfxMdkZ1bSHDI7KdpwCjYX9pH3A==', 2, 0, '{\"antiphishing\":\"\"}', '2019-06-28 11:37:35', '2019-06-28 11:37:35');
INSERT INTO `user_notice_event` VALUES (37, 17, 3022, 1, '0', 'dy.ppl.17@ppl.com', '9tF2Ba2z7H+gmxWbochAzO66ntONs5uFXzCK+A7WfiR7d8bqdjRKTtlYki6Kb0LPkZ1bSHDI7KdpwCjYX9pH3A==', 2, 1, '{\"code\":\"339484\"}', '2019-06-28 13:18:23', '2019-06-28 13:18:40');
INSERT INTO `user_notice_event` VALUES (38, 17, 3022, 2, '0', 'dy.ppl.17@ppl.com', '1QpWa97StXI4se3tvVWaTrtsTunyFLW4K30tNaynYex5rsLfqKYpA8R0vXlJHviJApfF47374pJflpNbHRvQWg==', 2, 0, '{\"antiphishing\":\"\"}', '2019-06-28 13:18:40', '2019-06-28 13:18:40');
INSERT INTO `user_notice_event` VALUES (39, -1, 3011, 1, '0', 'and9n9c3@fxfmail.com', 'Ny9TagYUJyur2PPNQEzwpfPmkJhnmcK/8IQf4tQPaNO+9FSMAKrSOXdWk5f1LuPryPX7S/WpSZE5q4PGQhRG0Q==', 2, 1, '{\"code\":\"457672\"}', '2019-06-28 17:35:27', '2019-06-28 17:36:31');
INSERT INTO `user_notice_event` VALUES (40, 20, 3011, 2, '0', 'and9n9c3@fxfmail.com', 'i8tOTNtIEDNJrsZEJXrZbBbW8ppF+Jdw0o9U7MRtCAda6v+84Aslv6+jJmdF1gXxZNjDfw7i2zP2Ar9c8vpLng==', 2, 0, '{\"antiphishing\":\"coinmex.com\"}', '2019-06-28 17:36:32', '2019-06-28 17:36:32');
INSERT INTO `user_notice_event` VALUES (41, -1, 3011, 1, '0', 'sunqc5@163.com', 'sYATK7+hj4avsYK1i3Q0v9rNklTgtEo5a2ztG+kfpKc9ZYy47lY4TnAkN7C8QXBlB6M3yfmvAddUgMu2Q5PTzQ==', 2, 1, '{\"code\":\"264415\"}', '2019-07-01 14:09:16', '2019-07-01 14:10:26');
INSERT INTO `user_notice_event` VALUES (42, 10000, 3011, 2, '0', 'sunqc5@163.com', '0PbTV/tifa6L7+pO4E8he8VUIU8+jlg1RK/6qps0jNoaB1M6vAeQ/qAio+RPf9B4CmKktyyVaYMNvsvIpyP7VQ==', 2, 0, '{\"antiphishing\":\"coinmex.com\"}', '2019-07-01 14:10:27', '2019-07-01 14:10:27');
INSERT INTO `user_notice_event` VALUES (43, 10000, 3022, 1, '0', 'sunqc5@163.com', 'uojd2Z2UvQ1uOXbRz3RlsXSBo3kEosLSpanj3k8V2Kt6Z0MZNaM4UEsKOkAG0cA6LGJAgHyNcf4oDe2IXcT6oA==', 2, 1, '{\"code\":\"307949\"}', '2019-07-01 14:26:30', '2019-07-01 14:26:51');
INSERT INTO `user_notice_event` VALUES (44, 10000, 3022, 2, '0', 'sunqc5@163.com', 'lCBVQna/lI078Mu2ue50sn4Atz45iM0QEX1V4dI/d8vMEdBMcgqaHVMXC+yfE9cRQnTwy0DV6cUqweB2HCAmWg==', 2, 0, '{\"antiphishing\":\"\"}', '2019-07-01 14:26:51', '2019-07-01 14:26:51');
COMMIT;

-- ----------------------------
-- Table structure for user_product_favorites
-- ----------------------------
DROP TABLE IF EXISTS `user_product_favorites`;
CREATE TABLE `user_product_favorites` (
  `user_id` bigint(20) NOT NULL COMMENT '用户收藏币种对表',
  `product_ids` varchar(1000) NOT NULL,
  `broker_id` int(11) NOT NULL DEFAULT '1' COMMENT '券商ID',
  PRIMARY KEY (`user_id`,`broker_id`),
  UNIQUE KEY `index_name` (`user_id`,`broker_id`) USING BTREE COMMENT '用户收藏索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户币对收藏表';

-- ----------------------------
-- Table structure for user_secure_event
-- ----------------------------
DROP TABLE IF EXISTS `user_secure_event`;
CREATE TABLE `user_secure_event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `behavior_id` int(11) NOT NULL DEFAULT '0' COMMENT '行为编号(具体编号说明参考user_behavior表)',
  `behavior_name` varchar(50) NOT NULL COMMENT '行为名称(具体编号说明参考user_behavior表)',
  `params` varchar(500) NOT NULL COMMENT '修改之前的参数json',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '0:未处理 1:人工确认',
  `message` varchar(100) NOT NULL DEFAULT '' COMMENT '说明',
  `ip_address` bigint(10) NOT NULL DEFAULT '0' COMMENT 'ip地址',
  `region` varchar(100) NOT NULL COMMENT '操作执行所在地',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `ix_user_id` (`user_id`) USING BTREE,
  KEY `ix_created_date` (`created_date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='用户安全事件表';

-- ----------------------------
-- Records of user_secure_event
-- ----------------------------
BEGIN;
INSERT INTO `user_secure_event` VALUES (1, 19, 1102, 'login_step2_auth', '', 0, '', 3232235998, '', '2019-06-26 18:42:41', '2019-06-26 18:42:41');
INSERT INTO `user_secure_event` VALUES (2, 18, 1102, 'login_step2_auth', '', 0, '', 3232235998, '', '2019-06-26 18:45:11', '2019-06-26 18:45:11');
INSERT INTO `user_secure_event` VALUES (3, 19, 1102, 'login_step2_auth', '', 0, '', 3232235998, '', '2019-06-26 18:47:59', '2019-06-26 18:47:59');
INSERT INTO `user_secure_event` VALUES (4, 18, 1102, 'login_step2_auth', '', 0, '', 3232235998, '', '2019-06-26 18:59:27', '2019-06-26 18:59:27');
INSERT INTO `user_secure_event` VALUES (5, 19, 1102, 'login_step2_auth', '', 0, '', 3232235998, '', '2019-06-27 12:01:18', '2019-06-27 12:01:18');
INSERT INTO `user_secure_event` VALUES (6, 18, 1102, 'login_step2_auth', '', 0, '', 3232235998, '', '2019-06-28 11:37:35', '2019-06-28 11:37:35');
INSERT INTO `user_secure_event` VALUES (7, 17, 1102, 'login_step2_auth', '', 0, '', 3232235998, '', '2019-06-28 13:18:40', '2019-06-28 13:18:40');
INSERT INTO `user_secure_event` VALUES (8, 20, 1001, 'register_email', '', 0, '', 3232235998, '', '2019-06-28 17:36:33', '2019-06-28 17:36:33');
INSERT INTO `user_secure_event` VALUES (9, 10000, 1001, 'register_email', '', 0, '', 3232235965, '', '2019-07-01 14:10:27', '2019-07-01 14:10:27');
INSERT INTO `user_secure_event` VALUES (10, 10000, 1102, 'login_step2_auth', '', 0, '', 3232235965, '', '2019-07-01 14:26:51', '2019-07-01 14:26:51');
COMMIT;

-- ----------------------------
-- Table structure for user_settings
-- ----------------------------
DROP TABLE IF EXISTS `user_settings`;
CREATE TABLE `user_settings` (
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `login_auth_flag` int(11) NOT NULL DEFAULT '1' COMMENT '是否开启登录2次验证默认开启',
  `google_auth_flag` int(2) NOT NULL DEFAULT '0' COMMENT 'Google验证码绑定状态 0未绑定 1绑定',
  `email_auth_flag` int(2) NOT NULL DEFAULT '0' COMMENT '邮箱验证状态 0：未开启 1：开启',
  `mobile_auth_flag` int(4) NOT NULL DEFAULT '0' COMMENT '手机验证状态 0：未开启 1：开启',
  `trade_auth_flag` int(2) NOT NULL DEFAULT '0' COMMENT '交易密码绑定状态表示交易时是否需要输入交易密码 0未绑定 1绑定',
  `login_pwd_strength` int(2) NOT NULL DEFAULT '0' COMMENT '登录密码强度级别',
  `trade_pwd_strength` int(2) NOT NULL DEFAULT '0' COMMENT '交易密码强度级别',
  `trade_pwd_input` int(2) NOT NULL DEFAULT '1' COMMENT '交易时输入资金密码设置0:每次输入;1每2小时输入;2不输入;默认为1',
  `alipay_auth_flag` int(2) NOT NULL DEFAULT '0' COMMENT '支付宝开关 0:关闭,1:打开',
  `wechat_pay_auth_flag` int(2) NOT NULL DEFAULT '0' COMMENT '微信支付开关 0:关闭,1:打开',
  `bank_pay_auth_flag` int(2) NOT NULL DEFAULT '0' COMMENT '银行卡支付开关0关闭1开始',
  `spot_frozen_flag` int(11) NOT NULL DEFAULT '0' COMMENT '是否冻结现货业务1表示是,0表示否，默认为0',
  `c2c_frozen_flag` int(11) NOT NULL DEFAULT '0' COMMENT '是否冻结C2C业务1表示是,0表示否，默认为0',
  `contracts_frozen_flag` int(11) NOT NULL DEFAULT '0' COMMENT '是否冻结合约业务1表示是,0表示否，默认为0',
  `asset_frozen_flag` int(11) NOT NULL DEFAULT '0' COMMENT '是否冻结资金管理业务1表示是,0表示否，默认为0',
  `spot_protocol_flag` int(11) NOT NULL DEFAULT '0' COMMENT '是否同意协议1表示是,0表示否，默认为0',
  `c2c_protocol_flag` int(11) NOT NULL DEFAULT '0' COMMENT '是否同意协议1表示是,0表示否，默认为0',
  `portfolio_protocol_flag` int(11) NOT NULL DEFAULT '0' COMMENT '是否同意协议1表示是,0表示否，默认为0',
  `perpetual_protocol_flag` int(11) NOT NULL DEFAULT '0' COMMENT '是否同意协议1表示是,0表示否，默认为0',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`user_id`),
  KEY `ix_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户开关设置表';

-- ----------------------------
-- Records of user_settings
-- ----------------------------
BEGIN;
INSERT INTO `user_settings` VALUES (1, 1, 0, 1, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2019-06-26 16:57:44', '2019-06-26 16:57:44');
INSERT INTO `user_settings` VALUES (2, 1, 0, 1, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2019-06-26 16:58:32', '2019-06-26 16:58:32');
INSERT INTO `user_settings` VALUES (3, 1, 0, 1, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2019-06-26 16:58:41', '2019-06-26 16:58:41');
INSERT INTO `user_settings` VALUES (4, 1, 0, 1, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2019-06-26 16:58:56', '2019-06-26 16:58:56');
INSERT INTO `user_settings` VALUES (5, 1, 0, 1, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2019-06-26 16:59:03', '2019-06-26 16:59:03');
INSERT INTO `user_settings` VALUES (6, 1, 0, 1, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2019-06-26 16:59:11', '2019-06-26 16:59:11');
INSERT INTO `user_settings` VALUES (7, 1, 0, 1, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2019-06-26 16:59:18', '2019-06-26 16:59:18');
INSERT INTO `user_settings` VALUES (8, 1, 0, 1, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2019-06-26 16:59:25', '2019-06-26 16:59:25');
INSERT INTO `user_settings` VALUES (9, 1, 0, 1, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2019-06-26 16:59:31', '2019-06-26 16:59:31');
INSERT INTO `user_settings` VALUES (10, 1, 0, 1, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2019-06-26 16:59:38', '2019-06-26 16:59:38');
INSERT INTO `user_settings` VALUES (11, 1, 0, 1, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2019-06-26 16:59:44', '2019-06-26 16:59:44');
INSERT INTO `user_settings` VALUES (12, 1, 0, 1, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2019-06-26 17:35:16', '2019-06-26 17:35:16');
INSERT INTO `user_settings` VALUES (13, 1, 0, 1, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2019-06-26 17:35:24', '2019-06-26 17:35:24');
INSERT INTO `user_settings` VALUES (14, 1, 0, 1, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2019-06-26 17:35:32', '2019-06-26 17:35:32');
INSERT INTO `user_settings` VALUES (15, 1, 0, 1, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2019-06-26 17:35:39', '2019-06-26 17:35:39');
INSERT INTO `user_settings` VALUES (16, 1, 0, 1, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2019-06-26 17:35:46', '2019-06-26 17:35:46');
INSERT INTO `user_settings` VALUES (17, 1, 0, 1, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, '2019-06-26 17:35:53', '2019-06-28 13:18:59');
INSERT INTO `user_settings` VALUES (18, 1, 0, 1, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, '2019-06-26 17:36:00', '2019-06-26 18:45:51');
INSERT INTO `user_settings` VALUES (19, 1, 0, 1, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, '2019-06-26 17:36:09', '2019-06-26 18:48:21');
INSERT INTO `user_settings` VALUES (20, 1, 0, 1, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, '2019-06-28 17:36:32', '2019-06-28 17:43:35');
INSERT INTO `user_settings` VALUES (10000, 1, 0, 1, 0, 0, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, '2019-07-01 14:10:27', '2019-07-01 14:18:40');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
