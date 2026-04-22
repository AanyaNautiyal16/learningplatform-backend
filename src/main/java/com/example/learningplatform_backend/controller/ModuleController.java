package com.example.learningplatform_backend.controller;

import com.example.learningplatform_backend.model.Module;
import com.example.learningplatform_backend.service.ModuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modules")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @PostMapping
    public ResponseEntity<Module> createModule(@RequestBody Module module) {
        Module createdModule = moduleService.createModule(module);
        return ResponseEntity.status(201).body(createdModule);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Module>> getModulesByCourse(@PathVariable int courseId) {
        List<Module> modules = moduleService.getModulesByCourseId(courseId);
        return ResponseEntity.ok(modules);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Module> getModuleById(@PathVariable int id) {
        Module module = moduleService.getModuleById(id);
        return ResponseEntity.ok(module);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Module> updateModule(@PathVariable int id, @RequestBody Module module) {
        Module updatedModule = moduleService.updateModule(id, module);
        return ResponseEntity.ok(updatedModule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable int id) {
        moduleService.deleteModule(id);
        return ResponseEntity.noContent().build();
    }
}