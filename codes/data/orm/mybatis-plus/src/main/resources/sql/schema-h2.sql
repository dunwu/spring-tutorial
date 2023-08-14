DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id`      INT AUTO_INCREMENT NOT NULL COMMENT 'ID',
  `name`    VARCHAR(255)        NULL DEFAULT NULL COMMENT '用户名',
  `age`     INT                 NULL DEFAULT NULL COMMENT '年龄',
  `address`   VARCHAR(255)        NULL DEFAULT NULL COMMENT '地址',
  `email`   VARCHAR(255)        NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `t_user2`;
CREATE TABLE `t_user2` (
  `id`   INT AUTO_INCREMENT NOT NULL COMMENT '主键ID',
  `name` VARCHAR(30)        NULL DEFAULT NULL COMMENT '姓名',
  `age`  INT                NULL DEFAULT NULL COMMENT '年龄',
  PRIMARY KEY (`id`)
);
