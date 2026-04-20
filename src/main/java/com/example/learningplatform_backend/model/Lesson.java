package com.example.learningplatform_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Lesson title is required")
    @Size(min = 1, max = 200, message = "Title must be between 1-200 characters")
    @Column(nullable = false)
    private String title;

    @Column(length = 5000)
    private String content;

    @Column(length = 500)
    private String videoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    // Constructors
    public Lesson() {}

    public Lesson(String title, String content, String videoUrl, Module module) {
        this.title = title;
        this.content = content;
        this.videoUrl = videoUrl;
        this.module = module;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }

    public Module getModule() { return module; }
    public void setModule(Module module) { this.module = module; }
}