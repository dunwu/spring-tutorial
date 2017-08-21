DROP TABLE IF EXISTS `user_t`;
CREATE TABLE `user_t` (
  `id`        INT(11)      NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(40)  NOT NULL,
  `password`  VARCHAR(255) NOT NULL,
  `age`       INT(4)       NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 2 DEFAULT CHARSET = utf8;
