DROP DATABASE IF EXISTS `atoz`;

CREATE DATABASE `atoz`;

DROP TABLE IF EXISTS `atoz_property`;
DROP TABLE IF EXISTS `client`;
DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `client_role`;
DROP TABLE IF EXISTS `access_control`;
DROP TABLE IF EXISTS `access_token`;
DROP TABLE IF EXISTS `access_tokens_history`;

USE `atoz`;

CREATE TABLE `atoz_property` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` VARCHAR (30)NOT NULL,
  `value` varchar(50) NOT NULL,
  `enabled` tinyint(4) NOT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE client (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `client_name` VARCHAR(80),
  `client_secret` VARCHAR(32) NOT NULL,
  `grant_type` VARCHAR(20) NOT NULL,
  `access_token_validity_seconds` INT (10) DEFAULT 30,
  `enabled` tinyint(4) NOT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_cl_clientname` (`client_name`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(64) NOT NULL,
  `description` varchar(64),
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_code_UNIQUE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `client_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `client_id` int(10) unsigned NOT NULL,
  `role_code` varchar(64) NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ur_client_role_UNIQUE` (`client_id`, `role_code`),
  CONSTRAINT `FK_role_to_user_role` FOREIGN KEY (`role_code`) REFERENCES `role` (`code`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_client_to_client_role` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `access_control` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `url_prefix` varchar(200) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `role_codes` varchar(64) NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `created` datetime NOT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_url_prefix` (`url_prefix`),
  KEY `enabled` (`enabled`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `access_token` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `token` VARCHAR(32) NOT NULL,
  `client_id` int(10) unsigned NOT NULL,
  `token_type` VARCHAR(20) NOT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_token_pk` (token),
  UNIQUE KEY `uq_at_client_id` (`client_id`),
  CONSTRAINT `FK_at_client_to_token` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `access_tokens_history` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `token` VARCHAR(32) NOT NULL,
  `client_name` VARCHAR(80) NOT NULL,
  `token_type` VARCHAR(20) NOT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `token_expired_on` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_ath_access_token` (`token`),
  KEY `idx_ath_client_name` (`client_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into docm_property(`name`,`value`,`enabled`,`created_by`,`updated_by`)values("primary.use.cache","true",1,"atoz","atoz");
insert into docm_property(`name`,`value`,`enabled`,`created_by`,`updated_by`)values("primary.use.redis","true",1,"atoz","atoz");

CREATE TABLE `pincode_information` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pincode` VARCHAR(32) NOT NULL,
  `state` VARCHAR(80) NOT NULL,
  `district` VARCHAR(80) NOT NULL,
  `city` VARCHAR(80) NOT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_pincode` (`pincode`),
  UNIQUE KEY `psdc` (`pincode`,`state`,`district`,`city`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `users` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `username` VARCHAR(100) NOT NULL,
  `phone` VARCHAR(10) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_user` (`username`),
  UNIQUE KEY `uuv` (`username`),
  UNIQUE KEY `upv` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `access_token` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `token` VARCHAR(100) NOT NULL,
  `expiry` VARCHAR (100) NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_us` (`user_id`),
  UNIQUE KEY `utv` (`token`),
  CONSTRAINT `FK_user_id_to_users_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `service` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `service_type` VARCHAR(100) NOT NULL,
  `limit` VARCHAR (100) NOT NULL,
  `expiry` VARCHAR (100) NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_ser_us` (`user_id`),
  UNIQUE KEY `uusv` (`user_id`,`service_type`),
  CONSTRAINT `FK_service_user_id_to_users_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
