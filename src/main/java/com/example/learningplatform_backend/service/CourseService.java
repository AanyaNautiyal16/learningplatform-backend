package com.example.learningplatform_backend.service;

import com.example.learningplatform_backend.model.Course;
import com.example.learningplatform_backend.model.User;
import com.example.learningplatform_backend.repository.CourseRepository;
import com.example.learningplatform_backend.repository.UserRepository;
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

    // 🔹 GET ALL COURSES
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // 🔹 ADD COURSE (WITH USER RELATION)
    public Course addCourse(Course course) {

        // ✅ Null safety check
        if (course.getUser() == null || course.getUser().getId() == null) {
            throw new RuntimeException("User ID is required to create a course");
        }

        // ✅ Fetch full user from DB
        Integer userId = course.getUser().getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // ✅ Set full user object
        course.setUser(user);

        return courseRepository.save(course);
    }

    // 🔹 GET COURSE BY ID
    public Course getCourseById(int id) {
        return courseRepository.findById(id).orElse(null);
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