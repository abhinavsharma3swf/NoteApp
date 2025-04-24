package com.bridge.example.noteapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteController.class)
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private NoteService noteService;

    Note newNote;
    Note savedNote;
    Note updateNote;
    List<Note> notes = new ArrayList<>();
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        newNote = new Note(1L, "New note");
        savedNote = new Note(1L, "New note");
        updateNote = new Note(1L, "Update note");
        Mockito.when(noteService.createNote(Mockito.any(Note.class))).thenReturn(savedNote);
        Mockito.when(noteService.fetchNote()).thenReturn(notes);
        Mockito.when(noteService.deleteNote(1L)).thenReturn("Note deleted");
        Mockito.when(noteService.editNote(2L, updateNote)).thenReturn(updateNote);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAcceptPostRequestToCreateNote() throws Exception {
        String savedNoteJson = objectMapper.writeValueAsString(savedNote);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/noteapp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(savedNoteJson))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.text").value("New note"));
        Mockito.verify(noteService).createNote(any(Note.class));
    }

    @Test
    void shouldAcceptRequestToFetchAllNotes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/noteapp"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAcceptRequestToDeleteNote () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/noteapp/1"))
                .andExpect(status().isOk())
                .andExpect((content().string("Note deleted")));
        Mockito.verify(noteService, times(1)).deleteNote(1L);
    }

    @Test
    void shouldAcceptRequestToEditTheNote() throws Exception{
    String updateNoteJson = objectMapper.writeValueAsString(updateNote);
    mockMvc.perform(MockMvcRequestBuilders.put("/api/noteapp/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(updateNoteJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.text").value("Update note"));
        ArgumentCaptor<Note> noteArgumentCaptor = ArgumentCaptor.forClass(Note.class);
    Mockito.verify(noteService).editNote(1L, noteArgumentCaptor.capture());
    assertThat(noteArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(updateNote);
    }
}
