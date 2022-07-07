CREATE TABLE users (
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  enabled TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (username)
);

CREATE TABLE users_secret_key (
  username VARCHAR(50) NOT NULL,
  secret_key VARCHAR(100) NOT NULL,
  PRIMARY KEY (username)
);

CREATE TABLE authorities (
  username VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  FOREIGN KEY (username) REFERENCES users(username)
);

CREATE UNIQUE INDEX ix_auth_username
  ON authorities (username, authority);

CREATE TABLE secrets (
  uuid VARCHAR(10) NOT NULL,
  sender_username VARCHAR(50) NOT NULL,
  receiver_username VARCHAR(50) NOT NULL,
  message VARCHAR(500) NOT NULL,
  PRIMARY KEY (uuid),
  FOREIGN KEY (sender_username) REFERENCES users(username),
  FOREIGN KEY (receiver_username) REFERENCES users(username)
);