package com.bridge.example.noteapp;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
    Long deleteNoteById(Long id);
}
