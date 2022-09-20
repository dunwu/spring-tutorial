-- -------------------------------------------
-- 分库示例 DDL
-- -------------------------------------------

-- 创建数据库
CREATE DATABASE IF NOT EXISTS master_databases;
USE master_databases;

-- 创建数据表
CREATE TABLE IF NOT EXISTS user (
    id      BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
    name    VARCHAR(255)         NOT NULL DEFAULT '' COMMENT '用户名',
    age     TINYINT(3)          NOT NULL DEFAULT 0 COMMENT '年龄',
    address VARCHAR(255)         NOT NULL DEFAULT '' COMMENT '地址',
    email   VARCHAR(255)         NOT NULL DEFAULT '' COMMENT '邮件',
    PRIMARY KEY (id)
) COMMENT = '用户表';

-- 创建数据库
CREATE DATABASE IF NOT EXISTS slave_databases_0;
USE slave_databases_0;

-- 创建数据表
CREATE TABLE IF NOT EXISTS user (
    id      BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
    name    VARCHAR(255)         NOT NULL DEFAULT '' COMMENT '用户名',
    age     TINYINT(3)          NOT NULL DEFAULT 0 COMMENT '年龄',
    address VARCHAR(255)         NOT NULL DEFAULT '' COMMENT '地址',
    email   VARCHAR(255)         NOT NULL DEFAULT '' COMMENT '邮件',
    PRIMARY KEY (id)
) COMMENT = '用户表';

-- 创建数据库
CREATE DATABASE IF NOT EXISTS slave_databases_1;
USE slave_databases_1;

-- 创建数据表
CREATE TABLE IF NOT EXISTS user (
    id      BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
    name    VARCHAR(255)         NOT NULL DEFAULT '' COMMENT '用户名',
    age     TINYINT(3)          NOT NULL DEFAULT 0 COMMENT '年龄',
    address VARCHAR(255)         NOT NULL DEFAULT '' COMMENT '地址',
    email   VARCHAR(255)         NOT NULL DEFAULT '' COMMENT '邮件',
    PRIMARY KEY (id)
) COMMENT = '用户表';
