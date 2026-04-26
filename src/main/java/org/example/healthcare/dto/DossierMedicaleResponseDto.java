package org.example.healthcare.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DossierMedicaleResponseDto {
    private Long id;
    private String diagnostic;
    private String observation;
    private LocalDateTime dateCreation;
    private String patientNom;
}
