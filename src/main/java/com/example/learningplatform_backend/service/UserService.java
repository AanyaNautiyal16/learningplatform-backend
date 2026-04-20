package com.example.learningplatform_backend.service;

import com.example.learningplatform_backend.model.User;
import com.example.learningplatform_backend.model.Course;
import com.example.learningplatform_backend.repository.UserRepository;
import com.example.learningplatform_backend.dto.UserDTO;
import com.example.learningplatform_backend.dto.CourseDTO;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 🔹 GET ALL USERS
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 🔹 ADD USER
    public User addUser(User user) {
        return userRepository.save(user);
    }

    // 🔹 GET USER BY ID (ENTITY)
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserDTO getUserDTOById(int id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<CourseDTO> courseDTOs = user.getCourses()
                .stream()
                .map(course -> new CourseDTO(
                        course.getId(),
                        course.getTitle(),
                        course.getPrice()
                ))
                .toList();

        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                courseDTOs
        );
    }

    // 🔹 UPDATE USER
    public User updateUser(int id, User updatedUser) {

        User user = userRepository.findById(id).orElse(null);

        if (user != null) {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            return userRepository.save(user);
        }
        return null;
    }

    // 🔹 DELETE USER
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}