CREATE DATABASE IF NOT EXISTS costshare;
USE costshare;

CREATE TABLE IF NOT EXISTS users (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(20) NULL DEFAULT NULL UNIQUE,
  email VARCHAR(50) NULL DEFAULT NULL UNIQUE,
  password VARCHAR(200) NULL DEFAULT NULL DEFAULT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS roles (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(20) NULL DEFAULT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS user_roles (
  user_id BIGINT NOT NULL,
  role_id INT NOT NULL,
  PRIMARY KEY(user_id, role_id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS cost_group (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NULL DEFAULT NULL,
  access_code VARCHAR(255) NULL DEFAULT NULL,
  date_created DATETIME(6) NULL DEFAULT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS expense (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  group_id BIGINT NOT NULL,
  name VARCHAR(255) NULL DEFAULT NULL,
  amount DECIMAL(13,2) NULL DEFAULT NULL,
  date_created DATETIME(6) NULL DEFAULT NULL,
  FOREIGN KEY (group_id) REFERENCES cost_group (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS user_group (
  user_id BIGINT NOT NULL,
  group_id BIGINT NOT NULL,
  admin BIT(1) DEFAULT 0,
  PRIMARY KEY(user_id, group_id),
  CONSTRAINT fk_user_gr FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT fk_group FOREIGN KEY (group_id) REFERENCES cost_group (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS user_expense (
  user_id BIGINT NOT NULL,
  expense_id BIGINT NOT NULL,
  settled BIT(1) DEFAULT 0,
  PRIMARY KEY(user_id, expense_id),
  CONSTRAINT fk_user_ex FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT fk_expense FOREIGN KEY (expense_id) REFERENCES expense (id)
) ENGINE=InnoDB;

INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
