DROP DATABASE IF EXISTS librarian;
CREATE DATABASE IF NOT EXISTS librarian;

USE librarian;

DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS employee_loans_by_employee;
DROP TABLE IF EXISTS employee_role;
DROP TABLE IF EXISTS hibernate_sequence;
DROP TABLE IF EXISTS item;
DROP TABLE IF EXISTS loan;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS user_loan_line;

CREATE TABLE IF NOT EXISTS employee (
  id BIGINT NOT NULL auto_increment,
  email VARCHAR(255),
  first_name VARCHAR(255),
  active BIT,
  last_name VARCHAR(255),
  mobile VARCHAR(255),
  password VARCHAR(255),
  reset_token VARCHAR(255),
  PRIMARY KEY (id)
) engine=InnoDB;


CREATE TABLE IF NOT EXISTS user (
  id BIGINT NOT NULL auto_increment,
  image LONGBLOB DEFAULT NULL,
  address VARCHAR(255),
  city VARCHAR(255),
  email VARCHAR(255),
  first_name VARCHAR(255),
  last_name VARCHAR(255),
  phone_number VARCHAR(255),
  post_code VARCHAR(255),
  PRIMARY KEY (id)
) engine=InnoDB;


CREATE TABLE IF NOT EXISTS item (
  id BIGINT NOT NULL auto_increment,
  image longblob DEFAULT NULL,
  author VARCHAR(255),
  distribution_type VARCHAR(255),
  genre VARCHAR(255),
  international_segregation_number VARCHAR(255),
  is_available VARCHAR(255),
  item_type VARCHAR(255),
  publisher VARCHAR(50),
  release_number VARCHAR(255),
  title VARCHAR(600),
  year INTEGER NOT NULL ,
  loan_id BIGINT,
  PRIMARY KEY (id)
) engine=InnoDB;


CREATE TABLE IF NOT EXISTS loan (
  id BIGINT NOT NULL auto_increment,
  before_deadline bit NOT NULL ,
  loan_date DATE,
  loan_deadline DATE,
  employee_id BIGINT,
  item_id BIGINT,
  user_id BIGINT,
  PRIMARY KEY (id),
  CONSTRAINT FK_employee_id FOREIGN KEY (employee_id) REFERENCES employee(id),
  CONSTRAINT FK_item_id FOREIGN KEY (item_id) REFERENCES item(id),
  CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES user(id)
) engine=InnoDB;


CREATE TABLE IF NOT EXISTS role (
  id BIGINT NOT NULL auto_increment,
  name VARCHAR(255),
  PRIMARY KEY (id)
) engine=InnoDB;


CREATE TABLE IF NOT EXISTS employee_loans_by_employee (
  employee_id BIGINT NOT NULL auto_increment,
  loans_by_employee_id BIGINT NOT NULL,
  PRIMARY KEY (employee_id, loans_by_employee_id),
  CONSTRAINT employee_loans_unique UNIQUE (loans_by_employee_id),
  CONSTRAINT FK_loans_by_emoployee_id FOREIGN KEY (loans_by_employee_id) REFERENCES loan (id),
  CONSTRAINT FK_emoployee_id FOREIGN KEY (employee_id) REFERENCES employee (id)
) engine=InnoDB;


CREATE TABLE IF NOT EXISTS employee_role (
  employee_role_id BIGINT NOT NULL auto_increment,
  employee_id BIGINT,
  role_id BIGINT,
  PRIMARY KEY (employee_role_id),
  CONSTRAINT FK_role_employee_id FOREIGN KEY (employee_id) REFERENCES employee (id),
  CONSTRAINT FK_role_id FOREIGN KEY (role_id) REFERENCES role(id)
) engine=InnoDB;


CREATE TABLE IF NOT EXISTS user_loan_line (
  user_id BIGINT NOT NULL auto_increment,
  loan_line_id BIGINT NOT NULL ,
  PRIMARY KEY (user_id, loan_line_id),
  CONSTRAINT user_loans_unique UNIQUE (loan_line_id),
  CONSTRAINT FK_loan_line_id FOREIGN KEY (loan_line_id) REFERENCES loan(id),
  CONSTRAINT FK_user_loan_id FOREIGN KEY (user_id) REFERENCES user(id)
) engine=InnoDB;

ALTER TABLE item
  ADD CONSTRAINT FK_loan_id FOREIGN KEY (loan_id) REFERENCES loan(id);
