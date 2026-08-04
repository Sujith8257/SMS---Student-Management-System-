/**
 * Student Management System - Frontend State Manager
 * Employs localStorage to act as a client-side database.
 */

(function () {
    const STORAGE_KEY = 'sms_students_registry';

    // Mock initial data if storage is empty
    const initialStudents = [
        { id: 101, name: "Alexander Wright", email: "alex.wright@university.edu", age: 20, course: "Computer Science", grade: 3.85 },
        { id: 102, name: "Sophia Martinez", email: "s.martinez@university.edu", age: 21, course: "Data Science", grade: 3.92 },
        { id: 103, name: "Marcus Thompson", email: "marcus.t@university.edu", age: 22, course: "Mechanical Engineering", grade: 2.85 },
        { id: 104, name: "Emily Henderson", email: "emily.h@university.edu", age: 19, course: "Bioinformatics", grade: 3.70 },
        { id: 105, name: "Darius Miller", email: "darius.m@university.edu", age: 23, course: "Cybersecurity", grade: 1.82 },
        { id: 106, name: "Livia Vance", email: "liv.vance@university.edu", age: 20, course: "Civil Engineering", grade: 3.45 }
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
                age: parseInt(studentData.age),
                course: studentData.course,
                grade: parseFloat(studentData.grade)
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
                    age: parseInt(updatedData.age),
                    course: updatedData.course,
                    grade: parseFloat(updatedData.grade)
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
