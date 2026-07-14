package org.example.healthcare.dto.auth;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientDTO {
    private String nom;
    private String prenom;
    private String telephone;
    private LocalDate dateNaissance;

}
