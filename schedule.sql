/*
Navicat MySQL Data Transfer

Source Server         : 云服务器
Source Server Version : 80031
Source Host           : 49.234.210.20:3306
Source Database       : schedule

Target Server Type    : MYSQL
Target Server Version : 80031
File Encoding         : 65001

Date: 2023-03-05 21:44:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `employeeId` varchar(255) NOT NULL,
  `storeId` varchar(255) DEFAULT NULL,
  `employeeName` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `position` varchar(255) NOT NULL,
  `phone` int DEFAULT NULL,
  `root` int NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`employeeId`),
  KEY `FK_Relationship_1` (`storeId`),
  CONSTRAINT `FK_Relationship_1` FOREIGN KEY (`storeId`) REFERENCES `store` (`storeId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('1', '1', 'zk', '123@163.com', '店员', '123456789', '2', '123', '123');
INSERT INTO `employee` VALUES ('10', '3', 'E', 'e@163.com', '店员', null, '2', 'E', 'E');
INSERT INTO `employee` VALUES ('10000001', '1', 'zz', '123123@163.com', '电员', '1242343', '1', '111', '111');
INSERT INTO `employee` VALUES ('11', '3', 'F', 'f@163.com', '店员', null, '2', 'F', 'F');
INSERT INTO `employee` VALUES ('12', '3', 'G', 'g@163.com', '店员', null, '2', 'G', 'G');
INSERT INTO `employee` VALUES ('13', '3', 'H', 'h@163.com', '店员', null, '2', 'H', 'H');
INSERT INTO `employee` VALUES ('14', '3', 'I', 'i@163.com', '店员', null, '2', 'I', 'I');
INSERT INTO `employee` VALUES ('15', '3', 'J', 'j@163.com', '店员', null, '2', 'J', 'J');
INSERT INTO `employee` VALUES ('16', '3', 'K', 'k@163.com', '店员', null, '2', 'K', 'K');
INSERT INTO `employee` VALUES ('2', '1', 'wjl', '456@163.com', '店员', '987654321', '1', 'wjl', 'wjl');
INSERT INTO `employee` VALUES ('3', '1', 'zzt', '789@163.com', '店员', '135792468', '1', '', '');
INSERT INTO `employee` VALUES ('4', '1', 'yry', '135@163.com', '店员', '246813579', '1', '', '');
INSERT INTO `employee` VALUES ('5', '2', 'test', '246@163.com', '小组长', '75318642', '1', 'test', 'test');
INSERT INTO `employee` VALUES ('6', '3', 'A', 'a@163.com', '门店经理', null, '2', 'A', 'A');
INSERT INTO `employee` VALUES ('7', '3', 'B', 'b@163.com', '副经理', null, '2', 'B', 'B');
INSERT INTO `employee` VALUES ('8', '3', 'C', 'c@163.com', '小组长', null, '2', 'C', 'C');
INSERT INTO `employee` VALUES ('9', '3', 'D', 'd@163.com', '小组长', null, '2', 'D', 'D');

-- ----------------------------
-- Table structure for preference
-- ----------------------------
DROP TABLE IF EXISTS `preference`;
CREATE TABLE `preference` (
  `preferId` varchar(255) NOT NULL,
  `employeeId` varchar(255) NOT NULL,
  `preferType` varchar(255) NOT NULL,
  `preferValue` varchar(255) NOT NULL,
  PRIMARY KEY (`preferId`),
  KEY `FK_Relationship_2` (`employeeId`),
  CONSTRAINT `FK_Relationship_2` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`employeeId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of preference
-- ----------------------------

-- ----------------------------
-- Table structure for rule
-- ----------------------------
DROP TABLE IF EXISTS `rule`;
CREATE TABLE `rule` (
  `ruleId` varchar(255) NOT NULL,
  `storeId` varchar(255) DEFAULT NULL,
  `ruleType` varchar(255) NOT NULL,
  `ruleValue` varchar(255) NOT NULL,
  PRIMARY KEY (`ruleId`),
  KEY `FK_Relationship_3` (`storeId`),
  CONSTRAINT `FK_Relationship_3` FOREIGN KEY (`storeId`) REFERENCES `store` (`storeId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of rule
-- ----------------------------
INSERT INTO `rule` VALUES ('1', null, '开店规则', '{\"before\":\"0.5\",\"fomula\":\"size/50\"}');
INSERT INTO `rule` VALUES ('2', null, '关店规则', '{\"after\":\"2\",\"count\":\"2\",\"fomula\":\"size/30\"}');
INSERT INTO `rule` VALUES ('3', null, '客流规则', '3.8');

-- ----------------------------
-- Table structure for scheduling
-- ----------------------------
DROP TABLE IF EXISTS `scheduling`;
CREATE TABLE `scheduling` (
  `id` int NOT NULL AUTO_INCREMENT,
  `storeId` varchar(255) NOT NULL,
  `employeeIds` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `day` date NOT NULL,
  `startTime` time NOT NULL,
  `endTime` time NOT NULL,
  `periodName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Relationship_5` (`employeeIds`),
  KEY `FK_Relationship_storeId` (`storeId`),
  CONSTRAINT `FK_Relationship_storeId` FOREIGN KEY (`storeId`) REFERENCES `store` (`storeId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of scheduling
-- ----------------------------
INSERT INTO `scheduling` VALUES ('3', '1', '10000001', '2023-02-25', '12:00:00', '14:00:00', '周六12:00-14:00');
INSERT INTO `scheduling` VALUES ('4', '1', '1', '2023-02-26', '17:00:00', '20:00:00', '周日17:00-20:00');
INSERT INTO `scheduling` VALUES ('6', '1', '1', '2023-02-20', '21:00:00', '23:00:00', '周一21:00-23:00');
INSERT INTO `scheduling` VALUES ('7', '1', '1,2', '2023-03-08', '08:30:00', '11:00:00', '周一8:30-11:00');
INSERT INTO `scheduling` VALUES ('11', '1', '1,2', '2023-02-07', '08:30:00', '11:00:00', '周一8:30-11:00');
INSERT INTO `scheduling` VALUES ('12', '1', '1', '2023-02-06', '08:30:00', '11:00:00', '周一8:30-11:00');
INSERT INTO `scheduling` VALUES ('23', '1', '3', '2023-02-24', '19:00:00', '21:00:00', '周五19:00:00-21:00:00');
INSERT INTO `scheduling` VALUES ('26', '1', '4', '2023-02-20', '08:30:00', '11:00:00', '周一08:30:00-11:00:00');
INSERT INTO `scheduling` VALUES ('28', '1', '1,10000001,4', '2023-02-23', '14:00:00', '17:00:00', '周四14:00:00-17:00:00');
INSERT INTO `scheduling` VALUES ('33', '1', '10000001,2', '2023-02-20', '19:00:00', '21:00:00', '周一19:00:00-21:00:00');
INSERT INTO `scheduling` VALUES ('36', '1', '1,10000001,2', '2023-02-22', '14:00:00', '17:00:00', '周三14:00:00-17:00:00');
INSERT INTO `scheduling` VALUES ('37', '1', '10000001', '2023-02-22', '19:00:00', '21:00:00', '周三19:00:00-21:00:00');
INSERT INTO `scheduling` VALUES ('40', '1', '10000001', '2023-02-23', '17:00:00', '19:00:00', '周四17:00:00-19:00:00');
INSERT INTO `scheduling` VALUES ('41', '1', '2', '2023-02-23', '19:00:00', '21:00:00', '周四19:00:00-21:00:00');
INSERT INTO `scheduling` VALUES ('42', '1', '1,10000001', '2023-02-23', '21:00:00', '23:00:00', '周四21:00:00-23:00:00');
INSERT INTO `scheduling` VALUES ('43', '1', '1', '2023-02-21', '11:00:00', '14:00:00', '周二11:00:00-14:00:00');
INSERT INTO `scheduling` VALUES ('44', '1', '1', '2023-02-25', '14:00:00', '17:00:00', '周六14:00:0017:00:00');

-- ----------------------------
-- Table structure for store
-- ----------------------------
DROP TABLE IF EXISTS `store`;
CREATE TABLE `store` (
  `storeId` varchar(255) NOT NULL,
  `storeName` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `size` float NOT NULL,
  `manger` varchar(255) NOT NULL,
  `workers` int DEFAULT NULL,
  PRIMARY KEY (`storeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of store
-- ----------------------------
INSERT INTO `store` VALUES ('1', '浙江分公司', '湖州街50号', '200', 'zzt', '40');
INSERT INTO `store` VALUES ('2', '上海分公司', '湖州街51号', '200', 'zk', '23');
INSERT INTO `store` VALUES ('3', '江苏分公司', '湖州街52号', '200', 'wjl', '33');
INSERT INTO `store` VALUES ('4', 'xxx服装店', '湖州街53号', '300', 'yry', '43');
