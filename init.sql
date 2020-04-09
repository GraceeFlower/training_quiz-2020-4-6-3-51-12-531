-- create database
CREATE DATABASE `parking_sys` DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

-- use this database
USE `parking_sys`;

-- create the table of lots
CREATE TABLE `lot_list` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `lot_name` CHAR(1) NOT NULL,
    `lot_no` INT NOT NULL,
    `car_id` INT DEFAULT 0
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- create the detail table of car
CREATE TABLE `car_info` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `car_number` CHAR(6) NOT NULL,
    `state` VARCHAR(5) DEFAULT 'enter'
) ENGINE=INNODB DEFAULT CHARSET=utf8;
