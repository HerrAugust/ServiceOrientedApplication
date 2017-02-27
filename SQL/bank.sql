DROP DATABASE IF EXISTS bank;
CREATE DATABASE IF NOT EXISTS bank;

GRANT ALL PRIVILEGES ON bank.* TO 'bank'@'localhost' IDENTIFIED BY 'bank';

USE `bank`;

CREATE TABLE `addresses` (
  `id` INT(6) NOT NULL AUTO_INCREMENT,
  `street` VARCHAR(50) NOT NULL,
  `city` VARCHAR(50) NOT NULL,
  `country` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `accounts` (
  `id` INT(6) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL UNIQUE,
  `password` VARCHAR(50) NOT NULL,
  `firstname` VARCHAR(50) NOT NULL,
  `lastname` VARCHAR(50) NOT NULL,
  `amount` DOUBLE NOT NULL,
  `address` INT(6) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`address`) REFERENCES `addresses`(`id`)
);

INSERT INTO `addresses`(`street`, `city`, `country`) VALUES ("Montefiore", "Sart-Tilmant", "Belgium");
INSERT INTO `addresses`(`street`, `city`, `country`) VALUES ("", "Paris", "France");
INSERT INTO `addresses`(`street`, `city`, `country`) VALUES ("", "Barcelona", "Spain");
INSERT INTO `addresses`(`street`, `city`, `country`) VALUES ("", "Roma", "Italy");
INSERT INTO `addresses`(`street`, `city`, `country`) VALUES ("", "Berlin", "Germany");
INSERT INTO `addresses`(`street`, `city`, `country`) VALUES ("", "Athenes", "Greece");

INSERT INTO `accounts`(`username`, `password`, `firstname`, `lastname`, `amount`, `address`) VALUES ("test0", "test0", "test0", "test0", 0, 1);
INSERT INTO `accounts`(`username`, `password`, `firstname`, `lastname`, `amount`, `address`) VALUES ("test1", "test1", "test1", "test1", 100, 2);
INSERT INTO `accounts`(`username`, `password`, `firstname`, `lastname`, `amount`, `address`) VALUES ("test2", "test2", "test2", "test2", 200, 3);
INSERT INTO `accounts`(`username`, `password`, `firstname`, `lastname`, `amount`, `address`) VALUES ("test3", "test3", "test3", "test3", 300, 4);
INSERT INTO `accounts`(`username`, `password`, `firstname`, `lastname`, `amount`, `address`) VALUES ("test4", "test4", "test4", "test4", 400, 5);
INSERT INTO `accounts`(`username`, `password`, `firstname`, `lastname`, `amount`, `address`) VALUES ("test5", "test5", "test5", "test5", 500, 6);
