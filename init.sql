-- create the detail table of "A" lot
CREATE TABLE `A` (
    `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `car_id` INT DEFAULT NULL,
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- create the detail table of "B" lot
CREATE TABLE `B` (
    `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `car_id` INT DEFAULT NULL,
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- create the detail table of car
CREATE TABLE `car_info` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `car_number` CHAR(6) NOT NULL,
    `state` VARCHAR(5) DEFAULT 'enter',
) ENGINE=INNODB DEFAULT CHARSET=utf8;
