CREATE TABLE `length_table` (
  `id` INT(10) AUTO_INCREMENT PRIMARY KEY,
  `from_length` CHAR(2) NOT NULL,
  `to_length` CHAR(2) NOT NULL,
  `conversion_factor` decimal(65,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
