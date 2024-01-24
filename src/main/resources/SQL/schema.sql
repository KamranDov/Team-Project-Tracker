INSERT INTO roles (id, role_name) VALUES ('1', 'SUPER_ADMIN') ON DUPLICATE KEY UPDATE id=id;
INSERT INTO roles (id, role_name) VALUES ('2', 'ADMIN') ON DUPLICATE KEY UPDATE id=id;
INSERT INTO roles (id, role_name) VALUES ('3', 'HEAD') ON DUPLICATE KEY UPDATE id=id;
INSERT INTO roles (id, role_name) VALUES ('4', 'USER') ON DUPLICATE KEY UPDATE id=id;

