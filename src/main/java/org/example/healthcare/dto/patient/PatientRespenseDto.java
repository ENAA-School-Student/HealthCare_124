package org.example.healthcare.dto.patient;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.healthcare.model.RendezVous;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientRespenseDto {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private LocalDateTime dateNaissance;
    private List<RendezVous> rendezVous;
}
