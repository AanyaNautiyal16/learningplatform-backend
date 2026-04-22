package com.example.learningplatform_backend.repository;

import com.example.learningplatform_backend.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Integer> {
}