package org.example.healthcare.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.healthcare.model.RendezVous;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedecinResponseDto {
    private long id;
    private String nom ;
    private String prenom;
    private String email;
    private String telephone;
    private String specialite;
    private List<RendezVous> rendezVous;

}
