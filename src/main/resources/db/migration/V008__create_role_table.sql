CREATE TABLE role (
    id NUMERIC PRIMARY KEY,
    name VARCHAR NOT NULL
);

create index idx_role_name on role (name);

INSERT INTO role (id, name) VALUES (1, 'ADMIN');
INSERT INTO role (id, name) VALUES (2, 'USER');
INSERT INTO role (id, name) VALUES (3, 'CUSTOMER');
