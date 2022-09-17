-- 创建数据表 user
CREATE TABLE IF NOT EXISTS user (
    id   INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
    name VARCHAR(64)      NOT NULL DEFAULT 'default' COMMENT '用户名',
    age  TINYINT(3)       NOT NULL DEFAULT 0 COMMENT '年龄',
    PRIMARY KEY (id)
) COMMENT = '用户表';

INSERT INTO user (name, age)
VALUES ('Jack', 18);
INSERT INTO user (name, age)
VALUES ('Tom', 19);
INSERT INTO user (name, age)
VALUES ('Jone', 28);
INSERT INTO user (name, age)
VALUES ('Bill', 20);

