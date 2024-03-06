-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema petappdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `petappdb` ;

-- -----------------------------------------------------
-- Schema petappdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `petappdb` DEFAULT CHARACTER SET utf8 ;
USE `petappdb` ;

-- -----------------------------------------------------
-- Table `address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `address` ;

CREATE TABLE IF NOT EXISTS `address` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `street` VARCHAR(100) NULL,
  `city` VARCHAR(45) NULL,
  `state` VARCHAR(25) NULL,
  `zip` VARCHAR(10) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user` ;

CREATE TABLE IF NOT EXISTS `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(75) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `enabled` TINYINT NOT NULL,
  `role` VARCHAR(45) NULL,
  `profile_picture` VARCHAR(2000) NULL,
  `biography` TEXT NULL,
  `background_picture` VARCHAR(2000) NULL,
  `first_name` VARCHAR(75) NULL,
  `last_name` VARCHAR(75) NULL,
  `created_at` DATETIME NULL,
  `updated_at` DATETIME NULL,
  `address_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  INDEX `fk_user_address1_idx` (`address_id` ASC),
  CONSTRAINT `fk_user_address1`
    FOREIGN KEY (`address_id`)
    REFERENCES `address` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `species`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `species` ;

CREATE TABLE IF NOT EXISTS `species` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `species` VARCHAR(75) NOT NULL,
  `image_url` TEXT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pet`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pet` ;

CREATE TABLE IF NOT EXISTS `pet` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(75) NOT NULL,
  `date_of_birth` DATE NULL,
  `breed` VARCHAR(75) NULL,
  `profile_picture` TEXT NULL,
  `description` TEXT NULL,
  `user_id` INT NOT NULL,
  `enabled` TINYINT NOT NULL,
  `species_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_pet_user1_idx` (`user_id` ASC),
  INDEX `fk_pet_species1_idx` (`species_id` ASC),
  CONSTRAINT `fk_pet_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pet_species1`
    FOREIGN KEY (`species_id`)
    REFERENCES `species` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `category` ;

CREATE TABLE IF NOT EXISTS `category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` TEXT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `post`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `post` ;

CREATE TABLE IF NOT EXISTS `post` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content` TEXT NOT NULL,
  `image_url` TEXT NULL,
  `created_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` INT NOT NULL,
  `enabled` TINYINT NOT NULL,
  `title` VARCHAR(75) NOT NULL,
  `pinned` TINYINT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_post_user_idx` (`user_id` ASC),
  CONSTRAINT `fk_post_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comment` ;

CREATE TABLE IF NOT EXISTS `comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(250) NOT NULL,
  `post_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `reply_to_id` INT NULL,
  `enabled` TINYINT NOT NULL,
  `created_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  PRIMARY KEY (`id`),
  INDEX `fk_comment_post1_idx` (`post_id` ASC),
  INDEX `fk_comment_user1_idx` (`user_id` ASC),
  INDEX `fk_comment_comment1_idx` (`reply_to_id` ASC),
  CONSTRAINT `fk_comment_post1`
    FOREIGN KEY (`post_id`)
    REFERENCES `post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_comment1`
    FOREIGN KEY (`reply_to_id`)
    REFERENCES `comment` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `post_has_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `post_has_category` ;

CREATE TABLE IF NOT EXISTS `post_has_category` (
  `post_id` INT NOT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`post_id`, `category_id`),
  INDEX `fk_post_has_category_category1_idx` (`category_id` ASC),
  INDEX `fk_post_has_category_post1_idx` (`post_id` ASC),
  CONSTRAINT `fk_post_has_category_post1`
    FOREIGN KEY (`post_id`)
    REFERENCES `post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_post_has_category_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pet_picture`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pet_picture` ;

CREATE TABLE IF NOT EXISTS `pet_picture` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `image_url` VARCHAR(2000) NOT NULL,
  `caption` TEXT NULL,
  `date_posted` DATETIME NULL,
  `pet_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_pet_picture_pet1_idx` (`pet_id` ASC),
  CONSTRAINT `fk_pet_picture_pet1`
    FOREIGN KEY (`pet_id`)
    REFERENCES `pet` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `follower`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `follower` ;

CREATE TABLE IF NOT EXISTS `follower` (
  `user_id` INT NOT NULL,
  `followed_user_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `followed_user_id`),
  INDEX `fk_user_has_user_user2_idx` (`followed_user_id` ASC),
  INDEX `fk_user_has_user_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_user_has_user_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_user_user2`
    FOREIGN KEY (`followed_user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `direct_message`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `direct_message` ;

CREATE TABLE IF NOT EXISTS `direct_message` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content` TEXT NOT NULL,
  `create_at` DATETIME NULL,
  `user_id` INT NOT NULL,
  `receiving_user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_direct_message_user1_idx` (`user_id` ASC),
  INDEX `fk_direct_message_user2_idx` (`receiving_user_id` ASC),
  CONSTRAINT `fk_direct_message_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_direct_message_user2`
    FOREIGN KEY (`receiving_user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `resource`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `resource` ;

CREATE TABLE IF NOT EXISTS `resource` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(75) NULL,
  `description` TEXT NULL,
  `image_url` VARCHAR(2000) NULL,
  `address_id` INT NOT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_resource_address1_idx` (`address_id` ASC),
  INDEX `fk_resource_category1_idx` (`category_id` ASC),
  CONSTRAINT `fk_resource_address1`
    FOREIGN KEY (`address_id`)
    REFERENCES `address` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_resource_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE = '';
DROP USER IF EXISTS petowner@localhost;
SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
CREATE USER 'petowner'@'localhost' IDENTIFIED BY 'petowner';

GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE * TO 'petowner'@'localhost';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `address`
-- -----------------------------------------------------
START TRANSACTION;
USE `petappdb`;
INSERT INTO `address` (`id`, `street`, `city`, `state`, `zip`) VALUES (1, 'test street 123', 'fakecity', 'fakestate', '12345');
INSERT INTO `address` (`id`, `street`, `city`, `state`, `zip`) VALUES (2, 'test street 321', 'citybefake', 'statefake', '54321');

COMMIT;


-- -----------------------------------------------------
-- Data for table `user`
-- -----------------------------------------------------
START TRANSACTION;
USE `petappdb`;
INSERT INTO `user` (`id`, `username`, `password`, `enabled`, `role`, `profile_picture`, `biography`, `background_picture`, `first_name`, `last_name`, `created_at`, `updated_at`, `address_id`) VALUES (1, 'admin', '$2a$10$nShOi5/f0bKNvHB8x0u3qOpeivazbuN0NE4TO0LGvQiTMafaBxLJS', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `user` (`id`, `username`, `password`, `enabled`, `role`, `profile_picture`, `biography`, `background_picture`, `first_name`, `last_name`, `created_at`, `updated_at`, `address_id`) VALUES (2, 'userone', '$2a$10$nShOi5/f0bKNvHB8x0u3qOpeivazbuN0NE4TO0LGvQiTMafaBxLJS', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 2);

COMMIT;


-- -----------------------------------------------------
-- Data for table `species`
-- -----------------------------------------------------
START TRANSACTION;
USE `petappdb`;
INSERT INTO `species` (`id`, `species`, `image_url`) VALUES (1, 'dog', NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `pet`
-- -----------------------------------------------------
START TRANSACTION;
USE `petappdb`;
INSERT INTO `pet` (`id`, `name`, `date_of_birth`, `breed`, `profile_picture`, `description`, `user_id`, `enabled`, `species_id`) VALUES (1, 'butterball', NULL, NULL, NULL, NULL, 1, 1, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `category`
-- -----------------------------------------------------
START TRANSACTION;
USE `petappdb`;
INSERT INTO `category` (`id`, `type`) VALUES (1, 'Pet Health');
INSERT INTO `category` (`id`, `type`) VALUES (2, 'Recreation');

COMMIT;


-- -----------------------------------------------------
-- Data for table `post`
-- -----------------------------------------------------
START TRANSACTION;
USE `petappdb`;
INSERT INTO `post` (`id`, `content`, `image_url`, `created_at`, `updated_at`, `user_id`, `enabled`, `title`, `pinned`) VALUES (1, 'this is a post', NULL, NULL, NULL, 1, 1, 'newtitle', 0);

COMMIT;


-- -----------------------------------------------------
-- Data for table `comment`
-- -----------------------------------------------------
START TRANSACTION;
USE `petappdb`;
INSERT INTO `comment` (`id`, `content`, `post_id`, `user_id`, `reply_to_id`, `enabled`, `created_at`, `updated_at`) VALUES (1, 'this is a comment', 1, 1, NULL, 1, NULL, NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `post_has_category`
-- -----------------------------------------------------
START TRANSACTION;
USE `petappdb`;
INSERT INTO `post_has_category` (`post_id`, `category_id`) VALUES (1, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `pet_picture`
-- -----------------------------------------------------
START TRANSACTION;
USE `petappdb`;
INSERT INTO `pet_picture` (`id`, `image_url`, `caption`, `date_posted`, `pet_id`) VALUES (1, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.fox6now.com%2Fnews%2Fspecial-needs-dog-named-butterball-struggling-to-find-forever-home&psig=AOvVaw1lJ1ZSEeCMlovS4qdZmKa2&ust=1709833030356000&source=images&cd=vfe&opi=89978449&ved=0CBMQjRxqFwoTCLD-78WW4IQDFQAAAAAdAAAAABAD', 'i have a dog', NULL, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `follower`
-- -----------------------------------------------------
START TRANSACTION;
USE `petappdb`;
INSERT INTO `follower` (`user_id`, `followed_user_id`) VALUES (1, 2);

COMMIT;


-- -----------------------------------------------------
-- Data for table `direct_message`
-- -----------------------------------------------------
START TRANSACTION;
USE `petappdb`;
INSERT INTO `direct_message` (`id`, `content`, `create_at`, `user_id`, `receiving_user_id`) VALUES (1, 'hi', NULL, 1, 2);

COMMIT;


-- -----------------------------------------------------
-- Data for table `resource`
-- -----------------------------------------------------
START TRANSACTION;
USE `petappdb`;
INSERT INTO `resource` (`id`, `name`, `description`, `image_url`, `address_id`, `category_id`) VALUES (1, 'dog clinic', NULL, NULL, 1, 1);

COMMIT;

