package com.bridge.example.noteapp;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

public class NoteServiceTest {
    @Mock
    NoteRepository noteRepository;

    @InjectMocks
    NoteService noteService;

    Note newnote;
    Note savedNote;
    List<Note> notes;

    @BeforeEach
    void setUp() {
        Note newNote = new Note(1L, "Hello this is my first note");
        Note savedNote = new Note(1L, "Hello this is my first note");

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createNote() {
        when(noteRepository.save(newnote)).thenReturn(newnote);
        Note newNote = new Note(1L, "Hello this is my first note");
        Note actualRequest = noteService.createNote(newNote) ;
        verify(noteRepository, times(1)).save(any(Note.class));
        assertThat(newnote).isEqualTo(savedNote);
    }

    @Test
    void deleteNote() {
        Note deleteNote = new Note(1L, "delete");
        deleteNote.setId(1L);
        when(noteRepository.deleteNoteById(deleteNote.getId())).thenReturn(deleteNote.getId());
        Mockito.verify(noteRepository, times(1)).deleteById(1L);
    }

}
