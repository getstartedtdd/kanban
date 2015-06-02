TRUNCATE TABLE user_roles;
TRUNCATE TABLE users;
TRUNCATE TABLE roles;

INSERT INTO users (username, password) VALUES ('admin', 'admin');
INSERT INTO roles (name) VALUES ('ADMIN');

INSERT INTO user_roles (user_id, role_id)
  SELECT
    users.id,
    roles.id
  FROM users, roles
  WHERE username = 'admin' AND roles.name = 'ADMIN';

UPDATE users
SET client_id = 'kanban', client_secret = '123456', resource_ids = 'kanban,others',
  scopes      = 'read,write', granted_types = 'password,refresh_token,client_credentials'

WHERE username = 'admin';