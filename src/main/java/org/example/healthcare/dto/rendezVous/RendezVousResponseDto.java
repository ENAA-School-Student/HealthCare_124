package org.example.healthcare.dto.rendezVous;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.healthcare.enums.RendezVousStatut;
import org.example.healthcare.model.Medecin;
import org.example.healthcare.model.Patient;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RendezVousResponseDto {
    private Long id ;
    private LocalDateTime dateRendezVous;
    private RendezVousStatut statut;
    private Patient patient;
    private Medecin medecin;
}
