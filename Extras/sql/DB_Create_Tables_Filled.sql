SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `WebAgenda` ;
CREATE SCHEMA IF NOT EXISTS `WebAgenda` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

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
-- Table `WebAgenda`.`PERMISSIONSET`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`PERMISSIONSET` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`PERMISSIONSET` (
  `plevel` INT NOT NULL ,
  `pversion` CHAR(1) NOT NULL ,
  `canEditSched` TINYINT(1)  NOT NULL DEFAULT 0 ,
  `canReadSched` TINYINT(1)  NOT NULL DEFAULT 1 ,
  `canReadOldSched` TINYINT(1)  NOT NULL DEFAULT 0 ,
  `canManageEmployee` TINYINT(1)  NOT NULL DEFAULT 0 ,
  `canViewResources` TINYINT(1)  NOT NULL DEFAULT 0 ,
  `canChangePermissions` TINYINT(1)  NOT NULL DEFAULT 0 ,
  `canReadLogs` TINYINT(1)  NOT NULL DEFAULT 0 ,
  `canAccessReports` TINYINT(1)  NOT NULL DEFAULT 0 ,
  `canRequestDaysOff` TINYINT(1)  NOT NULL DEFAULT 0 ,
  `maxDaysOff` INT NOT NULL ,
  `canTakeVacations` TINYINT(1)  NOT NULL DEFAULT 0 ,
  `maxVacationDays` INT NOT NULL ,
  `canTakeEmergencyDays` TINYINT(1)  NOT NULL DEFAULT 0 ,
  `canViewInactiveEmps` TINYINT(1)  NOT NULL DEFAULT 0 ,
  `canSendNotifications` TINYINT(1)  NOT NULL DEFAULT 0 ,
  `trusted` INT NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`plevel`, `pversion`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`EMPLOYEE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`EMPLOYEE` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`EMPLOYEE` (
  `empID` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `supID` INT UNSIGNED NULL ,
  `givenName` VARCHAR(70) NOT NULL ,
  `familyName` VARCHAR(70) NOT NULL ,
  `birthDate` DATE NULL ,
  `email` VARCHAR(50) NULL ,
  `username` VARCHAR(20) NOT NULL ,
  `password` VARCHAR(8) NOT NULL ,
  `lastLogin` TIMESTAMP NULL ,
  `prefPosition` VARCHAR(45) NULL ,
  `prefLocation` VARCHAR(45) NULL ,
  `plevel` INT NOT NULL DEFAULT 0 ,
  `pversion` CHAR(1) NOT NULL DEFAULT ' ' ,
  `active` TINYINT(1)  NOT NULL DEFAULT 1 ,
  `passChanged` TINYINT(1)  NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`empID`) ,
  INDEX `fk_EMPLOYEE_LOCATION` (`prefLocation` ASC) ,
  INDEX `fk_EMPLOYEE_POSITION` (`prefPosition` ASC) ,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) ,
  INDEX `empID_IDX` (`empID` ASC) ,
  INDEX `fk_EMPLOYEE_SUPERVISOR` (`supID` ASC) ,
  INDEX `fk_EMPLOYEE_PERMISSIONSET1` (`plevel` ASC, `pversion` ASC) ,
  CONSTRAINT `fk_EMPLOYEE_LOCATION`
    FOREIGN KEY (`prefLocation` )
    REFERENCES `WebAgenda`.`LOCATION` (`locName` )
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_EMPLOYEE_POSITION`
    FOREIGN KEY (`prefPosition` )
    REFERENCES `WebAgenda`.`POSITION` (`positionName` )
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_EMPLOYEE_SUPERVISOR`
    FOREIGN KEY (`supID` )
    REFERENCES `WebAgenda`.`EMPLOYEE` (`empID` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_EMPLOYEE_PERMISSIONSET1`
    FOREIGN KEY (`plevel` , `pversion` )
    REFERENCES `WebAgenda`.`PERMISSIONSET` (`plevel` , `pversion` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`NOTIFICATION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`NOTIFICATION` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`NOTIFICATION` (
  `notificationID` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `senderID` INT UNSIGNED NULL ,
  `recipientID` INT UNSIGNED NOT NULL ,
  `sentTime` TIMESTAMP NOT NULL DEFAULT NOW() ,
  `viewed` TINYINT(1)  NOT NULL DEFAULT 0 ,
  `message` VARCHAR(300) NOT NULL ,
  `type` VARCHAR(20) NOT NULL ,
  PRIMARY KEY (`notificationID`) ,
  INDEX `fk_NOTIFICATION_SENDER` (`senderID` ASC) ,
  INDEX `fk_NOTIFICATION_RECIPIENT` (`recipientID` ASC) ,
  CONSTRAINT `fk_NOTIFICATION_SENDER`
    FOREIGN KEY (`senderID` )
    REFERENCES `WebAgenda`.`EMPLOYEE` (`empID` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_NOTIFICATION_RECIPIENT`
    FOREIGN KEY (`recipientID` )
    REFERENCES `WebAgenda`.`EMPLOYEE` (`empID` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
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
  `empID` INT UNSIGNED NOT NULL ,
  `skillName` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`empID`, `skillName`) ,
  INDEX `fk_EMPSKILL_SKILL` (`skillName` ASC) ,
  INDEX `fk_EMPSKILL_EMPLOYEE` (`empID` ASC) ,
  CONSTRAINT `fk_EMPSKILL_SKILL`
    FOREIGN KEY (`skillName` )
    REFERENCES `WebAgenda`.`SKILL` (`skillName` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_EMPSKILL_EMPLOYEE`
    FOREIGN KEY (`empID` )
    REFERENCES `WebAgenda`.`EMPLOYEE` (`empID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
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
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_POSSKILL_SKILL`
    FOREIGN KEY (`skillName` )
    REFERENCES `WebAgenda`.`SKILL` (`skillName` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`SCHEDULETEMPLATE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`SCHEDULETEMPLATE` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`SCHEDULETEMPLATE` (
  `schedTempID` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `creatorID` INT UNSIGNED NOT NULL ,
  `name` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`schedTempID`) ,
  INDEX `fk_SCHEDULETEMPLATE_EMPLOYEE` (`creatorID` ASC) ,
  UNIQUE INDEX `SCHEDULETEMPLATE_UNIQUE` (`creatorID` ASC, `name` ASC) ,
  CONSTRAINT `fk_SCHEDULETEMPLATE_EMPLOYEE`
    FOREIGN KEY (`creatorID` )
    REFERENCES `WebAgenda`.`EMPLOYEE` (`empID` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`SHIFTTEMPLATE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`SHIFTTEMPLATE` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`SHIFTTEMPLATE` (
  `shiftTempID` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `schedTempID` INT UNSIGNED NOT NULL ,
  `day` INT UNSIGNED NOT NULL ,
  `startTime` TIME NOT NULL ,
  `endTime` TIME NOT NULL ,
  PRIMARY KEY (`shiftTempID`) ,
  INDEX `fk_SHIFT_SHIFTPOS` (`schedTempID` ASC) ,
  UNIQUE INDEX `SHIFTTEMPLATE_UNIQUE` (`schedTempID` ASC, `day` ASC, `startTime` ASC, `endTime` ASC) ,
  CONSTRAINT `fk_SHIFT_SHIFTPOS`
    FOREIGN KEY (`schedTempID` )
    REFERENCES `WebAgenda`.`SCHEDULETEMPLATE` (`schedTempID` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`SHIFTPOS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`SHIFTPOS` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`SHIFTPOS` (
  `shiftTempID` INT UNSIGNED NOT NULL ,
  `positionName` VARCHAR(45) NOT NULL ,
  `posCount` INT UNSIGNED NOT NULL ,
  PRIMARY KEY (`shiftTempID`, `positionName`) ,
  INDEX `fk_SHIFTPOS_SHIFTTEMPLATE` (`shiftTempID` ASC) ,
  INDEX `fk_SHIFTPOS_POSITION` (`positionName` ASC) ,
  CONSTRAINT `fk_SHIFTPOS_SHIFTTEMPLATE`
    FOREIGN KEY (`shiftTempID` )
    REFERENCES `WebAgenda`.`SHIFTTEMPLATE` (`shiftTempID` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SHIFTPOS_POSITION`
    FOREIGN KEY (`positionName` )
    REFERENCES `WebAgenda`.`POSITION` (`positionName` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`SCHEDULE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`SCHEDULE` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`SCHEDULE` (
  `schedID` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `creatorID` INT UNSIGNED NOT NULL ,
  `startDate` DATE NOT NULL ,
  `endDate` DATE NOT NULL ,
  PRIMARY KEY (`schedID`) ,
  INDEX `fk_SCHEDULE_EMPLOYEE` (`creatorID` ASC) ,
  CONSTRAINT `fk_SCHEDULE_EMPLOYEE`
    FOREIGN KEY (`creatorID` )
    REFERENCES `WebAgenda`.`EMPLOYEE` (`empID` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`SHIFT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`SHIFT` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`SHIFT` (
  `shiftID` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `schedID` INT UNSIGNED NOT NULL ,
  `day` INT UNSIGNED NOT NULL ,
  `startTime` TIME NOT NULL ,
  `endTime` TIME NOT NULL ,
  PRIMARY KEY (`shiftID`) ,
  INDEX `fk_SHIFT_SCHEDULE` (`schedID` ASC) ,
  UNIQUE INDEX `SCHEDULE_UNIQUE` (`schedID` ASC, `day` ASC, `startTime` ASC, `endTime` ASC) ,
  CONSTRAINT `fk_SHIFT_SCHEDULE`
    FOREIGN KEY (`schedID` )
    REFERENCES `WebAgenda`.`SCHEDULE` (`schedID` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`SHIFTEMP`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`SHIFTEMP` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`SHIFTEMP` (
  `shiftID` INT UNSIGNED NOT NULL ,
  `empID` INT UNSIGNED NOT NULL ,
  PRIMARY KEY (`shiftID`, `empID`) ,
  INDEX `fk_SHIFTEMP_SHIFT` (`shiftID` ASC) ,
  INDEX `fk_SHIFTEMP_EMPLOYEE` (`empID` ASC) ,
  CONSTRAINT `fk_SHIFTEMP_SHIFT`
    FOREIGN KEY (`shiftID` )
    REFERENCES `WebAgenda`.`SHIFT` (`shiftID` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SHIFTEMP_EMPLOYEE`
    FOREIGN KEY (`empID` )
    REFERENCES `WebAgenda`.`EMPLOYEE` (`empID` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`RULE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`RULE` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`RULE` (
  `ruleID` INT NOT NULL AUTO_INCREMENT ,
  `shiftTempID` INT UNSIGNED NOT NULL ,
  `ruleType` VARCHAR(45) NOT NULL ,
  `ruleValue` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`ruleID`) ,
  INDEX `fk_RULE_SHIFTTEMPLATE` (`shiftTempID` ASC) ,
  CONSTRAINT `fk_RULE_SHIFTTEMPLATE`
    FOREIGN KEY (`shiftTempID` )
    REFERENCES `WebAgenda`.`SHIFTTEMPLATE` (`shiftTempID` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


DROP USER 'WABroker'@'localhost';
CREATE USER 'WABroker'@'localhost' IDENTIFIED BY 'password';

grant SELECT, INSERT, UPDATE, DELETE on TABLE `WebAgenda`.* to 'WABroker'@'localhost';
grant SELECT, LOCK TABLES on *.* to 'WABroker'@'localhost';

FLUSH PRIVILEGES;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `WebAgenda`.`LOCATION`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
insert into `WebAgenda`.`LOCATION` (`locName`, `locDescription`) values ('Mohave Grill', NULL);
insert into `WebAgenda`.`LOCATION` (`locName`, `locDescription`) values ('TestLocation1', NULL);
insert into `WebAgenda`.`LOCATION` (`locName`, `locDescription`) values ('TestLocation2', NULL);
insert into `WebAgenda`.`LOCATION` (`locName`, `locDescription`) values ('TestLocation3', NULL);
insert into `WebAgenda`.`LOCATION` (`locName`, `locDescription`) values ('TestLocation4', NULL);
insert into `WebAgenda`.`LOCATION` (`locName`, `locDescription`) values ('TestLocation5', NULL);
insert into `WebAgenda`.`LOCATION` (`locName`, `locDescription`) values ('TestLocation6', NULL);
insert into `WebAgenda`.`LOCATION` (`locName`, `locDescription`) values ('TestLocation7', NULL);
insert into `WebAgenda`.`LOCATION` (`locName`, `locDescription`) values ('TestLocation8', NULL);

COMMIT;

-- -----------------------------------------------------
-- Data for table `WebAgenda`.`POSITION`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('General Manager', NULL);
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('Executive Chef', NULL);
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('Front of House Mgr.', NULL);
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('Cook', NULL);
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('Waiter', NULL);
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('TestPosition1', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('TestPosition2', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('TestPosition3', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('TestPosition4', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('TestPosition5', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('TestPosition6', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('TestPosition7', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('TestPosition8', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('TestPosition9', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('TestPosition10', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('TestPosition11', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('TestPosition12', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('TestPosition13', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('TestPosition14', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('TestPosition15', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('TestPosition16', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('Supervisor1', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('Supervisor2', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('Supervisor3', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('Supervisor4', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('Manager1', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('Manager2', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('CEO', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('TestPosition17', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('TestPosition18', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('TestPosition19', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('TestPosition20', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('TestPosition21', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('TestPosition22', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('TestPosition23', 'Test');
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('TestPosition24', 'Test');

COMMIT;

-- -----------------------------------------------------
-- Data for table `WebAgenda`.`PERMISSIONSET`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
insert into `WebAgenda`.`PERMISSIONSET` (`plevel`, `pversion`, `canEditSched`, `canReadSched`, `canReadOldSched`, `canManageEmployee`, `canViewResources`, `canChangePermissions`, `canReadLogs`, `canAccessReports`, `canRequestDaysOff`, `maxDaysOff`, `canTakeVacations`, `maxVacationDays`, `canTakeEmergencyDays`, `canViewInactiveEmps`, `canSendNotifications`, `trusted`) values (0, 'a', false, true, false, false, false, false, false, false, false, 0, false, 0, true, false, false, 1);
insert into `WebAgenda`.`PERMISSIONSET` (`plevel`, `pversion`, `canEditSched`, `canReadSched`, `canReadOldSched`, `canManageEmployee`, `canViewResources`, `canChangePermissions`, `canReadLogs`, `canAccessReports`, `canRequestDaysOff`, `maxDaysOff`, `canTakeVacations`, `maxVacationDays`, `canTakeEmergencyDays`, `canViewInactiveEmps`, `canSendNotifications`, `trusted`) values (99, 'a', true, true, true, true, true, true, true, true, true, 5, true, 20, true, true, true, 99);
insert into `WebAgenda`.`PERMISSIONSET` (`plevel`, `pversion`, `canEditSched`, `canReadSched`, `canReadOldSched`, `canManageEmployee`, `canViewResources`, `canChangePermissions`, `canReadLogs`, `canAccessReports`, `canRequestDaysOff`, `maxDaysOff`, `canTakeVacations`, `maxVacationDays`, `canTakeEmergencyDays`, `canViewInactiveEmps`, `canSendNotifications`, `trusted`) values (2, 'a', true, true, false, true, true, false, false, true, true, 5, true, 10, true, false, true, 2);

COMMIT;

-- -----------------------------------------------------
-- Data for table `WebAgenda`.`EMPLOYEE`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
insert into `WebAgenda`.`EMPLOYEE` (`empID`, `supID`, `givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (12314, NULL, 'Chaney', 'Henson', NULL, 'noorin671@gmail.com', 'user1', 'password', NULL, 'General Manager', 'Mohave Grill', 99, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`, `supID`, `givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (28472, 12314, 'Ray', 'Oliver', NULL, NULL, 'user2', 'password', NULL, 'Executive Chef', 'Mohave Grill', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`, `supID`, `givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (29379, 12314, 'Audra', 'Gordon', NULL, NULL, 'user3', 'password', NULL, 'Front of House Mgr.', 'Mohave Grill', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`, `supID`, `givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (38382, 28472, 'Rina', 'Pruitt', NULL, NULL, 'user4', 'password', NULL, 'Cook', 'Mohave Grill', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`, `supID`, `givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (38202, 28472, 'Quinn', 'Hart', NULL, NULL, 'user5', 'password', NULL, 'Cook', 'Mohave Grill', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`, `supID`, `givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (39280, 28472, 'Sierra', 'Dean', NULL, NULL, 'user6', 'password', NULL, 'Cook', 'Mohave Grill', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`, `supID`, `givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (39202, 29379, 'Sylvia', 'Dyer', NULL, NULL, 'user7', 'password', NULL, 'Waiter', 'Mohave Grill', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`, `supID`, `givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (39203, 29379, 'Kay', 'Bates', NULL, NULL, 'user8', 'password', NULL, 'Waiter', 'Mohave Grill', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`, `supID`, `givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (30293, 29379, 'Luke', 'Garrison', NULL, NULL, 'user9', 'password', NULL, 'Waiter', 'Mohave Grill', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`, `supID`, `givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (12345, NULL, 'Noorin', 'Hasan', '1989-06-20', 'noorin671@gmail.com', 'noorin671', 'password', NULL, NULL, NULL, 99, 'a', true, 1);

-- --- INSERT CEO -----
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (1,'Edward', 'Devito', '1973-02-06', 'Edward.R.Devito@dodgit.com', 'edwarDevit1', 'seisah7E', NULL, 'CEO', 'TestLocation1', 99, 'a', true, 1);

-- --- INSERT MANAGERS -----
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (2,1,'Karen', 'Gibson', '1954-09-11', 'Karen.H.Gibson@spambob.com', 'karenGibso2', 'mob8thah', NULL, 'Manager1', 'TestLocation1', 99, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (3,1,'Ronald', 'Smith', '1971-07-01', 'Ronald.P.Smith@dodgit.com', 'ronalSmith3', 'OomoHeiN', NULL, 'Manager2', 'TestLocation3', 99, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (4,1,'Bryant', 'Bryant', '1972-07-31', 'Bryant.C.Bryant@spambob.com', 'bryanBryan4', 'Ot2xieri', NULL, 'Manager1', 'TestLocation5', 99, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (5,1,'Henry', 'Rogers', '1962-02-23', 'Henry.T.Rogers@spambob.com', 'henryRoger5', 'aiquaiSo', NULL, 'Manager2', 'TestLocation7', 99, 'a', true, 1);

-- --- INSERT SUPERVISORS -----
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (6,2,'Maria', 'Melton', '1958-03-14', 'Maria.M.Melton@spambob.com', 'mariaMelto6', 'she7Ie3a', NULL, 'Supervisor1', 'TestLocation1', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (7,2,'Jane', 'Mctaggart', '1974-07-22', 'Jane.D.Mctaggart@trashymail.com', 'janeMctag7', 'OhL0OM6c', NULL, 'Supervisor1', 'TestLocation1', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (8,2,'Robert', 'Noda', '1954-05-09', 'Robert.M.Noda@pookmail.com', 'roberNoda8', 'uThush6o', NULL, 'Supervisor1', 'TestLocation1', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (9,2,'David', 'Cooper', '1978-08-03', 'David.L.Cooper@dodgit.com', 'davidCoope9', 'MeGh1hut', NULL, 'Supervisor1', 'TestLocation1', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (10,2,'Russell', 'Dougherty', '1984-10-19', 'Russell.L.Dougherty@trashymail.com', 'russeDough10', 'shooc0Ei', NULL, 'Supervisor2', 'TestLocation2', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (11,2,'Louise', 'Jennings', '1957-06-23', 'Louise.M.Jennings@mailinator.com', 'louisJenni11', 'zu6Eezah', NULL, 'Supervisor2', 'TestLocation2', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (12,2,'Frederick', 'Horton', '1969-01-02', 'Frederick.T.Horton@pookmail.com', 'fredeHorto12', 'eizu1Ug1', NULL, 'Supervisor2', 'TestLocation2', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (13,2,'Velma', 'Schoenrock', '1959-03-15', 'Velma.C.Schoenrock@dodgit.com', 'velmaSchoe13', 'aph7eiva', NULL, 'Supervisor2', 'TestLocation2', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (14,3,'Jason', 'Stackhouse', '1974-07-28', 'Jason.C.Stackhouse@mailinator.com', 'jasonStack14', 'Foochee6', NULL, 'Supervisor3', 'TestLocation3', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (15,3,'Evan', 'Dionne', '1966-11-20', 'Evan.G.Dionne@dodgit.com', 'evanDionn15', 'oov6iCho', NULL, 'Supervisor3', 'TestLocation3', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (16,3,'Brittany', 'Martin', '1958-06-10', 'Brittany.P.Martin@trashymail.com', 'brittMarti16', 'ooCohw1e', NULL, 'Supervisor3', 'TestLocation3', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (17,3,'Stephen', 'Warne', '1971-08-31', 'Stephen.M.Warne@trashymail.com', 'stephWarne17', 'eiYahM2m', NULL, 'Supervisor3', 'TestLocation3', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (18,3,'Terry', 'Conners', '1959-07-02', 'Terry.J.Conners@pookmail.com', 'terryConne18', 'ReiNgeo2', NULL, 'Supervisor4', 'TestLocation4', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (19,3,'Dwight', 'Mojica', '1954-11-18', 'Dwight.S.Mojica@trashymail.com', 'dwighMojic19', 'ook4heiG', NULL, 'Supervisor4', 'TestLocation4', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (20,3,'Jamie', 'Bogan', '1941-03-02', 'Jamie.M.Bogan@pookmail.com', 'jamieBogan20', 'eeChoog7', NULL, 'Supervisor4', 'TestLocation4', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (21,3,'Ed', 'Newman', '1975-10-24', 'Ed.G.Newman@trashymail.com', 'edNewma21', 'AhGh7eer', NULL, 'Supervisor4', 'TestLocation4', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (22,4,'Heather', 'Castillo', '1966-12-02', 'Heather.R.Castillo@pookmail.com', 'heathCasti22', 'thaik7ya', NULL, 'Supervisor1', 'TestLocation5', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (23,4,'Carla', 'Baldwin', '1964-02-25', 'Carla.C.Baldwin@pookmail.com', 'carlaBaldw23', 'Eifoo2yu', NULL, 'Supervisor1', 'TestLocation5', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (24,4,'Clementine', 'Franklin', '1956-04-09', 'Clementine.J.Franklin@dodgit.com', 'clemeFrank24', 'daatahJ6', NULL, 'Supervisor1', 'TestLocation5', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (25,4,'Deborah', 'Mcfarland', '1954-11-02', 'Deborah.M.Mcfarland@spambob.com', 'deborMcfar25', 'peeWeele', NULL, 'Supervisor1', 'TestLocation5', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (26,4,'Jennifer', 'Rodriguez', '1972-11-11', 'Jennifer.M.Rodriguez@mailinator.com', 'jenniRodri26', 'AhT6zav1', NULL, 'Supervisor2', 'TestLocation6', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (27,4,'Mathew', 'Floyd', '1941-11-26', 'Mathew.A.Floyd@mailinator.com', 'matheFloyd27', 'Zee2gae8', NULL, 'Supervisor2', 'TestLocation6', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (28,4,'Karen', 'Rosenblum', '1965-07-16', 'Karen.S.Rosenblum@pookmail.com', 'karenRosen28', 'jaPh3ahp', NULL, 'Supervisor2', 'TestLocation6', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (29,4,'Virginia', 'Pickard', '1979-05-15', 'Virginia.M.Pickard@dodgit.com', 'virgiPicka29', 'yee0ahQu', NULL, 'Supervisor2', 'TestLocation6', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (30,5,'Anthony', 'Ledet', '1953-06-01', 'Anthony.M.Ledet@mailinator.com', 'anthoLedet30', 'eiZae2ga', NULL, 'Supervisor3', 'TestLocation7', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (31,5,'Shirley', 'Reaves', '1979-05-13', 'Shirley.D.Reaves@trashymail.com', 'shirlReave31', 'uZohWie5', NULL, 'Supervisor3', 'TestLocation7', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (32,5,'Rosa', 'Hayes', '1944-05-06', 'Rosa.H.Hayes@pookmail.com', 'rosaHayes32', 'Qua3eoto', NULL, 'Supervisor3', 'TestLocation7', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (33,5,'Billy', 'Berger', '1981-02-25', 'Billy.L.Berger@dodgit.com', 'billyBerge33', 'aiSooche', NULL, 'Supervisor3', 'TestLocation7', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (34,5,'Brenda', 'Osborne', '1966-07-06', 'Brenda.W.Osborne@spambob.com', 'brendOsbor34', 'teiGhu0k', NULL, 'Supervisor4', 'TestLocation8', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (35,5,'Rebecca', 'Snyder', '1982-02-27', 'Rebecca.T.Snyder@pookmail.com', 'rebecSnyde35', 'Aepae6ai', NULL, 'Supervisor4', 'TestLocation8', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (36,5,'Bette', 'Strong', '1946-05-22', 'Bette.E.Strong@dodgit.com', 'betteStron36', 'Ohsh8iVo', NULL, 'Supervisor4', 'TestLocation8', 2, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (37,5,'Cole', 'Vickers', '1958-01-14', 'Cole.J.Vickers@trashymail.com', 'coleVicke37', 'Ohdoh4la', NULL, 'Supervisor4', 'TestLocation8', 2, 'a', true, 1);

-- --- INSERT EMPLOYEES --------
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (38,6,'Jesse', 'Harmon', '1940-10-18', 'Jesse.S.Harmon@trashymail.com', 'jesseHarmo38', 'ahngohBa', NULL, 'TestPosition1', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (39,6,'Michael', 'Largo', '1944-05-11', 'Michael.B.Largo@spambob.com', 'michaLargo39', 'Eeveesee', NULL, 'TestPosition1', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (40,6,'Lillie', 'Egan', '1984-06-26', 'Lillie.W.Egan@spambob.com', 'lilliEgan40', 'aegoh1co', NULL, 'TestPosition1', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (41,6,'Ronnie', 'Fenton', '1980-03-01', 'Ronnie.M.Fenton@mailinator.com', 'ronniFento41', 'nuiHai4z', NULL, 'TestPosition1', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (42,6,'Evangeline', 'Sanders', '1968-04-12', 'Evangeline.R.Sanders@trashymail.com', 'evangSande42', 'phuLah1o', NULL, 'TestPosition1', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (43,6,'Billy', 'Daniels', '1955-11-25', 'Billy.B.Daniels@dodgit.com', 'billyDanie43', 'Bai4shus', NULL, 'TestPosition2', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (44,6,'Jan', 'Cureton', '1944-02-01', 'Jan.M.Cureton@dodgit.com', 'janCuret44', 'iedoWi2a', NULL, 'TestPosition2', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (45,6,'Bettye', 'Dykes', '1966-08-05', 'Bettye.G.Dykes@trashymail.com', 'bettyDykes45', 'aij4Ahg0', NULL, 'TestPosition2', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (46,6,'Jared', 'Calloway', '1983-03-02', 'Jared.C.Calloway@mailinator.com', 'jaredCallo46', 'ceiXuZiN', NULL, 'TestPosition2', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (47,6,'Catherine', 'Hernandez', '1971-04-22', 'Catherine.F.Hernandez@trashymail.com', 'catheHerna47', 'Moohox5w', NULL, 'TestPosition2', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (48,6,'Donald', 'Hanna', '1969-08-12', 'Donald.E.Hanna@dodgit.com', 'donalHanna48', 'oJia8eek', NULL, 'TestPosition3', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (49,6,'Jane', 'Lee', '1979-11-17', 'Jane.C.Lee@trashymail.com', 'janeLee49', 'ezothaiD', NULL, 'TestPosition3', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (50,6,'Karen', 'Cross', '1983-07-13', 'Karen.N.Cross@dodgit.com', 'karenCross50', 'iu9Xiej5', NULL, 'TestPosition3', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (51,6,'Frank', 'Robertson', '1979-07-12', 'Frank.G.Robertson@spambob.com', 'frankRober51', 'Oleg4ok9', NULL, 'TestPosition3', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (52,6,'Elizabeth', 'Steele', '1961-08-10', 'Elizabeth.H.Steele@dodgit.com', 'elizaSteel52', 'aeThung3', NULL, 'TestPosition3', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (53,7,'Barry', 'Tate', '1949-04-18', 'Barry.J.Tate@trashymail.com', 'barryTate53', 'ohTeiho6', NULL, 'TestPosition4', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (54,7,'Patricia', 'Willoughby', '1960-01-16', 'Patricia.S.Willoughby@spambob.com', 'patriWillo54', 'agh2Aech', NULL, 'TestPosition4', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (55,7,'Kathryn', 'Walker', '1980-10-08', 'Kathryn.D.Walker@dodgit.com', 'kathrWalke55', 'KehaeXoh', NULL, 'TestPosition4', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (56,7,'Mary', 'Rancourt', '1940-07-09', 'Mary.C.Rancourt@pookmail.com', 'maryRanco56', 'eiL2aela', NULL, 'TestPosition4', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (57,7,'Bettie', 'Stpierre', '1984-08-07', 'Bettie.I.Stpierre@trashymail.com', 'bettiStpie57', 'johnguJo', NULL, 'TestPosition4', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (58,7,'Rodrick', 'Quinn', '1968-07-06', 'Rodrick.D.Quinn@trashymail.com', 'rodriQuinn58', 'ThohXele', NULL, 'TestPosition5', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (59,7,'Debra', 'Rose', '1952-01-15', 'Debra.J.Rose@dodgit.com', 'debraRose59', 'Amai6Iga', NULL, 'TestPosition5', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (60,7,'Christine', 'Cardenas', '1951-03-04', 'Christine.J.Cardenas@pookmail.com', 'chrisCarde60', 'eiNei2oo', NULL, 'TestPosition5', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (61,7,'Shannon', 'Holland', '1971-02-18', 'Shannon.E.Holland@mailinator.com', 'shannHolla61', 'eem2Ooth', NULL, 'TestPosition5', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (62,7,'Craig', 'Christmas', '1985-05-12', 'Craig.V.Christmas@trashymail.com', 'craigChris62', 'ok8Eithe', NULL, 'TestPosition5', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (63,7,'Willie', 'Rodriguez', '1970-09-03', 'Willie.W.Rodriguez@dodgit.com', 'williRodri63', 'Ooniid6s', NULL, 'TestPosition6', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (64,7,'Caterina', 'Henderson', '1952-08-06', 'Caterina.P.Henderson@dodgit.com', 'caterHende64', 'iyiaF8ee', NULL, 'TestPosition6', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (65,7,'Kayla', 'Duncan', '1972-11-17', 'Kayla.B.Duncan@mailinator.com', 'kaylaDunca65', 'Theep8qu', NULL, 'TestPosition6', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (66,7,'Marilyn', 'Snider', '1971-09-12', 'Marilyn.M.Snider@dodgit.com', 'marilSnide66', 'waht1ieL', NULL, 'TestPosition6', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (67,7,'Barbara', 'Stanton', '1961-02-09', 'Barbara.G.Stanton@mailinator.com', 'barbaStant67', 'Xahyohye', NULL, 'TestPosition6', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (68,8,'James', 'Ashley', '1977-05-13', 'James.A.Ashley@trashymail.com', 'jamesAshle68', 'uth1Chai', NULL, 'TestPosition7', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (69,8,'James', 'Adams', '1984-07-20', 'James.S.Adams@mailinator.com', 'jamesAdams69', 'eude9bai', NULL, 'TestPosition7', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (70,8,'Michael', 'Velasquez', '1977-11-22', 'Michael.D.Velasquez@pookmail.com', 'michaVelas70', 'quof4Ooq', NULL, 'TestPosition7', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (71,8,'Stephanie', 'Riley', '1976-03-23', 'Stephanie.M.Riley@dodgit.com', 'stephRiley71', 'iCh8epei', NULL, 'TestPosition7', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (72,8,'Lisa', 'Gibson', '1942-10-04', 'Lisa.J.Gibson@pookmail.com', 'lisaGibso72', 'Ohph9eit', NULL, 'TestPosition7', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (73,8,'Flora', 'Snow', '1959-02-18', 'Flora.G.Snow@mailinator.com', 'floraSnow73', 'fiuF9loh', NULL, 'TestPosition8', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (74,8,'Joanne', 'May', '1959-06-12', 'Joanne.L.May@dodgit.com', 'joannMay74', 'Fi2Ratie', NULL, 'TestPosition8', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (75,8,'Jennifer', 'Jennings', '1952-02-15', 'Jennifer.R.Jennings@dodgit.com', 'jenniJenni75', 'ohvoo1ba', NULL, 'TestPosition8', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (76,8,'Jennifer', 'Lane', '1949-03-16', 'Jennifer.D.Lane@dodgit.com', 'jenniLane76', 'ohch1Ahp', NULL, 'TestPosition8', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (77,8,'Katelyn', 'Dudley', '1954-02-02', 'Katelyn.F.Dudley@mailinator.com', 'katelDudle77', 'ooBiamor', NULL, 'TestPosition8', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (78,8,'Sylvia', 'Roach', '1969-07-13', 'Sylvia.R.Roach@pookmail.com', 'sylviRoach78', 'yoij2Iph', NULL, 'TestPosition9', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (79,8,'Jennifer', 'Reichert', '1972-09-03', 'Jennifer.C.Reichert@trashymail.com', 'jenniReich79', 'aicieN1i', NULL, 'TestPosition9', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (80,8,'Wallace', 'Corp', '1981-12-21', 'Wallace.L.Corp@pookmail.com', 'wallaCorp80', 'deiPie6B', NULL, 'TestPosition9', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (81,8,'Carla', 'White', '1974-12-11', 'Carla.M.White@mailinator.com', 'carlaWhite81', 'ahZee0oh', NULL, 'TestPosition9', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (82,8,'Robert', 'Cleveland', '1960-06-23', 'Robert.L.Cleveland@trashymail.com', 'roberCleve82', 'oy5veequ', NULL, 'TestPosition9', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (83,9,'Marilyn', 'Wilburn', '1963-08-01', 'Marilyn.Z.Wilburn@spambob.com', 'marilWilbu83', 'aeG0na8a', NULL, 'TestPosition10', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (84,9,'Kenneth', 'Romo', '1966-05-25', 'Kenneth.J.Romo@pookmail.com', 'kenneRomo84', 'Oo4pahTh', NULL, 'TestPosition10', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (85,9,'Amy', 'Lindsey', '1979-07-04', 'Amy.T.Lindsey@trashymail.com', 'amyLinds85', 'OoG5caiY', NULL, 'TestPosition10', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (86,9,'Cedric', 'Roman', '1981-10-17', 'Cedric.H.Roman@trashymail.com', 'cedriRoman86', 'Jui1uve3', NULL, 'TestPosition10', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (87,9,'Mark', 'Mitchell', '1980-11-08', 'Mark.L.Mitchell@mailinator.com', 'markMitch87', 'eeMaiK1c', NULL, 'TestPosition10', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (88,9,'Bridgette', 'Hummel', '1973-03-06', 'Bridgette.R.Hummel@dodgit.com', 'bridgHumme88', 'eeD0Laeb', NULL, 'TestPosition11', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (89,9,'Leroy', 'Brown', '1969-05-14', 'Leroy.D.Brown@spambob.com', 'leroyBrown89', 'niacheeV', NULL, 'TestPosition11', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (90,9,'Kerry', 'Parke', '1954-12-25', 'Kerry.A.Parke@spambob.com', 'kerryParke90', 'VuLahf0y', NULL, 'TestPosition11', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (91,9,'Heather', 'Ritter', '1945-07-09', 'Heather.H.Ritter@trashymail.com', 'heathRitte91', 'goo1Yi7T', NULL, 'TestPosition11', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (92,9,'Machelle', 'Howell', '1947-04-12', 'Machelle.C.Howell@trashymail.com', 'macheHowel92', 'aimi9Aes', NULL, 'TestPosition11', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (93,9,'Carmela', 'Brendel', '1979-11-16', 'Carmela.S.Brendel@trashymail.com', 'carmeBrend93', 'UTh3daif', NULL, 'TestPosition12', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (94,9,'Tricia', 'Goshorn', '1946-02-19', 'Tricia.G.Goshorn@pookmail.com', 'triciGosho94', 'aige9Wee', NULL, 'TestPosition12', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (95,9,'Mai', 'Torres', '1978-03-04', 'Mai.D.Torres@spambob.com', 'maiTorre95', 'Eethu9Se', NULL, 'TestPosition12', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (96,9,'Pamela', 'Letellier', '1967-02-02', 'Pamela.P.Letellier@pookmail.com', 'pamelLetel96', 'ohgheeYa', NULL, 'TestPosition12', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (97,9,'Michael', 'Core', '1958-04-16', 'Michael.C.Core@spambob.com', 'michaCore97', 'shaeMug0', NULL, 'TestPosition12', 'TestLocation1', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (98,10,'Maria', 'Armbruster', '1959-07-31', 'Maria.M.Armbruster@mailinator.com', 'mariaArmbr98', 'AeJoo1so', NULL, 'TestPosition13', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (99,10,'Francis', 'Patterson', '1979-01-13', 'Francis.T.Patterson@dodgit.com', 'francPatte99', 'eiPhouFu', NULL, 'TestPosition13', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (100,10,'Pamela', 'Giddings', '1941-12-19', 'Pamela.B.Giddings@dodgit.com', 'pamelGiddi100', 'oo4ushue', NULL, 'TestPosition13', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (101,10,'Marvin', 'Garcia', '1948-01-13', 'Marvin.G.Garcia@mailinator.com', 'marviGarci101', 'Thi4shai', NULL, 'TestPosition13', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (102,10,'Thelma', 'Redding', '1958-03-04', 'Thelma.F.Redding@spambob.com', 'thelmReddi102', 'Rip5eeva', NULL, 'TestPosition13', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (103,10,'Robert', 'Callaway', '1969-07-30', 'Robert.C.Callaway@pookmail.com', 'roberCalla103', 'ueKeus4a', NULL, 'TestPosition14', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (104,10,'John', 'Thaxton', '1954-08-29', 'John.O.Thaxton@trashymail.com', 'johnThaxt104', 'JaeLae7i', NULL, 'TestPosition14', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (105,10,'Dorothy', 'Carter', '1957-03-22', 'Dorothy.R.Carter@dodgit.com', 'dorotCarte105', 'neiC9aam', NULL, 'TestPosition14', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (106,10,'James', 'Castleberry', '1940-03-19', 'James.D.Castleberry@trashymail.com', 'jamesCastl106', 'eCaesheo', NULL, 'TestPosition14', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (107,10,'Scott', 'Dosch', '1941-10-24', 'Scott.R.Dosch@mailinator.com', 'scottDosch107', 'ueJ8aH8a', NULL, 'TestPosition14', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (108,10,'Jill', 'Rodriguez', '1942-08-31', 'Jill.W.Rodriguez@spambob.com', 'jillRodri108', 'eesh1poo', NULL, 'TestPosition15', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (109,10,'Florine', 'Delvalle', '1958-10-25', 'Florine.I.Delvalle@pookmail.com', 'floriDelva109', 'xuaz5see', NULL, 'TestPosition15', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (110,10,'Laura', 'Nunez', '1947-10-21', 'Laura.G.Nunez@spambob.com', 'lauraNunez110', 'ooGaequ4', NULL, 'TestPosition15', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (111,10,'Terra', 'Smith', '1956-07-23', 'Terra.S.Smith@dodgit.com', 'terraSmith111', 'ahl9liXo', NULL, 'TestPosition15', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (112,10,'Edna', 'Quinn', '1969-02-17', 'Edna.J.Quinn@trashymail.com', 'ednaQuinn112', 'Eic4chup', NULL, 'TestPosition15', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (113,11,'Angela', 'Smith', '1959-05-29', 'Angela.S.Smith@spambob.com', 'angelSmith113', 'oht1Shai', NULL, 'TestPosition16', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (114,11,'Kristen', 'Milano', '1968-11-29', 'Kristen.G.Milano@spambob.com', 'kristMilan114', 'ieThahQu', NULL, 'TestPosition16', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (115,11,'Tammy', 'Guy', '1970-09-26', 'Tammy.M.Guy@spambob.com', 'tammyGuy115', 'iew1koSh', NULL, 'TestPosition16', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (116,11,'Sarah', 'Vanleer', '1942-04-09', 'Sarah.R.Vanleer@dodgit.com', 'sarahVanle116', 'ab1AhPh0', NULL, 'TestPosition16', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (117,11,'Melanie', 'Stpierre', '1961-07-05', 'Melanie.S.Stpierre@dodgit.com', 'melanStpie117', 'hohF8sah', NULL, 'TestPosition16', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (118,11,'John', 'Schindler', '1961-10-26', 'John.L.Schindler@trashymail.com', 'johnSchin118', 'ThoojooY', NULL, 'TestPosition17', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (119,11,'Valentin', 'Gillian', '1951-09-09', 'Valentin.L.Gillian@spambob.com', 'valenGilli119', 'Taitoxee', NULL, 'TestPosition17', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (120,11,'Jose', 'Benitez', '1982-06-23', 'Jose.G.Benitez@spambob.com', 'joseBenit120', 'Oox4ne1D', NULL, 'TestPosition17', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (121,11,'Cindy', 'Hicks', '1956-01-06', 'Cindy.F.Hicks@dodgit.com', 'cindyHicks121', 'ieShu2ke', NULL, 'TestPosition17', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (122,11,'Debra', 'Hopkins', '1960-12-17', 'Debra.J.Hopkins@spambob.com', 'debraHopki122', 'ek1Shae3', NULL, 'TestPosition17', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (123,11,'Jeanne', 'Schoen', '1981-01-09', 'Jeanne.S.Schoen@dodgit.com', 'jeannSchoe123', 'oMeichah', NULL, 'TestPosition18', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (124,11,'Kimberly', 'Mcfadden', '1971-05-05', 'Kimberly.M.Mcfadden@trashymail.com', 'kimbeMcfad124', 'aeHe4oj2', NULL, 'TestPosition18', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (125,11,'John', 'Chabot', '1973-11-15', 'John.L.Chabot@mailinator.com', 'johnChabo125', 'OXeiPaim', NULL, 'TestPosition18', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (126,11,'Clara', 'Davidson', '1943-11-30', 'Clara.J.Davidson@dodgit.com', 'claraDavid126', 'eiZooz2d', NULL, 'TestPosition18', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (127,11,'Rose', 'Stanley', '1976-02-08', 'Rose.C.Stanley@mailinator.com', 'roseStanl127', 'yaX4Sheg', NULL, 'TestPosition18', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (128,12,'Misty', 'Floyd', '1951-10-13', 'Misty.L.Floyd@spambob.com', 'mistyFloyd128', 'yoh6Kah2', NULL, 'TestPosition19', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (129,12,'Raymond', 'Johnson', '1969-11-16', 'Raymond.A.Johnson@pookmail.com', 'raymoJohns129', 'OT4quae1', NULL, 'TestPosition19', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (130,12,'John', 'Blanchard', '1962-08-13', 'John.S.Blanchard@trashymail.com', 'johnBlanc130', 'oozaigh2', NULL, 'TestPosition19', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (131,12,'Michael', 'Hinds', '1985-06-08', 'Michael.B.Hinds@spambob.com', 'michaHinds131', 'uaN5uNge', NULL, 'TestPosition19', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (132,12,'Harold', 'Beard', '1960-06-14', 'Harold.F.Beard@mailinator.com', 'harolBeard132', 'fei7Xagh', NULL, 'TestPosition19', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (133,12,'John', 'Mercado', '1967-05-31', 'John.A.Mercado@trashymail.com', 'johnMerca133', 'kohy5Eic', NULL, 'TestPosition20', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (134,12,'Jack', 'Lyles', '1980-07-11', 'Jack.V.Lyles@trashymail.com', 'jackLyles134', 'eech4Eek', NULL, 'TestPosition20', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (135,12,'Shirley', 'Loggins', '1954-10-13', 'Shirley.C.Loggins@pookmail.com', 'shirlLoggi135', 'aeN3rae9', NULL, 'TestPosition20', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (136,12,'Shawn', 'Ali', '1945-02-23', 'Shawn.S.Ali@spambob.com', 'shawnAli136', 'ooN4bei0', NULL, 'TestPosition20', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (137,12,'Lida', 'Melgoza', '1962-01-24', 'Lida.F.Melgoza@spambob.com', 'lidaMelgo137', 'Einataek', NULL, 'TestPosition20', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (138,12,'James', 'Guzman', '1950-01-11', 'James.B.Guzman@trashymail.com', 'jamesGuzma138', 'Ahsah1oi', NULL, 'TestPosition21', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (139,12,'Candace', 'Jackson', '1967-01-12', 'Candace.W.Jackson@dodgit.com', 'candaJacks139', 'gie9Laif', NULL, 'TestPosition21', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (140,12,'Seth', 'Cochran', '1979-02-10', 'Seth.O.Cochran@mailinator.com', 'sethCochr140', 'vich8uTh', NULL, 'TestPosition21', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (141,12,'Victoria', 'Jones', '1959-10-02', 'Victoria.R.Jones@dodgit.com', 'victoJones141', 'aBefeeye', NULL, 'TestPosition21', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (142,12,'Nancy', 'Smith', '1941-12-15', 'Nancy.P.Smith@trashymail.com', 'nancySmith142', 'Thaingae', NULL, 'TestPosition21', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (143,13,'William', 'Carranza', '1973-02-21', 'William.C.Carranza@pookmail.com', 'williCarra143', 'Ej9uPohg', NULL, 'TestPosition22', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (144,13,'Thelma', 'Mcwilliams', '1961-10-24', 'Thelma.P.Mcwilliams@pookmail.com', 'thelmMcwil144', 'Caimok2E', NULL, 'TestPosition22', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (145,13,'Ruth', 'Buss', '1955-05-28', 'Ruth.J.Buss@pookmail.com', 'ruthBuss145', 'ba4aeCu9', NULL, 'TestPosition22', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (146,13,'Mark', 'Stiles', '1983-01-05', 'Mark.T.Stiles@spambob.com', 'markStile146', 'ieQu5iRe', NULL, 'TestPosition22', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (147,13,'Ralph', 'Cantu', '1961-06-16', 'Ralph.M.Cantu@mailinator.com', 'ralphCantu147', 'tuiNg8ee', NULL, 'TestPosition22', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (148,13,'Frank', 'Eaton', '1971-09-26', 'Frank.F.Eaton@trashymail.com', 'frankEaton148', 'teiNgu3W', NULL, 'TestPosition23', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (149,13,'Donna', 'Newell', '1967-12-15', 'Donna.T.Newell@spambob.com', 'donnaNewel149', 'enahSeiF', NULL, 'TestPosition23', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (150,13,'David', 'Miller', '1955-04-01', 'David.P.Miller@pookmail.com', 'davidMille150', 'eiwah9Ai', NULL, 'TestPosition23', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (151,13,'Ettie', 'Morales', '1982-02-07', 'Ettie.J.Morales@mailinator.com', 'ettieMoral151', 'shaeX8da', NULL, 'TestPosition23', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (152,13,'Kory', 'Albright', '1958-12-27', 'Kory.J.Albright@dodgit.com', 'koryAlbri152', 'eecooL5a', NULL, 'TestPosition23', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (153,13,'Allison', 'Scott', '1973-01-22', 'Allison.R.Scott@pookmail.com', 'allisScott153', 'woh7ev7L', NULL, 'TestPosition24', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (154,13,'Tyson', 'Bullock', '1955-01-29', 'Tyson.D.Bullock@pookmail.com', 'tysonBullo154', 'eiX9Ilei', NULL, 'TestPosition24', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (155,13,'Lee', 'Chapman', '1980-10-06', 'Lee.R.Chapman@mailinator.com', 'leeChapm155', 'ouw3Eine', NULL, 'TestPosition24', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (156,13,'Juanita', 'Vasquez', '1973-04-29', 'Juanita.R.Vasquez@spambob.com', 'juaniVasqu156', 'echi8Din', NULL, 'TestPosition24', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (157,13,'Rex', 'Pedigo', '1973-04-03', 'Rex.D.Pedigo@mailinator.com', 'rexPedig157', 'Rov0Oote', NULL, 'TestPosition24', 'TestLocation2', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (158,14,'Rhonda', 'Filson', '1978-11-29', 'Rhonda.M.Filson@spambob.com', 'rhondFilso158', 'ahph3Aeh', NULL, 'TestPosition1', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (159,14,'Juan', 'Kerner', '1956-10-24', 'Juan.M.Kerner@dodgit.com', 'juanKerne159', 'aeB8Cei4', NULL, 'TestPosition1', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (160,14,'Bradley', 'Poulin', '1951-08-25', 'Bradley.I.Poulin@dodgit.com', 'bradlPouli160', 'yai3uRah', NULL, 'TestPosition1', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (161,14,'David', 'Johnson', '1947-11-30', 'David.C.Johnson@dodgit.com', 'davidJohns161', 'unee2Shi', NULL, 'TestPosition1', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (162,14,'Robert', 'Chavez', '1961-07-19', 'Robert.A.Chavez@spambob.com', 'roberChave162', 'ooF7phoh', NULL, 'TestPosition1', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (163,14,'Nancy', 'Reed', '1967-02-10', 'Nancy.F.Reed@mailinator.com', 'nancyReed163', 'Aitheeg5', NULL, 'TestPosition2', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (164,14,'Gary', 'Tatum', '1969-05-18', 'Gary.D.Tatum@mailinator.com', 'garyTatum164', 'heghahR4', NULL, 'TestPosition2', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (165,14,'Rachel', 'Goodman', '1976-12-24', 'Rachel.E.Goodman@mailinator.com', 'racheGoodm165', 'roo5kugh', NULL, 'TestPosition2', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (166,14,'Mary', 'Phan', '1945-12-27', 'Mary.S.Phan@dodgit.com', 'maryPhan166', 'ZieReeda', NULL, 'TestPosition2', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (167,14,'Mary', 'Moore', '1976-03-20', 'Mary.A.Moore@spambob.com', 'maryMoore167', 'iNg5thae', NULL, 'TestPosition2', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (168,14,'Travis', 'Mcmahan', '1947-09-17', 'Travis.S.Mcmahan@spambob.com', 'traviMcmah168', 'quoo5iXa', NULL, 'TestPosition3', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (169,14,'Debbie', 'Popham', '1977-04-14', 'Debbie.K.Popham@pookmail.com', 'debbiPopha169', 'Eeth7rie', NULL, 'TestPosition3', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (170,14,'Milton', 'Garrison', '1976-12-15', 'Milton.T.Garrison@spambob.com', 'miltoGarri170', 'aphi8She', NULL, 'TestPosition3', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (171,14,'Eugene', 'Tucker', '1976-04-17', 'Eugene.V.Tucker@pookmail.com', 'eugenTucke171', 'shis3De5', NULL, 'TestPosition3', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (172,14,'Robert', 'Ware', '1947-10-29', 'Robert.P.Ware@pookmail.com', 'roberWare172', 'yah4Eilo', NULL, 'TestPosition3', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (173,15,'Dolores', 'Davenport', '1941-06-22', 'Dolores.D.Davenport@dodgit.com', 'dolorDaven173', 'ahn1Ooz7', NULL, 'TestPosition4', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (174,15,'Joshua', 'Bailey', '1973-06-11', 'Joshua.J.Bailey@dodgit.com', 'joshuBaile174', 'quaire3Z', NULL, 'TestPosition4', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (175,15,'Douglas', 'Whitman', '1957-08-26', 'Douglas.L.Whitman@pookmail.com', 'douglWhitm175', 'ox3kaidu', NULL, 'TestPosition4', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (176,15,'Dorothy', 'Mill', '1952-01-22', 'Dorothy.B.Mill@dodgit.com', 'dorotMill176', 'rie8oht4', NULL, 'TestPosition4', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (177,15,'Cathy', 'Lopez', '1950-12-27', 'Cathy.D.Lopez@dodgit.com', 'cathyLopez177', 'Ahl1voh0', NULL, 'TestPosition4', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (178,15,'Carolyn', 'Clark', '1964-08-04', 'Carolyn.A.Clark@spambob.com', 'carolClark178', 'OeR0oomo', NULL, 'TestPosition5', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (179,15,'Diana', 'Whitworth', '1971-10-30', 'Diana.M.Whitworth@trashymail.com', 'dianaWhitw179', 'omah5Bua', NULL, 'TestPosition5', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (180,15,'Benny', 'Casillas', '1946-11-02', 'Benny.D.Casillas@pookmail.com', 'bennyCasil180', 'ohk8Koh8', NULL, 'TestPosition5', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (181,15,'John', 'Simon', '1983-03-05', 'John.P.Simon@pookmail.com', 'johnSimon181', 'Iephooqu', NULL, 'TestPosition5', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (182,15,'Nancy', 'Tipps', '1976-01-16', 'Nancy.W.Tipps@mailinator.com', 'nancyTipps182', 'eaQua7ca', NULL, 'TestPosition5', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (183,15,'Carolina', 'Mccauley', '1974-09-06', 'Carolina.J.Mccauley@trashymail.com', 'carolMccau183', 'Loar8chi', NULL, 'TestPosition6', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (184,15,'Cathy', 'Jimenez', '1967-04-06', 'Cathy.J.Jimenez@trashymail.com', 'cathyJimen184', 'Nahw2nae', NULL, 'TestPosition6', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (185,15,'Gertrude', 'Moreno', '1979-06-29', 'Gertrude.J.Moreno@mailinator.com', 'gertrMoren185', 'Biosee7o', NULL, 'TestPosition6', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (186,15,'Effie', 'Esters', '1980-11-10', 'Effie.A.Esters@dodgit.com', 'effieEster186', 'Ohgi4Wo2', NULL, 'TestPosition6', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (187,15,'Pauline', 'Wynn', '1978-10-21', 'Pauline.F.Wynn@spambob.com', 'pauliWynn187', 'se8Yaev5', NULL, 'TestPosition6', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (188,16,'Charles', 'Wimbley', '1953-01-24', 'Charles.M.Wimbley@pookmail.com', 'charlWimbl188', 'waeFie2m', NULL, 'TestPosition7', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (189,16,'Ines', 'Petty', '1949-08-02', 'Ines.J.Petty@mailinator.com', 'inesPetty189', 'noo3Sheg', NULL, 'TestPosition7', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (190,16,'Nancy', 'Nolan', '1967-11-12', 'Nancy.D.Nolan@pookmail.com', 'nancyNolan190', 'KaluChee', NULL, 'TestPosition7', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (191,16,'Nancy', 'Smith', '1980-02-27', 'Nancy.K.Smith@spambob.com', 'nancySmith191', 'eu1Ahyoo', NULL, 'TestPosition7', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (192,16,'Samuel', 'Cordero', '1981-01-19', 'Samuel.I.Cordero@trashymail.com', 'samueCorde192', 'Chai2ahk', NULL, 'TestPosition7', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (193,16,'Leanna', 'Miller', '1973-10-18', 'Leanna.B.Miller@spambob.com', 'leannMille193', 'peichoh1', NULL, 'TestPosition8', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (194,16,'Matthew', 'Sharp', '1983-02-14', 'Matthew.I.Sharp@trashymail.com', 'matthSharp194', 'Ixaipae0', NULL, 'TestPosition8', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (195,16,'Kevin', 'Goudeau', '1943-11-08', 'Kevin.J.Goudeau@spambob.com', 'kevinGoude195', 'geiFei6x', NULL, 'TestPosition8', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (196,16,'Vivian', 'Ellis', '1943-10-05', 'Vivian.J.Ellis@pookmail.com', 'viviaEllis196', 'mi0biez4', NULL, 'TestPosition8', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (197,16,'Elizabeth', 'Turner', '1952-06-19', 'Elizabeth.B.Turner@mailinator.com', 'elizaTurne197', 'uaPhe8ee', NULL, 'TestPosition8', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (198,16,'Wendy', 'Fields', '1981-06-25', 'Wendy.L.Fields@dodgit.com', 'wendyField198', 'kiel6Oop', NULL, 'TestPosition9', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (199,16,'Ruth', 'Hull', '1959-05-01', 'Ruth.N.Hull@pookmail.com', 'ruthHull199', 'ohy8Phoh', NULL, 'TestPosition9', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (200,16,'James', 'Champlin', '1955-02-13', 'James.R.Champlin@mailinator.com', 'jamesChamp200', 'AiX4oroo', NULL, 'TestPosition9', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (201,16,'Debra', 'Haubrich', '1954-09-28', 'Debra.S.Haubrich@mailinator.com', 'debraHaubr201', 'fe0Deiph', NULL, 'TestPosition9', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (202,16,'Richard', 'Milam', '1981-05-21', 'Richard.M.Milam@trashymail.com', 'richaMilam202', 'ooghai2O', NULL, 'TestPosition9', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (203,17,'Mildred', 'Cain', '1960-05-01', 'Mildred.I.Cain@trashymail.com', 'mildrCain203', 'hohChoo9', NULL, 'TestPosition10', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (204,17,'Florence', 'Kleiman', '1979-11-06', 'Florence.T.Kleiman@mailinator.com', 'floreKleim204', 'raiKae9r', NULL, 'TestPosition10', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (205,17,'Derek', 'Parks', '1958-08-18', 'Derek.E.Parks@pookmail.com', 'derekParks205', 'ohZeikoo', NULL, 'TestPosition10', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (206,17,'Odessa', 'Bolt', '1973-06-27', 'Odessa.R.Bolt@dodgit.com', 'odessBolt206', 'oyi1ievi', NULL, 'TestPosition10', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (207,17,'Evan', 'Guy', '1980-08-10', 'Evan.M.Guy@spambob.com', 'evanGuy207', 'EephieV0', NULL, 'TestPosition10', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (208,17,'Marvin', 'Robinson', '1969-04-29', 'Marvin.L.Robinson@dodgit.com', 'marviRobin208', 'ie2QueeB', NULL, 'TestPosition11', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (209,17,'Juan', 'Carico', '1985-04-13', 'Juan.V.Carico@dodgit.com', 'juanCaric209', 'igh6ke6L', NULL, 'TestPosition11', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (210,17,'Joseph', 'Cohen', '1966-08-17', 'Joseph.P.Cohen@mailinator.com', 'josepCohen210', 'oopah4Da', NULL, 'TestPosition11', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (211,17,'Dustin', 'Penniman', '1952-03-07', 'Dustin.C.Penniman@mailinator.com', 'dustiPenni211', 'ed0Vae7w', NULL, 'TestPosition11', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (212,17,'James', 'Richmond', '1941-12-05', 'James.S.Richmond@trashymail.com', 'jamesRichm212', 'ooCh5nuG', NULL, 'TestPosition11', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (213,17,'Ruben', 'Groleau', '1948-12-17', 'Ruben.K.Groleau@spambob.com', 'rubenGrole213', 'pheiY9Ei', NULL, 'TestPosition12', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (214,17,'Craig', 'Cox', '1944-06-13', 'Craig.M.Cox@trashymail.com', 'craigCox214', 'maCheeM0', NULL, 'TestPosition12', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (215,17,'Charles', 'Peavler', '1948-11-19', 'Charles.D.Peavler@mailinator.com', 'charlPeavl215', 'Eekahgh3', NULL, 'TestPosition12', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (216,17,'Cora', 'Pharr', '1966-08-15', 'Cora.R.Pharr@spambob.com', 'coraPharr216', 'si4Ua4to', NULL, 'TestPosition12', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (217,17,'Kimberly', 'Butts', '1942-06-04', 'Kimberly.R.Butts@dodgit.com', 'kimbeButts217', 'abair2ie', NULL, 'TestPosition12', 'TestLocation3', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (218,18,'Ronald', 'Kennedy', '1946-09-15', 'Ronald.J.Kennedy@trashymail.com', 'ronalKenne218', 'Sheechie', NULL, 'TestPosition13', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (219,18,'George', 'Davidson', '1971-05-10', 'George.S.Davidson@dodgit.com', 'georgDavid219', 'Bohnu6Ai', NULL, 'TestPosition13', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (220,18,'William', 'Gomez', '1977-07-01', 'William.J.Gomez@mailinator.com', 'williGomez220', 'ooNoh8Ua', NULL, 'TestPosition13', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (221,18,'Mildred', 'Cummings', '1976-11-02', 'Mildred.G.Cummings@trashymail.com', 'mildrCummi221', 'rooxaiGh', NULL, 'TestPosition13', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (222,18,'Paul', 'Spray', '1956-12-25', 'Paul.V.Spray@pookmail.com', 'paulSpray222', 'thee9uTh', NULL, 'TestPosition13', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (223,18,'Carlos', 'Hill', '1954-04-17', 'Carlos.M.Hill@spambob.com', 'carloHill223', 'mieGheWe', NULL, 'TestPosition14', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (224,18,'Enriqueta', 'Comer', '1956-02-02', 'Enriqueta.J.Comer@pookmail.com', 'enriqComer224', 'agooshai', NULL, 'TestPosition14', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (225,18,'Michelle', 'Winkler', '1942-02-28', 'Michelle.M.Winkler@mailinator.com', 'micheWinkl225', 'agh0Ashe', NULL, 'TestPosition14', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (226,18,'Betty', 'Gibson', '1985-09-26', 'Betty.S.Gibson@pookmail.com', 'bettyGibso226', 'Ein1thij', NULL, 'TestPosition14', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (227,18,'Vera', 'Jackson', '1955-12-02', 'Vera.R.Jackson@dodgit.com', 'veraJacks227', 'jaer1ohZ', NULL, 'TestPosition14', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (228,18,'Edwina', 'Stiles', '1971-12-24', 'Edwina.D.Stiles@dodgit.com', 'edwinStile228', 'Iechahne', NULL, 'TestPosition15', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (229,18,'Mark', 'Hall', '1953-04-05', 'Mark.L.Hall@dodgit.com', 'markHall229', 'Fi9phaep', NULL, 'TestPosition15', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (230,18,'James', 'Jones', '1967-09-14', 'James.R.Jones@dodgit.com', 'jamesJones230', 'Jef6aes4', NULL, 'TestPosition15', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (231,18,'Jose', 'Hodge', '1968-06-27', 'Jose.M.Hodge@pookmail.com', 'joseHodge231', 'regoX9ai', NULL, 'TestPosition15', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (232,18,'Dennis', 'Hairston', '1948-08-21', 'Dennis.L.Hairston@dodgit.com', 'denniHairs232', 'neeleife', NULL, 'TestPosition15', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (233,19,'Twila', 'Green', '1950-07-23', 'Twila.J.Green@spambob.com', 'twilaGreen233', 'ohbahLo6', NULL, 'TestPosition16', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (234,19,'Jeremiah', 'Cook', '1970-10-18', 'Jeremiah.I.Cook@mailinator.com', 'jeremCook234', 'ooch2Eir', NULL, 'TestPosition16', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (235,19,'Sandra', 'Nelson', '1972-06-27', 'Sandra.D.Nelson@dodgit.com', 'sandrNelso235', 'Quai3ipi', NULL, 'TestPosition16', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (236,19,'Donald', 'Bennett', '1965-02-05', 'Donald.C.Bennett@trashymail.com', 'donalBenne236', 'Ke9yoMei', NULL, 'TestPosition16', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (237,19,'Laurie', 'Hicks', '1952-04-22', 'Laurie.S.Hicks@trashymail.com', 'lauriHicks237', 'bo3iuFec', NULL, 'TestPosition16', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (238,19,'Christine', 'Williams', '1955-11-30', 'Christine.J.Williams@pookmail.com', 'chrisWilli238', 'ohGhie5P', NULL, 'TestPosition17', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (239,19,'Jason', 'Solberg', '1954-07-27', 'Jason.A.Solberg@spambob.com', 'jasonSolbe239', 'ief8eeC3', NULL, 'TestPosition17', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (240,19,'Aron', 'Collins', '1967-10-31', 'Aron.N.Collins@spambob.com', 'aronColli240', 'chiChuph', NULL, 'TestPosition17', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (241,19,'Meghan', 'Oyler', '1948-10-19', 'Meghan.K.Oyler@pookmail.com', 'meghaOyler241', 'eeGhaix3', NULL, 'TestPosition17', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (242,19,'Kenneth', 'Chavez', '1948-01-03', 'Kenneth.D.Chavez@spambob.com', 'kenneChave242', 'tuThoh4y', NULL, 'TestPosition17', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (243,19,'Raymond', 'Craine', '1944-02-04', 'Raymond.V.Craine@pookmail.com', 'raymoCrain243', 'moa3laYa', NULL, 'TestPosition18', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (244,19,'Napoleon', 'Merrill', '1983-07-23', 'Napoleon.M.Merrill@trashymail.com', 'napolMerri244', 'iPheLoog', NULL, 'TestPosition18', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (245,19,'Loraine', 'Ellis', '1967-01-10', 'Loraine.J.Ellis@trashymail.com', 'loraiEllis245', 'eiF6soh6', NULL, 'TestPosition18', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (246,19,'Perry', 'Valdez', '1971-07-17', 'Perry.D.Valdez@pookmail.com', 'perryValde246', 'aetheibi', NULL, 'TestPosition18', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (247,19,'George', 'Rodriguez', '1969-02-20', 'George.S.Rodriguez@spambob.com', 'georgRodri247', 'iuf2eili', NULL, 'TestPosition18', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (248,20,'Eileen', 'Armstrong', '1957-07-27', 'Eileen.H.Armstrong@mailinator.com', 'eileeArmst248', 'eng4ahZa', NULL, 'TestPosition19', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (249,20,'Robin', 'Alderson', '1955-11-05', 'Robin.B.Alderson@mailinator.com', 'robinAlder249', 'ka5Jaht4', NULL, 'TestPosition19', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (250,20,'Laura', 'Mcdougal', '1940-05-07', 'Laura.M.Mcdougal@spambob.com', 'lauraMcdou250', 'vooKeeg5', NULL, 'TestPosition19', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (251,20,'John', 'Uribe', '1975-07-04', 'John.J.Uribe@spambob.com', 'johnUribe251', 'chei8Moo', NULL, 'TestPosition19', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (252,20,'Carmen', 'Young', '1970-04-12', 'Carmen.D.Young@spambob.com', 'carmeYoung252', 'aHie9ees', NULL, 'TestPosition19', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (253,20,'Edward', 'Conrad', '1971-09-22', 'Edward.V.Conrad@trashymail.com', 'edwarConra253', 'Eizahpuo', NULL, 'TestPosition20', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (254,20,'Seymour', 'Pryor', '1956-06-11', 'Seymour.V.Pryor@spambob.com', 'seymoPryor254', 'eu8AeX5l', NULL, 'TestPosition20', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (255,20,'Kathy', 'Christian', '1945-07-21', 'Kathy.T.Christian@dodgit.com', 'kathyChris255', 'sooy4ahr', NULL, 'TestPosition20', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (256,20,'Miles', 'Scott', '1975-04-27', 'Miles.F.Scott@mailinator.com', 'milesScott256', 'meeyaR4z', NULL, 'TestPosition20', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (257,20,'Rosalinda', 'Siefert', '1940-07-25', 'Rosalinda.D.Siefert@trashymail.com', 'rosalSiefe257', 'Eyeedu8t', NULL, 'TestPosition20', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (258,20,'Christine', 'Ricks', '1984-02-21', 'Christine.R.Ricks@spambob.com', 'chrisRicks258', 'emo7Ahk7', NULL, 'TestPosition21', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (259,20,'Annie', 'Robinson', '1975-02-14', 'Annie.C.Robinson@trashymail.com', 'annieRobin259', 'Tee7oob5', NULL, 'TestPosition21', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (260,20,'Denise', 'Webster', '1963-05-26', 'Denise.J.Webster@mailinator.com', 'denisWebst260', 'xo5Eej5P', NULL, 'TestPosition21', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (261,20,'Keith', 'Stephens', '1955-02-06', 'Keith.J.Stephens@trashymail.com', 'keithSteph261', 'oangefee', NULL, 'TestPosition21', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (262,20,'Gloria', 'Alvarez', '1958-11-28', 'Gloria.J.Alvarez@mailinator.com', 'gloriAlvar262', 'jeiXie7o', NULL, 'TestPosition21', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (263,21,'Jennifer', 'Moore', '1983-08-16', 'Jennifer.M.Moore@spambob.com', 'jenniMoore263', 'ahn7Iegh', NULL, 'TestPosition22', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (264,21,'Jim', 'Latham', '1981-07-08', 'Jim.C.Latham@spambob.com', 'jimLatha264', 'um0Heiye', NULL, 'TestPosition22', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (265,21,'Maritza', 'Sensabaugh', '1948-03-30', 'Maritza.R.Sensabaugh@trashymail.com', 'maritSensa265', 'yahwei9I', NULL, 'TestPosition22', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (266,21,'Patricia', 'Key', '1943-03-20', 'Patricia.B.Key@trashymail.com', 'patriKey266', 'aiR0eey2', NULL, 'TestPosition22', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (267,21,'Allen', 'Dubois', '1961-01-07', 'Allen.C.Dubois@trashymail.com', 'allenDuboi267', 'uot8exiS', NULL, 'TestPosition22', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (268,21,'Adrienne', 'Lane', '1964-01-28', 'Adrienne.J.Lane@mailinator.com', 'adrieLane268', 'aivi3Doo', NULL, 'TestPosition23', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (269,21,'Kristina', 'Jennings', '1980-10-31', 'Kristina.R.Jennings@pookmail.com', 'kristJenni269', 'uNutah4y', NULL, 'TestPosition23', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (270,21,'Albert', 'Downs', '1981-11-26', 'Albert.E.Downs@pookmail.com', 'alberDowns270', 'ieJeiw3g', NULL, 'TestPosition23', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (271,21,'John', 'Hicks', '1954-10-14', 'John.B.Hicks@mailinator.com', 'johnHicks271', 'Ihu5ii4o', NULL, 'TestPosition23', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (272,21,'Corey', 'Makin', '1951-12-09', 'Corey.N.Makin@spambob.com', 'coreyMakin272', 'bech9Afe', NULL, 'TestPosition23', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (273,21,'Heather', 'Jacques', '1975-02-02', 'Heather.M.Jacques@spambob.com', 'heathJacqu273', 'bohcau6v', NULL, 'TestPosition24', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (274,21,'Lisa', 'Mccarthy', '1970-06-21', 'Lisa.F.Mccarthy@mailinator.com', 'lisaMccar274', 'Gooxisai', NULL, 'TestPosition24', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (275,21,'Nora', 'Farr', '1941-09-06', 'Nora.D.Farr@pookmail.com', 'noraFarr275', 'ouP7emah', NULL, 'TestPosition24', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (276,21,'Ellen', 'Couture', '1946-01-03', 'Ellen.J.Couture@dodgit.com', 'ellenCoutu276', 'dohWeit5', NULL, 'TestPosition24', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (277,21,'Dorothy', 'Carpenter', '1951-07-05', 'Dorothy.G.Carpenter@dodgit.com', 'dorotCarpe277', 'Yahz2kie', NULL, 'TestPosition24', 'TestLocation4', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (278,22,'Edwin', 'Klemm', '1943-04-24', 'Edwin.A.Klemm@trashymail.com', 'edwinKlemm278', 'ahzie1Zi', NULL, 'TestPosition1', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (279,22,'Darryl', 'Paterson', '1984-10-11', 'Darryl.D.Paterson@dodgit.com', 'darryPater279', 'Jiemu4ut', NULL, 'TestPosition1', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (280,22,'Ronnie', 'Cardenas', '1950-11-05', 'Ronnie.K.Cardenas@dodgit.com', 'ronniCarde280', 'ku7Chait', NULL, 'TestPosition1', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (281,22,'Jean', 'Moore', '1961-01-08', 'Jean.P.Moore@spambob.com', 'jeanMoore281', 'giriQuoo', NULL, 'TestPosition1', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (282,22,'James', 'Welch', '1981-01-02', 'James.J.Welch@pookmail.com', 'jamesWelch282', 'omohV6xa', NULL, 'TestPosition1', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (283,22,'Heather', 'Salazar', '1967-08-13', 'Heather.E.Salazar@mailinator.com', 'heathSalaz283', 'ePienu2T', NULL, 'TestPosition2', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (284,22,'Edward', 'Davis', '1943-05-08', 'Edward.E.Davis@dodgit.com', 'edwarDavis284', 'Rahphohc', NULL, 'TestPosition2', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (285,22,'Josephine', 'Anselmo', '1948-05-11', 'Josephine.D.Anselmo@pookmail.com', 'josepAnsel285', 'OhPh7ook', NULL, 'TestPosition2', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (286,22,'John', 'Lopez', '1951-10-11', 'John.V.Lopez@trashymail.com', 'johnLopez286', 'nai7eeJi', NULL, 'TestPosition2', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (287,22,'Sharon', 'Rapp', '1970-02-13', 'Sharon.P.Rapp@trashymail.com', 'sharoRapp287', 'eatu1shi', NULL, 'TestPosition2', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (288,22,'Nydia', 'Swinney', '1954-04-04', 'Nydia.N.Swinney@trashymail.com', 'nydiaSwinn288', 'FoaChaik', NULL, 'TestPosition3', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (289,22,'Marilyn', 'Mccaffrey', '1981-01-22', 'Marilyn.T.Mccaffrey@trashymail.com', 'marilMccaf289', 'lol5eR2p', NULL, 'TestPosition3', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (290,22,'Clarence', 'Clark', '1958-12-06', 'Clarence.E.Clark@spambob.com', 'clareClark290', 'aig0iNgo', NULL, 'TestPosition3', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (291,22,'Russell', 'Chandler', '1985-04-07', 'Russell.L.Chandler@mailinator.com', 'russeChand291', 'Eehooxi2', NULL, 'TestPosition3', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (292,22,'Juan', 'Thomas', '1981-05-27', 'Juan.R.Thomas@spambob.com', 'juanThoma292', 'xa5vohHa', NULL, 'TestPosition3', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (293,23,'Clarence', 'Williams', '1968-05-27', 'Clarence.L.Williams@spambob.com', 'clareWilli293', 'aelah7On', NULL, 'TestPosition4', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (294,23,'Richard', 'Ridenour', '1980-02-28', 'Richard.P.Ridenour@trashymail.com', 'richaRiden294', 'yohsh9Oi', NULL, 'TestPosition4', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (295,23,'Sylvia', 'Stennett', '1962-12-21', 'Sylvia.A.Stennett@spambob.com', 'sylviStenn295', 'see8gai9', NULL, 'TestPosition4', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (296,23,'Juanita', 'Mcdougal', '1966-12-06', 'Juanita.V.Mcdougal@pookmail.com', 'juaniMcdou296', 'ush8ooBo', NULL, 'TestPosition4', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (297,23,'Lawrence', 'Edwards', '1982-05-29', 'Lawrence.L.Edwards@trashymail.com', 'lawreEdwar297', 'eiNgiqu9', NULL, 'TestPosition4', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (298,23,'Janice', 'Anaya', '1945-04-27', 'Janice.J.Anaya@dodgit.com', 'janicAnaya298', 'ooD8eido', NULL, 'TestPosition5', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (299,23,'Guillermina', 'Jefcoat', '1968-11-18', 'Guillermina.D.Jefcoat@dodgit.com', 'guillJefco299', 'Icicooyo', NULL, 'TestPosition5', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (300,23,'Lois', 'Williams', '1970-06-26', 'Lois.R.Williams@mailinator.com', 'loisWilli300', 'ieg6Alii', NULL, 'TestPosition5', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (301,23,'Luis', 'Jones', '1955-12-28', 'Luis.R.Jones@pookmail.com', 'luisJones301', 'toozoh4I', NULL, 'TestPosition5', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (302,23,'Jose', 'Sun', '1967-01-03', 'Jose.S.Sun@mailinator.com', 'joseSun302', 'oofei7Di', NULL, 'TestPosition5', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (303,23,'Minnie', 'Nguyen', '1976-06-26', 'Minnie.G.Nguyen@dodgit.com', 'minniNguye303', 'iez0AeQu', NULL, 'TestPosition6', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (304,23,'Phillip', 'Robinson', '1968-05-12', 'Phillip.D.Robinson@dodgit.com', 'phillRobin304', 'Bee3IePu', NULL, 'TestPosition6', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (305,23,'Connie', 'Harris', '1977-04-04', 'Connie.E.Harris@mailinator.com', 'conniHarri305', 'za4vae9W', NULL, 'TestPosition6', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (306,23,'William', 'Hartman', '1946-05-05', 'William.C.Hartman@mailinator.com', 'williHartm306', 'Au8fuqu9', NULL, 'TestPosition6', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (307,23,'Marsha', 'Daniels', '1984-06-20', 'Marsha.J.Daniels@spambob.com', 'marshDanie307', 'Roor4aiv', NULL, 'TestPosition6', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (308,24,'Edna', 'Charley', '1980-09-29', 'Edna.C.Charley@pookmail.com', 'ednaCharl308', 'ahhuf0Uv', NULL, 'TestPosition7', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (309,24,'Martha', 'Richards', '1956-05-22', 'Martha.J.Richards@dodgit.com', 'marthRicha309', 'jahMush9', NULL, 'TestPosition7', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (310,24,'Elena', 'Ortiz', '1980-03-28', 'Elena.B.Ortiz@pookmail.com', 'elenaOrtiz310', 'koixeiRi', NULL, 'TestPosition7', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (311,24,'Jesse', 'Woodall', '1976-08-26', 'Jesse.B.Woodall@mailinator.com', 'jesseWooda311', 'unahYee8', NULL, 'TestPosition7', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (312,24,'Ronald', 'Garcia', '1956-09-30', 'Ronald.J.Garcia@mailinator.com', 'ronalGarci312', 'keeR8Aer', NULL, 'TestPosition7', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (313,24,'Joseph', 'Frost', '1968-12-13', 'Joseph.A.Frost@pookmail.com', 'josepFrost313', 'Ieph1EuG', NULL, 'TestPosition8', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (314,24,'Kimberly', 'Blais', '1951-01-23', 'Kimberly.B.Blais@spambob.com', 'kimbeBlais314', 'aiz5Ashe', NULL, 'TestPosition8', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (315,24,'Ramon', 'Rendon', '1960-02-12', 'Ramon.O.Rendon@trashymail.com', 'ramonRendo315', 'oy7Aetae', NULL, 'TestPosition8', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (316,24,'Frederick', 'Murry', '1961-11-06', 'Frederick.E.Murry@dodgit.com', 'fredeMurry316', 'nie7euqu', NULL, 'TestPosition8', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (317,24,'Barry', 'Odonnell', '1943-02-11', 'Barry.B.Odonnell@mailinator.com', 'barryOdonn317', 'Ruya1or1', NULL, 'TestPosition8', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (318,24,'Rosaline', 'Reber', '1950-12-10', 'Rosaline.C.Reber@pookmail.com', 'rosalReber318', 'yah8Pima', NULL, 'TestPosition9', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (319,24,'Valerie', 'Jackson', '1941-10-17', 'Valerie.F.Jackson@spambob.com', 'valerJacks319', 'uyeivi4v', NULL, 'TestPosition9', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (320,24,'Edward', 'Mitchell', '1950-06-23', 'Edward.J.Mitchell@trashymail.com', 'edwarMitch320', 'teitheca', NULL, 'TestPosition9', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (321,24,'William', 'Valera', '1978-08-30', 'William.K.Valera@dodgit.com', 'williValer321', 'doNey5ai', NULL, 'TestPosition9', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (322,24,'Gertrude', 'Martin', '1982-10-09', 'Gertrude.H.Martin@trashymail.com', 'gertrMarti322', 'hozou7Xe', NULL, 'TestPosition9', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (323,25,'Fannie', 'Brown', '1973-11-28', 'Fannie.J.Brown@spambob.com', 'fanniBrown323', 'uphoo4EB', NULL, 'TestPosition10', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (324,25,'Cole', 'Deloach', '1940-03-23', 'Cole.A.Deloach@spambob.com', 'coleDeloa324', 'oSha9ke9', NULL, 'TestPosition10', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (325,25,'Doris', 'Tarver', '1943-02-02', 'Doris.H.Tarver@mailinator.com', 'dorisTarve325', 'ChahJ7ie', NULL, 'TestPosition10', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (326,25,'Johnnie', 'Ford', '1979-12-08', 'Johnnie.S.Ford@mailinator.com', 'johnnFord326', 'congoh9O', NULL, 'TestPosition10', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (327,25,'Mark', 'Pike', '1974-03-20', 'Mark.A.Pike@spambob.com', 'markPike327', 'Peer4Dah', NULL, 'TestPosition10', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (328,25,'Paula', 'Leak', '1959-09-16', 'Paula.J.Leak@dodgit.com', 'paulaLeak328', 'phaelee7', NULL, 'TestPosition11', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (329,25,'Mary', 'Cruz', '1978-10-23', 'Mary.J.Cruz@mailinator.com', 'maryCruz329', 'paiBai7W', NULL, 'TestPosition11', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (330,25,'Eliza', 'Perry', '1953-07-14', 'Eliza.G.Perry@trashymail.com', 'elizaPerry330', 'xai6Quee', NULL, 'TestPosition11', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (331,25,'Ronald', 'Corbeil', '1954-09-09', 'Ronald.R.Corbeil@spambob.com', 'ronalCorbe331', 'quae6aeP', NULL, 'TestPosition11', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (332,25,'Shirley', 'Stoner', '1971-08-22', 'Shirley.J.Stoner@pookmail.com', 'shirlStone332', 'Iepohche', NULL, 'TestPosition11', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (333,25,'Jack', 'Steinke', '1953-02-03', 'Jack.B.Steinke@dodgit.com', 'jackStein333', 'laeghoh5', NULL, 'TestPosition12', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (334,25,'Kathryn', 'Cook', '1957-02-05', 'Kathryn.R.Cook@spambob.com', 'kathrCook334', 'ahgh1pei', NULL, 'TestPosition12', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (335,25,'Willie', 'Walker', '1946-05-25', 'Willie.L.Walker@spambob.com', 'williWalke335', 'yeu7MohY', NULL, 'TestPosition12', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (336,25,'Vernon', 'Garcia', '1978-08-24', 'Vernon.S.Garcia@trashymail.com', 'vernoGarci336', 'shahLoo1', NULL, 'TestPosition12', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (337,25,'Craig', 'Wood', '1954-05-14', 'Craig.R.Wood@pookmail.com', 'craigWood337', 'Ag3mozoh', NULL, 'TestPosition12', 'TestLocation5', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (338,26,'David', 'Driscoll', '1965-12-06', 'David.J.Driscoll@mailinator.com', 'davidDrisc338', 'Eiw6vee5', NULL, 'TestPosition13', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (339,26,'Lillian', 'Christmas', '1971-09-24', 'Lillian.P.Christmas@pookmail.com', 'lilliChris339', 'Sij8EBoh', NULL, 'TestPosition13', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (340,26,'John', 'Holmes', '1985-07-03', 'John.S.Holmes@spambob.com', 'johnHolme340', 'Ainge5sh', NULL, 'TestPosition13', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (341,26,'Melanie', 'Harrell', '1973-03-07', 'Melanie.J.Harrell@spambob.com', 'melanHarre341', 'pheeThae', NULL, 'TestPosition13', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (342,26,'Rachel', 'Thompson', '1948-01-28', 'Rachel.D.Thompson@dodgit.com', 'racheThomp342', 'OhH7Nohs', NULL, 'TestPosition13', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (343,26,'Phillip', 'Stone', '1983-10-22', 'Phillip.E.Stone@mailinator.com', 'phillStone343', 'iS7Uij4c', NULL, 'TestPosition14', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (344,26,'Michael', 'Broyles', '1980-02-29', 'Michael.N.Broyles@pookmail.com', 'michaBroyl344', 'Peing2ai', NULL, 'TestPosition14', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (345,26,'Tony', 'Jansen', '1940-07-29', 'Tony.L.Jansen@dodgit.com', 'tonyJanse345', 'Doofe5ka', NULL, 'TestPosition14', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (346,26,'Brian', 'Ramsey', '1947-07-03', 'Brian.S.Ramsey@trashymail.com', 'brianRamse346', 'Suth9aeQ', NULL, 'TestPosition14', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (347,26,'David', 'Whiten', '1981-07-02', 'David.C.Whiten@dodgit.com', 'davidWhite347', 'sei3Zahm', NULL, 'TestPosition14', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (348,26,'Darrell', 'Brown', '1942-03-16', 'Darrell.L.Brown@spambob.com', 'darreBrown348', 'paekaec3', NULL, 'TestPosition15', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (349,26,'Benjamin', 'Garcia', '1958-12-18', 'Benjamin.M.Garcia@spambob.com', 'benjaGarci349', 'xahJ5zue', NULL, 'TestPosition15', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (350,26,'Sylvester', 'Garcia', '1964-09-02', 'Sylvester.C.Garcia@dodgit.com', 'sylveGarci350', 'meiGi8Ch', NULL, 'TestPosition15', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (351,26,'Frank', 'Thomas', '1948-04-12', 'Frank.F.Thomas@pookmail.com', 'frankThoma351', 'fieB6ahl', NULL, 'TestPosition15', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (352,26,'Michael', 'Comfort', '1947-12-04', 'Michael.P.Comfort@mailinator.com', 'michaComfo352', 'oozee1Ae', NULL, 'TestPosition15', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (353,27,'Dwayne', 'Hsu', '1950-09-24', 'Dwayne.S.Hsu@spambob.com', 'dwaynHsu353', 'Re9uboh8', NULL, 'TestPosition16', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (354,27,'Michelle', 'Blanco', '1967-03-27', 'Michelle.H.Blanco@pookmail.com', 'micheBlanc354', 'ahJ7Cay9', NULL, 'TestPosition16', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (355,27,'Jennifer', 'Peterman', '1955-05-25', 'Jennifer.J.Peterman@mailinator.com', 'jenniPeter355', 'ceez1vee', NULL, 'TestPosition16', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (356,27,'Anita', 'Rogers', '1969-12-24', 'Anita.C.Rogers@dodgit.com', 'anitaRoger356', 'tauW9aeg', NULL, 'TestPosition16', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (357,27,'Robert', 'Pike', '1977-02-28', 'Robert.R.Pike@dodgit.com', 'roberPike357', 'nieTeixa', NULL, 'TestPosition16', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (358,27,'Christine', 'Howe', '1954-04-18', 'Christine.A.Howe@trashymail.com', 'chrisHowe358', 'be7ve8za', NULL, 'TestPosition17', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (359,27,'Lauren', 'Robinson', '1943-04-18', 'Lauren.T.Robinson@dodgit.com', 'laureRobin359', 'neiXahz9', NULL, 'TestPosition17', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (360,27,'Elizabeth', 'Prince', '1943-10-06', 'Elizabeth.M.Prince@trashymail.com', 'elizaPrinc360', 'bahThoo5', NULL, 'TestPosition17', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (361,27,'Maryann', 'Blanchette', '1960-12-12', 'Maryann.A.Blanchette@spambob.com', 'maryaBlanc361', 'Aefee8fe', NULL, 'TestPosition17', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (362,27,'Maureen', 'Tyler', '1964-01-29', 'Maureen.J.Tyler@dodgit.com', 'maureTyler362', 'ahr5IQuo', NULL, 'TestPosition17', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (363,27,'Richard', 'Eaves', '1967-10-04', 'Richard.S.Eaves@dodgit.com', 'richaEaves363', 'eiv9lu2D', NULL, 'TestPosition18', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (364,27,'David', 'Morales', '1967-10-29', 'David.F.Morales@dodgit.com', 'davidMoral364', 'Ohng2loT', NULL, 'TestPosition18', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (365,27,'Jesse', 'Melnick', '1962-10-18', 'Jesse.R.Melnick@pookmail.com', 'jesseMelni365', 'ohGhie8i', NULL, 'TestPosition18', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (366,27,'Mike', 'Johnson', '1973-10-26', 'Mike.C.Johnson@dodgit.com', 'mikeJohns366', 'eih6poh3', NULL, 'TestPosition18', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (367,27,'Monica', 'Nickerson', '1942-10-27', 'Monica.R.Nickerson@dodgit.com', 'monicNicke367', 'shuPh4oF', NULL, 'TestPosition18', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (368,28,'David', 'Lucio', '1975-04-18', 'David.F.Lucio@spambob.com', 'davidLucio368', 'kooMei2a', NULL, 'TestPosition19', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (369,28,'Susan', 'Pittman', '1951-05-31', 'Susan.A.Pittman@mailinator.com', 'susanPittm369', 'Sohl7phi', NULL, 'TestPosition19', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (370,28,'Eddie', 'Bridges', '1941-11-12', 'Eddie.G.Bridges@dodgit.com', 'eddieBridg370', 'quohCies', NULL, 'TestPosition19', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (371,28,'Alicia', 'Bowie', '1976-01-26', 'Alicia.T.Bowie@mailinator.com', 'aliciBowie371', 'Beimie3o', NULL, 'TestPosition19', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (372,28,'Sandra', 'Dunlap', '1970-05-25', 'Sandra.R.Dunlap@dodgit.com', 'sandrDunla372', 'Ahli8eet', NULL, 'TestPosition19', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (373,28,'Tonya', 'Martin', '1943-11-02', 'Tonya.V.Martin@spambob.com', 'tonyaMarti373', 'Aevo2Iak', NULL, 'TestPosition20', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (374,28,'Sarah', 'Austin', '1965-03-01', 'Sarah.F.Austin@trashymail.com', 'sarahAusti374', 'oyaoj1lu', NULL, 'TestPosition20', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (375,28,'Stephen', 'Beyer', '1950-04-24', 'Stephen.B.Beyer@mailinator.com', 'stephBeyer375', 'zai4oqu7', NULL, 'TestPosition20', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (376,28,'Shirley', 'Cheung', '1981-05-12', 'Shirley.G.Cheung@spambob.com', 'shirlCheun376', 'Uughie3s', NULL, 'TestPosition20', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (377,28,'Marshall', 'Johnson', '1955-02-22', 'Marshall.H.Johnson@pookmail.com', 'marshJohns377', 'aiRi1kae', NULL, 'TestPosition20', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (378,28,'Cynthia', 'Scott', '1964-07-29', 'Cynthia.J.Scott@pookmail.com', 'cynthScott378', 'Hoh0xeec', NULL, 'TestPosition21', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (379,28,'Thomas', 'Martini', '1955-11-26', 'Thomas.V.Martini@spambob.com', 'thomaMarti379', 'yu1ICeip', NULL, 'TestPosition21', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (380,28,'Karlene', 'Rutkowski', '1983-06-29', 'Karlene.T.Rutkowski@pookmail.com', 'karleRutko380', 'Xuatoh1c', NULL, 'TestPosition21', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (381,28,'Ryan', 'Williams', '1946-03-18', 'Ryan.J.Williams@spambob.com', 'ryanWilli381', 'ai7quaP4', NULL, 'TestPosition21', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (382,28,'Linda', 'Peterson', '1949-07-30', 'Linda.M.Peterson@trashymail.com', 'lindaPeter382', 'yahshie6', NULL, 'TestPosition21', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (383,29,'Robert', 'Gray', '1971-05-12', 'Robert.G.Gray@pookmail.com', 'roberGray383', 'eike3iuF', NULL, 'TestPosition22', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (384,29,'James', 'Wynn', '1981-04-04', 'James.S.Wynn@spambob.com', 'jamesWynn384', 'bohng3oh', NULL, 'TestPosition22', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (385,29,'Guy', 'Townsend', '1953-11-06', 'Guy.M.Townsend@spambob.com', 'guyTowns385', 'ophuTaa7', NULL, 'TestPosition22', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (386,29,'Kimberly', 'Farmer', '1965-12-09', 'Kimberly.N.Farmer@trashymail.com', 'kimbeFarme386', 'eeT3ahf8', NULL, 'TestPosition22', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (387,29,'Jesus', 'White', '1975-10-13', 'Jesus.M.White@dodgit.com', 'jesusWhite387', 'eiYa5ait', NULL, 'TestPosition22', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (388,29,'Jessi', 'Heimann', '1946-03-15', 'Jessi.J.Heimann@pookmail.com', 'jessiHeima388', 'Ach6Ohv4', NULL, 'TestPosition23', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (389,29,'Hector', 'Schumacher', '1974-04-21', 'Hector.Y.Schumacher@spambob.com', 'hectoSchum389', 'foCe5pa1', NULL, 'TestPosition23', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (390,29,'Sean', 'Cooper', '1973-01-08', 'Sean.A.Cooper@spambob.com', 'seanCoope390', 'quezo0aF', NULL, 'TestPosition23', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (391,29,'Brenda', 'Jones', '1956-09-17', 'Brenda.C.Jones@mailinator.com', 'brendJones391', 'Oaz5io6v', NULL, 'TestPosition23', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (392,29,'Nancy', 'Cain', '1966-11-13', 'Nancy.A.Cain@trashymail.com', 'nancyCain392', 'iaki6Kog', NULL, 'TestPosition23', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (393,29,'Virginia', 'Lovejoy', '1949-11-11', 'Virginia.R.Lovejoy@mailinator.com', 'virgiLovej393', 'BeCheiye', NULL, 'TestPosition24', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (394,29,'Beth', 'Stegall', '1965-01-13', 'Beth.J.Stegall@spambob.com', 'bethStega394', 'ducheiTh', NULL, 'TestPosition24', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (395,29,'Lan', 'Harmon', '1944-03-28', 'Lan.J.Harmon@spambob.com', 'lanHarmo395', 'aesh9ahM', NULL, 'TestPosition24', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (396,29,'John', 'Brown', '1984-06-21', 'John.P.Brown@spambob.com', 'johnBrown396', 'bohK4ba1', NULL, 'TestPosition24', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (397,29,'Mark', 'Wilbanks', '1957-07-31', 'Mark.G.Wilbanks@mailinator.com', 'markWilba397', 'AeSaisah', NULL, 'TestPosition24', 'TestLocation6', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (398,30,'Tajuana', 'Lefebvre', '1947-04-05', 'Tajuana.R.Lefebvre@trashymail.com', 'tajuaLefeb398', 'oPeith5p', NULL, 'TestPosition1', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (399,30,'Joanne', 'Smith', '1968-10-29', 'Joanne.M.Smith@pookmail.com', 'joannSmith399', 'iech6oNg', NULL, 'TestPosition1', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (400,30,'Julianna', 'Finnie', '1967-06-20', 'Julianna.D.Finnie@mailinator.com', 'juliaFinni400', 'el9Aisae', NULL, 'TestPosition1', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (401,30,'Tiffany', 'Lancaster', '1981-10-05', 'Tiffany.G.Lancaster@spambob.com', 'tiffaLanca401', 'ceiphea8', NULL, 'TestPosition1', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (402,30,'Davis', 'Jackson', '1972-08-08', 'Davis.F.Jackson@mailinator.com', 'davisJacks402', 'Ahk7eub4', NULL, 'TestPosition1', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (403,30,'Sarah', 'Lehr', '1958-10-12', 'Sarah.S.Lehr@dodgit.com', 'sarahLehr403', 'ox0Eed2m', NULL, 'TestPosition2', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (404,30,'Maria', 'Williams', '1981-02-22', 'Maria.C.Williams@spambob.com', 'mariaWilli404', 'To8iw5oh', NULL, 'TestPosition2', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (405,30,'Leisa', 'Mercer', '1953-09-22', 'Leisa.R.Mercer@mailinator.com', 'leisaMerce405', 'heiTai9f', NULL, 'TestPosition2', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (406,30,'Ben', 'Bandy', '1959-04-28', 'Ben.C.Bandy@trashymail.com', 'benBandy406', 'yiap0Eev', NULL, 'TestPosition2', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (407,30,'William', 'Brundidge', '1941-05-24', 'William.S.Brundidge@pookmail.com', 'williBrund407', 'ahs4Oor8', NULL, 'TestPosition2', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (408,30,'Patricia', 'Williamson', '1972-03-28', 'Patricia.J.Williamson@trashymail.com', 'patriWilli408', 'coquo9Ae', NULL, 'TestPosition3', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (409,30,'John', 'Jordan', '1975-09-05', 'John.M.Jordan@mailinator.com', 'johnJorda409', 'Iewoo7ei', NULL, 'TestPosition3', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (410,30,'Ernestine', 'Smith', '1945-10-01', 'Ernestine.V.Smith@mailinator.com', 'ernesSmith410', 'Roj6pahF', NULL, 'TestPosition3', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (411,30,'Carolyn', 'Daniels', '1984-12-11', 'Carolyn.D.Daniels@pookmail.com', 'carolDanie411', 'kei6Ohke', NULL, 'TestPosition3', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (412,30,'Shirley', 'Schmidt', '1958-05-18', 'Shirley.J.Schmidt@mailinator.com', 'shirlSchmi412', 'roa1de5h', NULL, 'TestPosition3', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (413,31,'Michael', 'Ponton', '1946-09-10', 'Michael.A.Ponton@spambob.com', 'michaPonto413', 'Rieph3ye', NULL, 'TestPosition4', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (414,31,'Charlie', 'Thomas', '1975-05-31', 'Charlie.J.Thomas@trashymail.com', 'charlThoma414', 'ooS7ciet', NULL, 'TestPosition4', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (415,31,'Patricia', 'Mckinney', '1961-01-15', 'Patricia.W.Mckinney@pookmail.com', 'patriMckin415', 'Thu2eexa', NULL, 'TestPosition4', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (416,31,'Roseann', 'Smith', '1964-06-27', 'Roseann.R.Smith@pookmail.com', 'roseaSmith416', 'jaJui2oh', NULL, 'TestPosition4', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (417,31,'Nancy', 'Tarleton', '1966-01-28', 'Nancy.J.Tarleton@pookmail.com', 'nancyTarle417', 'lohthu8B', NULL, 'TestPosition4', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (418,31,'Olive', 'Hensley', '1964-02-29', 'Olive.T.Hensley@spambob.com', 'oliveHensl418', 'oopahb4O', NULL, 'TestPosition5', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (419,31,'Marilyn', 'Suzuki', '1956-01-11', 'Marilyn.J.Suzuki@trashymail.com', 'marilSuzuk419', 'ieF0ciec', NULL, 'TestPosition5', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (420,31,'James', 'Hamlin', '1952-09-19', 'James.A.Hamlin@trashymail.com', 'jamesHamli420', 'eiW5moiq', NULL, 'TestPosition5', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (421,31,'Jill', 'Guzman', '1940-05-06', 'Jill.H.Guzman@trashymail.com', 'jillGuzma421', 'Aequ1ohS', NULL, 'TestPosition5', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (422,31,'Natalie', 'Miller', '1969-12-09', 'Natalie.J.Miller@mailinator.com', 'natalMille422', 'EeNgu3Vo', NULL, 'TestPosition5', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (423,31,'Bernardo', 'Roberts', '1941-02-17', 'Bernardo.T.Roberts@mailinator.com', 'bernaRober423', 'heeFohTh', NULL, 'TestPosition6', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (424,31,'Heather', 'Mcginnis', '1950-10-03', 'Heather.K.Mcginnis@dodgit.com', 'heathMcgin424', 'weif6so2', NULL, 'TestPosition6', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (425,31,'Edward', 'Coon', '1952-11-23', 'Edward.C.Coon@spambob.com', 'edwarCoon425', 'uRaigh2a', NULL, 'TestPosition6', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (426,31,'Anthony', 'Davidson', '1974-10-24', 'Anthony.L.Davidson@pookmail.com', 'anthoDavid426', 'eiLuuche', NULL, 'TestPosition6', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (427,31,'Esther', 'Rodriguez', '1971-03-21', 'Esther.J.Rodriguez@trashymail.com', 'estheRodri427', 'deiwae5Y', NULL, 'TestPosition6', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (428,32,'Nicholas', 'Lynch', '1977-06-13', 'Nicholas.L.Lynch@mailinator.com', 'nichoLynch428', 'ooraeJ5k', NULL, 'TestPosition7', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (429,32,'Scott', 'Outten', '1985-06-26', 'Scott.E.Outten@pookmail.com', 'scottOutte429', 'utah5lah', NULL, 'TestPosition7', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (430,32,'Maggie', 'Mcgill', '1942-09-13', 'Maggie.D.Mcgill@pookmail.com', 'maggiMcgil430', 'aiphaiYe', NULL, 'TestPosition7', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (431,32,'Richard', 'Ott', '1951-08-31', 'Richard.A.Ott@trashymail.com', 'richaOtt431', 'sooquai7', NULL, 'TestPosition7', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (432,32,'Ernestine', 'Gantt', '1950-09-28', 'Ernestine.C.Gantt@trashymail.com', 'ernesGantt432', 'iufaNi9V', NULL, 'TestPosition7', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (433,32,'Monica', 'Kennedy', '1950-08-29', 'Monica.J.Kennedy@spambob.com', 'monicKenne433', 'oj8Ehohd', NULL, 'TestPosition8', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (434,32,'John', 'Wyatt', '1962-08-14', 'John.M.Wyatt@dodgit.com', 'johnWyatt434', 'doo7sooP', NULL, 'TestPosition8', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (435,32,'Robert', 'Driscoll', '1962-02-23', 'Robert.M.Driscoll@trashymail.com', 'roberDrisc435', 'epoeb5La', NULL, 'TestPosition8', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (436,32,'Peter', 'Cortez', '1971-09-01', 'Peter.T.Cortez@trashymail.com', 'peterCorte436', 'eteb5Goo', NULL, 'TestPosition8', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (437,32,'Ellen', 'Williams', '1942-07-02', 'Ellen.C.Williams@trashymail.com', 'ellenWilli437', 'jaiPishe', NULL, 'TestPosition8', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (438,32,'Stephen', 'Samuelson', '1953-05-04', 'Stephen.H.Samuelson@mailinator.com', 'stephSamue438', 'BabeaG3o', NULL, 'TestPosition9', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (439,32,'George', 'Folkerts', '1963-03-04', 'George.S.Folkerts@trashymail.com', 'georgFolke439', 'Itoh9wah', NULL, 'TestPosition9', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (440,32,'Walter', 'Welch', '1955-05-18', 'Walter.C.Welch@spambob.com', 'walteWelch440', 'JeeNuaCh', NULL, 'TestPosition9', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (441,32,'Carolina', 'Looney', '1950-12-08', 'Carolina.J.Looney@mailinator.com', 'carolLoone441', 'Aoqu4hae', NULL, 'TestPosition9', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (442,32,'Albert', 'Johnson', '1967-06-10', 'Albert.M.Johnson@dodgit.com', 'alberJohns442', 'ahG0NoBa', NULL, 'TestPosition9', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (443,33,'Daniel', 'Nagel', '1962-09-15', 'Daniel.L.Nagel@trashymail.com', 'danieNagel443', 'ohd9Iez5', NULL, 'TestPosition10', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (444,33,'James', 'Barajas', '1970-03-28', 'James.C.Barajas@pookmail.com', 'jamesBaraj444', 'Aeyee2ie', NULL, 'TestPosition10', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (445,33,'Cindy', 'Baldwin', '1967-12-23', 'Cindy.J.Baldwin@pookmail.com', 'cindyBaldw445', 'ahzaiz1B', NULL, 'TestPosition10', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (446,33,'Richard', 'Moore', '1957-10-23', 'Richard.E.Moore@pookmail.com', 'richaMoore446', 'soomaiv4', NULL, 'TestPosition10', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (447,33,'Peter', 'Beeler', '1957-11-19', 'Peter.B.Beeler@dodgit.com', 'peterBeele447', 'Jeephaaj', NULL, 'TestPosition10', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (448,33,'Ryan', 'Irwin', '1972-07-22', 'Ryan.P.Irwin@pookmail.com', 'ryanIrwin448', 'Chahph9a', NULL, 'TestPosition11', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (449,33,'Rudy', 'Nocera', '1984-05-21', 'Rudy.J.Nocera@dodgit.com', 'rudyNocer449', 'ohbaiCoh', NULL, 'TestPosition11', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (450,33,'Bradly', 'Lawson', '1942-12-17', 'Bradly.C.Lawson@spambob.com', 'bradlLawso450', 'Eekoeng5', NULL, 'TestPosition11', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (451,33,'Derek', 'Rose', '1969-11-25', 'Derek.S.Rose@dodgit.com', 'derekRose451', 'Ooth3soo', NULL, 'TestPosition11', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (452,33,'Geraldine', 'Crosby', '1960-08-24', 'Geraldine.P.Crosby@dodgit.com', 'geralCrosb452', 'duJu3Ain', NULL, 'TestPosition11', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (453,33,'Ricardo', 'Dixon', '1955-08-02', 'Ricardo.T.Dixon@pookmail.com', 'ricarDixon453', 'bei3Ohca', NULL, 'TestPosition12', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (454,33,'Ana', 'Jones', '1952-12-20', 'Ana.R.Jones@trashymail.com', 'anaJones454', 'aY4oohi3', NULL, 'TestPosition12', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (455,33,'Randall', 'Williams', '1943-01-08', 'Randall.L.Williams@mailinator.com', 'randaWilli455', 'jei6UTia', NULL, 'TestPosition12', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (456,33,'Lorena', 'Bracey', '1962-10-03', 'Lorena.T.Bracey@pookmail.com', 'lorenBrace456', 'Ishai8oo', NULL, 'TestPosition12', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (457,33,'Sarah', 'Park', '1951-06-17', 'Sarah.C.Park@dodgit.com', 'sarahPark457', 'gie7Nia6', NULL, 'TestPosition12', 'TestLocation7', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (458,34,'Donna', 'Rivera', '1941-05-20', 'Donna.G.Rivera@mailinator.com', 'donnaRiver458', 'Eth1Iex3', NULL, 'TestPosition13', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (459,34,'Debra', 'Savage', '1981-12-06', 'Debra.J.Savage@trashymail.com', 'debraSavag459', 'gooD8phu', NULL, 'TestPosition13', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (460,34,'John', 'Kelly', '1940-11-23', 'John.K.Kelly@trashymail.com', 'johnKelly460', 'ce3Fuz5p', NULL, 'TestPosition13', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (461,34,'Mickie', 'Gross', '1944-08-31', 'Mickie.W.Gross@trashymail.com', 'mickiGross461', 'eevej4Ia', NULL, 'TestPosition13', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (462,34,'Charity', 'Lyon', '1951-01-12', 'Charity.J.Lyon@spambob.com', 'chariLyon462', 'IeheV0Ch', NULL, 'TestPosition13', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (463,34,'Helen', 'Blackmon', '1956-11-26', 'Helen.J.Blackmon@mailinator.com', 'helenBlack463', 'Lae3chee', NULL, 'TestPosition14', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (464,34,'Maren', 'Kimball', '1977-11-19', 'Maren.P.Kimball@dodgit.com', 'marenKimba464', 'eiNg6yao', NULL, 'TestPosition14', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (465,34,'Alfred', 'Skinner', '1982-07-03', 'Alfred.K.Skinner@dodgit.com', 'alfreSkinn465', 'aeGoh6Vi', NULL, 'TestPosition14', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (466,34,'Cynthia', 'Petterson', '1982-08-05', 'Cynthia.J.Petterson@mailinator.com', 'cynthPette466', 'wi9ohWuf', NULL, 'TestPosition14', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (467,34,'Jake', 'Rodriguez', '1973-08-26', 'Jake.S.Rodriguez@mailinator.com', 'jakeRodri467', 'Fuxai3Pu', NULL, 'TestPosition14', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (468,34,'Richard', 'Johnson', '1962-11-14', 'Richard.A.Johnson@mailinator.com', 'richaJohns468', 'Eath2au7', NULL, 'TestPosition15', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (469,34,'David', 'Jones', '1942-03-19', 'David.V.Jones@spambob.com', 'davidJones469', 'aebai1Ch', NULL, 'TestPosition15', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (470,34,'Joe', 'Le', '1978-05-11', 'Joe.S.Le@spambob.com', 'joeLe470', 'aiph5uiP', NULL, 'TestPosition15', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (471,34,'Shirly', 'Luna', '1977-08-20', 'Shirly.J.Luna@spambob.com', 'shirlLuna471', 'eiNiebai', NULL, 'TestPosition15', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (472,34,'Michael', 'Sanchez', '1963-01-30', 'Michael.K.Sanchez@trashymail.com', 'michaSanch472', 'eex8ahCh', NULL, 'TestPosition15', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (473,35,'David', 'Berry', '1954-06-27', 'David.L.Berry@mailinator.com', 'davidBerry473', 'ahv6evu3', NULL, 'TestPosition16', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (474,35,'Marie', 'Lee', '1984-05-02', 'Marie.G.Lee@spambob.com', 'marieLee474', 'OvohBahS', NULL, 'TestPosition16', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (475,35,'Mary', 'Bower', '1978-06-14', 'Mary.W.Bower@dodgit.com', 'maryBower475', 'Aud7ahf4', NULL, 'TestPosition16', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (476,35,'Helen', 'Clausen', '1974-02-08', 'Helen.A.Clausen@pookmail.com', 'helenClaus476', 'eeth2koh', NULL, 'TestPosition16', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (477,35,'Isaac', 'Fuller', '1955-05-09', 'Isaac.S.Fuller@mailinator.com', 'isaacFulle477', 'Ierahch7', NULL, 'TestPosition16', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (478,35,'Raymond', 'Long', '1965-10-19', 'Raymond.J.Long@spambob.com', 'raymoLong478', 'Rait2van', NULL, 'TestPosition17', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (479,35,'Korey', 'Norris', '1985-01-19', 'Korey.D.Norris@pookmail.com', 'koreyNorri479', 'ui5aiDae', NULL, 'TestPosition17', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (480,35,'Rachel', 'Moore', '1969-01-31', 'Rachel.J.Moore@pookmail.com', 'racheMoore480', 'ahc5ahwa', NULL, 'TestPosition17', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (481,35,'Nelson', 'Welch', '1954-02-22', 'Nelson.J.Welch@pookmail.com', 'nelsoWelch481', 'roh2Hah7', NULL, 'TestPosition17', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (482,35,'Keith', 'Murphy', '1973-11-23', 'Keith.A.Murphy@dodgit.com', 'keithMurph482', 'Oogaegh1', NULL, 'TestPosition17', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (483,35,'Dawn', 'Johnson', '1949-01-10', 'Dawn.J.Johnson@mailinator.com', 'dawnJohns483', 'eil3Ohta', NULL, 'TestPosition18', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (484,35,'Clarence', 'Martin', '1941-07-18', 'Clarence.L.Martin@trashymail.com', 'clareMarti484', 'caerahqu', NULL, 'TestPosition18', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (485,35,'Ronald', 'Anderson', '1961-12-24', 'Ronald.S.Anderson@dodgit.com', 'ronalAnder485', 'bi7ahg7I', NULL, 'TestPosition18', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (486,35,'Dorothy', 'Grinnell', '1963-01-28', 'Dorothy.R.Grinnell@mailinator.com', 'dorotGrinn486', 'Quukaivi', NULL, 'TestPosition18', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (487,35,'Marilyn', 'Peterson', '1960-08-03', 'Marilyn.P.Peterson@pookmail.com', 'marilPeter487', 'eideez7U', NULL, 'TestPosition18', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (488,36,'Charles', 'Sparrow', '1972-04-28', 'Charles.T.Sparrow@mailinator.com', 'charlSparr488', 'haoroy6E', NULL, 'TestPosition19', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (489,36,'Justin', 'Dubay', '1949-02-28', 'Justin.K.Dubay@dodgit.com', 'justiDubay489', 'Rahx2noo', NULL, 'TestPosition19', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (490,36,'Rae', 'Price', '1968-07-09', 'Rae.M.Price@pookmail.com', 'raePrice490', 'ahshah6R', NULL, 'TestPosition19', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (491,36,'Robert', 'Brennan', '1942-08-25', 'Robert.M.Brennan@dodgit.com', 'roberBrenn491', 'iengei1h', NULL, 'TestPosition19', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (492,36,'Robert', 'Charlton', '1964-12-15', 'Robert.J.Charlton@spambob.com', 'roberCharl492', 'SeNaey1A', NULL, 'TestPosition19', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (493,36,'William', 'Peterson', '1953-07-29', 'William.S.Peterson@pookmail.com', 'williPeter493', 'thahcai0', NULL, 'TestPosition20', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (494,36,'Thomas', 'Rios', '1963-12-29', 'Thomas.L.Rios@spambob.com', 'thomaRios494', 'ooh5Haes', NULL, 'TestPosition20', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (495,36,'Hattie', 'Long', '1983-08-24', 'Hattie.R.Long@spambob.com', 'hattiLong495', 'bea2buaj', NULL, 'TestPosition20', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (496,36,'Walter', 'Storer', '1948-12-13', 'Walter.D.Storer@trashymail.com', 'walteStore496', 'Eenie2zo', NULL, 'TestPosition20', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (497,36,'Rachelle', 'Bailey', '1974-11-01', 'Rachelle.J.Bailey@trashymail.com', 'racheBaile497', 'aihetah3', NULL, 'TestPosition20', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (498,36,'Jerry', 'Chung', '1952-11-12', 'Jerry.M.Chung@trashymail.com', 'jerryChung498', 'shaic4Az', NULL, 'TestPosition21', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (499,36,'Robin', 'Mcdonald', '1955-11-25', 'Robin.L.Mcdonald@pookmail.com', 'robinMcdon499', 'uaJ8oowo', NULL, 'TestPosition21', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (500,36,'Jason', 'Pam', '1957-05-02', 'Jason.M.Pam@pookmail.com', 'jasonPam500', 'ahMi6aeT', NULL, 'TestPosition21', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (501,36,'Pauline', 'Barker', '1965-05-02', 'Pauline.M.Barker@spambob.com', 'pauliBarke501', 'aeMoh4pe', NULL, 'TestPosition21', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (502,36,'Marcia', 'Weekes', '1953-01-30', 'Marcia.W.Weekes@trashymail.com', 'marciWeeke502', 'yoo4Uefi', NULL, 'TestPosition21', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (503,37,'Lindsey', 'Jenkins', '1959-01-28', 'Lindsey.B.Jenkins@pookmail.com', 'lindsJenki503', 'um1oTela', NULL, 'TestPosition22', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (504,37,'Johnny', 'Scheer', '1979-03-29', 'Johnny.C.Scheer@trashymail.com', 'johnnSchee504', 'al4Fohr3', NULL, 'TestPosition22', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (505,37,'Samuel', 'Canales', '1948-03-30', 'Samuel.S.Canales@spambob.com', 'samueCanal505', 'ahtohvai', NULL, 'TestPosition22', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (506,37,'Marilyn', 'Diaz', '1940-10-08', 'Marilyn.C.Diaz@dodgit.com', 'marilDiaz506', 'eishaaBa', NULL, 'TestPosition22', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (507,37,'James', 'Ducan', '1968-04-30', 'James.J.Ducan@spambob.com', 'jamesDucan507', 'eeCh7rei', NULL, 'TestPosition22', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (508,37,'John', 'Kim', '1955-06-05', 'John.L.Kim@trashymail.com', 'johnKim508', 'OhCohF9p', NULL, 'TestPosition23', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (509,37,'David', 'Harris', '1974-09-10', 'David.L.Harris@trashymail.com', 'davidHarri509', 'epee4cho', NULL, 'TestPosition23', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (510,37,'Brigitte', 'Vanburen', '1968-06-25', 'Brigitte.W.Vanburen@mailinator.com', 'brigiVanbu510', 'gaihie7O', NULL, 'TestPosition23', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (511,37,'Charles', 'Albright', '1950-08-16', 'Charles.M.Albright@dodgit.com', 'charlAlbri511', 'zae2Ai2a', NULL, 'TestPosition23', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (512,37,'John', 'Perrin', '1944-03-15', 'John.S.Perrin@dodgit.com', 'johnPerri512', 'souch1No', NULL, 'TestPosition23', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (513,37,'Helen', 'Phillippi', '1947-08-02', 'Helen.G.Phillippi@pookmail.com', 'helenPhill513', 'su6oShic', NULL, 'TestPosition24', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (514,37,'Janet', 'Deans', '1978-04-06', 'Janet.W.Deans@pookmail.com', 'janetDeans514', 'phiC4wae', NULL, 'TestPosition24', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (515,37,'James', 'Shaw', '1958-12-01', 'James.R.Shaw@pookmail.com', 'jamesShaw515', 'OeD1kei7', NULL, 'TestPosition24', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (516,37,'Stephanie', 'Hoff', '1948-07-30', 'Stephanie.D.Hoff@dodgit.com', 'stephHoff516', 'nohpoh2P', NULL, 'TestPosition24', 'TestLocation8', 0, 'a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (517,37,'Isabel', 'Love', '1974-01-16', 'Isabel.P.Love@pookmail.com', 'isabeLove517', 'lo6Rie9o', NULL, 'TestPosition24', 'TestLocation8', 0, 'a', true, 1);

COMMIT;

-- -----------------------------------------------------
-- Data for table `WebAgenda`.`SKILL`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('Serving', NULL);
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('Cooking', NULL);
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill1', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill2', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill3', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill4', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill5', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill6', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill7', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill8', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill9', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill10', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill11', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill12', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill13', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill14', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill15', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill16', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill17', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill18', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill19', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill20', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill21', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill22', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill23', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill24', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill25', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill26', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill27', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill28', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill29', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill30', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill31', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill32', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkillMgr', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill33', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill34', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill35', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill36', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill37', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill38', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill39', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill40', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill41', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill42', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill43', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill44', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill45', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill46', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill47', 'Test');
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('TestSkill48', 'Test');

COMMIT;

-- -----------------------------------------------------
-- Data for table `WebAgenda`.`EMPSKILL`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
insert into `WebAgenda`.`EMPSKILL` (`empID`, `skillName`) values (28472, 'Cooking');
insert into `WebAgenda`.`EMPSKILL` (`empID`, `skillName`) values (38382, 'Cooking');
insert into `WebAgenda`.`EMPSKILL` (`empID`, `skillName`) values (38202, 'Cooking');
insert into `WebAgenda`.`EMPSKILL` (`empID`, `skillName`) values (39280, 'Cooking');
insert into `WebAgenda`.`EMPSKILL` (`empID`, `skillName`) values (39202, 'Serving');
insert into `WebAgenda`.`EMPSKILL` (`empID`, `skillName`) values (39203, 'Serving');
insert into `WebAgenda`.`EMPSKILL` (`empID`, `skillName`) values (30293, 'Serving');

COMMIT;

-- -----------------------------------------------------
-- Data for table `WebAgenda`.`POSSKILL`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('Executive Chef', 'Cooking');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('Cook', 'Cooking');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('Waiter', 'Serving');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition1', 'TestSkill1');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition1', 'TestSkill2');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition2', 'TestSkill3');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition2', 'TestSkill4');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition3', 'TestSkill5');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition3', 'TestSkill6');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition4', 'TestSkill7');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition4', 'TestSkill8');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition5', 'TestSkill9');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition5', 'TestSkill10');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition6', 'TestSkill11');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition6', 'TestSkill12');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition7', 'TestSkill13');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition7', 'TestSkill14');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition8', 'TestSkill15');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition8', 'TestSkill16');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition9', 'TestSkill17');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition9', 'TestSkill18');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition10', 'TestSkill19');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition10', 'TestSkill20');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition11', 'TestSkill21');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition11', 'TestSkill22');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition12', 'TestSkill23');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition12', 'TestSkill24');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition13', 'TestSkill25');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition13', 'TestSkill26');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition14', 'TestSkill27');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition14', 'TestSkill28');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition15', 'TestSkill29');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition15', 'TestSkill30');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition16', 'TestSkill31');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition16', 'TestSkill32');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('Supervisor1', 'TestSkillMgr');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('Supervisor2', 'TestSkillMgr');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('Supervisor3', 'TestSkillMgr');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('Supervisor4', 'TestSkillMgr');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('Manager1', 'TestSkillMgr');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('Manager2', 'TestSkillMgr');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('CEO', 'TestSkillMgr');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition17', 'TestSkill33');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition17', 'TestSkill34');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition18', 'TestSkill35');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition18', 'TestSkill36');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition19', 'TestSkill37');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition19', 'TestSkill38');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition20', 'TestSkill39');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition20', 'TestSkill40');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition21', 'TestSkill41');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition21', 'TestSkill42');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition22', 'TestSkill43');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition22', 'TestSkill44');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition23', 'TestSkill45');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition23', 'TestSkill46');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition24', 'TestSkill47');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('TestPosition24', 'TestSkill48');

COMMIT;

-- -----------------------------------------------------
-- Data for table `WebAgenda`.`SCHEDULETEMPLATE`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
insert into `WebAgenda`.`SCHEDULETEMPLATE` (`schedTempID`, `creatorID`, `name`) values (1, 12314, 'Test Template');

COMMIT;

-- -----------------------------------------------------
-- Data for table `WebAgenda`.`SHIFTTEMPLATE`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
insert into `WebAgenda`.`SHIFTTEMPLATE` (`shiftTempID`, `schedTempID`, `day`, `startTime`, `endTime`) values (1, 1, 2, '08:00:00', '17:00:00');
insert into `WebAgenda`.`SHIFTTEMPLATE` (`shiftTempID`, `schedTempID`, `day`, `startTime`, `endTime`) values (2, 1, 3, '08:00:00', '17:00:00');
insert into `WebAgenda`.`SHIFTTEMPLATE` (`shiftTempID`, `schedTempID`, `day`, `startTime`, `endTime`) values (3, 1, 4, '08:00:00', '17:00:00');
insert into `WebAgenda`.`SHIFTTEMPLATE` (`shiftTempID`, `schedTempID`, `day`, `startTime`, `endTime`) values (4, 1, 5, '08:00:00', '17:00:00');
insert into `WebAgenda`.`SHIFTTEMPLATE` (`shiftTempID`, `schedTempID`, `day`, `startTime`, `endTime`) values (5, 1, 6, '08:00:00', '17:00:00');

COMMIT;

-- -----------------------------------------------------
-- Data for table `WebAgenda`.`SHIFTPOS`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
insert into `WebAgenda`.`SHIFTPOS` (`shiftTempID`, `positionName`, `posCount`) values (1, 'Cook', 3);
insert into `WebAgenda`.`SHIFTPOS` (`shiftTempID`, `positionName`, `posCount`) values (1, 'Waiter', 3);
insert into `WebAgenda`.`SHIFTPOS` (`shiftTempID`, `positionName`, `posCount`) values (2, 'Cook', 3);
insert into `WebAgenda`.`SHIFTPOS` (`shiftTempID`, `positionName`, `posCount`) values (2, 'Waiter', 3);
insert into `WebAgenda`.`SHIFTPOS` (`shiftTempID`, `positionName`, `posCount`) values (3, 'Cook', 3);
insert into `WebAgenda`.`SHIFTPOS` (`shiftTempID`, `positionName`, `posCount`) values (3, 'Waiter', 3);
insert into `WebAgenda`.`SHIFTPOS` (`shiftTempID`, `positionName`, `posCount`) values (4, 'Cook', 3);
insert into `WebAgenda`.`SHIFTPOS` (`shiftTempID`, `positionName`, `posCount`) values (4, 'Waiter', 3);
insert into `WebAgenda`.`SHIFTPOS` (`shiftTempID`, `positionName`, `posCount`) values (5, 'Cook', 3);
insert into `WebAgenda`.`SHIFTPOS` (`shiftTempID`, `positionName`, `posCount`) values (5, 'Waiter', 3);

COMMIT;

-- -----------------------------------------------------
-- Data for table `WebAgenda`.`SCHEDULE`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
insert into `WebAgenda`.`SCHEDULE` (`schedID`, `creatorID`, `startDate`, `endDate`) values (1, 12314, '2010-04-18', '2010-04-24');

COMMIT;

-- -----------------------------------------------------
-- Data for table `WebAgenda`.`SHIFT`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
insert into `WebAgenda`.`SHIFT` (`shiftID`, `schedID`, `day`, `startTime`, `endTime`) values (1, 1, 2, '08:00:00', '17:00:00');
insert into `WebAgenda`.`SHIFT` (`shiftID`, `schedID`, `day`, `startTime`, `endTime`) values (2, 1, 3, '08:00:00', '17:00:00');
insert into `WebAgenda`.`SHIFT` (`shiftID`, `schedID`, `day`, `startTime`, `endTime`) values (3, 1, 4, '08:00:00', '17:00:00');
insert into `WebAgenda`.`SHIFT` (`shiftID`, `schedID`, `day`, `startTime`, `endTime`) values (4, 1, 5, '08:00:00', '17:00:00');
insert into `WebAgenda`.`SHIFT` (`shiftID`, `schedID`, `day`, `startTime`, `endTime`) values (5, 1, 6, '08:00:00', '17:00:00');

COMMIT;

-- -----------------------------------------------------
-- Data for table `WebAgenda`.`SHIFTEMP`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (1, 38382);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (1, 38202);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (1, 39280);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (1, 39202);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (1, 39203);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (1, 30293);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (2, 38382);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (2, 38202);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (2, 39280);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (2, 39202);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (2, 39203);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (2, 30293);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (3, 38382);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (3, 38202);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (3, 39280);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (3, 39202);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (3, 39203);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (3, 30293);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (4, 38382);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (4, 38202);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (4, 39280);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (4, 39202);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (4, 39203);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (4, 30293);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (5, 38382);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (5, 38202);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (5, 39280);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (5, 39202);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (5, 39203);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (5, 30293);

COMMIT;
