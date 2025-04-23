package com.bridge.example.noteapp;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class NoteServiceTest {
    @Mock
    NoteRepository noteRepository;

    @InjectMocks
    NoteService noteService;

    Note newNote;
    Note savedNote;
    List<Note> notes;

    @BeforeEach
    void setUp() {
        newNote = new Note(1L, "Hello this is my first note");
        savedNote = new Note(1L, "Hello this is my first note");
        notes = new ArrayList<>(List.of(newNote,savedNote));
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createNote() {
        when(noteRepository.save(newNote)).thenReturn(newNote);
        noteService.createNote(newNote);
        verify(noteRepository, times(1)).save(any(Note.class));
        assertThat(newNote).isEqualTo(newNote);
    }

    @Test
    void deleteNote() {
        when(noteRepository.deleteNoteById(1L)).thenReturn(1L);
        String message = noteService.deleteNote(1L);
        verify(noteRepository, times (1)).deleteNoteById(1L);
        assertThat(message).isEqualTo("Deleted");
    }

    @Test
    void deletedNote() {
        when(noteRepository.deleteNoteById(1L)).thenReturn(0L);
        String message = noteService.deleteNote(1L);
        verify(noteRepository, times(1)).deleteNoteById(1L);
        assertThat(message).isEqualTo("Not Found");
    }

    @Test
    void editNote() {
        Note updatedNote = new Note( 2L, "I am new note");
        when(noteRepository.findById(1L)).thenReturn(Optional.of(newNote));
        when(noteRepository.save(any(Note.class))).thenReturn(updatedNote);
        Note result = noteService.editNote(1L, updatedNote);
        assertThat(result.getId()).isEqualTo(2L);
        assertThat(result.getText()).isEqualTo("I am new note");
    }

    @Test
    void fetchNote() {
        when(noteRepository.findAll()).thenReturn(notes);
        List<Note> listofNoteRequest = noteService.fetchNote();
        verify(noteRepository, times(1)).findAll();
        assertThat(listofNoteRequest).isEqualTo(notes);
    }
}
