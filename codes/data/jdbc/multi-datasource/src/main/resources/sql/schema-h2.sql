-- -------------------------------------------------------------------
-- 运行本项目的初始化 DDL 脚本
-- H2 知识点可以参考：
-- https://dunwu.github.io/db-tutorial/#/sql/h2
-- -------------------------------------------------------------------

-- 创建用户表
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id`      INT AUTO_INCREMENT NOT NULL COMMENT 'ID',
  `name`    VARCHAR(255)       NOT NULL DEFAULT '' COMMENT '用户名',
  `age`     INT                NOT NULL DEFAULT 0 COMMENT '年龄',
  `address` VARCHAR(255)       NOT NULL DEFAULT '' COMMENT '地址',
  `email`   VARCHAR(255)       NOT NULL DEFAULT '' COMMENT '邮件',
  PRIMARY KEY (`id`),
  UNIQUE (`name`)
);
