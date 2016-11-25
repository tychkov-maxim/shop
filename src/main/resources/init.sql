CREATE TABLE PUBLIC.roles
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name CHAR(50) NOT NULL
);

CREATE UNIQUE INDEX "roles_name_uindex" ON PUBLIC.roles (name);

CREATE TABLE PUBLIC.users
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    login CHAR(50) NOT NULL,
    password CHAR(50) NOT NULL,
    first_name CHAR(255) NOT NULL,
    last_name CHAR(255) NOT NULL,
    role INT,
    account DECIMAL NOT NULL,
    account_unit CHAR(3) NOT NULL,
    CONSTRAINT users_ROLES_ID_fk FOREIGN KEY (role) REFERENCES roles (ID)
);

CREATE UNIQUE INDEX "users_login_uindex" ON PUBLIC.users (login);

insert into roles VALUES (0,'Anonymous');
insert into roles VALUES (1,'User');
insert into roles VALUES (2,'Administrator');
INSERT INTO users VALUES (DEFAULT, 'hello','world','Max','Last Name',2,0.0,'USD');