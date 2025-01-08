package com.example.services;

import com.example.models.Student;

import java.util.List;

public interface StudentManager {
    Student getStudentById(Integer id);

    void addStudent(Student student);

    void removeStudent(String id);

    void updateStudent(Student student);

    List<Student> displayAllStudents();

    double calculateAverageGrade();
}
