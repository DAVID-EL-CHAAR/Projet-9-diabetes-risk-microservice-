package com.diabetes_risk.repository;

import com.diabetes_risk.model.Patient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class PatientRepository {

    private final RestTemplate restTemplate;

    @Value("${microservice.patient-service-url}")
    private String patientServiceUrl;

    public PatientRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Patient getPatientById(Long patientId) {
        String url = patientServiceUrl + "/" + patientId;  // Appel au microservice Patient avec l'URL variabilisée
        return restTemplate.getForObject(url, Patient.class);
    }
}
