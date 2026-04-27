package org.example.healthcare.dto.consultation;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.healthcare.model.DossierMedicale;
import org.example.healthcare.model.Medecin;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationResponseDto {
    private Long id;
    private String diagnostic;
    private String observation;
    private LocalDateTime date_consultation;
    private DossierMedicale dossier;
    private Medecin medecin;

}
