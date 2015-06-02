CREATE TABLE users (
  id       INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  username VARCHAR(20) UNIQUE             NOT NULL,
  password VARCHAR(32)                    NOT NULL
);