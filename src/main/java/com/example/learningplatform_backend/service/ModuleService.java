package com.example.learningplatform_backend.service;

import com.example.learningplatform_backend.model.Module;
import com.example.learningplatform_backend.model.Course;
import com.example.learningplatform_backend.repository.ModuleRepository;
import com.example.learningplatform_backend.repository.CourseRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final CourseRepository courseRepository;

    public ModuleService(ModuleRepository moduleRepository, CourseRepository courseRepository) {
        this.moduleRepository = moduleRepository;
        this.courseRepository = courseRepository;
    }

    // 🔹 CREATE MODULE
    public Module createModule(Module module) {

        if (module.getCourse() == null || module.getCourse().getId() == null) {
            throw new RuntimeException("Course ID is required");
        }

        Integer courseId = module.getCourse().getId();

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));

        module.setCourse(course);

        return moduleRepository.save(module);
    }

    // 🔹 GET MODULES BY COURSE ID
    public List<Module> getModulesByCourseId(int courseId) {
        return moduleRepository.findAll()
                .stream()
                .filter(m -> m.getCourse() != null && m.getCourse().getId() == courseId)
                .toList();
    }

    // 🔹 GET MODULE BY ID
    public Module getModuleById(int id) {
        return moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module not found with ID: " + id));
    }

    // 🔹 UPDATE MODULE
    public Module updateModule(int id, Module updatedModule) {

        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module not found with ID: " + id));

        module.setTitle(updatedModule.getTitle());

        return moduleRepository.save(module);
    }

    // 🔹 DELETE MODULE
    public void deleteModule(int id) {
        if (!moduleRepository.existsById(id)) {
            throw new RuntimeException("Module not found with ID: " + id);
        }
        moduleRepository.deleteById(id);
    }
}