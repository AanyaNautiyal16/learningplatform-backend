package com.example.learningplatform_backend.service;

import com.example.learningplatform_backend.model.Course;
import com.example.learningplatform_backend.model.User;
import com.example.learningplatform_backend.repository.CourseRepository;
import com.example.learningplatform_backend.repository.UserRepository;
import com.example.learningplatform_backend.dto.CourseDTO;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    // 🔹 GET ALL COURSES (ENTITY)
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // 🔥 GET ALL COURSES (DTO)
    public List<CourseDTO> getAllCoursesDTO() {
        return courseRepository.findAll()
                .stream()
                .map(course -> new CourseDTO(
                        course.getId(),
                        course.getTitle(),
                        course.getPrice()
                ))
                .toList();
    }

    // 🔹 ADD COURSE (WITH USER RELATION)
    public Course addCourse(Course course) {

        if (course.getUser() == null || course.getUser().getId() == null) {
            throw new RuntimeException("User ID is required");
        }

        Integer userId = course.getUser().getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        course.setUser(user);

        return courseRepository.save(course);
    }

    // 🔹 GET COURSE BY ID (ENTITY)
    public Course getCourseById(int id) {
        return courseRepository.findById(id).orElse(null);
    }

    // 🔥 GET COURSE BY ID (DTO)
    public CourseDTO getCourseDTOById(int id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + id));

        return new CourseDTO(
                course.getId(),
                course.getTitle(),
                course.getPrice()
        );
    }

    // 🔹 UPDATE COURSE
    public Course updateCourse(int id, Course updatedCourse) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + id));

        course.setTitle(updatedCourse.getTitle());
        course.setDescription(updatedCourse.getDescription());
        course.setPrice(updatedCourse.getPrice());

        return courseRepository.save(course);
    }

    // 🔹 DELETE COURSE
    public void deleteCourse(int id) {
        courseRepository.deleteById(id);
    }
}