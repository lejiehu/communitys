CREATE TABLE USER
(
  ID int AUTO_INCREMENT PRIMARY KEY NOT NULL,
  ACCOUNT_ID varchar(100) DEFAULT 'NULL',
  NAME varchar(50) DEFAULT 'NULL',
  TOKEN varchar(36) DEFAULT 'NULL',
  GMT_CREATE bigint DEFAULT NULL,
  GMT_MODIFIED bigint DEFAULT NULL
)
