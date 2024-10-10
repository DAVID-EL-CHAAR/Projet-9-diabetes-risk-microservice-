package com.diabetes_risk.repository;

import com.diabetes_risk.model.Note;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Repository
public class NoteRepository {

    private final RestTemplate restTemplate;

    @Value("${microservice.note-service-url}")
    private String noteServiceUrl;

    public NoteRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Note> getNotesByPatientId(String patientId) {
        String url = noteServiceUrl + "/patient/" + patientId;  // Appel au microservice Note avec l'URL variabilisée
        Note[] notesArray = restTemplate.getForObject(url, Note[].class);
        return Arrays.asList(notesArray);
    }
}
