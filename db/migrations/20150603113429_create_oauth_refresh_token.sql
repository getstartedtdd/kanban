CREATE TABLE oauth_refresh_token (
  token_id       VARCHAR(256),
  token          BLOB,
  authentication BLOB
);