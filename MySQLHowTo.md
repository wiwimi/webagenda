# Introduction #

This wiki is intended for reference when using MySQL for various tasks. Feel free to contribute.


---


# MySQL Common Tasks #


---


## Setting Up ##

### 01 Setting Up The **WebAgenda** Account ###

`mysql -u root -p`

_Login as root user, but have it interactively ask for your password (more secure)_

`CREATE DATABASE webagenda;`

_Create the database name that we will be using_

`USE webagenda;`

_Enable the database_

`GRANT ALL ON webagenda.* TO webagendadb@localhost IDENTIFIED BY 'password';`

_Give all privilleges to the webagenda database that user webagendadb found at localhost will use. Password would be 'password' (no single quotes), obviously you would change this._

### 02 Logging in as a WebAgenda User ###

`mysql -u user -h hostname -p`

If you are not using the same machine that the mysql server is running on, you will need to specify the _host_ name, which would be localhost if you were on the same machine. MySQL defaults to localhost if no _host_ name is given. I assume it can be an IP address or a domain name.

### 03 Quitting from the MySQL Database ###

`> quit`

And then it exists.

### 04 Creating a Database ###

`CREATE DATABASE 'name';`

Creates a database with _name_
For unix, tables are case sensitive, so it would be best if table names start in lowercase

In order to actually USE the database once it has been created, you must explicitly tell mysql to use it.

`USE 'name';`

### 05 Creating a Table ###

`CREATE TABLE 'tablename' (var_name VARCHAR(20), var_name2 VARCHAR(20), var_name3 CHAR(5), var_date DATE);`

I don't think MySQL has a VARCHAR2 variable.
If you want to assign a default value to a field, append `DEFAULT [value]` after the data type and before the comma.



### 06 Running A Script (Command Line) ###

`source /home/$USER/Folder/SubFolder/sql_script.sql`

for **nix based systems or**

`source C:\Folder\SubFolder\sql_script.sql`

for Windows-ish systems. (not tested)


---


## Using MySQL ##

### 01 Using Your Database ###

In order for MySQL to be able to manipulate a database, you have to tell it which table to use.

`USE databasename;`

Now it has a database that it can access. USE refers to the default (current) database that following statements will affect.

### 02 Primary Keys ###

`ALTER TABLE user ADD PRIMARY KEY (columnname);`

It would be a good idea to use this when a table is empty, before data exists.
Otherwise you may encounter non-unique errors.

`ALTER TABLE user DROP PRIMARY KEY;`

Dropping a primary key is much easier; you do not have to specify the column. Only the table name.

### 03 Foreign Keys ###

`ALTER TABLE table_name ADD FOREIGN KEY (table_name_field) REFERENCES table (field);`

Dropping a foreign key is similar to dropping a primary key.

`ALTER TABLE user DROP FOREIGN KEY [field_name];`

This is probably because a table can have multiple FK's, but only one PK, which is why field name must be specified.

### 04 Insert Data ###

Just like oracle, the syntax is as follows:

`INSERT INTO table_name (col1, col2 col3 ...) VALUES (col1, col2, col3 ....);`

or if you want to insert an entire row, you can use:

`INSERT INTO table_name VALUES (col1, col2 ... coln);`



---


# MySQL Key Words #

`CURRENT DATE`

Also can be written as `current date` and `current_date`.
Key words are not case sensitive, like in Oracle.

`SELECT VERSION();`

This is your mysql version and your operating system.

`SELECT NOW();`

Selects the time, similar to SYSDATE in Oracle

`SELECT USER();`

Logged in name plus hostname

`SHOW DATABASES;`

Here you can see all the database tables that the user has. Or you can see the database that is currently selected by typing:

`SELECT DATABASE();`

After selecting a database, you can view the tables in said database by using the following key words:

`SHOW TABLES;`

### BACKUP DATABASE ###
`mysqldump -u [user] -p [password] database_name > file_to_dump_to.sql`