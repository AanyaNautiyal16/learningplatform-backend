package com.example.learningplatform_backend.repository;

import com.example.learningplatform_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}