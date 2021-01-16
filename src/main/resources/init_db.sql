CREATE SCHEMA `taxi_service` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `taxi_service`.`manufacturers` (
    `manufacturer_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
    `manufacturer_name` VARCHAR(255) NOT NULL,
    `manufacturer_country` VARCHAR(255) NOT NULL,
    `delete` TINYINT NULL,
    PRIMARY KEY (`manufacturer_id`));

ALTER TABLE `taxi_service`.`manufacturers`
    CHANGE COLUMN `delete` `delete` TINYINT(1) NULL DEFAULT 0 ;

ALTER TABLE `taxi_service`.`manufacturers`
    RENAME COLUMN `delete` TO `deleted`;

CREATE TABLE `taxi_service`.`drivers` (
    `driver_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
    `driver_name` VARCHAR(225) NOT NULL,
    `license_number` VARCHAR(225) NOT NULL,
    `deleted` TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (`driver_id`));
