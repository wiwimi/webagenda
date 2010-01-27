SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `WebAgenda` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `WebAgenda`;

-- -----------------------------------------------------
-- Table `WebAgenda`.`PERMISSIONSET`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`PERMISSIONSET` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`PERMISSIONSET` (
  `plevel` INT NOT NULL ,
  `canEditSched` BOOLEAN NOT NULL DEFAULT 0 ,
  `canReadSched` BOOLEAN NOT NULL DEFAULT 1 ,
  `canReadOldSched` BOOLEAN NOT NULL DEFAULT 0 ,
  `canViewResources` BOOLEAN NOT NULL DEFAULT 0 ,
  `canChangePermissions` BOOLEAN NOT NULL DEFAULT 0 ,
  `canReadLogs` BOOLEAN NOT NULL DEFAULT 0 ,
  `canAccessReports` BOOLEAN NOT NULL DEFAULT 0 ,
  `canRequestDaysOff` BOOLEAN NOT NULL DEFAULT 0 ,
  `maxDaysOff` INT NOT NULL ,
  `canTakeVacations` BOOLEAN NOT NULL DEFAULT 0 ,
  `maxVacationDays` INT NOT NULL ,
  `canTakeEmergencyDays` BOOLEAN NOT NULL DEFAULT 0 ,
  `canViewInactiveEmps` BOOLEAN NOT NULL DEFAULT 0 ,
  `canSendNotifications` BOOLEAN NOT NULL DEFAULT 0 ,
  `trusted` BOOLEAN NOT NULL DEFAULT 0 ,
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
  `active` BOOLEAN NOT NULL DEFAULT 1 ,
  PRIMARY KEY (`empID`) ,
  INDEX `fk_Employee_PermissionSet` (`plevel` ASC) ,
  INDEX `fk_Employee_LOCATION` (`prefLocation` ASC) ,
  INDEX `fk_EMPLOYEE_POSITION` (`prefPosition` ASC) ,
  CONSTRAINT `fk_Employee_PermissionSet`
    FOREIGN KEY (`plevel` )
    REFERENCES `WebAgenda`.`PERMISSIONSET` (`plevel` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Employee_LOCATION`
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
-- Table `WebAgenda`.`NotificationType`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`NotificationType` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`NotificationType` (
  `type` VARCHAR(20) NOT NULL ,
  PRIMARY KEY (`type`) )
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
  `viewed` BOOLEAN NOT NULL DEFAULT 0 ,
  `message` VARCHAR(300) NOT NULL ,
  `type` VARCHAR(20) NOT NULL ,
  PRIMARY KEY (`notificationID`) ,
  INDEX `fk_Notification_Employee` (`senderID` ASC, `recipientID` ASC) ,
  INDEX `fk_Notification_NotificationType` (`type` ASC) ,
  CONSTRAINT `fk_Notification_Employee`
    FOREIGN KEY (`senderID` , `recipientID` )
    REFERENCES `WebAgenda`.`EMPLOYEE` (`empID` , `empID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Notification_NotificationType`
    FOREIGN KEY (`type` )
    REFERENCES `WebAgenda`.`NotificationType` (`type` )
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
  INDEX `fk_EMPSKILL_Employee` (`empID` ASC) ,
  INDEX `fk_EMPSKILL_SKILL` (`skillName` ASC) ,
  CONSTRAINT `fk_EMPSKILL_Employee`
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
  PRIMARY KEY (`shiftID`) ,
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
  PRIMARY KEY (`workingShiftID`) ,
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
  `???` INT NOT NULL ,
  PRIMARY KEY (`???`) )
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
