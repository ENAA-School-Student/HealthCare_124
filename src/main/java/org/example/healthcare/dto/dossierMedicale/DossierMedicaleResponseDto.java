package org.example.healthcare.dto.dossierMedicale;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.healthcare.model.Patient;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DossierMedicaleResponseDto {
    private Long id;
    private LocalDateTime dateCreation;
    private Patient patient;
}
