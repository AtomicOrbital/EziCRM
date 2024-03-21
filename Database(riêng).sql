DROP DATABASE IF EXISTS crm_project;
CREATE DATABASE crm_project;
USE crm_project;

CREATE TABLE role (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255)
);

CREATE TABLE users (
    userid BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255) NULL,
    address VARCHAR(255),
    date_of_birth DATE,
    email VARCHAR(255) not null,
    phone VARCHAR(255),
    id_role BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (id_role) REFERENCES role(id)
);



CREATE TABLE usergroups  (
    groupid BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE usergrouprelations (
    userid BIGINT,
    groupid BIGINT,
    PRIMARY KEY (userid, groupid),
    FOREIGN KEY (userid) REFERENCES users(userid) ON DELETE CASCADE,
    FOREIGN KEY (groupid) REFERENCES usergroups(groupid) ON DELETE CASCADE
);

CREATE TABLE attributegroups (
    attributegroupid BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE userattributes (
    attributeid BIGINT AUTO_INCREMENT PRIMARY KEY,
    userid BIGINT,
    attributegroupid BIGINT,
    name VARCHAR(255),
    value TEXT,
    FOREIGN KEY (userid) REFERENCES users(userid) ON DELETE CASCADE,
    FOREIGN KEY (attributegroupid) REFERENCES attributegroups(attributegroupid) ON DELETE CASCADE
);

INSERT INTO usergroups (name) VALUES ('khach hang tiem nang'), ('khach hang thuong'), ('khach hang ngheo');
SELECT * FROM usergroups ORDER BY groupid;


INSERT INTO users (username, password, address, date_of_birth, email, phone, id_role)
VALUES ('john_doe', '$2a$12$/K9B3coKwWRA0CV5R72gwe8VK8omWnrio3qREdx2wmYLTZYhWeEMq', '123 Main St', '1980-01-01', 'abc@gmail.com', '555-1234', 1);

INSERT INTO role (name) VALUES ('ROLE_ADMIN'), ('ROLE_USER');

ALTER TABLE users MODIFY COLUMN password VARCHAR(255) NULL;



