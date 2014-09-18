SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `schoolwebdb` DEFAULT CHARACTER SET latin1 ;
USE `schoolwebdb` ;

-- -----------------------------------------------------
-- Table `schoolwebdb`.`users`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `schoolwebdb`.`users` (
  `username` VARCHAR(16) NOT NULL ,
  `password` VARCHAR(50) NOT NULL ,
  `email` VARCHAR(50) NULL DEFAULT NULL ,
  `mailinglist` CHAR(1) NULL DEFAULT NULL ,
  PRIMARY KEY (`username`) ,
  INDEX `username` (`username` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `schoolwebdb`.`drawings`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `schoolwebdb`.`drawings` (
  `username` VARCHAR(16) NOT NULL ,
  `caption` VARCHAR(50) NOT NULL ,
  `vXML` VARCHAR(11000) NOT NULL ,
  `datetime` DATETIME NOT NULL ,
  PRIMARY KEY (`username`, `caption`) ,
  INDEX `caption` (`username` ASC, `caption` ASC) ,
  INDEX `fk_drawings_users1` (`username` ASC) ,
  CONSTRAINT `fk_drawings_users1`
    FOREIGN KEY (`username` )
    REFERENCES `schoolwebdb`.`users` (`username` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `schoolwebdb`.`tests`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `schoolwebdb`.`tests` (
  `username` VARCHAR(16) NOT NULL ,
  `title` VARCHAR(50) NOT NULL ,
  `lessoncode` VARCHAR(50) NOT NULL ,
  `datetime` DATETIME NOT NULL ,
  `maxscore` INT(3) UNSIGNED NOT NULL ,
  `score` INT(3) UNSIGNED NOT NULL ,
  `duration` TIME NOT NULL ,
  PRIMARY KEY (`username`, `title`) ,
  INDEX `title` (`username` ASC, `title` ASC) ,
  INDEX `lessoncode` (`username` ASC, `lessoncode` ASC) ,
  INDEX `fk_tests_users1` (`username` ASC) ,
  CONSTRAINT `fk_tests_users1`
    FOREIGN KEY (`username` )
    REFERENCES `schoolwebdb`.`users` (`username` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `schoolwebdb`.`roles`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `schoolwebdb`.`roles` (
  `idroles` VARCHAR(16) NOT NULL ,
  `description` VARCHAR(50) NULL ,
  PRIMARY KEY (`idroles`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `schoolwebdb`.`users_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schoolwebdb`.`users_roles` (
  `username` varchar(16) NOT NULL,
  `idroles` varchar(16) NOT NULL,
  PRIMARY KEY (`username`,`idroles`),
  KEY `fk_users_roles_users1` (`username`),
  KEY `fk_users_roles_roles1` (`idroles`),
  CONSTRAINT `fk_users_roles_roles1`
    FOREIGN KEY (`idroles`)
    REFERENCES `roles` (`idroles`)
    ON UPDATE CASCADE,
  CONSTRAINT `fk_users_roles_users1`
    FOREIGN KEY (`username`)
    REFERENCES `users` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE = InnoDB;


CREATE USER `schoolwebuser`@`%` IDENTIFIED BY 'abcdef89';

grant ALL on TABLE `schoolwebdb`.`drawings` to `schoolwebuser`@`%`;
grant ALL on TABLE `schoolwebdb`.`roles` to `schoolwebuser`@`%`;
grant ALL on TABLE `schoolwebdb`.`users_roles` to `schoolwebuser`@`%`;
grant ALL on TABLE `schoolwebdb`.`tests` to `schoolwebuser`@`%`;
grant ALL on TABLE `schoolwebdb`.`users` to `schoolwebuser`@`%`;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
