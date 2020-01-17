SET FOREIGN_KEY_CHECKS = 0;


DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id`       INT(11)  NOT NULL,
  `username` CHAR(50) NOT NULL,
  `password` CHAR(50) NOT NULL,
  `role`     INT(2)   NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `tb_commodity`;
CREATE TABLE `tb_commodity` (
  `id`            INT(11) NOT NULL,
  `name`          CHAR(10)      DEFAULT NULL,
  `specification` CHAR(10)       DEFAULT NULL,
  `units`         CHAR(10)       DEFAULT NULL,
  `price`         DOUBLE(10, 2) DEFAULT NULL,
  `stock`         INT(5)        DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `tb_order_item`;
CREATE TABLE `tb_order_item` (
  `id`             INT(11) NOT NULL,
  `order_number`   INT(11)       DEFAULT NULL,
  `commodity_Id`   INT(11)       DEFAULT NULL,
  `commodity_name` VARCHAR(10)   DEFAULT NULL,
  `price`          DOUBLE(10, 2) DEFAULT NULL,
  `count`          INT(11)       DEFAULT NULL,
  `total`          DOUBLE(10, 2) DEFAULT NULL,
  `is_checked`     INT(2)        DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `commodityID` (`commodity_Id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order` (
  `id`            INT(11) NOT NULL,
  `order_number`   INT(11)       DEFAULT NULL,
  `sum`           DOUBLE(10, 2) DEFAULT NULL,
  `user_id`       INT(11)       DEFAULT NULL,
  `member_id`     INT(11)       DEFAULT NULL,
  `checkout_type` INT(2)        DEFAULT NULL,
  `checkout_time`    datetime(3)  DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `tb_member`;
CREATE TABLE `tb_member` (
  `id`            INT(11) NOT NULL,
  `name`          VARCHAR(50)   DEFAULT NULL,
  `phone`         VARCHAR(20)   DEFAULT NULL,
  `points`        INT(11)       DEFAULT NULL,
  `total`         DOUBLE(10, 2) DEFAULT NULL,
  `register_time` datetime(3)  DEFAULT NULL,
  `update_time`   datetime(3)  DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `tb_member_record`;
CREATE TABLE `tb_member_record` (
  `id`             INT(11) NOT NULL,
  `member_id`      INT(11)       DEFAULT NULL,
  `user_id`        INT(11)       DEFAULT NULL,
  `order_number`    INT(11)       DEFAULT NULL,
  `sum`            DOUBLE(10, 2) DEFAULT NULL,
  `balance`        DOUBLE(10, 2) DEFAULT NULL,
  `received_points` INT(11)       DEFAULT NULL,
  `checkout_time`  datetime(3)  DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

