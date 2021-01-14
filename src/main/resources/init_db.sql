CREATE SCHEMA `taxi_service` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `taxi_service`.`manufacturers` (
    `manufacturer_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
    `manufacturer_name` VARCHAR(255) NOT NULL,
    `manufacturer_country` VARCHAR(255) NOT NULL,
    `delete` TINYINT NULL,
    PRIMARY KEY (`manufacturer_id`));

ALTER TABLE `taxi_service`.`manufacturers`
    CHANGE COLUMN `delete` `delete` TINYINT(1) NULL DEFAULT 0 ;
