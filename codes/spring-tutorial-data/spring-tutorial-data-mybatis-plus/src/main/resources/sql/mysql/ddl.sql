USE mydb;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) NOT NULL COMMENT '用户ID' AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '用户名',
  `sex` int(1) NOT NULL COMMENT '性别。1表示男，2表示女',
  `age` int(4) NOT NULL DEFAULT '18' COMMENT '年龄',
  PRIMARY KEY (`id`)
) COMMENT='用户表';

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(10) NOT NULL COMMENT '产品ID' AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '产品名',
  `price` float DEFAULT NULL COMMENT '价格',
  PRIMARY KEY (`id`)
) COMMENT='产品表';

DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` int(10) NOT NULL COMMENT '订单ID' AUTO_INCREMENT,
  `user_id` int(10) NOT NULL COMMENT '用户ID',
  `product_id` int(10) NOT NULL COMMENT '产品ID',
  `num` int(10) NOT NULL DEFAULT '0' COMMENT '购买数量',
  `statu` int(1) NOT NULL DEFAULT '1' COMMENT '状态。1表示未支付；2表示已支付',
  PRIMARY KEY (`id`)
) COMMENT='订单表';

