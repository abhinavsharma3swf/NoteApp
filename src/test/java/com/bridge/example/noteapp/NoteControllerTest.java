package com.bridge.example.noteapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NoteController.class)
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NoteService noteService;

    Note newNote;
    Note savedNote;
    List<Note> notes = new ArrayList<>();
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        newNote = new Note(1L, "New note");
        savedNote = new Note(1L, "New note");
        Mockito.when(noteService.createNote(Mockito.any(Note.class))).thenReturn(savedNote);
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
}
