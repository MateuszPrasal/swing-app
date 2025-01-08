package com.example.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@DatabaseTable(tableName = "students")
@NoArgsConstructor
public class Student implements Serializable {
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "age")
    private int age;
    @DatabaseField(columnName = "grade")
    private double grade;
    @DatabaseField(id = true)
    private Integer id;

    public Object[] toTableObject() {
        return new Object[]{id, name, age, grade};
    }
}
