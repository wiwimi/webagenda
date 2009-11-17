/**
 * Adds Primary, Foreign, and NOT NULL constraints to
 * tables created by the DB_CREATE_TABLES.sql script.
 * If attempting to drop a primary key that does not
 * exist, the error should not interfere with the rest
 * of the script. Same goes for all constraints.
 * 
 * Created by Daniel Kettle, WebAgenda Team 2009
 * Revision 0.4 (current)
 * Revision 0.3: Moved login columns to User table, login now known as user_stats table.
 * - Introduced user_extras table (for admin use generally). Added foreign keys. Modified multi-column primary keys.
 * - Script can be run without any issues. Foreign and Primary keys dropped if exists.
 * Revision 0.2: Added all NOT NULL to required columns (to my knowledge)
 * Revision 0.1: userId fixed, modified primary keys
 *
 * :: TODOs ::
 * 
 * > Ensure that the user_stats table is created (DB_Create_Tables.sql script file) properly and constraints are applied and dropped correctly: Primary Key
 *	USER_STATS Table consists of last login date information and password change information
 */
USE webagenda;

-- Drop All Constraints that may be found
ALTER TABLE user DROP PRIMARY KEY;
ALTER TABLE user_stats DROP PRIMARY KEY;
ALTER TABLE user_stats DROP FOREIGN KEY;
ALTER TABLE user_extras DROP PRIMARY KEY;
ALTER TABLE user_extras DROP FOREIGN KEY;
ALTER TABLE address DROP PRIMARY KEY;
ALTER TABLE email DROP PRIMARY KEY;
ALTER TABLE job_type DROP PRIMARY KEY;
ALTER TABLE phone DROP PRIMARY KEY;
ALTER TABLE working_jobs DROP PRIMARY KEY;
ALTER TABLE working_jobs DROP FOREIGN KEY;


	-- NOT NULL CONSTRAINTS

-- User Table Constraints
ALTER TABLE user MODIFY firstname VARCHAR(20) NOT NULL;
ALTER TABLE user MODIFY lastname VARCHAR(20) NOT NULL;
ALTER TABLE user MODIFY userEnabled BIT(1) NOT NULL;
ALTER TABLE user MODIFY userName VARCHAR(30) NOT NULL;
ALTER TABLE user MODIFY password VARCHAR(30) NOT NULL;

-- Address Table Constraints
ALTER TABLE address MODIFY userID VARCHAR(10) NOT NULL;
ALTER TABLE address MODIFY address VARCHAR(100) NOT NULL;
ALTER TABLE address MODIFY city VARCHAR(30) NOT NULL;
ALTER TABLE address MODIFY postalCode VARCHAR(8) NOT NULL;
ALTER TABLE address MODIFY country VARCHAR(40) NOT NULL;

-- Phone Table Constraints
ALTER TABLE phone MODIFY userID VARCHAR(10) NOT NULL;
ALTER TABLE phone MODIFY phoneNumber CHAR(10) NOT NULL;
ALTER TABLE phone MODIFY phoneType VARCHAR(25) NOT NULL;

-- Job_Type Table Constraints
ALTER TABLE job_type MODIFY jobID VARCHAR(10) NOT NULL;
ALTER TABLE job_type MODIFY jobName VARCHAR(50) NOT NULL;
  -- Job must have a supervisor in order for it to be instantiated.
ALTER TABLE job_type MODIFY mainSupervisor VARCHAR(50) NOT NULL;

-- Email Table Constraints
ALTER TABLE email MODIFY userID VARCHAR(10) NOT NULL;
ALTER TABLE email MODIFY emailType VARCHAR(25) NOT NULL;
ALTER TABLE email MODIFY email VARCHAR(50) NOT NULL;



	-- Foreign Key Constraints

-- User_Stats Constraints
ALTER TABLE user_stats ADD FOREIGN KEY (userID) REFERENCES user(userID);
ALTER TABLE user_extras ADD FOREIGN KEY (userID) REFERENCES user(userID);

-- Foreign Key Constraints for Jobs
ALTER TABLE working_jobs ADD FOREIGN KEY (jobID) REFERENCES job_type(jobID);
ALTER TABLE working_jobs ADD FOREIGN KEY (userID) REFERENCES user(userID);

	-- Primary Key Constraints

-- Add Primary Keys to all tables
ALTER TABLE user ADD PRIMARY KEY (userID);
ALTER TABLE user_stats ADD PRIMARY KEY(userID);
ALTER TABLE user_extras ADD PRIMARY KEY(userID);
ALTER TABLE address ADD PRIMARY KEY (userID,postalCode,country);
ALTER TABLE email ADD PRIMARY KEY (userID,email);
ALTER TABLE job_type ADD PRIMARY KEY (jobID);
ALTER TABLE phone ADD PRIMARY KEY (userID,phoneNumber);
ALTER TABLE working_jobs ADD PRIMARY KEY (userID,jobID);