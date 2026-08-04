# Student Management System

A simple **Student Management System** developed as part of an internship assessment. The project demonstrates frontend development using **HTML, CSS, and Vanilla JavaScript**, a **Java console application**, and **MySQL database scripting**.

---

# Project Overview

This project is divided into **three independent tasks**:

1. **Frontend Development**

   * Responsive Student Management System using HTML, CSS, and Vanilla JavaScript.
   * Student data is stored using **JavaScript Local Storage**.

2. **Java Programming**

   * Console-based Student Management System demonstrating object-oriented programming concepts.

3. **MySQL**

   * Database creation, table creation, and SQL queries for basic CRUD operations.

---

# Technologies Used

### Frontend

* HTML5
* CSS3
* Vanilla JavaScript
* Local Storage API

### Java

* Java
* Object-Oriented Programming
* ArrayList
* Scanner

### Database

* MySQL

---

# Project Structure

```text
Intern_Task/
│
├── index.html                 # Login Page
├── dashboard.html             # Dashboard
├── student.html               # Student Registration
├── student-list.html          # Student List (or Student List section)
│
├── css/
│   └── style.css              # Application styles
│
├── js/
│   └── app.js                 # JavaScript logic
│
├── java/
│   └── StudentManagement.java # Java console application
│
├── sql/
│   └── student_db.sql         # MySQL queries
│
└── README.md
```

---

# Task 1 – Frontend Development

## Objective

Develop a responsive Student Management System using **HTML, CSS, and Vanilla JavaScript**.

---

## Pages

### 1. Login Page

Features

* Username input
* Password input
* Login button
* Client-side validation
* Redirect to Dashboard after successful login

---

### 2. Dashboard

Features

* Welcome section
* Navigation menu
* Register Student
* Student List
* Logout

---

### 3. Student Registration

The registration form contains the following fields:

* Student Name
* Email Address
* Phone Number
* Department (Dropdown)
* Gender (Radio Buttons)
* Date of Birth
* Submit Button

---

### Form Validation

The following validations are implemented:

* All fields are mandatory.
* Email must be in a valid format.
* Phone number must contain exactly **10 digits**.
* Validation messages are displayed for invalid inputs.
* Form submission is prevented until all validations pass.

---

### Local Storage

After successful form submission:

* Student details are stored in JavaScript Local Storage.
* Data remains available after refreshing the page.
* Student records are dynamically displayed.

---

### Student List

Displays all registered students in a table.

Table Columns

* Student Name
* Email
* Phone Number
* Department
* Gender
* Date of Birth
* Actions

Features

* Search student by name
* Edit student details
* Delete student record

---

### UI Features

* Responsive design
* Clean user interface
* Consistent color scheme
* Proper spacing
* Readable typography
* Simple navigation
* Modern card-based layout

---

# Task 2 – Java Programming

## Objective

Develop a console-based Student Management System using Java.

---

## Menu

```text
1. Add Student
2. View Students
3. Search Student
4. Exit
```

---

## Functional Requirements

### Add Student

Allows the user to enter student information and store it in an ArrayList.

---

### View Students

Displays all student records stored in memory.

---

### Search Student

Searches students by their name.

---

### Exit

Terminates the application.

---

## Java Concepts Used

* Classes
* Objects
* Methods
* ArrayList
* Loops
* Conditional Statements
* Scanner Class

---

# Task 3 – MySQL

## Database Name

```sql
student_db
```

---

## Table Name

```sql
students
```

---

## Table Structure

| Column     | Data Type |
| ---------- | --------- |
| id         | INT       |
| name       | VARCHAR   |
| email      | VARCHAR   |
| phone      | VARCHAR   |
| department | VARCHAR   |

---

## SQL Queries Included

* Create Database
* Create Table
* Insert at least five student records
* Display all students
* Search students by department
* Update a student's phone number
* Delete a student record

---

# How to Run

## Frontend

1. Download or clone the project.
2. Open the `Intern_Task` folder.
3. Open `index.html` in your browser.
4. Login and navigate through the application.
5. Register students and manage records.

---

## Java

1. Open the `java` folder.
2. Compile the program:

```bash
javac StudentManagement.java
```

3. Run the application:

```bash
java StudentManagement
```

4. Use the console menu to manage student records.

---

## MySQL

1. Open MySQL Workbench (or any MySQL client).
2. Open the `student_db.sql` file.
3. Execute all SQL statements.
4. Verify the database and table are created successfully.

---

# Features Summary

## Frontend

* Responsive design
* Login page
* Dashboard
* Student registration
* Client-side validation
* Local Storage integration
* Student list
* Search functionality
* Edit functionality
* Delete functionality

---

## Java

* Add student
* View all students
* Search student
* Exit application

---

## MySQL

* Create database
* Create table
* Insert records
* Retrieve records
* Search records
* Update records
* Delete records

---

# Learning Outcomes

This project demonstrates understanding of:

* HTML page structure
* CSS responsive layouts
* JavaScript DOM manipulation
* Form validation
* Local Storage API
* CRUD operations
* Java Object-Oriented Programming
* Collections using ArrayList
* SQL database design
* SQL CRUD queries

---

# Future Improvements

* Backend integration using Java Spring Boot or Node.js
* MySQL database connectivity for the web application
* User authentication with secure login
* Pagination for student records
* Department-wise filtering
* Student profile images
* Export data to Excel or PDF

---

# Author

**Name:** *Your Name*

**Internship Assignment:** Student Management System

**Technologies:** HTML, CSS, JavaScript, Java, MySQL
