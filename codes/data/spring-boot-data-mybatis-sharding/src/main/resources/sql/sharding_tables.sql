-- -------------------------------------------
-- 分表示例 DDL
-- -------------------------------------------

-- 创建数据库
CREATE DATABASE IF NOT EXISTS sharding_tables;
USE sharding_tables;

-- 创建数据表
CREATE TABLE IF NOT EXISTS user_0 (
    id      BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
    name    VARCHAR(255)         NOT NULL DEFAULT '' COMMENT '用户名',
    age     TINYINT(3)          NOT NULL DEFAULT 0 COMMENT '年龄',
    address VARCHAR(255)         NOT NULL DEFAULT '' COMMENT '地址',
    email   VARCHAR(255)         NOT NULL DEFAULT '' COMMENT '邮件',
    PRIMARY KEY (id)
) COMMENT = '用户表';

-- 创建数据表
CREATE TABLE IF NOT EXISTS user_1 (
    id      BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
    name    VARCHAR(255)         NOT NULL DEFAULT '' COMMENT '用户名',
    age     TINYINT(3)          NOT NULL DEFAULT 0 COMMENT '年龄',
    address VARCHAR(255)         NOT NULL DEFAULT '' COMMENT '地址',
    email   VARCHAR(255)         NOT NULL DEFAULT '' COMMENT '邮件',
    PRIMARY KEY (id)
) COMMENT = '用户表';
