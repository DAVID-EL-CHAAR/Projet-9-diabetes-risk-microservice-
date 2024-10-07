package com.diabetes_risk.repository;

import com.diabetes_risk.model.Patient;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class PatientRepository {

    private final RestTemplate restTemplate;

    public PatientRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Patient getPatientById(Long patientId) {
        String url = "http://localhost:8080/patients/" + patientId;  // Appel au microservice Patient
        return restTemplate.getForObject(url, Patient.class);
    }
}
