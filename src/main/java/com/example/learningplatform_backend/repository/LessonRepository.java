package com.example.learningplatform_backend.repository;

import com.example.learningplatform_backend.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
}