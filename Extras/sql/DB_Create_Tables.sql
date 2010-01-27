SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `WebAgenda` ;
CREATE SCHEMA IF NOT EXISTS `WebAgenda` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

-- -----------------------------------------------------
-- Table `WebAgenda`.`PERMISSIONSET`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`PERMISSIONSET` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`PERMISSIONSET` (
  `plevel` INT NOT NULL ,
  `canEditSched` TINYINT(1) NOT NULL DEFAULT 0 ,
  `canReadSched` TINYINT(1) NOT NULL DEFAULT 1 ,
  `canReadOldSched` TINYINT(1) NOT NULL DEFAULT 0 ,
  `canViewResources` TINYINT(1) NOT NULL DEFAULT 0 ,
  `canChangePermissions` TINYINT(1) NOT NULL DEFAULT 0 ,
  `canReadLogs` TINYINT(1) NOT NULL DEFAULT 0 ,
  `canAccessReports` TINYINT(1) NOT NULL DEFAULT 0 ,
  `canRequestDaysOff` TINYINT(1) NOT NULL DEFAULT 0 ,
  `maxDaysOff` INT NOT NULL ,
  `canTakeVacations` TINYINT(1) NOT NULL DEFAULT 0 ,
  `maxVacationDays` INT NOT NULL ,
  `canTakeEmergencyDays` TINYINT(1) NOT NULL DEFAULT 0 ,
  `canViewInactiveEmps` TINYINT(1) NOT NULL DEFAULT 0 ,
  `canSendNotifications` TINYINT(1) NOT NULL DEFAULT 0 ,
  `trusted` TINYINT(1) NOT NULL DEFAULT 0 ,
  `preferredRank` INT NOT NULL ,
  PRIMARY KEY (`plevel`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`LOCATION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`LOCATION` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`LOCATION` (
  `locName` VARCHAR(45) NOT NULL ,
  `locDescription` VARCHAR(200) NULL ,
  PRIMARY KEY (`locName`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`POSITION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`POSITION` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`POSITION` (
  `positionName` VARCHAR(45) NOT NULL ,
  `positionDescription` VARCHAR(200) NULL ,
  PRIMARY KEY (`positionName`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`EMPLOYEE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`EMPLOYEE` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`EMPLOYEE` (
  `empID` INT NOT NULL ,
  `supervisorID` INT NULL ,
  `givenName` VARCHAR(70) NOT NULL ,
  `familyName` VARCHAR(70) NOT NULL ,
  `email` VARCHAR(50) NULL ,
  `username` VARCHAR(20) NOT NULL ,
  `password` VARCHAR(8) NOT NULL ,
  `prefPosition` VARCHAR(45) NULL ,
  `prefLocation` VARCHAR(45) NULL ,
  `plevel` INT NOT NULL ,
  `active` TINYINT(1) NOT NULL DEFAULT 1 ,
  PRIMARY KEY (`empID`) ,
  INDEX `fk_EMPLOYEE_PERMISSIONSET` (`plevel` ASC) ,
  INDEX `fk_EMPLOYEE_LOCATION` (`prefLocation` ASC) ,
  INDEX `fk_EMPLOYEE_POSITION` (`prefPosition` ASC) ,
  CONSTRAINT `fk_EMPLOYEE_PERMISSIONSET`
    FOREIGN KEY (`plevel` )
    REFERENCES `WebAgenda`.`PERMISSIONSET` (`plevel` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_EMPLOYEE_LOCATION`
    FOREIGN KEY (`prefLocation` )
    REFERENCES `WebAgenda`.`LOCATION` (`locName` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_EMPLOYEE_POSITION`
    FOREIGN KEY (`prefPosition` )
    REFERENCES `WebAgenda`.`POSITION` (`positionName` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`NOTIFICATION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`NOTIFICATION` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`NOTIFICATION` (
  `notificationID` INT NOT NULL ,
  `senderID` INT NULL ,
  `recipientID` INT NOT NULL ,
  `sentTime` DATETIME NOT NULL ,
  `viewed` TINYINT(1) NOT NULL DEFAULT 0 ,
  `message` VARCHAR(300) NOT NULL ,
  `type` VARCHAR(20) NOT NULL ,
  PRIMARY KEY (`notificationID`) ,
  INDEX `fk_NOTIFICATION_EMPLOYEE_sender` (`senderID` ASC) ,
  INDEX `fk_NOTIFICATION_EMPLOYEE_recipient` (`recipientID` ASC) ,
  CONSTRAINT `fk_NOTIFICATION_EMPLOYEE_sender`
    FOREIGN KEY (`senderID` )
    REFERENCES `WebAgenda`.`EMPLOYEE` (`empID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_NOTIFICATION_EMPLOYEE_recipient`
    FOREIGN KEY (`recipientID` )
    REFERENCES `WebAgenda`.`EMPLOYEE` (`empID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`SKILL`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`SKILL` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`SKILL` (
  `skillName` VARCHAR(45) NOT NULL ,
  `skillDescription` VARCHAR(200) NULL ,
  PRIMARY KEY (`skillName`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`EMPSKILL`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`EMPSKILL` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`EMPSKILL` (
  `empID` INT NOT NULL ,
  `skillName` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`empID`, `skillName`) ,
  INDEX `fk_EMPSKILL_EMPLOYEE` (`empID` ASC) ,
  INDEX `fk_EMPSKILL_SKILL` (`skillName` ASC) ,
  CONSTRAINT `fk_EMPSKILL_EMPLOYEE`
    FOREIGN KEY (`empID` )
    REFERENCES `WebAgenda`.`EMPLOYEE` (`empID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_EMPSKILL_SKILL`
    FOREIGN KEY (`skillName` )
    REFERENCES `WebAgenda`.`SKILL` (`skillName` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`POSSKILL`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`POSSKILL` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`POSSKILL` (
  `positionName` VARCHAR(45) NOT NULL ,
  `skillName` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`positionName`, `skillName`) ,
  INDEX `fk_POSSKILL_POSITION` (`positionName` ASC) ,
  INDEX `fk_POSSKILL_SKILL` (`skillName` ASC) ,
  CONSTRAINT `fk_POSSKILL_POSITION`
    FOREIGN KEY (`positionName` )
    REFERENCES `WebAgenda`.`POSITION` (`positionName` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_POSSKILL_SKILL`
    FOREIGN KEY (`skillName` )
    REFERENCES `WebAgenda`.`SKILL` (`skillName` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`SHIFTTEMPLATE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`SHIFTTEMPLATE` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`SHIFTTEMPLATE` (
  `shiftReqID` INT NOT NULL ,
  PRIMARY KEY (`shiftReqID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`SHIFT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`SHIFT` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`SHIFT` (
  `shiftID` INT NOT NULL ,
  `shiftReqID` INT NOT NULL ,
  `startTime` TIME NOT NULL ,
  `endTime` TIME NOT NULL ,
  PRIMARY KEY (`shiftID`, `shiftReqID`) ,
  INDEX `fk_SHIFT_SHIFTREQS` (`shiftReqID` ASC) ,
  CONSTRAINT `fk_SHIFT_SHIFTREQS`
    FOREIGN KEY (`shiftReqID` )
    REFERENCES `WebAgenda`.`SHIFTTEMPLATE` (`shiftReqID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`SHIFTPOS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`SHIFTPOS` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`SHIFTPOS` (
  `shiftID` INT NOT NULL ,
  `positionName` VARCHAR(45) NOT NULL ,
  `posCount` INT NOT NULL ,
  PRIMARY KEY (`shiftID`, `positionName`) ,
  INDEX `fk_SHIFTPOS_SHIFT` (`shiftID` ASC) ,
  INDEX `fk_SHIFTPOS_POSITION` (`positionName` ASC) ,
  CONSTRAINT `fk_SHIFTPOS_SHIFT`
    FOREIGN KEY (`shiftID` )
    REFERENCES `WebAgenda`.`SHIFT` (`shiftID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SHIFTPOS_POSITION`
    FOREIGN KEY (`positionName` )
    REFERENCES `WebAgenda`.`POSITION` (`positionName` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`SCHEDULE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`SCHEDULE` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`SCHEDULE` (
  `scheduleID` INT NOT NULL ,
  `startDate` DATE NOT NULL ,
  `endDate` DATE NOT NULL ,
  PRIMARY KEY (`scheduleID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`WORKINGSHIFT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`WORKINGSHIFT` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`WORKINGSHIFT` (
  `workingShiftID` INT NOT NULL ,
  `scheduleID` INT NOT NULL ,
  `startTime` TIME NOT NULL ,
  `endTime` TIME NOT NULL ,
  PRIMARY KEY (`workingShiftID`, `scheduleID`) ,
  INDEX `fk_WORKINGSHIFT_SCHEDULE` (`scheduleID` ASC) ,
  CONSTRAINT `fk_WORKINGSHIFT_SCHEDULE`
    FOREIGN KEY (`scheduleID` )
    REFERENCES `WebAgenda`.`SCHEDULE` (`scheduleID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`WORKINGEMP`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`WORKINGEMP` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`WORKINGEMP` (
  `workingShiftID` INT NOT NULL ,
  `empID` INT NOT NULL ,
  PRIMARY KEY (`workingShiftID`, `empID`) ,
  INDEX `fk_WORKINGEMP_EMPLOYEE` (`empID` ASC) ,
  INDEX `fk_WORKINGEMP_WORKINGSHIFT` (`workingShiftID` ASC) ,
  CONSTRAINT `fk_WORKINGEMP_EMPLOYEE`
    FOREIGN KEY (`empID` )
    REFERENCES `WebAgenda`.`EMPLOYEE` (`empID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_WORKINGEMP_WORKINGSHIFT`
    FOREIGN KEY (`workingShiftID` )
    REFERENCES `WebAgenda`.`WORKINGSHIFT` (`workingShiftID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`GLOBALSETTINGS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`GLOBALSETTINGS` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`GLOBALSETTINGS` (
  `tempKey` INT NOT NULL ,
  PRIMARY KEY (`tempKey`) )
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
