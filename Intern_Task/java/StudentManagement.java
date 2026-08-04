import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentManagement {
    private static final String FILE_NAME = "students.dat";

    // Inner class representing a Student
    public static class Student implements Serializable {
        private static final long serialVersionUID = 1L;
        private static int idCounter = 101; // Auto-incrementing ID start
        private int id;
        private String name;
        private String email;
        private String phone;
        private String department;
        private String gender;
        private String dateOfBirth;

        // Constructor for new students (auto-generates ID)
        public Student(String name, String email, String phone, String department,
                       String gender, String dateOfBirth) {
            this.id = idCounter++;
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.department = department;
            this.gender = gender;
            this.dateOfBirth = dateOfBirth;
        }

        // Constructor for loading existing students
        public Student(int id, String name, String email, String phone, String department,
                       String gender, String dateOfBirth) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.department = department;
            this.gender = gender;
            this.dateOfBirth = dateOfBirth;
        }

        // Getters
        public int getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getPhone() { return phone; }
        public String getDepartment() { return department; }
        public String getGender() { return gender; }
        public String getDateOfBirth() { return dateOfBirth; }

        @Override
        public String toString() {
            return String.format("| %-4d | %-18s | %-24s | %-10s | %-8s | %-8s | %-10s |",
                    id, name, email, phone, department, gender, dateOfBirth);
        }
    }

    private static ArrayList<Student> studentList = new ArrayList<>();

    public static void main(String[] args) {
        loadStudents();

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

    private static void printTableHeader() {
        System.out.println("+------+--------------------+--------------------------+------------+----------+----------+------------+");
        System.out.println("| ID   | Name               | Email                    | Phone      | Dept     | Gender   | DOB        |");
        System.out.println("+------+--------------------+--------------------------+------------+----------+----------+------------+");
    }

    private static void printTableFooter() {
        System.out.println("+------+--------------------+--------------------------+------------+----------+----------+------------+");
    }

    // Load students from serialized file
    private static void loadStudents() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            studentList.add(new Student("Rahul Sharma", "rahul@klu.ac.in", "9876543210", "IT", "Male", "15/03/2002"));
            studentList.add(new Student("Priya Patel", "priya.p@klu.ac.in", "9876543211", "CS", "Female", "22/07/2001"));
            studentList.add(new Student("Ananya Singh", "asingh@klu.ac.in", "9876543212", "ECE", "Female", "10/11/2003"));
            saveStudents();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            @SuppressWarnings("unchecked")
            ArrayList<Student> loaded = (ArrayList<Student>) ois.readObject();
            studentList = loaded;

            int maxId = 100;
            for (Student s : studentList) {
                if (s.getId() > maxId) {
                    maxId = s.getId();
                }
            }
            Student.idCounter = maxId + 1;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading student records: " + e.getMessage());
        }
    }

    // Save students to serialized file
    private static void saveStudents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(studentList);
        } catch (IOException e) {
            System.out.println("Error saving student records: " + e.getMessage());
        }
    }

    private static void addStudent(Scanner scanner) {
        System.out.println("--- Add Student ---");

        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();
        while (name.isEmpty()) {
            System.out.print("Name cannot be empty. Enter Name: ");
            name = scanner.nextLine().trim();
        }

        System.out.print("Enter Email: ");
        String email = scanner.nextLine().trim();
        while (email.isEmpty() || !email.endsWith("@klu.ac.in")) {
            System.out.print("Invalid email. Must end with @klu.ac.in. Enter Email: ");
            email = scanner.nextLine().trim();
        }

        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine().trim();
        while (!phone.matches("\\d{10}")) {
            System.out.print("Invalid phone. Enter 10 digits. Enter Phone: ");
            phone = scanner.nextLine().trim();
        }

        System.out.print("Enter Department: ");
        String department = scanner.nextLine().trim();
        while (department.isEmpty()) {
            System.out.print("Department cannot be empty. Enter Department: ");
            department = scanner.nextLine().trim();
        }

        System.out.print("Enter Gender (Male/Female/Other): ");
        String gender = scanner.nextLine().trim();
        while (!isValidGender(gender)) {
            System.out.print("Invalid gender. Enter Gender (Male/Female/Other): ");
            gender = scanner.nextLine().trim();
        }
        gender = normalizeGender(gender);

        System.out.print("Enter Date of Birth (DD/MM/YYYY): ");
        String dateOfBirth = scanner.nextLine().trim();
        while (!dateOfBirth.matches("\\d{2}/\\d{2}/\\d{4}")) {
            System.out.print("Invalid format. Enter Date of Birth (DD/MM/YYYY): ");
            dateOfBirth = scanner.nextLine().trim();
        }

        Student student = new Student(name, email, phone, department, gender, dateOfBirth);
        studentList.add(student);
        saveStudents();
        System.out.println("\nSuccess: Student added successfully with ID: " + student.getId());
    }

    private static boolean isValidGender(String gender) {
        return gender.equalsIgnoreCase("Male")
                || gender.equalsIgnoreCase("Female")
                || gender.equalsIgnoreCase("Other");
    }

    private static String normalizeGender(String gender) {
        if (gender.equalsIgnoreCase("Male")) return "Male";
        if (gender.equalsIgnoreCase("Female")) return "Female";
        return "Other";
    }

    private static void viewStudents() {
        System.out.println("--- Student Records ---");
        if (studentList.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        printTableHeader();
        for (Student student : studentList) {
            System.out.println(student);
        }
        printTableFooter();
    }

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
                    System.out.println();
                    printTableHeader();
                    printedHeader = true;
                }
                System.out.println(student);
                found = true;
            }
        }

        if (printedHeader) {
            printTableFooter();
        }

        if (!found) {
            System.out.println("No matching student records found.");
        }
    }

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
                saveStudents();
                System.out.println("\nSuccess: Student record deleted successfully.");
            } else {
                System.out.println("Delete action cancelled.");
            }
        } else {
            System.out.println("Error: Student record with ID " + id + " not found.");
        }
    }
}
