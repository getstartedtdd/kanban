truncate table user_roles;
truncate table users;
truncate table roles;

INSERT INTO users (username, password) VALUES ('admin', 'admin');
INSERT INTO roles (name) VALUES ('ADMIN');

INSERT INTO user_roles (user_id, role_id)
  SELECT
    users.id,
    roles.id
  FROM users, roles
  WHERE username = 'admin' AND roles.name = 'ADMIN';