package model;

import java.sql.Date;

public class Student {
    private Integer student_id;
    private String email;
    private String password;
    private String full_name;

    // Default constructor
    public Student() {
    }

    // Constructor with all fields (except student_id which is auto-increment)
    public Student(String email, String password, String full_name) {
        this.email = email;
        this.password = password;
        this.full_name = full_name;
    }

    // Getters
    public Integer getStudent_id() {
        return student_id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFull_name() {
        return full_name;
    }

    // Setters
    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
}