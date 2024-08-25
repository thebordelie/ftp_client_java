package ru.infotecs.intern.model;

import java.util.Comparator;

public class Student {
    private long id;
    private String name;

    public Student() {
    }

    public Student(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != Student.class) return false;
        Student student = (Student) obj;
        return this.id == student.getId() && this.name.equals(student.getName());
    }
}


