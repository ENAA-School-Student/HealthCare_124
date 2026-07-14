package org.example.healthcare.dto.medecin;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.healthcare.dto.rendezVous.RendezVousResponseDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedecinResponseDto {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String specialite;
    private List<RendezVousResponseDto> rendezVous;
}
