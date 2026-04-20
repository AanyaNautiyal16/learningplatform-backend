package com.example.learningplatform_backend.repository;

import com.example.learningplatform_backend.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}