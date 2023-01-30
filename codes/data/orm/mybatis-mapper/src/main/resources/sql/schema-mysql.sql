DROP TABLE IF EXISTS `country`;
CREATE TABLE `country` (
  `id`          INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `countryname` VARCHAR(255) DEFAULT NULL COMMENT '名称',
  `countrycode` VARCHAR(255) DEFAULT NULL COMMENT '代码',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = `utf8` COMMENT ='国家信息';

DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `id`    BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name`  VARCHAR(255) CHARACTER SET `utf8` DEFAULT NULL,
  `state` VARCHAR(255) CHARACTER SET `utf8` DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = `utf8` COMMENT ='市级信息';

DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id`       INT(11)     NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` VARCHAR(32)          DEFAULT NULL COMMENT '密码',
  `usertype` VARCHAR(2)           DEFAULT NULL COMMENT '用户类型',
  `enabled`  INT(2)               DEFAULT NULL COMMENT '是否可用',
  `realname` VARCHAR(32)          DEFAULT NULL COMMENT '真实姓名',
  `qq`       VARCHAR(14)          DEFAULT NULL COMMENT 'QQ',
  `email`    VARCHAR(100)         DEFAULT NULL,
  `tel`      VARCHAR(255)         DEFAULT NULL COMMENT '联系电话',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = `utf8` COMMENT ='用户信息表';
