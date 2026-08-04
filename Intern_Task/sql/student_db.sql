-- Student Management Database Setup Script
-- Compatible with PostgreSQL and MySQL

-- 1. Database Creation (Run manually if required)
-- CREATE DATABASE student_db;

-- 2. Drop existing table if exists
DROP TABLE IF EXISTS students;

-- 3. Create Table Definition
CREATE TABLE students (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    age INT NOT NULL CHECK (age >= 16 AND age <= 100),
    course VARCHAR(100) NOT NULL,
    grade DECIMAL(4, 2) NOT NULL CHECK (grade >= 0.00 AND grade <= 4.00),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. Create Indexes for Search Performance Optimization
CREATE INDEX idx_students_name ON students (name);
CREATE INDEX idx_students_course ON students (course);

-- 5. Seed Database with Initial Student Entries
INSERT INTO students (name, email, age, course, grade) VALUES
('Alexander Wright', 'alex.wright@university.edu', 20, 'Computer Science', 3.85),
('Sophia Martinez', 's.martinez@university.edu', 21, 'Data Science', 3.92),
('Marcus Thompson', 'marcus.t@university.edu', 22, 'Mechanical Engineering', 2.85),
('Emily Henderson', 'emily.h@university.edu', 19, 'Bioinformatics', 3.70),
('Darius Miller', 'darius.m@university.edu', 23, 'Cybersecurity', 1.82),
('Livia Vance', 'liv.vance@university.edu', 20, 'Civil Engineering', 3.45);
