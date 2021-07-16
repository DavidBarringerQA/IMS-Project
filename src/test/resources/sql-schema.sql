-- MySQL Workbench Synchronization
-- Generated: 2021-07-09 13:46
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: david

CREATE SCHEMA IF NOT EXISTS `imsdb` DEFAULT CHARACTER SET utf8 ;
DROP TABLE IF EXISTS `order_items`;
DROP TABLE IF EXISTS `orders`;
DROP TABLE IF EXISTS `customers`;
DROP TABLE IF EXISTS `items`;


CREATE TABLE IF NOT EXISTS `imsdb`.`customers` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `forename` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));


CREATE TABLE IF NOT EXISTS `imsdb`.`items` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `value` DECIMAL(6,2) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE IF NOT EXISTS `imsdb`.`orders` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `cust_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `cust_id_idx` (`cust_id` ASC) VISIBLE,
  CONSTRAINT `cust_id`
    FOREIGN KEY (`cust_id`)
    REFERENCES `imsdb`.`customers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS `imsdb`.`order_items` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `order_id` INT(11) NOT NULL,
  `item_id` INT(11) NOT NULL,
  `quantity` INT(10) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `item_id_idx` (`item_id` ASC) VISIBLE,
  INDEX `order_id_idx` (`order_id` ASC) VISIBLE,
  CONSTRAINT `item_id`
    FOREIGN KEY (`item_id`)
    REFERENCES `imsdb`.`items` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `order_id`
    FOREIGN KEY (`order_id`)
    REFERENCES `imsdb`.`orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
