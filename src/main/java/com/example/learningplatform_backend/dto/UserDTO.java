package com.example.learningplatform_backend.dto;

import java.util.List;

public class UserDTO {

    private Integer id;
    private String name;
    private String email;
    private List<CourseDTO> courses;

    public UserDTO(Integer id, String name, String email, List<CourseDTO> courses) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.courses = courses;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public List<CourseDTO> getCourses() { return courses; }
}