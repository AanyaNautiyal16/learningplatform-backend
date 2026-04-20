package com.example.learningplatform_backend.service;

import com.example.learningplatform_backend.model.User;
import com.example.learningplatform_backend.dto.UserDTO;
import com.example.learningplatform_backend.dto.CourseDTO;
import com.example.learningplatform_backend.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 🔹 GET ALL USERS (ENTITY)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 🔥 GET ALL USERS (DTO)
    public List<UserDTO> getAllUsersDTO() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getCourses().stream()
                                .map(c -> new CourseDTO(
                                        c.getId(),
                                        c.getTitle(),
                                        c.getPrice()
                                ))
                                .toList()
                ))
                .toList();
    }

    // 🔹 ADD USER
    public User addUser(User user) {
        return userRepository.save(user);
    }

    // 🔹 GET USER BY ID
    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    // 🔥 GET USER DTO
    public UserDTO getUserDTOById(int id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

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

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());

        return userRepository.save(user);
    }

    // 🔹 DELETE USER
    public void deleteUser(int id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }
}