package com.diabetes_risk.controller;

import com.diabetes_risk.service.DiabetesRiskService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/diabetes-risk")
public class DiabetesRiskController {

    private final DiabetesRiskService diabetesRiskService;

    public DiabetesRiskController(DiabetesRiskService diabetesRiskService) {
        this.diabetesRiskService = diabetesRiskService;
    }

    // Endpoint pour obtenir le niveau de risque de diabète pour un patient spécifique
    @GetMapping("/{patientId}")
    public String getDiabetesRisk(@PathVariable Long patientId) {
        // Appelle le service pour obtenir le risque de diabète du patient
        return diabetesRiskService.getDiabetesRiskForPatient(patientId);
    }
}
