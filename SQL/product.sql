DROP DATABASE IF EXISTS product;
CREATE DATABASE IF NOT EXISTS product;

GRANT ALL PRIVILEGES ON product.* TO 'product'@'localhost' IDENTIFIED BY 'product';

USE `product`;

CREATE TABLE `products` (
  `id` INT(6) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `description` VARCHAR(250),
  `remaining_quantity` INT NOT NULL,
  `unit_price` DOUBLE NOT NULL,
  `image` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `products`(`name`, `description`, `remaining_quantity`, `unit_price`, `image`) VALUES ("name1", "desc1", 1, 1.0, "http://opsb.us/wp-content/uploads/2012/10/481px-NEW-21.gif");
INSERT INTO `products`(`name`, `description`, `remaining_quantity`, `unit_price`, `image`) VALUES ("name2", "desc2", 2, 2.0, "img/product.png");
INSERT INTO `products`(`name`, `description`, `remaining_quantity`, `unit_price`, `image`) VALUES ("name3", "desc3", 3, 3.0, "img/product.png");
INSERT INTO `products`(`name`, `description`, `remaining_quantity`, `unit_price`, `image`) VALUES ("name4", "desc4", 4, 4.0, "img/product.png");
INSERT INTO `products`(`name`, `description`, `remaining_quantity`, `unit_price`, `image`) VALUES ("name5", "desc5", 5, 5.0, "img/product.png");
INSERT INTO `products`(`name`, `description`, `remaining_quantity`, `unit_price`, `image`) VALUES ("name6", "desc6", 6, 6.0, "img/product.png");
INSERT INTO `products`(`name`, `description`, `remaining_quantity`, `unit_price`, `image`) VALUES ("name7", "desc7", 7, 7.0, "img/product.png");
INSERT INTO `products`(`name`, `description`, `remaining_quantity`, `unit_price`, `image`) VALUES ("name8", "desc8", 8, 8.0, "img/product.png");
INSERT INTO `products`(`name`, `description`, `remaining_quantity`, `unit_price`, `image`) VALUES ("name9", "desc9", 9, 9.0, "img/product.png");
INSERT INTO `products`(`name`, `description`, `remaining_quantity`, `unit_price`, `image`) VALUES ("name1", "desc10", 10, 10.0, "img/product.png");