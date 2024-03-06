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
  `address_id` INT NULL,
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
INSERT INTO `species` (`id`, `species`, `image_url`) VALUES (1, 'dog', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS_vTrgOGMA8Rm0TPwABCF_1t2rKPNlYlzbroLbBnV60iET80CDimysFcSADBhnCWZcW5I&usqp=CAU');
INSERT INTO `species` (`id`, `species`, `image_url`) VALUES (2, 'cat', 'data:image/png;base64,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');
INSERT INTO `species` (`id`, `species`, `image_url`) VALUES (3, 'fish', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNB5hr7EYFSoQdqQxVkMr-VxzeRlPkHtPmyg&usqp=CAU');
INSERT INTO `species` (`id`, `species`, `image_url`) VALUES (4, 'bird', 'data:image/png;base64,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');
INSERT INTO `species` (`id`, `species`, `image_url`) VALUES (5, 'reptile', 'https://media.istockphoto.com/id/1329557082/vector/set-of-black-silhouettes-lizard-salamander-chameleon-iguana-and-other-reptiles.jpg?s=612x612&w=0&k=20&c=735DRBt5NiSOptuKKANbvsQZ6yUREDU0JftqN6UGMLo=');
INSERT INTO `species` (`id`, `species`, `image_url`) VALUES (6, 'rabbit', 'data:image/png;base64,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');
INSERT INTO `species` (`id`, `species`, `image_url`) VALUES (7, 'poultry', 'data:image/png;base64,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');
INSERT INTO `species` (`id`, `species`, `image_url`) VALUES (8, 'hamster', 'data:image/png;base64,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');
INSERT INTO `species` (`id`, `species`, `image_url`) VALUES (9, 'guinea pig', 'data:image/png;base64,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');
INSERT INTO `species` (`id`, `species`, `image_url`) VALUES (10, 'ferret', 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAVcAAACTCAMAAAAN4ao8AAAAjVBMVEX///8AAAD8/PwEBAT39/fKysrw8PDk5OSnp6fp6enf398+Pj709PTW1tbOzs64uLhvb2/AwMATExNTU1Pb29vDw8N8fHwgICCMjIwuLi5hYWHNzc1paWnU1NQpKSmFhYWYmJifn58dHR02NjawsLBJSUkQEBBtbW1/f386Ojp2dnZFRUWamppbW1uQkJDfkjaoAAALQ0lEQVR4nO2dC5OqIBTHAfGZj3xVmqaV2/b+/h/vgrbdarPUKHTH38zd7c5OCH/xeDgcAICenp6enp6enp6enp6enp6enp4LEO8K/FFQL+x7+Gu60vYg7fOtyq/oWQDo4n4UuqH+8Rq8F/IAyjOYfPyiBNE9YPPbgYRAlvzjd/Sd7DNT/SNdV1pAOPvsJamu1hLCaAXvsRtZXdQWXVkz7EIBjj/aDvLM+4O7iuYcrC52WUR6S/GPoFuT/DlcKb5a/O3dlwbAHgHDLVc1Zxkmc8WyRU/DnfEUdExeVOS3am9DKqpwaou7Nt98ZaqQtTnI28eiCsL5o2u/+VYzxMDAw1py3WWKpoS+9NZLa/TpCJ/0Vfhf1q8sfm+F2IFADJBxLGuTM7REUFgKVtejiAYA8XH3TNF7uBMTt929pbYKa8CCVw/br5aIgFlDcvfYGrhaMr7uinVYRS03B7oMgCyNnrYvtNn1EKQQP+4bNlX1dP/HSpt1ReRxTMdVmjOQGAzZaQFx0EDMe8y0FndZA5da1hu+5JeFRUCyJ2xEJb3WjVQ2GrwDefjIsF61A6Yv94842DW0qL8rBGHGQoA3Iddpy0tBA2LJUzaSnlmzUoE937Uasq3dYXP/jPzQQtl8MFJthABD3FITG9dqhwCXcf1rYNL0OUy/GKtKqiPAscdeExY8HercNIV0WaPmJTwPoCi3qkJTd7W8NhBab9HlJRBSm7R0tvVx/vXSck+l5x9UF2isDcB/SO2H+N3RoZog2l2bdSBhbTwaJ3i4uACRdgr9OVsprytC6j8TP6VYVUbNGpKzK480zx36UyEOAPEAdu/rrHl94GIBhy1xZFEeRhGfxTyf4Cg6+D9vitApNuMPoA+AOAiJa8X8XXWPkb0P22FlqQDKEr78GnGUi5kGRDxUyTTJzRrO1wO4xCBj/J4qYzZtSXgLAfv+PFINhEKz7GfaVEKeoY0H8KTlWqKPQ6Wh3MsVaeJXM+Z0/YxZqzaiJNNS1QgML0p1h5WCOcwYqHx7LFINI/YahZPLmM1VFdihGsEPPfa/oY/Fu6eMHqPTiU2DXftJQePBURwtkxUd/5yf+08YgMtakMtx97aQxrZjLf53f079taDBEJsdumeYWspXgPewghxtLMJ0KKTx1uAdLE2F47srvzJNFPprCPDIO7dTtme8VWCPQENbvHRFZESUKZnZIDDQdugbw+ZnCDDGvofYjQtag3BcUmH5YWKgi7xVeAOBLu8DhZusNFIi2n+vwwowUImnw+3VhZAkyrr9JIOve5ARnhMXEVA+yBoAojY/jQz+1gDB4hcwpDdU9S0ptcd/TVXSmhG/qS6iq+J7pNfaoxcnDNoG7SVLXnEtOlMizgFx9SwJTJXDTb06TgDhUOakq2rtwVQloy4aKrA8SV5PLlL8ui2uI8amzMcUIMuSdR8gwyf/8aeAWFuAY0OeZOEhgAlvZV6FWxhWshGxAgAbU4BFhZjba186eHnmizM+p/CLoQOPeNCeqkqiSD4o15Pvyf5Uv84aBD49FukAUSV9oE1VUgXbuDRHxPoCF+6gw3QK7MNoXHQt8iZ1g6hLRwj2r8cGR3G6MniL0xjh02tQr8UrxDTvRYAwtgNw6KwlyIYTvhFuBLySpEt5B8Tj4MOzqqyIgC1xzn/B924sjXityd8U3gI1RW5Z4uYZBOhiSbTZws1n01bYsAX8olpV0JRleE646hJiO7vrf45KtHM6KKzRcmGnyPDmUceEpZXlm631HOrr7rdB9zyDOW53j6VLr6R11K0eC+HGa/s2BfmaFrljCQdCq9d4XhIW1e0IAnTabQbOSBvH7ZSw/DIJakD8bGxZTpd0DTBv0SqCUL01yrzxeQtWGatTkzQj3nJVRlW6FC0Y85arMsgcdETYfBjTEY+A4lqznwVxrSbXlU8eQSMs3+ItWUWipEu6An0/Cjvhxg7AuiuOFgXZ/vS9QRgmhZMyeCtVF0li0O4bZrvD92S/jTZwzGylTt3tUjiDgAwZpyEG5+2jkCyhmEXpAg28dMgfyJfYQ9N1i92AXmp4wVD9ZQZtBsJCGHZLVwBGC2CtX/W28i87W/neBiIqE12FluxNUhGkL0UAGKxO2GVFw3/3KkYGPP2sMK+CdaLFEsIjzTts3GcDv/wpRWx0hXrHDAECSgyhFr3Q5MnD9RUOC1UF6LY7i+AWuu4DHEZg0tjV3Bwfp1Sz8jc6eOYBxiBeD2b14zCLxM5zacqLRiBhMaJbWd08CAWBdLLza87NBFaVHcfZOFrDT6jAHgRi9VuOnDpp81+o+OYTJDbjZKWt6W+PIVWOVdcXaszUDivmpe4ZhHnbsJNOYyRLSudFKypsQjqs2n+QzGJL07HDacXR6yCgS1vHriYrrDwCQsBnoOvX3ezeLpCvnl4N8gnF5dN2OpUXBWP0+ruLOIGz7kzJ3oKAlZLH2x9BD8Loiba1Oo/NIhS7Gk2LV2X3Oq4IthgYHpTTYKg83uu1XiSEUUpYkKodPYTSkIAEMk/2v+Ls4RC0TqiZWO4Ng/Fs3uOjuCX7mDaBOPx7DMLhg4B/3RD+obyomrjdtbTFcjtTfJATU3ciz2S4f+TB+DmdLq9rlwxDUdlsX9Iyx67ZFoQkZj1W2Gba5W3t1Egs3xQalYWllw2KY/XyyhkPwuE6mUySZHvoUm5BDhGj5ICk+nlotFMZy+e2oMEWQKvgkCj5kX5dMQlU2Pm9pjfdi230RDA60ks9TWlgiVe75MG8Rasoqin/CpzMFg0aoFrpdjB+2l/XxQmtNYyxcP4B4bZLE4z65CZ+GFU8YQD9jI/yw3qeCyScd3tEaNskqLBaix3pshR86xWkdUtAWsVDJX7sC2q+zf0hzURDllqvrzr/tcFs1e6KEZbi7Wi9rXb8iQCjn2N+8t3+a2eUX6aXLCOFy1YbTyDtknx/ak7uSVKhwkSZyBHqJmjdLNRSRye9GzKw2hdN0Ifl9a3y/XmTaYL5VRFEEXkNm47T8i9t4nYJi06HpN1tkVulAKn+ThIC/PpdUH1rcC4tD9BP2iVr/HUom0QcmxXGjQiYSghrny57L3FQOs2QNU1wMEF7ImC5ZxSON2GiZJayumpSUqWa+dPnYe24jcLoAJ1BmMyPE9HzTN8+YSnHw+xWrbumG2e7xlEb8q1DJoGWGNqLk9GxMr5qfDVnAP2c3ZlznVWha2SI4M7O7b5kdi8Bg5RiLxp22OJLbtaWJeE/ssbXDoGTvnpMcnZ46CWkJTWRXk17/OY9U37xwGDx1s16eBTiw1Lzn+rTg663pd/Xcg9FEBqfBTrLqiTovJOpIcuaZyez2wZkTcyUZ8WqKqXf84z6o08USUsKoZeVjz/OW8NzFgOOe0vToyLPFbkRIakfREbplVMgPMsNn5aVU9xQbf69HDtC5DfMU/zitaEsACUDAoGm+dcmrtmzggqtxpJEHmgjOderFnQvg8/7BghYpVUd1g8bD2s1XKhx4jGpCbZrnvBccJB5hL9R2dt602Cwna1q9qdJ1af0ZI6krL60QeR93hYg3fLujZKGjQKbOprXaLAQNdnbTbKukvgr3MZF8vlkL3o9DSupYvmmocmqqsqaJjd1qklhd+/SlRCLYHcYTjJRBg1e1rk+yJwU8d0Jsbmz5+dmDY4cphrR3Td+s/tLR1xKYQVnX+GI3C2buHDE6ZLI7fI8csfIR/2861iTa/wcdqL6abgc69okerpjRbCw2zL6agwfb9EcPkxbyrq0Ovwxn9ZX8o80AuEoiUsk3tHJ8F04mVuxJnui3c1VIC0AXf5COlCzy6mIVoS2Osk5devBH3t6enp6enp6enp6enp6enp6enp6et7NPze4rjSdqTzpAAAAAElFTkSuQmCC');

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
INSERT INTO `category` (`id`, `type`) VALUES (2, 'Pet Supplies');
INSERT INTO `category` (`id`, `type`) VALUES (3, 'Pet Food');
INSERT INTO `category` (`id`, `type`) VALUES (4, 'Pet Daycare');
INSERT INTO `category` (`id`, `type`) VALUES (5, 'Pet Adoption');
INSERT INTO `category` (`id`, `type`) VALUES (6, 'Pet Shelter');
INSERT INTO `category` (`id`, `type`) VALUES (7, 'Pet Trainer');
INSERT INTO `category` (`id`, `type`) VALUES (8, 'Pet Recreation');

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

