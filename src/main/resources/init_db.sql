CREATE SCHEMA `taxi_service` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `taxi_service`.`manufacturers` (
    `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `country` VARCHAR(255) NOT NULL,
    `deleted` TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`));

CREATE TABLE `taxi_service`.`drivers` (
    `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(225) NOT NULL,
    `license_number` VARCHAR(225) NOT NULL,
    `deleted` TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`));

CREATE TABLE `taxi_service`.`cars` (
    `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
    `model` VARCHAR(225) NOT NULL,
    `manufacturer_id` BIGINT(11) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `manufacturer_id_index` (`manufacturer_id` ASC) VISIBLE,
    CONSTRAINT `manufacturer_id`
       FOREIGN KEY (`manufacturer_id`)
           REFERENCES `taxi_service`.`manufacturers` (`id`)
           ON DELETE NO ACTION
           ON UPDATE NO ACTION);

ALTER TABLE `taxi_service`.`cars`
    ADD COLUMN `deleted` TINYINT(1) NOT NULL DEFAULT 0 AFTER `manufacturer_id`;

CREATE TABLE `taxi_service`.`cars_drivers` (
    `car_id` BIGINT(11) NOT NULL,
    `driver_id` BIGINT(11) NOT NULL,
    INDEX `car_id_idx` (`car_id` ASC) VISIBLE,
    INDEX `driver_id_idx` (`driver_id` ASC) VISIBLE,
    CONSTRAINT `car_id`
    FOREIGN KEY (`car_id`)
       REFERENCES `taxi_service`.`cars` (`id`)
       ON DELETE NO ACTION
       ON UPDATE NO ACTION,
    CONSTRAINT `driver_id`
    FOREIGN KEY (`driver_id`)
       REFERENCES `taxi_service`.`drivers` (`id`)
       ON DELETE NO ACTION
       ON UPDATE NO ACTION);

ALTER TABLE `taxi_service`.`drivers`
    ADD COLUMN `login` VARCHAR(225) NOT NULL AFTER `license_number`,
    ADD COLUMN `password` VARCHAR(225) NOT NULL AFTER `login`;
