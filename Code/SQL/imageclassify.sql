/*
Navicat MySQL Data Transfer

Source Server         : 华为数据库
Source Server Version : 50636
Source Host           : 122.112.232.97:3306
Source Database       : imageclassify

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2017-09-09 12:43:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `admin_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(32) NOT NULL,
  `admin_password` varchar(32) NOT NULL,
  `admin_token` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', 'admin', '21232f297a57a5a743894a0e4a801fc3', '2c07306ad7bd685d6e5fc5faa9bc08ad');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `category_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `category_name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1', '水果');
INSERT INTO `category` VALUES ('2', '动物');
INSERT INTO `category` VALUES ('3', '植物');
INSERT INTO `category` VALUES ('4', '人类');
INSERT INTO `category` VALUES ('5', '科技');
INSERT INTO `category` VALUES ('6', '餐厅');
INSERT INTO `category` VALUES ('7', '车站');
INSERT INTO `category` VALUES ('8', '广场');
INSERT INTO `category` VALUES ('9', '海滩');
INSERT INTO `category` VALUES ('10', '红树林');
INSERT INTO `category` VALUES ('11', '机场');
INSERT INTO `category` VALUES ('12', '街道');
INSERT INTO `category` VALUES ('13', '商场');
INSERT INTO `category` VALUES ('14', '世界之窗');
INSERT INTO `category` VALUES ('15', '泰山');
INSERT INTO `category` VALUES ('16', '学校');
INSERT INTO `category` VALUES ('17', '医院');
INSERT INTO `category` VALUES ('18', '长城');
INSERT INTO `category` VALUES ('20', '家具');
INSERT INTO `category` VALUES ('21', '星空');
INSERT INTO `category` VALUES ('22', '书店');
INSERT INTO `category` VALUES ('23', '服装');
INSERT INTO `category` VALUES ('24', '食品');

-- ----------------------------
-- Table structure for checkin
-- ----------------------------
DROP TABLE IF EXISTS `checkin`;
CREATE TABLE `checkin` (
  `checkin_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `checkin_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `user_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`checkin_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `checkin_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of checkin
-- ----------------------------
INSERT INTO `checkin` VALUES ('1', '2017-08-09 15:58:09', '7');
INSERT INTO `checkin` VALUES ('2', '2017-08-18 15:58:21', '7');
INSERT INTO `checkin` VALUES ('3', '2017-08-10 16:03:40', '7');
INSERT INTO `checkin` VALUES ('6', '2017-09-03 10:12:24', '7');
INSERT INTO `checkin` VALUES ('7', '2017-09-05 23:41:20', '8');
INSERT INTO `checkin` VALUES ('8', '2017-09-06 00:05:34', '8');

-- ----------------------------
-- Table structure for composition
-- ----------------------------
DROP TABLE IF EXISTS `composition`;
CREATE TABLE `composition` (
  `is_masked` varchar(2) NOT NULL,
  `task_id` int(10) unsigned NOT NULL,
  `img_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`task_id`,`img_id`),
  KEY `img_id` (`img_id`),
  CONSTRAINT `composition_ibfk_1` FOREIGN KEY (`task_id`) REFERENCES `task` (`task_id`),
  CONSTRAINT `composition_ibfk_2` FOREIGN KEY (`img_id`) REFERENCES `image` (`img_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of composition
-- ----------------------------
INSERT INTO `composition` VALUES ('1', '5', '177');
INSERT INTO `composition` VALUES ('1', '5', '182');
INSERT INTO `composition` VALUES ('1', '5', '192');
INSERT INTO `composition` VALUES ('1', '5', '197');
INSERT INTO `composition` VALUES ('1', '5', '201');
INSERT INTO `composition` VALUES ('1', '5', '215');
INSERT INTO `composition` VALUES ('1', '5', '216');
INSERT INTO `composition` VALUES ('1', '5', '218');
INSERT INTO `composition` VALUES ('1', '5', '221');
INSERT INTO `composition` VALUES ('1', '5', '224');
INSERT INTO `composition` VALUES ('1', '6', '101');
INSERT INTO `composition` VALUES ('0', '6', '108');
INSERT INTO `composition` VALUES ('1', '6', '128');
INSERT INTO `composition` VALUES ('1', '6', '135');
INSERT INTO `composition` VALUES ('1', '6', '147');
INSERT INTO `composition` VALUES ('1', '6', '153');
INSERT INTO `composition` VALUES ('1', '6', '154');
INSERT INTO `composition` VALUES ('1', '6', '165');
INSERT INTO `composition` VALUES ('0', '6', '173');
INSERT INTO `composition` VALUES ('1', '6', '174');
INSERT INTO `composition` VALUES ('0', '7', '81');
INSERT INTO `composition` VALUES ('0', '7', '86');
INSERT INTO `composition` VALUES ('0', '7', '89');
INSERT INTO `composition` VALUES ('0', '7', '92');
INSERT INTO `composition` VALUES ('0', '7', '95');
INSERT INTO `composition` VALUES ('0', '7', '97');
INSERT INTO `composition` VALUES ('0', '7', '100');
INSERT INTO `composition` VALUES ('0', '7', '105');
INSERT INTO `composition` VALUES ('0', '7', '108');
INSERT INTO `composition` VALUES ('0', '7', '173');
INSERT INTO `composition` VALUES ('0', '8', '81');
INSERT INTO `composition` VALUES ('0', '8', '86');
INSERT INTO `composition` VALUES ('0', '8', '89');
INSERT INTO `composition` VALUES ('0', '8', '92');
INSERT INTO `composition` VALUES ('0', '8', '95');
INSERT INTO `composition` VALUES ('0', '8', '97');
INSERT INTO `composition` VALUES ('0', '8', '100');
INSERT INTO `composition` VALUES ('0', '8', '105');
INSERT INTO `composition` VALUES ('0', '8', '108');
INSERT INTO `composition` VALUES ('0', '8', '173');
INSERT INTO `composition` VALUES ('0', '9', '81');
INSERT INTO `composition` VALUES ('0', '9', '86');
INSERT INTO `composition` VALUES ('0', '9', '89');
INSERT INTO `composition` VALUES ('0', '9', '92');
INSERT INTO `composition` VALUES ('0', '9', '95');
INSERT INTO `composition` VALUES ('0', '9', '97');
INSERT INTO `composition` VALUES ('0', '9', '100');
INSERT INTO `composition` VALUES ('0', '9', '105');
INSERT INTO `composition` VALUES ('0', '9', '108');
INSERT INTO `composition` VALUES ('0', '9', '173');
INSERT INTO `composition` VALUES ('1', '10', '1');
INSERT INTO `composition` VALUES ('1', '10', '2');
INSERT INTO `composition` VALUES ('1', '10', '4');
INSERT INTO `composition` VALUES ('1', '10', '5');
INSERT INTO `composition` VALUES ('1', '10', '6');
INSERT INTO `composition` VALUES ('1', '10', '14');
INSERT INTO `composition` VALUES ('1', '10', '15');
INSERT INTO `composition` VALUES ('1', '10', '18');
INSERT INTO `composition` VALUES ('0', '10', '48');
INSERT INTO `composition` VALUES ('1', '10', '114');
INSERT INTO `composition` VALUES ('1', '11', '3');
INSERT INTO `composition` VALUES ('1', '11', '9');
INSERT INTO `composition` VALUES ('1', '11', '10');
INSERT INTO `composition` VALUES ('1', '11', '11');
INSERT INTO `composition` VALUES ('1', '11', '12');
INSERT INTO `composition` VALUES ('1', '11', '13');
INSERT INTO `composition` VALUES ('1', '11', '16');
INSERT INTO `composition` VALUES ('1', '11', '17');
INSERT INTO `composition` VALUES ('1', '11', '19');
INSERT INTO `composition` VALUES ('1', '11', '48');
INSERT INTO `composition` VALUES ('1', '12', '7');
INSERT INTO `composition` VALUES ('1', '12', '8');
INSERT INTO `composition` VALUES ('1', '12', '20');
INSERT INTO `composition` VALUES ('1', '12', '21');
INSERT INTO `composition` VALUES ('1', '12', '22');
INSERT INTO `composition` VALUES ('1', '12', '23');
INSERT INTO `composition` VALUES ('1', '12', '24');
INSERT INTO `composition` VALUES ('1', '12', '26');
INSERT INTO `composition` VALUES ('1', '12', '36');
INSERT INTO `composition` VALUES ('1', '12', '39');
INSERT INTO `composition` VALUES ('0', '13', '25');
INSERT INTO `composition` VALUES ('0', '13', '27');
INSERT INTO `composition` VALUES ('0', '13', '28');
INSERT INTO `composition` VALUES ('0', '13', '29');
INSERT INTO `composition` VALUES ('0', '13', '30');
INSERT INTO `composition` VALUES ('0', '13', '31');
INSERT INTO `composition` VALUES ('0', '13', '34');
INSERT INTO `composition` VALUES ('0', '13', '35');
INSERT INTO `composition` VALUES ('0', '13', '37');
INSERT INTO `composition` VALUES ('0', '13', '40');
INSERT INTO `composition` VALUES ('1', '14', '189');
INSERT INTO `composition` VALUES ('1', '14', '191');
INSERT INTO `composition` VALUES ('1', '14', '192');
INSERT INTO `composition` VALUES ('0', '14', '204');
INSERT INTO `composition` VALUES ('0', '14', '206');
INSERT INTO `composition` VALUES ('1', '14', '210');
INSERT INTO `composition` VALUES ('1', '14', '211');
INSERT INTO `composition` VALUES ('1', '14', '214');
INSERT INTO `composition` VALUES ('1', '14', '221');
INSERT INTO `composition` VALUES ('1', '14', '224');
INSERT INTO `composition` VALUES ('1', '15', '190');
INSERT INTO `composition` VALUES ('1', '15', '195');
INSERT INTO `composition` VALUES ('1', '15', '204');
INSERT INTO `composition` VALUES ('1', '15', '206');
INSERT INTO `composition` VALUES ('1', '15', '208');
INSERT INTO `composition` VALUES ('1', '15', '209');
INSERT INTO `composition` VALUES ('1', '15', '216');
INSERT INTO `composition` VALUES ('1', '15', '218');
INSERT INTO `composition` VALUES ('1', '15', '220');
INSERT INTO `composition` VALUES ('1', '15', '223');
INSERT INTO `composition` VALUES ('1', '16', '187');
INSERT INTO `composition` VALUES ('1', '16', '188');
INSERT INTO `composition` VALUES ('1', '16', '193');
INSERT INTO `composition` VALUES ('1', '16', '194');
INSERT INTO `composition` VALUES ('1', '16', '196');
INSERT INTO `composition` VALUES ('1', '16', '197');
INSERT INTO `composition` VALUES ('1', '16', '199');
INSERT INTO `composition` VALUES ('1', '16', '200');
INSERT INTO `composition` VALUES ('1', '16', '201');
INSERT INTO `composition` VALUES ('1', '16', '202');
INSERT INTO `composition` VALUES ('1', '16', '203');
INSERT INTO `composition` VALUES ('1', '16', '205');
INSERT INTO `composition` VALUES ('1', '16', '212');
INSERT INTO `composition` VALUES ('1', '16', '213');
INSERT INTO `composition` VALUES ('1', '16', '215');
INSERT INTO `composition` VALUES ('1', '16', '217');
INSERT INTO `composition` VALUES ('1', '16', '219');
INSERT INTO `composition` VALUES ('1', '16', '222');
INSERT INTO `composition` VALUES ('1', '16', '225');
INSERT INTO `composition` VALUES ('1', '16', '226');
INSERT INTO `composition` VALUES ('1', '17', '142');
INSERT INTO `composition` VALUES ('1', '17', '160');
INSERT INTO `composition` VALUES ('1', '17', '161');
INSERT INTO `composition` VALUES ('1', '17', '162');
INSERT INTO `composition` VALUES ('1', '17', '163');
INSERT INTO `composition` VALUES ('1', '17', '164');
INSERT INTO `composition` VALUES ('1', '17', '165');
INSERT INTO `composition` VALUES ('1', '17', '166');
INSERT INTO `composition` VALUES ('1', '17', '167');
INSERT INTO `composition` VALUES ('1', '17', '168');
INSERT INTO `composition` VALUES ('1', '17', '169');
INSERT INTO `composition` VALUES ('1', '17', '170');
INSERT INTO `composition` VALUES ('1', '17', '171');
INSERT INTO `composition` VALUES ('1', '17', '172');
INSERT INTO `composition` VALUES ('1', '17', '173');
INSERT INTO `composition` VALUES ('1', '17', '174');
INSERT INTO `composition` VALUES ('1', '17', '175');
INSERT INTO `composition` VALUES ('1', '17', '176');
INSERT INTO `composition` VALUES ('1', '17', '177');
INSERT INTO `composition` VALUES ('1', '17', '178');
INSERT INTO `composition` VALUES ('1', '17', '179');
INSERT INTO `composition` VALUES ('1', '17', '180');
INSERT INTO `composition` VALUES ('1', '17', '181');
INSERT INTO `composition` VALUES ('1', '17', '182');
INSERT INTO `composition` VALUES ('1', '17', '183');
INSERT INTO `composition` VALUES ('1', '17', '184');
INSERT INTO `composition` VALUES ('1', '17', '185');
INSERT INTO `composition` VALUES ('1', '17', '186');
INSERT INTO `composition` VALUES ('1', '17', '198');
INSERT INTO `composition` VALUES ('1', '17', '207');
INSERT INTO `composition` VALUES ('0', '18', '2');
INSERT INTO `composition` VALUES ('0', '18', '3');
INSERT INTO `composition` VALUES ('0', '18', '7');
INSERT INTO `composition` VALUES ('0', '18', '8');
INSERT INTO `composition` VALUES ('0', '18', '11');
INSERT INTO `composition` VALUES ('0', '18', '17');
INSERT INTO `composition` VALUES ('0', '18', '18');
INSERT INTO `composition` VALUES ('0', '18', '19');
INSERT INTO `composition` VALUES ('0', '18', '21');
INSERT INTO `composition` VALUES ('0', '18', '62');
INSERT INTO `composition` VALUES ('0', '18', '67');
INSERT INTO `composition` VALUES ('0', '18', '68');
INSERT INTO `composition` VALUES ('0', '18', '69');
INSERT INTO `composition` VALUES ('0', '18', '70');
INSERT INTO `composition` VALUES ('0', '18', '77');
INSERT INTO `composition` VALUES ('0', '18', '78');
INSERT INTO `composition` VALUES ('0', '18', '79');
INSERT INTO `composition` VALUES ('0', '18', '81');
INSERT INTO `composition` VALUES ('0', '18', '84');
INSERT INTO `composition` VALUES ('0', '18', '86');
INSERT INTO `composition` VALUES ('0', '18', '100');
INSERT INTO `composition` VALUES ('0', '18', '102');
INSERT INTO `composition` VALUES ('0', '18', '106');
INSERT INTO `composition` VALUES ('0', '18', '114');
INSERT INTO `composition` VALUES ('0', '18', '116');
INSERT INTO `composition` VALUES ('0', '18', '123');
INSERT INTO `composition` VALUES ('0', '18', '135');
INSERT INTO `composition` VALUES ('0', '18', '151');
INSERT INTO `composition` VALUES ('0', '18', '212');
INSERT INTO `composition` VALUES ('0', '18', '225');
INSERT INTO `composition` VALUES ('0', '19', '2');
INSERT INTO `composition` VALUES ('0', '19', '3');
INSERT INTO `composition` VALUES ('0', '19', '7');
INSERT INTO `composition` VALUES ('0', '19', '8');
INSERT INTO `composition` VALUES ('0', '19', '11');
INSERT INTO `composition` VALUES ('0', '19', '17');
INSERT INTO `composition` VALUES ('0', '19', '18');
INSERT INTO `composition` VALUES ('0', '19', '19');
INSERT INTO `composition` VALUES ('1', '19', '21');
INSERT INTO `composition` VALUES ('1', '19', '62');
INSERT INTO `composition` VALUES ('1', '19', '67');
INSERT INTO `composition` VALUES ('1', '19', '68');
INSERT INTO `composition` VALUES ('1', '19', '69');
INSERT INTO `composition` VALUES ('1', '19', '70');
INSERT INTO `composition` VALUES ('1', '19', '77');
INSERT INTO `composition` VALUES ('1', '19', '78');
INSERT INTO `composition` VALUES ('1', '19', '79');
INSERT INTO `composition` VALUES ('0', '19', '81');
INSERT INTO `composition` VALUES ('0', '19', '84');
INSERT INTO `composition` VALUES ('0', '19', '86');
INSERT INTO `composition` VALUES ('1', '19', '100');
INSERT INTO `composition` VALUES ('0', '19', '102');
INSERT INTO `composition` VALUES ('0', '19', '106');
INSERT INTO `composition` VALUES ('1', '19', '114');
INSERT INTO `composition` VALUES ('0', '19', '116');
INSERT INTO `composition` VALUES ('0', '19', '123');
INSERT INTO `composition` VALUES ('1', '19', '135');
INSERT INTO `composition` VALUES ('0', '19', '151');
INSERT INTO `composition` VALUES ('1', '19', '212');
INSERT INTO `composition` VALUES ('1', '19', '225');
INSERT INTO `composition` VALUES ('1', '20', '11');
INSERT INTO `composition` VALUES ('1', '20', '19');
INSERT INTO `composition` VALUES ('1', '20', '81');
INSERT INTO `composition` VALUES ('1', '20', '84');
INSERT INTO `composition` VALUES ('1', '20', '86');
INSERT INTO `composition` VALUES ('1', '20', '102');
INSERT INTO `composition` VALUES ('1', '20', '106');
INSERT INTO `composition` VALUES ('1', '20', '116');
INSERT INTO `composition` VALUES ('1', '20', '123');
INSERT INTO `composition` VALUES ('1', '20', '151');
INSERT INTO `composition` VALUES ('1', '21', '1');
INSERT INTO `composition` VALUES ('1', '21', '2');
INSERT INTO `composition` VALUES ('1', '21', '3');
INSERT INTO `composition` VALUES ('1', '21', '4');
INSERT INTO `composition` VALUES ('1', '21', '5');
INSERT INTO `composition` VALUES ('1', '21', '6');
INSERT INTO `composition` VALUES ('1', '21', '7');
INSERT INTO `composition` VALUES ('1', '21', '8');
INSERT INTO `composition` VALUES ('1', '21', '9');
INSERT INTO `composition` VALUES ('1', '21', '10');
INSERT INTO `composition` VALUES ('1', '21', '12');
INSERT INTO `composition` VALUES ('1', '21', '13');
INSERT INTO `composition` VALUES ('1', '21', '14');
INSERT INTO `composition` VALUES ('1', '21', '15');
INSERT INTO `composition` VALUES ('1', '21', '16');
INSERT INTO `composition` VALUES ('1', '21', '17');
INSERT INTO `composition` VALUES ('1', '21', '18');
INSERT INTO `composition` VALUES ('1', '21', '20');
INSERT INTO `composition` VALUES ('0', '21', '21');
INSERT INTO `composition` VALUES ('1', '21', '135');
INSERT INTO `composition` VALUES ('1', '22', '11');
INSERT INTO `composition` VALUES ('1', '22', '19');
INSERT INTO `composition` VALUES ('1', '22', '21');
INSERT INTO `composition` VALUES ('1', '22', '23');
INSERT INTO `composition` VALUES ('1', '22', '25');
INSERT INTO `composition` VALUES ('1', '22', '26');
INSERT INTO `composition` VALUES ('1', '22', '27');
INSERT INTO `composition` VALUES ('1', '22', '28');
INSERT INTO `composition` VALUES ('1', '22', '29');
INSERT INTO `composition` VALUES ('1', '22', '30');
INSERT INTO `composition` VALUES ('1', '22', '31');
INSERT INTO `composition` VALUES ('1', '22', '32');
INSERT INTO `composition` VALUES ('1', '22', '33');
INSERT INTO `composition` VALUES ('1', '22', '34');
INSERT INTO `composition` VALUES ('1', '22', '35');
INSERT INTO `composition` VALUES ('1', '22', '36');
INSERT INTO `composition` VALUES ('1', '22', '37');
INSERT INTO `composition` VALUES ('1', '22', '38');
INSERT INTO `composition` VALUES ('0', '22', '39');
INSERT INTO `composition` VALUES ('1', '22', '40');
INSERT INTO `composition` VALUES ('1', '23', '22');
INSERT INTO `composition` VALUES ('1', '23', '24');
INSERT INTO `composition` VALUES ('1', '23', '39');
INSERT INTO `composition` VALUES ('1', '23', '43');
INSERT INTO `composition` VALUES ('1', '23', '46');
INSERT INTO `composition` VALUES ('1', '23', '48');
INSERT INTO `composition` VALUES ('0', '23', '50');
INSERT INTO `composition` VALUES ('1', '23', '52');
INSERT INTO `composition` VALUES ('1', '23', '57');
INSERT INTO `composition` VALUES ('1', '23', '59');
INSERT INTO `composition` VALUES ('0', '24', '41');
INSERT INTO `composition` VALUES ('0', '24', '42');
INSERT INTO `composition` VALUES ('0', '24', '44');
INSERT INTO `composition` VALUES ('0', '24', '45');
INSERT INTO `composition` VALUES ('1', '24', '47');
INSERT INTO `composition` VALUES ('0', '24', '49');
INSERT INTO `composition` VALUES ('0', '24', '50');
INSERT INTO `composition` VALUES ('0', '24', '51');
INSERT INTO `composition` VALUES ('0', '24', '53');
INSERT INTO `composition` VALUES ('0', '24', '54');
INSERT INTO `composition` VALUES ('0', '24', '55');
INSERT INTO `composition` VALUES ('0', '24', '56');
INSERT INTO `composition` VALUES ('0', '24', '58');
INSERT INTO `composition` VALUES ('0', '24', '60');
INSERT INTO `composition` VALUES ('0', '24', '61');
INSERT INTO `composition` VALUES ('0', '24', '62');
INSERT INTO `composition` VALUES ('0', '24', '63');
INSERT INTO `composition` VALUES ('0', '24', '64');
INSERT INTO `composition` VALUES ('0', '24', '65');
INSERT INTO `composition` VALUES ('0', '24', '67');
INSERT INTO `composition` VALUES ('0', '24', '69');
INSERT INTO `composition` VALUES ('0', '24', '71');
INSERT INTO `composition` VALUES ('0', '24', '72');
INSERT INTO `composition` VALUES ('0', '24', '73');
INSERT INTO `composition` VALUES ('0', '24', '74');
INSERT INTO `composition` VALUES ('0', '24', '75');
INSERT INTO `composition` VALUES ('0', '24', '76');
INSERT INTO `composition` VALUES ('0', '24', '77');
INSERT INTO `composition` VALUES ('0', '24', '78');
INSERT INTO `composition` VALUES ('0', '24', '80');
INSERT INTO `composition` VALUES ('1', '25', '1');
INSERT INTO `composition` VALUES ('1', '25', '3');
INSERT INTO `composition` VALUES ('1', '25', '5');
INSERT INTO `composition` VALUES ('1', '25', '6');
INSERT INTO `composition` VALUES ('1', '25', '7');
INSERT INTO `composition` VALUES ('1', '25', '8');
INSERT INTO `composition` VALUES ('1', '25', '9');
INSERT INTO `composition` VALUES ('1', '25', '12');
INSERT INTO `composition` VALUES ('1', '25', '13');
INSERT INTO `composition` VALUES ('1', '25', '14');
INSERT INTO `composition` VALUES ('1', '25', '15');
INSERT INTO `composition` VALUES ('1', '25', '16');
INSERT INTO `composition` VALUES ('1', '25', '17');
INSERT INTO `composition` VALUES ('1', '25', '18');
INSERT INTO `composition` VALUES ('1', '25', '20');
INSERT INTO `composition` VALUES ('1', '25', '62');
INSERT INTO `composition` VALUES ('1', '25', '67');
INSERT INTO `composition` VALUES ('1', '25', '68');
INSERT INTO `composition` VALUES ('1', '25', '69');
INSERT INTO `composition` VALUES ('1', '25', '70');
INSERT INTO `composition` VALUES ('1', '25', '77');
INSERT INTO `composition` VALUES ('1', '25', '78');
INSERT INTO `composition` VALUES ('1', '25', '79');
INSERT INTO `composition` VALUES ('1', '25', '81');
INSERT INTO `composition` VALUES ('1', '25', '86');
INSERT INTO `composition` VALUES ('1', '25', '100');
INSERT INTO `composition` VALUES ('1', '25', '114');
INSERT INTO `composition` VALUES ('1', '25', '127');
INSERT INTO `composition` VALUES ('1', '25', '192');
INSERT INTO `composition` VALUES ('1', '25', '221');
INSERT INTO `composition` VALUES ('1', '26', '2');
INSERT INTO `composition` VALUES ('1', '26', '4');
INSERT INTO `composition` VALUES ('0', '26', '10');
INSERT INTO `composition` VALUES ('1', '26', '11');
INSERT INTO `composition` VALUES ('1', '26', '19');
INSERT INTO `composition` VALUES ('1', '26', '25');
INSERT INTO `composition` VALUES ('1', '26', '28');
INSERT INTO `composition` VALUES ('1', '26', '29');
INSERT INTO `composition` VALUES ('1', '26', '32');
INSERT INTO `composition` VALUES ('1', '26', '35');
INSERT INTO `composition` VALUES ('1', '27', '10');
INSERT INTO `composition` VALUES ('1', '27', '22');
INSERT INTO `composition` VALUES ('1', '27', '27');
INSERT INTO `composition` VALUES ('1', '27', '30');
INSERT INTO `composition` VALUES ('1', '27', '31');
INSERT INTO `composition` VALUES ('1', '27', '33');
INSERT INTO `composition` VALUES ('1', '27', '34');
INSERT INTO `composition` VALUES ('0', '27', '37');
INSERT INTO `composition` VALUES ('1', '27', '38');
INSERT INTO `composition` VALUES ('1', '27', '40');
INSERT INTO `composition` VALUES ('1', '28', '21');
INSERT INTO `composition` VALUES ('1', '28', '23');
INSERT INTO `composition` VALUES ('1', '28', '24');
INSERT INTO `composition` VALUES ('1', '28', '26');
INSERT INTO `composition` VALUES ('1', '28', '36');
INSERT INTO `composition` VALUES ('1', '28', '37');
INSERT INTO `composition` VALUES ('1', '28', '39');
INSERT INTO `composition` VALUES ('1', '28', '45');
INSERT INTO `composition` VALUES ('1', '28', '47');
INSERT INTO `composition` VALUES ('1', '28', '58');
INSERT INTO `composition` VALUES ('1', '29', '42');
INSERT INTO `composition` VALUES ('1', '29', '43');
INSERT INTO `composition` VALUES ('1', '29', '44');
INSERT INTO `composition` VALUES ('1', '29', '46');
INSERT INTO `composition` VALUES ('1', '29', '49');
INSERT INTO `composition` VALUES ('1', '29', '51');
INSERT INTO `composition` VALUES ('1', '29', '52');
INSERT INTO `composition` VALUES ('1', '29', '55');
INSERT INTO `composition` VALUES ('1', '29', '59');
INSERT INTO `composition` VALUES ('1', '29', '60');
INSERT INTO `composition` VALUES ('1', '31', '81');
INSERT INTO `composition` VALUES ('1', '31', '86');
INSERT INTO `composition` VALUES ('1', '31', '89');
INSERT INTO `composition` VALUES ('1', '31', '92');
INSERT INTO `composition` VALUES ('1', '31', '95');
INSERT INTO `composition` VALUES ('1', '31', '97');
INSERT INTO `composition` VALUES ('1', '31', '100');
INSERT INTO `composition` VALUES ('1', '31', '105');
INSERT INTO `composition` VALUES ('1', '31', '108');
INSERT INTO `composition` VALUES ('1', '31', '173');
INSERT INTO `composition` VALUES ('1', '33', '62');
INSERT INTO `composition` VALUES ('1', '33', '67');
INSERT INTO `composition` VALUES ('1', '33', '68');
INSERT INTO `composition` VALUES ('1', '33', '69');
INSERT INTO `composition` VALUES ('1', '33', '70');
INSERT INTO `composition` VALUES ('1', '33', '77');
INSERT INTO `composition` VALUES ('1', '33', '78');
INSERT INTO `composition` VALUES ('1', '33', '79');
INSERT INTO `composition` VALUES ('0', '33', '229');
INSERT INTO `composition` VALUES ('1', '33', '231');
INSERT INTO `composition` VALUES ('0', '34', '21');
INSERT INTO `composition` VALUES ('0', '34', '44');
INSERT INTO `composition` VALUES ('0', '34', '45');
INSERT INTO `composition` VALUES ('0', '34', '46');
INSERT INTO `composition` VALUES ('0', '34', '50');
INSERT INTO `composition` VALUES ('0', '34', '55');
INSERT INTO `composition` VALUES ('0', '34', '58');
INSERT INTO `composition` VALUES ('0', '34', '73');
INSERT INTO `composition` VALUES ('0', '34', '76');
INSERT INTO `composition` VALUES ('0', '34', '229');
INSERT INTO `composition` VALUES ('1', '35', '187');
INSERT INTO `composition` VALUES ('1', '35', '188');
INSERT INTO `composition` VALUES ('1', '35', '189');
INSERT INTO `composition` VALUES ('1', '35', '190');
INSERT INTO `composition` VALUES ('1', '35', '194');
INSERT INTO `composition` VALUES ('1', '35', '205');
INSERT INTO `composition` VALUES ('0', '35', '209');
INSERT INTO `composition` VALUES ('0', '35', '211');
INSERT INTO `composition` VALUES ('0', '35', '217');
INSERT INTO `composition` VALUES ('1', '35', '228');
INSERT INTO `composition` VALUES ('0', '36', '180');
INSERT INTO `composition` VALUES ('0', '36', '184');
INSERT INTO `composition` VALUES ('0', '36', '203');
INSERT INTO `composition` VALUES ('0', '36', '206');
INSERT INTO `composition` VALUES ('0', '36', '208');
INSERT INTO `composition` VALUES ('0', '36', '209');
INSERT INTO `composition` VALUES ('0', '36', '211');
INSERT INTO `composition` VALUES ('0', '36', '217');
INSERT INTO `composition` VALUES ('0', '36', '220');
INSERT INTO `composition` VALUES ('0', '36', '223');
INSERT INTO `composition` VALUES ('1', '37', '167');
INSERT INTO `composition` VALUES ('1', '37', '169');
INSERT INTO `composition` VALUES ('1', '37', '203');
INSERT INTO `composition` VALUES ('1', '37', '206');
INSERT INTO `composition` VALUES ('1', '37', '208');
INSERT INTO `composition` VALUES ('1', '37', '209');
INSERT INTO `composition` VALUES ('0', '37', '211');
INSERT INTO `composition` VALUES ('0', '37', '217');
INSERT INTO `composition` VALUES ('1', '37', '220');
INSERT INTO `composition` VALUES ('1', '37', '223');
INSERT INTO `composition` VALUES ('0', '38', '161');
INSERT INTO `composition` VALUES ('0', '38', '170');
INSERT INTO `composition` VALUES ('0', '38', '175');
INSERT INTO `composition` VALUES ('0', '38', '178');
INSERT INTO `composition` VALUES ('0', '38', '179');
INSERT INTO `composition` VALUES ('0', '38', '180');
INSERT INTO `composition` VALUES ('0', '38', '184');
INSERT INTO `composition` VALUES ('0', '38', '186');
INSERT INTO `composition` VALUES ('0', '38', '211');
INSERT INTO `composition` VALUES ('0', '38', '217');

-- ----------------------------
-- Table structure for constant
-- ----------------------------
DROP TABLE IF EXISTS `constant`;
CREATE TABLE `constant` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `key` varchar(100) NOT NULL,
  `value` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of constant
-- ----------------------------
INSERT INTO `constant` VALUES ('1', 'threshold', '30');
INSERT INTO `constant` VALUES ('2', 'timehandle', '11:20');
INSERT INTO `constant` VALUES ('3', 'identify_time', 'more');
INSERT INTO `constant` VALUES ('4', 'identify_frequency_marks', 'later');

-- ----------------------------
-- Table structure for contribute_img
-- ----------------------------
DROP TABLE IF EXISTS `contribute_img`;
CREATE TABLE `contribute_img` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `upload_img_time` timestamp NULL DEFAULT NULL,
  `storage_path` varchar(200) NOT NULL,
  `upload_img_review_status` varchar(2) NOT NULL DEFAULT '0',
  `user_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of contribute_img
-- ----------------------------
INSERT INTO `contribute_img` VALUES ('1', '2017-09-03 22:19:44', 'http://122.112.232.97/ImageClassify/contribute/1574c25f2104ff726ab8672e9549d38b.zip', '0', '7');
INSERT INTO `contribute_img` VALUES ('2', '2017-09-05 23:26:12', 'http://122.112.232.97/ImageClassify/contribute/63632054209c25131935ba68bc0f427c.zip', '1', '8');
INSERT INTO `contribute_img` VALUES ('3', '2017-09-05 23:58:56', 'http://122.112.232.97/ImageClassify/contribute/c06b556038bc580ad64f19b951268c26.zip', '-1', '8');

-- ----------------------------
-- Table structure for export_record
-- ----------------------------
DROP TABLE IF EXISTS `export_record`;
CREATE TABLE `export_record` (
  `export_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '导出id',
  `export_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '导出时间',
  PRIMARY KEY (`export_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of export_record
-- ----------------------------
INSERT INTO `export_record` VALUES ('1', '2017-09-02 21:41:21');
INSERT INTO `export_record` VALUES ('2', '2017-09-02 21:41:05');
INSERT INTO `export_record` VALUES ('3', '2017-09-02 21:45:59');
INSERT INTO `export_record` VALUES ('4', '2017-09-05 23:21:57');

-- ----------------------------
-- Table structure for export_tag
-- ----------------------------
DROP TABLE IF EXISTS `export_tag`;
CREATE TABLE `export_tag` (
  `export_id` int(11) NOT NULL,
  `img_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`export_id`,`img_id`),
  KEY `img_id` (`img_id`),
  CONSTRAINT `export_tag_ibfk_1` FOREIGN KEY (`export_id`) REFERENCES `export_record` (`export_id`),
  CONSTRAINT `export_tag_ibfk_2` FOREIGN KEY (`img_id`) REFERENCES `image` (`img_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of export_tag
-- ----------------------------
INSERT INTO `export_tag` VALUES ('1', '1');
INSERT INTO `export_tag` VALUES ('2', '1');
INSERT INTO `export_tag` VALUES ('1', '2');
INSERT INTO `export_tag` VALUES ('2', '2');
INSERT INTO `export_tag` VALUES ('1', '3');
INSERT INTO `export_tag` VALUES ('2', '3');
INSERT INTO `export_tag` VALUES ('1', '4');
INSERT INTO `export_tag` VALUES ('3', '5');
INSERT INTO `export_tag` VALUES ('3', '6');
INSERT INTO `export_tag` VALUES ('3', '7');
INSERT INTO `export_tag` VALUES ('3', '8');
INSERT INTO `export_tag` VALUES ('4', '47');
INSERT INTO `export_tag` VALUES ('4', '101');
INSERT INTO `export_tag` VALUES ('4', '128');
INSERT INTO `export_tag` VALUES ('4', '135');
INSERT INTO `export_tag` VALUES ('4', '153');
INSERT INTO `export_tag` VALUES ('4', '154');
INSERT INTO `export_tag` VALUES ('4', '174');

-- ----------------------------
-- Table structure for image
-- ----------------------------
DROP TABLE IF EXISTS `image`;
CREATE TABLE `image` (
  `img_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `img_name` varchar(40) NOT NULL,
  `img_path` varchar(200) NOT NULL,
  `img_finish_time` timestamp NULL DEFAULT NULL,
  `img_label_name` varchar(200) DEFAULT NULL,
  `img_is_finish` varchar(2) DEFAULT '0',
  `zip_id` int(10) NOT NULL,
  `img_machine_tag_label` varchar(120) DEFAULT NULL,
  `is_clustered` int(10) NOT NULL,
  `is_export` int(10) NOT NULL,
  PRIMARY KEY (`img_id`)
) ENGINE=InnoDB AUTO_INCREMENT=267 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of image
-- ----------------------------
INSERT INTO `image` VALUES ('1', '944c1a34f282047c9d193bd69db88c3f.jpg', 'http://122.112.232.97/ImageClassify/zip/7741815183c0c41a908b06b9130f9a58/', '2017-09-02 21:36:02', '菜品;餐厅;聚会;', '1', '1', '53|菜品;48|餐厅;33|聚会;26|盘子;17|碗;15|合影;14|杯子;12|酒店;', '1', '0');
INSERT INTO `image` VALUES ('2', '5d5c135768ed948654d99161b5c5565f.jpg', 'http://122.112.232.97/ImageClassify/zip/7741815183c0c41a908b06b9130f9a58/', '2017-09-02 21:37:32', '客厅;沙发;', '1', '1', '35|客厅;31|桌子;29|灯;26|椅子;23|沙发;', '1', '0');
INSERT INTO `image` VALUES ('3', '1bbfedf4c64d8eddf0abe6058a51cd5a.jpg', 'http://122.112.232.97/ImageClassify/zip/7741815183c0c41a908b06b9130f9a58/', '2017-09-02 21:37:34', '客厅;酒店;', '1', '1', '33|灯;25|椅子;17|酒店;16|客厅;13|窗户;', '1', '0');
INSERT INTO `image` VALUES ('4', 'bad123149cc51c0c9fe0f853e5cd5514.jpg', 'http://122.112.232.97/ImageClassify/zip/7741815183c0c41a908b06b9130f9a58/', '2017-09-02 21:37:37', '客厅;桌子;', '1', '1', '50|桌子;48|椅子;21|家具;17|灯;15|客厅;14|餐厅;', '1', '0');
INSERT INTO `image` VALUES ('5', '0e58a8547b61db1ea67fd486bab939ce.jpg', 'http://122.112.232.97/ImageClassify/zip/7741815183c0c41a908b06b9130f9a58/', '2017-09-02 21:37:39', '客厅;餐厅;', '1', '1', '56|菜品;47|餐厅;44|盘子;23|碗;13|合影;', '1', '0');
INSERT INTO `image` VALUES ('6', 'cc9083e22f54e098a8bc70f14b1d9fe3.jpg', 'http://122.112.232.97/ImageClassify/zip/7741815183c0c41a908b06b9130f9a58/', '2017-09-02 21:37:41', '客厅;', '1', '1', '35|椅子;18|灯;12|窗户;', '1', '0');
INSERT INTO `image` VALUES ('7', '342902392704cb596a5300fbfccb60a5.jpg', 'http://122.112.232.97/ImageClassify/zip/7741815183c0c41a908b06b9130f9a58/', '2017-09-02 21:37:43', '客厅;灯;', '1', '1', '30|灯;22|店铺;12|家具;', '1', '1');
INSERT INTO `image` VALUES ('8', '911e53d44544a73ec48b5947dc8fc9b8.jpg', 'http://122.112.232.97/ImageClassify/zip/7741815183c0c41a908b06b9130f9a58/', '2017-09-02 21:37:45', '客厅;店铺', '1', '1', '17|灯;14|店铺;', '1', '1');
INSERT INTO `image` VALUES ('9', '1565de8335823a8c3f02125cf11303cf.jpg', 'http://122.112.232.97/ImageClassify/zip/7741815183c0c41a908b06b9130f9a58/', null, null, '0', '1', '47|桌子;32|椅子;27|客厅;26|家具;16|窗户;', '1', '0');
INSERT INTO `image` VALUES ('10', 'af2a7d35fdb025803e25b93bb77a0994.jpg', 'http://122.112.232.97/ImageClassify/zip/7741815183c0c41a908b06b9130f9a58/', null, null, '0', '1', '29|门;28|店铺;20|街道;19|房屋;15|建筑;', '1', '0');
INSERT INTO `image` VALUES ('11', 'd683a5f79d297921a0fac21f155e04fe.jpg', 'http://122.112.232.97/ImageClassify/zip/7741815183c0c41a908b06b9130f9a58/', null, null, '0', '1', '38|酒店;30|灯;23|椅子;17|餐厅;14|聚会;', '1', '0');
INSERT INTO `image` VALUES ('12', '636990a77d37872480af6637f046a093.jpg', 'http://122.112.232.97/ImageClassify/zip/7741815183c0c41a908b06b9130f9a58/', null, null, '0', '1', '37|灯;30|桌子;23|餐厅;22|酒店;16|聚会;15|会场;', '1', '0');
INSERT INTO `image` VALUES ('13', '28cb539238ed18eee8d056a96e208b9c.jpg', 'http://122.112.232.97/ImageClassify/zip/7741815183c0c41a908b06b9130f9a58/', null, null, '0', '1', '68|菜品;56|餐厅;39|聚会;31|合影;19|酒店;13|桌子;', '1', '0');
INSERT INTO `image` VALUES ('14', 'aadab8ff6a31cfb223f99486e5f19df2.jpg', 'http://122.112.232.97/ImageClassify/zip/7741815183c0c41a908b06b9130f9a58/', null, null, '0', '1', '44|餐厅;42|菜品;28|合影;20|盘子;16|聚会;', '1', '0');
INSERT INTO `image` VALUES ('15', '8e75974618b630eb2bda630157d2d027.jpg', 'http://122.112.232.97/ImageClassify/zip/7741815183c0c41a908b06b9130f9a58/', null, null, '0', '1', '46|桌子;32|椅子;16|餐厅;', '1', '0');
INSERT INTO `image` VALUES ('16', '5b48d3c5126d055d2338e3876623677f.jpg', 'http://122.112.232.97/ImageClassify/zip/7741815183c0c41a908b06b9130f9a58/', null, null, '0', '1', '35|桌子;23|灯;22|餐厅;20|椅子;13|客厅;', '1', '0');
INSERT INTO `image` VALUES ('17', '4be0e5cae72ad9ecf9236627244f736f.jpg', 'http://122.112.232.97/ImageClassify/zip/7741815183c0c41a908b06b9130f9a58/', null, null, '0', '1', '46|桌子;32|椅子;', '1', '0');
INSERT INTO `image` VALUES ('18', '876b67d1301da2e7b4c9d82834415281.jpg', 'http://122.112.232.97/ImageClassify/zip/7741815183c0c41a908b06b9130f9a58/', null, null, '0', '1', '64|菜品;53|餐厅;36|盘子;19|碗;18|合影;', '1', '0');
INSERT INTO `image` VALUES ('19', '0fe1e44198aeb4e2de9114d285cd3530.jpg', 'http://122.112.232.97/ImageClassify/zip/7741815183c0c41a908b06b9130f9a58/', null, null, '0', '1', '41|客厅;25|桌子;22|椅子;21|灯;19|沙发;', '1', '0');
INSERT INTO `image` VALUES ('20', '8e13d9b9ef6343f680f61c6f7b72e81c.jpg', 'http://122.112.232.97/ImageClassify/zip/7741815183c0c41a908b06b9130f9a58/', null, null, '0', '1', '43|桌子;34|椅子;31|灯;23|餐厅;22|客厅;20|家具;15|沙发;', '1', '0');
INSERT INTO `image` VALUES ('21', 'b7a84356e848c163e2563ff8bbdacc2c.jpg', 'http://122.112.232.97/ImageClassify/zip/8497a1fd22aa855f8af0125ebce3aec2/', null, null, '0', '2', '24|天空;15|建筑;12|路;', '1', '0');
INSERT INTO `image` VALUES ('22', '35993ec0097d4918f770df4ac5b80a7e.jpg', 'http://122.112.232.97/ImageClassify/zip/8497a1fd22aa855f8af0125ebce3aec2/', null, null, '0', '2', '50|天空;49|汽车;41|房屋;34|路;20|建筑;17|广场;15|街道;12|高楼;', '1', '0');
INSERT INTO `image` VALUES ('23', '62ff414703db90b31da84303e4c573d2.jpg', 'http://122.112.232.97/ImageClassify/zip/8497a1fd22aa855f8af0125ebce3aec2/', null, null, '0', '2', '46|汽车;44|天空;34|路;31|房屋;20|树木;19|建筑;', '1', '0');
INSERT INTO `image` VALUES ('24', 'd18dbf147a878989c5d3d3b50681636c.jpg', 'http://122.112.232.97/ImageClassify/zip/8497a1fd22aa855f8af0125ebce3aec2/', null, null, '0', '2', '24|天空;15|建筑;', '1', '0');
INSERT INTO `image` VALUES ('25', '95aebf06c8b38e2ab6aafae5e4067941.jpg', 'http://122.112.232.97/ImageClassify/zip/8497a1fd22aa855f8af0125ebce3aec2/', null, null, '0', '2', '27|广场;23|建筑;22|天空;17|人群;16|房屋;', '1', '0');
INSERT INTO `image` VALUES ('26', '0f80a27398e826b5834f6b4fbe404178.jpg', 'http://122.112.232.97/ImageClassify/zip/8497a1fd22aa855f8af0125ebce3aec2/', null, null, '0', '2', '56|天空;28|汽车;24|路;21|云;12|高楼;', '1', '0');
INSERT INTO `image` VALUES ('27', 'ad154ae581c0b5af3adce71345eec3eb.jpg', 'http://122.112.232.97/ImageClassify/zip/8497a1fd22aa855f8af0125ebce3aec2/', null, null, '0', '2', '53|天空;29|广场;23|汽车;18|房屋;12|路;', '1', '0');
INSERT INTO `image` VALUES ('28', '072a165560ee317eeb2056cc81bf53ee.jpg', 'http://122.112.232.97/ImageClassify/zip/8497a1fd22aa855f8af0125ebce3aec2/', null, null, '0', '2', '16|汽车;14|灯;', '1', '0');
INSERT INTO `image` VALUES ('29', '8c4d08be895dbea7e121735e3ec27875.jpg', 'http://122.112.232.97/ImageClassify/zip/8497a1fd22aa855f8af0125ebce3aec2/', null, null, '0', '2', '52|天空;48|汽车;36|路;30|山;22|树木;18|建筑;', '1', '0');
INSERT INTO `image` VALUES ('30', '182d570fcf5059511ec5389d18d3013d.jpg', 'http://122.112.232.97/ImageClassify/zip/8497a1fd22aa855f8af0125ebce3aec2/', null, null, '0', '2', '30|人群;16|房屋;', '1', '0');
INSERT INTO `image` VALUES ('31', '203957957aac4c2663fa6cbb07213008.jpg', 'http://122.112.232.97/ImageClassify/zip/8497a1fd22aa855f8af0125ebce3aec2/', null, null, '0', '2', '41|天空;32|房屋;30|汽车;27|路;23|广场;', '1', '0');
INSERT INTO `image` VALUES ('32', '8b75d4820db98d9c60aadd757f576419.jpg', 'http://122.112.232.97/ImageClassify/zip/8497a1fd22aa855f8af0125ebce3aec2/', null, null, '0', '2', '30|灯;29|舞台;26|夜晚;23|演出;', '1', '0');
INSERT INTO `image` VALUES ('33', 'a9c19f13016479a54311a8ccd2359e31.jpg', 'http://122.112.232.97/ImageClassify/zip/8497a1fd22aa855f8af0125ebce3aec2/', null, null, '0', '2', '36|人群;20|大厅;12|合影;', '1', '0');
INSERT INTO `image` VALUES ('34', 'f3473f48559a5e06abb5ee0e13448ea2.jpg', 'http://122.112.232.97/ImageClassify/zip/8497a1fd22aa855f8af0125ebce3aec2/', null, null, '0', '2', '38|广场;19|建筑;15|房屋;', '1', '0');
INSERT INTO `image` VALUES ('35', '8c25a2399457b9543e9c3b07ebdb6547.jpg', 'http://122.112.232.97/ImageClassify/zip/8497a1fd22aa855f8af0125ebce3aec2/', null, null, '0', '2', '14|房屋;', '1', '0');
INSERT INTO `image` VALUES ('36', '5bb22b2e29bbf54af8fd4414e6b510b2.jpg', 'http://122.112.232.97/ImageClassify/zip/8497a1fd22aa855f8af0125ebce3aec2/', null, null, '0', '2', '33|汽车;32|路;15|天空;13|建筑;', '1', '0');
INSERT INTO `image` VALUES ('37', '3e4f0d85cdc17e4fe60817dedbedd8a7.jpg', 'http://122.112.232.97/ImageClassify/zip/8497a1fd22aa855f8af0125ebce3aec2/', null, null, '0', '2', '35|天空;30|房屋;29|建筑;17|广场;16|汽车;', '1', '0');
INSERT INTO `image` VALUES ('38', '606f73fc8c8dae237d1aaf8e9320108a.jpg', 'http://122.112.232.97/ImageClassify/zip/8497a1fd22aa855f8af0125ebce3aec2/', null, null, '0', '2', '15|车站;12|灯;', '1', '0');
INSERT INTO `image` VALUES ('39', '0fd5f5b866bf68f4b752725add1061d4.jpg', 'http://122.112.232.97/ImageClassify/zip/8497a1fd22aa855f8af0125ebce3aec2/', null, null, '0', '2', '24|人群;14|大厅;', '1', '0');
INSERT INTO `image` VALUES ('40', '9c115ea082d6851d39b4e2ed41b01ff9.jpg', 'http://122.112.232.97/ImageClassify/zip/8497a1fd22aa855f8af0125ebce3aec2/', null, null, '0', '2', '54|天空;32|建筑;26|广场;24|房屋;23|汽车;', '1', '0');
INSERT INTO `image` VALUES ('41', 'f704c251547a9a53ad02e78ad3b0552b.jpg', 'http://122.112.232.97/ImageClassify/zip/7340889f6f0d132e76b905013ac4e2d4/', null, null, '0', '3', '62|天空;56|树木;31|云;16|路;13|广场;', '1', '0');
INSERT INTO `image` VALUES ('42', '83f9821bf385a4c435fee9e760a0581c.jpg', 'http://122.112.232.97/ImageClassify/zip/7340889f6f0d132e76b905013ac4e2d4/', null, null, '0', '3', '45|树木;28|草地;22|花丛;17|天空;13|花;', '1', '0');
INSERT INTO `image` VALUES ('43', '94832185041d0c7c6a613105512d5dcb.jpg', 'http://122.112.232.97/ImageClassify/zip/7340889f6f0d132e76b905013ac4e2d4/', null, null, '0', '3', '53|天空;36|广场;31|树木;28|雕像;', '1', '0');
INSERT INTO `image` VALUES ('44', '120daba3f4368e46d0eda91a00288068.jpg', 'http://122.112.232.97/ImageClassify/zip/7340889f6f0d132e76b905013ac4e2d4/', null, null, '0', '3', '37|天空;34|房屋;17|路;14|街道;13|广场;12|欧式建筑;', '1', '0');
INSERT INTO `image` VALUES ('45', 'cb3b45575f66212fb989d58b8cbfa78b.jpg', 'http://122.112.232.97/ImageClassify/zip/7340889f6f0d132e76b905013ac4e2d4/', null, null, '0', '3', '38|树木;15|楼阁;13|房屋;12|欧式建筑;', '1', '0');
INSERT INTO `image` VALUES ('46', '16af43df4015bbf0baf4e6ffa9642335.jpg', 'http://122.112.232.97/ImageClassify/zip/7340889f6f0d132e76b905013ac4e2d4/', null, null, '0', '3', '45|夜晚;38|灯;19|天空;15|高楼;12|建筑;', '1', '0');
INSERT INTO `image` VALUES ('47', '18eb257c94d795f117cd1c3157a60abf.jpg', 'http://122.112.232.97/ImageClassify/zip/7340889f6f0d132e76b905013ac4e2d4/', '2017-09-03 09:57:38', '树木;天空;', '1', '3', '47|树木;28|天空;20|房屋;', '1', '1');
INSERT INTO `image` VALUES ('48', 'ca58909d2bbdda5bf729ac6584e23abd.jpg', 'http://122.112.232.97/ImageClassify/zip/7340889f6f0d132e76b905013ac4e2d4/', null, null, '0', '3', '48|天空;27|广场;21|树木;18|欧式建筑;12|建筑;', '1', '0');
INSERT INTO `image` VALUES ('49', '4c1e5cd40a710006eefa7594c8e474a1.jpg', 'http://122.112.232.97/ImageClassify/zip/7340889f6f0d132e76b905013ac4e2d4/', null, null, '0', '3', '56|天空;29|云;24|路;21|树木;17|房屋;16|汽车;14|广场;13|建筑;', '1', '0');
INSERT INTO `image` VALUES ('50', '2cabbc23ae2122a82b2ea00d19f570e9.jpg', 'http://122.112.232.97/ImageClassify/zip/7340889f6f0d132e76b905013ac4e2d4/', null, null, '0', '3', '41|树木;38|草地;20|房屋;', '1', '0');
INSERT INTO `image` VALUES ('51', 'b315e6ec913fa479e51892ae34335627.jpg', 'http://122.112.232.97/ImageClassify/zip/7340889f6f0d132e76b905013ac4e2d4/', null, null, '0', '3', '52|天空;42|广场;36|树木;16|路;13|建筑;', '1', '0');
INSERT INTO `image` VALUES ('52', '1fd906a51f150ec475f614a2116d66f7.jpg', 'http://122.112.232.97/ImageClassify/zip/7340889f6f0d132e76b905013ac4e2d4/', null, null, '0', '3', '61|截图;', '1', '0');
INSERT INTO `image` VALUES ('53', '4099fc24a4f71683dd1725efdbdeb848.jpg', 'http://122.112.232.97/ImageClassify/zip/7340889f6f0d132e76b905013ac4e2d4/', null, null, '0', '3', '53|天空;35|树木;20|云;17|广场;16|房屋;14|路;', '1', '0');
INSERT INTO `image` VALUES ('54', 'f19221c06c9e616b24a9529a92dc8198.jpg', 'http://122.112.232.97/ImageClassify/zip/7340889f6f0d132e76b905013ac4e2d4/', null, null, '0', '3', '56|树木;40|天空;34|房屋;30|广场;24|路;15|建筑;', '1', '0');
INSERT INTO `image` VALUES ('55', '16e814e7a940ef39ccf512f05fa1d02f.jpg', 'http://122.112.232.97/ImageClassify/zip/7340889f6f0d132e76b905013ac4e2d4/', null, null, '0', '3', '38|树木;33|天空;22|云;', '1', '0');
INSERT INTO `image` VALUES ('56', 'b0a76a85ba0f624d41fcd588c2787312.jpg', 'http://122.112.232.97/ImageClassify/zip/7340889f6f0d132e76b905013ac4e2d4/', null, null, '0', '3', '45|树木;36|门;32|楼阁;30|天空;28|建筑;15|广场;', '1', '0');
INSERT INTO `image` VALUES ('57', '63bddaf916d940ce3eb6d18cf82b72cf.jpg', 'http://122.112.232.97/ImageClassify/zip/7340889f6f0d132e76b905013ac4e2d4/', null, null, '0', '3', '57|天空;38|树木;28|云;17|路;16|建筑;13|广场;', '1', '0');
INSERT INTO `image` VALUES ('58', '3d154698e16e25385ba786de4e3a0427.jpg', 'http://122.112.232.97/ImageClassify/zip/7340889f6f0d132e76b905013ac4e2d4/', null, null, '0', '3', '53|树木;49|天空;30|房屋;29|路;26|广场;16|草地;13|建筑;', '1', '0');
INSERT INTO `image` VALUES ('59', 'ad67fd53fa37e43b60a01dbb309a8ebb.jpg', 'http://122.112.232.97/ImageClassify/zip/7340889f6f0d132e76b905013ac4e2d4/', null, null, '0', '3', '57|天空;37|树木;34|汽车;32|路;26|高楼;21|房屋;17|建筑;', '1', '0');
INSERT INTO `image` VALUES ('60', 'c93dd1994a7afaa490ccc6dd98c132f3.jpg', 'http://122.112.232.97/ImageClassify/zip/7340889f6f0d132e76b905013ac4e2d4/', null, null, '0', '3', '69|树木;44|天空;37|广场;21|路;12|建筑;', '1', '0');
INSERT INTO `image` VALUES ('61', '4594369389615bc18de7013b59e550b9.jpg', 'http://122.112.232.97/ImageClassify/zip/6898834022e99a8ff43c73b0eec8f18f/', null, null, '0', '4', '72|天空;64|大海;32|云;24|沙滩;21|海岸;16|岩石;', '1', '0');
INSERT INTO `image` VALUES ('62', '55c37a982aa0acb962123bca849bb048.jpg', 'http://122.112.232.97/ImageClassify/zip/6898834022e99a8ff43c73b0eec8f18f/', null, null, '0', '4', '66|天空;53|沙滩;52|大海;30|云;13|海岸;', '1', '0');
INSERT INTO `image` VALUES ('63', '500a16d107cb2a5ebbe6b3732dac1b6d.jpg', 'http://122.112.232.97/ImageClassify/zip/6898834022e99a8ff43c73b0eec8f18f/', null, null, '0', '4', '67|天空;45|大海;38|沙滩;33|人群;15|云;12|水;', '1', '0');
INSERT INTO `image` VALUES ('64', '2a6ff8037461d11185bde9868d103b76.jpg', 'http://122.112.232.97/ImageClassify/zip/6898834022e99a8ff43c73b0eec8f18f/', null, null, '0', '4', '61|大海;46|天空;39|合影;27|沙滩;22|海岸;15|女孩;', '1', '0');
INSERT INTO `image` VALUES ('65', '1e2c4bcb0a9fdcbb19a474beca46abe7.jpg', 'http://122.112.232.97/ImageClassify/zip/6898834022e99a8ff43c73b0eec8f18f/', null, null, '0', '4', '64|天空;49|大海;43|沙滩;25|合影;16|海岸;15|人群;14|人;12|云;', '1', '0');
INSERT INTO `image` VALUES ('66', '3a7fc0f1a29598900133df59479ea36d.jpg', 'http://122.112.232.97/ImageClassify/zip/6898834022e99a8ff43c73b0eec8f18f/', null, null, '0', '4', '67|天空;48|大海;38|云;17|树木;', '1', '0');
INSERT INTO `image` VALUES ('67', '9844eba2ded5273ddc427f6597ad7bb6.jpg', 'http://122.112.232.97/ImageClassify/zip/6898834022e99a8ff43c73b0eec8f18f/', null, null, '0', '4', '52|天空;43|沙滩;33|人群;25|大海;16|云;', '1', '0');
INSERT INTO `image` VALUES ('68', '3fe27ec79f07a2e1baa5866f327c8f51.jpg', 'http://122.112.232.97/ImageClassify/zip/6898834022e99a8ff43c73b0eec8f18f/', null, null, '0', '4', '72|天空;54|大海;33|云;28|沙滩;15|海岸;', '1', '0');
INSERT INTO `image` VALUES ('69', 'd6092becdfa0a9a206ad25bdcf60a852.jpg', 'http://122.112.232.97/ImageClassify/zip/6898834022e99a8ff43c73b0eec8f18f/', null, null, '0', '4', '57|天空;51|大海;29|人群;26|沙滩;18|海岸;13|人;12|合影;', '1', '0');
INSERT INTO `image` VALUES ('70', '842f1dfb783acd376aff2a2bc1953e6e.jpg', 'http://122.112.232.97/ImageClassify/zip/6898834022e99a8ff43c73b0eec8f18f/', null, null, '0', '4', '61|天空;32|大海;29|沙滩;25|云;12|海岸;', '1', '0');
INSERT INTO `image` VALUES ('71', '454e403a9a21a9fde8974c15c0fff20c.jpg', 'http://122.112.232.97/ImageClassify/zip/6898834022e99a8ff43c73b0eec8f18f/', null, null, '0', '4', '54|大海;49|天空;39|沙滩;15|云;14|女孩;13|海岸;', '1', '0');
INSERT INTO `image` VALUES ('72', '67c32a99dbb9774943a3514d657d7b16.jpg', 'http://122.112.232.97/ImageClassify/zip/6898834022e99a8ff43c73b0eec8f18f/', null, null, '0', '4', '60|天空;50|大海;48|沙滩;26|海岸;15|人;13|女孩;', '1', '0');
INSERT INTO `image` VALUES ('73', '660b154c2e6b61ed39b46cfbe3a86d87.jpg', 'http://122.112.232.97/ImageClassify/zip/6898834022e99a8ff43c73b0eec8f18f/', null, null, '0', '4', '69|天空;56|大海;35|沙滩;34|云;27|合影;16|女孩;14|海岸;', '1', '0');
INSERT INTO `image` VALUES ('74', '822dfdc80099c8aa5701c07373378f61.jpg', 'http://122.112.232.97/ImageClassify/zip/6898834022e99a8ff43c73b0eec8f18f/', null, null, '0', '4', '67|天空;58|大海;22|云;19|合影;17|海岸;12|沙滩;', '1', '0');
INSERT INTO `image` VALUES ('75', '029cfe883326d10160e7caa3efdd46c5.jpg', 'http://122.112.232.97/ImageClassify/zip/6898834022e99a8ff43c73b0eec8f18f/', null, null, '0', '4', '49|大海;44|天空;35|沙滩;23|女孩;22|合影;13|海岸;', '1', '0');
INSERT INTO `image` VALUES ('76', '45dede296edadea00cf5d6d05b1311f5.jpg', 'http://122.112.232.97/ImageClassify/zip/6898834022e99a8ff43c73b0eec8f18f/', null, null, '0', '4', '62|天空;54|大海;29|云;26|沙滩;24|女孩;22|合影;16|海岸;', '1', '0');
INSERT INTO `image` VALUES ('77', 'ff1ef6b65c286df61ad6f8f6209a0df4.jpg', 'http://122.112.232.97/ImageClassify/zip/6898834022e99a8ff43c73b0eec8f18f/', null, null, '0', '4', '69|天空;40|大海;30|云;27|沙滩;14|海岸;', '1', '0');
INSERT INTO `image` VALUES ('78', '60d248b536f7a3cc090d63c24886e5a1.jpg', 'http://122.112.232.97/ImageClassify/zip/6898834022e99a8ff43c73b0eec8f18f/', null, null, '0', '4', '51|大海;46|天空;26|沙滩;21|海岸;17|合影;', '1', '0');
INSERT INTO `image` VALUES ('79', 'eeaef97a64b5816cc088825f1f281a83.jpg', 'http://122.112.232.97/ImageClassify/zip/6898834022e99a8ff43c73b0eec8f18f/', null, null, '0', '4', '71|天空;53|大海;36|沙滩;29|云;28|海岸;', '1', '0');
INSERT INTO `image` VALUES ('80', '14bba7271ceb2fb4b300ee711cc3e147.jpg', 'http://122.112.232.97/ImageClassify/zip/6898834022e99a8ff43c73b0eec8f18f/', null, null, '0', '4', '62|天空;27|沙滩;21|海岸;17|云;13|岩石;12|女孩;', '1', '0');
INSERT INTO `image` VALUES ('81', '882529c156b809884e241916f632ecf1.jpg', 'http://122.112.232.97/ImageClassify/zip/efd4374805e536aa1a69e764735282d7/', null, null, '0', '5', '55|天空;48|大海;43|沙滩;15|云;14|海岸;', '1', '0');
INSERT INTO `image` VALUES ('82', 'd52b3392aec6d6e63fd4192a03a10c04.jpg', 'http://122.112.232.97/ImageClassify/zip/efd4374805e536aa1a69e764735282d7/', null, null, '0', '5', '67|天空;42|云;34|大海;21|合影;', '1', '0');
INSERT INTO `image` VALUES ('83', '882f3536ca037a6f35cc139100bba044.jpg', 'http://122.112.232.97/ImageClassify/zip/efd4374805e536aa1a69e764735282d7/', null, null, '0', '5', '72|草地;43|云;37|树木;', '1', '0');
INSERT INTO `image` VALUES ('84', 'cfb52ae148cdbc04a57e05d7bcf4d360.jpg', 'http://122.112.232.97/ImageClassify/zip/efd4374805e536aa1a69e764735282d7/', null, null, '0', '5', '66|天空;25|船;24|云;22|水;21|湖;15|都市远景;13|高楼;', '1', '0');
INSERT INTO `image` VALUES ('85', 'c1df5bcae83794610b2eae802f7afa1b.jpg', 'http://122.112.232.97/ImageClassify/zip/efd4374805e536aa1a69e764735282d7/', null, null, '0', '5', '46|树木;41|天空;39|房屋;14|草地;13|水;', '1', '0');
INSERT INTO `image` VALUES ('86', '5c31dcc138b7032c529c4e5520505e76.jpg', 'http://122.112.232.97/ImageClassify/zip/efd4374805e536aa1a69e764735282d7/', null, null, '0', '5', '66|沙滩;53|天空;42|大海;24|人群;21|女孩;', '1', '0');
INSERT INTO `image` VALUES ('87', '6f50c162644e84de17bd9b9fef62bdab.jpg', 'http://122.112.232.97/ImageClassify/zip/efd4374805e536aa1a69e764735282d7/', null, null, '0', '5', '63|天空;28|大海;23|树木;22|湖;17|云;16|水;', '1', '0');
INSERT INTO `image` VALUES ('88', '32d0f5a1ac44133a2da189cab617427f.jpg', 'http://122.112.232.97/ImageClassify/zip/efd4374805e536aa1a69e764735282d7/', null, null, '0', '5', '62|天空;40|树木;19|水;17|湖;', '1', '0');
INSERT INTO `image` VALUES ('89', 'eb7a86556d7d9cb54536d04f53febaa9.jpg', 'http://122.112.232.97/ImageClassify/zip/efd4374805e536aa1a69e764735282d7/', null, null, '0', '5', '44|广场;41|树木;40|天空;23|人群;13|路;', '1', '0');
INSERT INTO `image` VALUES ('90', '1e1e10be121577836d7006d2f5e911fb.jpg', 'http://122.112.232.97/ImageClassify/zip/efd4374805e536aa1a69e764735282d7/', null, null, '0', '5', '73|树木;52|草地;46|天空;', '1', '0');
INSERT INTO `image` VALUES ('91', '5da5e7726cc4b21f5961c10087ea2290.jpg', 'http://122.112.232.97/ImageClassify/zip/efd4374805e536aa1a69e764735282d7/', null, null, '0', '5', '51|天空;30|女孩;21|湖;16|水;13|男孩;', '1', '0');
INSERT INTO `image` VALUES ('92', 'd7e4ea22472eb8798734d9faea442fc3.jpg', 'http://122.112.232.97/ImageClassify/zip/efd4374805e536aa1a69e764735282d7/', null, null, '0', '5', '62|天空;42|云;27|大海;17|女孩;14|山;', '1', '0');
INSERT INTO `image` VALUES ('93', '1c5ba38c58dffa867cf44b16cb97d6d3.jpg', 'http://122.112.232.97/ImageClassify/zip/efd4374805e536aa1a69e764735282d7/', null, null, '0', '5', '72|天空;53|树木;45|草地;44|云;', '1', '0');
INSERT INTO `image` VALUES ('94', 'f863734e6e1e36f4226001d0f3cb9eca.jpg', 'http://122.112.232.97/ImageClassify/zip/efd4374805e536aa1a69e764735282d7/', null, null, '0', '5', '70|草地;58|树木;54|天空;25|云;', '1', '0');
INSERT INTO `image` VALUES ('95', '97914c7aea574246f22a15e2aab79745.jpg', 'http://122.112.232.97/ImageClassify/zip/efd4374805e536aa1a69e764735282d7/', null, null, '0', '5', '51|天空;24|高楼;18|树木;14|云;12|建筑;', '1', '0');
INSERT INTO `image` VALUES ('96', 'ce71e6eb8ed7c426d7fec56b95395c89.jpg', 'http://122.112.232.97/ImageClassify/zip/efd4374805e536aa1a69e764735282d7/', null, null, '0', '5', '74|树木;41|合影;20|公园;12|路;', '1', '0');
INSERT INTO `image` VALUES ('97', 'cb8fc04a842f7d7f63fbf0845bc6cc3c.jpg', 'http://122.112.232.97/ImageClassify/zip/efd4374805e536aa1a69e764735282d7/', null, null, '0', '5', '71|天空;45|云;34|大海;', '1', '0');
INSERT INTO `image` VALUES ('98', '003584c19fa9c3c47d709ca2ec9ec8ef.jpg', 'http://122.112.232.97/ImageClassify/zip/efd4374805e536aa1a69e764735282d7/', null, null, '0', '5', '64|大海;60|天空;27|岩石;20|云;19|海岸;12|人群;', '1', '0');
INSERT INTO `image` VALUES ('99', '03b90388349b75a38a2f8709c3987c6f.jpg', 'http://122.112.232.97/ImageClassify/zip/efd4374805e536aa1a69e764735282d7/', null, null, '0', '5', '40|天空;30|大海;26|日出日落;22|云;13|沙滩;', '1', '0');
INSERT INTO `image` VALUES ('100', '8ca73b8cb09ca82d0e78ca067e69ff71.jpg', 'http://122.112.232.97/ImageClassify/zip/efd4374805e536aa1a69e764735282d7/', null, null, '0', '5', '62|天空;25|人群;22|湖;19|大海;16|水;', '1', '0');
INSERT INTO `image` VALUES ('101', 'c6e3f6bed113f49324fba7f68f740011.jpg', 'http://122.112.232.97/ImageClassify/zip/2c034854d520b6013504c96fbc0626c6/', '2017-09-03 09:58:44', '广场;灯;夜晚;', '1', '6', '32|人群;24|广场;16|建筑;15|灯;13|夜晚;', '1', '1');
INSERT INTO `image` VALUES ('102', 'de89ccc41109aca7f190bb017d1148a5.jpg', 'http://122.112.232.97/ImageClassify/zip/2c034854d520b6013504c96fbc0626c6/', null, null, '0', '6', '52|天空;42|汽车;24|路;20|房屋;16|船;', '1', '0');
INSERT INTO `image` VALUES ('103', '246377f5771ba3c63347e31bb6114252.jpg', 'http://122.112.232.97/ImageClassify/zip/2c034854d520b6013504c96fbc0626c6/', null, null, '0', '6', '36|灯;29|夜晚;', '1', '0');
INSERT INTO `image` VALUES ('104', 'd8b737f66ebfcc8e3bf7faa4739edbb2.jpg', 'http://122.112.232.97/ImageClassify/zip/2c034854d520b6013504c96fbc0626c6/', null, null, '0', '6', '53|天空;23|云;20|飞机;12|汽车;', '1', '0');
INSERT INTO `image` VALUES ('105', '78e7f38e3d5a9e916acdcf2d62f814c1.jpg', 'http://122.112.232.97/ImageClassify/zip/2c034854d520b6013504c96fbc0626c6/', null, null, '0', '6', '18|人群;14|大厅;12|灯;', '1', '0');
INSERT INTO `image` VALUES ('106', '8ddcc136489a5d3f95698df59f9a70ef.jpg', 'http://122.112.232.97/ImageClassify/zip/2c034854d520b6013504c96fbc0626c6/', null, null, '0', '6', '89|灯;', '1', '0');
INSERT INTO `image` VALUES ('107', 'aef0b7ef1439be991a2389c33af0c28a.jpeg', 'http://122.112.232.97/ImageClassify/zip/2c034854d520b6013504c96fbc0626c6/', null, null, '0', '6', '36|广场;27|建筑;17|楼阁;', '1', '0');
INSERT INTO `image` VALUES ('108', '23aa570ffa9fef66a64813fdef549a95.jpg', 'http://122.112.232.97/ImageClassify/zip/2c034854d520b6013504c96fbc0626c6/', null, null, '0', '6', '52|天空;22|飞机;21|广场;20|汽车;14|路;', '1', '0');
INSERT INTO `image` VALUES ('109', 'f730f827e0788897cba28b05a1fc247f.jpg', 'http://122.112.232.97/ImageClassify/zip/2c034854d520b6013504c96fbc0626c6/', null, null, '0', '6', '24|灯;17|人群;16|夜晚;15|房屋;', '1', '0');
INSERT INTO `image` VALUES ('110', '33360f7cae96efd8009a143ced96c679.jpg', 'http://122.112.232.97/ImageClassify/zip/2c034854d520b6013504c96fbc0626c6/', null, null, '0', '6', '58|天空;42|云;', '1', '0');
INSERT INTO `image` VALUES ('111', '74c59de382437a83dcda33198c403701.jpg', 'http://122.112.232.97/ImageClassify/zip/2c034854d520b6013504c96fbc0626c6/', null, null, '0', '6', '61|天空;26|路;23|云;15|汽车;', '1', '0');
INSERT INTO `image` VALUES ('112', 'ab112e7f88164c4419e723182f8cb682.jpg', 'http://122.112.232.97/ImageClassify/zip/2c034854d520b6013504c96fbc0626c6/', null, null, '0', '6', '50|天空;26|飞机;16|汽车;13|路;', '1', '0');
INSERT INTO `image` VALUES ('113', 'f885042682862bf564a1e6ffe69b0064.jpg', 'http://122.112.232.97/ImageClassify/zip/2c034854d520b6013504c96fbc0626c6/', null, null, '0', '6', '68|飞机;45|乘客;', '1', '0');
INSERT INTO `image` VALUES ('114', '984c02932eff1b04933e02d2ca318da9.jpg', 'http://122.112.232.97/ImageClassify/zip/2c034854d520b6013504c96fbc0626c6/', null, null, '0', '6', '56|机场;', '1', '0');
INSERT INTO `image` VALUES ('115', '58cd7b5578839b1a101b13b5f5feaf79.jpg', 'http://122.112.232.97/ImageClassify/zip/2c034854d520b6013504c96fbc0626c6/', null, null, '0', '6', '59|天空;31|广场;20|建筑;19|云;14|汽车;13|路;', '1', '0');
INSERT INTO `image` VALUES ('116', 'd666575c6dcb06327f610bdc6dae8a48.jpg', 'http://122.112.232.97/ImageClassify/zip/2c034854d520b6013504c96fbc0626c6/', null, null, '0', '6', '58|天空;20|船;16|山;', '1', '0');
INSERT INTO `image` VALUES ('117', '95b317e95050ce14e21a3080fd4069bc.jpg', 'http://122.112.232.97/ImageClassify/zip/2c034854d520b6013504c96fbc0626c6/', null, null, '0', '6', '45|车;34|天空;', '1', '0');
INSERT INTO `image` VALUES ('118', 'e6193d035fd62a0e4f04cba205d5ff94.jpg', 'http://122.112.232.97/ImageClassify/zip/2c034854d520b6013504c96fbc0626c6/', null, null, '0', '6', '54|天空;53|船;27|湖;18|水;16|大海;', '1', '0');
INSERT INTO `image` VALUES ('119', '8d1f0887ff42f5dd35645bae1c441965.jpg', 'http://122.112.232.97/ImageClassify/zip/2c034854d520b6013504c96fbc0626c6/', null, null, '0', '6', '28|人群;21|广场;19|合影;13|建筑;', '1', '0');
INSERT INTO `image` VALUES ('120', 'e31cd8e55b73dcbe5df4b9a69b4d9e78.jpg', 'http://122.112.232.97/ImageClassify/zip/2c034854d520b6013504c96fbc0626c6/', null, null, '0', '6', '45|天空;44|飞机;', '1', '0');
INSERT INTO `image` VALUES ('121', '93299e02a1359a4f942bdd1596b3c476.jpg', 'http://122.112.232.97/ImageClassify/zip/19d32de74be932a263a1ec1e7469ec4c/', null, null, '0', '7', '56|电梯;45|商场;', '1', '0');
INSERT INTO `image` VALUES ('122', '217a0c96167d94d881d5f920f5fd4b74.jpg', 'http://122.112.232.97/ImageClassify/zip/19d32de74be932a263a1ec1e7469ec4c/', null, null, '0', '7', '17|人群;12|舞台;', '1', '0');
INSERT INTO `image` VALUES ('123', 'c0e64a9fbf6a63b14f41dc3ddcfd9bd8.jpg', 'http://122.112.232.97/ImageClassify/zip/19d32de74be932a263a1ec1e7469ec4c/', null, null, '0', '7', '22|灯;', '1', '0');
INSERT INTO `image` VALUES ('124', 'f28f8bb15f2bd96d649b9248cb17392f.jpg', 'http://122.112.232.97/ImageClassify/zip/19d32de74be932a263a1ec1e7469ec4c/', null, null, '0', '7', '36|街道;26|人群;19|房屋;18|灯;12|建筑;', '1', '0');
INSERT INTO `image` VALUES ('125', 'ec79958712dd4b9fc420726025e905a9.jpg', 'http://122.112.232.97/ImageClassify/zip/19d32de74be932a263a1ec1e7469ec4c/', null, null, '0', '7', '25|汽车;16|灯;13|大厅;', '1', '0');
INSERT INTO `image` VALUES ('126', 'dc0bbeb3aa29ec85b252555b83b07810.jpg', 'http://122.112.232.97/ImageClassify/zip/19d32de74be932a263a1ec1e7469ec4c/', null, null, '0', '7', '16|树木;15|建筑;', '1', '0');
INSERT INTO `image` VALUES ('127', '257adc619ec664a77697c1a6f27d74a5.jpg', 'http://122.112.232.97/ImageClassify/zip/19d32de74be932a263a1ec1e7469ec4c/', null, null, '0', '7', '32|灯;14|合影;13|酒店;', '1', '0');
INSERT INTO `image` VALUES ('128', 'f4a4aad07c6933a8d8d84b5a8111ec73.jpg', 'http://122.112.232.97/ImageClassify/zip/19d32de74be932a263a1ec1e7469ec4c/', '2017-09-03 09:59:40', '天空;汽车;房屋;', '1', '7', '49|天空;30|汽车;28|路;24|房屋;21|高楼;17|广场;15|建筑;14|街道;12|云;', '1', '1');
INSERT INTO `image` VALUES ('129', 'f97292e55d59cf4a24bdbe87e21a2729.jpg', 'http://122.112.232.97/ImageClassify/zip/19d32de74be932a263a1ec1e7469ec4c/', null, null, '0', '7', '32|灯;20|桌子;16|客厅;14|家具;12|沙发;', '1', '0');
INSERT INTO `image` VALUES ('130', '400d90dc5db455fd6751c167cb96d694.jpg', 'http://122.112.232.97/ImageClassify/zip/19d32de74be932a263a1ec1e7469ec4c/', null, null, '0', '7', '21|灯;19|大厅;12|桌子;', '1', '0');
INSERT INTO `image` VALUES ('131', '4dcfdf6d0d79290b23898f0de59b3beb.jpg', 'http://122.112.232.97/ImageClassify/zip/19d32de74be932a263a1ec1e7469ec4c/', null, null, '0', '7', '28|店铺;14|超市;', '1', '0');
INSERT INTO `image` VALUES ('132', '158abbe908d6a4e9456fa03bc1fcda08.jpg', 'http://122.112.232.97/ImageClassify/zip/19d32de74be932a263a1ec1e7469ec4c/', null, null, '0', '7', '31|房屋;30|天空;17|树木;14|街道;', '1', '0');
INSERT INTO `image` VALUES ('133', 'f41b28587070c3336f422e83b08fe516.jpg', 'http://122.112.232.97/ImageClassify/zip/19d32de74be932a263a1ec1e7469ec4c/', null, null, '0', '7', '26|大厅;22|灯;', '1', '0');
INSERT INTO `image` VALUES ('134', '665e0d2563166237f4ed5583ee4e7bb5.jpg', 'http://122.112.232.97/ImageClassify/zip/19d32de74be932a263a1ec1e7469ec4c/', null, null, '0', '7', '18|灯;14|汽车;12|大厅;', '1', '0');
INSERT INTO `image` VALUES ('135', 'f4fab6bec8cdc580884501d5a92fe88f.jpg', 'http://122.112.232.97/ImageClassify/zip/19d32de74be932a263a1ec1e7469ec4c/', '2017-09-03 10:00:19', '建筑;树木;', '1', '7', '19|游乐场;18|建筑;13|树木;', '1', '1');
INSERT INTO `image` VALUES ('136', '5acf500e19cb107256805d8c31944585.jpg', 'http://122.112.232.97/ImageClassify/zip/19d32de74be932a263a1ec1e7469ec4c/', null, null, '0', '7', '26|桌子;24|灯;21|椅子;20|家具;19|客厅;13|沙发;', '1', '0');
INSERT INTO `image` VALUES ('137', 'dad6847cb0407c8654233f8417b3d320.jpg', 'http://122.112.232.97/ImageClassify/zip/19d32de74be932a263a1ec1e7469ec4c/', null, null, '0', '7', '35|街道;30|灯;29|高楼;25|汽车;19|房屋;18|路;16|人群;13|天空;', '1', '0');
INSERT INTO `image` VALUES ('138', 'bb66b52376c40efa4b1713c8b2f2ae8c.jpg', 'http://122.112.232.97/ImageClassify/zip/19d32de74be932a263a1ec1e7469ec4c/', null, null, '0', '7', '34|灯;16|大厅;', '1', '0');
INSERT INTO `image` VALUES ('139', '6474ed87292c2aaeb1e3283bd72334ad.jpg', 'http://122.112.232.97/ImageClassify/zip/19d32de74be932a263a1ec1e7469ec4c/', null, null, '0', '7', '55|天空;35|路;25|汽车;20|树木;18|云;14|灯;', '1', '0');
INSERT INTO `image` VALUES ('140', '895440443b2461e9c508ce8fdac19115.jpg', 'http://122.112.232.97/ImageClassify/zip/19d32de74be932a263a1ec1e7469ec4c/', null, null, '0', '7', '26|灯;16|走廊;12|大厅;', '1', '0');
INSERT INTO `image` VALUES ('141', 'eb5ee434bbcd1245689a3cf9cf15390a.jpg', 'http://122.112.232.97/ImageClassify/zip/839a2b3f987f9d80b947a9fd4f09228f/', null, null, '0', '8', '42|街道;38|房屋;27|路;23|建筑;18|天空;14|树木;12|汽车;', '1', '0');
INSERT INTO `image` VALUES ('142', '5b36deded8728a8c715dd1f1750f5194.jpg', 'http://122.112.232.97/ImageClassify/zip/839a2b3f987f9d80b947a9fd4f09228f/', null, null, '0', '8', '42|街道;33|房屋;21|人群;20|路;16|汽车;13|天空;', '1', '0');
INSERT INTO `image` VALUES ('143', 'f98501907147c96489991375b6cd13a7.jpg', 'http://122.112.232.97/ImageClassify/zip/839a2b3f987f9d80b947a9fd4f09228f/', null, null, '0', '8', '40|房屋;36|路;29|街道;23|天空;20|树木;13|汽车;', '1', '0');
INSERT INTO `image` VALUES ('144', 'fc8b971f521f5068eef56b6fc3da22fe.jpg', 'http://122.112.232.97/ImageClassify/zip/839a2b3f987f9d80b947a9fd4f09228f/', null, null, '0', '8', '43|汽车;36|街道;33|房屋;29|路;14|树木;13|建筑;', '1', '0');
INSERT INTO `image` VALUES ('145', 'e229885bfa702931c7de46118a783867.jpg', 'http://122.112.232.97/ImageClassify/zip/839a2b3f987f9d80b947a9fd4f09228f/', null, null, '0', '8', '47|汽车;37|天空;36|房屋;33|路;22|街道;20|高楼;17|建筑;14|树木;', '1', '0');
INSERT INTO `image` VALUES ('146', '77095a8644fa2e80940e11e4d514285d.jpg', 'http://122.112.232.97/ImageClassify/zip/839a2b3f987f9d80b947a9fd4f09228f/', null, null, '0', '8', '62|汽车;35|房屋;33|街道;23|树木;19|路;18|天空;', '1', '0');
INSERT INTO `image` VALUES ('147', '0ad3b2b94d6b667e91975f3fc6ec5c09.jpg', 'http://122.112.232.97/ImageClassify/zip/839a2b3f987f9d80b947a9fd4f09228f/', null, null, '0', '8', '41|房屋;39|天空;36|路;28|树木;23|广场;22|建筑;14|汽车;', '1', '0');
INSERT INTO `image` VALUES ('148', '149be172fa3efdad736b3790d72712f7.jpg', 'http://122.112.232.97/ImageClassify/zip/839a2b3f987f9d80b947a9fd4f09228f/', null, null, '0', '8', '53|街道;31|人群;25|房屋;23|建筑;15|路;', '1', '0');
INSERT INTO `image` VALUES ('149', '29326575adce3d11cd1f3c7dd238858f.jpg', 'http://122.112.232.97/ImageClassify/zip/839a2b3f987f9d80b947a9fd4f09228f/', null, null, '0', '8', '40|街道;28|路;26|房屋;25|汽车;16|建筑;13|高楼;', '1', '0');
INSERT INTO `image` VALUES ('150', '7014463c29bb7ab2dc842e5374d3b3e8.jpg', 'http://122.112.232.97/ImageClassify/zip/839a2b3f987f9d80b947a9fd4f09228f/', null, null, '0', '8', '39|街道;31|房屋;25|路;17|人群;15|天空;12|树木;', '1', '0');
INSERT INTO `image` VALUES ('151', '2b31db070df3f95a96e61010c5a84b77.jpg', 'http://122.112.232.97/ImageClassify/zip/839a2b3f987f9d80b947a9fd4f09228f/', null, null, '0', '8', '30|夜晚;26|灯;23|街道;19|房屋;13|建筑;', '1', '0');
INSERT INTO `image` VALUES ('152', '34dea7ca2b65ee4c941e875d49b6a94a.jpg', 'http://122.112.232.97/ImageClassify/zip/839a2b3f987f9d80b947a9fd4f09228f/', null, null, '0', '8', '42|房屋;29|路;26|街道;24|人群;20|建筑;18|汽车;13|树木;', '1', '0');
INSERT INTO `image` VALUES ('153', '7864038cf3d58c60c8c9677372ff8b77.jpg', 'http://122.112.232.97/ImageClassify/zip/839a2b3f987f9d80b947a9fd4f09228f/', '2017-09-03 10:03:46', '天空;房屋;建筑;', '1', '8', '50|天空;33|房屋;22|建筑;13|欧式建筑;', '1', '1');
INSERT INTO `image` VALUES ('154', '658806554c2f21bc328f93ae674c53ca.jpg', 'http://122.112.232.97/ImageClassify/zip/839a2b3f987f9d80b947a9fd4f09228f/', '2017-09-03 10:04:26', '房屋;天空;街道;', '1', '8', '43|房屋;39|天空;25|街道;20|路;18|建筑;', '1', '1');
INSERT INTO `image` VALUES ('155', '71511ce250b27cb75b23a8271ecc0489.jpg', 'http://122.112.232.97/ImageClassify/zip/839a2b3f987f9d80b947a9fd4f09228f/', null, null, '0', '8', '64|树木;43|路;25|天空;17|房屋;12|街道;', '1', '0');
INSERT INTO `image` VALUES ('156', '5eaf249d7ded2b7a8d5cb97662416213.jpg', 'http://122.112.232.97/ImageClassify/zip/839a2b3f987f9d80b947a9fd4f09228f/', null, null, '0', '8', '29|街道;21|房屋;', '1', '0');
INSERT INTO `image` VALUES ('157', '77168f593c16e04680db2580cd4aa344.jpg', 'http://122.112.232.97/ImageClassify/zip/839a2b3f987f9d80b947a9fd4f09228f/', null, null, '0', '8', '39|房屋;38|街道;34|路;26|天空;18|建筑;16|汽车;12|树木;', '1', '0');
INSERT INTO `image` VALUES ('158', 'fdb64da6f91c7bc3d1a0b3d5707f9e94.jpg', 'http://122.112.232.97/ImageClassify/zip/839a2b3f987f9d80b947a9fd4f09228f/', null, null, '0', '8', '45|天空;35|房屋;31|汽车;28|街道;27|路;22|建筑;', '1', '0');
INSERT INTO `image` VALUES ('159', '1893db4b2635ce7ead23eecf03014bd2.jpg', 'http://122.112.232.97/ImageClassify/zip/839a2b3f987f9d80b947a9fd4f09228f/', null, null, '0', '8', '57|天空;37|房屋;33|路;25|树木;24|汽车;18|街道;12|建筑;', '1', '0');
INSERT INTO `image` VALUES ('160', '907bdd795bd9421e19303ed5ce0eaf05.jpeg', 'http://122.112.232.97/ImageClassify/zip/215aad82d7aa3a24fae36cf3b0dc4382/', null, null, '0', '9', '22|夜晚;20|树木;19|天空;13|烟火;', '1', '0');
INSERT INTO `image` VALUES ('161', '138d2a94b873271068a0a6217fec5358.jpg', 'http://122.112.232.97/ImageClassify/zip/215aad82d7aa3a24fae36cf3b0dc4382/', null, null, '0', '9', '39|夜晚;27|烟火;', '1', '0');
INSERT INTO `image` VALUES ('162', '15f9a930a98a63ce72679b0cfeb88e1d.jpg', 'http://122.112.232.97/ImageClassify/zip/215aad82d7aa3a24fae36cf3b0dc4382/', null, null, '0', '9', '41|夜晚;39|烟火;', '1', '0');
INSERT INTO `image` VALUES ('163', '0b9f4e2a9c4385367b4c374fcb82f301.jpeg', 'http://122.112.232.97/ImageClassify/zip/215aad82d7aa3a24fae36cf3b0dc4382/', null, null, '0', '9', '23|卡通;22|截图;', '1', '0');
INSERT INTO `image` VALUES ('164', 'b6e9b3d2f632d2af46e6de7d327bb231.jpg', 'http://122.112.232.97/ImageClassify/zip/215aad82d7aa3a24fae36cf3b0dc4382/', null, null, '0', '9', '36|夜晚;22|烟火;', '1', '0');
INSERT INTO `image` VALUES ('165', 'f4b4de588a394bb9ee1fa721d9b12122.jpg', 'http://122.112.232.97/ImageClassify/zip/215aad82d7aa3a24fae36cf3b0dc4382/', null, null, '0', '9', '34|夜晚;25|烟火;13|卡通;', '1', '0');
INSERT INTO `image` VALUES ('166', '50f359372a6bfcf2cde42d7db948cb61.jpeg', 'http://122.112.232.97/ImageClassify/zip/215aad82d7aa3a24fae36cf3b0dc4382/', null, null, '0', '9', '28|夜晚;19|截图;14|月亮;', '1', '0');
INSERT INTO `image` VALUES ('167', '79368c9e545b570a9d7464d9bee41b4f.jpg', 'http://122.112.232.97/ImageClassify/zip/00dc232ffa5e87b51f5be71b660a4dc8/', null, null, '0', '10', '43|房屋;28|树木;19|天空;16|高楼;', '1', '0');
INSERT INTO `image` VALUES ('168', 'e10a16f5ecf13cef39c7460755e21576.jpg', 'http://122.112.232.97/ImageClassify/zip/00dc232ffa5e87b51f5be71b660a4dc8/', null, null, '0', '10', '56|天空;23|路;20|房屋;17|汽车;16|高楼;', '1', '0');
INSERT INTO `image` VALUES ('169', '9ca7110d14342467013822db91bf3948.jpg', 'http://122.112.232.97/ImageClassify/zip/00dc232ffa5e87b51f5be71b660a4dc8/', null, null, '0', '10', '27|房屋;14|建筑;', '1', '0');
INSERT INTO `image` VALUES ('170', '0f3d694f1bac2a7d6a83b8d15f505257.JPG', 'http://122.112.232.97/ImageClassify/zip/00dc232ffa5e87b51f5be71b660a4dc8/', null, null, '0', '10', '50|汽车;40|天空;34|房屋;33|路;26|树木;17|建筑;', '1', '0');
INSERT INTO `image` VALUES ('171', '8baf26f6d639ee5ad5afd48edda17224.jpg', 'http://122.112.232.97/ImageClassify/zip/00dc232ffa5e87b51f5be71b660a4dc8/', null, null, '0', '10', '59|树木;32|天空;29|房屋;23|草地;20|水;19|湖;14|建筑;', '1', '0');
INSERT INTO `image` VALUES ('172', 'b9901d26aa5705f1a50524bfd33afecb.jpg', 'http://122.112.232.97/ImageClassify/zip/00dc232ffa5e87b51f5be71b660a4dc8/', null, null, '0', '10', '60|树木;34|房屋;27|草地;25|天空;14|建筑;', '1', '0');
INSERT INTO `image` VALUES ('173', 'c47ea14eaa6d835567c9df329d902291.jpg', 'http://122.112.232.97/ImageClassify/zip/00dc232ffa5e87b51f5be71b660a4dc8/', null, null, '0', '10', '49|树木;46|天空;34|都市远景;20|房屋;', '1', '0');
INSERT INTO `image` VALUES ('174', 'e21f6cb425b0dcd83fc18da2146ac67a.jpg', 'http://122.112.232.97/ImageClassify/zip/00dc232ffa5e87b51f5be71b660a4dc8/', '2017-09-03 11:07:39', '房屋;天空;路;', '1', '10', '52|树木;37|门;22|天空;19|建筑;13|房屋;', '1', '1');
INSERT INTO `image` VALUES ('175', '90a692932554fb86f7ed9712b5f59d96.jpg', 'http://122.112.232.97/ImageClassify/zip/00dc232ffa5e87b51f5be71b660a4dc8/', null, null, '0', '10', '58|天空;51|树木;35|房屋;20|云;15|建筑;13|草地;', '1', '0');
INSERT INTO `image` VALUES ('176', '472ffb3ec28eff2a5af8950f317a0915.jpg', 'http://122.112.232.97/ImageClassify/zip/00dc232ffa5e87b51f5be71b660a4dc8/', null, null, '0', '10', '60|树木;30|天空;25|房屋;13|建筑;12|山;', '1', '0');
INSERT INTO `image` VALUES ('177', '65766b340f5f1b6d1a2ad664845ffdde.jpg', 'http://122.112.232.97/ImageClassify/zip/00dc232ffa5e87b51f5be71b660a4dc8/', null, null, '0', '10', '56|天空;43|房屋;32|树木;26|云;14|建筑;', '1', '0');
INSERT INTO `image` VALUES ('178', 'c1f3fc369dc54ca400a45cf1eb747c73.jpg', 'http://122.112.232.97/ImageClassify/zip/00dc232ffa5e87b51f5be71b660a4dc8/', null, null, '0', '10', '55|树木;51|草地;14|公园;13|合影;', '1', '0');
INSERT INTO `image` VALUES ('179', 'a21ee01caed076d6057d7f5ff424b123.jpg', 'http://122.112.232.97/ImageClassify/zip/00dc232ffa5e87b51f5be71b660a4dc8/', null, null, '0', '10', '58|树木;30|房屋;17|天空;14|建筑;', '1', '0');
INSERT INTO `image` VALUES ('180', '31e46b8a376b8c3c9b5223e30f494c08.jpg', 'http://122.112.232.97/ImageClassify/zip/00dc232ffa5e87b51f5be71b660a4dc8/', null, null, '0', '10', '48|天空;46|门;35|建筑;31|树木;', '1', '0');
INSERT INTO `image` VALUES ('181', 'a54169c0c55346712397e195836a9ec3.jpg', 'http://122.112.232.97/ImageClassify/zip/00dc232ffa5e87b51f5be71b660a4dc8/', null, null, '0', '10', '35|树木;33|路;29|汽车;24|房屋;18|建筑;15|街道;12|门;', '1', '0');
INSERT INTO `image` VALUES ('182', '7ef4c41bb7b1b28c1a84f28df22a5709.jpg', 'http://122.112.232.97/ImageClassify/zip/00dc232ffa5e87b51f5be71b660a4dc8/', null, null, '0', '10', '57|天空;32|树木;30|路;21|云;19|房屋;16|高楼;13|建筑;', '1', '0');
INSERT INTO `image` VALUES ('183', 'd15a315a3a5c5417b3f10f7d062e5b6f.jpg', 'http://122.112.232.97/ImageClassify/zip/00dc232ffa5e87b51f5be71b660a4dc8/', null, null, '0', '10', '57|树木;45|天空;37|房屋;27|草地;15|建筑;', '1', '0');
INSERT INTO `image` VALUES ('184', '6b88d9269a8badaca969db54e842b78b.jpg', 'http://122.112.232.97/ImageClassify/zip/00dc232ffa5e87b51f5be71b660a4dc8/', null, null, '0', '10', '35|天空;19|建筑;', '1', '0');
INSERT INTO `image` VALUES ('185', 'cf97e550365a0d95d613096a9439b988.jpg', 'http://122.112.232.97/ImageClassify/zip/00dc232ffa5e87b51f5be71b660a4dc8/', null, null, '0', '10', '46|树木;38|天空;34|水;33|湖;17|楼阁;14|河;13|船;', '1', '0');
INSERT INTO `image` VALUES ('186', '92b9145c50f146301340b775f1adbe4a.jpg', 'http://122.112.232.97/ImageClassify/zip/00dc232ffa5e87b51f5be71b660a4dc8/', null, null, '0', '10', '70|天空;44|云;29|树木;19|路;18|房屋;12|高楼;', '1', '0');
INSERT INTO `image` VALUES ('187', 'ff169b08379a533018217adb32729c5a.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '26|桌子;25|椅子;24|灯;13|教室;', '1', '0');
INSERT INTO `image` VALUES ('188', '59212933b77338d7df21ea6175862532.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '16|桌子;13|灯;', '1', '0');
INSERT INTO `image` VALUES ('189', 'c953caf2730907ae8e372aa6cb9c67fb.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '24|家具;18|客厅;14|桌子;12|屏幕;', '1', '0');
INSERT INTO `image` VALUES ('190', 'b7c8a6676896ed3aa42c572d765de011.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '26|教室;25|桌子;14|椅子;13|人群;', '1', '0');
INSERT INTO `image` VALUES ('191', '5fc2a705737d4d02762d07b18d4e20c0.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '30|房屋;22|汽车;14|建筑;', '1', '0');
INSERT INTO `image` VALUES ('192', '6a98db6c34011f31dd653b8d327df139.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '16|家具;', '1', '0');
INSERT INTO `image` VALUES ('193', '64e0e750ac24067a45c1936b1f6d2e5b.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '22|灯;18|走廊;17|大厅;', '1', '0');
INSERT INTO `image` VALUES ('194', '006b774f45bd78fd51b6298d801677eb.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '65|天空;28|云;27|树木;24|路;21|高楼;20|房屋;16|汽车;13|草地;', '1', '0');
INSERT INTO `image` VALUES ('195', 'a0fd5f829e73285541eeadc58c44a26b.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '22|灯;18|走廊;17|大厅;', '1', '0');
INSERT INTO `image` VALUES ('196', '833279459e0b8881b4ecceeff4d77d5c.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '42|房屋;36|树木;23|汽车;21|路;18|建筑;', '1', '0');
INSERT INTO `image` VALUES ('197', 'ebee87abb7dba5fb10db1c664d30042a.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '54|天空;43|高楼;24|树木;17|房屋;13|建筑;', '1', '0');
INSERT INTO `image` VALUES ('198', 'a646a6e364cc2068aae8664beb9ad649.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '16|人群;15|灯;14|桌子;', '1', '0');
INSERT INTO `image` VALUES ('199', '1243193adfccec320d4f08e288614a29.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '19|桌子;14|灯;12|大厅;', '1', '0');
INSERT INTO `image` VALUES ('200', '8e763670f404fe0583bde3db0457a76d.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '26|桌子;14|椅子;12|家具;', '1', '0');
INSERT INTO `image` VALUES ('201', '66d6b3ca5a494f8607267748e5dab919.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '54|天空;43|高楼;24|树木;17|房屋;13|建筑;', '1', '0');
INSERT INTO `image` VALUES ('202', 'ed2bcd2c8db9d1f96392e1445ba7204d.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '26|桌子;14|椅子;12|家具;', '1', '0');
INSERT INTO `image` VALUES ('203', 'e94cbf42b6ddab2b4091b282a73a4081.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '65|天空;28|云;27|树木;24|路;21|高楼;20|房屋;16|汽车;13|草地;', '1', '0');
INSERT INTO `image` VALUES ('204', 'efa7442f03be667292d2650812f99d46.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '33|天空;32|房屋;25|建筑;22|路;16|广场;', '1', '0');
INSERT INTO `image` VALUES ('205', '71c420d677beb5b9c9e0c0bd75f26fa8.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '60|合影;42|婚纱;', '1', '0');
INSERT INTO `image` VALUES ('206', 'a06a168dfce05190ef0f1cca5c043d7d.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '28|房屋;27|树木;15|高楼;12|街道;', '1', '0');
INSERT INTO `image` VALUES ('207', '896e49da1d80685426136e3db67b88b4.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '42|房屋;36|树木;23|汽车;21|路;18|建筑;', '1', '0');
INSERT INTO `image` VALUES ('208', '32e8ff9687b7fd3efbf13f6c906d0c9d.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '26|教室;25|桌子;14|椅子;13|人群;', '1', '0');
INSERT INTO `image` VALUES ('209', '2c9c30979bdcb0f92dc15631267246cf.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '26|桌子;25|椅子;24|灯;13|教室;', '1', '0');
INSERT INTO `image` VALUES ('210', '7c74ebdb585ca531c4018d19414d75cf.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '27|客厅;23|灯;19|桌子;18|椅子;15|家具;', '1', '0');
INSERT INTO `image` VALUES ('211', '56a5fa86d82db5a7bb4621d83aa54428.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '60|合影;42|婚纱;', '1', '0');
INSERT INTO `image` VALUES ('212', '1110cdfe11e3bc4c53f3f0905feff3a3.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '36|灯;25|桌子;17|家具;16|椅子;15|沙发;', '1', '0');
INSERT INTO `image` VALUES ('213', '09849ea51e7a147f6c1271d4c5cb0542.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '33|天空;32|房屋;25|建筑;22|路;16|广场;', '1', '0');
INSERT INTO `image` VALUES ('214', 'bad6c73ebf28287a9242b13a2a8b559e.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '27|客厅;23|灯;19|桌子;18|椅子;15|家具;', '1', '0');
INSERT INTO `image` VALUES ('215', 'd803c8071082e02dcaf09544df0268b4.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '35|楼阁;32|树木;31|天空;14|门;13|建筑;', '1', '0');
INSERT INTO `image` VALUES ('216', '7c47c306b202ddac1ce7bc6b9ddad23e.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '21|灯;20|大厅;18|人群;', '1', '0');
INSERT INTO `image` VALUES ('217', '8499f3b39727887892c95daf985668c7.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '28|房屋;27|树木;15|高楼;12|街道;', '1', '0');
INSERT INTO `image` VALUES ('218', '0a33fe08a46997f86e13e33fc1aef28a.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '21|灯;20|大厅;18|人群;', '1', '0');
INSERT INTO `image` VALUES ('219', '4d89937d07a06fdfb00e767493e70752.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '16|人群;15|灯;14|桌子;', '1', '0');
INSERT INTO `image` VALUES ('220', 'fd8b0c984ac471ea0447e2598578cabc.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '24|家具;18|客厅;14|桌子;12|屏幕;', '1', '0');
INSERT INTO `image` VALUES ('221', '540296e588207f2a1ec3359260641627.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '16|家具;', '1', '0');
INSERT INTO `image` VALUES ('222', 'e78cf0d124840bad28cbbead01329f0b.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '19|桌子;14|灯;12|大厅;', '1', '0');
INSERT INTO `image` VALUES ('223', 'f4c1d84cd7dcbce7139ef6ca428a41a8.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '16|桌子;13|灯;', '1', '0');
INSERT INTO `image` VALUES ('224', 'fb76add6a827555038878f75e22ad273.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '35|楼阁;32|树木;31|天空;14|门;13|建筑;', '1', '0');
INSERT INTO `image` VALUES ('225', 'e226fd08a312e1a70df7ff5768205715.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '36|灯;25|桌子;17|家具;16|椅子;15|沙发;', '1', '0');
INSERT INTO `image` VALUES ('226', '8c4e03bc3c610889f7d123e0f55c31fd.jpg', 'http://122.112.232.97/ImageClassify/zip/b25807a7d96b98deb54971d61aa657f4/', null, null, '0', '11', '30|房屋;22|汽车;14|建筑;', '1', '0');
INSERT INTO `image` VALUES ('227', 'cd41ab5b451d24bdfa232f9cb1a2bd8a.jpg', 'http://122.112.232.97/ImageClassify/zip/2233c86883f8fbb7ae1e7c12b7764da2/', null, null, '0', '16', '72|草地;43|云;37|树木;', '1', '0');
INSERT INTO `image` VALUES ('228', 'e94a9220fd936a19f1a7bbfbd752005c.jpg', 'http://122.112.232.97/ImageClassify/zip/2233c86883f8fbb7ae1e7c12b7764da2/', null, null, '0', '16', '62|天空;40|树木;19|水;17|湖;', '1', '0');
INSERT INTO `image` VALUES ('229', 'c223232859c92636467ab5f2b78f65ec.jpg', 'http://122.112.232.97/ImageClassify/zip/2233c86883f8fbb7ae1e7c12b7764da2/', null, null, '0', '16', '44|广场;41|树木;40|天空;23|人群;13|路;', '1', '0');
INSERT INTO `image` VALUES ('230', '89220df233144b0f2239867814d82f1f.jpg', 'http://122.112.232.97/ImageClassify/zip/2233c86883f8fbb7ae1e7c12b7764da2/', null, null, '0', '16', '51|天空;30|女孩;21|湖;16|水;13|男孩;', '1', '0');
INSERT INTO `image` VALUES ('231', '47d7219db66085da181255724d5d9520.jpg', 'http://122.112.232.97/ImageClassify/zip/2233c86883f8fbb7ae1e7c12b7764da2/', null, null, '0', '16', '51|天空;24|高楼;18|树木;14|云;12|建筑;', '1', '0');
INSERT INTO `image` VALUES ('232', 'cdea5aeae9e2941a2ec5fbc0ebc954bf.jpg', 'http://122.112.232.97/ImageClassify/zip/1b687ebb8781e79a6b6841dd668ee39d/', null, null, '0', '12', null, '0', '0');
INSERT INTO `image` VALUES ('233', 'a8550c733e920c2355a9ffdc5c53d3f5.jpg', 'http://122.112.232.97/ImageClassify/zip/1b687ebb8781e79a6b6841dd668ee39d/', null, null, '0', '12', null, '0', '0');
INSERT INTO `image` VALUES ('234', '07827dcadd3f58516ecb9d33cefd6848.jpg', 'http://122.112.232.97/ImageClassify/zip/1b687ebb8781e79a6b6841dd668ee39d/', null, null, '0', '12', null, '0', '0');
INSERT INTO `image` VALUES ('235', 'be4ffb56fb28e28132b931c46a77def7.jpg', 'http://122.112.232.97/ImageClassify/zip/1b687ebb8781e79a6b6841dd668ee39d/', null, null, '0', '12', null, '0', '0');
INSERT INTO `image` VALUES ('236', '1aa8ea8fc53a7db20f17a79f576e6431.jpg', 'http://122.112.232.97/ImageClassify/zip/1b687ebb8781e79a6b6841dd668ee39d/', null, null, '0', '12', null, '0', '0');
INSERT INTO `image` VALUES ('237', '24469032ad081dce84c51d87910ae771.jpg', 'http://122.112.232.97/ImageClassify/zip/1b687ebb8781e79a6b6841dd668ee39d/', null, null, '0', '12', null, '0', '0');
INSERT INTO `image` VALUES ('238', '758a7f3ae2a986a4421111ade2f61327.jpg', 'http://122.112.232.97/ImageClassify/zip/1b687ebb8781e79a6b6841dd668ee39d/', null, null, '0', '12', null, '0', '0');
INSERT INTO `image` VALUES ('239', '28f57ab22c52a4048f571fed18b86155.jpg', 'http://122.112.232.97/ImageClassify/zip/1b687ebb8781e79a6b6841dd668ee39d/', null, null, '0', '12', null, '0', '0');
INSERT INTO `image` VALUES ('240', 'e3c49d0243ec400e3a05e631852f2106.jpeg', 'http://122.112.232.97/ImageClassify/zip/1b687ebb8781e79a6b6841dd668ee39d/', null, null, '0', '12', null, '0', '0');
INSERT INTO `image` VALUES ('241', 'af271e4c5679c88338fc93cc075c15a9.jpeg', 'http://122.112.232.97/ImageClassify/zip/1b687ebb8781e79a6b6841dd668ee39d/', null, null, '0', '12', null, '0', '0');
INSERT INTO `image` VALUES ('242', '1ad2e4085fa37589845794ef853956ef.png', 'http://122.112.232.97/ImageClassify/zip/1b687ebb8781e79a6b6841dd668ee39d/', null, null, '0', '12', null, '0', '0');
INSERT INTO `image` VALUES ('243', '175435ded880aaadf5022573aa2658a7.jpg', 'http://122.112.232.97/ImageClassify/zip/1b687ebb8781e79a6b6841dd668ee39d/', null, null, '0', '12', null, '0', '0');
INSERT INTO `image` VALUES ('244', '8bd0eed60311e8410d432ddaadf33045.jpg', 'http://122.112.232.97/ImageClassify/zip/1b687ebb8781e79a6b6841dd668ee39d/', null, null, '0', '12', null, '0', '0');
INSERT INTO `image` VALUES ('245', 'a86e24fe294ecea2b26e3714fb10a2ec.jpg', 'http://122.112.232.97/ImageClassify/zip/1b687ebb8781e79a6b6841dd668ee39d/', null, null, '0', '12', null, '0', '0');
INSERT INTO `image` VALUES ('246', 'fded37c0da68c8fdf5a8d8fe4afd24da.jpeg', 'http://122.112.232.97/ImageClassify/zip/1b687ebb8781e79a6b6841dd668ee39d/', null, null, '0', '12', null, '0', '0');
INSERT INTO `image` VALUES ('247', 'f66e4d54b00a73c2e4adb6f02c6dbe09.jpg', 'http://122.112.232.97/ImageClassify/zip/1b687ebb8781e79a6b6841dd668ee39d/', null, null, '0', '12', null, '0', '0');
INSERT INTO `image` VALUES ('248', '2ac401207463dfc4bb54057b094a3416.JPG', 'http://122.112.232.97/ImageClassify/zip/1b687ebb8781e79a6b6841dd668ee39d/', null, null, '0', '12', null, '0', '0');
INSERT INTO `image` VALUES ('249', '86e3461f8dd1b36eadf9265e86d53f30.jpg', 'http://122.112.232.97/ImageClassify/zip/1b687ebb8781e79a6b6841dd668ee39d/', null, null, '0', '12', null, '0', '0');
INSERT INTO `image` VALUES ('250', 'a4f6794dda8865e71c231aadfc5c2485.jpg', 'http://122.112.232.97/ImageClassify/zip/1b687ebb8781e79a6b6841dd668ee39d/', null, null, '0', '12', null, '0', '0');
INSERT INTO `image` VALUES ('251', '608883042dd2a99e8a028a92cff47412.jpg', 'http://122.112.232.97/ImageClassify/zip/1b687ebb8781e79a6b6841dd668ee39d/', null, null, '0', '12', null, '0', '0');
INSERT INTO `image` VALUES ('252', '9dbe32d04fa3943cb2fbb7cedae9cfd8.jpg', 'http://122.112.232.97/ImageClassify/zip/29f375ecec1ad8ddb0cc9774ffe7dd4e/', null, null, '0', '18', null, '0', '0');
INSERT INTO `image` VALUES ('253', 'a82018e3d7bbd12c78f9898df532e33e.jpg', 'http://122.112.232.97/ImageClassify/zip/29f375ecec1ad8ddb0cc9774ffe7dd4e/', null, null, '0', '18', null, '0', '0');
INSERT INTO `image` VALUES ('254', '1d87a1c3e234f3d8ffab9b4db0459ad4.jpg', 'http://122.112.232.97/ImageClassify/zip/29f375ecec1ad8ddb0cc9774ffe7dd4e/', null, null, '0', '18', null, '0', '0');
INSERT INTO `image` VALUES ('255', '855852bcaa452c4d1e30044323d7f59f.jpg', 'http://122.112.232.97/ImageClassify/zip/29f375ecec1ad8ddb0cc9774ffe7dd4e/', null, null, '0', '18', null, '0', '0');
INSERT INTO `image` VALUES ('256', 'c86d4df81cb892499787310070914aec.jpg', 'http://122.112.232.97/ImageClassify/zip/29f375ecec1ad8ddb0cc9774ffe7dd4e/', null, null, '0', '18', null, '0', '0');
INSERT INTO `image` VALUES ('257', 'f367c26824d6ffcef74b9da6fb015d64.jpg', 'http://122.112.232.97/ImageClassify/zip/869ad6c77cc963bf3d6cb65f93a19ef0/', null, null, '0', '19', '72|草地;43|云;37|树木;', '0', '0');
INSERT INTO `image` VALUES ('258', 'f575787a97fae9ec739eb7165394ca34.jpg', 'http://122.112.232.97/ImageClassify/zip/869ad6c77cc963bf3d6cb65f93a19ef0/', null, null, '0', '19', '62|天空;40|树木;19|水;17|湖;', '0', '0');
INSERT INTO `image` VALUES ('259', '6ad7a6387eb76443ee57d0f16588cb3e.jpg', 'http://122.112.232.97/ImageClassify/zip/869ad6c77cc963bf3d6cb65f93a19ef0/', null, null, '0', '19', '44|广场;41|树木;40|天空;23|人群;13|路;', '0', '0');
INSERT INTO `image` VALUES ('260', '20ab868f1e5b4a3a58d61acff816f2d5.jpg', 'http://122.112.232.97/ImageClassify/zip/869ad6c77cc963bf3d6cb65f93a19ef0/', null, null, '0', '19', '51|天空;30|女孩;21|湖;16|水;13|男孩;', '0', '0');
INSERT INTO `image` VALUES ('261', '720d4897b9e9a3e2f852a4823c5bdfc4.jpg', 'http://122.112.232.97/ImageClassify/zip/869ad6c77cc963bf3d6cb65f93a19ef0/', null, null, '0', '19', '51|天空;24|高楼;18|树木;14|云;12|建筑;', '0', '0');
INSERT INTO `image` VALUES ('262', '806c0df54db54872deace96ee1d0b674.jpg', 'http://122.112.232.97/ImageClassify/zip/bf6b56994bb242a39ac0c1d012139e4b/', null, null, '0', '21', '72|草地;43|云;37|树木;', '0', '0');
INSERT INTO `image` VALUES ('263', 'a970a88af954fff318d6396360513eab.jpg', 'http://122.112.232.97/ImageClassify/zip/bf6b56994bb242a39ac0c1d012139e4b/', null, null, '0', '21', '62|天空;40|树木;19|水;17|湖;', '0', '0');
INSERT INTO `image` VALUES ('264', 'e66365e99776d79963e34fa576f7426e.jpg', 'http://122.112.232.97/ImageClassify/zip/bf6b56994bb242a39ac0c1d012139e4b/', null, null, '0', '21', '44|广场;41|树木;40|天空;23|人群;13|路;', '0', '0');
INSERT INTO `image` VALUES ('265', 'f50d8c6b8355895024bce1a27c79b724.jpg', 'http://122.112.232.97/ImageClassify/zip/bf6b56994bb242a39ac0c1d012139e4b/', null, null, '0', '21', '51|天空;30|女孩;21|湖;16|水;13|男孩;', '0', '0');
INSERT INTO `image` VALUES ('266', 'e59d6c2dd7a688fc96602872bf8be3e7.jpg', 'http://122.112.232.97/ImageClassify/zip/bf6b56994bb242a39ac0c1d012139e4b/', null, null, '0', '21', '51|天空;24|高楼;18|树木;14|云;12|建筑;', '0', '0');

-- ----------------------------
-- Table structure for image_category
-- ----------------------------
DROP TABLE IF EXISTS `image_category`;
CREATE TABLE `image_category` (
  `img_id` int(10) unsigned NOT NULL,
  `category_id` int(10) unsigned NOT NULL,
  KEY `img_id` (`img_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `image_category_ibfk_1` FOREIGN KEY (`img_id`) REFERENCES `image` (`img_id`),
  CONSTRAINT `image_category_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of image_category
-- ----------------------------
INSERT INTO `image_category` VALUES ('1', '5');
INSERT INTO `image_category` VALUES ('2', '23');
INSERT INTO `image_category` VALUES ('3', '12');
INSERT INTO `image_category` VALUES ('4', '14');
INSERT INTO `image_category` VALUES ('5', '7');
INSERT INTO `image_category` VALUES ('6', '12');
INSERT INTO `image_category` VALUES ('7', '23');
INSERT INTO `image_category` VALUES ('8', '14');
INSERT INTO `image_category` VALUES ('9', '6');
INSERT INTO `image_category` VALUES ('10', '5');
INSERT INTO `image_category` VALUES ('11', '23');
INSERT INTO `image_category` VALUES ('12', '14');
INSERT INTO `image_category` VALUES ('13', '22');
INSERT INTO `image_category` VALUES ('14', '5');
INSERT INTO `image_category` VALUES ('15', '12');
INSERT INTO `image_category` VALUES ('16', '12');
INSERT INTO `image_category` VALUES ('17', '14');
INSERT INTO `image_category` VALUES ('18', '7');
INSERT INTO `image_category` VALUES ('19', '23');
INSERT INTO `image_category` VALUES ('20', '12');
INSERT INTO `image_category` VALUES ('21', '17');
INSERT INTO `image_category` VALUES ('22', '14');
INSERT INTO `image_category` VALUES ('23', '14');
INSERT INTO `image_category` VALUES ('24', '14');
INSERT INTO `image_category` VALUES ('25', '21');
INSERT INTO `image_category` VALUES ('26', '21');
INSERT INTO `image_category` VALUES ('27', '21');
INSERT INTO `image_category` VALUES ('28', '14');
INSERT INTO `image_category` VALUES ('29', '14');
INSERT INTO `image_category` VALUES ('30', '14');
INSERT INTO `image_category` VALUES ('31', '21');
INSERT INTO `image_category` VALUES ('32', '14');
INSERT INTO `image_category` VALUES ('33', '6');
INSERT INTO `image_category` VALUES ('34', '8');
INSERT INTO `image_category` VALUES ('35', '6');
INSERT INTO `image_category` VALUES ('36', '14');
INSERT INTO `image_category` VALUES ('37', '21');
INSERT INTO `image_category` VALUES ('38', '14');
INSERT INTO `image_category` VALUES ('39', '14');
INSERT INTO `image_category` VALUES ('40', '21');
INSERT INTO `image_category` VALUES ('41', '8');
INSERT INTO `image_category` VALUES ('42', '12');
INSERT INTO `image_category` VALUES ('43', '14');
INSERT INTO `image_category` VALUES ('44', '21');
INSERT INTO `image_category` VALUES ('45', '6');
INSERT INTO `image_category` VALUES ('46', '21');
INSERT INTO `image_category` VALUES ('47', '6');
INSERT INTO `image_category` VALUES ('48', '7');
INSERT INTO `image_category` VALUES ('49', '14');
INSERT INTO `image_category` VALUES ('50', '6');
INSERT INTO `image_category` VALUES ('51', '8');
INSERT INTO `image_category` VALUES ('52', '1');
INSERT INTO `image_category` VALUES ('53', '14');
INSERT INTO `image_category` VALUES ('54', '14');
INSERT INTO `image_category` VALUES ('55', '21');
INSERT INTO `image_category` VALUES ('56', '14');
INSERT INTO `image_category` VALUES ('57', '14');
INSERT INTO `image_category` VALUES ('58', '21');
INSERT INTO `image_category` VALUES ('59', '12');
INSERT INTO `image_category` VALUES ('60', '8');
INSERT INTO `image_category` VALUES ('61', '14');
INSERT INTO `image_category` VALUES ('62', '9');
INSERT INTO `image_category` VALUES ('63', '14');
INSERT INTO `image_category` VALUES ('64', '14');
INSERT INTO `image_category` VALUES ('65', '14');
INSERT INTO `image_category` VALUES ('66', '14');
INSERT INTO `image_category` VALUES ('67', '9');
INSERT INTO `image_category` VALUES ('68', '9');
INSERT INTO `image_category` VALUES ('69', '9');
INSERT INTO `image_category` VALUES ('70', '9');
INSERT INTO `image_category` VALUES ('71', '14');
INSERT INTO `image_category` VALUES ('72', '14');
INSERT INTO `image_category` VALUES ('73', '21');
INSERT INTO `image_category` VALUES ('74', '14');
INSERT INTO `image_category` VALUES ('75', '14');
INSERT INTO `image_category` VALUES ('76', '21');
INSERT INTO `image_category` VALUES ('77', '9');
INSERT INTO `image_category` VALUES ('78', '9');
INSERT INTO `image_category` VALUES ('79', '9');
INSERT INTO `image_category` VALUES ('80', '14');
INSERT INTO `image_category` VALUES ('81', '9');
INSERT INTO `image_category` VALUES ('82', '14');
INSERT INTO `image_category` VALUES ('83', '12');
INSERT INTO `image_category` VALUES ('84', '23');
INSERT INTO `image_category` VALUES ('85', '12');
INSERT INTO `image_category` VALUES ('86', '9');
INSERT INTO `image_category` VALUES ('87', '14');
INSERT INTO `image_category` VALUES ('88', '14');
INSERT INTO `image_category` VALUES ('89', '21');
INSERT INTO `image_category` VALUES ('90', '12');
INSERT INTO `image_category` VALUES ('91', '5');
INSERT INTO `image_category` VALUES ('92', '21');
INSERT INTO `image_category` VALUES ('93', '14');
INSERT INTO `image_category` VALUES ('94', '14');
INSERT INTO `image_category` VALUES ('95', '21');
INSERT INTO `image_category` VALUES ('96', '14');
INSERT INTO `image_category` VALUES ('97', '21');
INSERT INTO `image_category` VALUES ('98', '14');
INSERT INTO `image_category` VALUES ('99', '1');
INSERT INTO `image_category` VALUES ('100', '9');
INSERT INTO `image_category` VALUES ('101', '21');
INSERT INTO `image_category` VALUES ('102', '23');
INSERT INTO `image_category` VALUES ('103', '14');
INSERT INTO `image_category` VALUES ('104', '14');
INSERT INTO `image_category` VALUES ('105', '6');
INSERT INTO `image_category` VALUES ('106', '23');
INSERT INTO `image_category` VALUES ('107', '8');
INSERT INTO `image_category` VALUES ('108', '21');
INSERT INTO `image_category` VALUES ('109', '14');
INSERT INTO `image_category` VALUES ('110', '14');
INSERT INTO `image_category` VALUES ('111', '14');
INSERT INTO `image_category` VALUES ('112', '14');
INSERT INTO `image_category` VALUES ('113', '14');
INSERT INTO `image_category` VALUES ('114', '11');
INSERT INTO `image_category` VALUES ('115', '14');
INSERT INTO `image_category` VALUES ('116', '23');
INSERT INTO `image_category` VALUES ('117', '14');
INSERT INTO `image_category` VALUES ('118', '12');
INSERT INTO `image_category` VALUES ('119', '14');
INSERT INTO `image_category` VALUES ('120', '14');
INSERT INTO `image_category` VALUES ('121', '14');
INSERT INTO `image_category` VALUES ('122', '14');
INSERT INTO `image_category` VALUES ('123', '23');
INSERT INTO `image_category` VALUES ('124', '12');
INSERT INTO `image_category` VALUES ('125', '12');
INSERT INTO `image_category` VALUES ('126', '14');
INSERT INTO `image_category` VALUES ('127', '22');
INSERT INTO `image_category` VALUES ('128', '21');
INSERT INTO `image_category` VALUES ('129', '12');
INSERT INTO `image_category` VALUES ('130', '12');
INSERT INTO `image_category` VALUES ('131', '14');
INSERT INTO `image_category` VALUES ('132', '14');
INSERT INTO `image_category` VALUES ('133', '14');
INSERT INTO `image_category` VALUES ('134', '12');
INSERT INTO `image_category` VALUES ('135', '17');
INSERT INTO `image_category` VALUES ('136', '14');
INSERT INTO `image_category` VALUES ('137', '14');
INSERT INTO `image_category` VALUES ('138', '14');
INSERT INTO `image_category` VALUES ('139', '14');
INSERT INTO `image_category` VALUES ('140', '12');
INSERT INTO `image_category` VALUES ('141', '12');
INSERT INTO `image_category` VALUES ('142', '14');
INSERT INTO `image_category` VALUES ('143', '14');
INSERT INTO `image_category` VALUES ('144', '14');
INSERT INTO `image_category` VALUES ('145', '14');
INSERT INTO `image_category` VALUES ('146', '14');
INSERT INTO `image_category` VALUES ('147', '21');
INSERT INTO `image_category` VALUES ('148', '12');
INSERT INTO `image_category` VALUES ('149', '14');
INSERT INTO `image_category` VALUES ('150', '14');
INSERT INTO `image_category` VALUES ('151', '23');
INSERT INTO `image_category` VALUES ('152', '12');
INSERT INTO `image_category` VALUES ('153', '21');
INSERT INTO `image_category` VALUES ('154', '21');
INSERT INTO `image_category` VALUES ('155', '12');
INSERT INTO `image_category` VALUES ('156', '14');
INSERT INTO `image_category` VALUES ('157', '12');
INSERT INTO `image_category` VALUES ('158', '14');
INSERT INTO `image_category` VALUES ('159', '12');
INSERT INTO `image_category` VALUES ('160', '14');
INSERT INTO `image_category` VALUES ('161', '14');
INSERT INTO `image_category` VALUES ('162', '14');
INSERT INTO `image_category` VALUES ('163', '5');
INSERT INTO `image_category` VALUES ('164', '14');
INSERT INTO `image_category` VALUES ('165', '21');
INSERT INTO `image_category` VALUES ('166', '1');
INSERT INTO `image_category` VALUES ('167', '14');
INSERT INTO `image_category` VALUES ('168', '12');
INSERT INTO `image_category` VALUES ('169', '14');
INSERT INTO `image_category` VALUES ('170', '14');
INSERT INTO `image_category` VALUES ('171', '12');
INSERT INTO `image_category` VALUES ('172', '12');
INSERT INTO `image_category` VALUES ('173', '6');
INSERT INTO `image_category` VALUES ('174', '21');
INSERT INTO `image_category` VALUES ('175', '14');
INSERT INTO `image_category` VALUES ('176', '12');
INSERT INTO `image_category` VALUES ('177', '21');
INSERT INTO `image_category` VALUES ('178', '14');
INSERT INTO `image_category` VALUES ('179', '14');
INSERT INTO `image_category` VALUES ('180', '14');
INSERT INTO `image_category` VALUES ('181', '12');
INSERT INTO `image_category` VALUES ('182', '21');
INSERT INTO `image_category` VALUES ('183', '12');
INSERT INTO `image_category` VALUES ('184', '14');
INSERT INTO `image_category` VALUES ('185', '12');
INSERT INTO `image_category` VALUES ('186', '14');
INSERT INTO `image_category` VALUES ('187', '14');
INSERT INTO `image_category` VALUES ('188', '14');
INSERT INTO `image_category` VALUES ('189', '14');
INSERT INTO `image_category` VALUES ('190', '14');
INSERT INTO `image_category` VALUES ('191', '12');
INSERT INTO `image_category` VALUES ('192', '20');
INSERT INTO `image_category` VALUES ('193', '12');
INSERT INTO `image_category` VALUES ('194', '14');
INSERT INTO `image_category` VALUES ('195', '12');
INSERT INTO `image_category` VALUES ('196', '12');
INSERT INTO `image_category` VALUES ('197', '6');
INSERT INTO `image_category` VALUES ('198', '12');
INSERT INTO `image_category` VALUES ('199', '12');
INSERT INTO `image_category` VALUES ('200', '12');
INSERT INTO `image_category` VALUES ('201', '6');
INSERT INTO `image_category` VALUES ('202', '12');
INSERT INTO `image_category` VALUES ('203', '14');
INSERT INTO `image_category` VALUES ('204', '8');
INSERT INTO `image_category` VALUES ('205', '14');
INSERT INTO `image_category` VALUES ('206', '14');
INSERT INTO `image_category` VALUES ('207', '12');
INSERT INTO `image_category` VALUES ('208', '14');
INSERT INTO `image_category` VALUES ('209', '14');
INSERT INTO `image_category` VALUES ('210', '12');
INSERT INTO `image_category` VALUES ('211', '14');
INSERT INTO `image_category` VALUES ('212', '23');
INSERT INTO `image_category` VALUES ('213', '8');
INSERT INTO `image_category` VALUES ('214', '12');
INSERT INTO `image_category` VALUES ('215', '21');
INSERT INTO `image_category` VALUES ('216', '6');
INSERT INTO `image_category` VALUES ('217', '14');
INSERT INTO `image_category` VALUES ('218', '6');
INSERT INTO `image_category` VALUES ('219', '12');
INSERT INTO `image_category` VALUES ('220', '14');
INSERT INTO `image_category` VALUES ('221', '20');
INSERT INTO `image_category` VALUES ('222', '12');
INSERT INTO `image_category` VALUES ('223', '14');
INSERT INTO `image_category` VALUES ('224', '21');
INSERT INTO `image_category` VALUES ('225', '23');
INSERT INTO `image_category` VALUES ('226', '12');
INSERT INTO `image_category` VALUES ('227', '12');
INSERT INTO `image_category` VALUES ('228', '14');
INSERT INTO `image_category` VALUES ('229', '21');
INSERT INTO `image_category` VALUES ('230', '5');
INSERT INTO `image_category` VALUES ('231', '21');

-- ----------------------------
-- Table structure for interest
-- ----------------------------
DROP TABLE IF EXISTS `interest`;
CREATE TABLE `interest` (
  `user_id` int(10) unsigned NOT NULL,
  `category_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`user_id`,`category_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `interest_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `interest_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of interest
-- ----------------------------
INSERT INTO `interest` VALUES ('7', '2');
INSERT INTO `interest` VALUES ('7', '3');
INSERT INTO `interest` VALUES ('8', '3');
INSERT INTO `interest` VALUES ('7', '6');
INSERT INTO `interest` VALUES ('8', '7');
INSERT INTO `interest` VALUES ('7', '9');
INSERT INTO `interest` VALUES ('10', '9');
INSERT INTO `interest` VALUES ('12', '9');
INSERT INTO `interest` VALUES ('7', '10');
INSERT INTO `interest` VALUES ('8', '10');
INSERT INTO `interest` VALUES ('10', '10');
INSERT INTO `interest` VALUES ('11', '10');
INSERT INTO `interest` VALUES ('12', '10');
INSERT INTO `interest` VALUES ('8', '11');
INSERT INTO `interest` VALUES ('10', '11');
INSERT INTO `interest` VALUES ('12', '11');
INSERT INTO `interest` VALUES ('11', '13');
INSERT INTO `interest` VALUES ('8', '14');
INSERT INTO `interest` VALUES ('7', '15');
INSERT INTO `interest` VALUES ('8', '15');
INSERT INTO `interest` VALUES ('12', '15');
INSERT INTO `interest` VALUES ('7', '17');
INSERT INTO `interest` VALUES ('10', '17');
INSERT INTO `interest` VALUES ('11', '17');
INSERT INTO `interest` VALUES ('7', '20');
INSERT INTO `interest` VALUES ('12', '20');
INSERT INTO `interest` VALUES ('7', '21');
INSERT INTO `interest` VALUES ('12', '22');
INSERT INTO `interest` VALUES ('10', '23');
INSERT INTO `interest` VALUES ('8', '24');

-- ----------------------------
-- Table structure for mark
-- ----------------------------
DROP TABLE IF EXISTS `mark`;
CREATE TABLE `mark` (
  `mark_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `option_mark_name` varchar(32) DEFAULT NULL,
  `manual_mark_name` varchar(32) DEFAULT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `img_id` int(10) unsigned NOT NULL,
  `mark_accuracy` varchar(40) NOT NULL,
  PRIMARY KEY (`user_id`,`img_id`),
  KEY `img_id` (`img_id`),
  CONSTRAINT `mark_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `mark_ibfk_2` FOREIGN KEY (`img_id`) REFERENCES `image` (`img_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mark
-- ----------------------------
INSERT INTO `mark` VALUES ('2017-09-05 22:08:31', '天空;', '', '7', '62', '');
INSERT INTO `mark` VALUES ('2017-09-05 22:08:31', '沙滩;人群;', '', '7', '67', '');
INSERT INTO `mark` VALUES ('2017-09-05 22:08:31', '天空;大海;', '', '7', '68', '');
INSERT INTO `mark` VALUES ('2017-09-05 22:08:31', '大海;', '', '7', '69', '');
INSERT INTO `mark` VALUES ('2017-09-05 22:08:31', '天空;', '', '7', '70', '');
INSERT INTO `mark` VALUES ('2017-09-05 22:08:31', '天空;', '', '7', '77', '');
INSERT INTO `mark` VALUES ('2017-09-05 22:08:31', '天空;', '', '7', '78', '');
INSERT INTO `mark` VALUES ('2017-09-05 22:08:32', '天空;', '', '7', '79', '');
INSERT INTO `mark` VALUES ('2017-09-03 11:11:12', '大海;', '', '7', '81', '');
INSERT INTO `mark` VALUES ('2017-09-03 11:11:12', '天空;', '', '7', '86', '');
INSERT INTO `mark` VALUES ('2017-09-03 11:11:12', '树木;', '', '7', '89', '');
INSERT INTO `mark` VALUES ('2017-09-03 11:11:12', '云;', '', '7', '92', '');
INSERT INTO `mark` VALUES ('2017-09-03 11:11:12', '高楼;', '', '7', '95', '');
INSERT INTO `mark` VALUES ('2017-09-03 11:11:12', '云;', '', '7', '97', '');
INSERT INTO `mark` VALUES ('2017-09-03 11:11:12', '人群;', '', '7', '100', '');
INSERT INTO `mark` VALUES ('2017-09-03 10:59:25', '人群;灯;', '', '7', '101', '0.54');
INSERT INTO `mark` VALUES ('2017-09-03 11:11:12', '大厅;', '', '7', '105', '');
INSERT INTO `mark` VALUES ('2017-09-03 11:11:12', '天空;', '', '7', '108', '');
INSERT INTO `mark` VALUES ('2017-09-03 10:59:25', '房屋;', '', '7', '128', '0.87');
INSERT INTO `mark` VALUES ('2017-09-03 10:59:25', '建筑;', '', '7', '135', '0.89');
INSERT INTO `mark` VALUES ('2017-09-03 10:59:25', '路;', '', '7', '147', '');
INSERT INTO `mark` VALUES ('2017-09-03 10:59:25', '房屋;树木;', '', '7', '153', '0.68');
INSERT INTO `mark` VALUES ('2017-09-03 10:59:25', '天空;', '房屋;', '7', '154', '0.54');
INSERT INTO `mark` VALUES ('2017-09-03 10:59:25', '卡通;', '', '7', '165', '');
INSERT INTO `mark` VALUES ('2017-09-03 11:11:12', '树木;', '', '7', '173', '');
INSERT INTO `mark` VALUES ('2017-09-03 10:59:25', '树木;门;', '', '7', '174', '0.67');
INSERT INTO `mark` VALUES ('2017-09-03 21:44:09', '天空;房屋;云;', '', '7', '177', '');
INSERT INTO `mark` VALUES ('2017-09-03 21:44:09', '天空;树木;路;云;', '', '7', '182', '');
INSERT INTO `mark` VALUES ('2017-09-03 21:44:09', '', '医生;护士;', '7', '192', '');
INSERT INTO `mark` VALUES ('2017-09-03 21:44:09', '天空;高楼;树木;', '医院;人群;', '7', '197', '');
INSERT INTO `mark` VALUES ('2017-09-03 21:44:09', '天空;高楼;', '医院;人群;', '7', '201', '');
INSERT INTO `mark` VALUES ('2017-09-03 21:44:09', '楼阁;树木;门;', '医院;', '7', '215', '');
INSERT INTO `mark` VALUES ('2017-09-03 21:44:09', '灯;', '', '7', '216', '');
INSERT INTO `mark` VALUES ('2017-09-03 21:44:09', '灯;大厅;人群;', '窗户;', '7', '218', '');
INSERT INTO `mark` VALUES ('2017-09-03 21:44:09', '', '护士;', '7', '221', '');
INSERT INTO `mark` VALUES ('2017-09-03 21:44:09', '楼阁;门;天空;', '医院;', '7', '224', '');
INSERT INTO `mark` VALUES ('2017-09-05 22:08:32', '天空;云;', '石头;花草;', '7', '231', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:19', '餐厅;', '', '8', '1', '0.23');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:19', '桌子;', '', '8', '2', '0.23');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:38', '椅子;', '', '8', '3', '0.23');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:19', '椅子;', '', '8', '4', '0.23');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:19', '菜品;盘子;餐厅;碗;', '', '8', '5', '0.45');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:19', '灯;', '', '8', '6', '0.24');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:53', '灯;', '', '8', '7', '0.45');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:53', '灯;', '', '8', '8', '0.36');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:38', '椅子;', '', '8', '9', '0.45');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:38', '店铺;', '', '8', '10', '0.24');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:38', '灯;', '', '8', '11', '0.78');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:38', '桌子;', '', '8', '12', '0.89');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:38', '餐厅;', '', '8', '13', '0.78');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:19', '菜品;', '', '8', '14', '0.87');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:19', '椅子;', '', '8', '15', '0.87');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:38', '灯;', '', '8', '16', '0.91');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:38', '椅子;', '', '8', '17', '0.54');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:19', '菜品;盘子;', '', '8', '18', '0.65');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:38', '桌子;', '', '8', '19', '0.37');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:53', '桌子;', '', '8', '20', '0.54');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:53', '建筑;', '', '8', '21', '0.78');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:53', '汽车;', '', '8', '22', '0.45');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:53', '天空;', '', '8', '23', '0.65');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:53', '建筑;', '', '8', '24', '0.54');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:53', '汽车;', '', '8', '26', '0.45');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:53', '路;', '', '8', '36', '0.34');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:53', '大厅;', '', '8', '39', '0.45');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:38', '天空;广场;', '', '8', '48', '0.45');
INSERT INTO `mark` VALUES ('2017-09-02 21:07:19', '机场;', '', '8', '114', '0.68');
INSERT INTO `mark` VALUES ('2017-09-05 23:53:29', '房屋;', '', '8', '167', '');
INSERT INTO `mark` VALUES ('2017-09-05 23:53:29', '房屋;建筑;', '', '8', '169', '');
INSERT INTO `mark` VALUES ('2017-09-05 22:57:51', '椅子;', '', '8', '187', '');
INSERT INTO `mark` VALUES ('2017-09-05 22:57:51', '桌子;', '', '8', '188', '');
INSERT INTO `mark` VALUES ('2017-09-05 22:57:51', '家具;', '', '8', '189', '');
INSERT INTO `mark` VALUES ('2017-09-05 22:57:51', '桌子;', '', '8', '190', '');
INSERT INTO `mark` VALUES ('2017-09-05 22:57:51', '天空;', '', '8', '194', '');
INSERT INTO `mark` VALUES ('2017-09-05 23:53:29', '天空;云;', '', '8', '203', '');
INSERT INTO `mark` VALUES ('2017-09-05 22:57:51', '合影;', '', '8', '205', '');
INSERT INTO `mark` VALUES ('2017-09-05 23:53:29', '房屋;树木;高楼;', '大楼;窗户;', '8', '206', '');
INSERT INTO `mark` VALUES ('2017-09-05 23:53:29', '教室;', '', '8', '208', '');
INSERT INTO `mark` VALUES ('2017-09-05 23:53:29', '椅子;', '', '8', '209', '');
INSERT INTO `mark` VALUES ('2017-09-05 23:53:29', '家具;', '', '8', '220', '');
INSERT INTO `mark` VALUES ('2017-09-05 23:53:29', '灯;', '', '8', '223', '');
INSERT INTO `mark` VALUES ('2017-09-05 22:57:51', '天空;水;', '河;人群;', '8', '228', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '人群;街道;房屋;', '', '9', '142', '0.67');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '夜晚;', '', '9', '160', '0.69');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '夜晚;', '', '9', '161', '0.64');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '夜晚;', '', '9', '162', '0.45');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '卡通;', '', '9', '163', '0.24');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '烟火;夜晚;', '', '9', '164', '0.35');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '烟火;', '', '9', '165', '0.68');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '夜晚;', '', '9', '166', '0.67');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '房屋;高楼;', '', '9', '167', '0.69');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '路;', '', '9', '168', '0.34');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '建筑;', '', '9', '169', '0.35');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '汽车;天空;', '', '9', '170', '0.36');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '天空;房屋;草地;树木;', '', '9', '171', '0.34');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '房屋;草地;', '', '9', '172', '0.54');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '天空;都市远景;树木;', '', '9', '173', '0.56');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '门;天空;', '', '9', '174', '0.57');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '树木;天空;云;', '', '9', '175', '0.68');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '树木;建筑;天空;', '', '9', '176', '0.67');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '房屋;', '', '9', '177', '0.57');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '草地;', '', '9', '178', '0.8');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '房屋;', '', '9', '179', '0.69');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '门;', '', '9', '180', '0.2');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '路;', '', '9', '181', '0.5');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '天空;', '', '9', '182', '0.3');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '天空;', '', '9', '183', '0.4');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '建筑;', '', '9', '184', '0.5');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '水;', '', '9', '185', '0.7');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '云;', '', '9', '186', '0.3');
INSERT INTO `mark` VALUES ('2017-09-02 21:11:26', '椅子;', '', '9', '187', '0.4');
INSERT INTO `mark` VALUES ('2017-09-02 21:11:26', '灯;', '', '9', '188', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:10:21', '客厅;', '', '9', '189', '0.8');
INSERT INTO `mark` VALUES ('2017-09-02 21:10:37', '桌子;', '', '9', '190', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:10:21', '汽车;', '', '9', '191', '0.4');
INSERT INTO `mark` VALUES ('2017-09-02 21:10:21', '家具;', '', '9', '192', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:11:26', '走廊;', '', '9', '193', '0.8');
INSERT INTO `mark` VALUES ('2017-09-02 21:11:26', '云;', '', '9', '194', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:10:37', '走廊;', '', '9', '195', '0.5');
INSERT INTO `mark` VALUES ('2017-09-02 21:11:26', '树木;', '', '9', '196', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:11:26', '高楼;', '', '9', '197', '0.6');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '灯;', '', '9', '198', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:11:26', '灯;', '', '9', '199', '0.7');
INSERT INTO `mark` VALUES ('2017-09-02 21:11:26', '椅子;', '', '9', '200', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:11:26', '高楼;', '', '9', '201', '0.4');
INSERT INTO `mark` VALUES ('2017-09-02 21:11:26', '椅子;', '', '9', '202', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:11:26', '云;', '', '9', '203', '0.5');
INSERT INTO `mark` VALUES ('2017-09-02 21:10:37', '房屋;', '', '9', '204', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:11:26', '婚纱;', '', '9', '205', '0.8');
INSERT INTO `mark` VALUES ('2017-09-02 21:10:37', '树木;', '', '9', '206', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:13:26', '房屋;', '', '9', '207', '0.7');
INSERT INTO `mark` VALUES ('2017-09-02 21:10:37', '桌子;', '', '9', '208', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:10:37', '椅子;', '', '9', '209', '0.7');
INSERT INTO `mark` VALUES ('2017-09-02 21:10:21', '灯;', '', '9', '210', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:10:21', '婚纱;', '', '9', '211', '0.7');
INSERT INTO `mark` VALUES ('2017-09-02 21:11:26', '桌子;', '', '9', '212', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:11:26', '房屋;', '', '9', '213', '0.7');
INSERT INTO `mark` VALUES ('2017-09-02 21:10:21', '灯;', '', '9', '214', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:11:26', '树木;', '', '9', '215', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:10:37', '大厅;', '', '9', '216', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:11:26', '树木;', '', '9', '217', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:10:37', '大厅;', '', '9', '218', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:11:26', '灯;', '', '9', '219', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:10:37', '客厅;', '', '9', '220', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:10:21', '家具;', '', '9', '221', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:11:26', '灯;', '', '9', '222', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:10:37', '灯;', '', '9', '223', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:10:21', '树木;', '', '9', '224', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:11:26', '桌子;', '', '9', '225', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:11:26', '汽车;', '', '9', '226', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:15:57', '灯;', '', '10', '11', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:15:57', '桌子;', '', '10', '19', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:15:38', '建筑;', '', '10', '21', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:15:38', '沙滩;', '', '10', '62', '0.7');
INSERT INTO `mark` VALUES ('2017-09-02 21:15:38', '沙滩;', '', '10', '67', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:15:38', '大海;', '', '10', '68', '0.7');
INSERT INTO `mark` VALUES ('2017-09-02 21:15:38', '大海;', '', '10', '69', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:15:38', '天空;大海;', '', '10', '70', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:15:38', '大海;天空;', '', '10', '77', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:15:38', '天空;大海;', '', '10', '78', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:15:38', '大海;天空;', '', '10', '79', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:15:57', '大海;', '', '10', '81', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:15:57', '船;', '', '10', '84', '0.8');
INSERT INTO `mark` VALUES ('2017-09-02 21:15:57', '沙滩;天空;', '', '10', '86', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:15:38', '天空;人群;', '', '10', '100', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:15:57', '汽车;', '', '10', '102', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:15:57', '灯;', '', '10', '106', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:15:38', '机场;', '', '10', '114', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:15:57', '天空;船;', '', '10', '116', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:15:57', '灯;', '', '10', '123', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:15:38', '建筑;', '', '10', '135', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:15:57', '夜晚;灯;', '', '10', '151', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:15:38', '桌子;', '', '10', '212', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:15:38', '桌子;', '', '10', '225', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:17:58', '餐厅;', '', '11', '1', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:17:58', '桌子;', '', '11', '2', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:17:58', '椅子;', '', '11', '3', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:17:58', '椅子;', '', '11', '4', '0.7');
INSERT INTO `mark` VALUES ('2017-09-02 21:17:58', '菜品;', '', '11', '5', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:17:58', '灯;', '', '11', '6', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:17:58', '灯;店铺;', '', '11', '7', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:17:58', '灯;店铺;', '', '11', '8', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:17:58', '椅子;', '', '11', '9', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:17:58', '店铺;', '', '11', '10', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:18:25', '灯;', '', '11', '11', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:17:58', '桌子;', '', '11', '12', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:17:58', '餐厅;', '', '11', '13', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:17:58', '菜品;', '', '11', '14', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:17:58', '椅子;', '', '11', '15', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:17:58', '灯;', '', '11', '16', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:17:58', '桌子;', '', '11', '17', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:17:58', '餐厅;', '', '11', '18', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:18:25', '桌子;', '', '11', '19', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:17:58', '椅子;', '', '11', '20', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:18:25', '天空;', '', '11', '21', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:18:40', '天空;', '', '11', '22', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:18:25', '天空;', '', '11', '23', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:18:40', '天空;', '', '11', '24', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:18:25', '建筑;', '', '11', '25', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:18:25', '汽车;', '', '11', '26', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:18:25', '广场;', '', '11', '27', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:18:25', '灯;', '', '11', '28', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:18:25', '汽车;', '', '11', '29', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:18:25', '房屋;', '', '11', '30', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:18:25', '房屋;', '', '11', '31', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:18:25', '舞台;', '', '11', '32', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:18:25', '大厅;', '', '11', '33', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:18:25', '建筑;', '', '11', '34', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:18:25', '房屋;', '', '11', '35', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:18:25', '路;', '', '11', '36', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:18:25', '房屋;', '', '11', '37', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:18:25', '灯;', '', '11', '38', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:18:40', '人群;', '', '11', '39', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:18:25', '建筑;', '', '11', '40', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:18:40', '天空;', '', '11', '43', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:18:40', '夜晚;', '', '11', '46', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:19:08', '天空;', '', '11', '47', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:18:40', '天空;', '', '11', '48', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:18:40', '截图;', '', '11', '52', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:18:40', '天空;', '', '11', '57', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:18:40', '天空;', '', '11', '59', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:17:58', '游乐场;', '', '11', '135', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '菜品;', '', '12', '1', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:56', '客厅;', '', '12', '2', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '灯;', '', '12', '3', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:56', '桌子;', '', '12', '4', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '菜品;', '', '12', '5', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '椅子;', '', '12', '6', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '灯;', '', '12', '7', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '灯;', '', '12', '8', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '桌子;', '', '12', '9', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:10', '门;店铺;', '', '12', '10', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:56', '酒店;', '', '12', '11', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '灯;', '', '12', '12', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '菜品;', '', '12', '13', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '餐厅;', '', '12', '14', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '桌子;', '', '12', '15', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '桌子;', '', '12', '16', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '桌子;', '', '12', '17', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '菜品;', '', '12', '18', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:56', '客厅;', '', '12', '19', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '桌子;', '', '12', '20', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:27', '天空;', '', '12', '21', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:10', '天空;', '', '12', '22', '0.57');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:27', '汽车;', '', '12', '23', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:27', '天空;', '', '12', '24', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:56', '广场;', '', '12', '25', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:27', '天空;', '', '12', '26', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:10', '天空;', '', '12', '27', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:56', '汽车;', '', '12', '28', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:56', '天空;', '', '12', '29', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:10', '人群;', '', '12', '30', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:10', '天空;', '', '12', '31', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:56', '灯;', '', '12', '32', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:10', '人群;', '', '12', '33', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:10', '广场;', '', '12', '34', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:56', '房屋;', '', '12', '35', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:27', '汽车;', '', '12', '36', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:26', '天空;', '', '12', '37', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:10', '车站;', '', '12', '38', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:27', '人群;', '', '12', '39', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:10', '天空;', '', '12', '40', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:39', '树木;', '', '12', '42', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:39', '天空;', '', '12', '43', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:39', '天空;', '', '12', '44', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:27', '树木;', '', '12', '45', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:39', '夜晚;', '', '12', '46', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:27', '树木;', '', '12', '47', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:39', '天空;', '', '12', '49', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:39', '天空;', '', '12', '51', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:39', '截图;', '', '12', '52', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:39', '树木;', '', '12', '55', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:27', '树木;', '', '12', '58', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:39', '天空;', '', '12', '59', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:21:39', '树木;', '', '12', '60', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '天空;', '', '12', '62', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '天空;', '', '12', '67', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '天空;', '', '12', '68', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '天空;', '', '12', '69', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '天空;', '', '12', '70', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '天空;', '', '12', '77', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '大海;', '', '12', '78', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '天空;', '', '12', '79', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '天空;', '', '12', '81', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '沙滩;', '', '12', '86', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '天空;', '', '12', '100', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '机场;', '', '12', '114', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '灯;', '', '12', '127', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '家具;', '', '12', '192', '');
INSERT INTO `mark` VALUES ('2017-09-02 21:20:42', '家具;', '', '12', '221', '');

-- ----------------------------
-- Table structure for oauth
-- ----------------------------
DROP TABLE IF EXISTS `oauth`;
CREATE TABLE `oauth` (
  `oauth_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `oauth_token` varchar(32) NOT NULL,
  `oauth_token_expiration` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `user_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`oauth_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `oauth_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth
-- ----------------------------
INSERT INTO `oauth` VALUES ('2', '9504124f6dc8ab637f0c01bb8ed0797c', '2017-09-13 10:44:25', '7');
INSERT INTO `oauth` VALUES ('3', '72441c1ce0403bf31887447116af3177', '2017-09-12 23:49:19', '8');
INSERT INTO `oauth` VALUES ('4', '826c47a5943c44ef457019e5a01638d1', '2017-09-11 21:04:32', '9');
INSERT INTO `oauth` VALUES ('5', 'f6d51bf9cb4aeb4c06aba87bbc8f50c9', '2017-09-11 20:53:45', '10');
INSERT INTO `oauth` VALUES ('6', 'db252869289c7f59f4af3f8a0dd1d070', '2017-09-11 20:54:45', '11');
INSERT INTO `oauth` VALUES ('7', '2c25bcf149ab78590e2d9336a72e0d57', '2017-09-11 20:55:26', '12');
INSERT INTO `oauth` VALUES ('8', '804c7b8df78c927d59fa8da6f1f76e1d', '2017-09-11 20:56:08', '13');
INSERT INTO `oauth` VALUES ('9', '4ffdb114166c531cc77bd70b151e271d', '2017-09-11 20:56:55', '14');
INSERT INTO `oauth` VALUES ('10', '051aa928465548057796f3b5243da206', '2017-09-11 20:57:39', '15');

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `task_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `task_img_amount` int(11) NOT NULL,
  `task_start_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `task_iscommit` int(101) NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`task_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `task_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task
-- ----------------------------
INSERT INTO `task` VALUES ('5', '10', '2017-09-02 20:53:21', '1', '7');
INSERT INTO `task` VALUES ('6', '10', '2017-09-02 20:59:24', '1', '7');
INSERT INTO `task` VALUES ('7', '10', '2017-09-02 21:01:06', '0', '7');
INSERT INTO `task` VALUES ('8', '10', '2017-09-02 21:01:11', '0', '7');
INSERT INTO `task` VALUES ('9', '10', '2017-09-02 21:01:16', '0', '7');
INSERT INTO `task` VALUES ('10', '10', '2017-09-02 21:06:57', '1', '8');
INSERT INTO `task` VALUES ('11', '10', '2017-09-02 21:07:21', '1', '8');
INSERT INTO `task` VALUES ('12', '10', '2017-09-02 21:07:39', '1', '8');
INSERT INTO `task` VALUES ('13', '10', '2017-09-02 21:08:20', '0', '8');
INSERT INTO `task` VALUES ('14', '10', '2017-09-02 21:10:08', '1', '9');
INSERT INTO `task` VALUES ('15', '10', '2017-09-02 21:10:23', '1', '9');
INSERT INTO `task` VALUES ('16', '20', '2017-09-02 21:10:41', '1', '9');
INSERT INTO `task` VALUES ('17', '30', '2017-09-02 21:11:31', '1', '9');
INSERT INTO `task` VALUES ('18', '30', '2017-09-02 21:14:56', '0', '10');
INSERT INTO `task` VALUES ('19', '30', '2017-09-02 21:15:05', '1', '10');
INSERT INTO `task` VALUES ('20', '10', '2017-09-02 21:15:42', '1', '10');
INSERT INTO `task` VALUES ('21', '20', '2017-09-02 21:17:29', '1', '11');
INSERT INTO `task` VALUES ('22', '20', '2017-09-02 21:18:03', '1', '11');
INSERT INTO `task` VALUES ('23', '10', '2017-09-02 21:18:27', '1', '11');
INSERT INTO `task` VALUES ('24', '30', '2017-09-02 21:18:54', '1', '11');
INSERT INTO `task` VALUES ('25', '30', '2017-09-02 21:20:02', '1', '12');
INSERT INTO `task` VALUES ('26', '10', '2017-09-02 21:20:45', '1', '12');
INSERT INTO `task` VALUES ('27', '10', '2017-09-02 21:20:59', '1', '12');
INSERT INTO `task` VALUES ('28', '10', '2017-09-02 21:21:16', '1', '12');
INSERT INTO `task` VALUES ('29', '10', '2017-09-02 21:21:28', '1', '12');
INSERT INTO `task` VALUES ('30', '10', '2017-09-02 22:48:02', '0', '9');
INSERT INTO `task` VALUES ('31', '10', '2017-09-03 11:10:59', '1', '7');
INSERT INTO `task` VALUES ('32', '10', '2017-09-04 21:43:21', '0', '7');
INSERT INTO `task` VALUES ('33', '10', '2017-09-05 22:05:18', '1', '7');
INSERT INTO `task` VALUES ('34', '10', '2017-09-05 22:07:03', '0', '7');
INSERT INTO `task` VALUES ('35', '10', '2017-09-05 22:55:11', '1', '8');
INSERT INTO `task` VALUES ('36', '10', '2017-09-05 22:56:45', '0', '8');
INSERT INTO `task` VALUES ('37', '10', '2017-09-05 23:51:11', '1', '8');
INSERT INTO `task` VALUES ('38', '10', '2017-09-05 23:52:27', '0', '8');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `password` varchar(64) NOT NULL,
  `sex` varchar(2) DEFAULT NULL,
  `avatar_url` varchar(255) DEFAULT 'http://122.112.232.97/ImageClassify/avatar/ic_user_default_avatar.png',
  `tel_num` varchar(11) DEFAULT NULL,
  `integral` int(11) NOT NULL DEFAULT '0',
  `accuracy` varchar(40) DEFAULT NULL,
  `is_frozen` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('7', '哈哈', 'e10adc3949ba59abbe56e057f20f883e', '男', 'http://122.112.232.97/ImageClassify/avatar/15161132971/07a488100c023a60b057a08cd30f5202.jpg', '15161132971', '18', '0.11324324324324325', '1');
INSERT INTO `user` VALUES ('8', '小鸿', 'e10adc3949ba59abbe56e057f20f883e', '男', 'http://122.112.232.97/ImageClassify/avatar/15161132977/e1f09d9f678d048a7733e1fe0580903c.jpg', '15161132977', '7', '0.34318181818181803', '0');
INSERT INTO `user` VALUES ('9', '潘颖慧', 'e10adc3949ba59abbe56e057f20f883e', '女', 'http://122.112.232.97/ImageClassify/avatar/15161132978/6bae28ed5be1ee6ca8be5a458d2195ca.jpg', '15161132978', '4', '0.3404411764705882', '0');
INSERT INTO `user` VALUES ('10', '吕伟伟', 'e10adc3949ba59abbe56e057f20f883e', '男', 'http://122.112.232.97/ImageClassify/avatar/15161171093/9b00d3f90a8093db7def03b2568278dc.jpg', '15161171093', '2', '0.09166666666666667', '0');
INSERT INTO `user` VALUES ('11', '黄丽婷', 'e10adc3949ba59abbe56e057f20f883e', '女', 'http://122.112.232.97/ImageClassify/avatar/15161171099/8e6fe35f0f67d692098c28cdaf148ef4.jpg', '15161171099', '1', '0.014583333333333332', '0');
INSERT INTO `user` VALUES ('12', '孙小莉', 'e10adc3949ba59abbe56e057f20f883e', '女', 'http://122.112.232.97/ImageClassify/avatar/15161171042/40440bf49788faa938f4cbc14d6a515a.jpg', '15161171042', '4', '0.00838235294117647', '0');
INSERT INTO `user` VALUES ('13', '刘璐', 'e10adc3949ba59abbe56e057f20f883e', '女', 'http://122.112.232.97/ImageClassify/avatar/15161171025/699b24e8fe596cc7d18fdf41c54b8c6b.jpg', '15161171025', '5', '0.0', '0');
INSERT INTO `user` VALUES ('14', '于跃', 'e10adc3949ba59abbe56e057f20f883e', '男', 'http://122.112.232.97/ImageClassify/avatar/13913495389/9b2a38dd932ffb9055b48f31290fa5a9.jpg', '13913495389', '6', '0.0', '0');
INSERT INTO `user` VALUES ('15', '杨辉', 'e10adc3949ba59abbe56e057f20f883e', '男', 'http://122.112.232.97/ImageClassify/avatar/13913495356/56db96397416eada782d5f30fca3d091.jpg', '13913495356', '7', '0.0', '0');

-- ----------------------------
-- Table structure for zip
-- ----------------------------
DROP TABLE IF EXISTS `zip`;
CREATE TABLE `zip` (
  `zip_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `zip_name` varchar(40) NOT NULL,
  `zip_url` varchar(50) NOT NULL,
  `upload_time` timestamp NULL DEFAULT NULL,
  `old_zip_name` varchar(100) NOT NULL,
  `isZip` int(11) NOT NULL,
  `isClassify` int(11) NOT NULL,
  `unzip_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`zip_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of zip
-- ----------------------------
INSERT INTO `zip` VALUES ('1', '7741815183c0c41a908b06b9130f9a58.zip', './zip', '2017-09-02 11:53:53', '餐厅.zip', '1', '1', '2017-09-02 12:39:18');
INSERT INTO `zip` VALUES ('2', '8497a1fd22aa855f8af0125ebce3aec2.zip', './zip', '2017-09-02 11:55:27', '车站.zip', '1', '1', '2017-09-02 12:42:34');
INSERT INTO `zip` VALUES ('3', '7340889f6f0d132e76b905013ac4e2d4.zip', './zip', '2017-09-02 11:58:15', '广场.zip', '1', '1', '2017-09-02 12:44:34');
INSERT INTO `zip` VALUES ('4', '6898834022e99a8ff43c73b0eec8f18f.zip', './zip', '2017-09-02 11:58:23', '海滩.zip', '1', '1', '2017-09-02 15:41:37');
INSERT INTO `zip` VALUES ('5', 'efd4374805e536aa1a69e764735282d7.zip', './zip', '2017-09-02 11:58:32', '红树林.zip', '1', '1', '2017-09-02 15:41:40');
INSERT INTO `zip` VALUES ('6', '2c034854d520b6013504c96fbc0626c6.zip', './zip', '2017-09-02 11:59:05', '机场.zip', '1', '1', '2017-09-02 15:44:11');
INSERT INTO `zip` VALUES ('7', '19d32de74be932a263a1ec1e7469ec4c.zip', './zip', '2017-09-02 12:27:38', '商场.zip', '1', '1', '2017-09-02 15:44:13');
INSERT INTO `zip` VALUES ('8', '839a2b3f987f9d80b947a9fd4f09228f.zip', './zip', '2017-09-02 12:27:42', '街道.zip', '1', '1', '2017-09-02 15:44:15');
INSERT INTO `zip` VALUES ('9', '215aad82d7aa3a24fae36cf3b0dc4382.zip', './zip', '2017-09-02 12:28:01', '星空.zip', '1', '1', '2017-09-02 15:57:32');
INSERT INTO `zip` VALUES ('10', '00dc232ffa5e87b51f5be71b660a4dc8.zip', './zip', '2017-09-02 12:28:23', '学校.zip', '1', '1', '2017-09-02 15:57:50');
INSERT INTO `zip` VALUES ('11', 'b25807a7d96b98deb54971d61aa657f4.zip', './zip', '2017-09-02 12:28:39', '医院.zip', '1', '1', '2017-09-02 20:23:16');
INSERT INTO `zip` VALUES ('12', '1b687ebb8781e79a6b6841dd668ee39d.zip', './zip', '2017-09-02 12:29:09', '世界之窗.zip', '1', '0', '2017-09-05 23:08:00');
INSERT INTO `zip` VALUES ('13', '327d4acd6ea486c9e6d8303e53209d8e.zip', './zip', '2017-09-02 12:29:24', '长城.zip', '0', '0', null);
INSERT INTO `zip` VALUES ('15', 'de9a85eb21801ef917afe20e2d19d0da.zip', './zip', '2017-09-02 12:39:09', '餐厅.zip', '0', '0', null);
INSERT INTO `zip` VALUES ('16', '2233c86883f8fbb7ae1e7c12b7764da2.zip', './zip', '2017-09-04 10:41:38', '红树林.zip', '1', '1', '2017-09-04 10:41:50');
INSERT INTO `zip` VALUES ('18', '29f375ecec1ad8ddb0cc9774ffe7dd4e.zip', './zip', '2017-09-05 23:16:05', '测试文件.zip', '1', '0', '2017-09-05 23:18:39');
INSERT INTO `zip` VALUES ('19', '869ad6c77cc963bf3d6cb65f93a19ef0.zip', './zip', '2017-09-05 23:20:51', '测试文件.zip', '1', '1', '2017-09-05 23:21:02');
INSERT INTO `zip` VALUES ('20', '63632054209c25131935ba68bc0f427c.zip', './zip', '2017-09-05 23:26:58', '63632054209c25131935ba68bc0f427c.zip', '0', '0', null);
INSERT INTO `zip` VALUES ('21', 'bf6b56994bb242a39ac0c1d012139e4b.zip', './zip', '2017-09-05 23:56:31', '测试文件.zip', '1', '1', '2017-09-05 23:56:44');

-- ----------------------------
-- View structure for image_composition
-- ----------------------------
DROP VIEW IF EXISTS `image_composition`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `image_composition` AS select count(0) AS `amount`,`image`.`img_id` AS `img_id`,`image`.`img_machine_tag_label` AS `img_machine_tag_label`,`image`.`img_path` AS `img_path`,`image`.`img_name` AS `img_name`,`mark`.`user_id` AS `user_id`,`zip`.`upload_time` AS `upload_time` from ((`image` left join `mark` on((`image`.`img_id` = `mark`.`img_id`))) join `zip` on((`zip`.`zip_id` = `image`.`zip_id`))) group by `image`.`img_id` order by count(0) desc ;

-- ----------------------------
-- View structure for image_mark
-- ----------------------------
DROP VIEW IF EXISTS `image_mark`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `image_mark` AS select `image`.`img_machine_tag_label` AS `img_machine_tag_label`,`image`.`img_path` AS `img_path`,`image`.`img_name` AS `img_name`,`mark`.`user_id` AS `user_id`,`mark`.`img_id` AS `img_id`,`mark`.`option_mark_name` AS `option_mark_name`,`mark`.`manual_mark_name` AS `manual_mark_name`,`mark`.`mark_accuracy` AS `mark_accuracy`,`image`.`img_is_finish` AS `img_is_finish` from (`mark` join `image` on((`mark`.`img_id` = `image`.`img_id`))) ;

-- ----------------------------
-- View structure for recommend_img
-- ----------------------------
DROP VIEW IF EXISTS `recommend_img`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `recommend_img` AS select `mark`.`user_id` AS `user_id`,`image_category`.`category_id` AS `category_id`,count(0) AS `preference` from (`mark` join `image_category`) where (`mark`.`img_id` = `image_category`.`img_id`) group by `mark`.`user_id` ;

-- ----------------------------
-- View structure for user_contribute_img
-- ----------------------------
DROP VIEW IF EXISTS `user_contribute_img`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `user_contribute_img` AS select `contribute_img`.`user_id` AS `user_id`,`contribute_img`.`upload_img_time` AS `upload_img_time`,`contribute_img`.`storage_path` AS `storage_path`,`contribute_img`.`upload_img_review_status` AS `upload_img_review_status`,`contribute_img`.`id` AS `id`,`user`.`username` AS `username`,`user`.`tel_num` AS `tel_num` from (`contribute_img` join `user` on((`contribute_img`.`user_id` = `user`.`user_id`))) ;
