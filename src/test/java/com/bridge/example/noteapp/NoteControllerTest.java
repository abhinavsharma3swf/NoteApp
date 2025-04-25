package com.bridge.example.noteapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    @BeforeEach
    void setUp() throws ParseException {
        newNote = new Note("New note", sdf.parse("03-04-2024").toInstant(), 5, .30);
        newNote.setId(1L);
        savedNote = new Note("New note", sdf.parse("03-04-2024").toInstant(), 5, .30);
        savedNote.setId(1L);
        updateNote = new Note("Update note", sdf.parse("03-04-2024").toInstant(), 5, .30);
        updateNote.setId(1L);
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
                .andExpect(jsonPath("$.text").value("New note"))
                .andExpect(jsonPath("$.date").value(sdf.parse("03-04-2024").toInstant().toString()))
                .andExpect(jsonPath("$.importance").value(5))
                .andExpect(jsonPath("$.completion").value(.30));


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
            .andExpect(jsonPath("$.text").value("Update note"))
            .andExpect(jsonPath("$.date").value(sdf.parse("03-04-2024").toInstant().toString()))
            .andExpect(jsonPath("$.importance").value(5))
            .andExpect(jsonPath("$.completion").value(.30));
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Note> noteArgumentCaptor = ArgumentCaptor.forClass(Note.class);
    Mockito.verify(noteService).editNote(idCaptor.capture(), noteArgumentCaptor.capture());
    assertThat(noteArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(updateNote);
    }
}
