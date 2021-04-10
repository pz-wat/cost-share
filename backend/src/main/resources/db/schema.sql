CREATE DATABASE IF NOT EXISTS costshare;
USE costshare;

CREATE TABLE IF NOT EXISTS `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `email` VARCHAR(255) NULL DEFAULT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `group` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(255) DEFAULT NULL,
  `access_code` VARCHAR(255) DEFAULT NULL,
  `date_created` DATETIME(6) DEFAULT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `expense` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `group_id` BIGINT NOT NULL,
  `name` VARCHAR(255) DEFAULT NULL,
  `amount` DECIMAL(13,2) DEFAULT NULL,
  `date_created` DATETIME(6) DEFAULT NULL,
  FOREIGN KEY (`group_id`) REFERENCES `group` (`id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `user_group` (
  `user_id` BIGINT NOT NULL,
  `group_id` BIGINT NOT NULL,
  `admin` BIT(1) DEFAULT 0,
  PRIMARY KEY(`user_id`, `group_id`),
  CONSTRAINT `fk_user_gr` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_group` FOREIGN KEY (`group_id`) REFERENCES `group` (`id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `user_expense` (
  `user_id` BIGINT NOT NULL,
  `expense_id` BIGINT NOT NULL,
  `settled` BIT(1) DEFAULT 0,
  PRIMARY KEY(`user_id`, `expense_id`),
  CONSTRAINT `fk_user_ex` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_expense` FOREIGN KEY (`expense_id`) REFERENCES `expense` (`id`)
) ENGINE=InnoDB;
