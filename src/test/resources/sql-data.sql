INSERT INTO `customers` (`forename`, `surname`) VALUES ('jordan', 'harrison');
INSERT INTO `customers` (`forename`, `surname`) VALUES ('david', 'barringer');
INSERT INTO `items` (`name`, `value`) VALUES ('ball', 1.00);
INSERT INTO `items` (`name`, `value`) VALUES ('mug', 3.50);
INSERT INTO `orders` (`cust_id`) VALUES (1);
INSERT INTO `order_items` (`order_id`, `item_id`, `quantity`) VALUES (1, 1, 1);
