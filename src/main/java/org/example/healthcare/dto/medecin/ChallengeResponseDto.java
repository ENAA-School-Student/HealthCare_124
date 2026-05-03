package org.example.healthcare.dto.medecin;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.healthcare.model.RendezVous;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeResponseDto {
    private long id;
    private String nom ;
    private String prenom;
    private String email;
    private String specialite;
    private Long countRendezvous;
    private List<RendezVous> rendezVous;
}
