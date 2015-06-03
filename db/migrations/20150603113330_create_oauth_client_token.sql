CREATE TABLE oauth_client_token (
  token_id          VARCHAR(256),
  token             BLOB,
  authentication_id VARCHAR(256),
  user_name         VARCHAR(256),
  client_id         VARCHAR(256)
);