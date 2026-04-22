package com.example.learningplatform_backend.service;

import com.example.learningplatform_backend.model.Lesson;
import com.example.learningplatform_backend.model.Module;
import com.example.learningplatform_backend.repository.LessonRepository;
import com.example.learningplatform_backend.repository.ModuleRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final ModuleRepository moduleRepository;

    public LessonService(LessonRepository lessonRepository, ModuleRepository moduleRepository) {
        this.lessonRepository = lessonRepository;
        this.moduleRepository = moduleRepository;
    }

    // 🔹 CREATE LESSON
    public Lesson createLesson(Lesson lesson) {

        // ✅ Check module exists
        if (lesson.getModule() == null || lesson.getModule().getId() == null) {
            throw new RuntimeException("Module ID is required");
        }

        Integer moduleId = lesson.getModule().getId();

        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found with ID: " + moduleId));

        lesson.setModule(module);

        return lessonRepository.save(lesson);
    }

    // 🔹 GET LESSONS BY MODULE ID
    public List<Lesson> getLessonsByModuleId(int moduleId) {

        return lessonRepository.findAll()
                .stream()
                .filter(l -> l.getModule() != null && l.getModule().getId() == moduleId)
                .toList();
    }

    // 🔹 GET LESSON BY ID
    public Lesson getLessonById(int id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found with ID: " + id));
    }

    // 🔹 UPDATE LESSON
    public Lesson updateLesson(int id, Lesson updatedLesson) {

        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found with ID: " + id));

        lesson.setTitle(updatedLesson.getTitle());
        lesson.setContent(updatedLesson.getContent());
        lesson.setVideoUrl(updatedLesson.getVideoUrl());

        return lessonRepository.save(lesson);
    }

    // 🔹 DELETE LESSON
    public void deleteLesson(int id) {
        if (!lessonRepository.existsById(id)) {
            throw new RuntimeException("Lesson not found with ID: " + id);
        }
        lessonRepository.deleteById(id);
    }
}