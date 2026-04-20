package com.example.learningplatform_backend.controller;

import com.example.learningplatform_backend.model.User;
import com.example.learningplatform_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

/**
 * UserController - Handles all user-related HTTP requests
 * 
 * ✅ Uses @Valid to enforce validation defined in User entity
 * ✅ Returns proper HTTP status codes
 * ✅ Uses UserService for business logic
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * GET /users - Retrieve all users
     * @return List of all users with 200 OK status
     */
    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * GET /users/{id} - Retrieve a specific user by ID
     * @param id User ID
     * @return User object with 200 OK, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * POST /users - Create a new user
     * 
     * ✅ @Valid enforces User validation annotations:
     *    - @NotBlank on name and email
     *    - @Size on name (2-100 chars)
     *    - @Email on email format
     * 
     * If validation fails, Spring automatically returns 400 Bad Request
     * with error details in the response body.
     * 
     * @param user User object from request body (must be valid)
     * @return Created user with 201 CREATED status
     */
    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        User createdUser = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * PUT /users/{id} - Update an existing user
     * @param id User ID
     * @param updatedUser Updated user data (validated)
     * @return Updated user with 200 OK, or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @Valid @RequestBody User updatedUser) {
        User user = userService.updateUser(id, updatedUser);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * DELETE /users/{id} - Delete a user
     * @param id User ID
     * @return 204 NO CONTENT on success
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}