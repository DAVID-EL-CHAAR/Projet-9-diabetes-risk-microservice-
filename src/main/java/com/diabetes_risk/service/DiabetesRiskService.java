package com.diabetes_risk.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import com.diabetes_risk.model.Note;
import com.diabetes_risk.model.Patient;
import com.diabetes_risk.repository.NoteRepository;
import com.diabetes_risk.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DiabetesRiskService {

    private final PatientRepository patientRepository;
    private final NoteRepository noteRepository;

    public DiabetesRiskService(PatientRepository patientRepository, NoteRepository noteRepository) {
        this.patientRepository = patientRepository;
        this.noteRepository = noteRepository;
    }

    public String getDiabetesRiskForPatient(Long patientId) {
        // Récupération des données du patient et des notes via les repositories
        Patient patient = patientRepository.getPatientById(patientId);
        List<Note> notes = noteRepository.getNotesByPatientId(patientId.toString());

        // Appliquer la logique d'évaluation du risque de diabète
        return evaluateDiabetesRisk(patient, notes);
    }
  
    private String evaluateDiabetesRisk(Patient patient, List<Note> notes) {
        // Liste des déclencheurs
        List<String> triggers = Arrays.asList("Hémoglobine A1C", "Microalbumine", "Taille", "Poids", 
            "Fumeur", "Fumeuse", "Anormal", "Cholestérol", "Vertiges", "Rechute", "Réaction", "Anticorps");

        int triggerCount = 0;

        // Compter le nombre de déclencheurs dans les notes (en ignorant la casse)
        for (Note note : notes) {
        	//if (note == null || note.getNoteContent() == null || note.getNoteContent().isEmpty()) continue;  // Ignorer les notes vides
        	//if (triggerCount > 7) break;  green code 
            String noteContent = note.getNoteContent().toLowerCase(); // Convertir en minuscule pour la comparaison
            for (String trigger : triggers) {
                if (noteContent.contains(trigger.toLowerCase())) {
                    triggerCount++;
                }
            }
        }
        

        // Calculer l'âge du patient
        int age = calculateAge(patient.getBirthDate());

        // Appliquer les règles pour évaluer le risque de diabète
        if (triggerCount == 0) {
            return "None";
        } else if (triggerCount >= 2 && triggerCount <= 5 && age > 30) {
            return "Borderline";
        } else if (age < 30 && patient.getGender().equalsIgnoreCase("M") && triggerCount >= 3 && triggerCount < 5) {
            return "In Danger";
        } else if (age < 30 && patient.getGender().equalsIgnoreCase("F") && triggerCount >= 4 && triggerCount < 7) {
            return "In Danger";
        } else if (age > 30 && triggerCount >= 6 && triggerCount <= 7) {
            return "In Danger";
        } else if (age < 30 && patient.getGender().equalsIgnoreCase("M") && triggerCount >= 5) {
            return "Early Onset";
        } else if (age < 30 && patient.getGender().equalsIgnoreCase("F") && triggerCount >= 7) {
            return "Early Onset";
        } else if (age > 30 && triggerCount >= 8) {
            return "Early Onset";
        }

        return "None";  // Par défaut, si aucune règle n'est respectée
    }


    // Méthode pour calculer l'âge à partir de la date de naissance (yyyy-MM-dd)
    private int calculateAge(String birthDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDateLocal = LocalDate.parse(birthDate, formatter);
        LocalDate currentDate = LocalDate.now();

        if ((birthDateLocal != null) && (currentDate != null)) {
            return Period.between(birthDateLocal, currentDate).getYears();
        } else {
            return 0;  // Valeur par défaut si la date n'est pas valide
        }
    }
}
