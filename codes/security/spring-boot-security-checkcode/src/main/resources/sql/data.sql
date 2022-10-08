---------------------------------------------
-- 运行本项目的初始化 DML 脚本
---------------------------------------------

INSERT INTO `account` (`username`, `password`, `email`)
VALUES ('admin', '$2a$10$Y9uV9YjFuNlATDGz5MeTZeuo8LbebbpP6jRgtZYQcgiCZRlf8rJYG', 'admin@xxx.com');

INSERT INTO `account` (`username`, `password`, `email`)
VALUES ('user', '$2a$10$Y9uV9YjFuNlATDGz5MeTZeuo8LbebbpP6jRgtZYQcgiCZRlf8rJYG', 'user@xxx.com');
