-- These are extra lines I need to add into the
-- create script for everything to work.

DROP USER 'WABroker'@'localhost';
grant SELECT ON `mysql`.`proc` to 'WABroker'@'localhost';
