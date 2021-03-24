/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : database

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 24/03/2021 19:33:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `sys_oss_id` bigint(0) NULL DEFAULT NULL COMMENT '上传id',
  `folder_id` bigint(0) NULL DEFAULT NULL COMMENT '文件夹id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `size` bigint(0) NULL DEFAULT 0 COMMENT '大小',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'URL地址',
  `state` tinyint unsigned NULL COMMENT '状态（0:在使用，1回收站，2不可用）',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_folder_id`(`folder_id`) USING BTREE,
  INDEX `idx_sys_oss_id`(`sys_oss_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file
-- ----------------------------
INSERT INTO `file` VALUES (1374600375764881410, 1374600375668412417, 1374345967789498369, 'marble.png', 856016, 'E:/upload/20210324/25ec760acb9149e79e7f97790059493a.png', 0, 1368482737376579586, '2021-03-24 13:54:03', 1368482737376579586, '2021-03-24 18:28:53');
INSERT INTO `file` VALUES (1374600457822244866, 1374600457729970178, 1374345967789498369, 'marble.png', 856016, 'E:/upload/20210324/c8138c3d799f495894287b2feb2f3f66.png', 0, 1368482737376579586, '2021-03-24 13:54:23', 1368482737376579586, '2021-03-24 13:54:23');
INSERT INTO `file` VALUES (1374605201097629698, 1374545769101914114, 1374599435087351810, 'marble.png', 856016, 'E:/upload/20210324/a7a5b10cd6454e6a9f07d0327bb2e35d.png', 0, 1368482737376579586, '2021-03-24 14:13:14', 1368482737376579586, '2021-03-24 14:13:14');
INSERT INTO `file` VALUES (1374605201126989826, 1374600375668412417, 1374599435087351810, 'marble.png', 856016, 'E:/upload/20210324/25ec760acb9149e79e7f97790059493a.png', 0, 1368482737376579586, '2021-03-24 14:13:14', 1368482737376579586, '2021-03-24 14:13:14');
INSERT INTO `file` VALUES (1374605201126989827, 1374600457729970178, 1374599435087351810, 'marble.png', 856016, 'E:/upload/20210324/c8138c3d799f495894287b2feb2f3f66.png', 0, 1368482737376579586, '2021-03-24 14:13:14', 1368482737376579586, '2021-03-24 14:13:14');
INSERT INTO `file` VALUES (1374661289985798145, 1374661289826414593, 1374345967789498369, 'marble.png', 856016, 'E:/upload/20210324/9c82546a913046d092173d8016818fab.png', 0, 1368482737376579586, '2021-03-24 17:56:06', 1368482737376579586, '2021-03-24 17:56:06');
INSERT INTO `file` VALUES (1374661562267430913, 1374661562212904961, 1374633760092651521, 'marble.png', 856016, 'E:/upload/20210324/39ba1064186a484da2c6a93adfd3d879.png', 0, 1368482737376579586, '2021-03-24 17:57:11', 1368482737376579586, '2021-03-24 17:57:11');

-- ----------------------------
-- Table structure for folder
-- ----------------------------
DROP TABLE IF EXISTS `folder`;
CREATE TABLE `folder`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `pid` bigint(0) NULL DEFAULT 0 COMMENT 'pid',
  `warehouse_id` bigint(0) NULL DEFAULT NULL COMMENT '仓库id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件夹名称',
  `size` bigint(0) NULL DEFAULT NULL COMMENT '容量大小',
  `state` tinyint unsigned NULL COMMENT '状态（0:在使用，1回收站，2不可用）',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_warehouse_id`(`warehouse_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件夹' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of folder
-- ----------------------------
INSERT INTO `folder` VALUES (1374345967789498369, 0, 1374319642282393602, '学习', 4444444555, 0, 1368482737376579586, '2021-03-23 21:03:07', 1368482737376579586, '2021-03-24 16:23:08');
INSERT INTO `folder` VALUES (1374599435087351810, 1374345967789498369, 1374319642282393602, '备份文件夹', 4444444555, 0, 1368482737376579586, '2021-03-24 13:50:19', 1368482737376579586, '2021-03-24 13:50:19');
INSERT INTO `folder` VALUES (1374633760092651521, 1374345967789498369, 1374319642282393602, '备份文件夹', NULL, 0, 1368482737376579586, '2021-03-24 16:06:43', 1368482737376579586, '2021-03-24 16:06:43');
INSERT INTO `folder` VALUES (1374636275588726785, 0, 1374319642282393602, '备份文件夹', NULL, 0, 1368482737376579586, '2021-03-24 16:16:42', 1368482737376579586, '2021-03-24 16:16:42');

-- ----------------------------
-- Table structure for user_warehouse
-- ----------------------------
DROP TABLE IF EXISTS `user_warehouse`;
CREATE TABLE `user_warehouse`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户ID',
  `warehouse_id` bigint(0) NULL DEFAULT NULL COMMENT '仓库id',
  `warehouse_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '仓库名',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_warehouse_id`(`warehouse_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户仓库关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_warehouse
-- ----------------------------
INSERT INTO `user_warehouse` VALUES (1374319642366279682, 1368482737376579586, 1374319642282393602, '工作资料1', 1368482737376579586, '2021-03-23 19:18:31');
INSERT INTO `user_warehouse` VALUES (1374343401076748289, 1368482737376579586, 1374343401005445122, '学习资料2', 1368482737376579586, '2021-03-23 20:52:56');

-- ----------------------------
-- Table structure for warehouse
-- ----------------------------
DROP TABLE IF EXISTS `warehouse`;
CREATE TABLE `warehouse`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `size` bigint(0) NULL DEFAULT NULL COMMENT '容量大小{B)',
  `state` tinyint unsigned NULL COMMENT '状态（0:在使用，1无人用）',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '仓库' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of warehouse
-- ----------------------------
INSERT INTO `warehouse` VALUES (1374319642282393602, '工作资料1', 5555555555555, 0, 1368482737376579586, '2021-03-23 19:18:31', 1368482737376579586, '2021-03-24 15:24:58');
INSERT INTO `warehouse` VALUES (1374343401005445122, '学习资料2', 4444444555, 0, 1368482737376579586, '2021-03-23 20:52:56', 1368482737376579586, '2021-03-23 20:52:56');
INSERT INTO `warehouse` VALUES (1374628224123072513, '学习资料3', 4444444555, 0, 1368482737376579586, '2021-03-24 15:44:43', 1368482737376579586, '2021-03-24 15:44:43');
INSERT INTO `warehouse` VALUES (1374628603573391362, '学习资料3', 4444444555, 0, 1368482737376579586, '2021-03-24 15:46:13', 1368482737376579586, '2021-03-24 15:46:13');
INSERT INTO `warehouse` VALUES (1374629461983821826, '学习资料3', 4444444555, 1, 1368482737376579586, '2021-03-24 15:49:38', 1368482737376579586, '2021-03-24 15:49:38');

SET FOREIGN_KEY_CHECKS = 1;
