/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : localhost:3306
 Source Schema         : warehouse_test_2

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

 Date: 31/05/2024 15:48:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for outbound_task
-- ----------------------------
DROP TABLE IF EXISTS `outbound_task`;
CREATE TABLE `outbound_task` (
  `outboundTaskId` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `status` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`outboundTaskId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;
