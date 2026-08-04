# Student Management System

A **Student Management System** developed as part of an internship assessment. The project covers three independent tasks:

1. **Frontend** — responsive web UI using HTML, Tailwind CSS, and Vanilla JavaScript with Local Storage
2. **Java** — console-based application with OOP, collections, and file persistence via Java Serialization
3. **MySQL** — database schema, seed data, and CRUD SQL queries

Each task can be run on its own. They are not connected to each other (no shared backend).

---

## Table of Contents

- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Task 1 – Frontend Development](#task-1--frontend-development)
- [Task 2 – Java Programming](#task-2--java-programming)
- [Task 3 – MySQL Database](#task-3--mysql-database)
- [How to Run](#how-to-run)
- [Troubleshooting](#troubleshooting)
- [Features Summary](#features-summary)
- [Learning Outcomes](#learning-outcomes)
- [Future Improvements](#future-improvements)

---

## Prerequisites

| Component | Requirement |
|-----------|-------------|
| **Web browser** | Chrome, Edge, or Firefox (latest recommended) |
| **Java** | JDK 8 or higher (`java` and `javac` on PATH) |
| **MySQL** | MySQL Server 8.x + MySQL Workbench or CLI client |
| **Internet** | Required for frontend (Tailwind CSS and Google Fonts load from CDN) |

Verify Java installation:

```bash
java -version
javac -version
```

---

## Project Structure

```text
Intern_Task/
│
├── index.html                 # Login page
├── signup.html                # User sign-up page
├── dashboard.html             # Dashboard with stats and navigation
├── student.html               # Student list, search, add/edit/delete (modal)
├── register student.html      # Standalone student registration page (alternate UI)
│
├── css/
│   └── style.css              # Shared design tokens and scrollbar styles
│
├── js/
│   └── app.js                 # Local Storage API for student CRUD
│
├── java/
│   ├── StudentManagement.java # Console application source
│   └── students.dat           # Runtime data file (created after first run; do not commit)
│
├── sql/
│   └── student_db.sql         # Database creation and sample queries
│
└── README.md
```

---

## Task 1 – Frontend Development

### Objective

Build a responsive Student Management System using **HTML**, **Tailwind CSS** (CDN), and **Vanilla JavaScript**. Student records are stored in the browser using the **Local Storage API**.

### Application Flow

```text
index.html (Login)
    ├── signup.html (Create account) → redirects back to login
    └── dashboard.html (After login)
            ├── Register Student → student.html?action=register
            └── View Student List → student.html
```

### Pages

#### 1. Login (`index.html`)

- Username and password fields with show/hide password toggle
- Client-side validation (both fields required)
- On success, username is saved to `localStorage` under key `sms_current_user`
- Redirects to `dashboard.html`
- Link to **Sign Up** page

> **Note:** Login is demo-only. Any non-empty username and password will succeed. There is no server-side authentication.

#### 2. Sign Up (`signup.html`)

- Username, password, and confirm password
- Validates that passwords match
- Stores account in `localStorage` as `sms_mock_user_<username>`
- Redirects to login with a success message (`?registered=true`)

#### 3. Dashboard (`dashboard.html`)

- Welcome message using logged-in username
- Quick stats (total students, today's registrations, etc.)
- **Register Student** — opens student list with registration modal
- **View Student List** — navigates to full student table
- Logout returns to `index.html`

#### 4. Student List (`student.html`)

Main CRUD interface for students.

**Table columns:** ID, Name, Email, Phone, Department, Gender, Date of Birth, Actions

**Features:**

- Search students by name, email, phone, or department
- **Add** — opens registration modal
- **Edit** — opens modal pre-filled with existing data
- **Delete** — removes record after confirmation
- Responsive layout with mobile navigation drawer

**Registration form fields:**

| Field | Input Type | Validation |
|-------|------------|------------|
| Full Name | Text | Required |
| Email Address | Email | Required, valid email format |
| Phone Number | Text | Required |
| Department | Dropdown (IT, CS, ECE, ME, Civil) | Required |
| Gender | Radio (Male / Female / Other) | Required |
| Date of Birth | Date picker | Required |

Data is managed through `js/app.js` using the global `window.SMS` object:

| Method | Description |
|--------|-------------|
| `SMS.getStudents()` | Returns all students |
| `SMS.getStudent(id)` | Returns one student by ID |
| `SMS.addStudent(data)` | Adds a new student (auto ID) |
| `SMS.updateStudent(id, data)` | Updates an existing student |
| `SMS.deleteStudent(id)` | Deletes a student by ID |

Local Storage key: `sms_students_registry_v2`

Three sample students are seeded automatically on first visit.

### UI Features

- Responsive design (mobile, tablet, desktop)
- Tailwind CSS with custom color palette
- Material Symbols icons
- Card-based layout and modal dialogs
- Clean typography (DM Sans, Inter)

---

## Task 2 – Java Programming

### Objective

Develop a **console-based** Student Management System demonstrating OOP, `ArrayList`, input handling, and **Java Serialization** for persistent storage.

### Menu

```text
========================================
       STUDENT MANAGEMENT SYSTEM
========================================
1. Add Student
2. View Students
3. Search Student
4. Delete Student
5. Exit
```

### Student Fields

| Field | Validation |
|-------|------------|
| Name | Cannot be empty |
| Email | Must end with `@klu.ac.in` |
| Phone | Exactly 10 digits |
| Department | Cannot be empty |
| Gender | Male, Female, or Other (case-insensitive) |
| Date of Birth | Format `DD/MM/YYYY` (e.g. `15/03/2002`) |

Auto-generated student IDs start at **101** and increment automatically.

### Persistence (`students.dat`)

- Records are saved to `students.dat` using **Java Serialization** (`ObjectOutputStream` / `ObjectInputStream`)
- The `Student` inner class implements `Serializable`
- Add, edit (via re-add flow), and delete operations write to disk immediately
- Data persists across application restarts
- On **first run only** (when `students.dat` does not exist), three sample students are created and saved

> **Important:** Always compile and run from the `java/` folder so `students.dat` is created in the correct location.

### Java Concepts Used

- Classes and inner classes
- Encapsulation (private fields, getters)
- `ArrayList` collection
- `Scanner` for console input
- Loops and switch statements
- File I/O and Java Serialization
- Input validation

---

## Task 3 – MySQL Database

### Database Name

```sql
student_db
```

### Table Name

```sql
students
```

### Table Structure

| Column | Data Type | Constraints |
|--------|-----------|-------------|
| id | INT | AUTO_INCREMENT, PRIMARY KEY |
| name | VARCHAR(100) | NOT NULL |
| email | VARCHAR(100) | NOT NULL, UNIQUE |
| phone | VARCHAR(10) | NOT NULL |
| department | VARCHAR(50) | NOT NULL |
| gender | VARCHAR(10) | NOT NULL |
| dob | DATE | NOT NULL |

### SQL Queries Included (`student_db.sql`)

1. Create database (`student_db`)
2. Use database
3. Drop existing table (optional reset)
4. Create `students` table
5. Insert six sample student records
6. `SELECT *` — display all students
7. Search students by department
8. Update a student's phone number
9. Delete a student record
10. Count total students

---

## How to Run

### Task 1 – Frontend (Web Application)

#### Option A: Open directly in browser

1. Navigate to the project folder:

   ```bash
   cd Intern_Task
   ```

2. Open `index.html` in your browser:
   - **Windows:** Double-click `index.html`, or right-click → Open with → Chrome/Edge
   - **Or** drag `index.html` into a browser window

3. Log in with any username and password (e.g. `admin` / `admin123`).

4. Use the dashboard to register and manage students.

#### Option B: Local HTTP server (recommended)

Some browsers restrict Local Storage when opening files via `file://`. Use a simple server:

**Python 3:**

```bash
cd Intern_Task
python -m http.server 8080
```

Then open: [http://localhost:8080/index.html](http://localhost:8080/index.html)

**Node.js (npx):**

```bash
cd Intern_Task
npx serve .
```

#### Frontend walkthrough

1. **Login** → enter username and password → Dashboard opens
2. **Dashboard** → click **Register Student** or **View Student List**
3. **Student List** → search, add, edit, or delete records
4. **Logout** → returns to login page

To reset frontend data, clear browser Local Storage for the site (DevTools → Application → Local Storage → delete `sms_students_registry_v2`).

---

### Task 2 – Java Console Application

1. Open a terminal and go to the Java folder:

   ```bash
   cd Intern_Task/java
   ```

2. Compile:

   ```bash
   javac StudentManagement.java
   ```

3. Run:

   ```bash
   java StudentManagement
   ```

4. Follow the menu prompts:

   | Option | Action |
   |--------|--------|
   | 1 | Add a new student (enter all six fields) |
   | 2 | View all students in a formatted table |
   | 3 | Search by name (partial match, case-insensitive) |
   | 4 | Delete by ID (shows list first, asks Y/N confirmation) |
   | 5 | Exit the application |

5. After adding or deleting, restart the app and choose **2. View Students** to confirm data persisted in `students.dat`.

#### Example: Add Student session

```text
--- Add Student ---
Enter Name: John Doe
Enter Email: john@klu.ac.in
Enter Phone: 9123456789
Enter Department: CS
Enter Gender (Male/Female/Other): Male
Enter Date of Birth (DD/MM/YYYY): 05/07/2004

Success: Student added successfully with ID: 104
```

#### Reset Java data

Delete `students.dat` and run again — fresh seed data will be created:

```bash
# Windows PowerShell
Remove-Item students.dat -ErrorAction SilentlyContinue
java StudentManagement
```

---

### Task 3 – MySQL Database

#### Using MySQL Workbench

1. Open **MySQL Workbench** and connect to your local MySQL server.
2. Go to **File → Open SQL Script**.
3. Select `Intern_Task/sql/student_db.sql`.
4. Click the **Execute** (lightning bolt) button to run all statements.
5. Verify in the **Schemas** panel:
   - Database `student_db` exists
   - Table `students` contains sample rows

#### Using MySQL CLI

```bash
mysql -u root -p < Intern_Task/sql/student_db.sql
```

Or connect interactively:

```bash
mysql -u root -p
```

Then:

```sql
SOURCE D:/SMS/Intern_Task/sql/student_db.sql;
```

(Adjust the path to match your machine.)

#### Verify results

```sql
USE student_db;
SELECT * FROM students;
SELECT COUNT(*) AS Total_Students FROM students;
```

---

## Troubleshooting

| Issue | Solution |
|-------|----------|
| Java: `students.dat` not found / data lost | Run `java StudentManagement` from the `java/` folder, not the project root |
| Java: deleted students reappear | Old `students.txt` is no longer used. Delete `students.dat` only if you want a fresh start |
| Frontend: data not saving | Use a local server instead of `file://`, or check browser Local Storage settings |
| Frontend: blank student list | Open DevTools → Console for errors; ensure `js/app.js` loads correctly |
| MySQL: `Access denied` | Check username/password; use `mysql -u root -p` |
| MySQL: duplicate email error | Script drops and recreates the table; run sections 3–5 again if needed |
| `javac` not recognized | Install JDK and add `bin` folder to system PATH |

---

## Features Summary

### Frontend

- Login and sign-up pages (client-side demo auth)
- Responsive dashboard
- Student registration with validation
- Student list with search
- Edit and delete actions
- Local Storage persistence
- Modal-based add/edit UI

### Java

- Console menu (Add, View, Search, Delete, Exit)
- Six-field student model (including gender and DOB)
- Input validation with retry prompts
- Formatted table output
- Java Serialization persistence (`students.dat`)
- Auto-incrementing student IDs
- Delete confirmation (Y/N)

### MySQL

- Database and table creation
- Six seed records
- SELECT, INSERT, UPDATE, DELETE examples
- Department search and row count queries

---

## Learning Outcomes

This project demonstrates:

- HTML structure and semantic layout
- Responsive UI with Tailwind CSS
- JavaScript DOM manipulation and event handling
- Client-side form validation
- Local Storage as a client-side database
- CRUD operations in JavaScript
- Java OOP (classes, encapsulation, inner classes)
- Java collections (`ArrayList`)
- File persistence with Java Serialization
- Console I/O with `Scanner`
- SQL database design and normalization basics
- MySQL CRUD queries

---

## Future Improvements

- Backend API (Java Spring Boot or Node.js/Express)
- Connect web frontend to MySQL database
- Secure authentication (JWT, hashed passwords)
- REST API for student CRUD
- Pagination and advanced filtering
- Export student data to CSV/PDF
- Unit tests for Java and JavaScript modules
- Migrate Java storage to JDBC + MySQL

---

## Author

**Internship Assignment:** Student Management System

**Technologies:** HTML, Tailwind CSS, JavaScript, Java, MySQL
