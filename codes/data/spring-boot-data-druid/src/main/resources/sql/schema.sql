-- -------------------------------------------------------------------
-- 运行本项目的初始化 DDL 脚本
-- Mysql 知识点可以参考：
-- https://dunwu.github.io/db-tutorial/#/sql/mysql/README
-- -------------------------------------------------------------------

-- 强制新建用户表
DROP TABLE IF EXISTS user;
CREATE TABLE user (
    id       BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    username VARCHAR(255)        NOT NULL COMMENT '用户名',
    password VARCHAR(255)        NOT NULL COMMENT '密码',
    email    VARCHAR(255)        NOT NULL COMMENT '邮箱',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username(username)
) COMMENT ='用户表';
