package org.example.healthcare.dto.rendezVous;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.healthcare.enums.RendezVousStatut;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RendezVousResponseDto {
    private Long id;
    private LocalDateTime dateRendezVous;
    private RendezVousStatut statut;
    private Long patientId;
    private String patientNom;
    private String patientPrenom;
    private Long medecinId;
    private String medecinNom;
    private String medecinPrenom;
}
