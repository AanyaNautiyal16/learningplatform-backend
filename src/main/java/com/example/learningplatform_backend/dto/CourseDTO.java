package com.example.learningplatform_backend.dto;

public class CourseDTO {

    private Integer id;
    private String title;
    private double price;

    public CourseDTO(Integer id, String title, double price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public Integer getId() { return id; }
    public String getTitle() { return title; }
    public double getPrice() { return price; }
}