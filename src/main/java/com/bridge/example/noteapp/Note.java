package com.bridge.example.noteapp;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "Note")

public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private Instant date;
    private Integer importance;
    private Double completion;

    public Note() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Integer getImportance() {
        return importance;
    }

    public void setImportance(Integer importance) {
        this.importance = importance;
    }

    public Double getCompletion() {
        return completion;
    }

    public void setCompletion(Double completion) {
        this.completion = completion;
    }

    public Note(String text, Instant date, Integer rating, Double completion) {
        this.text = text;
        this.date = date;
        this.importance = rating;
        this.completion = completion;
    }
}
