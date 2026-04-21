package com.example.learningplatform_backend.controller;

import com.example.learningplatform_backend.model.Lesson;
import com.example.learningplatform_backend.service.LessonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping
    public ResponseEntity<Lesson> createLesson(@RequestBody Lesson lesson) {
        Lesson createdLesson = lessonService.createLesson(lesson);
        return ResponseEntity.status(201).body(createdLesson);
    }

    @GetMapping("/module/{moduleId}")
    public ResponseEntity<List<Lesson>> getLessonsByModule(@PathVariable int moduleId) {
        List<Lesson> lessons = lessonService.getLessonsByModuleId(moduleId);
        return ResponseEntity.ok(lessons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable int id) {
        Lesson lesson = lessonService.getLessonById(id);
        return ResponseEntity.ok(lesson);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lesson> updateLesson(@PathVariable int id, @RequestBody Lesson lesson) {
        Lesson updatedLesson = lessonService.updateLesson(id, lesson);
        return ResponseEntity.ok(updatedLesson);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable int id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }
}