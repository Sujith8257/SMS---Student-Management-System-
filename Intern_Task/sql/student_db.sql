-- =====================================================
-- Student Management System
-- Task 3 - MySQL Database
-- =====================================================

-- ==========================================
-- 1. Create Database
-- ==========================================

CREATE DATABASE IF NOT EXISTS student_db;

-- ==========================================
-- 2. Use Database
-- ==========================================

USE student_db;

-- ==========================================
-- 3. Drop Existing Table (Optional)
-- ==========================================

DROP TABLE IF EXISTS students;

-- ==========================================
-- 4. Create Students Table
-- ==========================================

CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(10) NOT NULL,
    department VARCHAR(50) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    dob DATE NOT NULL
);

-- ==========================================
-- 5. Insert Student Records
-- ==========================================

INSERT INTO students
(name, email, phone, department, gender, dob)
VALUES
('Tanguturin Venkata Sujith Gopi', '99220041389@klu.ac.in', '7989418257', 'CS', 'Male', '2004-07-05'),
('Rahul Sharma', 'rahul@klu.ac.in', '9876543210', 'IT', 'Male', '2003-02-10'),
('Priya Patel', 'priya@klu.ac.in', '9876543211', 'CS', 'Female', '2002-08-15'),
('Ananya Singh', 'ananya@klu.ac.in', '9876543212', 'ECE', 'Female', '2003-05-21'),
('Arjun Kumar', 'arjun@klu.ac.in', '9876543213', 'Mechanical', 'Male', '2002-11-30'),
('Sneha Reddy', 'sneha@klu.ac.in', '9876543214', 'Civil', 'Female', '2003-01-18');

-- ==========================================
-- 6. Display All Students
-- ==========================================

SELECT * FROM students;

-- ==========================================
-- 7. Search Students by Department
-- ==========================================

SELECT *
FROM students
WHERE department = 'IT';

-- ==========================================
-- 8. Update Student Phone Number
-- ==========================================

UPDATE students
SET phone = '9999999999'
WHERE id = 2;

-- Verify Update
SELECT *
FROM students
WHERE id = 2;

-- ==========================================
-- 9. Delete Student Record
-- ==========================================

DELETE FROM students
WHERE id = 5;

-- Verify Remaining Records
SELECT * FROM students;

-- ==========================================
-- 10. Count Total Students (Optional)
-- ==========================================

SELECT COUNT(*) AS Total_Students
FROM students;