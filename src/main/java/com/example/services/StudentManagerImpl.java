package com.example.services;

import com.example.models.Student;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class StudentManagerImpl implements StudentManager {
    private Dao<Student, String> studentsDao;

    public StudentManagerImpl() {
        try {
            var connectionSource = new JdbcConnectionSource("jdbc:sqlite:database.db");
            this.studentsDao = DaoManager.createDao(
                    connectionSource,
                    Student.class
            );

            TableUtils.createTableIfNotExists(connectionSource, Student.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Student getStudentById(Integer id) {
        try {
            return this.studentsDao.queryForId(String.valueOf(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addStudent(Student student) {
        try {
            this.studentsDao.create(student);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeStudent(String id) {
        try {
            Student student = this.studentsDao.queryForId(id);
            this.studentsDao.delete(student);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateStudent(Student student) {
        try {
            this.studentsDao.update(student);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Student> displayAllStudents() {
        try {
            return this.studentsDao.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double calculateAverageGrade() {
        return 0;
    }
}
