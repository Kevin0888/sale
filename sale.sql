/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : sale

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 06/02/2020 16:05:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_commodity
-- ----------------------------
DROP TABLE IF EXISTS `tb_commodity`;
CREATE TABLE `tb_commodity`  (
  `id` int(11) NOT NULL COMMENT '商品编码',
  `name` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名',
  `specification` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `units` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位',
  `price` double(10, 2) NULL DEFAULT NULL COMMENT '单价',
  `stock` int(5) NULL DEFAULT NULL COMMENT '库存',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_commodity
-- ----------------------------
INSERT INTO `tb_commodity` VALUES (1901, '可乐', '350ml', '瓶', 5.00, 41);
INSERT INTO `tb_commodity` VALUES (1902, '雪碧', '300ml', '瓶', 5.00, 68);
INSERT INTO `tb_commodity` VALUES (1903, '红茶', '500ml', '瓶', 3.00, 14);
INSERT INTO `tb_commodity` VALUES (1904, '牛奶', '240ml', '袋', 2.50, 22);
INSERT INTO `tb_commodity` VALUES (1905, '青岛啤酒', '350ml', '瓶', 5.00, 20);
INSERT INTO `tb_commodity` VALUES (1906, '红星二锅头', '500ml', '瓶', 30.00, 10);
INSERT INTO `tb_commodity` VALUES (1907, '牛栏山二锅头', '500ml', '瓶', 11.00, 20);
INSERT INTO `tb_commodity` VALUES (1908, '农夫山泉', '500ml', '瓶', 3.50, 10);
INSERT INTO `tb_commodity` VALUES (1909, '康师傅矿泉水', '500ml', '瓶', 2.00, 20);
INSERT INTO `tb_commodity` VALUES (1910, '燕京啤酒', '350ml', '瓶', 3.00, 20);
INSERT INTO `tb_commodity` VALUES (1911, '百岁山矿泉水', '500ml', '瓶', 5.00, 20);

-- ----------------------------
-- Table structure for tb_member
-- ----------------------------
DROP TABLE IF EXISTS `tb_member`;
CREATE TABLE `tb_member`  (
  `id` int(11) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员名',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `points` int(11) NULL DEFAULT NULL COMMENT '返点',
  `total` double(10, 2) NULL DEFAULT NULL COMMENT '总价',
  `registerTime` datetime(3) NULL DEFAULT NULL COMMENT '注册时间',
  `updateTime` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_member
-- ----------------------------
INSERT INTO `tb_member` VALUES (1001, '张七', '18613800886', 65, 35.00, NULL, '2020-02-03 11:42:38.624');
INSERT INTO `tb_member` VALUES (1002, '张六', '18613800886', 14, 86.00, NULL, '2020-02-03 11:48:35.327');
INSERT INTO `tb_member` VALUES (1003, '张四', '18613800886', 0, 100.00, NULL, '2020-02-02 21:55:32.848');
INSERT INTO `tb_member` VALUES (1004, '红茶', '18613800886', 10, 100.00, NULL, '2020-02-02 21:58:06.084');
INSERT INTO `tb_member` VALUES (1005, '小萌', '18889897788', 0, 100.00, NULL, '2020-02-02 21:57:59.724');
INSERT INTO `tb_member` VALUES (1006, '张八', '18613800886', 0, 100.00, NULL, '2020-02-02 21:55:56.848');
INSERT INTO `tb_member` VALUES (1007, '张二', '18613800886', 0, 100.00, NULL, '2020-02-02 21:56:20.930');
INSERT INTO `tb_member` VALUES (1008, '张九', '18613800886', 0, 100.00, NULL, '2020-02-02 21:56:06.428');
INSERT INTO `tb_member` VALUES (1009, '张三', '18978738787', 0, 100.00, NULL, '2020-02-02 21:57:18.745');
INSERT INTO `tb_member` VALUES (1010, '小兰', '15799908989', 400, 400.00, '2020-01-15 23:20:02.000', '2020-02-02 21:57:28.595');
INSERT INTO `tb_member` VALUES (1011, '小明', '19934324343', 300, 300.00, '2020-01-16 11:53:00.000', '2020-02-02 21:57:39.920');
INSERT INTO `tb_member` VALUES (1012, '小红', '18612301256', 226, 174.00, '2020-01-09 19:13:31.000', '2020-02-02 21:56:53.213');
INSERT INTO `tb_member` VALUES (1013, '小米', '18801200221', 200, 200.00, '2020-01-09 19:13:25.000', '2020-02-02 21:56:56.763');
INSERT INTO `tb_member` VALUES (1014, '郭麒麟', '18613800886', 0, 100.00, NULL, NULL);
INSERT INTO `tb_member` VALUES (1015, '郭德纲', '18613800886', 0, 100.00, '0000-00-00 00:00:00.000', '0000-00-00 00:00:00.000');
INSERT INTO `tb_member` VALUES (1016, '岳云鹏', '18613800886', 0, 100.00, '0000-00-00 00:00:00.000', '0000-00-00 00:00:00.000');

-- ----------------------------
-- Table structure for tb_member_record
-- ----------------------------
DROP TABLE IF EXISTS `tb_member_record`;
CREATE TABLE `tb_member_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `memberId` int(11) NULL DEFAULT NULL COMMENT '会员Id',
  `userId` int(11) NULL DEFAULT NULL COMMENT '管理员Id',
  `orderNumber` int(11) NULL DEFAULT NULL COMMENT '订单号',
  `sum` double(10, 2) NULL DEFAULT NULL COMMENT '消费总金额',
  `balance` double(10, 2) NULL DEFAULT NULL COMMENT '余额',
  `receivedPoints` int(11) NULL DEFAULT NULL COMMENT '返积分数',
  `checkoutTime` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '结账时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会员记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_member_record
-- ----------------------------
INSERT INTO `tb_member_record` VALUES (1, 22, 0, 1161101860, 16.00, 0.00, 16, '0000-00-00 00:00:00.000');
INSERT INTO `tb_member_record` VALUES (2, 22, 0, 1162301505, 10.00, 0.00, 10, '0000-00-00 00:00:00.000');
INSERT INTO `tb_member_record` VALUES (3, 1001, 0, 1171201663, 6.00, 0.00, 6, '0000-00-00 00:00:00.000');
INSERT INTO `tb_member_record` VALUES (4, 1001, 0, 1171201663, 6.00, 0.00, 6, '0000-00-00 00:00:00.000');
INSERT INTO `tb_member_record` VALUES (5, 1002, 0, 1171201896, 4.00, 0.00, 4, '0000-00-00 00:00:00.000');
INSERT INTO `tb_member_record` VALUES (6, 1001, 0, 1312201607, 4.00, 0.00, 4, '0000-00-00 00:00:00.000');
INSERT INTO `tb_member_record` VALUES (7, 1001, 0, 1312201851, 4.00, 0.00, 4, '0000-00-00 00:00:00.000');
INSERT INTO `tb_member_record` VALUES (8, 1001, 0, 203000226, 10.00, 0.00, 10, '0000-00-00 00:00:00.000');
INSERT INTO `tb_member_record` VALUES (9, 1001, 0, 2031102257, 35.00, 0.00, 35, '0000-00-00 00:00:00.000');
INSERT INTO `tb_member_record` VALUES (10, 1002, 0, 2031102909, 10.00, 0.00, 10, '0000-00-00 00:00:00.000');

-- ----------------------------
-- Table structure for tb_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `orderNumber` int(11) NULL DEFAULT NULL COMMENT '订单号',
  `sum` double(10, 2) NULL DEFAULT NULL COMMENT '总价',
  `userId` int(11) NULL DEFAULT NULL COMMENT '管理员Id',
  `memberId` int(11) NULL DEFAULT NULL COMMENT '会员Id',
  `checkoutType` int(2) NULL DEFAULT NULL COMMENT '0：未结算；1;已结算；2取消订单',
  `checkoutTime` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '状态变化时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 191 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_order
-- ----------------------------
INSERT INTO `tb_order` VALUES (2, 1101301237, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (3, 1101301109, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (4, 110130184, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (5, 1101301921, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (136, 2031102532, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (137, 2031102524, 10.00, 0, 0, 1, '2020-02-03 11:29:38.032');
INSERT INTO `tb_order` VALUES (148, 2041202923, 0.00, 0, 0, 2, '2020-02-04 12:10:05.230');
INSERT INTO `tb_order` VALUES (149, 2041402280, 10.00, 0, 0, 1, '2020-02-04 14:45:33.896');
INSERT INTO `tb_order` VALUES (150, 204140243, 10.00, 0, 0, 1, '2020-02-04 14:47:26.491');
INSERT INTO `tb_order` VALUES (151, 2041402465, 10.00, 0, 0, 1, '2020-02-04 14:52:40.574');
INSERT INTO `tb_order` VALUES (152, 204170229, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (154, 2041702629, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (155, 2041702212, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (156, 2052202744, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (157, 2052302364, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (158, 2052302545, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (159, 2052302832, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (160, 2052302469, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (161, 2052302125, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (162, 2060002897, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (163, 2060002632, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (164, 2060002857, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (165, 2060002511, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (166, 2060102239, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (167, 2060102310, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (168, 2060102815, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (169, 2060102456, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (170, 2061102681, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (171, 2061102294, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (172, 2061202837, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (173, 2061202971, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (174, 206120249, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (175, 206120237, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (176, 2061302421, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (177, 2061302776, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (178, 2061302292, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (179, 2061302436, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (180, 2061302826, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (181, 2061302892, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (182, 2061302398, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (183, 2061402944, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (184, 2061502485, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (185, 2061502869, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (186, 2061502349, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (187, 2061502478, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (188, 2061502238, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (189, 2061502334, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');
INSERT INTO `tb_order` VALUES (190, 2061602785, 0.00, 0, 0, 0, '0000-00-00 00:00:00.000');

-- ----------------------------
-- Table structure for tb_order_item
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_item`;
CREATE TABLE `tb_order_item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderNumber` int(11) NULL DEFAULT NULL COMMENT '订单号',
  `commodityId` int(11) NULL DEFAULT NULL COMMENT '商品编码',
  `commodityName` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `price` double(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `count` int(11) NULL DEFAULT NULL COMMENT '数量',
  `total` double(10, 2) NULL DEFAULT NULL COMMENT '总价',
  `isChecked` int(2) NULL DEFAULT NULL COMMENT '结账状态，0未结账；1已结账',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `commodityID`(`commodityId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 251 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_order_item
-- ----------------------------
INSERT INTO `tb_order_item` VALUES (1, 1101301109, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (2, 110130184, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (3, 1101301921, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (4, 1101301359, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (5, 1101401220, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (210, 2041402280, 1901, '可乐', 5.00, 2, 10.00, 1);
INSERT INTO `tb_order_item` VALUES (211, 204140243, 1901, '可乐', 5.00, 2, 10.00, 1);
INSERT INTO `tb_order_item` VALUES (212, 2041402465, 1901, '可乐', 5.00, 2, 10.00, 1);
INSERT INTO `tb_order_item` VALUES (213, 204170229, 1901, '可乐', 5.00, 1, 5.00, 0);
INSERT INTO `tb_order_item` VALUES (215, 2041702629, 1903, '红茶', 3.00, 2, 6.00, 2);
INSERT INTO `tb_order_item` VALUES (216, 2041702212, 1904, '牛奶', 2.50, 2, 5.00, 0);
INSERT INTO `tb_order_item` VALUES (217, 2052302545, 1901, '可乐', 5.00, 2, 2.00, 0);
INSERT INTO `tb_order_item` VALUES (218, 2052302832, 1901, '可乐', 5.00, 4, 4.00, 0);
INSERT INTO `tb_order_item` VALUES (219, 2052302469, 1901, '可乐', 5.00, 2, 2.00, 0);
INSERT INTO `tb_order_item` VALUES (220, 2052302469, 1902, '雪碧', 5.00, 2, 2.00, 0);
INSERT INTO `tb_order_item` VALUES (221, 2052302125, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (222, 2060002897, 1901, '可乐', 5.00, 4, 4.00, 0);
INSERT INTO `tb_order_item` VALUES (223, 2060002897, 1902, '雪碧', 5.00, 4, 4.00, 0);
INSERT INTO `tb_order_item` VALUES (224, 2060002632, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (225, 2060002857, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (226, 2060002511, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (227, 2060102239, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (228, 2060102310, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (229, 2060102815, 1902, '雪碧', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (230, 2060102456, 1901, '可乐', 5.00, 4, 20.00, 0);
INSERT INTO `tb_order_item` VALUES (231, 2061102681, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (232, 2061102294, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (233, 2061202837, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (234, 2061202971, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (235, 206120249, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (236, 206120237, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (237, 2061302421, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (238, 2061302776, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (239, 2061302292, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (240, 2061302436, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (241, 2061302826, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (242, 2061302892, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (243, 2061302398, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (244, 2061402944, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (245, 2061502485, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (246, 2061502869, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (247, 2061502349, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (248, 2061502478, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (249, 2061502238, 1901, '可乐', 5.00, 2, 10.00, 0);
INSERT INTO `tb_order_item` VALUES (250, 2061602785, 1901, '可乐', 5.00, 2, 10.00, 0);

-- ----------------------------
-- Table structure for tb_task
-- ----------------------------
DROP TABLE IF EXISTS `tb_task`;
CREATE TABLE `tb_task`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `order_number` int(11) NULL DEFAULT NULL COMMENT '订单号',
  `frequency` int(4) NULL DEFAULT NULL COMMENT '执行次数',
  `msg` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '数据',
  `status` int(4) NULL DEFAULT NULL COMMENT '1未执行，2执行中，3成功，4失败，5取消',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `data_type` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务类型',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_task
-- ----------------------------
INSERT INTO `tb_task` VALUES (1, 2032302267, 2, '{\"count\":2,\"commodityId\":1901}', 3, 'pre', 'commodity', '2020-02-03 15:47:27', '2020-02-04 00:12:59');
INSERT INTO `tb_task` VALUES (2, 2040002118, 0, '{\"count\":2,\"commodityId\":1902}', 3, 'pre', 'commodity', '2020-02-03 16:12:03', '2020-02-04 00:15:03');
INSERT INTO `tb_task` VALUES (3, 2040002545, 0, '{\"count\":2,\"commodityId\":1901}', 3, 'pre', 'commodity', '2020-02-04 00:25:17', '2020-02-04 00:28:20');
INSERT INTO `tb_task` VALUES (4, 2041102848, 0, '{\"count\":2,\"commodityId\":1901}', 3, 'pre', 'commodity', '2020-02-04 11:32:17', '2020-02-04 11:35:20');
INSERT INTO `tb_task` VALUES (5, 2041102848, 0, '{\"count\":2,\"commodityId\":1902}', 3, 'pre', 'commodity', '2020-02-04 11:32:23', '2020-02-04 11:35:30');
INSERT INTO `tb_task` VALUES (8, 2041102278, NULL, '{\"count\":5,\"commodityId\":1901}', 5, 'order', 'commodity', '2020-02-04 11:51:00', '2020-02-04 11:52:03');
INSERT INTO `tb_task` VALUES (9, 2041102278, NULL, '{\"count\":5,\"commodityId\":1902}', 5, 'order', 'commodity', '2020-02-04 11:51:36', '2020-02-04 11:52:03');
INSERT INTO `tb_task` VALUES (10, 2041202923, 0, '{\"count\":5,\"commodityId\":1902}', 3, 'order', 'commodity', '2020-02-04 12:07:00', '2020-02-04 12:10:05');
INSERT INTO `tb_task` VALUES (11, 2041402280, NULL, '{\"count\":2,\"commodityId\":1901}', 5, 'order', 'commodity', '2020-02-04 14:45:26', '2020-02-04 14:45:33');
INSERT INTO `tb_task` VALUES (12, 204140243, NULL, '{\"count\":2,\"commodityId\":1901}', 5, 'order', 'commodity', '2020-02-04 14:47:16', '2020-02-04 14:47:26');
INSERT INTO `tb_task` VALUES (13, 2041402465, NULL, '{\"count\":2,\"commodityId\":1901}', 5, 'order', 'commodity', '2020-02-04 14:52:38', '2020-02-04 14:52:40');
INSERT INTO `tb_task` VALUES (14, 204170229, 4, '{\"count\":1,\"commodityId\":1901}', 4, 'order', 'commodity', '2020-02-04 17:15:20', '2020-02-04 17:18:21');
INSERT INTO `tb_task` VALUES (15, 2041702629, 4, '{\"count\":2,\"commodityId\":1903}', 4, 'order', 'commodity', '2020-02-04 17:24:46', '2020-02-04 17:27:54');
INSERT INTO `tb_task` VALUES (16, 2041702212, 4, '{\"count\":2,\"commodityId\":1904}', 4, 'order', 'commodity', '2020-02-04 17:30:33', '2020-02-04 17:33:36');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` char(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` char(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `role` int(2) NOT NULL COMMENT '角色1：管理员；2收银员，3库管',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, '张三', '1', 1);
INSERT INTO `tb_user` VALUES (2, '李四', '2', 2);
INSERT INTO `tb_user` VALUES (3, '王二', '3', 3);

SET FOREIGN_KEY_CHECKS = 1;
