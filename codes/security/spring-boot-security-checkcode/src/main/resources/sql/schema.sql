-- -------------------------------------------
-- 运行本项目的初始化 DDL 脚本
-- -------------------------------------------

-- 用户表
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id`       INT(20)     NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` VARCHAR(30) NOT NULL COMMENT '用户名',
  `password` VARCHAR(60) NOT NULL COMMENT '密码',
  `email`    VARCHAR(100) DEFAULT '' COMMENT '邮箱',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username`(`username`),
  UNIQUE KEY `uk_email`(`email`)
)
  ENGINE = INNODB COMMENT ='用户表';

