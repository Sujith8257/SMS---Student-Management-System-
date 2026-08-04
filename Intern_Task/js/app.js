/**
 * Student Management System - Frontend State Manager
 * Employs localStorage to act as a client-side database.
 */

(function () {
    const STORAGE_KEY = 'sms_students_registry_v2';

    // Mock initial data matching the previous hardcoded values
    const initialStudents = [
        { id: 101, name: "Rahul Sharma", email: "rahul@gmail.com", phone: "9876543210", dept: "IT", gender: "Male", dob: "12-05-2002" },
        { id: 102, name: "Priya Patel", email: "priya.p@student.edu", phone: "9876543211", dept: "CS", gender: "Female", dob: "24-08-2001" },
        { id: 103, name: "Ananya Singh", email: "asingh22@edu.com", phone: "9876543212", dept: "ECE", gender: "Female", dob: "10-11-2003" }
    ];

    // Helper to read database
    function getStoredStudents() {
        const stored = localStorage.getItem(STORAGE_KEY);
        if (!stored) {
            localStorage.setItem(STORAGE_KEY, JSON.stringify(initialStudents));
            return initialStudents;
        }
        return JSON.parse(stored);
    }

    // Helper to write database
    function saveStoredStudents(students) {
        localStorage.setItem(STORAGE_KEY, JSON.stringify(students));
    }

    // Exported SMS APIs
    window.SMS = {
        getStudents: function () {
            return getStoredStudents();
        },

        getStudent: function (id) {
            const students = getStoredStudents();
            return students.find(s => s.id == id) || null;
        },

        addStudent: function (studentData) {
            const students = getStoredStudents();
            
            // Generate unique numeric ID
            const nextId = students.length > 0 ? Math.max(...students.map(s => s.id)) + 1 : 101;
            
            const newStudent = {
                id: nextId,
                name: studentData.name,
                email: studentData.email,
                phone: studentData.phone,
                dept: studentData.dept,
                gender: studentData.gender,
                dob: studentData.dob
            };

            students.push(newStudent);
            saveStoredStudents(students);
            return newStudent;
        },

        updateStudent: function (id, updatedData) {
            const students = getStoredStudents();
            const index = students.findIndex(s => s.id == id);
            
            if (index !== -1) {
                students[index] = {
                    id: parseInt(id),
                    name: updatedData.name,
                    email: updatedData.email,
                    phone: updatedData.phone,
                    dept: updatedData.dept,
                    gender: updatedData.gender,
                    dob: updatedData.dob
                };
                saveStoredStudents(students);
                return students[index];
            }
            return null;
        },

        deleteStudent: function (id) {
            let students = getStoredStudents();
            students = students.filter(s => s.id != id);
            saveStoredStudents(students);
        }
    };
})();
