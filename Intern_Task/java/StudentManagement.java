import java.util.ArrayList;
import java.util.Scanner;

public class StudentManagement {
    // Inner class representing a Student
    public static class Student {
        private static int idCounter = 101; // Auto-incrementing ID start
        private int id;
        private String name;
        private String email;
        private String phone;
        private String department;

        public Student(String name, String email, String phone, String department) {
            this.id = idCounter++;
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
        // Prepopulate with seed data matching Task 1
        studentList.add(new Student("Rahul Sharma", "rahul@klu.ac.in", "9876543210", "IT"));
        studentList.add(new Student("Priya Patel", "priya.p@klu.ac.in", "9876543211", "CS"));
        studentList.add(new Student("Ananya Singh", "asingh@klu.ac.in", "9876543212", "ECE"));

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            printHeader();
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. Exit");
            System.out.print("\nEnter your choice (1-4): ");

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
                    System.out.println("Exiting the application. Goodbye!");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please select an option between 1 and 4.");
            }
        }
        scanner.close();
    }

    private static void printHeader() {
        System.out.println("\n========================================");
        System.out.println("       STUDENT MANAGEMENT SYSTEM        ");
        System.out.println("========================================");
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
}
