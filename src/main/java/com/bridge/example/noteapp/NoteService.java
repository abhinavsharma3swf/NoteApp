package com.bridge.example.noteapp;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note createNote( Note newNote){
        return noteRepository.save(newNote);
    }

    public String deleteNote(Long id) {
        if (noteRepository.deleteNoteById(id) == 0)
            return "Not Found";
        return "Deleted";
    }

    public Note editNote(Long id, Note updatedNote){
        Note note = noteRepository.findById(id).orElseThrow();
        note.setId(updatedNote.getId());
        note.setText(updatedNote.getText());
        return noteRepository.save(updatedNote);
    }

    public List<Note> fetchNote() {
        return noteRepository.findAll();
    }

}

