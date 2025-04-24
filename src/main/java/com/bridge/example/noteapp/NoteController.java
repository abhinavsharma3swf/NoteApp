package com.bridge.example.noteapp;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/noteapp")

@CrossOrigin(origins = "http://localhost:5173")

public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note newNote) {
    return new ResponseEntity<>(noteService.createNote(newNote), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Note> fetchNote(){
        return noteService.fetchNote();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteNote(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.deleteNote(id));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<String> editNote(@PathVariable Long id, @RequestBody Note updateNote){
//        Note updated = noteService.editNote(id, updateNote);
//        String messasge = "MNote update";
//        noteService.editNote(id, updateNote);
//        return ResponseEntity.ok(messasge);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> editNote(@PathVariable Long id, @RequestBody Note updateNote) {
        Note updated = noteService.editNote(id, updateNote);
        return new ResponseEntity<>(updateNote, HttpStatus.OK);
    }
}
