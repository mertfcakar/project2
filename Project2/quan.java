CREATE DATABASE FirmManagement;


USE FirmManagement;

CREATE TABLE employees (
    employee_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role ENUM('manager', 'engineer', 'technician', 'intern') NOT NULL,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    phone_no VARCHAR(15),
    date_of_birth DATE,
    date_of_start DATE NOT NULL,
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO employees (username, password, role, name, surname, phone_no, date_of_birth, date_of_start, email) 
VALUES
('manager1', 'defaultPassword1', 'manager', 'Alice', 'Smith', '1234567890', '1980-05-15', '2015-06-01', 'alice.smith@example.com'),
('manager2', 'defaultPassword2', 'manager', 'Bob', 'Johnson', '0987654321', '1978-03-22', '2010-01-12', 'bob.johnson@example.com'),
('engineer1', 'defaultPassword3', 'engineer', 'Charlie', 'Brown', '1122334455', '1990-07-19', '2020-02-15', 'charlie.brown@example.com'),
('technician1', 'defaultPassword4', 'technician', 'Dave', 'Clark', '6677889900', '1995-12-01', '2018-04-20', 'dave.clark@example.com'),
('intern1', 'defaultPassword5', 'intern', 'Eve', 'Miller', '4455667788', '2000-10-10', '2023-01-05', 'eve.miller@example.com'),
-- Add more rows to ensure there are at least 20 entries

DELIMITER //
CREATE PROCEDURE AddEmployee (
    IN p_username VARCHAR(50),
    IN p_password VARCHAR(100),
    IN p_role ENUM('manager', 'engineer', 'technician', 'intern'),
    IN p_name VARCHAR(50),
    IN p_surname VARCHAR(50),
    IN p_phone_no VARCHAR(15),
    IN p_date_of_birth DATE,
    IN p_date_of_start DATE,
    IN p_email VARCHAR(100)
)
BEGIN
    INSERT INTO employees (username, password, role, name, surname, phone_no, date_of_birth, date_of_start, email)
    VALUES (p_username, p_password, p_role, p_name, p_surname, p_phone_no, p_date_of_birth, p_date_of_start, p_email);
END;
//
DELIMITER ;


DELIMITER //
CREATE PROCEDURE UpdateEmployee (
    IN p_employee_id INT,
    IN p_phone_no VARCHAR(15),
    IN p_email VARCHAR(100),
    IN p_password VARCHAR(100)
)
BEGIN
    UPDATE employees
    SET phone_no = p_phone_no, email = p_email, password = p_password
    WHERE employee_id = p_employee_id;
END;
//
DELIMITER ;


DELIMITER //
CREATE PROCEDURE DeleteEmployee (IN p_employee_id INT)
BEGIN
    DELETE FROM employees WHERE employee_id = p_employee_id;
END;
//
DELIMITER ;
