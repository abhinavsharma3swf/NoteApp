package com.bridge.example.noteapp;

import jakarta.persistence.*;

@Entity
@Table(name = "Note")

public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;

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

    public Note(Long id, String text) {
        this.id = id;
        this.text = text;
    }
}
