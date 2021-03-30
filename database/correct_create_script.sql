-- MySQL Workbench Forward Engineering

-- SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
-- SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
-- SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema elgrand_casino
-- -----------------------------------------------------
-- Elgrand casino database. Dice game.
-- 

-- -----------------------------------------------------
-- Schema elgrand_casino
--
-- Elgrand casino database. Dice game.
-- 
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `elgrand_casino` ;
USE `elgrand_casino` ;

-- -----------------------------------------------------
-- Table `elgrand_casino`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `elgrand_casino`.`account` (
  `accountId` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `email` VARCHAR(60) NULL,
  `password` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NULL,
  `admin` TINYINT(1) NOT NULL DEFAULT 0,
  `status` ENUM('active', 'banned') NOT NULL DEFAULT 'active',
  `numOfVictories` INT NULL DEFAULT 0,
  `numOfGames` INT NULL DEFAULT 0,
  `birthDate` DATE NULL,
  `avatar` LONGBLOB NULL,
  `locale` ENUM('ru-RU', 'en-US') NULL DEFAULT 'en-US',
  PRIMARY KEY (`accountId`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
  UNIQUE INDEX `userId_UNIQUE` (`accountId` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `elgrand_casino`.`message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `elgrand_casino`.`message` (
  `messageId` INT NOT NULL AUTO_INCREMENT,
  `creator` INT NOT NULL,
  `addressee` INT NOT NULL,
  `isRead` TINYINT(1) NOT NULL DEFAULT 0,
  `creationTime` DATETIME NULL,
  `message` LONGTEXT NULL,
  `theme` MEDIUMTEXT NULL,
  PRIMARY KEY (`messageId`, `creator`, `addressee`),
  INDEX `userId_idx` (`creator` ASC) VISIBLE,
  INDEX `userId_idx1` (`addressee` ASC) VISIBLE,
  UNIQUE INDEX `messageId_UNIQUE` (`messageId` ASC) VISIBLE,
  CONSTRAINT `userId2`
    FOREIGN KEY (`creator`)
    REFERENCES `elgrand_casino`.`account` (`accountId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `userId3`
    FOREIGN KEY (`addressee`)
    REFERENCES `elgrand_casino`.`account` (`accountId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `elgrand_casino`.`creditCardInfo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `elgrand_casino`.`creditCardInfo` (
  `account` INT NOT NULL,
  `creditCard` VARCHAR(16) NULL,
  `cardValid` DATE NULL,
  `secretCode` VARCHAR(3) NULL,
  `moneyAmount` INT NULL DEFAULT 0,
  `blockedMoney` INT NULL DEFAULT 0,
  PRIMARY KEY (`account`),
  CONSTRAINT `userId1`
    FOREIGN KEY (`account`)
    REFERENCES `elgrand_casino`.`account` (`accountId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `elgrand_casino`.`game`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `elgrand_casino`.`game` (
  `gameId` INT NOT NULL AUTO_INCREMENT,
  `time` DATETIME NULL,
  `rate` INT NULL DEFAULT 0,
  `creator` INT NOT NULL,
  `player` INT NULL,
  `creatorScore` INT NULL DEFAULT 0,
  `playerScore` INT NULL DEFAULT 0,
  `lastPlayerResult` INT NULL,
  `lastCreatorResult` INT NULL,
  `finished` TINYINT(1) NULL DEFAULT 0,
  `playerMove` TINYINT(1) NULL DEFAULT 1,
  PRIMARY KEY (`gameId`),
  UNIQUE INDEX `gameId_UNIQUE` (`gameId` ASC) VISIBLE,
  INDEX `accontId_idx` (`creator` ASC) VISIBLE,
  INDEX `account_idx` (`player` ASC) VISIBLE,
  CONSTRAINT `accountId1`
    FOREIGN KEY (`creator`)
    REFERENCES `elgrand_casino`.`account` (`accountId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `account`
    FOREIGN KEY (`player`)
    REFERENCES `elgrand_casino`.`account` (`accountId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `elgrand_casino`.`result`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `elgrand_casino`.`result` (
  `game` INT NOT NULL,
  `player` INT NOT NULL,
  `resultAmmount` INT NULL,
  `roundNumber` INT NOT NULL,
  PRIMARY KEY (`game`, `player`),
  INDEX `accountId_idx` (`player` ASC) VISIBLE,
  CONSTRAINT `accountId`
    FOREIGN KEY (`player`)
    REFERENCES `elgrand_casino`.`account` (`accountId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `gameId`
    FOREIGN KEY (`game`)
    REFERENCES `elgrand_casino`.`game` (`gameId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `elgrand_casino`.`adminstration`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `elgrand_casino`.`adminstration` (
  `minNumOfPoints` INT NOT NULL DEFAULT 10,
  `minRate` INT NOT NULL DEFAULT 0)
ENGINE = InnoDB;


-- SET SQL_MODE=@OLD_SQL_MODE;
-- SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
-- SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;



INSERT INTO `adminstration` (`minNumOfPoints`, `minRate`) VALUES ('14', '10');
INSERT INTO `elgrand_casino`.`account` (`login`, `email`, `password`, `name`, `surname`, `admin`, `status`, `numOfVictories`, `numOfGames`, `birthDate`) VALUES ('admin', 'velooor@gmail.com', 'password', 'Vadim', 'Zakharchenya', '1', 'active', '0', '0', '1996-06-19');
INSERT INTO `elgrand_casino`.`account` (`login`, `email`, `password`, `name`, `surname`, `admin`, `status`, `numOfVictories`, `numOfGames`, `birthDate`) VALUES ('user', 'user@gmail.com', 'password', 'John', 'Johnson', '0', 'active', '0', '0', '1995-06-11');

INSERT INTO `elgrand_casino`.`creditCardInfo` (`account`, `creditCard`, `cardValid`, `secretCode`, `moneyAmount`, `blockedMoney`) VALUES ('1', '1234123412341234', '2025-10-10', '123', '1000', '0');
INSERT INTO `elgrand_casino`.`creditCardInfo` (`account`, `creditCard`, `cardValid`, `secretCode`, `moneyAmount`, `blockedMoney`) VALUES ('2', '1234123412341234', '2025-10-10', '123', '1000', '0');

