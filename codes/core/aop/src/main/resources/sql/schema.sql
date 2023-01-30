-- -------------------------------------------------------------------
-- 运行本项目的初始化 DDL 脚本
-- Mysql 知识点可以参考：
-- https://dunwu.github.io/db-tutorial/#/sql/mysql/README
-- -------------------------------------------------------------------

-- 强制新建用户表
DROP TABLE IF EXISTS log_record;
CREATE TABLE log_record (
    id           BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    description  VARCHAR(255) NULL     DEFAULT NULL COMMENT '日志描述信息',
    level        VARCHAR(10)  NULL     DEFAULT NULL COMMENT '日志级别',
    exception    TEXT         NULL     DEFAULT NULL COMMENT '异常信息，只有日志级别为ERROR时才有值',
    method       VARCHAR(255) NULL     DEFAULT NULL COMMENT '被调用方法的名称',
    params       TEXT         NULL     DEFAULT NULL COMMENT '被调用方法的参数',
    request_time VARCHAR(255) NULL     DEFAULT NULL COMMENT '请求的耗时',
    create_time  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '日志记录时间',
    PRIMARY KEY (id) USING BTREE
)
    ENGINE = InnoDB
    ROW_FORMAT = COMPACT
    COMMENT = '日志记录';
