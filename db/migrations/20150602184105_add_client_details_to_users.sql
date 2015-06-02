ALTER TABLE users
ADD client_id VARCHAR(64),
ADD resource_ids VARCHAR(100),
ADD client_secret VARCHAR(64),
ADD scopes VARCHAR(100),
ADD granted_types VARCHAR(100);