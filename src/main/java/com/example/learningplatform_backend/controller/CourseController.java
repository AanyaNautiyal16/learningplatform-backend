package com.example.learningplatform_backend.controller;

import com.example.learningplatform_backend.dto.CourseDTO;
import com.example.learningplatform_backend.model.Course;
import com.example.learningplatform_backend.service.CourseService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    // 🔹 GET ALL COURSES (DTO) - Accessible by STUDENT and ADMIN
    @GetMapping
    @PreAuthorize("hasAnyRole('STUDENT','ADMIN')")
    public ResponseEntity<List<CourseDTO>> getCourses() {
        return ResponseEntity.ok(courseService.getAllCoursesDTO());
    }

    // 🔹 GET BY ID (DTO) - Accessible by STUDENT and ADMIN
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STUDENT','ADMIN')")
    public ResponseEntity<CourseDTO> getCourse(@PathVariable int id) {
        return ResponseEntity.ok(courseService.getCourseDTOById(id));
    }

    // 🔹 CREATE - Admin only
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Course> addCourse(@Valid @RequestBody Course course) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(courseService.addCourse(course));
    }

    // 🔹 UPDATE - Admin only
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Course> updateCourse(
            @PathVariable int id,
            @Valid @RequestBody Course course) {

        return ResponseEntity.ok(courseService.updateCourse(id, course));
    }

    // 🔹 DELETE - Admin only
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCourse(@PathVariable int id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}