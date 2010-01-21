SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `WebAgenda` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `WebAgenda`;

-- -----------------------------------------------------
-- Table `WebAgenda`.`jobType`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`jobType` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`jobType` (
  `jobTypeID` INT NOT NULL ,
  `jobTitle` VARCHAR(20) NOT NULL ,
  `jobDescription` VARCHAR(500) NOT NULL ,
  PRIMARY KEY (`jobTypeID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`PermissionSet`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`PermissionSet` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`PermissionSet` (
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
-- Table `WebAgenda`.`Workgroup`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`Workgroup` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`Workgroup` (
  `wkgpID` INT NOT NULL ,
  `wkgpName` VARCHAR(20) NOT NULL ,
  `supervisorID` INT NULL ,
  PRIMARY KEY (`wkgpID`) ,
  INDEX `fk_Workgroup_Employee` (`supervisorID` ASC) ,
  CONSTRAINT `fk_Workgroup_Employee`
    FOREIGN KEY (`supervisorID` )
    REFERENCES `WebAgenda`.`Employee` (`empID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`Employee`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`Employee` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`Employee` (
  `empID` INT NOT NULL ,
  `givenName` VARCHAR(70) NOT NULL ,
  `familyName` VARCHAR(70) NOT NULL ,
  `email` VARCHAR(50) NULL ,
  `username` VARCHAR(20) NOT NULL ,
  `password` VARCHAR(8) NOT NULL ,
  `jobTypeID` INT NOT NULL ,
  `wkgpID` INT NULL ,
  `plevel` INT NOT NULL ,
  `active` BOOLEAN NOT NULL DEFAULT 1 ,
  PRIMARY KEY (`empID`) ,
  INDEX `fk_Employee_jobType` (`jobTypeID` ASC) ,
  INDEX `fk_Employee_PermissionSet` (`plevel` ASC) ,
  INDEX `fk_Employee_Workgroup` (`wkgpID` ASC) ,
  CONSTRAINT `fk_Employee_jobType`
    FOREIGN KEY (`jobTypeID` )
    REFERENCES `WebAgenda`.`jobType` (`jobTypeID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Employee_PermissionSet`
    FOREIGN KEY (`plevel` )
    REFERENCES `WebAgenda`.`PermissionSet` (`plevel` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Employee_Workgroup`
    FOREIGN KEY (`wkgpID` )
    REFERENCES `WebAgenda`.`Workgroup` (`wkgpID` )
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
-- Table `WebAgenda`.`Notification`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`Notification` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`Notification` (
  `notificationID` INT NOT NULL ,
  `senderID` INT NULL ,
  `recipientID` INT NOT NULL ,
  `viewed` BOOLEAN NOT NULL DEFAULT 0 ,
  `message` VARCHAR(300) NOT NULL ,
  `type` VARCHAR(20) NOT NULL ,
  PRIMARY KEY (`notificationID`) ,
  INDEX `fk_Notification_Employee` (`senderID` ASC, `recipientID` ASC) ,
  INDEX `fk_Notification_NotificationType` (`type` ASC) ,
  CONSTRAINT `fk_Notification_Employee`
    FOREIGN KEY (`senderID` , `recipientID` )
    REFERENCES `WebAgenda`.`Employee` (`empID` , `empID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Notification_NotificationType`
    FOREIGN KEY (`type` )
    REFERENCES `WebAgenda`.`NotificationType` (`type` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`Event`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`Event` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`Event` (
  `eventID` INT NOT NULL ,
  `jobTypeID` INT NULL ,
  `wkgpID` INT NULL ,
  `startTime` DATETIME NOT NULL ,
  `endTime` DATETIME NOT NULL ,
  `name` VARCHAR(20) NOT NULL ,
  `description` VARCHAR(300) NULL ,
  PRIMARY KEY (`eventID`) ,
  INDEX `fk_Event_jobType` (`jobTypeID` ASC) ,
  INDEX `fk_Event_Workgroup` (`wkgpID` ASC) ,
  CONSTRAINT `fk_Event_jobType`
    FOREIGN KEY (`jobTypeID` )
    REFERENCES `WebAgenda`.`jobType` (`jobTypeID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Event_Workgroup`
    FOREIGN KEY (`wkgpID` )
    REFERENCES `WebAgenda`.`Workgroup` (`wkgpID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`Schedule`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`Schedule` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`Schedule` (
  `scheduleID` INT NOT NULL ,
  `activeDate` DATE NOT NULL ,
  `endDate` DATE NOT NULL ,
  `description` VARCHAR(100) NULL ,
  PRIMARY KEY (`scheduleID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`ShiftLocation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`ShiftLocation` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`ShiftLocation` (
  `location` VARCHAR(20) NOT NULL ,
  PRIMARY KEY (`location`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`Shift`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`Shift` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`Shift` (
  `shiftID` INT NOT NULL ,
  `empID` INT NOT NULL ,
  `mon_start` TIME NULL ,
  `mon_end` TIME NULL ,
  `tue_start` TIME NULL ,
  `tue_end` TIME NULL ,
  `wed_start` TIME NULL ,
  `wed_end` TIME NULL ,
  `thu_start` TIME NULL ,
  `thu_end` TIME NULL ,
  `fri_start` TIME NULL ,
  `fri_end` TIME NULL ,
  `sat_start` TIME NULL ,
  `sat_end` TIME NULL ,
  `sun_start` TIME NULL ,
  `sun_end` TIME NULL ,
  `location` VARCHAR(20) NOT NULL ,
  PRIMARY KEY (`shiftID`) ,
  INDEX `fk_Shift_Employee` (`empID` ASC) ,
  INDEX `fk_Shift_Location` (`location` ASC) ,
  CONSTRAINT `fk_Shift_Employee`
    FOREIGN KEY (`empID` )
    REFERENCES `WebAgenda`.`Employee` (`empID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Shift_Location`
    FOREIGN KEY (`location` )
    REFERENCES `WebAgenda`.`ShiftLocation` (`location` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`ShiftSched`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`ShiftSched` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`ShiftSched` (
  `scheduleID` INT NOT NULL ,
  `shiftID` INT NOT NULL ,
  PRIMARY KEY (`scheduleID`, `shiftID`) ,
  INDEX `fk_ShiftSched_Shift` (`shiftID` ASC) ,
  INDEX `fk_ShiftSched_Schedule` (`scheduleID` ASC) ,
  CONSTRAINT `fk_ShiftSched_Shift`
    FOREIGN KEY (`shiftID` )
    REFERENCES `WebAgenda`.`Shift` (`shiftID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ShiftSched_Schedule`
    FOREIGN KEY (`scheduleID` )
    REFERENCES `WebAgenda`.`Schedule` (`scheduleID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`WorkgroupRelation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`WorkgroupRelation` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`WorkgroupRelation` (
  `wkgpID` INT NOT NULL ,
  `childWkgpID` INT NOT NULL ,
  PRIMARY KEY (`wkgpID`, `childWkgpID`) ,
  INDEX `fk_WorkgroupRelation_Workgroup` (`wkgpID` ASC, `childWkgpID` ASC) ,
  CONSTRAINT `fk_WorkgroupRelation_Workgroup`
    FOREIGN KEY (`wkgpID` , `childWkgpID` )
    REFERENCES `WebAgenda`.`Workgroup` (`wkgpID` , `wkgpID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
