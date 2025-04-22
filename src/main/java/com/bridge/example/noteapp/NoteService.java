package com.bridge.example.noteapp;


import org.springframework.stereotype.Service;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note createNote( Note newNote){
        return noteRepository.save(newNote);
    }

    public int deleteNote(Long id){
        noteRepository.deleteNoteById(id);
        return -1;
    }
}
