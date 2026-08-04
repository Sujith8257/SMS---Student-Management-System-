package java;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentManagement {
    // Database configuration defaults
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/student_db";
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "admin";
    
    private static boolean useDatabase = false;
    private static List<Student> inMemoryRegistry = new ArrayList<>();
    private static int idSequence = 101;

    // Student Entity Model
    public static class Student {
        private int id;
        private String name;
        private String email;
        private int age;
        private String course;
        private double grade;

        public Student(int id, String name, String email, int age, String course, double grade) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.age = age;
            this.course = course;
            this.grade = grade;
        }

        // Getters and Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }
        public String getCourse() { return course; }
        public void setCourse(String course) { this.course = course; }
        public double getGrade() { return grade; }
        public void setGrade(double grade) { this.grade = grade; }

        @Override
        public String toString() {
            return String.format("| %-4d | %-20s | %-28s | %-3d | %-22s | %-5.2f |", 
                    id, name, email, age, course, grade);
        }
    }

    public static void main(String[] args) {
        initializeFallbackData();
        testDatabaseConnection();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            printHeader();
            System.out.println(" 1. View All Student Records");
            System.out.println(" 2. Register New Student");
            System.out.println(" 3. Update Existing Student");
            System.out.println(" 4. Remove Student Record");
            System.out.println(" 5. Search Student by Name");
            System.out.println(" 6. Exit Application");
            System.out.print("\nSelect an action (1-6): ");

            String choice = scanner.nextLine();
            System.out.println();

            switch (choice) {
                case "1":
                    viewAllStudents();
                    break;
                case "2":
                    registerStudent(scanner);
                    break;
                case "3":
                    updateStudent(scanner);
                    break;
                case "4":
                    removeStudent(scanner);
                    break;
                case "5":
                    searchStudent(scanner);
                    break;
                case "6":
                    System.out.println("Thank you for using Student Management System. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Error: Invalid option. Please enter a number between 1 and 6.");
            }
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    private static void printHeader() {
        System.out.println("\n==========================================================================");
        System.out.println("                     STUDENT MANAGEMENT SYSTEM (SMS)");
        System.out.println("==========================================================================");
        System.out.println(" Database Mode: " + (useDatabase ? "CONNECTED (PostgreSQL)" : "OFFLINE (Local Memory Fallback)"));
        System.out.println("--------------------------------------------------------------------------");
    }

    private static void initializeFallbackData() {
        inMemoryRegistry.add(new Student(101, "Alexander Wright", "alex.wright@university.edu", 20, "Computer Science", 3.85));
        inMemoryRegistry.add(new Student(102, "Sophia Martinez", "s.martinez@university.edu", 21, "Data Science", 3.92));
        inMemoryRegistry.add(new Student(103, "Marcus Thompson", "marcus.t@university.edu", 22, "Mechanical Engineering", 2.85));
        idSequence = 104;
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    private static void testDatabaseConnection() {
        try {
            // Attempt to load driver and establish a connection
            Class.forName("org.postgresql.Driver");
            try (Connection conn = getConnection()) {
                useDatabase = true;
                System.out.println("Successfully connected to database: " + conn.getMetaData().getDatabaseProductName());
            }
        } catch (Exception e) {
            useDatabase = false;
            System.out.println("[System Info] Database not connected. Running in Offline Memory mode.");
        }
    }

    private static void viewAllStudents() {
        System.out.println("+------+----------------------+------------------------------+-----+------------------------+-------+");
        System.out.println("| ID   | Name                 | Email                        | Age | Course                 | GPA   |");
        System.out.println("+------+----------------------+------------------------------+-----+------------------------+-------+");

        if (useDatabase) {
            String sql = "SELECT * FROM students ORDER BY id ASC";
            try (Connection conn = getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                int count = 0;
                while (rs.next()) {
                    Student s = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("age"),
                        rs.getString("course"),
                        rs.getDouble("grade")
                    );
                    System.out.println(s);
                    count++;
                }
                if (count == 0) {
                    System.out.println("|                      No student records found in the database.                        |");
                }
            } catch (SQLException e) {
                System.out.println("Error reading database: " + e.getMessage());
            }
        } else {
            if (inMemoryRegistry.isEmpty()) {
                System.out.println("|                        No student records found.                              |");
            } else {
                for (Student s : inMemoryRegistry) {
                    System.out.println(s);
                }
            }
        }
        System.out.println("+------+----------------------+------------------------------+-----+------------------------+-------+");
    }

    private static void registerStudent(Scanner scanner) {
        System.out.println("--- Register New Student ---");
        System.out.print("Enter Full Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email Address: ");
        String email = scanner.nextLine();
        
        int age = 0;
        while (true) {
            try {
                System.out.print("Enter Age: ");
                age = Integer.parseInt(scanner.nextLine());
                if (age >= 16 && age <= 100) break;
                System.out.println("Error: Age must be between 16 and 100.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number for age.");
            }
        }

        System.out.print("Enter Course Department: ");
        String course = scanner.nextLine();

        double gpa = 0.0;
        while (true) {
            try {
                System.out.print("Enter GPA (0.00 - 4.00): ");
                gpa = Double.parseDouble(scanner.nextLine());
                if (gpa >= 0.0 && gpa <= 4.0) break;
                System.out.println("Error: GPA must be between 0.0 and 4.0.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid decimal for GPA.");
            }
        }

        if (useDatabase) {
            String sql = "INSERT INTO students (name, email, age, course, grade) VALUES (?, ?, ?, ?, ?)";
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.setInt(3, age);
                pstmt.setString(4, course);
                pstmt.setDouble(5, gpa);
                pstmt.executeUpdate();
                
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        System.out.println("\nSuccess: Student registered with ID: " + generatedKeys.getInt(1));
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error saving to database: " + e.getMessage());
            }
        } else {
            Student s = new Student(idSequence++, name, email, age, course, gpa);
            inMemoryRegistry.add(s);
            System.out.println("\nSuccess: Student registered with offline ID: " + s.getId());
        }
    }

    private static void updateStudent(Scanner scanner) {
        System.out.println("--- Update Existing Student ---");
        System.out.print("Enter Student ID to update: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid ID format.");
            return;
        }

        Student existing = null;
        if (useDatabase) {
            String sql = "SELECT * FROM students WHERE id = ?";
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        existing = new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getInt("age"),
                            rs.getString("course"),
                            rs.getDouble("grade")
                        );
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error loading student: " + e.getMessage());
            }
        } else {
            for (Student s : inMemoryRegistry) {
                if (s.getId() == id) {
                    existing = s;
                    break;
                }
            }
        }

        if (existing == null) {
            System.out.println("Error: Student record not found.");
            return;
        }

        System.out.println("\nCurrent Profile:");
        System.out.println(existing);
        System.out.println("\nEnter new information (Leave blank to keep current):");

        System.out.print("Name [" + existing.getName() + "]: ");
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) name = existing.getName();

        System.out.print("Email [" + existing.getEmail() + "]: ");
        String email = scanner.nextLine();
        if (email.trim().isEmpty()) email = existing.getEmail();

        int age = existing.getAge();
        while (true) {
            System.out.print("Age [" + existing.getAge() + "]: ");
            String ageStr = scanner.nextLine();
            if (ageStr.trim().isEmpty()) break;
            try {
                int parsedAge = Integer.parseInt(ageStr);
                if (parsedAge >= 16 && parsedAge <= 100) {
                    age = parsedAge;
                    break;
                }
                System.out.println("Error: Age must be between 16 and 100.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid integer.");
            }
        }

        System.out.print("Course [" + existing.getCourse() + "]: ");
        String course = scanner.nextLine();
        if (course.trim().isEmpty()) course = existing.getCourse();

        double grade = existing.getGrade();
        while (true) {
            System.out.print("GPA [" + existing.getGrade() + "]: ");
            String gradeStr = scanner.nextLine();
            if (gradeStr.trim().isEmpty()) break;
            try {
                double parsedGrade = Double.parseDouble(gradeStr);
                if (parsedGrade >= 0.0 && parsedGrade <= 4.0) {
                    grade = parsedGrade;
                    break;
                }
                System.out.println("Error: GPA must be between 0.0 and 4.0.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid decimal.");
            }
        }

        if (useDatabase) {
            String sql = "UPDATE students SET name = ?, email = ?, age = ?, course = ?, grade = ? WHERE id = ?";
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.setInt(3, age);
                pstmt.setString(4, course);
                pstmt.setDouble(5, grade);
                pstmt.setInt(6, id);
                pstmt.executeUpdate();
                System.out.println("\nSuccess: Student profile updated in database.");
            } catch (SQLException e) {
                System.out.println("Error updating database: " + e.getMessage());
            }
        } else {
            existing.setName(name);
            existing.setEmail(email);
            existing.setAge(age);
            existing.setCourse(course);
            existing.setGrade(grade);
            System.out.println("\nSuccess: Student profile updated locally.");
        }
    }

    private static void removeStudent(Scanner scanner) {
        System.out.println("--- Remove Student Record ---");
        System.out.print("Enter Student ID to remove: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid ID format.");
            return;
        }

        if (useDatabase) {
            String sql = "DELETE FROM students WHERE id = ?";
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("\nSuccess: Student record deleted from database.");
                } else {
                    System.out.println("Error: Student record not found in the database.");
                }
            } catch (SQLException e) {
                System.out.println("Error deleting from database: " + e.getMessage());
            }
        } else {
            Student toRemove = null;
            for (Student s : inMemoryRegistry) {
                if (s.getId() == id) {
                    toRemove = s;
                    break;
                }
            }
            if (toRemove != null) {
                inMemoryRegistry.remove(toRemove);
                System.out.println("\nSuccess: Student record deleted locally.");
            } else {
                System.out.println("Error: Student record not found.");
            }
        }
    }

    private static void searchStudent(Scanner scanner) {
        System.out.println("--- Search Student ---");
        System.out.print("Enter Name fragment to search: ");
        String nameQuery = scanner.nextLine().trim();

        System.out.println("+------+----------------------+------------------------------+-----+------------------------+-------+");
        System.out.println("| ID   | Name                 | Email                        | Age | Course                 | GPA   |");
        System.out.println("+------+----------------------+------------------------------+-----+------------------------+-------+");

        if (useDatabase) {
            String sql = "SELECT * FROM students WHERE name ILIKE ? ORDER BY id ASC";
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, "%" + nameQuery + "%");
                
                int count = 0;
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Student s = new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getInt("age"),
                            rs.getString("course"),
                            rs.getDouble("grade")
                        );
                        System.out.println(s);
                        count++;
                    }
                }
                if (count == 0) {
                    System.out.println("|                       No matching student records found.                              |");
                }
            } catch (SQLException e) {
                System.out.println("Error querying database: " + e.getMessage());
            }
        } else {
            int count = 0;
            for (Student s : inMemoryRegistry) {
                if (s.getName().toLowerCase().contains(nameQuery.toLowerCase())) {
                    System.out.println(s);
                    count++;
                }
            }
            if (count == 0) {
                System.out.println("|                       No matching student records found.                              |");
            }
        }
        System.out.println("+------+----------------------+------------------------------+-----+------------------------+-------+");
    }
}
