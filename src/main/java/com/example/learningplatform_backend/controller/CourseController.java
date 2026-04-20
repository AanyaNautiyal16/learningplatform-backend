package com.example.learningplatform_backend.controller;

import com.example.learningplatform_backend.dto.CourseDTO;
import com.example.learningplatform_backend.model.Course;
import com.example.learningplatform_backend.service.CourseService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // 🔹 GET ALL COURSES (DTO)
    @GetMapping
    public ResponseEntity<List<CourseDTO>> getCourses() {
        return ResponseEntity.ok(courseService.getAllCoursesDTO());
    }

    // 🔹 GET BY ID (DTO)
    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourse(@PathVariable int id) {
        return ResponseEntity.ok(courseService.getCourseDTOById(id));
    }

    // 🔹 CREATE
    @PostMapping
    public ResponseEntity<Course> addCourse(@Valid @RequestBody Course course) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(courseService.addCourse(course));
    }

    // 🔹 UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable int id,
            @Valid @RequestBody Course course) {

        return ResponseEntity.ok(courseService.updateCourse(id, course));
    }

    // 🔹 DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}