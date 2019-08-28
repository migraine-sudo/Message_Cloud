/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50553
 Source Host           : localhost:3306
 Source Schema         : message_cloud

 Target Server Type    : MySQL
 Target Server Version : 50553
 File Encoding         : 65001

 Date: 23/08/2019 14:59:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `message_id` int(10) NOT NULL AUTO_INCREMENT,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `message` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`message_id`) USING BTREE,
  INDEX `phone`(`phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (1, '1801547619', '好GV房产网火耨刀耕好好呢him国际化ID似乎是荻花宫赐给睫毛膏魔法公鸡12595368fingcfmvie');
INSERT INTO `message` VALUES (2, '182501547619', '好GV房产网火耨刀耕好好呢him国际化ID似乎是荻花宫赐给睫毛膏魔法公鸡12595368fingcfmvie');

-- ----------------------------
-- Table structure for um
-- ----------------------------
DROP TABLE IF EXISTS `um`;
CREATE TABLE `um`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `um_ibfk_1`(`username`) USING BTREE,
  INDEX `um_ibfk_2`(`phone`) USING BTREE,
  CONSTRAINT `um_ibfk_1` FOREIGN KEY (`username`) REFERENCES `userm` (`username`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `um_ibfk_2` FOREIGN KEY (`phone`) REFERENCES `message` (`phone`) ON DELETE NO ACTION ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for userm
-- ----------------------------
DROP TABLE IF EXISTS `userm`;
CREATE TABLE `userm`  (
  `userid` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`userid`) USING BTREE,
  INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
