package com.example.services;

import com.example.models.Student;

import java.util.List;

public interface StudentManager {
    /**
     * Metoda pobierająca dane o studencie na podstawie ID
     *
     * @param id
     * @return
     */
    Student getStudentById(Integer id);

    /**
     * Metoda dodająca studenta do bazy danych
     *
     * @param student
     */
    void addStudent(Student student);

    /**
     * Metoda usuwająca studenta z bazy danych na podstawie ID
     *
     * @param id
     */
    void removeStudent(String id);

    /**
     * Metoda do aktualizacji danych studenta
     *
     * @param student
     */
    void updateStudent(Student student);

    /**
     * Metoda zwracająca listę wszystkich studentów z bazy danych
     *
     * @return
     */
    List<Student> displayAllStudents();

    /**
     * Metoda zwracająca informację o średniej ocenie studentów
     *
     * @return
     */
    double calculateAverageGrade();
}
