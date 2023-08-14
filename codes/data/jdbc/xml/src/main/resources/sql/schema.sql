-- -------------------------------------------
-- 运行本项目的 DDL 脚本
-- -------------------------------------------

-- 创建用户表
CREATE TABLE `t_user` (
    `id`      BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`    VARCHAR(255)        NOT NULL DEFAULT '' COMMENT '用户名',
    `age`     INT(3)              NOT NULL DEFAULT 0 COMMENT '年龄',
    `address` VARCHAR(255)        NOT NULL DEFAULT '' COMMENT '地址',
    `email`   VARCHAR(255)        NOT NULL DEFAULT '' COMMENT '邮件',
    PRIMARY KEY (`id`),
    UNIQUE (`name`)
) COMMENT = '用户表';
