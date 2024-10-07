package com.diabetes_risk.repository;

import com.diabetes_risk.model.Note;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Repository
public class NoteRepository {

    private final RestTemplate restTemplate;

    public NoteRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Note> getNotesByPatientId(String patientId) {
        String url = "http://localhost:8083/notes/patient/" + patientId;  // Appel au microservice Note
        Note[] notesArray = restTemplate.getForObject(url, Note[].class);
        return Arrays.asList(notesArray);
    }
}
