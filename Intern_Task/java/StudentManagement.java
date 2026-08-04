import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentManagement {
    private static final String FILE_NAME = "students.txt";

    // Inner class representing a Student
    public static class Student {
        private static int idCounter = 101; // Auto-incrementing ID start
        private int id;
        private String name;
        private String email;
        private String phone;
        private String department;

        // Constructor for new students (auto-generates ID)
        public Student(String name, String email, String phone, String department) {
            this.id = idCounter++;
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.department = department;
        }

        // Constructor for loading existing students
        public Student(int id, String name, String email, String phone, String department) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.department = department;
        }

        // Getters
        public int getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getPhone() { return phone; }
        public String getDepartment() { return department; }

        @Override
        public String toString() {
            return String.format("| %-4d | %-20s | %-28s | %-12s | %-12s |", 
                    id, name, email, phone, department);
        }
    }

    private static ArrayList<Student> studentList = new ArrayList<>();

    public static void main(String[] args) {
        // Load records from local file
        loadStudentsFromFile();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            printHeader();
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("\nEnter your choice (1-5): ");

            String choice = scanner.nextLine().trim();
            System.out.println();

            switch (choice) {
                case "1":
                    addStudent(scanner);
                    break;
                case "2":
                    viewStudents();
                    break;
                case "3":
                    searchStudent(scanner);
                    break;
                case "4":
                    deleteStudent(scanner);
                    break;
                case "5":
                    System.out.println("Exiting the application. Goodbye!");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please select an option between 1 and 5.");
            }
        }
        scanner.close();
    }

    private static void printHeader() {
        System.out.println("\n========================================");
        System.out.println("       STUDENT MANAGEMENT SYSTEM        ");
        System.out.println("========================================");
    }

    // Method to load students from text file
    private static void loadStudentsFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            // Prepopulate seed data if file doesn't exist yet
            studentList.add(new Student("Rahul Sharma", "rahul@klu.ac.in", "9876543210", "IT"));
            studentList.add(new Student("Priya Patel", "priya.p@klu.ac.in", "9876543211", "CS"));
            studentList.add(new Student("Ananya Singh", "asingh@klu.ac.in", "9876543212", "ECE"));
            saveStudentsToFile();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int maxId = 100;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length == 5) {
                    try {
                        int id = Integer.parseInt(parts[0].trim());
                        String name = parts[1].trim();
                        String email = parts[2].trim();
                        String phone = parts[3].trim();
                        String department = parts[4].trim();
                        
                        Student s = new Student(id, name, email, phone, department);
                        studentList.add(s);
                        if (id > maxId) {
                            maxId = id;
                        }
                    } catch (NumberFormatException e) {
                        // Ignore corrupt rows
                    }
                }
            }
            // Update counter sequence
            Student.idCounter = maxId + 1;
        } catch (IOException e) {
            System.out.println("Error loading student records from file: " + e.getMessage());
        }
    }

    // Method to save students to text file
    private static void saveStudentsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student s : studentList) {
                writer.write(String.format("%d,%s,%s,%s,%s\n", 
                        s.getId(), s.getName(), s.getEmail(), s.getPhone(), s.getDepartment()));
            }
        } catch (IOException e) {
            System.out.println("Error saving student records to file: " + e.getMessage());
        }
    }

    // Method to add a new student
    private static void addStudent(Scanner scanner) {
        System.out.println("--- Add Student ---");
        
        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();
        while (name.isEmpty()) {
            System.out.print("Name cannot be empty. Enter Name: ");
            name = scanner.nextLine().trim();
        }

        System.out.print("Enter Email (@klu.ac.in): ");
        String email = scanner.nextLine().trim();
        while (email.isEmpty() || !email.endsWith("@klu.ac.in")) {
            System.out.print("Invalid format. Enter Email (@klu.ac.in): ");
            email = scanner.nextLine().trim();
        }

        System.out.print("Enter Phone (10 digits): ");
        String phone = scanner.nextLine().trim();
        while (!phone.matches("\\d{10}")) {
            System.out.print("Invalid format. Enter Phone (10 digits): ");
            phone = scanner.nextLine().trim();
        }

        System.out.print("Enter Department (e.g., IT, CS, ECE): ");
        String department = scanner.nextLine().trim();
        while (department.isEmpty()) {
            System.out.print("Department cannot be empty. Enter Department: ");
            department = scanner.nextLine().trim();
        }

        Student student = new Student(name, email, phone, department);
        studentList.add(student);
        saveStudentsToFile(); // Save changes to disk
        System.out.println("\nSuccess: Student added successfully with ID: " + student.getId());
    }

    // Method to view all students
    private static void viewStudents() {
        System.out.println("--- Student Records ---");
        if (studentList.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        System.out.println("+------+----------------------+------------------------------+--------------+--------------+");
        System.out.println("| ID   | Name                 | Email                        | Phone        | Department   |");
        System.out.println("+------+----------------------+------------------------------+--------------+--------------+");
        for (Student student : studentList) {
            System.out.println(student);
        }
        System.out.println("+------+----------------------+------------------------------+--------------+--------------+");
    }

    // Method to search students by name
    private static void searchStudent(Scanner scanner) {
        System.out.println("--- Search Student by Name ---");
        System.out.print("Enter name to search: ");
        String query = scanner.nextLine().trim().toLowerCase();

        if (query.isEmpty()) {
            System.out.println("Search query cannot be empty.");
            return;
        }

        boolean found = false;
        boolean printedHeader = false;

        for (Student student : studentList) {
            if (student.getName().toLowerCase().contains(query)) {
                if (!printedHeader) {
                    System.out.println("\n+------+----------------------+------------------------------+--------------+--------------+");
                    System.out.println("| ID   | Name                 | Email                        | Phone        | Department   |");
                    System.out.println("+------+----------------------+------------------------------+--------------+--------------+");
                    printedHeader = true;
                }
                System.out.println(student);
                found = true;
            }
        }

        if (printedHeader) {
            System.out.println("+------+----------------------+------------------------------+--------------+--------------+");
        }

        if (!found) {
            System.out.println("No matching student records found.");
        }
    }

    // Method to delete a student by ID
    private static void deleteStudent(Scanner scanner) {
        System.out.println("--- Delete Student ---");
        viewStudents();
        if (studentList.isEmpty()) {
            return;
        }
        System.out.print("Enter Student ID to delete: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid ID format. Please enter a valid number.");
            return;
        }

        Student toRemove = null;
        for (Student student : studentList) {
            if (student.getId() == id) {
                toRemove = student;
                break;
            }
        }

        if (toRemove != null) {
            System.out.print("Are you sure you want to delete student " + toRemove.getName() + "? (Y/N): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            if (confirm.equals("y") || confirm.equals("yes")) {
                studentList.remove(toRemove);
                saveStudentsToFile(); // Save changes to disk
                System.out.println("\nSuccess: Student record deleted successfully.");
            } else {
                System.out.println("Delete action cancelled.");
            }
        } else {
            System.out.println("Error: Student record with ID " + id + " not found.");
        }
    }
}
